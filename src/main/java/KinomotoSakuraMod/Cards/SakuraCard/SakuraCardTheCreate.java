package KinomotoSakuraMod.Cards.SakuraCard;

import KinomotoSakuraMod.Cards.ClowCard.ClowCardTheCreate;
import KinomotoSakuraMod.Cards.KSMOD_AbstractMagicCard;
import KinomotoSakuraMod.Characters.KinomotoSakura;
import KinomotoSakuraMod.KSMOD;
import KinomotoSakuraMod.Patches.KSMOD_CustomCardColor;
import KinomotoSakuraMod.Patches.KSMOD_CustomTag;
import KinomotoSakuraMod.Powers.KSMOD_CreatePower;
import KinomotoSakuraMod.Utility.KSMOD_Utility;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;

public class SakuraCardTheCreate extends KSMOD_AbstractMagicCard
{
    public static final String ID = "SakuraCardTheCreate";
    private static final String NAME;
    private static final String DESCRIPTION;
    private static final String IMAGE_PATH = "img/cards/sakuracard/the_create.png";
    private static final int COST = 0;
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
        if (TryRemoveThisFromMasterDeck())
        {
            GetRelics();
        }
    }

    private void GetRelics()
    {
        AbstractDungeon.getCurrRoom().addRelicToRewards(GetRandomTier());
        AbstractRelic relic = GetRandomSakuraRelic();
        if (relic != null)
        {
            AbstractDungeon.getCurrRoom().addRelicToRewards(relic);
        }
        else
        {
            AbstractDungeon.getCurrRoom().addRelicToRewards(GetRandomTier());
        }
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
        ArrayList<AbstractRelic> relicList = new ArrayList<>();
        for (AbstractRelic r : KSMOD.GetRelicList())
        {
            if (r.tier == AbstractRelic.RelicTier.COMMON && isRelicPoolContains(r.relicId, AbstractDungeon.commonRelicPool))
            {
                relicList.add(r);
            }
            if (r.tier == AbstractRelic.RelicTier.UNCOMMON && isRelicPoolContains(r.relicId, AbstractDungeon.uncommonRelicPool))
            {
                relicList.add(r);
            }
            if (r.tier == AbstractRelic.RelicTier.RARE && isRelicPoolContains(r.relicId, AbstractDungeon.rareRelicPool))
            {
                relicList.add(r);
            }
        }
        relic = KSMOD_Utility.GetRandomListElement(relicList);
        if (relic != null)
        {
            if (relic.tier == AbstractRelic.RelicTier.COMMON)
            {
                AbstractDungeon.commonRelicPool.remove(relic.relicId);
            }
            else if (relic.tier == AbstractRelic.RelicTier.UNCOMMON)
            {
                AbstractDungeon.uncommonRelicPool.remove(relic.relicId);
            }
            else if (relic.tier == AbstractRelic.RelicTier.RARE)
            {
                AbstractDungeon.rareRelicPool.remove(relic.relicId);
            }
        }
        return relic;
    }

    private boolean TryRemoveThisFromMasterDeck()
    {
        for (AbstractCard card : AbstractDungeon.player.masterDeck.group)
        {
            if (card.cardID == this.cardID)
            {
                AbstractDungeon.player.masterDeck.removeCard(card);
                return true;
            }
        }
        AbstractDungeon.actionManager.addToBottom(new TalkAction(true, KinomotoSakura.GetMessage(2), 1.0F, 2.0F));
        return false;
    }

    private boolean isRelicPoolContains(String relicID, ArrayList<String> list)
    {
        for (String id : list)
        {
            if (relicID.equals(id))
            {
                return true;
            }
        }
        return false;
    }
}
