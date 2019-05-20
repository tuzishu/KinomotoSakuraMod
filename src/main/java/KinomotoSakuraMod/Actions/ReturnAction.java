package KinomotoSakuraMod.Actions;

import KinomotoSakuraMod.Cards.AbstractMagicCard;
import KinomotoSakuraMod.Cards.AbstractSpellCard;
import KinomotoSakuraMod.Utility.KSMOD_Utility;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public class ReturnAction extends AbstractGameAction
{
    private static final String ACTION_ID = "ReturnAction";
    private static final String[] TEXT;
    private static final float DURATION = Settings.ACTION_DUR_XFAST;
    private AbstractPlayer player;
    boolean canDraw = false;

    static
    {
        UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ACTION_ID);
        TEXT = uiStrings.TEXT;
    }

    public ReturnAction(int amount)
    {
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.player = AbstractDungeon.player;
        this.amount = amount;
        this.duration = DURATION;
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
            CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            int count = 0;
            for (AbstractCard card : this.player.exhaustPile.group)
            {
                if (card instanceof AbstractMagicCard || card instanceof AbstractSpellCard)
                {
                    count += 1;
                    group.group.add(card);
                }
            }
            KSMOD_Utility.Logger.info(count);
            if (count == 0)
            {
                this.isDone = true;
            }
            else
            {
                AbstractDungeon.gridSelectScreen.open(group, this.amount,true, TEXT[0]+this.amount+TEXT[1]);
            }
            canDraw = true;
            tickDuration();
            return;
        }

        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty())
        {
            for (AbstractCard card : AbstractDungeon.gridSelectScreen.selectedCards)
            {
                this.player.exhaustPile.removeCard(card);
                this.player.exhaustPile.moveToDeck(card, true);
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            AbstractDungeon.player.hand.refreshHandLayout();
        }

        if (canDraw)
        {
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(player, 1));
            canDraw = false;
        }
        tickDuration();
    }
}
