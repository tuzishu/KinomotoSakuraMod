package KinomotoSakuraMod.Cards.ClowCard;

import KinomotoSakuraMod.Cards.KSMOD_AbstractMagicCard;
import KinomotoSakuraMod.Patches.KSMOD_CustomCardColor;
import KinomotoSakuraMod.Patches.KSMOD_CustomTag;
import KinomotoSakuraMod.Powers.SilentPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BufferPower;

public class ClowCardTheSilent extends KSMOD_AbstractMagicCard
{
    public static final String ID = "ClowCardTheSilent";
    private static final String NAME;
    private static final String DESCRIPTION;
    private static final String IMAGE_PATH = "img/cards/clowcard/the_silent.png";
    private static final int COST = 1;
    private static final int UPGRADED_COST = 0;
    private static final CardType CARD_TYPE = CardType.SKILL;
    private static final CardColor CARD_COLOR = KSMOD_CustomCardColor.CLOWCARD_COLOR;
    private static final CardRarity CARD_RARITY = CardRarity.COMMON;
    private static final CardTarget CARD_TARGET = CardTarget.SELF;
    private static final int BASE_MAGIC_NUMBER = 1;

    static
    {
        CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
    }

    public ClowCardTheSilent()
    {
        super(ID, NAME, IMAGE_PATH, COST, DESCRIPTION, CARD_TYPE, CARD_COLOR, CARD_RARITY, CARD_TARGET, KSMOD_CustomTag.PHYSICS_CARD);
        setBaseMagicNumber(BASE_MAGIC_NUMBER);
    }

    @Override
    public void upgrade()
    {
        if (!this.upgraded)
        {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
        }
    }

    @Override
    public KSMOD_AbstractMagicCard makeCopy()
    {
        return new ClowCardTheSilent();
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new BufferPower(player, this.magicNumber), this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new SilentPower(player)));
    }
}
