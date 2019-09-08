package KinomotoSakuraMod.Actions;

import KinomotoSakuraMod.Cards.SpellCard.SpellCardTurn;
import KinomotoSakuraMod.Characters.KinomotoSakura;
import KinomotoSakuraMod.Patches.KSMOD_CustomCardColor;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;

import java.util.ArrayList;

public class KSMOD_TurnAction extends AbstractGameAction
{
    public static final String ACTION_ID = "KSMOD_TurnAction";
    private static final String[] TEXT;
    private static final float DURATION = Settings.ACTION_DUR_FAST;
    private AbstractPlayer player;
    private ArrayList<AbstractCard> cannotDuplicateList = new ArrayList<AbstractCard>();

    static
    {
        UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ACTION_ID);
        TEXT = uiStrings.TEXT;
    }

    public KSMOD_TurnAction()
    {
        this(1);
    }

    public KSMOD_TurnAction(int amount)
    {
        this.setValues(AbstractDungeon.player, AbstractDungeon.player, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = DURATION;
        this.player = AbstractDungeon.player;
        this.amount = amount;
    }

    public void update()
    {
        if (AbstractDungeon.getCurrRoom().isBattleEnding())
        {
            this.isDone = true;
            return;
        }
        if (this.duration == DURATION)
        {
            for (AbstractCard card : this.player.hand.group)
            {
                if (!this.IsCorrectCardType(card))
                {
                    this.cannotDuplicateList.add(card);
                }
            }
            if (this.cannotDuplicateList.size() == this.player.hand.group.size())
            {
                this.isDone = true;
                return;
            }

            this.player.hand.group.removeAll(this.cannotDuplicateList);
            if (this.player.hand.group.size() == 1)
            {
                for (int i = 0; i < this.amount; ++i)
                {
                    AbstractCard card = AbstractDungeon.player.hand.getTopCard();
                    AbstractDungeon.player.hand.removeCard(card);
                    TurnClowCardToSakuraCard(card);
                }
                this.returnCards();
                this.isDone = true;
            }

            if (this.player.hand.group.size() > 1)
            {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false, false);
                this.tickDuration();
                return;
            }
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved)
        {
            for (AbstractCard card : AbstractDungeon.handCardSelectScreen.selectedCards.group)
            {
                for (int j = 0; j < this.amount; ++j)
                {
                    TurnClowCardToSakuraCard(card);
                }
            }
            this.returnCards();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone = true;
        }
        this.tickDuration();
    }

    private void returnCards()
    {
        for (AbstractCard card : this.cannotDuplicateList)
        {
            this.player.hand.addToTop(card);
        }
        this.player.hand.refreshHandLayout();
    }

    private boolean IsCorrectCardType(AbstractCard card)
    {
        if (card.color == KSMOD_CustomCardColor.CLOWCARD_COLOR)
        {
            String cardID = card.cardID;
            if (cardID.contains("ClowCardThe"))
            {
                cardID = cardID.replaceAll("ClowCardThe", "SakuraCardThe");
            }
            if (!hasTargetSakuraCardInMasterDeck(cardID))
            {
                return true;
            }
        }
        return false;
    }

    private void TurnClowCardToSakuraCard(AbstractCard clowCard)
    {
        AbstractCard sakuraCard = null;
        try
        {
            Class obj = Class.forName(clowCard.getClass().getName().replaceAll("Clow", "Sakura"));
            sakuraCard = (AbstractCard) obj.newInstance();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            AbstractDungeon.effectList.add(new ThoughtBubble(player.dialogX, player.dialogY, 3.0F, KinomotoSakura.GetMessage(0), true));
            sakuraCard = clowCard;
            AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(new SpellCardTurn()));
        }
        RemoveTargetClowCardFromMasterDeck(clowCard);
        AbstractDungeon.player.masterDeck.addToBottom(sakuraCard);
        AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(sakuraCard));
    }

    private void RemoveTargetClowCardFromMasterDeck(AbstractCard clowCard)
    {
        for (AbstractCard card : AbstractDungeon.player.masterDeck.group)
        {
            if (card.uuid == clowCard.uuid)
            {
                AbstractDungeon.player.masterDeck.removeCard(card);
                break;
            }
        }
    }

    private boolean hasTargetSakuraCardInMasterDeck(String cardID)
    {
        for (AbstractCard card : AbstractDungeon.player.masterDeck.group)
        {
            if (card.cardID.equals(cardID))
            {
                return true;
            }
        }
        return false;
    }
}
