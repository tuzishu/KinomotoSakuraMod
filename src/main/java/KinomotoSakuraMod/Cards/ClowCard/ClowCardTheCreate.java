package KinomotoSakuraMod.Cards.ClowCard;

import KinomotoSakuraMod.Cards.KSMOD_AbstractMagicCard;
import KinomotoSakuraMod.Patches.KSMOD_CustomCardColor;
import KinomotoSakuraMod.Patches.KSMOD_CustomTag;
import KinomotoSakuraMod.Powers.KSMOD_CreatePower;
import KinomotoSakuraMod.Utility.KSMOD_Utility;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class ClowCardTheCreate extends KSMOD_AbstractMagicCard
{
    public static final String ID = "ClowCardTheCreate";
    private static final String NAME;
    private static final String DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION;
    private static final String IMAGE_PATH = "img/cards/clowcard/the_create.png";
    private static final int COST = 4;
    private static final int UPGRADED_COST = 1;
    private static final CardType CARD_TYPE = CardType.POWER;
    private static final CardColor CARD_COLOR = KSMOD_CustomCardColor.CLOWCARD_COLOR;
    private static final CardRarity CARD_RARITY = CardRarity.RARE;
    private static final CardTarget CARD_TARGET = CardTarget.SELF;
    private static final float RARE_RELIC_RATE = 0.125F;
    private static final float UNCOMMON_RELIC_RATE = 0.375F;

    static
    {
        CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }

    public ClowCardTheCreate()
    {
        super(ID, NAME, IMAGE_PATH, COST, DESCRIPTION, CARD_TYPE, CARD_COLOR, CARD_RARITY, CARD_TARGET);
        this.tags.add(KSMOD_CustomTag.KSMOD_EARTHY_CARD);
        this.tags.add(KSMOD_CustomTag.KSMOD_WINDY_CARD);
    }

    @Override
    public void upgrade()
    {
        if (!this.upgraded)
        {
            upgradeName();
            this.upgradeBaseCost(UPGRADED_COST);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy()
    {
        if (KSMOD_Utility.IsReallyCopyingCard())
        {
            return new VoidCard();
        }
        return new ClowCardTheCreate();
    }

    @Override
    public void applyNormalEffect(AbstractPlayer player, AbstractMonster monster)
    {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new KSMOD_CreatePower(), 1));
        TryRemoveThisFromMasterDeck();
        AbstractDungeon.getCurrRoom().addRelicToRewards(GetRandomTier());
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

    private void TryRemoveThisFromMasterDeck()
    {
        for (AbstractCard card : AbstractDungeon.player.masterDeck.group)
        {
            if (card.uuid == this.uuid)
            {
                AbstractDungeon.player.masterDeck.removeCard(card);
                break;
            }
        }
    }
}
