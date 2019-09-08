package KinomotoSakuraMod.Actions;

import KinomotoSakuraMod.Cards.KSMOD_AbstractMagicCard;
import KinomotoSakuraMod.Patches.KSMOD_CustomCardColor;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import java.util.ArrayList;

public class KSMOD_ReleaseAction extends AbstractGameAction
{
    private static final String ACTION_ID = "KSMOD_ReleaseAction";
    private static final String[] TEXT;
    private AbstractPlayer player;
    private static final float DURATION = Settings.ACTION_DUR_XFAST;
    private int vulnAmount;
    private float releaseRate;
    private ArrayList<AbstractCard> cannotReleaseList = new ArrayList<AbstractCard>();

    static
    {
        UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ACTION_ID);
        TEXT = uiStrings.TEXT;
    }

    public KSMOD_ReleaseAction(int vulnAmount, float rate)
    {
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.player = AbstractDungeon.player;
        this.duration = DURATION;
        this.vulnAmount = vulnAmount;
        this.releaseRate = rate;
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
                    this.cannotReleaseList.add(card);
                }
            }
            if (this.cannotReleaseList.size() == this.player.hand.group.size())
            {
                this.isDone = true;
                return;
            }

            this.player.hand.group.removeAll(this.cannotReleaseList);
            if (this.player.hand.group.size() == 1)
            {
                AbstractCard card = AbstractDungeon.player.hand.getTopCard();
                AbstractDungeon.player.hand.removeCard(card);
                ReleaseCard(card);
                this.returnCards();
                this.isDone = true;
                for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters)
                {
                    AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(monster, this.player, new VulnerablePower(monster, this.vulnAmount, false)));
                }
                this.tickDuration();
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
            if (AbstractDungeon.handCardSelectScreen.selectedCards.size() > 0)
            {
                for (AbstractCard card : AbstractDungeon.handCardSelectScreen.selectedCards.group)
                {
                    ReleaseCard(card);
                    returnCards();
                    for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters)
                    {
                        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(monster, this.player, new VulnerablePower(monster, this.vulnAmount, false)));
                    }
                }
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }
        tickDuration();
    }

    private void returnCards()
    {

        for (AbstractCard card : this.cannotReleaseList)
        {
            this.player.hand.addToTop(card);
        }
        this.player.hand.refreshHandLayout();
    }

    private boolean IsCorrectCardType(AbstractCard card)
    {
        return card.color == KSMOD_CustomCardColor.CLOWCARD_COLOR || card.color == KSMOD_CustomCardColor.SAKURACARD_COLOR;
    }

    private void ReleaseCard(AbstractCard card)
    {
        ((KSMOD_AbstractMagicCard) card).unreleasedDesc = card.rawDescription;
        ((KSMOD_AbstractMagicCard) card).originExhaust = card.exhaust;
        ((KSMOD_AbstractMagicCard) card).originEthereal = card.isEthereal;
        if (card.costForTurn > 0)
        {
            card.setCostForTurn(0);
            card.superFlash(Color.GOLD.cpy());
        }
        if (card.type == AbstractCard.CardType.POWER)
        {
            card.rawDescription = reloadReleasedCardDescription(card.rawDescription, !card.isEthereal, !card.exhaust);
            card.initializeDescription();
            card.isEthereal = true;
        }
        else
        {
            ((KSMOD_AbstractMagicCard) card).release(releaseRate);
            card.rawDescription = reloadReleasedCardDescription(card.rawDescription, !card.isEthereal, !card.exhaust);
            card.initializeDescription();
            card.isEthereal = true;
            card.exhaust = true;
        }
        AbstractDungeon.player.hand.addToTop(card);
    }

    public static String reloadReleasedCardDescription(String desc, boolean isAddEthereal, boolean isAddExhaust)
    {
        if (!desc.contains(TEXT[6] + TEXT[7] + TEXT[8]))
        {
            String cardHeadStr = "";
            for (int i = 1; i <= 5; i++)
            {
                if (desc.contains(TEXT[i]))
                {
                    cardHeadStr = cardHeadStr + TEXT[i];
                    desc = desc.replace(cardHeadStr, "");
                }
            }

            cardHeadStr = cardHeadStr + TEXT[6];
            if (isAddEthereal)
            {
                cardHeadStr = cardHeadStr + TEXT[7];
            }
            if (isAddExhaust)
            {
                cardHeadStr = cardHeadStr + TEXT[8];
            }
            desc = cardHeadStr + desc;
        }
        return desc;
    }
}
