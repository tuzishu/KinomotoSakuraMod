package KinomotoSakuraMod.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
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
    private CardGroup cardGroup;
    private boolean isCardFromDrawPile;

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
        this.isCardFromDrawPile = isCardFromDrawPile;
        if (isCardFromDrawPile)
        {
            this.cardGroup = AbstractDungeon.player.drawPile;
            this.isCardToHand = true;
        }
        else
        {
            this.cardGroup = AbstractDungeon.player.exhaustPile;
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
                if (this.cardGroup.isEmpty())
                {
                    this.isDone = true;
                    return;
                }

                if (this.cardGroup.size() == this.amount)
                {
                    AbstractCard card = this.cardGroup.getTopCard();
                    MoveCard(card, this.isCardToHand, this.cardGroup);
                }

                if (this.cardGroup.group.size() > this.amount)
                {
                    AbstractDungeon.gridSelectScreen.open(this.cardGroup, this.amount, TEXT[0], false, false, false, false);
                    this.tickDuration();
                    return;
                }
            }

            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty())
            {
                for (AbstractCard card : AbstractDungeon.gridSelectScreen.selectedCards)
                {
                    MoveCard(card, this.isCardToHand, this.cardGroup);
                }
                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                AbstractDungeon.player.hand.refreshHandLayout();
            }

            this.tickDuration();
        }
    }

    private void MoveCard(AbstractCard card, boolean isCardToHand, CardGroup group)
    {
        group.removeCard(card);
        if (isCardToHand)
        {
            group.moveToHand(card, group);
        }
        else
        {
            group.moveToDeck(card, true);
        }
        if (!this.isCardFromDrawPile)
        {
            card.unhover();
            card.fadingOut = false;
            card.unfadeOut();
        }
    }
}
