package KinomotoSakuraMod.Cards.ClowCard;

import KinomotoSakuraMod.Actions.KSMOD_MirrorAction;
import KinomotoSakuraMod.Cards.KSMOD_AbstractMagicCard;
import KinomotoSakuraMod.Patches.KSMOD_CustomCardColor;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ClowCardTheMirror extends KSMOD_AbstractMagicCard
{
    public static final String ID = "ClowCardTheMirror";
    private static final String NAME;
    private static final String DESCRIPTION;
    private static final String[] EXTENDED_DESCRIPTION;
    private static final String IMAGE_PATH = "img/cards/clowcard/the_mirror.png";
    private static final int COST = 1;
    private static final int UPGRADED_COST = 0;
    private static final CardType CARD_TYPE = CardType.SKILL;
    private static final CardColor CARD_COLOR = KSMOD_CustomCardColor.CLOWCARD_COLOR;
    private static final CardRarity CARD_RARITY = CardRarity.COMMON;
    private static final CardTarget CARD_TARGET = CardTarget.NONE;
    private static final int COPY_NUMBER = 1;
    private static final int EXTRA_COPY_NUMBER = 2;

    static
    {
        CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    }

    public ClowCardTheMirror()
    {
        super(ID, NAME, IMAGE_PATH, COST, DESCRIPTION, CARD_TYPE, CARD_COLOR, CARD_RARITY, CARD_TARGET, true);
        this.exhaust = true;
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
        return new ClowCardTheMirror();
    }

    @Override
    public void applyNormalEffect(AbstractPlayer player, AbstractMonster monster)
    {
        AbstractDungeon.actionManager.addToBottom(new KSMOD_MirrorAction(COPY_NUMBER));
    }

    @Override
    public void applyExtraEffect(AbstractPlayer player, AbstractMonster monster)
    {
        AbstractDungeon.actionManager.addToBottom(new KSMOD_MirrorAction(EXTRA_COPY_NUMBER));
    }

    @Override
    public String getExtraDescription()
    {
        return this.rawDescription + EXTENDED_DESCRIPTION[0];
    }
}
