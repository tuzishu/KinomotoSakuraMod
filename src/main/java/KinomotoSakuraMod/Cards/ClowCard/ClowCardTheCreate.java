package KinomotoSakuraMod.Cards.ClowCard;

import KinomotoSakuraMod.Cards.KSMOD_AbstractMagicCard;
import KinomotoSakuraMod.Patches.KSMOD_CustomCardColor;
import KinomotoSakuraMod.Powers.KSMOD_CreatePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
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
    private static final CardType CARD_TYPE = CardType.POWER;
    private static final CardColor CARD_COLOR = KSMOD_CustomCardColor.CLOWCARD_COLOR;
    private static final CardRarity CARD_RARITY = CardRarity.RARE;
    private static final CardTarget CARD_TARGET = CardTarget.SELF;

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
    }

    @Override
    public void upgrade()
    {
        if (!this.upgraded)
        {
            upgradeName();
            this.retain = true;
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public KSMOD_AbstractMagicCard makeCopy()
    {
        return new ClowCardTheCreate();
    }

    @Override
    public void applyNormalEffect(AbstractPlayer player, AbstractMonster monster)
    {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new KSMOD_CreatePower(), 1));
        RemoveThisFromMasterDeck();
    }

    private void RemoveThisFromMasterDeck()
    {
        int size = AbstractDungeon.player.masterDeck.group.size();
        for (int i = 0; i < size; i++)
        {
            AbstractCard card = AbstractDungeon.player.masterDeck.group.get(i);
            if (card instanceof ClowCardTheCreate && card.name.equals(this.name) && card.upgraded == this.upgraded)
            {
                AbstractDungeon.player.masterDeck.removeCard(card);
                AbstractDungeon.getCurrRoom().addRelicToRewards(GetRandomTier());
                break;
            }
        }
    }

    private AbstractRelic.RelicTier GetRandomTier()
    {
        float randNum = new Random().random(0F, 1F);
        if (randNum <= 0.125F)
        {
            return AbstractRelic.RelicTier.RARE;
        }
        else if (randNum <= 0.4F)
        {
            return AbstractRelic.RelicTier.UNCOMMON;
        }
        else
        {
            return AbstractRelic.RelicTier.COMMON;
        }
    }
}
