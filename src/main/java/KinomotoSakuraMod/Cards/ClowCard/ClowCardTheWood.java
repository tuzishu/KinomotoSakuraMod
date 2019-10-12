package KinomotoSakuraMod.Cards.ClowCard;

import KinomotoSakuraMod.Cards.KSMOD_AbstractMagicCard;
import KinomotoSakuraMod.Patches.KSMOD_CustomCardColor;
import KinomotoSakuraMod.Patches.KSMOD_CustomTag;
import KinomotoSakuraMod.Relics.KSMOD_SealedBook;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

public class ClowCardTheWood extends KSMOD_AbstractMagicCard
{
    public static final String ID = "ClowCardTheWood";
    private static final String NAME;
    private static final String DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION;
    private static final String[] EXTENDED_DESCRIPTION;
    private static final String IMAGE_PATH = "img/cards/clowcard/the_wood.png";
    private static final int COST = 1;
    private static final CardType CARD_TYPE = CardType.SKILL;
    private static final CardColor CARD_COLOR = KSMOD_CustomCardColor.CLOWCARD_COLOR;
    private static final CardRarity CARD_RARITY = CardRarity.COMMON;
    private static final CardTarget CARD_TARGET = CardTarget.ENEMY;
    private static final int BASE_MAGIC_NUMBER = 4;
    private static final int UPGRADE_MAGIC_NUMBER = 2;
    private static final float BASE_POISON_DEEPEN = 0.25F;
    private static final float UPGRADE_POISON_DEEPEN = 0.5F;// 未赋值变量，修改时记得改描述。
    private static final String SOUND_KEY = "POWER_CONSTRICTED";

    static
    {
        CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    }

    public ClowCardTheWood()
    {
        super(ID, NAME, IMAGE_PATH, COST, DESCRIPTION, CARD_TYPE, CARD_COLOR, CARD_RARITY, CARD_TARGET, true);
        this.tags.add(KSMOD_CustomTag.KSMOD_EARTHY_CARD);
        this.setBaseMagicNumber(BASE_MAGIC_NUMBER);
    }

    @Override
    public void upgrade()
    {
        if (!this.upgraded)
        {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_MAGIC_NUMBER);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public KSMOD_AbstractMagicCard makeCopy()
    {
        return new ClowCardTheWood();
    }

    @Override
    public void applyNormalEffect(AbstractPlayer player, AbstractMonster monster)
    {
        AbstractDungeon.actionManager.addToBottom(new SFXAction(SOUND_KEY, 0.05F));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, player, new StrengthPower(monster, -this.magicNumber), -this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, player, new GainStrengthPower(monster, this.magicNumber), this.magicNumber));
        int amount = 0;
        if (upgraded)
        {
            amount = GatherPosion(player, monster);
        }
        if (amount <= 0 && monster.hasPower(PoisonPower.POWER_ID))
        {
            amount = monster.getPower(PoisonPower.POWER_ID).amount;
        }
        amount = MathUtils.ceil(amount * (1.0F + (upgraded ? UPGRADE_POISON_DEEPEN : BASE_POISON_DEEPEN)));
        if (amount > 0)
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, player, new PoisonPower(monster, player, amount), amount));
        }
    }

    public void applyExtraEffect(AbstractPlayer player, AbstractMonster monster)
    {
        applyNormalEffect(player, monster);
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, player, new WeakPower(monster, KSMOD_SealedBook.WEAKENED_NUMBER, false), KSMOD_SealedBook.WEAKENED_NUMBER));
    }

    public String getExtraDescription()
    {
        return this.rawDescription + EXTENDED_DESCRIPTION[0] + KSMOD_SealedBook.WEAKENED_NUMBER + EXTENDED_DESCRIPTION[1];
    }

    private int GatherPosion(AbstractPlayer player, AbstractMonster monster)
    {
        int count = 0;
        for (AbstractMonster mon : AbstractDungeon.getMonsters().monsters)
        {
            if (mon.hasPower(PoisonPower.POWER_ID))
            {
                AbstractPower power = mon.getPower(PoisonPower.POWER_ID);
                count += power.amount;
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(mon, player, power));
            }
        }
        if (player.hasPower(PoisonPower.POWER_ID))
        {
            AbstractPower power = player.getPower(PoisonPower.POWER_ID);
            count += power.amount;
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(player, player, power));
        }
        return count;
    }
}
