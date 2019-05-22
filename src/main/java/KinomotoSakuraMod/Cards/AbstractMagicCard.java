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
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DescriptionLine;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public abstract class AbstractMagicCard extends CustomCard
{
    //////////
    // Override Method Usage
    //////////
    private static final float ENERGY_COST_OFFSET_X = -92;
    private static final float ENERGY_COST_OFFSET_Y = 222;
    private static final Color ENERGY_COST_RESTRICTED_COLOR = new Color(1.0F, 0.3F, 0.3F, 1.0F);
    private static final Color ENERGY_COST_MODIFIED_COLOR = new Color(0.4F, 1.0F, 0.4F, 1.0F);
    private static final float CARD_WIDTH = 220F;
    private static final float CARD_HEIGHT = 500F;
    private static final float DESC_LINE_WIDTH = 160F * Settings.scale;
    private static final float DESC_OFFSET_RATE_X = 0.79F;
    private static final float DESC_OFFSET_RATE_Y = 0.321F;
    private static final float CARD_ENERGY_IMG_WIDTH = 24.0F * Settings.scale;
    private static final float HB_W = CARD_WIDTH * Settings.scale;
    private static final float HB_H = CARD_HEIGHT * Settings.scale;

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
    public void renderEnergy(SpriteBatch sb) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException
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

    private Texture GetFrameImage()
    {
        Texture texture;
        switch (this.rarity)
        {
            case RARE:
                texture = ImageConst.FRAME_RARE;
                break;
            case UNCOMMON:
                texture = ImageConst.FRAME_UNCOMMON;
                break;
            default:
                texture = ImageConst.FRAME_COMMON;
                break;
        }
        return texture;
    }

    @SpireOverride
    public void renderAttackPortrait(SpriteBatch sb, float x, float y) throws NoSuchFieldException, IllegalAccessException
    {
        Field renderColor = Utility.GetFieldByReflect(this, AbstractCard.class, "renderColor");
        sb.setColor((Color) renderColor.get(this));
        sb.draw(GetFrameImage(), x, y, 256.0F, 256.0F, 512.0F, 512.0F, this.drawScale * Settings.scale, this.drawScale * Settings.scale, this.angle, 0, 0, 512, 512, false, false);
    }

    @SpireOverride
    public void renderSkillPortrait(SpriteBatch sb, float x, float y) throws NoSuchFieldException, IllegalAccessException
    {
        Field renderColor = Utility.GetFieldByReflect(this, AbstractCard.class, "renderColor");
        sb.setColor((Color) renderColor.get(this));
        sb.draw(GetFrameImage(), x, y, 256.0F, 256.0F, 512.0F, 512.0F, this.drawScale * Settings.scale, this.drawScale * Settings.scale, this.angle, 0, 0, 512, 512, false, false);
    }

    @SpireOverride
    public void renderPowerPortrait(SpriteBatch sb, float x, float y) throws NoSuchFieldException, IllegalAccessException
    {
        Field renderColor = Utility.GetFieldByReflect(this, AbstractCard.class, "renderColor");
        sb.setColor((Color) renderColor.get(this));
        sb.draw(GetFrameImage(), x, y, 256.0F, 256.0F, 512.0F, 512.0F, this.drawScale * Settings.scale, this.drawScale * Settings.scale, this.angle, 0, 0, 512, 512, false, false);
    }

    private Texture GetBannerImage()
    {
        Texture texture;
        switch (this.rarity)
        {
            case RARE:
                texture = ImageConst.BANNER_RARE;
                break;
            case UNCOMMON:
                texture = ImageConst.BANNER_UNCOMMON;
                break;
            default:
                texture = ImageConst.BANNER_COMMON;
                break;
        }
        return texture;
    }

    @SpireOverride
    public void renderBannerImage(SpriteBatch sb, float drawX, float drawY) throws NoSuchFieldException, IllegalAccessException
    {
        Field renderColor = Utility.GetFieldByReflect(this, AbstractCard.class, "renderColor");
        sb.setColor((Color) renderColor.get(this));
        sb.draw(GetBannerImage(), drawX, drawY, 256.0F, 256.0F, 512.0F, 512.0F, this.drawScale * Settings.scale, this.drawScale * Settings.scale, this.angle, 0, 0, 512, 512, false, false);
        if (this.hb.hovered)
        {
            sb.draw(ImageConst.MASK, drawX, drawY, 256.0F, 256.0F, 512.0F, 512.0F, this.drawScale * Settings.scale, this.drawScale * Settings.scale, this.angle, 0, 0, 512, 512, false, false);
        }
    }

    @SpireOverride
    public void renderDescriptionCN(SpriteBatch sb) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException
    {
        if (!this.hb.hovered)
        {
            return;
        }
        if (this.isSeen && !this.isLocked)
        {
            Method getDescFont = Utility.GetMethodByReflect(this, AbstractCard.class, "getDescFont");
            BitmapFont font = (BitmapFont) getDescFont.invoke(this);
            float draw_y = this.current_y - CARD_HEIGHT * this.drawScale / 2.0F + CARD_HEIGHT * DESC_OFFSET_RATE_Y * this.drawScale;
            draw_y += (float) this.description.size() * font.getCapHeight() * 0.775F - font.getCapHeight() * 0.375F;
            float spacing = 1.45F * -font.getCapHeight() / Settings.scale / this.drawScale;
            GlyphLayout gl = new GlyphLayout();

            for (int i = 0; i < this.description.size(); ++i)
            {
                float start_x = 0.0F;
                if (Settings.leftAlignCards)
                {
                    start_x = this.current_x - CARD_WIDTH * DESC_OFFSET_RATE_X * this.drawScale / 2.0F + 2.0F * Settings.scale;
                }
                else
                {
                    start_x = this.current_x - ((DescriptionLine) this.description.get(i)).width * this.drawScale / 2.0F - 14.0F * Settings.scale;
                }

                String desc = ((DescriptionLine) this.description.get(i)).text;
                String[] var9 = desc.split(" ");
                int var10 = var9.length;

                for (int var11 = 0; var11 < var10; ++var11)
                {
                    String tmp = var9[var11];
                    tmp = tmp.replace("!", "");
                    String updateTmp = null;

                    int j;
                    for (j = 0; j < tmp.length(); ++j)
                    {
                        if (tmp.charAt(j) == 'D' || tmp.charAt(j) == 'B' && !tmp.contains("[B]") || tmp.charAt(j) == 'M')
                        {
                            updateTmp = tmp.substring(0, j);
                            Method getDynamicValue = Utility.GetMethodByReflect(this, AbstractCard.class, "getDynamicValue", char.class);
                            updateTmp = updateTmp + getDynamicValue.invoke(this, tmp.charAt(j));
                            updateTmp = updateTmp + tmp.substring(j + 1);
                            break;
                        }
                    }

                    if (updateTmp != null)
                    {
                        tmp = updateTmp;
                    }

                    for (j = 0; j < tmp.length(); ++j)
                    {
                        if (tmp.charAt(j) == 'D' || tmp.charAt(j) == 'B' && !tmp.contains("[B]") || tmp.charAt(j) == 'M')
                        {
                            updateTmp = tmp.substring(0, j);
                            Method getDynamicValue = Utility.GetMethodByReflect(this, AbstractCard.class, "getDynamicValue", char.class);
                            updateTmp = updateTmp + getDynamicValue.invoke(this, tmp.charAt(j));
                            updateTmp = updateTmp + tmp.substring(j + 1);
                            break;
                        }
                    }

                    if (updateTmp != null)
                    {
                        tmp = updateTmp;
                    }

                    if (tmp.length() > 0 && tmp.charAt(0) == '*')
                    {
                        tmp = tmp.substring(1);
                        String punctuation = "";
                        if (tmp.length() > 1 && !Character.isLetter(tmp.charAt(tmp.length() - 2)))
                        {
                            punctuation = punctuation + tmp.charAt(tmp.length() - 2);
                            tmp = tmp.substring(0, tmp.length() - 2);
                            punctuation = punctuation + ' ';
                        }

                        gl.setText(font, tmp);
                        Color goldColor = (Color) Utility.GetFieldByReflect(this, AbstractCard.class, "goldColor").get(this);
                        FontHelper.renderRotatedText(sb, font, tmp, this.current_x, this.current_y, start_x - this.current_x + gl.width / 2.0F, (float) i * 1.45F * -font.getCapHeight() + draw_y - this.current_y + -6.0F, this.angle, true, goldColor);
                        start_x = (float) Math.round(start_x + gl.width);
                        gl.setText(font, punctuation);
                        Color textColor = (Color) Utility.GetFieldByReflect(this, AbstractCard.class, "textColor").get(this);
                        FontHelper.renderRotatedText(sb, font, punctuation, this.current_x, this.current_y, start_x - this.current_x + gl.width / 2.0F, (float) i * 1.45F * -font.getCapHeight() + draw_y - this.current_y + -6.0F, this.angle, true, textColor);
                        gl.setText(font, punctuation);
                        start_x += gl.width;
                    }
                    else if (tmp.equals("[R]"))
                    {
                        gl.width = CARD_ENERGY_IMG_WIDTH * this.drawScale;
                        this.renderSmallEnergy(sb, orb_red, (start_x - this.current_x) / Settings.scale / this.drawScale, -100.0F - (((float) this.description.size() - 4.0F) / 2.0F - (float) i + 1.0F) * spacing);
                        start_x += gl.width;
                    }
                    else if (tmp.equals("[G]"))
                    {
                        gl.width = CARD_ENERGY_IMG_WIDTH * this.drawScale;
                        this.renderSmallEnergy(sb, orb_green, (start_x - this.current_x) / Settings.scale / this.drawScale, -100.0F - (((float) this.description.size() - 4.0F) / 2.0F - (float) i + 1.0F) * spacing);
                        start_x += gl.width;
                    }
                    else if (tmp.equals("[B]"))
                    {
                        gl.width = CARD_ENERGY_IMG_WIDTH * this.drawScale;
                        this.renderSmallEnergy(sb, orb_blue, (start_x - this.current_x) / Settings.scale / this.drawScale, -100.0F - (((float) this.description.size() - 4.0F) / 2.0F - (float) i + 1.0F) * spacing);
                        start_x += gl.width;
                    }
                    else
                    {
                        gl.setText(font, tmp);
                        Color textColor = (Color) Utility.GetFieldByReflect(this, AbstractCard.class, "textColor").get(this);
                        FontHelper.renderRotatedText(sb, font, tmp, this.current_x, this.current_y, start_x - this.current_x + gl.width / 2.0F, (float) i * 1.45F * -font.getCapHeight() + draw_y - this.current_y + -6.0F, this.angle, true, textColor);
                        start_x += gl.width;
                    }
                }
            }

            font.getData().setScale(1.0F);
        }
        else
        {
            FontHelper.menuBannerFont.getData().setScale(this.drawScale * 1.25F);
            Color textColor = (Color) Utility.GetFieldByReflect(this, AbstractCard.class, "textColor").get(this);
            FontHelper.renderRotatedText(sb, FontHelper.menuBannerFont, "? ? ?", this.current_x, this.current_y, 0.0F, -200.0F * Settings.scale * this.drawScale / 2.0F, this.angle, true, textColor);
            FontHelper.menuBannerFont.getData().setScale(1.0F);
        }
    }

    @Override
    public void initializeDescriptionCN()
    {
        try
        {
            this.description.clear();
            int numLines = 1;
            StringBuilder currentLine = new StringBuilder("");
            float currentWidth = 0.0F;
            String[] var4 = this.rawDescription.split(" ");
            int var5 = var4.length;

            for (int var6 = 0; var6 < var5; ++var6)
            {
                String word = var4[var6];
                word = word.trim();
                if (!word.contains("NL"))
                {
                    String keywordTmp = word.toLowerCase();
                    Method dedupeKeyword = Utility.GetMethodByReflect(this, AbstractCard.class, "dedupeKeyword", String.class);
                    keywordTmp = (String) dedupeKeyword.invoke(this, keywordTmp);
                    GlyphLayout gl;
                    if (GameDictionary.keywords.containsKey(keywordTmp))
                    {
                        if (!this.keywords.contains(keywordTmp))
                        {
                            this.keywords.add(keywordTmp);
                        }

                        gl = new GlyphLayout(FontHelper.cardDescFont_N, word);
                        if (currentWidth + gl.width > DESC_LINE_WIDTH)
                        {
                            ++numLines;
                            this.description.add(new DescriptionLine(currentLine.toString(), currentWidth));
                            currentLine = new StringBuilder("");
                            currentWidth = gl.width;
                            currentLine.append(" *").append(word).append(" ");
                        }
                        else
                        {
                            currentLine.append(" *").append(word).append(" ");
                            currentWidth += gl.width;
                        }
                    }
                    else if (!word.equals("[R]") && !word.equals("[G]") && !word.equals("[B]"))
                    {
                        if (word.equals("!D!"))
                        {
                            gl = new GlyphLayout(FontHelper.cardDescFont_N, word);
                            if (currentWidth + gl.width > DESC_LINE_WIDTH)
                            {
                                ++numLines;
                                this.description.add(new DescriptionLine(currentLine.toString(), currentWidth));
                                currentLine = new StringBuilder("");
                                currentWidth = gl.width;
                                currentLine.append(" D ");
                            }
                            else
                            {
                                currentLine.append(" D ");
                                currentWidth += gl.width;
                            }
                        }
                        else if (word.equals("!B!"))
                        {
                            gl = new GlyphLayout(FontHelper.cardDescFont_N, word);
                            if (currentWidth + gl.width > DESC_LINE_WIDTH)
                            {
                                ++numLines;
                                this.description.add(new DescriptionLine(currentLine.toString(), currentWidth));
                                currentLine = new StringBuilder("");
                                currentWidth = gl.width;
                                currentLine.append(" ").append(word).append("! ");
                            }
                            else
                            {
                                currentLine.append(" ").append(word).append("! ");
                                currentWidth += gl.width;
                            }
                        }
                        else if (word.equals("!M!"))
                        {
                            gl = new GlyphLayout(FontHelper.cardDescFont_N, word);
                            if (currentWidth + gl.width > DESC_LINE_WIDTH)
                            {
                                ++numLines;
                                this.description.add(new DescriptionLine(currentLine.toString(), currentWidth));
                                currentLine = new StringBuilder("");
                                currentWidth = gl.width;
                                currentLine.append(" ").append(word).append("! ");
                            }
                            else
                            {
                                currentLine.append(" ").append(word).append("! ");
                                currentWidth += gl.width;
                            }
                        }
                        else
                        {
                            word = word.trim();
                            char[] var10 = word.toCharArray();
                            int var11 = var10.length;

                            for (int var12 = 0; var12 < var11; ++var12)
                            {
                                char c = var10[var12];
                                gl = new GlyphLayout(FontHelper.cardDescFont_N, Character.toString(c));
                                currentLine.append(c);
                                if (currentWidth + gl.width > DESC_LINE_WIDTH)
                                {
                                    ++numLines;
                                    this.description.add(new DescriptionLine(currentLine.toString(), currentWidth));
                                    currentLine = new StringBuilder("");
                                    currentWidth = gl.width;
                                }
                                else
                                {
                                    currentWidth += gl.width;
                                }
                            }
                        }
                    }
                    else
                    {
                        switch (this.color)
                        {
                            case RED:
                                if (!this.keywords.contains("[R]"))
                                {
                                    this.keywords.add("[R]");
                                }
                                break;
                            case GREEN:
                                if (!this.keywords.contains("[G]"))
                                {
                                    this.keywords.add("[G]");
                                }
                                break;
                            case BLUE:
                                if (!this.keywords.contains("[B]"))
                                {
                                    this.keywords.add("[B]");
                                }
                                break;
                            default:
                                Utility.Logger.info("ERROR: Tried to display an invalid energy type");
                                break;
                        }

                        if (currentWidth + CARD_ENERGY_IMG_WIDTH > DESC_LINE_WIDTH)
                        {
                            ++numLines;
                            this.description.add(new DescriptionLine(currentLine.toString(), currentWidth));
                            currentLine = new StringBuilder("");
                            currentWidth = CARD_ENERGY_IMG_WIDTH;
                            currentLine.append(" ").append(word).append(" ");
                        }
                        else
                        {
                            currentLine.append(" ").append(word).append(" ");
                            currentWidth += CARD_ENERGY_IMG_WIDTH;
                        }
                    }
                }
            }

            String lastLine = currentLine.toString();
            if (!lastLine.isEmpty())
            {
                this.description.add(new DescriptionLine(lastLine, currentWidth));
            }

            if (lastLine.equals("。"))
            {
                ArrayList var10000 = this.description;
                int var10001 = this.description.size() - 2;
                StringBuilder var10004 = new StringBuilder();
                DescriptionLine var10006 = (DescriptionLine) this.description.get(this.description.size() - 2);
                var10000.set(var10001, new DescriptionLine(var10006.text = var10004.append(var10006.text).append("。").toString(), ((DescriptionLine) this.description.get(this.description.size() - 2)).width));
                this.description.remove(this.description.size() - 1);
            }

            if (numLines > 8)
            {
                Utility.Logger.info("WARNING: Card " + this.name + " has lots of text");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public void update()
    {
        try
        {
            Method updateFlashVfx = Utility.GetMethodByReflect(this, AbstractCard.class, "updateFlashVfx");
            updateFlashVfx.invoke(this);
            if (this.hoverTimer != 0.0F)
            {
                this.hoverTimer -= Gdx.graphics.getDeltaTime();
                if (this.hoverTimer < 0.0F)
                {
                    this.hoverTimer = 0.0F;
                }
            }

            if (AbstractDungeon.player != null && AbstractDungeon.player.isDraggingCard && this == AbstractDungeon.player.hoveredCard)
            {
                this.current_x = MathHelper.cardLerpSnap(this.current_x, this.target_x);
                this.current_y = MathHelper.cardLerpSnap(this.current_y, this.target_y);
                if (AbstractDungeon.player.hasRelic("Necronomicon"))
                {
                    if (this.cost >= 2 && this.type == AbstractCard.CardType.ATTACK && AbstractDungeon.player.getRelic("Necronomicon").checkTrigger())
                    {
                        AbstractDungeon.player.getRelic("Necronomicon").beginLongPulse();
                    }
                    else
                    {
                        AbstractDungeon.player.getRelic("Necronomicon").stopPulse();
                    }
                }
            }

            if (Settings.FAST_MODE)
            {
                this.current_x = MathHelper.cardLerpSnap(this.current_x, this.target_x);
                this.current_y = MathHelper.cardLerpSnap(this.current_y, this.target_y);
            }

            this.current_x = MathHelper.cardLerpSnap(this.current_x, this.target_x);
            this.current_y = MathHelper.cardLerpSnap(this.current_y, this.target_y);
            this.hb.move(this.current_x, this.current_y);
            this.hb.resize(HB_W * this.drawScale, HB_H * this.drawScale);
            if (this.hb.clickStarted && this.hb.hovered)
            {
                this.drawScale = MathHelper.cardScaleLerpSnap(this.drawScale, this.targetDrawScale * 0.9F);
                this.drawScale = MathHelper.cardScaleLerpSnap(this.drawScale, this.targetDrawScale * 0.9F);
            }
            else
            {
                this.drawScale = MathHelper.cardScaleLerpSnap(this.drawScale, this.targetDrawScale);
            }

            if (this.angle != this.targetAngle)
            {
                this.angle = MathHelper.angleLerpSnap(this.angle, this.targetAngle);
            }

            Method updateTransparency = Utility.GetMethodByReflect(this, AbstractCard.class, "updateTransparency");
            Method updateColor = Utility.GetMethodByReflect(this, AbstractCard.class, "updateColor");
            updateTransparency.invoke(this);
            updateColor.invoke(this);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
