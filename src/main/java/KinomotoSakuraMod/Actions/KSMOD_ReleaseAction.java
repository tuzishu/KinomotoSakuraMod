package KinomotoSakuraMod.Actions;

import KinomotoSakuraMod.Cards.KSMOD_AbstractMagicCard;
import KinomotoSakuraMod.Patches.KSMOD_CustomCardColor;
import KinomotoSakuraMod.Utility.KSMOD_Utility;
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

import java.util.ArrayList;

public class KSMOD_ReleaseAction extends AbstractGameAction
{
    private static final String ACTION_ID = "KSMOD_ReleaseAction";
    private static final String[] TEXT;
    private AbstractPlayer player;
    private static final float DURATION = Settings.ACTION_DUR_XFAST;
    private float releaseRate;
    private int damage;
    private ArrayList<AbstractCard> cannotReleaseList = new ArrayList<AbstractCard>();

    static
    {
        UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ACTION_ID);
        TEXT = uiStrings.TEXT;
    }

    public KSMOD_ReleaseAction(int damage, float rate)
    {
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.player = AbstractDungeon.player;
        this.damage = damage;
        this.releaseRate = rate;
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
                ReleaseCard(this.player.hand.getTopCard());
                this.returnCards();
                this.isDone = true;
                AbstractDungeon.actionManager.addToTop(new DamageAllEnemiesAction(player, KSMOD_Utility.GetDamageList(this.damage), DamageInfo.DamageType.HP_LOSS, AttackEffect.FIRE));
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
                    AbstractDungeon.actionManager.addToTop(new DamageAllEnemiesAction(player, KSMOD_Utility.GetDamageList(this.damage), DamageInfo.DamageType.HP_LOSS, AttackEffect.FIRE));
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
        if (!desc.contains(TEXT[5] + TEXT[6] + TEXT[7]))
        {
            String cardHeadStr = "";
            if (desc.contains(TEXT[1]))
            {
                cardHeadStr = TEXT[1];
                desc = desc.replace(cardHeadStr, "");
            }
            else if (desc.contains(TEXT[2]))
            {
                cardHeadStr = TEXT[2];
                desc = desc.replace(cardHeadStr, "");
            }
            else if (desc.contains(TEXT[3]))
            {
                cardHeadStr = TEXT[3];
                desc = desc.replace(cardHeadStr, "");
            }
            else if (desc.contains(TEXT[4]))
            {
                cardHeadStr = TEXT[4];
                desc = desc.replace(cardHeadStr, "");
            }
            cardHeadStr = cardHeadStr + TEXT[5];
            if (isAddEthereal)
            {
                cardHeadStr = cardHeadStr + TEXT[6];
            }
            if (isAddExhaust)
            {
                cardHeadStr = cardHeadStr + TEXT[7];
            }
            desc = cardHeadStr + desc;
        }
        return desc;
    }
}
