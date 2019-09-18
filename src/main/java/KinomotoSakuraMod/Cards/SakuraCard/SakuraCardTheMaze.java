package KinomotoSakuraMod.Cards.SakuraCard;

import KinomotoSakuraMod.Cards.ClowCard.ClowCardTheMaze;
import KinomotoSakuraMod.Cards.KSMOD_AbstractMagicCard;
import KinomotoSakuraMod.Patches.KSMOD_CustomCardColor;
import KinomotoSakuraMod.Patches.KSMOD_CustomTag;
import KinomotoSakuraMod.Utility.KSMOD_Utility;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SakuraCardTheMaze extends KSMOD_AbstractMagicCard
{
    public static final String ID = "SakuraCardTheMaze";
    private static final String NAME;
    private static final String DESCRIPTION;
    private static final String IMAGE_PATH = "img/cards/sakuracard/the_maze.png";
    private static final int COST = 1;
    private static final CardType CARD_TYPE = CardType.SKILL;
    private static final CardColor CARD_COLOR = KSMOD_CustomCardColor.SAKURACARD_COLOR;
    private static final CardRarity CARD_RARITY = CardRarity.SPECIAL;
    private static final CardTarget CARD_TARGET = CardTarget.SELF;
    private static final int BASE_BLOCK = 10;
    private static final int BASE_MAGIC_NUMBER = 3;

    static
    {
        CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
    }

    public SakuraCardTheMaze()
    {
        super(ID, NAME, IMAGE_PATH, COST, DESCRIPTION, CARD_TYPE, CARD_COLOR, CARD_RARITY, CARD_TARGET);
        this.tags.add(KSMOD_CustomTag.KSMOD_EARTHY_CARD);
        this.baseBlock = BASE_BLOCK;
        this.setBaseMagicNumber(3);
    }

    @Override
    public void upgrade()
    {

    }

    @Override
    public KSMOD_AbstractMagicCard makeCopy()
    {
        if (KSMOD_Utility.IsReallyCopyingCard() && this.hasSameSakuraCard())
        {
            return getSameNameClowCard();
        }
        return new SakuraCardTheMaze();
    }

    public KSMOD_AbstractMagicCard getSameNameClowCard()
    {
        return new ClowCardTheMaze();
    }

    @Override
    public void applyNormalEffect(AbstractPlayer player, AbstractMonster monster)
    {
        for (int i = 0; i < this.magicNumber; i++)
        {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(player, player, this.block));
        }
    }
}
