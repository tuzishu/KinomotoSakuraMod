package KinomotoSakuraMod.Actions;

import KinomotoSakuraMod.Cards.AbstrackSakuraCard;
import KinomotoSakuraMod.Cards.AbstractClowCard;
import KinomotoSakuraMod.Cards.SpellCard.SpellCardRelease;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
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
                    AbstractDungeon.actionManager.addToTop(new DamageAllEnemiesAction(player, new int[] {this.damage}, DamageInfo.DamageType.HP_LOSS, AttackEffect.FIRE));
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
            if (card.costForTurn > 0)
            {
                card.cost = 0;
                card.costForTurn = 0;
                card.isCostModified = true;
                card.superFlash(Color.GOLD.cpy());
            }
            if (card.type == AbstractCard.CardType.POWER)
            {
                reloadCardDescription(card, !card.isEthereal, !card.exhaust);
                card.isEthereal = true;
            }
            else
            {
                ((AbstractClowCard) card).release(RELEASE_UPGRADE_RATE);
                reloadCardDescription(card, !card.isEthereal, !card.exhaust);
                card.isEthereal = true;
                card.exhaust = true;
            }
        }
        else if (card instanceof AbstrackSakuraCard)
        {
            if (card.costForTurn > 0)
            {
                card.cost = 0;
                card.costForTurn = 0;
                card.isCostModified = true;
                card.superFlash(Color.GOLD.cpy());
            }
            if (card.type == AbstractCard.CardType.POWER)
            {
                reloadCardDescription(card, !card.isEthereal, !card.exhaust);
                card.isEthereal = true;
            }
            else
            {
                ((AbstrackSakuraCard) card).release(RELEASE_UPGRADE_RATE);
                reloadCardDescription(card, !card.isEthereal, !card.exhaust);
                card.isEthereal = true;
                card.exhaust = true;
            }
        }
        AbstractDungeon.player.hand.addToTop(card);
    }

    private void reloadCardDescription(AbstractCard card, boolean isAddEthereal, boolean isAddExhaust)
    {
        boolean isChanged = false;
        if (isAddEthereal)
        {
            card.rawDescription = SpellCardRelease.EXTENDED_DESCRIPTION[0] + card.rawDescription;
            isChanged = true;
        }
        if (isAddExhaust)
        {
            card.rawDescription = SpellCardRelease.EXTENDED_DESCRIPTION[1] + card.rawDescription;
            isChanged = true;
        }
        if (isChanged)
        {
            card.initializeDescription();
        }
    }
}
