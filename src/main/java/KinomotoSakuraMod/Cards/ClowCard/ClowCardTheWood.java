package KinomotoSakuraMod.Cards.ClowCard;

import KinomotoSakuraMod.Cards.KSMOD_AbstractMagicCard;
import KinomotoSakuraMod.Patches.KSMOD_CustomCardColor;
import KinomotoSakuraMod.Relics.KSMOD_SealedBook;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.WeakPower;

public class ClowCardTheWood extends KSMOD_AbstractMagicCard
{
    public static final String ID = "ClowCardTheWood";
    private static final String NAME;
    private static final String DESCRIPTION;
    private static final String[] EXTENDED_DESCRIPTION;
    private static final String IMAGE_PATH = "img/cards/clowcard/the_wood.png";
    private static final int COST = 1;
    private static final CardType CARD_TYPE = CardType.ATTACK;
    private static final CardColor CARD_COLOR = KSMOD_CustomCardColor.CLOWCARD_COLOR;
    private static final CardRarity CARD_RARITY = CardRarity.COMMON;
    private static final CardTarget CARD_TARGET = CardTarget.ENEMY;
    private static final int BASE_MAGIC_NUMBER = 3;
    private static final int UPGRADE_MAGIC_NUMBER = 2;
    private static final String SOUND_KEY = "POWER_CONSTRICTED";

    static
    {
        CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    }

    public ClowCardTheWood()
    {
        super(ID, NAME, IMAGE_PATH, COST, DESCRIPTION, CARD_TYPE, CARD_COLOR, CARD_RARITY, CARD_TARGET);
        this.setBaseMagicNumber(BASE_MAGIC_NUMBER);
    }

    @Override
    public void upgrade()
    {
        if (!this.upgraded)
        {
            upgradeName();
            this.upgradeMagicNumber(UPGRADE_MAGIC_NUMBER);
        }
    }

    @Override
    public KSMOD_AbstractMagicCard makeCopy()
    {
        return new ClowCardTheWood();
    }

    @Override
    public void applyNormalEffect(AbstractPlayer player, AbstractMonster monster)
    {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, player, new StrengthPower(monster, -this.magicNumber), -this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, player, new GainStrengthPower(monster, this.magicNumber), this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new SFXAction(SOUND_KEY, 0.05F));
    }

    public void applyExtraEffect(AbstractPlayer player, AbstractMonster monster)
    {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, player, new StrengthPower(monster, -this.magicNumber), -this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, player, new GainStrengthPower(monster, this.magicNumber), this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, player, new WeakPower(monster, KSMOD_SealedBook.WEAKENED_NUMBER, false), KSMOD_SealedBook.WEAKENED_NUMBER));
    }

    public String getExtraDescription()
    {
        return this.rawDescription + EXTENDED_DESCRIPTION[0] + KSMOD_SealedBook.WEAKENED_NUMBER + EXTENDED_DESCRIPTION[1];
    }
}
