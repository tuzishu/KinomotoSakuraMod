package KinomotoSakuraMod.Actions;

import KinomotoSakuraMod.Patches.CustomTag;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public class ChangeCardAction extends AbstractGameAction
{
    private static final String ACTION_ID = "ChangeCardAction";
    private static final String[] TEXT;
    private static final float DURATION = Settings.ACTION_DUR_XFAST;
    private AbstractPlayer player;

    static
    {
        UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ACTION_ID);
        TEXT = uiStrings.TEXT;
    }

    public ChangeCardAction(int amount)
    {
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.player = AbstractDungeon.player;
        this.amount = amount;
    }

    public void update()
    {
        if (this.duration == Settings.ACTION_DUR_FAST)
        {
            if (this.player.hand.isEmpty())
            {
                this.isDone = true;
                return;
            }
            AbstractDungeon.handCardSelectScreen.open(TEXT[0], this.amount, true, true);
            tickDuration();
            return;
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved)
        {
            int count = AbstractDungeon.handCardSelectScreen.selectedCards.size();
            for (AbstractCard card : AbstractDungeon.handCardSelectScreen.selectedCards.group)
            {
                this.player.hand.moveToDiscardPile(card);
                card.triggerOnManualDiscard();
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            if (count > 0) {
                AbstractDungeon.actionManager.addToBottom(new DrawCardAction(player, count));
            }
        }

        tickDuration();
    }
}
