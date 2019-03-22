package KinomotoSakuraMod.Actions;

import KinomotoSakuraMod.Cards.AbstrackSakuraCard;
import KinomotoSakuraMod.Cards.AbstractClowCard;
import KinomotoSakuraMod.Utility.ModLogger;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public class ReleaseAction extends AbstractGameAction
{
    private static final String ACTION_ID = "ReleaseAction";
    private static final String[] TEXT;
    private AbstractPlayer player;
    private static final float DURATION = Settings.ACTION_DUR_XFAST;
    private static final float RELEASE_UPGRADE_RATE = 0.5F;
    private int damage;

    static
    {
        UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ACTION_ID);
        TEXT = uiStrings.TEXT;
    }

    public ReleaseAction(int damage)
    {
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.player = AbstractDungeon.player;
        this.damage = damage;
        this.duration = DURATION;
    }

    public void update()
    {
        if (this.duration == Settings.ACTION_DUR_XFAST)
        {
            AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false, true, false, false, true);
            tickDuration();
            return;
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved)
        {
            if (AbstractDungeon.handCardSelectScreen.selectedCards.size() > 0)
            {
                for (AbstractCard card : AbstractDungeon.handCardSelectScreen.selectedCards.group)
                {
                    tryReleaseCard(card);
                }
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }
        tickDuration();
    }

    private void tryReleaseCard(AbstractCard card)
    {
        if (card instanceof AbstractClowCard)
        {
            ModLogger.logger.info("card : "+card.name+" is instance of AbstractClowCard");
            if (card.costForTurn > 0)
            {
                card.cost = 0;
                card.costForTurn = 0;
                card.isCostModified = true;
                card.superFlash(Color.GOLD.cpy());
            }
            if (card.type != AbstractCard.CardType.POWER && !card.exhaust && !card.exhaustOnFire && !card.exhaustOnUseOnce)
            {
                card.isEthereal = true;
                card.exhaust = true;
                ((AbstractClowCard) card).release(RELEASE_UPGRADE_RATE);
            }
            reloadCardDescription(card);
        }
        else if (card instanceof AbstrackSakuraCard)
        {
            ModLogger.logger.info("card : "+card.name+" is instance of AbstrackSakuraCard");
            if (card.costForTurn > 0)
            {
                card.cost = 0;
                card.costForTurn = 0;
                card.isCostModified = true;
                card.superFlash(Color.GOLD.cpy());
            }
            if (card.type != AbstractCard.CardType.POWER && !card.exhaust && !card.exhaustOnFire && !card.exhaustOnUseOnce)
            {
                card.isEthereal = true;
                card.exhaust = true;
                ((AbstrackSakuraCard) card).release(RELEASE_UPGRADE_RATE);
            }
            reloadCardDescription(card);
        }
        AbstractDungeon.player.hand.addToTop(card);
    }

    private void reloadCardDescription(AbstractCard card)
    {
        card.rawDescription = " 消耗 ， 保留 。" + card.rawDescription;
        card.initializeDescription();
    }
}
