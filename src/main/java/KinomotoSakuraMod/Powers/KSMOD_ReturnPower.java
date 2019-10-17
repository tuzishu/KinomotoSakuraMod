package KinomotoSakuraMod.Powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.RemoveAllPowersAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.HashMap;
import java.util.Map;

public class KSMOD_ReturnPower extends KSMOD_CustomPower
{
    public static final String POWER_ID = "KSMOD_ReturnPower";
    private static final String POWER_NAME;
    private static final String[] POWER_DESCRIPTIONS;
    private static final String POWER_IMG_PATH = "retain";
    private static final PowerType POWER_TYPE = PowerType.BUFF;
    private HashMap<AbstractPower, Integer> recordPowerGroup = new HashMap<>();
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
        super(POWER_ID, POWER_NAME, POWER_IMG_PATH, POWER_TYPE, target, amount, false);
        this.updateDescription();
    }

    public void updateDescription()
    {
        this.description = POWER_DESCRIPTIONS[0] + this.amount + POWER_DESCRIPTIONS[1];
    }

    public void onInitialApplication()
    {
        for (AbstractPower power : this.owner.powers)
        {
            if (!(power instanceof KSMOD_ReturnPower))
            {
                this.recordPowerGroup.put(power, power.amount);
            }
        }
        this.recordHP = this.owner.currentHealth;
        this.recordBlock = this.owner.currentBlock;

    }

    public void atStartOfTurn()
    {
        if (this.amount > 1)
        {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this, 1));
        }
        else
        {
            for (AbstractPower p: this.owner.powers)
            {
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, p));
            }
        }
    }

    public void onRemove()
    {
        for (Map.Entry<AbstractPower, Integer> entry : this.recordPowerGroup.entrySet())
        {
            AbstractPower power = entry.getKey();
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, power));
            power.amount = entry.getValue();
            power.updateDescription();
        }

        if (this.recordHP > this.owner.currentHealth)
        {
            AbstractDungeon.actionManager.addToBottom(new HealAction(this.owner, this.owner, this.recordHP - this.owner.currentHealth));
        }
        else if (this.recordHP < this.owner.currentHealth)
        {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(this.owner, new DamageInfo(this.owner, this.owner.currentHealth - this.recordHP, DamageInfo.DamageType.HP_LOSS), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }

        AbstractDungeon.actionManager.addToBottom(new RemoveAllBlockAction(this.owner, this.owner));
        if (this.recordBlock > 0)
        {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this.owner, this.owner, this.recordBlock));
        }
    }
}
