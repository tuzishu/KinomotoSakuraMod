package KinomotoSakuraMod.Cards;

import KinomotoSakuraMod.Patches.CustomTag;
import KinomotoSakuraMod.Powers.ElementMagickPower;
import KinomotoSakuraMod.Powers.EnhancementMagickPower;
import KinomotoSakuraMod.Utility.ModUtility;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static com.megacrit.cardcrawl.helpers.ImageMaster.loadImage;

public abstract class AbstractMagicCard extends CustomCard
{
    private static final String BANNER_COMMON_IMAGE_PATH = "img/banner/common.png";
    private static final String BANNER_UNCOMMON_IMAGE_PATH = "img/banner/uncommon.png";
    private static final String BANNER_RARE_IMAGE_PATH = "img/banner/rare.png";
    private static final Texture BANNER_COMMON;
    private static final Texture BANNER_UNCOMMON;
    private static final Texture BANNER_RARE;
    private boolean hasReleased = false;
    private float releaseRate = 0F;

    static
    {
        BANNER_COMMON = loadImage(BANNER_COMMON_IMAGE_PATH);
        BANNER_UNCOMMON = loadImage(BANNER_UNCOMMON_IMAGE_PATH);
        BANNER_RARE = loadImage(BANNER_RARE_IMAGE_PATH);
    }

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

    // @SpireOverride
    // protected void renderHelper(SpriteBatch sb, Color color, Texture img, float drawX, float drawY)
    // {
    //     sb.setColor(color);
    //     try
    //     {
    //         sb.draw(img, drawX, drawY, 256.0F, 256.0F, 512.0F, 512.0F, this.drawScale * Settings.scale, this.drawScale * Settings.scale, this.angle, 0, 0, 512, 512, false, false);
    //     }
    //     catch (Exception var7)
    //     {
    //         ModUtility.Logger.error("renderHelper line 55: " + var7);
    //     }
    // }
    //
    // @SpireOverride
    // protected void renderBannerImage(SpriteBatch sb, float drawX, float drawY)
    // {
    //     Color color = Color.WHITE.cpy();
    //     switch (this.rarity)
    //     {
    //         case UNCOMMON:
    //             this.renderHelper(sb, color, BANNER_UNCOMMON, drawX, drawY);
    //             return;
    //         case RARE:
    //             this.renderHelper(sb, color, BANNER_RARE, drawX, drawY);
    //         default:
    //             this.renderHelper(sb, color, BANNER_COMMON, drawX, drawY);
    //             return;
    //     }
    // }
}
