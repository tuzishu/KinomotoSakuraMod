package KinomotoSakuraMod.Cards.ClowCard;

import KinomotoSakuraMod.Cards.KSMOD_AbstractMagicCard;
import KinomotoSakuraMod.Cards.SakuraCard.SakuraCardTheThrough;
import KinomotoSakuraMod.Patches.KSMOD_CustomCardColor;
import KinomotoSakuraMod.Patches.KSMOD_CustomTag;
import KinomotoSakuraMod.Relics.KSMOD_SealedBook;
import KinomotoSakuraMod.Utility.KSMOD_Utility;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;

public class ClowCardTheThrough extends KSMOD_AbstractMagicCard
{
    public static final String ID = "ClowCardTheThrough";
    private static final String NAME;
    private static final String DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION;
    private static final String[] EXTENDED_DESCRIPTION;
    private static final String IMAGE_PATH = "img/cards/clowcard/the_through.png";
    private static final int COST = 1;
    private static final CardType CARD_TYPE = CardType.ATTACK;
    private static final CardColor CARD_COLOR = KSMOD_CustomCardColor.CLOWCARD_COLOR;
    private static final CardRarity CARD_RARITY = CardRarity.RARE;
    private static final CardTarget CARD_TARGET = CardTarget.ALL_ENEMY;
    private static final int BASE_DAMAGE = 12;
    private static final int UPGRADE_DAMAGE = 3;
    private static final int BASE_MAGIC_NUMBER = 0;
    private static final int UPGRADE_MAGIC_NUMBER = 2;
    private static final float CLEAVE_DURATION = 0.1F;
    private static final String SOUND_KEY = "ATTACK_DAGGER_";

    static
    {
        CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    }

    public ClowCardTheThrough()
    {
        super(ID, NAME, IMAGE_PATH, COST, DESCRIPTION, CARD_TYPE, CARD_COLOR, CARD_RARITY, CARD_TARGET, true);
        this.tags.add(KSMOD_CustomTag.KSMOD_EARTHY_CARD);
        this.cardsToPreview = new SakuraCardTheThrough();
        this.baseDamage = BASE_DAMAGE;
        this.setBaseMagicNumber(BASE_MAGIC_NUMBER);
    }

    @Override
    public void upgrade()
    {
        if (!this.upgraded)
        {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_DAMAGE);
            this.upgradeMagicNumber(UPGRADE_MAGIC_NUMBER);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public KSMOD_AbstractMagicCard makeCopy()
    {
        return new ClowCardTheThrough();
    }

    @Override
    public void applyNormalEffect(AbstractPlayer player, AbstractMonster monster)
    {
        CardCrawlGame.sound.play(SOUND_KEY + MathUtils.random(1, 6));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(player, new CleaveEffect(), CLEAVE_DURATION));
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(player, KSMOD_Utility.GetDamageList(this.damage), DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        for (AbstractMonster mon : AbstractDungeon.getMonsters().monsters)
        {
            if (mon.hasPower(PoisonPower.POWER_ID))
            {
                int amount = mon.getPower(PoisonPower.POWER_ID).amount;
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mon, player, new PoisonPower(mon, player, this.magicNumber), this.magicNumber));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(mon, new DamageInfo(player, amount, DamageInfo.DamageType.HP_LOSS), AbstractGameAction.AttackEffect.POISON, true));
            }
        }
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new VoidCard(), 1));
    }

    @Override
    public void applyExtraEffect(AbstractPlayer player, AbstractMonster monster)
    {
        CardCrawlGame.sound.play(SOUND_KEY + MathUtils.random(1, 6));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(player, new CleaveEffect(), CLEAVE_DURATION));
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(player, KSMOD_Utility.GetDamageList(this.damage), DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        for (AbstractMonster mon : AbstractDungeon.getMonsters().monsters)
        {
            if (mon.hasPower(PoisonPower.POWER_ID))
            {
                int amount = mon.getPower(PoisonPower.POWER_ID).amount + this.magicNumber;
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mon, player, new PoisonPower(mon, player, this.magicNumber), this.magicNumber));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(mon, new DamageInfo(player, amount, DamageInfo.DamageType.HP_LOSS), AbstractGameAction.AttackEffect.POISON, true));
            }
        }
        CardCrawlGame.sound.play(SOUND_KEY + MathUtils.random(1, 6));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(player, new CleaveEffect(), CLEAVE_DURATION));
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(player, GetPercentageDamageList(), DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new VoidCard(), 1));
    }

    @Override
    public String getExtraDescription()
    {
        return this.rawDescription + EXTENDED_DESCRIPTION[0] + (int) (KSMOD_SealedBook.PERCENTAGE_RATE * 100) + EXTENDED_DESCRIPTION[1];
    }

    private int[] GetPercentageDamageList()
    {
        int size = AbstractDungeon.getMonsters().monsters.size();
        int[] damageList = new int[size];
        for (int i = 0; i < size; i++)
        {
            damageList[i] = (int) (AbstractDungeon.getMonsters().monsters.get(i).maxHealth * KSMOD_SealedBook.PERCENTAGE_RATE);
        }
        return damageList;
    }
}
