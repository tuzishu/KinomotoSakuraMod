package KinomotoSakuraMod.Cards;

import KinomotoSakuraMod.Patches.CustomTag;
import KinomotoSakuraMod.Powers.ElementMagickPower;
import KinomotoSakuraMod.Powers.EnhancementMagickPower;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public abstract class AbstrackSakuraCard extends CustomCard
{
    public AbstrackSakuraCard(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target)
    {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
    }

    public abstract void upgrade();

    public abstract AbstractClowCard makeCopy();

    public abstract void use(AbstractPlayer player, AbstractMonster monster);

    public void setBaseMagicNumber(int value)
    {
        this.baseMagicNumber = value;
        this.magicNumber = value;
    }

    public int correctDamage()
    {
        return getCorrentValue(this.damage);
    }

    public int correctBlock()
    {
        return getCorrentValue(this.block);
    }

    public int correctMagicNumber()
    {
        return getCorrentValue(this.magicNumber);
    }

    private int getCorrentValue(int value)
    {
        if (this.tags.contains(CustomTag.PHYSICS_CARD))
        {
            AbstractPower power = AbstractDungeon.player.getPower(EnhancementMagickPower.POWER_ID);
            int count = power != null ? power.amount : 0;
            float rate = EnhancementMagickPower.CORRECTION_RATE;
            value = (int) (value * (1F + count * rate));
        }
        if (this.tags.contains(CustomTag.ELEMENT_CARD))
        {
            AbstractPower power = AbstractDungeon.player.getPower(ElementMagickPower.POWER_ID);
            int count = power != null ? power.amount : 0;
            float rate = ElementMagickPower.CORRECTION_RATE;
            value = (int) (value * (1F + count * rate));
        }
        return value;
    }
}
