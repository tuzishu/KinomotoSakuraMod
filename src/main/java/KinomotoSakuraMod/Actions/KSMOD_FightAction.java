package KinomotoSakuraMod.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class KSMOD_FightAction extends AbstractGameAction
{
    public static final String ACTION_ID = "KSMOD_FightAction";
    private static final float DURATION = Settings.ACTION_DUR_FAST;
    private int targetDamage;
    private int recordHP;
    private int gainStrength;

    public KSMOD_FightAction(AbstractCreature target, int targetDamage, int recordHP, int gainStrength)
    {
        this.actionType = ActionType.DAMAGE;
        this.duration = DURATION;
        this.target = target;
        this.targetDamage = targetDamage;
        this.recordHP = recordHP;
        this.gainStrength = gainStrength;
    }

    public void update()
    {
        if (AbstractDungeon.getCurrRoom().isBattleEnding())
        {
            this.isDone = true;
            return;
        }
        if (this.duration == DURATION)
        {
            int strengthAmount = 0;
            if (this.target.hasPower(StrengthPower.POWER_ID))
            {
                strengthAmount = this.target.getPower(StrengthPower.POWER_ID).amount;
            }
            if (this.recordHP - this.target.currentHealth >= this.targetDamage + strengthAmount)
            {
                AbstractPlayer player = AbstractDungeon.player;
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(player, player, new StrengthPower(player, this.gainStrength), this.gainStrength));
            }
            tickDuration();
            return;
        }
        tickDuration();
    }
}
