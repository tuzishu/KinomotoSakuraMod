package KinomotoSakuraMod.Powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.EntanglePower;

public class SilentPower extends CustomPower
{
    public static final String POWER_ID = "SilentPower";
    private static final String POWER_NAME;
    private static final String[] POWER_DESCRIPTIONS;
    private static final String POWER_IMG = "img/powers/default_power.png";
    private static final PowerType POWER_TYPE = PowerType.DEBUFF;

    static
    {
        PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        POWER_NAME = powerStrings.NAME;
        POWER_DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }

    public SilentPower(AbstractCreature target)
    {
        super(POWER_ID, POWER_NAME, POWER_IMG, POWER_TYPE, target, 1);
        updateDescription();
    }

    public void updateDescription()
    {
        this.description = POWER_DESCRIPTIONS[0];
    }

    public void atStartOfTurn()
    {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new EntanglePower(this.owner)));
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
    }
}
