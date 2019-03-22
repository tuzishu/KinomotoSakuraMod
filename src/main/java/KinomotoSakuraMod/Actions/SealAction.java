package KinomotoSakuraMod.Actions;

import KinomotoSakuraMod.Powers.SealPower;
import KinomotoSakuraMod.Utility.ModLogger;
import KinomotoSakuraMod.Utility.ModNavigator;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MinionPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class SealAction extends AbstractGameAction
{
    private static final float DURATION = Settings.ACTION_DUR_XFAST;
    private AbstractPlayer player;
    private AbstractMonster monster;
    private int damage;

    public SealAction(AbstractMonster monster, int damage)
    {
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.player = AbstractDungeon.player;
        this.monster = monster;
        this.damage = damage;
        this.duration = DURATION;
    }

    public void update()
    {
        if (this.duration == Settings.ACTION_DUR_XFAST && this.monster != null)
        {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.monster.hb.cX, this.monster.hb.cY, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            this.monster.damage(new DamageInfo(this.player, this.damage));

            if (!this.monster.hasPower(MinionPower.POWER_ID) && this.monster.isDying || this.monster.currentHealth <= 0)
            {
                ModLogger.logger.info(ModNavigator.getLineNumber());
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new SealPower(), 1));
            }

            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead())
            {
                AbstractDungeon.getCurrRoom().addCardToRewards();
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }
        tickDuration();
    }
}
