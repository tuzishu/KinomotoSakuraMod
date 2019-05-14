package KinomotoSakuraMod.Cards.SpellCard;

import KinomotoSakuraMod.Actions.ReleaseAction;
import KinomotoSakuraMod.Cards.AbstractSpellCard;
import KinomotoSakuraMod.Patches.CustomCardColor;
import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SpellCardRelease extends AbstractSpellCard
{
    public static final String ID = "SpellCardRelease";
    private static final String NAME;
    private static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    private static final String IMAGE_PATH = "img/cards/default_skill_card.png";
    private static final int COST = 1;
    private static final CardType CARD_TYPE = CardType.SKILL;
    private static final CardColor CARD_COLOR = CustomCardColor.SPELL_COLOR;
    private static final CardRarity CARD_RARITY = CardRarity.BASIC;
    private static final CardTarget CARD_TARGET = CardTarget.NONE;
    private static final int BASE_DAMAGE = 3;
    private static final int UPGRADE_DAMAGE = 2;
    private static final float BASE_RELEASE_UPGRADE_RATE = 0.25F;
    private static final float UPGRADE_RELEASE_UPGRADE_RATE = 0.25F;

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
        this.tags.add(BaseModCardTags.BASIC_STRIKE);
        this.baseDamage = BASE_DAMAGE;
    }

    @Override
    public void upgrade()
    {
        if (!this.upgraded)
        {
            upgradeName();
            upgradeDamage(UPGRADE_DAMAGE);
        }
    }

    @Override
    public AbstractSpellCard makeCopy()
    {
        return new SpellCardRelease();
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        AbstractDungeon.actionManager.addToBottom(new ReleaseAction(this.damage, upgraded ? BASE_RELEASE_UPGRADE_RATE : UPGRADE_RELEASE_UPGRADE_RATE));
    }
}
