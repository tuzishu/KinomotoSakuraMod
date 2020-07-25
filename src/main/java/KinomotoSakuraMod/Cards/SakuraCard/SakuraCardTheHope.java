package KinomotoSakuraMod.Cards.SakuraCard;

import KinomotoSakuraMod.Cards.KSMOD_AbstractMagicCard;
import KinomotoSakuraMod.Cards.SpellCard.SpellCardEmptySpell;
import KinomotoSakuraMod.Patches.KSMOD_CustomCardColor;
import KinomotoSakuraMod.Utility.KSMOD_ReflectTool;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SakuraCardTheHope extends KSMOD_AbstractMagicCard
{
    public static final String ID = "SakuraCardTheHope";
    private static final String NAME;
    private static final String DESCRIPTION;
    private static final String IMAGE_PATH = "img/cards/sakuracard/the_hope.png";
    private static final int COST = 3;
    private static final CardType CARD_TYPE = CardType.POWER;
    private static final CardColor CARD_COLOR = KSMOD_CustomCardColor.SAKURACARD_COLOR;
    private static final CardRarity CARD_RARITY = AbstractCard.CardRarity.SPECIAL;
    private static final CardTarget CARD_TARGET = CardTarget.SELF;

    static
    {
        CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
    }

    public SakuraCardTheHope()
    {
        super(ID, NAME, IMAGE_PATH, COST, DESCRIPTION, CARD_TYPE, CARD_COLOR, CARD_RARITY, CARD_TARGET);
    }

    public boolean canUpgrade()
    {
        return false;
    }

    public void upgrade()
    {

    }

    @Override
    public AbstractCard makeCopy()
    {
        if (KSMOD_ReflectTool.IsReallyCopyingCard() && this.hasSameSakuraCard())
        {
            return getSameNameClowCard();
        }
        return new SakuraCardTheLove();
    }

    public AbstractCard getSameNameClowCard()
    {
        return new SpellCardEmptySpell();
    }

    @Override
    public void applyNormalEffect(AbstractPlayer player, AbstractMonster monster)
    {

    }
}
