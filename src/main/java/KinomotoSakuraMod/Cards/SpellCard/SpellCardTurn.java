package KinomotoSakuraMod.Cards.SpellCard;

import KinomotoSakuraMod.Cards.AbstractSpellCard;
import KinomotoSakuraMod.Cards.CardMagicalType;
import KinomotoSakuraMod.Cards.ClowCard.ClowCardTheSword;
import KinomotoSakuraMod.Patches.CardColorEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SpellCardTurn extends AbstractSpellCard
{
    public static final String ID = "SpellCardTurn";
    private static final String NAME;
    private static final String DESCRIPTION;
    private static final String IMAGE_PATH = "img/cards/SpellCardTurn.png";
    private static final int COST = 0;
    private static final CardType CARD_TYPE = CardType.SKILL;
    private static final CardColor CARD_COLOR = CardColorEnum.SPELL_COLOR;
    private static final CardRarity CARD_RARITY = CardRarity.SPECIAL;
    private static final CardTarget CARD_TARGET = CardTarget.SELF;
    private static final CardMagicalType CARD_MAGICAL_TYPE = CardMagicalType.DEFAULT;

    static
    {
        CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
    }

    public SpellCardTurn()
    {
        super(ID, NAME, IMAGE_PATH, COST, DESCRIPTION, CARD_TYPE, CARD_COLOR, CARD_RARITY, CARD_TARGET, CARD_MAGICAL_TYPE);
    }

    @Override
    public void upgrade()
    {

    }

    @Override
    public AbstractCard makeCopy()
    {
        return new SpellCardTurn();
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {

    }
}
