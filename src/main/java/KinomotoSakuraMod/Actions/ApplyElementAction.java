package KinomotoSakuraMod.Actions;

import KinomotoSakuraMod.Powers.EarthyElementpower;
import KinomotoSakuraMod.Powers.EarthyMagickPower;
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


    public ApplyElementAction(AbstractCreature target, AbstractCreature source, AbstractPower powerToApply)
    {
        this(target, source, powerToApply, 1, false);
    }

    public ApplyElementAction(AbstractCreature target, AbstractCreature source, AbstractPower powerToApply, int stackAmount)
    {
        this(target, source, powerToApply, stackAmount, false);
    }

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
            if (this.power instanceof EarthyElementpower)
            {
                if (this.source.hasPower(EarthyMagickPower.POWER_ID) && isApplyAddition)
                {
                    this.amount += EarthyMagickPower.EXTRA_NUMBER;
                    this.source.getPower(EarthyMagickPower.POWER_ID).flash();
                }
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.target, this.source, new EarthyElementpower(this.target, this.amount), this.amount));
            }
            // else if (this.power instanceof )
            // {
            //
            // }
            this.isDone = true;
        }
        tickDuration();
    }
}
