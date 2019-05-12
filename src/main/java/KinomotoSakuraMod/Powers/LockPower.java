package KinomotoSakuraMod.Powers;

import KinomotoSakuraMod.Utility.ModUtility;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class LockPower extends CustomPower
{
    public static final String POWER_ID = "LockPower";
    private static final String POWER_NAME;
    private static final String[] POWER_DESCRIPTIONS;
    private static final String POWER_IMG_PATH = "img/powers/default_power.png";
    private static final PowerType POWER_TYPE = PowerType.DEBUFF;

    static
    {
        PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        POWER_NAME = powerStrings.NAME;
        POWER_DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }

    public LockPower(AbstractCreature target, int amount)
    {
        super(POWER_ID, POWER_NAME, POWER_IMG_PATH, POWER_TYPE, target, amount);
        this.updateDescription();
    }

    public void updateDescription()
    {
        this.description = POWER_DESCRIPTIONS[0];
    }

    public void atEndOfTurn(boolean isPlayer)
    {
        if (this.amount > 1)
        {
            AbstractDungeon.actionManager.addToTop(new ReducePowerAction(this.owner, AbstractDungeon.player, this, 1));
        }
        else
        {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, AbstractDungeon.player, this));
        }
    }

    public void OnActived()
    {
        this.flash();
        if (this.amount > 1)
        {
            AbstractDungeon.actionManager.addToTop(new ReducePowerAction(this.owner, AbstractDungeon.player, this, 1));
        }
        else
        {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, AbstractDungeon.player, this));
        }
    }
}
