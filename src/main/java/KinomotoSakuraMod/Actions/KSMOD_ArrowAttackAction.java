package KinomotoSakuraMod.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import com.megacrit.cardcrawl.vfx.combat.ThrowDaggerEffect;

public class KSMOD_ArrowAttackAction extends AbstractGameAction
{
    public static final String ACTION_ID = "KSMOD_ArrowAttackAction";
    private static final float DURATION = Settings.ACTION_DUR_FAST;
    private static final float DURATION_WAIT = 0.2F;
    private static final float OFFSET_X = 220F;
    private static final float OFFSET_Y = 170F;
    private static final float ANGLE = -60F;
    private AbstractPlayer player;
    private int damage;
    private AbstractMonster monster;

    public KSMOD_ArrowAttackAction(AbstractMonster monster, int damage, int amount)
    {
        this.actionType = ActionType.DAMAGE;
        this.player = AbstractDungeon.player;
        this.duration = DURATION;
        this.damage = damage;
        this.monster = monster;
        this.amount = amount;
    }

    public void update()
    {

        if (AbstractDungeon.getCurrRoom().isBattleEnding() || AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead())
        {
            AbstractDungeon.actionManager.clearPostCombatActions();
            this.isDone = true;
            return;
        }
        this.target = this.monster == null ? AbstractDungeon.getRandomMonster() : this.monster;
        if (this.target != null && this.target.currentHealth > 0)
        {
            AbstractDungeon.effectList.add(new ThrowDaggerEffect(this.target.hb.cX + OFFSET_X, this.target.hb.cY + OFFSET_Y, ANGLE));
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.SLASH_VERTICAL));
            DamageInfo info = new DamageInfo(this.player, this.damage, DamageInfo.DamageType.NORMAL);
            // info.applyPowers(info.owner, this.target);
            this.target.damage(info);
            if (this.amount > 1 && !AbstractDungeon.getMonsters().areMonstersBasicallyDead())
            {
                AbstractDungeon.actionManager.addToTop(new KSMOD_ArrowAttackAction(this.monster, this.damage, this.amount - 1));
            }
            AbstractDungeon.actionManager.addToTop(new WaitAction(DURATION_WAIT));
        }
        this.isDone = true;
    }
}
