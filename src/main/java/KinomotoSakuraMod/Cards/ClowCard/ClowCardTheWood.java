package KinomotoSakuraMod.Cards.ClowCard;

import KinomotoSakuraMod.Actions.ApplyElementAction;
import KinomotoSakuraMod.Cards.AbstractMagicCard;
import KinomotoSakuraMod.Patches.CustomCardColor;
import KinomotoSakuraMod.Patches.CustomTag;
import KinomotoSakuraMod.Powers.EarthyElementPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

public class ClowCardTheWood extends AbstractMagicCard
{
    public static final String ID = "ClowCardTheWood";
    private static final String NAME;
    private static final String DESCRIPTION;
    private static final String IMAGE_PATH = "img/cards/default_attack_card.png";
    private static final int COST = 1;
    private static final CardType CARD_TYPE = CardType.ATTACK;
    private static final CardColor CARD_COLOR = CustomCardColor.CLOWCARD_COLOR;
    private static final CardRarity CARD_RARITY = CardRarity.COMMON;
    private static final CardTarget CARD_TARGET = CardTarget.ENEMY;
    private static final int BASE_DAMAGE = 4;
    private static final int UPGRADE_DAMAGE = 2;
    private static final int BASE_MAGIC_NUMBER = 2;
    private static final int ACTIVE_ELEMENT_NUMBER = 12;
    private static final int CONSTRICTED_NUMBER = 2;
    private static final String SOUND_KEY = "POWER_CONSTRICTED";

    static
    {
        CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
    }

    public ClowCardTheWood()
    {
        super(ID, NAME, IMAGE_PATH, COST, DESCRIPTION, CARD_TYPE, CARD_COLOR, CARD_RARITY, CARD_TARGET, CustomTag.ELEMENT_CARD);
        this.baseDamage = BASE_DAMAGE;
        this.setBaseMagicNumber(BASE_MAGIC_NUMBER);
    }

    @Override
    public void upgrade()
    {
        if (!this.upgraded)
        {
            upgradeName();
            this.upgradeDamage(UPGRADE_DAMAGE);
        }
    }

    @Override
    public AbstractMagicCard makeCopy()
    {
        return new ClowCardTheWood();
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, new DamageInfo(player, this.correctDamage(), DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, player, new StrengthPower(monster, -this.correctDamage()), -this.correctDamage()));
        if (!monster.hasPower(ArtifactPower.POWER_ID))
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, player, new GainStrengthPower(monster, this.correctDamage()), this.correctDamage()));
        }

        if (EarthyElementPower.TryActiveEarthyElement(monster, ACTIVE_ELEMENT_NUMBER, true))
        {
            AbstractDungeon.actionManager.addToBottom(new SFXAction(SOUND_KEY, 0.05F));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, player, new ConstrictedPower(monster, player, CONSTRICTED_NUMBER), CONSTRICTED_NUMBER));
        }

        AbstractDungeon.actionManager.addToBottom(new ApplyElementAction(monster, player, new EarthyElementPower(monster, this.correctMagicNumber()), this.correctMagicNumber(), true));
    }
}
