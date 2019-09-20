package KinomotoSakuraMod.Cards.ClowCard;

import KinomotoSakuraMod.Cards.KSMOD_AbstractMagicCard;
import KinomotoSakuraMod.Patches.KSMOD_CustomCardColor;
import KinomotoSakuraMod.Patches.KSMOD_CustomTag;
import KinomotoSakuraMod.Powers.KSMOD_MagickChargePower;
import KinomotoSakuraMod.Relics.KSMOD_SealedBook;
import KinomotoSakuraMod.Utility.KSMOD_Utility;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.BlizzardEffect;

public class ClowCardTheSnow extends KSMOD_AbstractMagicCard
{
    public static final String ID = "ClowCardTheSnow";
    private static final String NAME;
    private static final String DESCRIPTION;
    private static final String[] EXTENDED_DESCRIPTION;
    private static final String IMAGE_PATH = "img/cards/clowcard/the_snow.png";
    private static final int COST = 1;
    private static final AbstractCard.CardType CARD_TYPE = AbstractCard.CardType.ATTACK;
    private static final AbstractCard.CardColor CARD_COLOR = KSMOD_CustomCardColor.CLOWCARD_COLOR;
    private static final CardRarity CARD_RARITY = CardRarity.UNCOMMON;
    private static final CardTarget CARD_TARGET = CardTarget.ALL_ENEMY;
    private static final int BASE_DAMAGE = 3;
    private static final int UPGRADE_DAMAGE = 2;

    static
    {
        CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    }

    public ClowCardTheSnow()
    {
        super(ID, NAME, IMAGE_PATH, COST, DESCRIPTION, CARD_TYPE, CARD_COLOR, CARD_RARITY, CARD_TARGET, true);
        this.tags.add(KSMOD_CustomTag.KSMOD_WATERY_CARD);
        this.baseDamage = BASE_DAMAGE;
    }

    @Override
    public void upgrade()
    {
        if (!this.upgraded)
        {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_DAMAGE);
        }
    }

    @Override
    public KSMOD_AbstractMagicCard makeCopy()
    {
        return new ClowCardTheSnow();
    }

    @Override
    public void applyNormalEffect(AbstractPlayer player, AbstractMonster monster)
    {
        int count = 0;
        for (AbstractCard card: AbstractDungeon.actionManager.cardsPlayedThisTurn)
        {
            if (card.type == CardType.SKILL)
            {
                count += 1;
            }
        }
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new BlizzardEffect(count, AbstractDungeon.getMonsters().shouldFlipVfx()), 1.0F));
        for (int i = 0; i < count; i++)
        {
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(player, KSMOD_Utility.GetDamageList(this.damage), DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.BLUNT_HEAVY, true));
        }
    }

    @Override
    public void applyExtraEffect(AbstractPlayer player, AbstractMonster monster)
    {
        applyNormalEffect(player, monster);
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(player, KSMOD_Utility.GetDamageList(KSMOD_SealedBook.ENTIRETY_DAMAGE), DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }

    @Override
    public String getExtraDescription()
    {
        return this.rawDescription + EXTENDED_DESCRIPTION[0] + KSMOD_SealedBook.DAMAGE_INCREASE + EXTENDED_DESCRIPTION[1];
    }
}
