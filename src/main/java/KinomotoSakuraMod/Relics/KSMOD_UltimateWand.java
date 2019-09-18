package KinomotoSakuraMod.Relics;

import KinomotoSakuraMod.Cards.KSMOD_AbstractMagicCard;
import KinomotoSakuraMod.Cards.SpellCard.SpellCardTurn;
import KinomotoSakuraMod.Characters.KinomotoSakura;
import KinomotoSakuraMod.Patches.KSMOD_CustomCardColor;
import KinomotoSakuraMod.Patches.KSMOD_CustomTag;
import KinomotoSakuraMod.Powers.KSMOD_CerberusMarkPower;
import KinomotoSakuraMod.Powers.KSMOD_MagickChargePower;
import KinomotoSakuraMod.Utility.KSMOD_Utility;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.common.UpgradeRandomCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MinionPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;

public class KSMOD_UltimateWand extends CustomRelic
{
    public static final String RELIC_ID = "KSMOD_UltimateWand";
    private static final String RELIC_IMG_PATH = "img/relics/icon/ultimate_wand.png";
    private static final String RELIC_IMG_OTL_PATH = "img/relics/outline/ultimate_wand.png";
    private static final RelicTier RELIC_TIER = RelicTier.SPECIAL;
    private static final LandingSound RELIC_SOUND = AbstractRelic.LandingSound.MAGICAL;
    private static final int START_COUNT = 0;
    private static final int UPDATE_TRIGGER_NUMBER = 30;
    private static final int BASE_TRIGGER_NUMBER = 40;
    private static final int GAIN_NUMBER = 5;
    private static final int MAGICK_CHARGE_TRIGGER_NUMBER = 2;
    private static final int MAGICK_CHARGE_NUMBER = 1;
    private static final int MARKING_NUMBER = 1;
    private static final float DAMAGE_PROMOTION = 0.5F;
    private static final int UPGRADE_NUMBER = 2;
    private int elementCounter;

    public KSMOD_UltimateWand()
    {
        super(RELIC_ID, ImageMaster.loadImage(RELIC_IMG_PATH), ImageMaster.loadImage(RELIC_IMG_OTL_PATH), RELIC_TIER, RELIC_SOUND);
        this.counter = START_COUNT;
        this.elementCounter = 0;
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0] + MAGICK_CHARGE_TRIGGER_NUMBER + DESCRIPTIONS[1] + MAGICK_CHARGE_NUMBER + DESCRIPTIONS[2] + UPGRADE_NUMBER + DESCRIPTIONS[3] + MARKING_NUMBER + DESCRIPTIONS[4] + (int) (DAMAGE_PROMOTION * 100) + DESCRIPTIONS[5] + GAIN_NUMBER + DESCRIPTIONS[6] + GetTriggerNumber() + DESCRIPTIONS[7] + GetTriggerNumber() + DESCRIPTIONS[8] + UPDATE_TRIGGER_NUMBER + DESCRIPTIONS[9];
    }

    public boolean canSpawn()
    {
        return false;
    }

    public AbstractRelic makeCopy()
    {
        return new KSMOD_UltimateWand();
    }

    // public void obtain()
    // {
    //     AbstractRelic oldWand = AbstractDungeon.player.getRelic(KSMOD_StarWand.RELIC_ID);
    //     this.counter = oldWand.counter;
    //     int targetIndex = AbstractDungeon.player.relics.indexOf(oldWand);
    //     if (AbstractDungeon.player.hasRelic(KSMOD_StarWand.RELIC_ID))
    //     {
    //         this.instantObtain(AbstractDungeon.player, targetIndex, false);
    //     }
    //     else
    //     {
    //         super.obtain();
    //     }
    // }

    public void atPreBattle()
    {
        if (AbstractDungeon.player instanceof KinomotoSakura)
        {
            CheckSakuraCardRepeat(AbstractDungeon.player.masterDeck.group);
            CheckSakuraCardRepeat(AbstractDungeon.player.drawPile.group);
        }
    }

    public void atBattleStart()
    {
        this.elementCounter = 0;
    }

    public void atTurnStartPostDraw()
    {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        for (int i = 0; i < UPGRADE_NUMBER; i++)
        {
            AbstractDungeon.actionManager.addToBottom(new UpgradeRandomCardAction());
        }
    }

    public void atTurnStart()
    {
        this.flash();
        AbstractMonster monster = AbstractDungeon.getRandomMonster();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, AbstractDungeon.player, new KSMOD_CerberusMarkPower(monster, DAMAGE_PROMOTION)));

        if (this.counter >= GetTriggerNumber() && !AbstractDungeon.getCurrRoom().isBattleEnding() && !AbstractDungeon.getMonsters().areMonstersBasicallyDead())
        {
            ActiveRelic();
        }
    }

    public void onMonsterDeath(AbstractMonster monster)
    {
        if (!monster.hasPower(MinionPower.POWER_ID))
        {
            GainCharge(GAIN_NUMBER);
        }
    }

    public void GainCharge(int chargeNumber)
    {
        this.counter += chargeNumber;
    }

    private void ActiveRelic()
    {
        this.counter -= GetTriggerNumber();
        if (this.counter < 0)
        {
            this.counter = 0;
        }
        AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractCard card = new SpellCardTurn();
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(card));
        AbstractDungeon.player.masterDeck.addToBottom(card);
    }

    public int GetTriggerNumber()
    {
        int sakuraCardAmount = 0;
        if (AbstractDungeon.player instanceof KinomotoSakura)
        {
            for (AbstractCard card : AbstractDungeon.player.masterDeck.group)
            {
                if (card.cardID.contains("SakuraCardThe") || card.cardID.contains("SpellCardTurn"))
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

    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction)
    {
        if (targetCard.hasTag(KSMOD_CustomTag.KSMOD_EARTHY_CARD) || targetCard.hasTag(KSMOD_CustomTag.KSMOD_WATERY_CARD) || targetCard.hasTag(KSMOD_CustomTag.KSMOD_FIREY_CARD) || targetCard.hasTag(KSMOD_CustomTag.KSMOD_WINDY_CARD))
        {
            this.elementCounter += 1;
        }
        if (this.elementCounter >= MAGICK_CHARGE_TRIGGER_NUMBER)
        {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new KSMOD_MagickChargePower(AbstractDungeon.player, MAGICK_CHARGE_NUMBER), MAGICK_CHARGE_NUMBER));
            this.elementCounter -= MAGICK_CHARGE_TRIGGER_NUMBER;
        }
    }
}