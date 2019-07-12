package KinomotoSakuraMod.Cards.ClowCard;

import KinomotoSakuraMod.Cards.KSMOD_AbstractMagicCard;
import KinomotoSakuraMod.Patches.KSMOD_CustomCardColor;
import KinomotoSakuraMod.Patches.KSMOD_CustomTag;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ClowCardTheIllusion extends KSMOD_AbstractMagicCard
{
    public static final String ID = "ClowCardTheIllusion";
    private static final String NAME;
    private static final String DESCRIPTION;
    private static final String[] EXTENDED_DESCRIPTION;
    private static final String IMAGE_PATH = "img/cards/clowcard/the_illusion.png";
    private static final int COST = 1;
    private static final CardType CARD_TYPE = CardType.ATTACK;
    private static final CardColor CARD_COLOR = KSMOD_CustomCardColor.CLOWCARD_COLOR;
    private static final CardRarity CARD_RARITY = CardRarity.COMMON;
    private static final CardTarget CARD_TARGET = CardTarget.ENEMY;
    private static final int BASE_BLOCK = 7;
    private static final int UPGRADE_BLOCK = 4;
    private static final int BASE_MAGIC_NUMBER = 4;
    private static final int UPGRADE_MAGIC_NUMBER = 1;
    private static final int INCREASE_NUMBER = 3;
    private static final int CHARGED_INCREASE_NUMBER = 2;

    static
    {
        CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    }

    public ClowCardTheIllusion()
    {
        super(ID, NAME, IMAGE_PATH, COST, DESCRIPTION, CARD_TYPE, CARD_COLOR, CARD_RARITY, CARD_TARGET, true);
        this.tags.add(KSMOD_CustomTag.KSMOD_FIREY_CARD);
        this.baseBlock = BASE_BLOCK;
        this.setBaseMagicNumber(BASE_MAGIC_NUMBER);
    }

    @Override
    public void upgrade()
    {
        if (!this.upgraded)
        {
            upgradeName();
            this.upgradeDamage(UPGRADE_BLOCK);
            this.upgradeMagicNumber(UPGRADE_MAGIC_NUMBER);
        }
    }

    @Override
    public KSMOD_AbstractMagicCard makeCopy()
    {
        return new ClowCardTheIllusion();
    }

    @Override
    public void applyNormalEffect(AbstractPlayer player, AbstractMonster monster)
    {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(player, player, this.block));
        int count = player.exhaustPile.size() / INCREASE_NUMBER;
        if (count > 0)
        {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, new DamageInfo(player, this.magicNumber * count, DamageInfo.DamageType.HP_LOSS), AbstractGameAction.AttackEffect.FIRE));
        }
    }

    public void applyExtraEffect(AbstractPlayer player, AbstractMonster monster)
    {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(player, player, this.block));
        int count = player.exhaustPile.size() / CHARGED_INCREASE_NUMBER;
        if (count > 0)
        {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(player, player, this.magicNumber * count));
        }
    }

    public String getExtraDescription()
    {
        return EXTENDED_DESCRIPTION[0];
    }
}
