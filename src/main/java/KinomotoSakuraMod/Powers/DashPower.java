package KinomotoSakuraMod.Powers;

import KinomotoSakuraMod.Cards.AbstrackSakuraCard;
import KinomotoSakuraMod.Cards.AbstractClowCard;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class DashPower extends CustomPower
{
    public static final String POWER_ID = "DashPower";
    private static final String POWER_NAME;
    private static final String[] POWER_DESCRIPTIONS;
    private static final String POWER_IMG_PATH = "img/powers/default_power.png";
    private static final PowerType POWER_TYPE = PowerType.BUFF;
    private static int TRIGGER_NUMBER = 5;
    private int counter;

    static
    {
        PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        POWER_NAME = powerStrings.NAME;
        POWER_DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }

    public DashPower(AbstractCreature target, int amount)
    {
        super(POWER_ID, POWER_NAME, POWER_IMG_PATH, POWER_TYPE, target, amount);
        this.updateDescription();
        this.counter = 0;
    }

    public void updateDescription()
    {
        this.description = POWER_DESCRIPTIONS[0] + this.amount + POWER_DESCRIPTIONS[1] + (TRIGGER_NUMBER - this.counter) + POWER_DESCRIPTIONS[2];
    }

    public void onUseCard(AbstractCard card, UseCardAction action)
    {
        if (card instanceof AbstractClowCard || card instanceof AbstrackSakuraCard)
        {
            counter += 1;
            if (counter >= TRIGGER_NUMBER)
            {
                AbstractDungeon.actionManager.addToBottom(new DrawCardAction(this.owner, this.amount));
                counter -= TRIGGER_NUMBER;
            }
            updateDescription();
        }
    }
}
