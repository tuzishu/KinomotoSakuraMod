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
    private static final int START_COUNT = 0;
    private static final int UPDATE_TRIGGER_NUMBER = 25;
    private static final int BASE_TRIGGER_NUMBER = 50;
    private boolean isCardFromWand = false;

    public KSMOD_SealedWand()
    {
        super(RELIC_ID, ImageMaster.loadImage(RELIC_IMG_PATH), ImageMaster.loadImage(RELIC_IMG_OTL_PATH), RELIC_TIER, RELIC_SOUND);
        this.counter = START_COUNT;
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0] + GetGainNumber() + DESCRIPTIONS[1] + BASE_TRIGGER_NUMBER + DESCRIPTIONS[2] + BASE_TRIGGER_NUMBER + DESCRIPTIONS[3] + UPDATE_TRIGGER_NUMBER + DESCRIPTIONS[4] + GetTriggerNumber() + DESCRIPTIONS[5];
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

    public void atTurnStart()
    {
        if (this.counter >= GetTriggerNumber() && !AbstractDungeon.getCurrRoom().isBattleEnding() && !AbstractDungeon.getMonsters().areMonstersBasicallyDead())
        {
            this.flash();
            ActiveRelic();
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
    }

    private int GetGainNumber()
    {
        return 3;
    }

    private void ActiveRelic()
    {
        this.counter -= GetTriggerNumber();
        if (this.counter < 0)
        {
            this.counter = 0;
        }
        this.isCardFromWand = true;
        AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new SpellCardTurn()));
        this.getUpdatedDescription();
    }

    public void GetTurnCardOver()
    {
        this.isCardFromWand = false;
    }

    public boolean IsCardFromWand()
    {
        return isCardFromWand;
    }

    public int GetTriggerNumber()
    {
        int sakuraCardAmount = 0;
        if (AbstractDungeon.player != null && AbstractDungeon.player instanceof KinomotoSakura)
        {
            for (AbstractCard card : AbstractDungeon.player.masterDeck.group)
            {
                if (card.cardID.contains("SakuraCardThe"))
                {
                    sakuraCardAmount += 1;
                }
            }
        }
        return BASE_TRIGGER_NUMBER + UPDATE_TRIGGER_NUMBER * sakuraCardAmount;
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
            arrayList.add(i, cardsToAdd.get(i));
        }
    }
}
