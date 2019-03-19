package KinomotoSakuraMod.Cards.ClowCard;

import KinomotoSakuraMod.Cards.AbstractClowCard;
import KinomotoSakuraMod.Cards.CardMagicalType;
import KinomotoSakuraMod.Patches.CardColorEnum;
import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ClowCardTheShield extends AbstractClowCard
{
    public static final String ID = "ClowCardTheShield";
    private static final String NAME;
    private static final String DESCRIPTION;
    private static final String IMAGE_PATH = "img/cards/ClowCardTheShield.png";
    private static final int COST = 1;
    private static final CardType CARD_TYPE = CardType.SKILL;
    private static final CardColor CARD_COLOR = CardColorEnum.CLOWCARD_COLOR;
    private static final CardRarity CARD_RARITY = CardRarity.COMMON;
    private static final CardTarget CARD_TARGET = CardTarget.ENEMY;
    private static final CardMagicalType CARD_MAGICAL_TYPE = CardMagicalType.PHYSICS_CARD;
    private static final int BASE_BLOCK = 5;
    private static final int UPGRADE_BLOCK = 3;

    static
    {
        CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
    }

    public ClowCardTheShield()
    {
        super(ID, NAME, IMAGE_PATH, COST, DESCRIPTION, CARD_TYPE, CARD_COLOR, CARD_RARITY, CARD_TARGET, CARD_MAGICAL_TYPE);
        this.tags.add(BaseModCardTags.BASIC_DEFEND);
        this.baseBlock = BASE_BLOCK;
    }

    @Override
    public void upgrade()
    {
        if (!this.upgraded)
        {
            upgradeName();
            upgradeBlock(UPGRADE_BLOCK);
        }
    }

    @Override
    public AbstractCard makeCopy()
    {
        return new ClowCardTheShield();
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {

    }
}
