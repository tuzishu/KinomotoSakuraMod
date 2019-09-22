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
import com.megacrit.cardcrawl.monsters.beyond.Darkling;
import com.megacrit.cardcrawl.powers.MinionPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;

public class KSMOD_StarWand extends CustomRelic
{
    public static final String RELIC_ID = "KSMOD_StarWand";
    private static final String RELIC_IMG_PATH = "img/relics/icon/star_wand.png";
    private static final String RELIC_IMG_OTL_PATH = "img/relics/outline/star_wand.png";
    private static final RelicTier RELIC_TIER = RelicTier.BOSS;
    private static final LandingSound RELIC_SOUND = AbstractRelic.LandingSound.MAGICAL;
    private static final int START_COUNT = 0;
    private static final int UPDATE_TRIGGER_NUMBER = 30;
    private static final int BASE_TRIGGER_NUMBER = 30;
    private static final int GAIN_NUMBER = 4;
    private ArrayList<AbstractMonster> sealedMonsters = new ArrayList<>();

    public KSMOD_StarWand()
    {
        super(RELIC_ID, ImageMaster.loadImage(RELIC_IMG_PATH), ImageMaster.loadImage(RELIC_IMG_OTL_PATH), RELIC_TIER, RELIC_SOUND);
        this.counter = START_COUNT;
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0] + GAIN_NUMBER + DESCRIPTIONS[1] + BASE_TRIGGER_NUMBER + DESCRIPTIONS[2] + BASE_TRIGGER_NUMBER + DESCRIPTIONS[3] + UPDATE_TRIGGER_NUMBER + DESCRIPTIONS[4];
    }

    public boolean canSpawn()
    {
        return AbstractDungeon.player instanceof KinomotoSakura && AbstractDungeon.player.hasRelic(KSMOD_SealedWand.RELIC_ID);
    }

    public AbstractRelic makeCopy()
    {
        return new KSMOD_StarWand();
    }

    public void obtain()
    {
        AbstractRelic oldWand = AbstractDungeon.player.getRelic(KSMOD_SealedWand.RELIC_ID);
        int targetIndex = AbstractDungeon.player.relics.indexOf(oldWand);
        if (AbstractDungeon.player.hasRelic(KSMOD_SealedWand.RELIC_ID))
        {
            if (AbstractDungeon.player.hasRelic(KSMOD_Cerberus.RELIC_ID) && AbstractDungeon.player.hasRelic(KSMOD_Yue.RELIC_ID))
            {
                KSMOD_UltimateWand wand = new KSMOD_UltimateWand();
                wand.counter = oldWand.counter;
                wand.instantObtain(AbstractDungeon.player, targetIndex, true);
                AbstractDungeon.player.loseRelic(KSMOD_Cerberus.RELIC_ID);
                AbstractDungeon.player.loseRelic(KSMOD_Yue.RELIC_ID);
            }
            else
            {
                this.counter = oldWand.counter;
                this.instantObtain(AbstractDungeon.player, targetIndex, false);
            }
        }
        else
        {
            super.obtain();
        }
    }

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
        sealedMonsters.clear();
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
        if (!monster.hasPower(MinionPower.POWER_ID) && !sealedMonsters.contains(monster))
        {
            GainCharge(GAIN_NUMBER);
        }
        if (monster.id == Darkling.ID)
        {
            sealedMonsters.add(monster);
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
}
