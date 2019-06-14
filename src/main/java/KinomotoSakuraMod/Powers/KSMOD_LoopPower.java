package KinomotoSakuraMod.Powers;

import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class KSMOD_LoopPower extends KSMOD_CustomPower
{
    public static final String POWER_ID = "KSMOD_LoopPower";
    private static final String POWER_NAME;
    private static final String[] POWER_DESCRIPTIONS;
    private static final String POWER_IMG_PATH = "img/powers/default_power.png";
    private static final PowerType POWER_TYPE = PowerType.BUFF;

    static
    {
        PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        POWER_NAME = powerStrings.NAME;
        POWER_DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }

    public KSMOD_LoopPower(int amout)
    {
        this(AbstractDungeon.player, amout);
    }

    public KSMOD_LoopPower(AbstractCreature target, int amount)
    {
        super(POWER_ID, POWER_NAME, POWER_IMG_PATH, POWER_TYPE, target, amount);
        this.updateDescription();
    }

    public void updateDescription()
    {
        this.description = POWER_DESCRIPTIONS[0] + this.amount + POWER_DESCRIPTIONS[1] + this.amount + POWER_DESCRIPTIONS[2];
    }

    public void atStartOfTurn()
    {
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(this.owner, this.amount));
        AbstractDungeon.actionManager.addToBottom(new DiscardAction(this.owner, this.owner, this.amount, false));
    }
}
