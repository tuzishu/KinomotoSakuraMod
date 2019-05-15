package KinomotoSakuraMod.Actions;

import KinomotoSakuraMod.Powers.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class ApplyElementAction extends AbstractGameAction
{
    public static final String ACTION_ID = "ApplyElementAction";
    private static final float DURATION = Settings.ACTION_DUR_XFAST;
    private AbstractPower power;
    private boolean isApplyAddition;

    public ApplyElementAction(AbstractCreature target, AbstractCreature source, AbstractPower powerToApply, int stackAmount, boolean isApplyAddition)
    {
        this.actionType = ActionType.DAMAGE;
        this.setValues(target, source, stackAmount);
        this.power = powerToApply;
        this.isApplyAddition = isApplyAddition;
        this.duration = DURATION;
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
            if (this.power instanceof EarthyElementPower)
            {
                if (this.source.hasPower(EarthyMagickPower.POWER_ID) && isApplyAddition)
                {
                    this.amount += EarthyMagickPower.EXTRA_NUMBER;
                    this.source.getPower(EarthyMagickPower.POWER_ID).flash();
                }
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.target, this.source, new EarthyElementPower(this.target, this.amount), this.amount));
            }
            else if (this.power instanceof WateryElementPower)
            {
                if (this.source.hasPower(WateryMagickPower.POWER_ID) && isApplyAddition)
                {
                    this.amount += WateryMagickPower.EXTRA_NUMBER;
                    this.source.getPower(WateryMagickPower.POWER_ID).flash();
                }
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.target, this.source, new WateryElementPower(this.target, this.amount), this.amount));
            }
            else if (this.power instanceof FireyElementPower)
            {
                if (this.source.hasPower(FireyMagickPower.POWER_ID) && isApplyAddition)
                {
                    this.amount += FireyMagickPower.EXTRA_NUMBER;
                    this.source.getPower(FireyMagickPower.POWER_ID).flash();
                }
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.target, this.source, new FireyElementPower(this.target, this.amount), this.amount));
            }
            else if (this.power instanceof WindyElementPower)
            {
                if (this.source.hasPower(WindyMagickPower.POWER_ID) && isApplyAddition)
                {
                    this.amount += WindyMagickPower.EXTRA_NUMBER;
                    this.source.getPower(WindyMagickPower.POWER_ID).flash();
                }
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.target, this.source, new WindyElementPower(this.target, this.amount), this.amount));
            }
            this.isDone = true;
        }
        tickDuration();
    }
}
