package KinomotoSakuraMod.Cards.ClowCard;

import KinomotoSakuraMod.Cards.KSMOD_AbstractMagicCard;
import KinomotoSakuraMod.Patches.CustomCardColor;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.VerticalImpactEffect;

public class ClowCardThePower extends KSMOD_AbstractMagicCard
{
    public static final String ID = "ClowCardThePower";
    private static final String NAME;
    private static final String DESCRIPTION;
    private static final String[] EXTENDED_DESCRIPTION;
    private static final String IMAGE_PATH = "img/cards/clowcard/the_power.png";
    private static final int COST = 2;
    private static final CardType CARD_TYPE = CardType.ATTACK;
    private static final CardColor CARD_COLOR = CustomCardColor.CLOWCARD_COLOR;
    private static final CardRarity CARD_RARITY = CardRarity.COMMON;
    private static final CardTarget CARD_TARGET = CardTarget.ENEMY;
    private static final int BASE_DAMAGE = 12;
    private static final int UPGRADE_DAMAGE = 3;

    static
    {
        CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    }

    public ClowCardThePower()
    {
        this(0);
    }

    public ClowCardThePower(int upgrades)
    {
        super(ID, NAME, IMAGE_PATH, COST, DESCRIPTION, CARD_TYPE, CARD_COLOR, CARD_RARITY, CARD_TARGET, true);
        this.baseDamage = BASE_DAMAGE;
        this.timesUpgraded = upgrades;
    }

    public KSMOD_AbstractMagicCard makeCopy()
    {
        return new ClowCardThePower(this.timesUpgraded);
    }

    public void upgrade()
    {
        this.upgradeDamage(UPGRADE_DAMAGE + this.timesUpgraded);
        this.timesUpgraded += 1;
        this.upgraded = true;
        this.name = NAME + "+" + this.timesUpgraded;
        this.initializeTitle();
    }

    public boolean canUpgrade()
    {
        return true;
    }

    public void applyNormalEffect(AbstractPlayer player, AbstractMonster monster)
    {
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new VerticalImpactEffect(monster.hb.cX + monster.hb.width / 4.0F, monster.hb.cY - monster.hb.height / 4.0F)));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, new DamageInfo(player, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }

    public void applyExtraEffect(AbstractPlayer player, AbstractMonster monster)
    {
        int damage = this.damage;
        if (player.hasPower(StrengthPower.POWER_ID) && this.timesUpgraded > 0)
        {
            StrengthPower power = (StrengthPower) player.getPower(StrengthPower.POWER_ID);
            this.damage += this.timesUpgraded * power.amount;
        }
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new VerticalImpactEffect(monster.hb.cX + monster.hb.width / 4.0F, monster.hb.cY - monster.hb.height / 4.0F)));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, new DamageInfo(player, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        this.damage = damage;
    }

    public String getExtraDescription()
    {
        return this.rawDescription + EXTENDED_DESCRIPTION[0] + this.timesUpgraded + EXTENDED_DESCRIPTION[1];
    }
}
