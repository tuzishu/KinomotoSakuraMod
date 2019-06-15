package KinomotoSakuraMod.Powers;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.RemoveAllPowersAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;

public class KSMOD_ReturnPower extends KSMOD_CustomPower
{
    public static final String POWER_ID = "KSMOD_ReturnPower";
    private static final String POWER_NAME;
    private static final String[] POWER_DESCRIPTIONS;
    private static final String POWER_IMG_PATH = "img/powers/default_power.png";
    private static final PowerType POWER_TYPE = PowerType.BUFF;
    private ArrayList<AbstractPower> powerList = new ArrayList<>();
    private int recordHP;
    private int recordBlock;

    static
    {
        PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        POWER_NAME = powerStrings.NAME;
        POWER_DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }

    public KSMOD_ReturnPower(AbstractCreature target, int amount)
    {
        super(POWER_ID, POWER_NAME, POWER_IMG_PATH, POWER_TYPE, target, amount);
        this.updateDescription();
    }

    public void updateDescription()
    {
        this.description = POWER_DESCRIPTIONS[0] + this.amount + POWER_DESCRIPTIONS[1];
    }

    public void onInitialApplication()
    {
        this.powerList = (ArrayList<AbstractPower>) this.owner.powers.clone();
        this.recordHP = this.owner.currentHealth;
        this.recordBlock = this.owner.currentBlock;
        if (this.powerList.contains(this))
        {
            this.powerList.remove(this);
        }
    }

    public void atStartOfTurn()
    {
        if (this.amount > 1)
        {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this, 1));
        }
        else
        {
            AbstractDungeon.actionManager.addToBottom(new RemoveAllPowersAction(this.owner, false));
            for (AbstractPower power : this.powerList)
            {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, power));
            }
            AbstractDungeon.actionManager.addToBottom(new HealAction(this.owner, this.owner, this.recordHP - this.owner.currentHealth));
            AbstractDungeon.actionManager.addToBottom(new RemoveAllBlockAction(this.owner, this.owner));
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this.owner, this.owner, this.recordBlock));
        }
    }
}
