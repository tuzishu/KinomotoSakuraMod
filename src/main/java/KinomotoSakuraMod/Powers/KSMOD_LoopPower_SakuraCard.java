package KinomotoSakuraMod.Powers;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class KSMOD_LoopPower_SakuraCard extends KSMOD_CustomPower
{
    public static final String POWER_ID = "KSMOD_LoopPower_SakuraCard";
    private static final String POWER_NAME;
    private static final String[] POWER_DESCRIPTIONS;
    private static final String POWER_IMG_PATH = "img/powers/loop_power_sakuracard.png";
    private static final PowerType POWER_TYPE = PowerType.BUFF;

    static
    {
        PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        POWER_NAME = powerStrings.NAME;
        POWER_DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }

    public KSMOD_LoopPower_SakuraCard(int amount)
    {
        this(AbstractDungeon.player, amount);
    }

    public KSMOD_LoopPower_SakuraCard(AbstractCreature target, int amount)
    {
        super(POWER_ID, POWER_NAME, POWER_IMG_PATH, POWER_TYPE, target, amount, true);
        this.updateDescription();
    }

    public void updateDescription()
    {
        this.description = POWER_DESCRIPTIONS[0] + this.amount + POWER_DESCRIPTIONS[1];
    }

    public void onManualDiscard()
    {
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(this.owner, this.amount));
    }
}
