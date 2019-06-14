package KinomotoSakuraMod.Cards.ClowCard;

import KinomotoSakuraMod.Cards.KSMOD_AbstractMagicCard;
import KinomotoSakuraMod.Patches.KSMOD_CustomCardColor;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ClowCardTheRain extends KSMOD_AbstractMagicCard
{
    public static final String ID = "ClowCardTheRain";
    private static final String NAME;
    private static final String DESCRIPTION;
    private static final String[] EXTENDED_DESCRIPTION;
    private static final String IMAGE_PATH = "img/cards/clowcard/the_rain.png";
    private static final int COST = 2;
    private static final int UPGRADED_COST = 1;
    private static final CardType CARD_TYPE = CardType.SKILL;
    private static final CardColor CARD_COLOR = KSMOD_CustomCardColor.CLOWCARD_COLOR;
    private static final CardRarity CARD_RARITY = CardRarity.COMMON;
    private static final CardTarget CARD_TARGET = CardTarget.NONE;

    static
    {
        CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    }

    public ClowCardTheRain()
    {
        super(ID, NAME, IMAGE_PATH, COST, DESCRIPTION, CARD_TYPE, CARD_COLOR, CARD_RARITY, CARD_TARGET, true);
    }

    @Override
    public void upgrade()
    {
        if (!this.upgraded)
        {
            this.upgradeName();
            this.upgradeBaseCost(UPGRADED_COST);
        }
    }

    @Override
    public KSMOD_AbstractMagicCard makeCopy()
    {
        return new ClowCardTheRain();
    }

    @Override
    public void applyNormalEffect(AbstractPlayer player, AbstractMonster monster)
    {
        this.exhaust = true;
        for (AbstractCard card : player.hand.group)
        {
            card.setCostForTurn(card.cost - 1);
        }
    }

    public void applyExtraEffect(AbstractPlayer player, AbstractMonster monster)
    {
        this.exhaust = false;
        for (AbstractCard card : player.hand.group)
        {
            card.setCostForTurn(card.cost - 1);
        }
    }

    public String getExtraDescription()
    {
        return EXTENDED_DESCRIPTION[0];
    }
}
