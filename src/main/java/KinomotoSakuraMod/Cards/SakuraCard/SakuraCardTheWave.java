package KinomotoSakuraMod.Cards.SakuraCard;

import KinomotoSakuraMod.Cards.ClowCard.ClowCardTheWave;
import KinomotoSakuraMod.Cards.KSMOD_AbstractMagicCard;
import KinomotoSakuraMod.Patches.KSMOD_CustomCardColor;
import KinomotoSakuraMod.Patches.KSMOD_CustomTag;
import KinomotoSakuraMod.Powers.KSMOD_EarthyPower;
import KinomotoSakuraMod.Powers.KSMOD_FireyPower;
import KinomotoSakuraMod.Powers.KSMOD_WateryPower;
import KinomotoSakuraMod.Powers.KSMOD_WindyPower;
import KinomotoSakuraMod.Utility.KSMOD_ReflectTool;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SakuraCardTheWave extends KSMOD_AbstractMagicCard
{
    public static final String ID = "SakuraCardTheWave";
    private static final String NAME;
    private static final String DESCRIPTION;
    private static final String IMAGE_PATH = "img/cards/sakuracard/the_wave.png";
    private static final int COST = 1;
    private static final CardType CARD_TYPE = CardType.SKILL;
    private static final CardColor CARD_COLOR = KSMOD_CustomCardColor.SAKURACARD_COLOR;
    private static final CardRarity CARD_RARITY = CardRarity.SPECIAL;
    private static final CardTarget CARD_TARGET = CardTarget.SELF;

    static
    {
        CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
    }

    public SakuraCardTheWave()
    {
        super(ID, NAME, IMAGE_PATH, COST, DESCRIPTION, CARD_TYPE, CARD_COLOR, CARD_RARITY, CARD_TARGET);
        this.tags.add(KSMOD_CustomTag.KSMOD_WATERY_CARD);
        this.exhaust = true;
    }

    @Override
    public KSMOD_AbstractMagicCard makeCopy()
    {
        if (KSMOD_ReflectTool.IsReallyCopyingCard() && this.hasSameSakuraCard())
        {
            return getSameNameClowCard();
        }
        return new SakuraCardTheWave();
    }

    public KSMOD_AbstractMagicCard getSameNameClowCard()
    {
        return new ClowCardTheWave();
    }

    public void upgrade()
    {

    }

    @Override
    public void applyNormalEffect(AbstractPlayer player, AbstractMonster monster)
    {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new KSMOD_EarthyPower(player, 1)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new KSMOD_FireyPower(player, 1)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new KSMOD_WateryPower(player, 1)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new KSMOD_WindyPower(player, 1)));
    }
}
