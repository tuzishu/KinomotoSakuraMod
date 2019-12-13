package KinomotoSakuraMod.Cards.ClowCard;

import KinomotoSakuraMod.Cards.KSMOD_AbstractMagicCard;
import KinomotoSakuraMod.Patches.KSMOD_CustomCardColor;
import KinomotoSakuraMod.Patches.KSMOD_CustomTag;
import KinomotoSakuraMod.Powers.KSMOD_MagickChargePower;
import KinomotoSakuraMod.Relics.KSMOD_SealedBook;
import KinomotoSakuraMod.Utility.KSMOD_Utility;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

import java.util.ArrayList;

public class ClowCardTheBubbles extends KSMOD_AbstractMagicCard
{
    public static final String ID = "ClowCardTheBubbles";
    private static final String NAME;
    private static final String DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION;
    private static final String[] EXTENDED_DESCRIPTION;
    private static final String IMAGE_PATH = "img/cards/clowcard/the_bubbles.png";
    private static final int COST = 1;
    private static final CardType CARD_TYPE = CardType.ATTACK;
    private static final CardColor CARD_COLOR = KSMOD_CustomCardColor.CLOWCARD_COLOR;
    private static final CardRarity CARD_RARITY = CardRarity.UNCOMMON;
    private static final CardTarget CARD_TARGET = CardTarget.ENEMY;
    private static final int BASE_DAMAGE = 5;
    private static final int BASE_MAGIC_NUMBER = 2;
    private static final ArrayList<String> powerBlackList = new ArrayList<>();

    static
    {
        CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    }

    public ClowCardTheBubbles()
    {
        super(ID, NAME, IMAGE_PATH, COST, DESCRIPTION, CARD_TYPE, CARD_COLOR, CARD_RARITY, CARD_TARGET, true);
        this.tags.add(KSMOD_CustomTag.KSMOD_WATERY_CARD);
        this.baseDamage = BASE_DAMAGE;
        this.setBaseMagicNumber(BASE_MAGIC_NUMBER);
    }

    @Override
    public void upgrade()
    {
        if (!this.upgraded)
        {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public KSMOD_AbstractMagicCard makeCopy()
    {
        return new ClowCardTheBubbles();
    }

    @Override
    public void applyNormalEffect(AbstractPlayer player, AbstractMonster monster)
    {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, new DamageInfo(player, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.POISON));
        if (RemoveRandomBuff(player, monster) && player.hasRelic(KSMOD_SealedBook.RELIC_ID))
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new KSMOD_MagickChargePower(player, this.magicNumber), this.magicNumber));
        }
    }

    @Override
    public void applyExtraEffect(AbstractPlayer player, AbstractMonster monster)
    {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, new DamageInfo(player, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.POISON));
        if (RemoveAllBuffs(player, monster) && player.hasRelic(KSMOD_SealedBook.RELIC_ID))
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new KSMOD_MagickChargePower(player, this.magicNumber), this.magicNumber));
        }
    }

    @Override
    public String getExtraDescription()
    {
        return upgraded ? EXTENDED_DESCRIPTION[0] : EXTENDED_DESCRIPTION[1];
    }

    private boolean RemoveRandomBuff(AbstractPlayer player, AbstractMonster monster)
    {
        boolean hasbuffremoved = false;
        ArrayList<AbstractPower> buffs = new ArrayList<>();
        for (AbstractPower power : monster.powers)
        {
            if (power.type == AbstractPower.PowerType.BUFF && !KSMOD_Utility.IsStringListContains(GetUnremovablePowerList(), power.ID))
            {
                buffs.add(power);
            }
        }
        if (buffs.size() > 0)
        {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(monster, player, KSMOD_Utility.GetRandomListElement(buffs)));
            hasbuffremoved = true;
        }
        return hasbuffremoved;
    }

    private boolean RemoveAllBuffs(AbstractPlayer player, AbstractMonster monster)
    {
        boolean hasbuffremoved = false;
        ArrayList<AbstractPower> buffs = new ArrayList<>();
        for (AbstractPower power : monster.powers)
        {
            if (power.type == AbstractPower.PowerType.BUFF && !KSMOD_Utility.IsStringListContains(GetUnremovablePowerList(), power.ID))
            {
                buffs.add(power);
            }
        }
        if (buffs.size() > 0)
        {
            while (buffs.size() > 0)
            {
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(monster, player, buffs.get(0)));
                buffs.remove(buffs.get(0));
                hasbuffremoved = true;
            }
        }
        return hasbuffremoved;
    }

    public static ArrayList<String> GetUnremovablePowerList()
    {
        if (powerBlackList.size() == 0)
        {
            powerBlackList.add(ModeShiftPower.POWER_ID);
            powerBlackList.add(RegrowPower.POWER_ID);
            powerBlackList.add(FadingPower.POWER_ID);
            powerBlackList.add(ShiftingPower.POWER_ID);
            powerBlackList.add(UnawakenedPower.POWER_ID);
            powerBlackList.add(InvinciblePower.POWER_ID);
        }
        return powerBlackList;
    }
}
