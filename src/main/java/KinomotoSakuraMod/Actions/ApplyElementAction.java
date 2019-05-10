package KinomotoSakuraMod.Actions;

import KinomotoSakuraMod.Powers.EarthyElementpower;
import KinomotoSakuraMod.Powers.EarthyMagickPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class ApplyElementAction extends AbstractGameAction
{
    public static final String ACTION_ID = "ApplyElementAction";
    private static final float DURATION = Settings.ACTION_DUR_XFAST;
    private static final int MAGIC_ADDITION = 2;
    private AbstractPlayer player;
    private AbstractMonster monster;
    private AbstractPower power;
    private boolean isApplyAddition;

    public ApplyElementAction(AbstractCreature target, AbstractCreature source, AbstractPower powerToApply, int amount, boolean isApplyAddition)
    {
        this.actionType = ActionType.DAMAGE;
        this.target = target;
        this.source = source;
        this.amount = amount;
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
                if (this.source.hasPower(EarthyMagickPower.POWER_ID))
                {
                    this.amount += isApplyAddition ? MAGIC_ADDITION : 0;
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.target, this.source, new EarthyElementpower(this.target, this.amount), this.amount));
                }
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
