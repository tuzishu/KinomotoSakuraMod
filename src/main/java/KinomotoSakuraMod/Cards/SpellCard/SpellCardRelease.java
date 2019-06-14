package KinomotoSakuraMod.Cards.SpellCard;

import KinomotoSakuraMod.Actions.KSMOD_ReleaseAction;
import KinomotoSakuraMod.Cards.KSMOD_AbstractSpellCard;
import KinomotoSakuraMod.Patches.KSMOD_CustomCardColor;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SpellCardRelease extends KSMOD_AbstractSpellCard
{
    public static final String ID = "SpellCardRelease";
    private static final String NAME;
    private static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    private static final String IMAGE_PATH = "img/cards/default_card.png";
    private static final int COST = 1;
    private static final CardType CARD_TYPE = CardType.SKILL;
    private static final CardColor CARD_COLOR = KSMOD_CustomCardColor.SPELL_COLOR;
    private static final CardRarity CARD_RARITY = CardRarity.BASIC;
    private static final CardTarget CARD_TARGET = CardTarget.NONE;
    private static final int BASE_DAMAGE = 3;
    private static final float BASE_RELEASE_UPGRADE_RATE = 0.25F;
    // private static final float UPGRADE_RELEASE_UPGRADE_RATE = 0.5F;

    static
    {
        CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    }

    public SpellCardRelease()
    {
        super(ID, NAME, IMAGE_PATH, COST, DESCRIPTION, CARD_TYPE, CARD_COLOR, CARD_RARITY, CARD_TARGET);
        this.baseDamage = BASE_DAMAGE;
    }

    public boolean canUpgrade()
    {
        return false;
    }

    @Override
    public void upgrade()
    {

    }

    @Override
    public KSMOD_AbstractSpellCard makeCopy()
    {
        return new SpellCardRelease();
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        // 剑玉未添加
        AbstractDungeon.actionManager.addToBottom(new KSMOD_ReleaseAction(this.damage, BASE_RELEASE_UPGRADE_RATE));
    }
}
