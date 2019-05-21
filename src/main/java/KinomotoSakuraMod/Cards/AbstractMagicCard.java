package KinomotoSakuraMod.Cards;

import KinomotoSakuraMod.Patches.CustomTag;
import KinomotoSakuraMod.Powers.ElementMagickPower;
import KinomotoSakuraMod.Powers.EnhancementMagickPower;
import KinomotoSakuraMod.Utility.ImageConst;
import KinomotoSakuraMod.Utility.Utility;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public abstract class AbstractMagicCard extends CustomCard
{
    //////////
    // Override Method Usage
    //////////
    private static final float ENERGY_COST_OFFSET_X = -92;
    private static final float ENERGY_COST_OFFSET_Y = 222;
    private static final Color ENERGY_COST_RESTRICTED_COLOR = new Color(1.0F, 0.3F, 0.3F, 1.0F);
    private static final Color ENERGY_COST_MODIFIED_COLOR = new Color(0.4F, 1.0F, 0.4F, 1.0F);

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

    public int getCorrentValue(int value)
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

    //////////
    // Method Override
    //////////

    @Override
    public void updateHoverLogic()
    {
        try
        {
            Field hoverDuration = Utility.GetFieldByReflect(this, AbstractCard.class, "hoverDuration");
            Field renderTip = Utility.GetFieldByReflect(this, AbstractCard.class, "renderTip");
            boolean justHovered = this.hb.hovered;
            boolean justUnhovered = false;
            this.hb.update();
            if (this.hb.hovered)
            {
                this.hover();
                hoverDuration.setFloat(this, hoverDuration.getFloat(this) + Gdx.graphics.getDeltaTime());
                if (hoverDuration.getFloat(this) > 0.2F && !Settings.hideCards)
                {
                    renderTip.setBoolean(this, true);
                }
            }
            else
            {
                this.unhover();
                if (justHovered)
                {
                    justUnhovered = true;
                }
            }

            if (this.hb.justHovered)
            {
                this.initializeDescription();
            }
            if (justUnhovered)
            {
                this.description.clear();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @SpireOverride
    public void renderEnergy(SpriteBatch sb)
    {
        try
        {
            boolean darken = Utility.GetFieldByReflect(this, AbstractCard.class, "darken").getBoolean(this);
            if (this.cost > -2 && !darken && !this.isLocked && this.isSeen)
            {
                float drawX = this.current_x - 256.0F;
                float drawY = this.current_y - 256.0F;

                Method renderHelper = Utility.GetMethodByReflect(this, AbstractCard.class, "renderHelper", SpriteBatch.class, Color.class, Texture.class, float.class, float.class);
                Color renderColor = (Color) Utility.GetFieldByReflect(this, AbstractCard.class, "renderColor").get(this);
                renderHelper.invoke(this, sb, renderColor, ImageConst.ORB, drawX, drawY);

                Color costColor = Color.WHITE.cpy();
                if (AbstractDungeon.player != null && AbstractDungeon.player.hand.contains(this) && !this.hasEnoughEnergy())
                {
                    costColor = ENERGY_COST_RESTRICTED_COLOR;
                }
                else if (this.isCostModified || this.isCostModifiedForTurn || this.freeToPlayOnce)
                {
                    costColor = ENERGY_COST_MODIFIED_COLOR;
                }

                costColor.a = this.transparency;

                Method getCost = Utility.GetMethodByReflect(this, AbstractCard.class, "getCost");
                String text = (String) getCost.invoke(this);

                Method getEnergyFont = Utility.GetMethodByReflect(this, AbstractCard.class, "getEnergyFont");
                BitmapFont font = (BitmapFont) getEnergyFont.invoke(this);

                if ((this.type != AbstractCard.CardType.STATUS || this.cardID.equals("Slimed")) && (this.color != AbstractCard.CardColor.CURSE || this.cardID.equals("Pride")))
                {
                    FontHelper.renderRotatedText(sb, font, text, this.current_x, this.current_y, ENERGY_COST_OFFSET_X * this.drawScale * Settings.scale, ENERGY_COST_OFFSET_Y * this.drawScale * Settings.scale, this.angle, false, costColor);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
