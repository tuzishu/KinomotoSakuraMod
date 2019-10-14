package KinomotoSakuraMod.Actions;

import KinomotoSakuraMod.Cards.ClowCard.ClowCardTheCreate;
import KinomotoSakuraMod.Cards.ClowCard.ClowCardTheMirror;
import KinomotoSakuraMod.Cards.SakuraCard.SakuraCardTheCreate;
import KinomotoSakuraMod.Cards.SakuraCard.SakuraCardTheMirror;
import KinomotoSakuraMod.Patches.KSMOD_CustomCardColor;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;

public class KSMOD_MirrorAction extends AbstractGameAction
{
    public static final String ACTION_ID = "KSMOD_MirrorAction";
    private static final String[] TEXT;
    private static final float DURATION = Settings.ACTION_DUR_FAST;
    private static ArrayList<String> cardidBlackList = new ArrayList<>();
    private AbstractPlayer player;
    private ArrayList<AbstractCard> cannotDuplicateList = new ArrayList<AbstractCard>();

    static
    {
        UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ACTION_ID);
        TEXT = uiStrings.TEXT;
        cardidBlackList.add(ClowCardTheMirror.ID);
        cardidBlackList.add(SakuraCardTheMirror.ID);
        cardidBlackList.add(ClowCardTheCreate.ID);
        cardidBlackList.add(SakuraCardTheCreate.ID);
    }

    public KSMOD_MirrorAction(int amount)
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
                    AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(this.player.hand.getTopCard().makeStatEquivalentCopy()));
                }
                this.returnCards();
                this.isDone = true;
                return;
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
                AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(card.makeStatEquivalentCopy()));
                for (int j = 0; j < this.amount; ++j)
                {
                    AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(card.makeStatEquivalentCopy()));
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
        if (card.color != KSMOD_CustomCardColor.CLOWCARD_COLOR && card.color != KSMOD_CustomCardColor.SAKURACARD_COLOR)
        {
            return false;
        }
        for (String cardid : cardidBlackList)
        {
            if (card.cardID.equals(cardid))
            {
                return false;
            }
        }
        return true;
    }
}
