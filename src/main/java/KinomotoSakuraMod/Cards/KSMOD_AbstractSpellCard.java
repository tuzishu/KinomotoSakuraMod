package KinomotoSakuraMod.Cards;

import KinomotoSakuraMod.Utility.KSMOD_ImageConst;
import KinomotoSakuraMod.Utility.KSMOD_Utility;
import basemod.BaseMod;
import basemod.abstracts.CustomCard;
import basemod.abstracts.DynamicVariable;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DescriptionLine;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class KSMOD_AbstractSpellCard extends CustomCard
{
    //////////
    // Override Method Usage
    //////////
    private static final float ENERGY_COST_OFFSET_X = -90;
    private static final float ENERGY_COST_OFFSET_Y = 222;
    private static final Color ENERGY_COST_RESTRICTED_COLOR = new Color(1.0F, 0.3F, 0.3F, 1.0F);
    private static final Color ENERGY_COST_MODIFIED_COLOR = new Color(0.4F, 1.0F, 0.4F, 1.0F);
    public static final float IMG_WIDTH = 220F * Settings.scale;
    public static final float IMG_HEIGHT = 500F * Settings.scale;
    private static final float DESC_LINE_WIDTH = 190F * Settings.scale;
    private static final float DESC_SCALE_RATE_X = 0.83F;
    private static final float DESC_OFFSET_TO_BOTTOM_Y = 0.35F;
    private static final float CARD_ENERGY_IMG_WIDTH = 24.0F * Settings.scale;
    private static final float HB_W = IMG_WIDTH;
    private static final float HB_H = IMG_HEIGHT;
    private static final float TITLE_HEIGHT_TO_CENTER = 222.0F;
    private static final float TITLE_BOTTOM_HEIGHT_TO_CENTER = -205.0F;
    private static final float PORTRAIT_WIDTH = 151F;
    private static final float PORTRAIT_HEIGHT = 393F;
    private static final float PORTRAIT_ORIGIN_X = 75F;
    private static final float PORTRAIT_ORIGIN_Y = 178F;
    public static boolean isHandSelectScreenOpened = false;
    private String BOTTOM_TITLE = "";

    public KSMOD_AbstractSpellCard(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target)
    {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
    }

    public abstract void upgrade();

    public abstract AbstractCard makeCopy();

    public abstract void use(AbstractPlayer player, AbstractMonster monster);

    public void setBaseMagicNumber(int value)
    {
        this.baseMagicNumber = value;
        this.magicNumber = value;
    }

    //////////
    // Method Override
    //////////

    @Override
    public boolean isHoveredInHand(float scale)
    {
        if (this.hoverTimer > 0.0F)
        {
            return false;
        }
        else
        {
            int x = InputHelper.mX;
            int y = InputHelper.mY;
            return (float) x > this.current_x - IMG_WIDTH * scale / 2.0F && (float) x < this.current_x + IMG_WIDTH * scale / 2.0F && (float) y > this.current_y - IMG_HEIGHT * scale / 2.0F && (float) y < this.current_y + IMG_HEIGHT * scale / 2.0F;
        }
    }

    private Texture GetEnergyImage()
    {
        Texture texture;
        switch (this.type)
        {
            case ATTACK:
                texture = KSMOD_ImageConst.ORB_CLOWCARD_ATTACK;
                break;
            case SKILL:
                texture = KSMOD_ImageConst.ORB_CLOWCARD_SKILL;
                break;
            case POWER:
                texture = KSMOD_ImageConst.ORB_CLOWCARD_POWER;
                break;
            default:
                texture = KSMOD_ImageConst.ORB_CLOWCARD_SKILL;
                break;
        }
        return texture;
    }

    @SpireOverride
    public void renderEnergy(SpriteBatch sb) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException
    {
        boolean darken = KSMOD_Utility.GetFieldByReflect(AbstractCard.class, "darken").getBoolean(this);
        if (this.cost > -2 && !darken && !this.isLocked && this.isSeen)
        {
            Method renderHelper = KSMOD_Utility.GetMethodByReflect(AbstractCard.class, "renderHelper", SpriteBatch.class, Color.class, Texture.class, float.class, float.class);
            Color renderColor = (Color) KSMOD_Utility.GetFieldByReflect(AbstractCard.class, "renderColor").get(this);
            renderHelper.invoke(this, sb, renderColor, GetEnergyImage(), this.current_x - 512F, this.current_y - 512F);

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

            Method getCost = KSMOD_Utility.GetMethodByReflect(AbstractCard.class, "getCost");
            String text = (String) getCost.invoke(this);

            Method getEnergyFont = KSMOD_Utility.GetMethodByReflect(AbstractCard.class, "getEnergyFont");
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
            case COMMON:
                texture = KSMOD_ImageConst.FRAME_CLOWCARD_COMMON;
                break;
            case UNCOMMON:
                texture = KSMOD_ImageConst.FRAME_CLOWCARD_UNCOMMON;
                break;
            default:
                texture = KSMOD_ImageConst.FRAME_CLOWCARD_RARE;
                break;
        }
        return texture;
    }

    private void renderFramePortrait(SpriteBatch sb, float x, float y) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException
    {
        Color renderColor = (Color) KSMOD_Utility.GetFieldByReflect(AbstractCard.class, "renderColor").get(this);
        Method renderHelper = KSMOD_Utility.GetMethodByReflect(AbstractCard.class, "renderHelper", SpriteBatch.class, Color.class, Texture.class, float.class, float.class);
        renderHelper.invoke(this, sb, renderColor, GetFrameImage(), x - 512F, y - 512F);
    }

    @SpireOverride
    public void renderAttackPortrait(SpriteBatch sb, float x, float y) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, NoSuchFieldException
    {
        renderFramePortrait(sb, x, y);
    }

    @SpireOverride
    public void renderSkillPortrait(SpriteBatch sb, float x, float y) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException
    {
        renderFramePortrait(sb, x, y);
    }

    @SpireOverride
    public void renderPowerPortrait(SpriteBatch sb, float x, float y) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException
    {
        renderFramePortrait(sb, x, y);
    }

    private Texture GetBannerImage()
    {
        Texture texture;
        switch (this.rarity)
        {
            case COMMON:
                texture = KSMOD_ImageConst.BANNER_CLOWCARD_COMMON;
                break;
            case UNCOMMON:
                texture = KSMOD_ImageConst.BANNER_CLOWCARD_UNCOMMON;
                break;
            default:
                texture = KSMOD_ImageConst.BANNER_CLOWCARD_RARE;
                break;
        }
        return texture;
    }

    @SpireOverride
    public void renderBannerImage(SpriteBatch sb, float drawX, float drawY) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException
    {
        Color renderColor = (Color) KSMOD_Utility.GetFieldByReflect(AbstractCard.class, "renderColor").get(this);
        Method renderHelper = KSMOD_Utility.GetMethodByReflect(AbstractCard.class, "renderHelper", SpriteBatch.class, Color.class, Texture.class, float.class, float.class);
        renderHelper.invoke(this, sb, renderColor, GetBannerImage(), drawX - 512F, drawY - 512F);
    }

    @SpireOverride
    public void renderDescriptionCN(SpriteBatch sb) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException
    {
        if (AbstractDungeon.player != null && AbstractDungeon.player.hand != null && AbstractDungeon.player.hand.contains(this) && !this.isHandSelectScreenOpened && AbstractDungeon.player.hoveredCard != this)
        {
            return;
        }
        sb.draw(KSMOD_ImageConst.MASK, this.current_x - 256.0F, this.current_y - 256.0F, 256.0F, 256.0F, 512.0F, 512.0F, this.drawScale * Settings.scale, this.drawScale * Settings.scale, this.angle, 0, 0, 512, 512, false, false);
        if (this.isSeen && !this.isLocked)
        {
            Method getDescFont = KSMOD_Utility.GetMethodByReflect(AbstractCard.class, "getDescFont");
            BitmapFont font = (BitmapFont) getDescFont.invoke(this);
            float draw_y = this.current_y - IMG_HEIGHT * this.drawScale / 2.0F + IMG_HEIGHT * DESC_OFFSET_TO_BOTTOM_Y * this.drawScale;
            draw_y += (float) this.description.size() * font.getCapHeight() * 0.775F - font.getCapHeight() * 0.375F;
            float spacing = 1.45F * -font.getCapHeight() / Settings.scale / this.drawScale;

            for (int i = 0; i < this.description.size(); ++i)
            {
                float start_x = 0.0F;
                if (Settings.leftAlignCards)
                {
                    start_x = this.current_x - IMG_WIDTH * DESC_SCALE_RATE_X * this.drawScale / 2.0F + 2.0F * Settings.scale;
                }
                else
                {
                    start_x = this.current_x - this.description.get(i).width * this.drawScale / 2.0F - 14.0F * Settings.scale;
                }

                String[] var7 = this.description.get(i).getCachedTokenizedTextCN();
                int var8 = var7.length;
                Method getDynamicValue = KSMOD_Utility.GetMethodByReflect(AbstractCard.class, "getDynamicValue", char.class);
                GlyphLayout gl = (GlyphLayout) KSMOD_Utility.GetFieldByReflect(AbstractCard.class, "gl").get(this);
                for (int var9 = 0; var9 < var8; ++var9)
                {
                    String tmp = var7[var9];
                    String updateTmp = null;

                    ////// Patch From RenderCustomDynamicVariableCN
                    if (tmp.startsWith("$"))
                    {
                        String key = tmp;
                        Pattern pattern = Pattern.compile("\\$(.+)\\$\\$");
                        Matcher matcher = pattern.matcher(key);
                        if (matcher.find())
                        {
                            key = matcher.group(1);
                        }

                        DynamicVariable dv = (DynamicVariable) BaseMod.cardDynamicVariableMap.get(key);
                        if (dv != null)
                        {
                            if (dv.isModified(this))
                            {
                                if (dv.value(this) >= dv.baseValue(this))
                                {
                                    tmp = "[#" + dv.getIncreasedValueColor().toString() + "]" + Integer.toString(dv.value(this)) + "[]";
                                }
                                else
                                {
                                    tmp = "[#" + dv.getDecreasedValueColor().toString() + "]" + Integer.toString(dv.value(this)) + "[]";
                                }
                            }
                            else
                            {
                                tmp = Integer.toString(dv.baseValue(this));
                            }
                        }
                    }
                    ////// Patch End

                    int j;
                    for (j = 0; j < tmp.length(); ++j)
                    {
                        if (tmp.charAt(j) == 'D' || tmp.charAt(j) == 'B' && !tmp.contains("[B]") || tmp.charAt(j) == 'M')
                        {
                            updateTmp = tmp.substring(0, j);
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
                        Color goldColor = (Color) KSMOD_Utility.GetFieldByReflect(AbstractCard.class, "goldColor").get(this);
                        FontHelper.renderRotatedText(sb, font, tmp, this.current_x, this.current_y, start_x - this.current_x + gl.width / 2.0F, (float) i * 1.45F * -font.getCapHeight() + draw_y - this.current_y + -6.0F, this.angle, true, goldColor);
                        start_x = (float) Math.round(start_x + gl.width);
                        gl.setText(font, punctuation);
                        Color textColor = (Color) KSMOD_Utility.GetFieldByReflect(AbstractCard.class, "textColor").get(this);
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
                        Color textColor = (Color) KSMOD_Utility.GetFieldByReflect(AbstractCard.class, "textColor").get(this);
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
            Color textColor = (Color) KSMOD_Utility.GetFieldByReflect(AbstractCard.class, "textColor").get(this);
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
            StringBuilder sbuilder = (StringBuilder) KSMOD_Utility.GetFieldByReflect(AbstractCard.class, "sbuilder").get(this);
            sbuilder.setLength(0);
            float currentWidth = 0.0F;
            String[] var3 = this.rawDescription.split(" ");
            int var4 = var3.length;

            for (int var5 = 0; var5 < var4; ++var5)
            {
                String word = var3[var5];
                word = word.trim();
                if (Settings.manualLineBreak || !word.contains("NL"))
                {
                    Method dedupeKeyword = KSMOD_Utility.GetMethodByReflect(AbstractCard.class, "dedupeKeyword", String.class);
                    GlyphLayout gl = (GlyphLayout) KSMOD_Utility.GetFieldByReflect(AbstractCard.class, "gl").get(this);
                    String keywordTmp = word.toLowerCase();
                    keywordTmp = (String) dedupeKeyword.invoke(this, keywordTmp);
                    if (GameDictionary.keywords.containsKey(keywordTmp))
                    {
                        if (!this.keywords.contains(keywordTmp))
                        {
                            this.keywords.add(keywordTmp);
                        }

                        gl.setText(FontHelper.cardDescFont_N, word);
                        if (currentWidth + gl.width > DESC_LINE_WIDTH)
                        {
                            ++numLines;
                            this.description.add(new DescriptionLine(sbuilder.toString(), currentWidth));
                            sbuilder.setLength(0);
                            currentWidth = gl.width;
                            sbuilder.append(" *").append(word).append(" ");
                        }
                        else
                        {
                            sbuilder.append(" *").append(word).append(" ");
                            currentWidth += gl.width;
                        }
                    }
                    else if (!word.equals("[R]") && !word.equals("[G]") && !word.equals("[B]"))
                    {
                        if (word.equals("!D!"))
                        {
                            gl.setText(FontHelper.cardDescFont_N, word);
                            if (currentWidth + gl.width > DESC_LINE_WIDTH)
                            {
                                ++numLines;
                                this.description.add(new DescriptionLine(sbuilder.toString(), currentWidth));
                                sbuilder.setLength(0);
                                currentWidth = gl.width;
                                sbuilder.append(" D ");
                            }
                            else
                            {
                                sbuilder.append(" D ");
                                currentWidth += gl.width;
                            }
                        }
                        else if (word.equals("!B!"))
                        {
                            gl.setText(FontHelper.cardDescFont_N, word);
                            if (currentWidth + gl.width > DESC_LINE_WIDTH)
                            {
                                ++numLines;
                                this.description.add(new DescriptionLine(sbuilder.toString(), currentWidth));
                                sbuilder.setLength(0);
                                currentWidth = gl.width;
                                sbuilder.append(" ").append(word).append("! ");
                            }
                            else
                            {
                                sbuilder.append(" ").append(word).append("! ");
                                currentWidth += gl.width;
                            }
                        }
                        else if (word.equals("!M!"))
                        {
                            gl.setText(FontHelper.cardDescFont_N, word);
                            if (currentWidth + gl.width > DESC_LINE_WIDTH)
                            {
                                ++numLines;
                                this.description.add(new DescriptionLine(sbuilder.toString(), currentWidth));
                                sbuilder.setLength(0);
                                currentWidth = gl.width;
                                sbuilder.append(" ").append(word).append("! ");
                            }
                            else
                            {
                                sbuilder.append(" ").append(word).append("! ");
                                currentWidth += gl.width;
                            }
                        }
                        else if (Settings.manualLineBreak && word.equals("NL"))
                        {
                            gl.width = 0.0F;
                            word = "";
                            this.description.add(new DescriptionLine(sbuilder.toString().trim(), currentWidth));
                            currentWidth = 0.0F;
                            ++numLines;
                            sbuilder.setLength(0);
                        }
                        else
                        {
                            word = word.trim();
                            char[] var8 = word.toCharArray();
                            int var9 = var8.length;

                            for (int var10 = 0; var10 < var9; ++var10)
                            {
                                char c = var8[var10];
                                gl.setText(FontHelper.cardDescFont_N, String.valueOf(c));
                                sbuilder.append(c);
                                if (!Settings.manualLineBreak)
                                {
                                    if (currentWidth + gl.width > DESC_LINE_WIDTH)
                                    {
                                        ++numLines;
                                        this.description.add(new DescriptionLine(sbuilder.toString(), currentWidth));
                                        sbuilder.setLength(0);
                                        currentWidth = gl.width;
                                    }
                                    else
                                    {
                                        currentWidth += gl.width;
                                    }
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
                                KSMOD_Utility.Logger.info("ERROR: Tried to display an invalid energy type");
                        }

                        if (currentWidth + CARD_ENERGY_IMG_WIDTH > DESC_LINE_WIDTH)
                        {
                            ++numLines;
                            this.description.add(new DescriptionLine(sbuilder.toString(), currentWidth));
                            sbuilder.setLength(0);
                            currentWidth = CARD_ENERGY_IMG_WIDTH;
                            sbuilder.append(" ").append(word).append(" ");
                        }
                        else
                        {
                            sbuilder.append(" ").append(word).append(" ");
                            currentWidth += CARD_ENERGY_IMG_WIDTH;
                        }
                    }
                }
            }

            if (sbuilder.length() != 0)
            {
                this.description.add(new DescriptionLine(sbuilder.toString(), currentWidth));
            }

            if (sbuilder.toString().equals(LocalizedStrings.PERIOD))
            {
                this.description.set(this.description.size() - 2, new DescriptionLine(((DescriptionLine) this.description.get(this.description.size() - 2)).getText() + LocalizedStrings.PERIOD, ((DescriptionLine) this.description.get(this.description.size() - 2)).width));
                this.description.remove(this.description.size() - 1);
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
            Method updateFlashVfx = KSMOD_Utility.GetMethodByReflect(AbstractCard.class, "updateFlashVfx");
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

            Method updateTransparency = KSMOD_Utility.GetMethodByReflect(AbstractCard.class, "updateTransparency");
            Method updateColor = KSMOD_Utility.GetMethodByReflect(AbstractCard.class, "updateColor");
            updateTransparency.invoke(this);
            updateColor.invoke(this);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @SpireOverride
    public void renderTitle(SpriteBatch sb)
    {
        try
        {
            BitmapFont font;
            Color renderColor = (Color) KSMOD_Utility.GetFieldByReflect(AbstractCard.class, "renderColor").get(this);
            if (this.isLocked)
            {
                font = FontHelper.cardTitleFont;
                font.getData().setScale(this.drawScale);
                FontHelper.renderRotatedText(sb, font, LOCKED_STRING, this.current_x, this.current_y, 0.0F, TITLE_HEIGHT_TO_CENTER * this.drawScale * Settings.scale, this.angle, false, renderColor);
                FontHelper.renderRotatedText(sb, font, LOCKED_STRING, this.current_x, this.current_y, 0.0F, TITLE_BOTTOM_HEIGHT_TO_CENTER * this.drawScale * Settings.scale, this.angle, false, renderColor);
            }
            else if (!this.isSeen)
            {
                font = FontHelper.cardTitleFont;
                font.getData().setScale(this.drawScale);
                FontHelper.renderRotatedText(sb, font, UNKNOWN_STRING, this.current_x, this.current_y, 0.0F, TITLE_HEIGHT_TO_CENTER * this.drawScale * Settings.scale, this.angle, false, renderColor);
                FontHelper.renderRotatedText(sb, font, UNKNOWN_STRING, this.current_x, this.current_y, 0.0F, TITLE_BOTTOM_HEIGHT_TO_CENTER * this.drawScale * Settings.scale, this.angle, false, renderColor);
            }
            else
            {
                boolean useSmallTitleFont = KSMOD_Utility.GetFieldByReflect(AbstractCard.class, "useSmallTitleFont").getBoolean(this);
                if (!useSmallTitleFont)
                {
                    font = FontHelper.cardTitleFont;
                }
                else
                {
                    font = FontHelper.cardTitleFont_small;
                }

                font.getData().setScale(this.drawScale);
                if (this.upgraded)
                {
                    Color color = Settings.GREEN_TEXT_COLOR.cpy();
                    color.a = renderColor.a;
                    FontHelper.renderRotatedText(sb, font, this.name, this.current_x, this.current_y, 0.0F, TITLE_HEIGHT_TO_CENTER * this.drawScale * Settings.scale, this.angle, false, color);
                    FontHelper.renderRotatedText(sb, font, BOTTOM_TITLE, this.current_x, this.current_y, 0.0F, TITLE_BOTTOM_HEIGHT_TO_CENTER * this.drawScale * Settings.scale, this.angle, false, color);
                }
                else
                {
                    FontHelper.renderRotatedText(sb, font, this.name, this.current_x, this.current_y, 0.0F, TITLE_HEIGHT_TO_CENTER * this.drawScale * Settings.scale, this.angle, false, renderColor);
                    FontHelper.renderRotatedText(sb, font, BOTTOM_TITLE, this.current_x, this.current_y, 0.0F, TITLE_BOTTOM_HEIGHT_TO_CENTER * this.drawScale * Settings.scale, this.angle, false, renderColor);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @SpireOverride
    public void renderPortrait(SpriteBatch sb) throws NoSuchFieldException, IllegalAccessException
    {
        float drawX = this.current_x;
        float drawY = this.current_y;
        Texture img = null;
        if (this.portraitImg != null)
        {
            img = this.portraitImg;
        }

        if (!this.isLocked)
        {
            Color renderColor = (Color) KSMOD_Utility.GetFieldByReflect(AbstractCard.class, "renderColor").get(this);
            if (this.portrait != null)
            {
                drawX = this.current_x - PORTRAIT_ORIGIN_X;
                drawY = this.current_y - PORTRAIT_ORIGIN_Y;
                sb.setColor(renderColor);
                sb.draw(this.portrait, drawX, drawY, PORTRAIT_ORIGIN_X, PORTRAIT_ORIGIN_Y, PORTRAIT_WIDTH, PORTRAIT_HEIGHT, this.drawScale * Settings.scale, this.drawScale * Settings.scale, this.angle);
            }
            else if (img != null)
            {
                sb.setColor(renderColor);
                sb.draw(img, drawX, drawY, PORTRAIT_ORIGIN_X, PORTRAIT_ORIGIN_Y, PORTRAIT_WIDTH, PORTRAIT_HEIGHT, this.drawScale * Settings.scale, this.drawScale * Settings.scale, this.angle, 0, 0, (int) PORTRAIT_WIDTH, (int) PORTRAIT_HEIGHT, false, false);
            }
        }
        else
        {
            sb.draw(this.portraitImg, drawX, drawY, PORTRAIT_ORIGIN_X, PORTRAIT_ORIGIN_Y, PORTRAIT_WIDTH, PORTRAIT_HEIGHT, this.drawScale * Settings.scale, this.drawScale * Settings.scale, this.angle, 0, 0, (int) PORTRAIT_WIDTH, (int) PORTRAIT_HEIGHT, false, false);
        }

    }

    @SpireOverride
    public void renderType(SpriteBatch sb)
    {
    }

    @SpireOverride
    public void renderDynamicFrame(SpriteBatch sb, float x, float y, float typeOffset, float typeWidth)
    {
    }

    @Override
    public Texture getCardBg()
    {
        switch (this.type)
        {
            case ATTACK:
            case SKILL:
            case POWER:
                return KSMOD_ImageConst.SILHOUETTE;
            default:
                return null;
        }
    }

    @Override
    public TextureAtlas.AtlasRegion getCardBgAtlas()
    {
        return KSMOD_ImageConst.SILHOUETTE_ATLAS;
    }

    @SpireOverride
    public void renderMainBorder(SpriteBatch sb) throws NoSuchFieldException, IllegalAccessException
    {
        if (this.isGlowing)
        {
            sb.setBlendFunction(770, 1);
            TextureAtlas.AtlasRegion img = KSMOD_ImageConst.SILHOUETTE_ATLAS;
            if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT)
            {
                Color BLUE_BORDER_GLOW_COLOR = (Color) KSMOD_Utility.GetFieldByReflect(AbstractCard.class, "BLUE_BORDER_GLOW_COLOR").get(this);
                sb.setColor(BLUE_BORDER_GLOW_COLOR);
            }
            else
            {
                Color GREEN_BORDER_GLOW_COLOR = (Color) KSMOD_Utility.GetFieldByReflect(AbstractCard.class, "GREEN_BORDER_GLOW_COLOR").get(this);
                sb.setColor(GREEN_BORDER_GLOW_COLOR);
            }

            sb.draw(img, this.current_x + img.offsetX - (float) img.originalWidth / 2.0F, this.current_y + img.offsetY - (float) img.originalWidth / 2.0F, (float) img.originalWidth / 2.0F - img.offsetX, (float) img.originalWidth / 2.0F - img.offsetY, (float) img.packedWidth, (float) img.packedHeight, this.drawScale * Settings.scale * 1.04F, this.drawScale * Settings.scale * 1.03F, this.angle);
        }
    }
}
