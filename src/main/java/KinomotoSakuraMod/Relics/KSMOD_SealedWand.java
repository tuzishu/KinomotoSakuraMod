package KinomotoSakuraMod.Relics;

import KinomotoSakuraMod.Cards.KSMOD_AbstractMagicCard;
import KinomotoSakuraMod.Cards.SpellCard.SpellCardTurn;
import KinomotoSakuraMod.Characters.KinomotoSakura;
import KinomotoSakuraMod.Patches.KSMOD_CustomCardColor;
import KinomotoSakuraMod.Utility.KSMOD_Utility;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.util.ArrayList;

public class KSMOD_SealedWand extends CustomRelic
{
    public static final String RELIC_ID = "KSMOD_SealedWand";
    private static final String RELIC_IMG_PATH = "img/relics/icon/SealedWand.png";
    private static final String RELIC_IMG_OTL_PATH = "img/relics/outline/SealedWand.png";
    private static final RelicTier RELIC_TIER = RelicTier.STARTER;
    private static final LandingSound RELIC_SOUND = AbstractRelic.LandingSound.MAGICAL;
    private static int TriggerNumber = 100;

    public KSMOD_SealedWand()
    {
        super(RELIC_ID, ImageMaster.loadImage(RELIC_IMG_PATH), ImageMaster.loadImage(RELIC_IMG_OTL_PATH), RELIC_TIER, RELIC_SOUND);
        this.counter = 0;
    }

    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy()
    {
        return new KSMOD_SealedWand();
    }

    public void onUseCard(AbstractCard card, UseCardAction action)
    {
        if (card instanceof KSMOD_AbstractMagicCard)
        {
            ++this.counter;
            this.flash();
            if (this.counter >= TriggerNumber)
            {
                active();
            }
        }
    }

    private void active()
    {
        this.counter -= TriggerNumber;
        if (this.counter < 0)
        {
            this.counter = 0;
        }
        AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, 4));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new SpellCardTurn()));
    }

    public void setTriggerNumber(int value)
    {
        TriggerNumber = value;
    }

    public void atBattleStart()
    {
        KSMOD_Utility.Logger.info("receivePostBattle");
        if (!(AbstractDungeon.player instanceof KinomotoSakura))
        {
            return;
        }
        ArrayList<String> sakuraCardList = new ArrayList<>();
        ArrayList<AbstractCard> cardsToRemove = new ArrayList<>();
        ArrayList<AbstractCard> cardsToAdd = new ArrayList<>();
        for (AbstractCard card : AbstractDungeon.player.masterDeck.group)
        {
            if (card.color == KSMOD_CustomCardColor.SAKURACARD_COLOR)
            {
                String cardClassName = card.getClass().getName();
                if (isStringListContains(sakuraCardList, cardClassName))
                {
                    cardsToRemove.add(card);
                    cardsToAdd.add(((KSMOD_AbstractMagicCard)card).getSameNameClowCard());
                }
                else
                {
                    sakuraCardList.add(cardClassName);
                }
            }
        }
        for (AbstractCard card: cardsToRemove)
        {
            KSMOD_Utility.Logger.info("remove : "+card.name);
        }
        for (AbstractCard card: cardsToAdd)
        {
            KSMOD_Utility.Logger.info("add : "+card.name);
        }
        AbstractDungeon.player.masterDeck.group.removeAll(cardsToRemove);
        AbstractDungeon.player.masterDeck.group.addAll(cardsToAdd);
    }

    private boolean isStringListContains(ArrayList<String> stringList, String targetStr)
    {
        for (String cardClassName : stringList)
        {
            if (cardClassName.equals(targetStr))
            {
                KSMOD_Utility.Logger.info("cardClassName equals targetStr : "+targetStr);
                return true;
            }
        }
        KSMOD_Utility.Logger.info("false : "+targetStr);
        return false;
    }
}
