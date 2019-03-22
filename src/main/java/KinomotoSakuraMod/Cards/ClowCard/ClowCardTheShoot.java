package KinomotoSakuraMod.Cards.ClowCard;

import KinomotoSakuraMod.Cards.AbstractClowCard;
import KinomotoSakuraMod.Patches.CustomCardColor;
import KinomotoSakuraMod.Patches.CustomTag;
import KinomotoSakuraMod.Utility.ModLogger;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

public class ClowCardTheShoot extends AbstractClowCard
{
    public static final String ID = "ClowCardTheShoot";
    private static final String NAME;
    private static final String DESCRIPTION;
    private static final String IMAGE_PATH = "img/cards/default_attack_card.png";
    private static final int COST = 1;
    private static final CardType CARD_TYPE = CardType.ATTACK;
    private static final CardColor CARD_COLOR = CustomCardColor.CLOWCARD_COLOR;
    private static final CardRarity CARD_RARITY = CardRarity.COMMON;
    private static final CardTarget CARD_TARGET = CardTarget.ENEMY;
    private static final int BASE_DAMAGE = 3;
    private static final int UPGRADE_DAMAGE = 1;
    private static final String UPGRADE_DESCRIPTION;

    static
    {
        CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }

    public ClowCardTheShoot()
    {
        super(ID, NAME, IMAGE_PATH, COST, DESCRIPTION, CARD_TYPE, CARD_COLOR, CARD_RARITY, CARD_TARGET);
        this.tags.add(CustomTag.PHYSICS_CARD);
        this.baseDamage = BASE_DAMAGE;
    }

    @Override
    public void upgrade()
    {
        if (!this.upgraded)
        {
            upgradeName();
            upgradeDamage(UPGRADE_DAMAGE);
            this.rawDescription = UPGRADE_DESCRIPTION;
        }
    }

    @Override
    public AbstractClowCard makeCopy()
    {
        return new ClowCardTheShoot();
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        ModLogger.logger.info(this.correctDamage()+","+this.damage);
        ModLogger.logger.info(this.correctBlock()+","+this.block);
        ModLogger.logger.info(this.correctMagicNumber()+","+this.magicNumber);
        AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, new DamageInfo(player, this.correctDamage(), this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, player, new VulnerablePower(monster, 1, false), 1));

        AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, new DamageInfo(player, this.correctDamage(), this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, player, new VulnerablePower(monster, 1, false),1));

        if (this.upgradedMagicNumber)
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, player, new VulnerablePower(player, 1, false),1));
        }
    }
}