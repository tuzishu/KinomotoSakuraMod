package KinomotoSakuraMod.Cards.ClowCard;

import KinomotoSakuraMod.Cards.AbstractMagicCard;
import KinomotoSakuraMod.Patches.CustomCardColor;
import KinomotoSakuraMod.Patches.CustomTag;
import KinomotoSakuraMod.Powers.BigPower;
import KinomotoSakuraMod.Powers.SmallPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ClowCardTheBig extends AbstractMagicCard
{
    public static final String ID = "ClowCardTheBig";
    private static final String NAME;
    private static final String DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION;
    private static final String IMAGE_PATH = "img/cards/default_power_card.png";
    private static final int COST = 1;
    private static final AbstractCard.CardType CARD_TYPE = AbstractCard.CardType.POWER;
    private static final AbstractCard.CardColor CARD_COLOR = CustomCardColor.CLOWCARD_COLOR;
    private static final AbstractCard.CardRarity CARD_RARITY = AbstractCard.CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget CARD_TARGET = AbstractCard.CardTarget.SELF;
    private static final int BASE_MAGIC_NUMBER = 10;
    private static final int UPGRADE_MAGIC_NUMBER = 10;

    static
    {
        CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }

    public ClowCardTheBig()
    {
        super(ID, NAME, IMAGE_PATH, COST, DESCRIPTION, CARD_TYPE, CARD_COLOR, CARD_RARITY, CARD_TARGET);
        this.tags.add(CustomTag.PHYSICS_CARD);
        this.setBaseMagicNumber(BASE_MAGIC_NUMBER);
    }

    public AbstractMagicCard makeCopy()
    {
        return new ClowCardTheBig();
    }

    public void upgrade()
    {
        if (!this.upgraded)
        {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_MAGIC_NUMBER);
            this.rawDescription = UPGRADE_DESCRIPTION;
        }
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        if (player.hasPower(SmallPower.POWER_ID))
        {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(player,player,SmallPower.POWER_ID));
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new BigPower(this.correctMagicNumber()), this.correctMagicNumber()));
    }
}
