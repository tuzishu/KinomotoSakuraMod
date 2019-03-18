package KinomotoSakuraMod.Cards.ClowCard;

import KinomotoSakuraMod.Cards.AbstractClowCard;
import KinomotoSakuraMod.Cards.CardMagicalType;
import KinomotoSakuraMod.Patches.CardColorEnum;
import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ClowCardTheSword extends AbstractClowCard
{
    private static final String ID = "ClowCardTheSword";
    private static final String NAME;
    private static final String DESCRIPTION;
    private static final String IMAGE_PATH = "img/cards/ClowCardTheSword.png";
    private static final int COST = 1;
    private static final CardType CARD_TYPE = CardType.ATTACK;
    private static final CardColor CARD_COLOR = CardColorEnum.CLOWCARD_COLOR;
    private static final CardRarity CARD_RARITY = CardRarity.COMMON;
    private static final CardTarget CARD_TARGET = CardTarget.ENEMY;
    private static final CardMagicalType CARD_MAGICAL_TYPE = CardMagicalType.PhysicsCard;
    private static final int BASE_DAMAGE = 6;
    private static final int UPGRADE_DAMAGE = 3;

    static
    {
        CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
    }

    public ClowCardTheSword()
    {
        super(ID, NAME, IMAGE_PATH, COST, DESCRIPTION, CARD_TYPE, CARD_COLOR, CARD_RARITY, CARD_TARGET, CARD_MAGICAL_TYPE);
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

    public AbstractCard makeCopy()
    {
        return new ClowCardTheSword();
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, new DamageInfo(player, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }
}
