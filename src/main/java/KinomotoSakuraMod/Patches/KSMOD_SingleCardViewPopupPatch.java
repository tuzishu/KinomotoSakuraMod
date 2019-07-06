package KinomotoSakuraMod.Patches;

import KinomotoSakuraMod.Cards.KSMOD_AbstractMagicCard;
import KinomotoSakuraMod.Utility.KSMOD_ImageConst;
import KinomotoSakuraMod.Utility.KSMOD_Utility;
import basemod.BaseMod;
import basemod.abstracts.DynamicVariable;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KSMOD_SingleCardViewPopupPatch
{
    private static final float ENERGY_COST_OFFSET_X = -180F;
    private static final float ENERGY_COST_OFFSET_Y = 444F;
    private static final float ENERGY_ICON_WIDTH = 164F;
    private static final float IMG_WIDTH = 440F * Settings.scale;
    private static final float IMG_HEIGHT = 1000F * Settings.scale;
    private static final float HB_W = IMG_WIDTH;
    private static final float HB_H = IMG_HEIGHT;
    private static final float TITLE_HEIGHT_TO_CENTER = 444.0F;
    private static final float TITLE_BOTTOM_HEIGHT_TO_CENTER = -410.0F;
    private static final float PORTRAIT_WIDTH = 303F;
    private static final float PORTRAIT_HEIGHT = 786F;
    private static final float PORTRAIT_ORIGIN_X = 151F;
    private static final float PORTRAIT_ORIGIN_Y = 356F;
    private static final String[] TEXT;
    private static final float DESC_X = Settings.WIDTH * 0.05F;
    private static final float DESC_Y = Settings.HEIGHT * 0.9F;
    private static final float TOGGLE_X = Settings.WIDTH * 0.15F;
    private static final float TOGGLE_Y = Settings.HEIGHT * 0.1F;

    static
    {
        UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("SingleCardViewPopup");
        TEXT = uiStrings.TEXT;
    }

    public static boolean IsKSCard(AbstractCard card)
    {
        return card.color == KSMOD_CustomCardColor.CLOWCARD_COLOR || card.color == KSMOD_CustomCardColor.SAKURACARD_COLOR || card.color == KSMOD_CustomCardColor.SPELL_COLOR;
    }

    @SpirePatch(clz = SingleCardViewPopup.class, method = "renderCardBack", paramtypez = {SpriteBatch.class})
    public static class renderCardBack
    {
        public static SpireReturn<Object> Prefix(SingleCardViewPopup view, SpriteBatch sb) throws NoSuchFieldException, IllegalAccessException
        {
            AbstractCard card = (AbstractCard) KSMOD_Utility.GetFieldByReflect(SingleCardViewPopup.class, "card").get(view);
            if (IsKSCard(card))
            {
                Texture img = null;
                if (card.color == KSMOD_CustomCardColor.CLOWCARD_COLOR)
                {
                    img = KSMOD_ImageConst.CLOWCARD_BG_LARGE;
                }
                else if (card.color == KSMOD_CustomCardColor.SAKURACARD_COLOR)
                {
                    img = KSMOD_ImageConst.SAKURACARD_BG_LARGE;
                }
                else if (card.color == KSMOD_CustomCardColor.SPELL_COLOR)
                {
                    img = KSMOD_ImageConst.SPELLCARD_BG_LARGE;
                }

                if (img != null)
                {
                    sb.draw(img, Settings.WIDTH * 0.5F - 512.0F, Settings.HEIGHT * 0.5F - 512.0F, 512.0F, 512.0F, 1024.0F, 1024.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 1024, 1024, false, false);
                }
                return SpireReturn.Return(null);
            }
            else
            {
                return SpireReturn.Continue();
            }
        }
    }

    @SpirePatch(clz = SingleCardViewPopup.class, method = "renderPortrait", paramtypez = {SpriteBatch.class})
    public static class renderPortrait
    {
        public static SpireReturn<Object> Prefix(SingleCardViewPopup view, SpriteBatch sb) throws NoSuchFieldException, IllegalAccessException
        {
            AbstractCard card = (AbstractCard) KSMOD_Utility.GetFieldByReflect(SingleCardViewPopup.class, "card").get(view);
            if (IsKSCard(card))
            {
                Texture portraitImg = (Texture) KSMOD_Utility.GetFieldByReflect(SingleCardViewPopup.class, "portraitImg").get(view);
                sb.draw(portraitImg, Settings.WIDTH * 0.5F - PORTRAIT_ORIGIN_X, Settings.HEIGHT * 0.5F - PORTRAIT_ORIGIN_Y, PORTRAIT_ORIGIN_X, PORTRAIT_ORIGIN_Y, PORTRAIT_WIDTH, PORTRAIT_HEIGHT, Settings.scale, Settings.scale, 0.0F, 0, 0, (int) PORTRAIT_WIDTH, (int) PORTRAIT_HEIGHT, false, false);
                return SpireReturn.Return(null);
            }
            else
            {
                return SpireReturn.Continue();
            }
        }
    }

    @SpirePatch(clz = SingleCardViewPopup.class, method = "renderFrame", paramtypez = {SpriteBatch.class})
    public static class renderFrame
    {
        private static Texture GetFrameImage(AbstractCard card)
        {
            Texture texture;
            if (card.color == KSMOD_CustomCardColor.CLOWCARD_COLOR)
            {
                switch (card.rarity)
                {
                    case RARE:
                        texture = KSMOD_ImageConst.FRAME_CLOWCARD_RARE_LARGE;
                        break;
                    case UNCOMMON:
                        texture = KSMOD_ImageConst.FRAME_CLOWCARD_UNCOMMON_LARGE;
                        break;
                    default:
                        texture = KSMOD_ImageConst.FRAME_CLOWCARD_COMMON_LARGE;
                        break;
                }
            }
            else
            {
                switch (card.rarity)
                {
                    case RARE:
                        texture = KSMOD_ImageConst.FRAME_SAKURACARD_RARE_LARGE;
                        break;
                    case UNCOMMON:
                        texture = KSMOD_ImageConst.FRAME_SAKURACARD_UNCOMMON_LARGE;
                        break;
                    default:
                        texture = KSMOD_ImageConst.FRAME_SAKURACARD_COMMON_LARGE;
                        break;
                }
            }
            return texture;
        }

        public static SpireReturn<Object> Prefix(SingleCardViewPopup view, SpriteBatch sb) throws NoSuchFieldException, IllegalAccessException
        {
            AbstractCard card = (AbstractCard) KSMOD_Utility.GetFieldByReflect(SingleCardViewPopup.class, "card").get(view);
            if (IsKSCard(card))
            {
                sb.draw(GetFrameImage(card), Settings.WIDTH * 0.5F - 512.0F, Settings.HEIGHT * 0.5F - 512.0F, 512.0F, 512.0F, 1024.0F, 1024.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 1024, 1024, false, false);
                return SpireReturn.Return(null);
            }
            else
            {
                return SpireReturn.Continue();
            }
        }
    }

    @SpirePatch(clz = SingleCardViewPopup.class, method = "renderCardBanner", paramtypez = {SpriteBatch.class})
    public static class renderCardBanner
    {
        private static Texture GetBannerImage(AbstractCard card)
        {
            Texture texture;
            if (card.color == KSMOD_CustomCardColor.CLOWCARD_COLOR)
            {
                switch (card.rarity)
                {
                    case RARE:
                        texture = KSMOD_ImageConst.BANNER_CLOWCARD_RARE_LARGE;
                        break;
                    case UNCOMMON:
                        texture = KSMOD_ImageConst.BANNER_CLOWCARD_UNCOMMON_LARGE;
                        break;
                    default:
                        texture = KSMOD_ImageConst.BANNER_CLOWCARD_COMMON_LARGE;
                }
            }
            else
            {
                switch (card.rarity)
                {
                    case RARE:
                        texture = KSMOD_ImageConst.BANNER_SAKURACARD_RARE_LARGE;
                        break;
                    case UNCOMMON:
                        texture = KSMOD_ImageConst.BANNER_SAKURACARD_UNCOMMON_LARGE;
                        break;
                    default:
                        texture = KSMOD_ImageConst.BANNER_SAKURACARD_COMMON_LARGE;
                }
            }
            return texture;
        }

        public static SpireReturn<Object> Prefix(SingleCardViewPopup view, SpriteBatch sb) throws NoSuchFieldException, IllegalAccessException
        {
            AbstractCard card = (AbstractCard) KSMOD_Utility.GetFieldByReflect(SingleCardViewPopup.class, "card").get(view);
            if (IsKSCard(card))
            {
                sb.draw(GetBannerImage(card), Settings.WIDTH * 0.5F - 512.0F, Settings.HEIGHT * 0.5F - 512.0F, 512.0F, 512.0F, 1024.0F, 1024.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 1024, 1024, false, false);
                return SpireReturn.Return(null);
            }
            else
            {
                return SpireReturn.Continue();
            }
        }
    }

    @SpirePatch(clz = SingleCardViewPopup.class, method = "renderCardTypeText", paramtypez = {SpriteBatch.class})
    public static class renderCardTypeText
    {
        public static SpireReturn<Object> Prefix(SingleCardViewPopup view, SpriteBatch sb) throws NoSuchFieldException, IllegalAccessException
        {
            AbstractCard card = (AbstractCard) KSMOD_Utility.GetFieldByReflect(SingleCardViewPopup.class, "card").get(view);
            if (IsKSCard(card))
            {
                return SpireReturn.Return(null);
            }
            else
            {
                return SpireReturn.Continue();
            }
        }
    }

    @SpirePatch(clz = SingleCardViewPopup.class, method = "renderDescriptionCN", paramtypez = {SpriteBatch.class})
    public static class renderDescriptionCN
    {
        public static SpireReturn<Object> Prefix(SingleCardViewPopup view, SpriteBatch sb) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException
        {
            AbstractCard card = (AbstractCard) KSMOD_Utility.GetFieldByReflect(SingleCardViewPopup.class, "card").get(view);
            if (IsKSCard(card))
            {
                if (!card.isLocked && card.isSeen)
                {
                    BitmapFont font = FontHelper.SCP_cardDescFont;
                    float draw_y = DESC_Y;
                    float spacing = 1.53F * -font.getCapHeight() / Settings.scale / card.drawScale;
                    GlyphLayout gl = new GlyphLayout();
                    for (int i = 0; i < card.description.size(); ++i)
                    {
                        float start_x = DESC_X;
                        Method getDynamicValue = KSMOD_Utility.GetMethodByReflect(SingleCardViewPopup.class, "getDynamicValue", char.class);
                        String[] var8 = card.description.get(i).getCachedTokenizedTextCN();
                        int var9 = var8.length;
                        for (int var10 = 0; var10 < var9; ++var10)
                        {
                            String tmp = var8[var10];
                            tmp = tmp.replace("!", "");
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
                                    if (dv.isModified(card))
                                    {
                                        if (dv.value(card) >= dv.baseValue(card))
                                        {
                                            tmp = "[#" + dv.getIncreasedValueColor().toString() + "]" + Integer.toString(dv.value(card)) + "[]";
                                        }
                                        else
                                        {
                                            tmp = "[#" + dv.getDecreasedValueColor().toString() + "]" + Integer.toString(dv.value(card)) + "[]";
                                        }
                                    }
                                    else
                                    {
                                        tmp = Integer.toString(dv.baseValue(card));
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
                                    updateTmp = updateTmp + getDynamicValue.invoke(view, tmp.charAt(j));
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
                                    updateTmp = updateTmp + getDynamicValue.invoke(view, tmp.charAt(j));
                                    updateTmp = updateTmp + tmp.substring(j + 1);
                                    break;
                                }
                            }
                            if (updateTmp != null)
                            {
                                tmp = updateTmp;
                            }
                            float card_energy_w = KSMOD_Utility.GetFieldByReflect(SingleCardViewPopup.class, "card_energy_w").getFloat(view);
                            Method renderSmallEnergy = KSMOD_Utility.GetMethodByReflect(SingleCardViewPopup.class, "renderSmallEnergy", SpriteBatch.class, TextureAtlas.AtlasRegion.class, float.class, float.class);
                            if (tmp.charAt(0) == '*')
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
                                FontHelper.renderRotatedText(sb, font, tmp, card.current_x, card.current_y, start_x - card.current_x + gl.width * 0.5F, i * 1.53F * -font.getCapHeight() + draw_y - card.current_y + -12.0F, 0.0F, true, Settings.GOLD_COLOR.cpy());
                                start_x = (float) Math.round(start_x + gl.width);
                                gl.setText(font, punctuation);
                                FontHelper.renderRotatedText(sb, font, punctuation, card.current_x, card.current_y, start_x - card.current_x + gl.width * 0.5F, i * 1.53F * -font.getCapHeight() + draw_y - card.current_y + -12.0F, 0.0F, true, Settings.CREAM_COLOR.cpy());
                                gl.setText(font, punctuation);
                                start_x += gl.width;
                            }
                            else if (tmp.equals("[R]"))
                            {
                                gl.width = card_energy_w * card.drawScale;
                                renderSmallEnergy.invoke(view, sb, AbstractCard.orb_red, (start_x - card.current_x) / Settings.scale / card.drawScale, -87.0F - ((card.description.size() - 4.0F) * 0.5F - i + 1.0F) * spacing);
                                start_x += gl.width;
                            }
                            else if (tmp.equals("[G]"))
                            {
                                gl.width = card_energy_w * card.drawScale;
                                renderSmallEnergy.invoke(view, sb, AbstractCard.orb_green, (start_x - card.current_x) / Settings.scale / card.drawScale, -87.0F - ((card.description.size() - 4.0F) * 0.5F - i + 1.0F) * spacing);
                                start_x += gl.width;
                            }
                            else if (tmp.equals("[B]"))
                            {
                                gl.width = card_energy_w * card.drawScale;
                                renderSmallEnergy.invoke(view, sb, AbstractCard.orb_blue, (start_x - card.current_x) / Settings.scale / card.drawScale, -87.0F - ((card.description.size() - 4.0F) * 0.5F - i + 1.0F) * spacing);
                                start_x += gl.width;
                            }
                            else
                            {
                                gl.setText(font, tmp);
                                FontHelper.renderRotatedText(sb, font, tmp, card.current_x, card.current_y, start_x - card.current_x + gl.width * 0.5F, i * 1.53F * -font.getCapHeight() + draw_y - card.current_y + -12.0F, 0.0F, true, Settings.CREAM_COLOR.cpy());
                                start_x += gl.width;
                            }
                        }
                    }
                    font.getData().setScale(1.0F);
                }
                else
                {
                    FontHelper.renderFontCentered(sb, FontHelper.largeCardFont, "? ? ?", Settings.WIDTH * 0.5F, Settings.HEIGHT * 0.5F - 195.0F * Settings.scale, Settings.CREAM_COLOR.cpy());
                }
                return SpireReturn.Return(null);
            }
            else
            {
                return SpireReturn.Continue();
            }
        }
    }

    @SpirePatch(clz = SingleCardViewPopup.class, method = "renderTitle", paramtypez = {SpriteBatch.class})
    public static class renderTitle
    {
        public static boolean hasInit = false;

        public static SpireReturn<Object> Prefix(SingleCardViewPopup view, SpriteBatch sb) throws NoSuchFieldException, IllegalAccessException, InvocationTargetException, NoSuchMethodException
        {
            AbstractCard card = (AbstractCard) KSMOD_Utility.GetFieldByReflect(SingleCardViewPopup.class, "card").get(view);
            if (IsKSCard(card) && hasInit)
            {
                Method allowUpgradePreview = KSMOD_Utility.GetMethodByReflect(SingleCardViewPopup.class, "allowUpgradePreview");
                if (card.isLocked)
                {
                    FontHelper.renderFontCentered(sb, FontHelper.SCP_cardTitleFont_small, TEXT[4], Settings.WIDTH * 0.5F, Settings.HEIGHT * 0.5F + TITLE_HEIGHT_TO_CENTER * Settings.scale, Settings.CREAM_COLOR.cpy());
                }
                else if (card.isSeen)
                {
                    if (SingleCardViewPopup.isViewingUpgrade && !((Boolean) allowUpgradePreview.invoke(view)))
                    {
                        FontHelper.renderFontCentered(sb, FontHelper.SCP_cardTitleFont_small, card.name, Settings.WIDTH * 0.5F, Settings.HEIGHT * 0.5F + TITLE_HEIGHT_TO_CENTER * Settings.scale, Settings.GREEN_TEXT_COLOR.cpy());
                    }
                    else
                    {
                        FontHelper.renderFontCentered(sb, FontHelper.SCP_cardTitleFont_small, card.name, Settings.WIDTH * 0.5F, Settings.HEIGHT * 0.5F + TITLE_HEIGHT_TO_CENTER * Settings.scale, Settings.CREAM_COLOR.cpy());
                    }
                }
                else
                {
                    FontHelper.renderFontCentered(sb, FontHelper.SCP_cardTitleFont_small, TEXT[5], Settings.WIDTH * 0.5F, Settings.HEIGHT * 0.5F + TITLE_HEIGHT_TO_CENTER * Settings.scale, Settings.CREAM_COLOR.cpy());
                }
                if (card.color != KSMOD_CustomCardColor.SPELL_COLOR)
                {
                    if (card.isLocked)
                    {
                        FontHelper.renderFontCentered(sb, FontHelper.SCP_cardTitleFont_small, TEXT[4], Settings.WIDTH * 0.5F, Settings.HEIGHT * 0.5F + TITLE_BOTTOM_HEIGHT_TO_CENTER * Settings.scale, Settings.CREAM_COLOR.cpy());
                    }
                    else if (card.isSeen)
                    {
                        String BOTTOM_TITLE = (String) KSMOD_Utility.GetFieldByReflect(KSMOD_AbstractMagicCard.class, "BOTTOM_TITLE").get(card);
                        if (SingleCardViewPopup.isViewingUpgrade && !((Boolean) allowUpgradePreview.invoke(view)))
                        {
                            FontHelper.renderFontCentered(sb, FontHelper.SCP_cardTitleFont_small, BOTTOM_TITLE, Settings.WIDTH * 0.5F, Settings.HEIGHT * 0.5F + TITLE_BOTTOM_HEIGHT_TO_CENTER * Settings.scale, Settings.GREEN_TEXT_COLOR.cpy());
                        }
                        else
                        {
                            FontHelper.renderFontCentered(sb, FontHelper.SCP_cardTitleFont_small, BOTTOM_TITLE, Settings.WIDTH * 0.5F, Settings.HEIGHT * 0.5F + TITLE_BOTTOM_HEIGHT_TO_CENTER * Settings.scale, Settings.CREAM_COLOR.cpy());
                        }
                    }
                    else
                    {
                        FontHelper.renderFontCentered(sb, FontHelper.SCP_cardTitleFont_small, TEXT[5], Settings.WIDTH * 0.5F, Settings.HEIGHT * 0.5F + TITLE_BOTTOM_HEIGHT_TO_CENTER * Settings.scale, Settings.CREAM_COLOR.cpy());
                    }
                }
                return SpireReturn.Return(null);
            }
            else
            {
                hasInit = true;
                return SpireReturn.Continue();
            }
        }
    }

    @SpirePatch(clz = SingleCardViewPopup.class, method = "renderCost", paramtypez = {SpriteBatch.class})
    public static class renderCost
    {
        private static Texture GetEnergyImage(AbstractCard card)
        {
            Texture texture;
            if (card.color == KSMOD_CustomCardColor.CLOWCARD_COLOR)
            {
                switch (card.type)
                {
                    case ATTACK:
                        texture = KSMOD_ImageConst.ORB_CLOWCARD_ATTACK_LARGE;
                        break;
                    case SKILL:
                        texture = KSMOD_ImageConst.ORB_CLOWCARD_SKILL_LARGE;
                        break;
                    case POWER:
                        texture = KSMOD_ImageConst.ORB_CLOWCARD_POWER_LARGE;
                        break;
                    default:
                        texture = KSMOD_ImageConst.ORB_CLOWCARD_SKILL_LARGE;
                        break;
                }
            }
            else
            {
                switch (card.type)
                {
                    case ATTACK:
                        texture = KSMOD_ImageConst.ORB_SAKURACARD_ATTACK_LARGE;
                        break;
                    case SKILL:
                        texture = KSMOD_ImageConst.ORB_SAKURACARD_SKILL_LARGE;
                        break;
                    case POWER:
                        texture = KSMOD_ImageConst.ORB_SAKURACARD_POWER_LARGE;
                        break;
                    default:
                        texture = KSMOD_ImageConst.ORB_SAKURACARD_SKILL_LARGE;
                        break;
                }
            }
            return texture;
        }

        public static SpireReturn<Object> Prefix(SingleCardViewPopup view, SpriteBatch sb) throws NoSuchFieldException, IllegalAccessException, InvocationTargetException, NoSuchMethodException
        {
            AbstractCard card = (AbstractCard) KSMOD_Utility.GetFieldByReflect(SingleCardViewPopup.class, "card").get(view);
            if (IsKSCard(card))
            {
                if (!card.isLocked && card.isSeen && card.cost > -2)
                {

                    sb.draw(GetEnergyImage(card), Settings.WIDTH * 0.5F - ENERGY_ICON_WIDTH / 2F + (ENERGY_COST_OFFSET_X - 2F) * Settings.scale, Settings.HEIGHT * 0.5F - ENERGY_ICON_WIDTH / 2F + ENERGY_COST_OFFSET_Y * Settings.scale, ENERGY_ICON_WIDTH / 2F, ENERGY_ICON_WIDTH / 2F, ENERGY_ICON_WIDTH, ENERGY_ICON_WIDTH, Settings.scale, Settings.scale, 0.0F, 0, 0, (int) ENERGY_ICON_WIDTH, (int) ENERGY_ICON_WIDTH, false, false);
                    Color color = card.isCostModified ? Settings.GREEN_TEXT_COLOR.cpy() : Settings.CREAM_COLOR.cpy();
                    switch (card.cost)
                    {
                        case -1:
                            FontHelper.renderFontCentered(sb, FontHelper.SCP_cardEnergyFont, "X", Settings.WIDTH * 0.5F + ENERGY_COST_OFFSET_X * Settings.scale, Settings.HEIGHT * 0.5F + ENERGY_COST_OFFSET_Y * Settings.scale, color);
                            break;
                        default:
                            FontHelper.renderFontCentered(sb, FontHelper.SCP_cardEnergyFont, Integer.toString(card.cost), Settings.WIDTH * 0.5F + ENERGY_COST_OFFSET_X * Settings.scale, Settings.HEIGHT * 0.5F + ENERGY_COST_OFFSET_Y * Settings.scale, color);
                            break;
                    }
                }
                return SpireReturn.Return(null);
            }
            else
            {
                return SpireReturn.Continue();
            }
        }
    }

    @SpirePatch(clz = SingleCardViewPopup.class, method = "open", paramtypez = {AbstractCard.class})
    public static class open
    {
        public static void Postfix(SingleCardViewPopup view, AbstractCard card) throws NoSuchFieldException, IllegalAccessException
        {
            if (IsKSCard(card))
            {
                Hitbox cardHb = (Hitbox) KSMOD_Utility.GetFieldByReflect(SingleCardViewPopup.class, "cardHb").get(view);
                cardHb.resize(HB_W, HB_H);
                cardHb.move(Settings.WIDTH * 0.5F, Settings.HEIGHT * 0.5F);
                Hitbox upgradeHb = (Hitbox) KSMOD_Utility.GetFieldByReflect(SingleCardViewPopup.class, "upgradeHb").get(view);
                upgradeHb.move(TOGGLE_X, TOGGLE_Y);
            }
        }
    }

    @SpirePatch(clz = SingleCardViewPopup.class, method = "open", paramtypez = {
            AbstractCard.class,
            CardGroup.class
    })
    public static class open_group
    {
        public static void Postfix(SingleCardViewPopup view, AbstractCard card, CardGroup group) throws NoSuchFieldException, IllegalAccessException
        {
            if (IsKSCard(card))
            {
                Hitbox cardHb = (Hitbox) KSMOD_Utility.GetFieldByReflect(SingleCardViewPopup.class, "cardHb").get(view);
                cardHb.resize(HB_W, HB_H);
                cardHb.move(Settings.WIDTH * 0.5F, Settings.HEIGHT * 0.5F);
                Hitbox upgradeHb = (Hitbox) KSMOD_Utility.GetFieldByReflect(SingleCardViewPopup.class, "upgradeHb").get(view);
                upgradeHb.move(TOGGLE_X, TOGGLE_Y);
            }
        }
    }
}
