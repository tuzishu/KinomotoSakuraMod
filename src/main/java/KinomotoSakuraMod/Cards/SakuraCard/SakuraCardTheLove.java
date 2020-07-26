package KinomotoSakuraMod.Cards.SakuraCard;

import KinomotoSakuraMod.Cards.KSMOD_AbstractMagicCard;
import KinomotoSakuraMod.Cards.SpellCard.SpellCardEmptySpell;
import KinomotoSakuraMod.Patches.KSMOD_CustomCardColor;
import KinomotoSakuraMod.Utility.KSMOD_ReflectTool;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SakuraCardTheLove extends KSMOD_AbstractMagicCard
{
    public static final String ID = "SakuraCardTheLove";
    private static final String NAME;
    private static final String DESCRIPTION;
    private static final String IMAGE_PATH = "img/cards/sakuracard/the_love.png";
    private static final int COST = -2;
    private static final CardType CARD_TYPE = CardType.SKILL;
    private static final CardColor CARD_COLOR = KSMOD_CustomCardColor.SAKURACARD_COLOR;
    private static final CardRarity CARD_RARITY = CardRarity.SPECIAL;
    private static final CardTarget CARD_TARGET = CardTarget.NONE;
    private static final int BASE_MAGIC_NUMBER = 1;

    static
    {
        CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
    }

    public SakuraCardTheLove()
    {
        super(ID, NAME, IMAGE_PATH, COST, DESCRIPTION, CARD_TYPE, CARD_COLOR, CARD_RARITY, CARD_TARGET);
        this.cardsToPreview = new SakuraCardTheHope();
        this.setBaseMagicNumber(BASE_MAGIC_NUMBER);
    }

    public boolean canUpgrade()
    {
        return false;
    }

    public void upgrade()
    {

    }

    public boolean canUse(AbstractPlayer player, AbstractMonster monster)
    {
        return false;
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

    public void triggerWhenDrawn()
    {
        AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(this.magicNumber));
    }
}
