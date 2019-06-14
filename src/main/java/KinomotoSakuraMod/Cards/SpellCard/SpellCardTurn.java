package KinomotoSakuraMod.Cards.SpellCard;

import KinomotoSakuraMod.Cards.KSMOD_AbstractSpellCard;
import KinomotoSakuraMod.Patches.KSMOD_CustomCardColor;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SpellCardTurn extends KSMOD_AbstractSpellCard
{
    public static final String ID = "SpellCardTurn";
    private static final String NAME;
    private static final String DESCRIPTION;
    private static final String IMAGE_PATH = "img/cards/default_card.png";
    private static final int COST = 0;
    private static final CardType CARD_TYPE = CardType.SKILL;
    private static final CardColor CARD_COLOR = KSMOD_CustomCardColor.SPELL_COLOR;
    private static final CardRarity CARD_RARITY = CardRarity.SPECIAL;
    private static final CardTarget CARD_TARGET = CardTarget.SELF;

    static
    {
        CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
    }

    public SpellCardTurn()
    {
        super(ID, NAME, IMAGE_PATH, COST, DESCRIPTION, CARD_TYPE, CARD_COLOR, CARD_RARITY, CARD_TARGET);
    }

    public boolean canUpgrade()
    {
        return false;
    }

    @Override
    public void upgrade()
    {
        
    }

    @Override
    public KSMOD_AbstractSpellCard makeCopy()
    {
        return new SpellCardTurn();
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {

    }
}
