package KinomotoSakuraMod.Relics;

import KinomotoSakuraMod.Cards.KSMOD_AbstractMagicCard;
import KinomotoSakuraMod.Cards.SpellCard.SpellCardTurn;
import KinomotoSakuraMod.Characters.KinomotoSakura;
import KinomotoSakuraMod.Patches.KSMOD_CustomCardColor;
import KinomotoSakuraMod.Utility.KSMOD_Utility;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MinionPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;

public class KSMOD_SealedWand extends CustomRelic
{
    public static final String RELIC_ID = "KSMOD_SealedWand";
    private static final String RELIC_IMG_PATH = "img/relics/icon/SealedWand.png";
    private static final String RELIC_IMG_OTL_PATH = "img/relics/outline/SealedWand.png";
    private static final RelicTier RELIC_TIER = RelicTier.STARTER;
    private static final LandingSound RELIC_SOUND = AbstractRelic.LandingSound.MAGICAL;
    private static int TRIGGER_NUMBER_INCREASE = 25;
    private static int triggerNumber = 30;

    public KSMOD_SealedWand()
    {
        super(RELIC_ID, ImageMaster.loadImage(RELIC_IMG_PATH), ImageMaster.loadImage(RELIC_IMG_OTL_PATH), RELIC_TIER, RELIC_SOUND);
        this.counter = 0;
    }

    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0] + GetGainNumber() + this.DESCRIPTIONS[1] + triggerNumber + this.DESCRIPTIONS[2] + triggerNumber + this.DESCRIPTIONS[3] + TRIGGER_NUMBER_INCREASE + this.DESCRIPTIONS[4];
    }

    public AbstractRelic makeCopy()
    {
        return new KSMOD_SealedWand();
    }

    public void atPreBattle()
    {
        if (AbstractDungeon.player instanceof KinomotoSakura)
        {
            CheckSakuraCardRepeat(AbstractDungeon.player.masterDeck.group);
            CheckSakuraCardRepeat(AbstractDungeon.player.drawPile.group);
            return;
        }
    }

    public void onMonsterDeath(AbstractMonster monster)
    {
        if (!monster.hasPower(MinionPower.POWER_ID))
        {
            GainCharge(GetGainNumber());
        }
    }

    public void GainCharge(int chargeNumber)
    {
        this.counter += chargeNumber;
        if (this.counter >= triggerNumber)
        {
            this.flash();
            ActiveRelic();
        }
    }

    private int GetGainNumber()
    {
        return 3;
    }

    private void ActiveRelic()
    {
        this.counter -= triggerNumber;
        setTriggerNumber(triggerNumber + TRIGGER_NUMBER_INCREASE);
        if (this.counter < 0)
        {
            this.counter = 0;
        }
        AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new SpellCardTurn()));
    }

    public void setTriggerNumber(int value)
    {
        triggerNumber = value;
    }

    private void CheckSakuraCardRepeat(ArrayList<AbstractCard> arrayList)
    {
        ArrayList<String> sakuraCardList = new ArrayList<>();
        ArrayList<Integer> cardsIndexList = new ArrayList<>();
        ArrayList<AbstractCard> cardsToRemove = new ArrayList<>();
        ArrayList<AbstractCard> cardsToAdd = new ArrayList<>();
        for (AbstractCard card : arrayList)
        {
            if (card.color == KSMOD_CustomCardColor.SAKURACARD_COLOR)
            {
                String cardClassName = card.getClass().getName();
                if (KSMOD_Utility.IsStringListContains(sakuraCardList, cardClassName))
                {
                    cardsToRemove.add(card);
                    cardsToAdd.add(((KSMOD_AbstractMagicCard) card).getSameNameClowCard());
                    cardsIndexList.add(arrayList.indexOf(card));
                }
                else
                {
                    sakuraCardList.add(cardClassName);
                }
            }
        }
        arrayList.removeAll(cardsToRemove);
        for (int i = 0; i < cardsToAdd.size(); i++)
        {
            arrayList.add(i ,cardsToAdd.get(i));
        }
    }
}
