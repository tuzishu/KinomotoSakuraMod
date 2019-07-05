package KinomotoSakuraMod.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public class KSMOD_MoveAction extends AbstractGameAction
{
    public static final String ACTION_ID = "KSMOD_MoveAction";
    private static final String[] TEXT;
    private static final float DURATION = Settings.ACTION_DUR_FASTER;
    private boolean isCardToHand;
    private CardGroup source;

    static
    {
        UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ACTION_ID);
        TEXT = uiStrings.TEXT;
    }

    public KSMOD_MoveAction(int amount, boolean isCardToHand)
    {
        this(amount, isCardToHand, false);
    }

    public KSMOD_MoveAction(int amount, boolean isCardToHand, boolean isCardFromDrawPile)
    {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = DURATION;
        this.amount = amount;
        this.isCardToHand = isCardToHand;
        if (isCardFromDrawPile)
        {
            this.source = AbstractDungeon.player.drawPile;
            this.isCardToHand = true;
        }
        else
        {
            this.source = AbstractDungeon.player.exhaustPile;
        }
    }

    public void update()
    {
        if (AbstractDungeon.getCurrRoom().isBattleEnding())
        {
            this.isDone = true;
        }
        else
        {
            if (this.duration == DURATION)
            {
                if (this.source.isEmpty())
                {
                    this.isDone = true;
                    return;
                }

                if (this.source.size() == this.amount)
                {
                    AbstractCard card = this.source.getTopCard();
                    this.source.removeCard(card);
                    if (isCardToHand)
                    {
                        this.source.moveToHand(card, this.source);
                    }
                    else
                    {
                        this.source.moveToDeck(card, true);
                    }
                }

                if (this.source.group.size() > this.amount)
                {
                    AbstractDungeon.gridSelectScreen.open(this.source, this.amount, TEXT[0], false, false, false, false);
                    this.tickDuration();
                    return;
                }
            }

            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty())
            {
                for (AbstractCard card : AbstractDungeon.gridSelectScreen.selectedCards)
                {
                    this.source.removeCard(card);
                    if (isCardToHand)
                    {
                        this.source.moveToHand(card, this.source);
                    }
                    else
                    {
                        this.source.moveToDeck(card, true);
                    }
                }
                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                AbstractDungeon.player.hand.refreshHandLayout();
            }

            this.tickDuration();
        }
    }
}
