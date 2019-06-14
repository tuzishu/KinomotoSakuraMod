package KinomotoSakuraMod.Cards.ClowCard;

import KinomotoSakuraMod.Cards.KSMOD_AbstractMagicCard;
import KinomotoSakuraMod.Patches.KSMOD_CustomCardColor;
import KinomotoSakuraMod.Patches.KSMOD_CustomTag;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.combat.ThrowDaggerEffect;

public class ClowCardTheShot extends KSMOD_AbstractMagicCard
{
    public static final String ID = "ClowCardTheShot";
    private static final String NAME;
    private static final String DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION;
    private static final String IMAGE_PATH = "img/cards/clowcard/the_shot.png";
    private static final int COST = 1;
    private static final CardType CARD_TYPE = CardType.ATTACK;
    private static final CardColor CARD_COLOR = KSMOD_CustomCardColor.CLOWCARD_COLOR;
    private static final CardRarity CARD_RARITY = CardRarity.COMMON;
    private static final CardTarget CARD_TARGET = CardTarget.ENEMY;
    private static final int BASE_DAMAGE = 3;
    private static final int UPGRADE_DAMAGE = 2;
    private static final int BASE_MAGIC_NUMBER = 1;

    static
    {
        CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }

    public ClowCardTheShot()
    {
        super(ID, NAME, IMAGE_PATH, COST, DESCRIPTION, CARD_TYPE, CARD_COLOR, CARD_RARITY, CARD_TARGET, KSMOD_CustomTag.PHYSICS_CARD);
        this.baseDamage = BASE_DAMAGE;
        this.setBaseMagicNumber(BASE_MAGIC_NUMBER);
    }

    @Override
    public void upgrade()
    {
        if (!this.upgraded)
        {
            upgradeName();
            upgradeDamage(UPGRADE_DAMAGE);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public KSMOD_AbstractMagicCard makeCopy()
    {
        return new ClowCardTheShot();
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        int count = 2;
        for (int i = 0; i < count; i++)
        {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new ThrowDaggerEffect(monster.hb.cX, monster.hb.cY)));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, new DamageInfo(player, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, player, new VulnerablePower(monster, this.magicNumber, false), this.magicNumber, true));
        }

        if (this.upgradedMagicNumber)
        {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new ThrowDaggerEffect(monster.hb.cX, monster.hb.cY)));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, player, new VulnerablePower(player, this.magicNumber, false), this.magicNumber));
        }
    }
}
