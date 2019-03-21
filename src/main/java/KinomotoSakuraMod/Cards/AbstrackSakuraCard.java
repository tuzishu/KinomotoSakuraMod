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

    public int correctDamage()
    {
        if (this.tags.contains(CustomTag.PHYSICS_CARD))
        {
            AbstractPower power = AbstractDungeon.player.getPower(EnhancementMagickPower.POWER_ID);
            int cnt = power != null ? power.amount : 0;
            return (int) Math.floor(this.damage * (1F + cnt * EnhancementMagickPower.CORRECTION_RATE));
        }
        else if (this.tags.contains(CustomTag.ELEMENT_CARD))
        {
            AbstractPower power = AbstractDungeon.player.getPower(ElementMagickPower.POWER_ID);
            int cnt = power != null ? power.amount : 0;
            return (int) Math.floor(this.damage * (1F + cnt * ElementMagickPower.CORRECTION_RATE));
        }
        else
        {
            return this.damage;
        }
    }

    public int correctBlock()
    {
        if (this.tags.contains(CustomTag.PHYSICS_CARD))
        {
            AbstractPower power = AbstractDungeon.player.getPower(EnhancementMagickPower.POWER_ID);
            int cnt = power != null ? power.amount : 0;
            return (int) Math.floor(this.block * (1F + cnt * EnhancementMagickPower.CORRECTION_RATE));
        }
        else if (this.tags.contains(CustomTag.ELEMENT_CARD))
        {
            AbstractPower power = AbstractDungeon.player.getPower(ElementMagickPower.POWER_ID);
            int cnt = power != null ? power.amount : 0;
            return (int) Math.floor(this.block * (1F + cnt * ElementMagickPower.CORRECTION_RATE));
        }
        else
        {
            return this.block;
        }
    }

    public int correctMagicNumber()
    {
        if (this.tags.contains(CustomTag.PHYSICS_CARD))
        {
            AbstractPower power = AbstractDungeon.player.getPower(EnhancementMagickPower.POWER_ID);
            int cnt = power != null ? power.amount : 0;
            return (int) Math.floor(this.magicNumber * (1F + cnt * EnhancementMagickPower.CORRECTION_RATE));
        }
        else if (this.tags.contains(CustomTag.ELEMENT_CARD))
        {
            AbstractPower power = AbstractDungeon.player.getPower(ElementMagickPower.POWER_ID);
            int cnt = power != null ? power.amount : 0;
            return (int) Math.floor(this.magicNumber * (1F + cnt * ElementMagickPower.CORRECTION_RATE));
        }
        else
        {
            return this.magicNumber;
        }
    }
}
