package KinomotoSakuraMod.Cards;

import KinomotoSakuraMod.Patches.CustomTag;
import KinomotoSakuraMod.Powers.ElementMagickPower;
import KinomotoSakuraMod.Powers.EnhancementMagickPower;
import KinomotoSakuraMod.Utility.ModUtility;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DescriptionLine;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
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

    public void unRelease()
    {
        if (hasReleased)
        {
            hasReleased = false;
        }
    }

    @SpireOverride
    protected void renderHelper(SpriteBatch sb, Color color, Texture img, float drawX, float drawY)
    {
        sb.setColor(color);
        try
        {
            sb.draw(img, drawX, drawY, 256.0F, 256.0F, 512.0F, 512.0F, this.drawScale * Settings.scale, this.drawScale * Settings.scale, this.angle, 0, 0, 512, 512, false, false);
        }
        catch (Exception var7)
        {
            ModUtility.Logger.error("renderHelper line 55: " + var7);
        }
    }

    @SpireOverride
    protected void renderBannerImage(SpriteBatch sb, float drawX, float drawY)
    {
        Color color = Color.WHITE.cpy();
        switch (this.rarity)
        {
            case UNCOMMON:
                this.renderHelper(sb, color, BANNER_UNCOMMON, drawX, drawY);
                return;
            case RARE:
                this.renderHelper(sb, color, BANNER_RARE, drawX, drawY);
            default:
                this.renderHelper(sb, color, BANNER_COMMON, drawX, drawY);
                return;
        }
    }

    private void renderDescriptionCN(SpriteBatch sb) {
    if (this.isSeen && !this.isLocked) {
        BitmapFont font = this.getDescFont();
        float draw_y = this.current_y - IMG_HEIGHT * this.drawScale / 2.0F + DESC_OFFSET_Y * this.drawScale;
        draw_y += (float)this.description.size() * font.getCapHeight() * 0.775F - font.getCapHeight() * 0.375F;
        float spacing = 1.45F * -font.getCapHeight() / Settings.scale / this.drawScale;
        GlyphLayout gl = new GlyphLayout();

        for(int i = 0; i < this.description.size(); ++i) {
            float start_x = 0.0F;
            if (Settings.leftAlignCards) {
                start_x = this.current_x - DESC_BOX_WIDTH * this.drawScale / 2.0F + 2.0F * Settings.scale;
            } else {
                start_x = this.current_x - ((DescriptionLine)this.description.get(i)).width * this.drawScale / 2.0F - 14.0F * Settings.scale;
            }

            String desc = ((DescriptionLine)this.description.get(i)).text;
            String[] var9 = desc.split(" ");
            int var10 = var9.length;

            for(int var11 = 0; var11 < var10; ++var11) {
                String tmp = var9[var11];
                tmp = tmp.replace("!", "");
                String updateTmp = null;

                int j;
                for(j = 0; j < tmp.length(); ++j) {
                    if (tmp.charAt(j) == 'D' || tmp.charAt(j) == 'B' && !tmp.contains("[B]") || tmp.charAt(j) == 'M') {
                        updateTmp = tmp.substring(0, j);
                        updateTmp = updateTmp + this.getDynamicValue(tmp.charAt(j));
                        updateTmp = updateTmp + tmp.substring(j + 1);
                        break;
                    }
                }

                if (updateTmp != null) {
                    tmp = updateTmp;
                }

                for(j = 0; j < tmp.length(); ++j) {
                    if (tmp.charAt(j) == 'D' || tmp.charAt(j) == 'B' && !tmp.contains("[B]") || tmp.charAt(j) == 'M') {
                        updateTmp = tmp.substring(0, j);
                        updateTmp = updateTmp + this.getDynamicValue(tmp.charAt(j));
                        updateTmp = updateTmp + tmp.substring(j + 1);
                        break;
                    }
                }

                if (updateTmp != null) {
                    tmp = updateTmp;
                }

                if (tmp.length() > 0 && tmp.charAt(0) == '*') {
                    tmp = tmp.substring(1);
                    String punctuation = "";
                    if (tmp.length() > 1 && !Character.isLetter(tmp.charAt(tmp.length() - 2))) {
                        punctuation = punctuation + tmp.charAt(tmp.length() - 2);
                        tmp = tmp.substring(0, tmp.length() - 2);
                        punctuation = punctuation + ' ';
                    }

                    gl.setText(font, tmp);
                    FontHelper.renderRotatedText(sb, font, tmp, this.current_x, this.current_y, start_x - this.current_x + gl.width / 2.0F, (float)i * 1.45F * -font.getCapHeight() + draw_y - this.current_y + -6.0F, this.angle, true, this.goldColor);
                    start_x = (float)Math.round(start_x + gl.width);
                    gl.setText(font, punctuation);
                    FontHelper.renderRotatedText(sb, font, punctuation, this.current_x, this.current_y, start_x - this.current_x + gl.width / 2.0F, (float)i * 1.45F * -font.getCapHeight() + draw_y - this.current_y + -6.0F, this.angle, true, this.textColor);
                    gl.setText(font, punctuation);
                    start_x += gl.width;
                } else if (tmp.equals("[R]")) {
                    gl.width = CARD_ENERGY_IMG_WIDTH * this.drawScale;
                    this.renderSmallEnergy(sb, orb_red, (start_x - this.current_x) / Settings.scale / this.drawScale, -100.0F - (((float)this.description.size() - 4.0F) / 2.0F - (float)i + 1.0F) * spacing);
                    start_x += gl.width;
                } else if (tmp.equals("[G]")) {
                    gl.width = CARD_ENERGY_IMG_WIDTH * this.drawScale;
                    this.renderSmallEnergy(sb, orb_green, (start_x - this.current_x) / Settings.scale / this.drawScale, -100.0F - (((float)this.description.size() - 4.0F) / 2.0F - (float)i + 1.0F) * spacing);
                    start_x += gl.width;
                } else if (tmp.equals("[B]")) {
                    gl.width = CARD_ENERGY_IMG_WIDTH * this.drawScale;
                    this.renderSmallEnergy(sb, orb_blue, (start_x - this.current_x) / Settings.scale / this.drawScale, -100.0F - (((float)this.description.size() - 4.0F) / 2.0F - (float)i + 1.0F) * spacing);
                    start_x += gl.width;
                } else {
                    gl.setText(font, tmp);
                    FontHelper.renderRotatedText(sb, font, tmp, this.current_x, this.current_y, start_x - this.current_x + gl.width / 2.0F, (float)i * 1.45F * -font.getCapHeight() + draw_y - this.current_y + -6.0F, this.angle, true, this.textColor);
                    start_x += gl.width;
                }
            }
        }

        font.getData().setScale(1.0F);
    } else {
        FontHelper.menuBannerFont.getData().setScale(this.drawScale * 1.25F);
        FontHelper.renderRotatedText(sb, FontHelper.menuBannerFont, "? ? ?", this.current_x, this.current_y, 0.0F, -200.0F * Settings.scale * this.drawScale / 2.0F, this.angle, true, this.textColor);
        FontHelper.menuBannerFont.getData().setScale(1.0F);
    }
}
}
