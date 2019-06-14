package KinomotoSakuraMod.Cards.ClowCard;

import KinomotoSakuraMod.Actions.KSMOD_ReturnAction;
import KinomotoSakuraMod.Cards.KSMOD_AbstractMagicCard;
import KinomotoSakuraMod.Patches.KSMOD_CustomCardColor;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ClowCardTheReturn extends KSMOD_AbstractMagicCard
{
    public static final String ID = "ClowCardTheReturn";
    private static final String NAME;
    private static final String DESCRIPTION;
    private static final String IMAGE_PATH = "img/cards/clowcard/the_return.png";
    private static final int COST = 1;
    private static final CardType CARD_TYPE = CardType.SKILL;
    private static final CardColor CARD_COLOR = KSMOD_CustomCardColor.CLOWCARD_COLOR;
    private static final CardRarity CARD_RARITY = CardRarity.UNCOMMON;
    private static final CardTarget CARD_TARGET = CardTarget.NONE;
    private static final int BASE_MAGIC_NUMBER = 1;
    private static final int UPGRADE_MAGIC_NUMBER = 1;

    static
    {
        CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
    }

    public ClowCardTheReturn()
    {
        super(ID, NAME, IMAGE_PATH, COST, DESCRIPTION, CARD_TYPE, CARD_COLOR, CARD_RARITY, CARD_TARGET);
        this.setBaseMagicNumber(BASE_MAGIC_NUMBER);
        this.exhaust = true;
    }

    public KSMOD_AbstractMagicCard makeCopy()
    {
        return new ClowCardTheReturn();
    }

    public void upgrade()
    {
        if (!this.upgraded)
        {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_MAGIC_NUMBER);
        }
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        AbstractDungeon.actionManager.addToBottom(new KSMOD_ReturnAction(this.magicNumber));
    }
}
