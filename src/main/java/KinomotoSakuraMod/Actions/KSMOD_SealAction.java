package KinomotoSakuraMod.Actions;

import KinomotoSakuraMod.Powers.KSMOD_SealPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MinionPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class KSMOD_SealAction extends AbstractGameAction
{
    private static final float DURATION = Settings.ACTION_DUR_XFAST;
    private AbstractPlayer player;
    private AbstractMonster monster;
    private int damage;

    public KSMOD_SealAction(AbstractMonster monster, int damage)
    {
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.player = AbstractDungeon.player;
        this.monster = monster;
        this.damage = damage;
        this.duration = DURATION;
    }

    public void update()
    {
        if (AbstractDungeon.getCurrRoom().isBattleEnding())
        {
            this.isDone = true;
            return;
        }
        if (this.duration == DURATION && this.monster != null)
        {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.monster.hb.cX, this.monster.hb.cY, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            this.monster.damage(new DamageInfo(this.player, this.damage));

            if (!this.monster.hasPower(MinionPower.POWER_ID) && this.monster.isDying || this.monster.currentHealth <= 0 || AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead())
            {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new KSMOD_SealPower(), 1));
                AbstractDungeon.getCurrRoom().addCardToRewards();
            }
        }
        tickDuration();
    }
}
