package KinomotoSakuraMod.Powers;

import KinomotoSakuraMod.Actions.LoopAction;
import KinomotoSakuraMod.Cards.AbstractMagicCard;
import KinomotoSakuraMod.Cards.AbstractSpellCard;
import KinomotoSakuraMod.Patches.CustomTag;
import com.badlogic.gdx.scenes.scene2d.actions.AfterAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class LoopPower extends CustomPower
{
    public static final String POWER_ID = "LoopPower";
    private static final String POWER_NAME;
    private static final String[] POWER_DESCRIPTIONS;
    private static final String POWER_IMG_PATH = "img/powers/default_power.png";
    private static final PowerType POWER_TYPE = PowerType.BUFF;
    private int storedAmount;

    static
    {
        PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        POWER_NAME = powerStrings.NAME;
        POWER_DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }

    public LoopPower(int amout)
    {
        this(AbstractDungeon.player, amout);
    }

    public LoopPower(AbstractCreature target, int amount)
    {
        super(POWER_ID, POWER_NAME, POWER_IMG_PATH, POWER_TYPE, target, amount);
        this.storedAmount = this.amount;
        this.updateDescription();
    }

    public void updateDescription()
    {
        this.description = POWER_DESCRIPTIONS[0] + this.amount + POWER_DESCRIPTIONS[1] + this.storedAmount + POWER_DESCRIPTIONS[2];
    }

    public void atStartOfTurn()
    {
        this.storedAmount = this.amount;
        this.updateDescription();
    }

    public void onAfterCardPlayed(AbstractCard usedCard)
    {
        if (this.storedAmount > 0 && (usedCard instanceof AbstractMagicCard || usedCard instanceof AbstractSpellCard) && usedCard.type != AbstractCard.CardType.POWER && !usedCard.exhaust)
        {
            AbstractDungeon.actionManager.addToBottom(new LoopAction(usedCard));
            this.storedAmount -= 1;
            this.updateDescription();
        }
    }
}
