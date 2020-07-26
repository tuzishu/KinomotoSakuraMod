package KinomotoSakuraMod.Cards.ClowCard;

import KinomotoSakuraMod.Cards.KSMOD_AbstractMagicCard;
import KinomotoSakuraMod.Cards.SakuraCard.SakuraCardTheHope;
import KinomotoSakuraMod.Patches.KSMOD_CustomCardColor;
import KinomotoSakuraMod.Powers.KSMOD_NothingPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ClowCardTheNothing extends KSMOD_AbstractMagicCard
{
    public static final String ID = "ClowCardTheNothing";
    private static final String NAME;
    private static final String DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION;
    private static final String[] EXTENDED_DESCRIPTION;
    private static final String IMAGE_PATH = "img/cards/clowcard/the_nothing.png";
    private static final int COST = 2;
    private static final CardType CARD_TYPE = CardType.POWER;
    private static final CardColor CARD_COLOR = KSMOD_CustomCardColor.CLOWCARD_COLOR;
    private static final CardRarity CARD_RARITY = CardRarity.SPECIAL;
    private static final CardTarget CARD_TARGET = CardTarget.SELF;
    public static final int BASE_DAMAGE = 4;
    public static final int UPGRADE_DAMAGE = 2;
    public static final int BASE_BLOCK = 3;
    public static final int UPGRADE_BLOCK = 2;
    private static final int BASE_MAGIC_NUMBER = 1;

    static
    {
        CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    }

    public ClowCardTheNothing()
    {
        super(ID, NAME, IMAGE_PATH, COST, DESCRIPTION, CARD_TYPE, CARD_COLOR, CARD_RARITY, CARD_TARGET);
        this.cardsToPreview = new SakuraCardTheHope();
        this.baseDamage = BASE_DAMAGE;
        this.baseBlock = BASE_BLOCK;
        this.setBaseMagicNumber(BASE_MAGIC_NUMBER);
    }

    @Override
    public void upgrade()
    {
        if (!this.upgraded)
        {
            upgradeName();
            this.upgradeDamage(UPGRADE_DAMAGE);
            this.upgradeBlock(UPGRADE_BLOCK);
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public KSMOD_AbstractMagicCard makeCopy()
    {
        return new ClowCardTheNothing();
    }


    @Override
    public void applyNormalEffect(AbstractPlayer player, AbstractMonster monster)
    {
        if (player.hasPower(KSMOD_NothingPower.POWER_ID))
        {
            ((KSMOD_NothingPower)player.getPower(KSMOD_NothingPower.POWER_ID)).SetValue(this.damage, this.block, this.upgraded);
        }
        else
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new KSMOD_NothingPower(player, this.damage, this.block, this.upgraded)));
        }
    }
}
