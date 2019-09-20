package KinomotoSakuraMod.Cards.SakuraCard;

import KinomotoSakuraMod.Cards.ClowCard.ClowCardTheCreate;
import KinomotoSakuraMod.Cards.KSMOD_AbstractMagicCard;
import KinomotoSakuraMod.KSMOD;
import KinomotoSakuraMod.Patches.KSMOD_CustomCardColor;
import KinomotoSakuraMod.Patches.KSMOD_CustomTag;
import KinomotoSakuraMod.Powers.KSMOD_CreatePower;
import KinomotoSakuraMod.Utility.KSMOD_Utility;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;

import java.util.ArrayList;

public class SakuraCardTheCreate extends KSMOD_AbstractMagicCard
{
    public static final String ID = "SakuraCardTheCreate";
    private static final String NAME;
    private static final String DESCRIPTION;
    private static final String IMAGE_PATH = "img/cards/sakuracard/the_create.png";
    private static final int COST = 1;
    private static final CardType CARD_TYPE = CardType.POWER;
    private static final CardColor CARD_COLOR = KSMOD_CustomCardColor.SAKURACARD_COLOR;
    private static final CardRarity CARD_RARITY = CardRarity.SPECIAL;
    private static final CardTarget CARD_TARGET = CardTarget.SELF;
    private static final float RARE_RELIC_RATE = 0.125F;
    private static final float UNCOMMON_RELIC_RATE = 0.375F;

    static
    {
        CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
    }

    public SakuraCardTheCreate()
    {
        super(ID, NAME, IMAGE_PATH, COST, DESCRIPTION, CARD_TYPE, CARD_COLOR, CARD_RARITY, CARD_TARGET);
        this.tags.add(KSMOD_CustomTag.KSMOD_EARTHY_CARD);
        this.tags.add(KSMOD_CustomTag.KSMOD_WINDY_CARD);
    }

    @Override
    public void upgrade()
    {

    }

    @Override
    public KSMOD_AbstractMagicCard makeCopy()
    {
        if (KSMOD_Utility.IsReallyCopyingCard() && this.hasSameSakuraCard())
        {
            return getSameNameClowCard();
        }
        return new SakuraCardTheCreate();
    }

    public KSMOD_AbstractMagicCard getSameNameClowCard()
    {
        return new ClowCardTheCreate();
    }

    @Override
    public void applyNormalEffect(AbstractPlayer player, AbstractMonster monster)
    {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new KSMOD_CreatePower(player, 2), 2));
        AbstractDungeon.getCurrRoom().addRelicToRewards(GetRandomTier());
        AbstractRelic relic = GetRandomSakuraRelic();
        if (relic != null)
        {
            AbstractDungeon.getCurrRoom().addRelicToRewards(relic);
        }
        TryRemoveThisFromMasterDeck();
    }

    private AbstractRelic.RelicTier GetRandomTier()
    {
        float randNum = new Random().random(0F, 1F);
        if (randNum <= RARE_RELIC_RATE)
        {
            return AbstractRelic.RelicTier.RARE;
        }
        else if (randNum <= RARE_RELIC_RATE + UNCOMMON_RELIC_RATE)
        {
            return AbstractRelic.RelicTier.UNCOMMON;
        }
        else
        {
            return AbstractRelic.RelicTier.COMMON;
        }
    }

    private AbstractRelic GetRandomSakuraRelic()
    {
        AbstractRelic relic;
        ArrayList<AbstractRelic> commonRelicList = new ArrayList<>();
        ArrayList<AbstractRelic> uncommonRelicList = new ArrayList<>();
        ArrayList<AbstractRelic> rareRelicList = new ArrayList<>();
        for (AbstractRelic r : KSMOD.GetRelicList())
        {
            if (AbstractDungeon.player.hasRelic(r.relicId))
            {
                continue;
            }
            for (RewardItem reward : AbstractDungeon.getCurrRoom().rewards)
            {
                if (r.relicId.equals(reward.relic.relicId))
                {
                    continue;
                }
            }
            if (r.tier == AbstractRelic.RelicTier.COMMON)
            {
                commonRelicList.add(r);
            }
            if (r.tier == AbstractRelic.RelicTier.UNCOMMON)
            {
                uncommonRelicList.add(r);
            }
            if (r.tier == AbstractRelic.RelicTier.RARE)
            {
                rareRelicList.add(r);
            }
        }
        float randNum = new Random().random(0F, 1F);
        if (randNum <= 0.125F)
        {
            relic = GetRandomListElement(rareRelicList);
            if (relic == null)
            {
                KSMOD_Utility.Logger.error("Sakura's rare relics are insufficient");
            }
        }
        else if (randNum <= 0.4F)
        {
            relic = GetRandomListElement(uncommonRelicList);
            if (relic == null)
            {
                KSMOD_Utility.Logger.error("Sakura's uncommon relics are insufficient");
            }
        }
        else
        {
            relic = GetRandomListElement(commonRelicList);
            if (relic == null)
            {
                KSMOD_Utility.Logger.error("Sakura's common relics are insufficient");
            }
        }
        return relic;
    }

    private <T> T GetRandomListElement(ArrayList<T> arrayList)
    {
        if (arrayList.size() > 0)
        {
            return arrayList.get(new Random().random(0, arrayList.size() - 1));
        }
        return null;
    }

    private void TryRemoveThisFromMasterDeck()
    {
        for (AbstractCard card : AbstractDungeon.player.masterDeck.group)
        {
            if (card.cardID == this.cardID)
            {
                AbstractDungeon.player.masterDeck.removeCard(card);
                break;
            }
        }
    }
}
