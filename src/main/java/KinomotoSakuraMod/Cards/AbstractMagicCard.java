package KinomotoSakuraMod.Cards;

import KinomotoSakuraMod.Patches.CustomTag;
import KinomotoSakuraMod.Powers.ElementMagickPower;
import KinomotoSakuraMod.Powers.EnhancementMagickPower;
import KinomotoSakuraMod.Utility.ModUtility;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.lang.reflect.Field;

public abstract class AbstractMagicCard extends CustomCard
{
    private boolean hasReleased = false;
    private float releaseRate = 0F;

    public AbstractMagicCard(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target)
    {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
    }

    public AbstractMagicCard(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target, AbstractCard.CardTags tag)
    {
        this(id, name, img, cost, rawDescription, type, color, rarity, target);
        this.tags.add(tag);
    }

    public abstract void upgrade();

    public abstract AbstractMagicCard makeCopy();

    public abstract void use(AbstractPlayer player, AbstractMonster monster);

    public void triggerOnExhaust()
    {
        this.hasReleased = false;
    }

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
            value = (int) (value * (1F + count * rate) * (1F + (hasReleased ? releaseRate : 0F)));
        }
        if (this.tags.contains(CustomTag.ELEMENT_CARD))
        {
            AbstractPower power = AbstractDungeon.player.getPower(ElementMagickPower.POWER_ID);
            int count = power != null ? power.amount : 0;
            float rate = ElementMagickPower.CORRECTION_RATE;
            value = (int) (value * (1F + count * rate) * (1F + (hasReleased ? releaseRate : 0F)));
        }
        return value;
    }

    public void release(float releaseRate)
    {
        if (!hasReleased)
        {
            this.releaseRate = releaseRate;
            hasReleased = true;
        }
    }
}
