package KinomotoSakuraMod.Patches;

import KinomotoSakuraMod.Utility.ImageConst;
import KinomotoSakuraMod.Utility.Utility;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

public class SingleCardViewPopupPatch
{
    private static final float ENERGY_COST_OFFSET_X = -180F;
    private static final float ENERGY_COST_OFFSET_Y = 444F;
    private static final float ENERGY_ICON_WIDTH = 164F;
    private static final float IMG_WIDTH = 440F * Settings.scale;
    private static final float IMG_HEIGHT = 1000F * Settings.scale;
    private static final float DESC_LINE_WIDTH = 190F * Settings.scale;
    private static final float DESC_SCALE_RATE_X = 0.83F;
    private static final float DESC_OFFSET_TO_BOTTOM_Y = 0.367F;
    private static final float CARD_ENERGY_IMG_WIDTH = 24.0F * Settings.scale;
    private static final float HB_W = IMG_WIDTH;
    private static final float HB_H = IMG_HEIGHT;
    private static final float TITLE_HEIGHT_TO_CENTER = 444.0F;
    private static final float PORTRAIT_WIDTH = 303F;
    private static final float PORTRAIT_HEIGHT = 786F;
    private static final float PORTRAIT_ORIGIN_X = 151F;
    private static final float PORTRAIT_ORIGIN_Y = 356F;
    private static final String[] TEXT;

    static
    {
        UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("SingleCardViewPopup");
        TEXT = uiStrings.TEXT;
    }

    public static boolean IsKSCard(AbstractCard card)
    {
        return card.color == CustomCardColor.CLOWCARD_COLOR || card.color == CustomCardColor.SAKURACARD_COLOR || card.color == CustomCardColor.SPELL_COLOR;
    }

    @SpirePatch(clz = SingleCardViewPopup.class, method = "renderCardBack", paramtypez = {SpriteBatch.class})
    public static class renderCardBack
    {
        public static SpireReturn<Object> Prefix(SingleCardViewPopup view, SpriteBatch sb) throws NoSuchFieldException, IllegalAccessException
        {
            AbstractCard card = (AbstractCard) Utility.GetFieldByReflect(SingleCardViewPopup.class, "card").get(view);
            if (IsKSCard(card))
            {
                Texture img = null;
                if (card.color == CustomCardColor.CLOWCARD_COLOR)
                {
                    img = ImageConst.CLOWCARD_BG_LARGE;
                }
                else if (card.color == CustomCardColor.SAKURACARD_COLOR)
                {
                    img = ImageConst.SAKURACARD_BG_LARGE;
                }
                else if (card.color == CustomCardColor.SPELL_COLOR)
                {
                    img = ImageConst.SPELLCARD_BG_LARGE;
                }

                if (img != null)
                {
                    sb.draw(img, Settings.WIDTH / 2.0F - 512.0F, Settings.HEIGHT / 2.0F - 512.0F, 512.0F, 512.0F, 1024.0F, 1024.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 1024, 1024, false, false);
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
            AbstractCard card = (AbstractCard) Utility.GetFieldByReflect(SingleCardViewPopup.class, "card").get(view);
            if (IsKSCard(card))
            {
                Texture portraitImg = (Texture) Utility.GetFieldByReflect(SingleCardViewPopup.class, "portraitImg").get(view);
                sb.draw(portraitImg, Settings.WIDTH / 2.0F - PORTRAIT_ORIGIN_X, Settings.HEIGHT / 2.0F - PORTRAIT_ORIGIN_Y, PORTRAIT_ORIGIN_X, PORTRAIT_ORIGIN_Y, PORTRAIT_WIDTH, PORTRAIT_HEIGHT, Settings.scale, Settings.scale, 0.0F, 0, 0, (int) PORTRAIT_WIDTH, (int) PORTRAIT_HEIGHT, false, false);
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
        public static SpireReturn<Object> Prefix(SingleCardViewPopup view, SpriteBatch sb) throws NoSuchFieldException, IllegalAccessException
        {
            AbstractCard card = (AbstractCard) Utility.GetFieldByReflect(SingleCardViewPopup.class, "card").get(view);
            if (IsKSCard(card))
            {
                Texture frameImg = null;
                switch (card.rarity)
                {
                    case RARE:
                        frameImg = ImageConst.FRAME_RARE_LARGE;
                        break;
                    case UNCOMMON:
                        frameImg = ImageConst.FRAME_UNCOMMON_LARGE;
                        break;
                    default:
                        frameImg = ImageConst.FRAME_COMMON_LARGE;
                        break;
                }
                sb.draw(frameImg, Settings.WIDTH / 2.0F - 512.0F, Settings.HEIGHT / 2.0F - 512.0F, 512.0F, 512.0F, 1024.0F, 1024.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 1024, 1024, false, false);
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
        public static SpireReturn<Object> Prefix(SingleCardViewPopup view, SpriteBatch sb) throws NoSuchFieldException, IllegalAccessException
        {
            AbstractCard card = (AbstractCard) Utility.GetFieldByReflect(SingleCardViewPopup.class, "card").get(view);
            if (IsKSCard(card))
            {
                Texture bannerImg = null;
                switch (card.rarity)
                {
                    case RARE:
                        bannerImg = ImageConst.BANNER_RARE_LARGE;
                        break;
                    case UNCOMMON:
                        bannerImg = ImageConst.BANNER_UNCOMMON_LARGE;
                        break;
                    default:
                        bannerImg = ImageConst.BANNER_COMMON_LARGE;
                }
                sb.draw(bannerImg, Settings.WIDTH / 2.0F - 512.0F, Settings.HEIGHT / 2.0F - 512.0F, 512.0F, 512.0F, 1024.0F, 1024.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 1024, 1024, false, false);
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
            AbstractCard card = (AbstractCard) Utility.GetFieldByReflect(SingleCardViewPopup.class, "card").get(view);
            if (IsKSCard(card))
            {
                if (!card.isLocked && card.isSeen)
                {
                    float current_x = Utility.GetFieldByReflect(SingleCardViewPopup.class, "current_x").getFloat(view);
                    float current_y = Utility.GetFieldByReflect(SingleCardViewPopup.class, "current_y").getFloat(view);
                    float drawScale = Utility.GetFieldByReflect(SingleCardViewPopup.class, "drawScale").getFloat(view);
                    BitmapFont font = FontHelper.SCP_cardDescFont;
                    float draw_y = current_y - IMG_HEIGHT / 2.0F + IMG_HEIGHT * DESC_OFFSET_TO_BOTTOM_Y * drawScale;
                    draw_y += card.description.size() * font.getCapHeight() * 0.775F - font.getCapHeight() * 0.375F;
                    float spacing = 1.53F * -font.getCapHeight() / Settings.scale / drawScale;
                    GlyphLayout gl = new GlyphLayout();

                    for (int i = 0; i < card.description.size(); ++i)
                    {
                        float start_x = 0.0F;
                        if (Settings.leftAlignCards)
                        {
                            start_x = current_x - IMG_WIDTH * DESC_SCALE_RATE_X * drawScale / 2.0F + 2.0F * Settings.scale;
                        }
                        else
                        {
                            start_x = current_x - card.description.get(i).width * drawScale / 2.0F - 20.0F * Settings.scale;
                        }
                        Field scannerField = Utility.GetFieldByReflect(SingleCardViewPopup.class, "scanner");
                        scannerField.set(view, new Scanner(card.description.get(i).text));
                        Scanner scanner = (Scanner) scannerField.get(view);
                        Method getDynamicValue = Utility.GetMethodByReflect(SingleCardViewPopup.class, "getDynamicValue", char.class);
                        while (scanner.hasNext())
                        {
                            String tmp = scanner.next();
                            tmp = tmp.replace("!", "");
                            String updateTmp = null;
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
                            float card_energy_w = Utility.GetFieldByReflect(SingleCardViewPopup.class, "card_energy_w").getFloat(view);
                            Method renderSmallEnergy = Utility.GetMethodByReflect(SingleCardViewPopup.class, "renderSmallEnergy", SpriteBatch.class, TextureAtlas.AtlasRegion.class, float.class, float.class);
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
                                FontHelper.renderRotatedText(sb, font, tmp, current_x, current_y, start_x - current_x + gl.width / 2.0F, i * 1.53F * -font.getCapHeight() + draw_y - current_y + -12.0F, 0.0F, true, Settings.GOLD_COLOR.cpy());
                                start_x = (float) Math.round(start_x + gl.width);
                                gl.setText(font, punctuation);
                                FontHelper.renderRotatedText(sb, font, punctuation, current_x, current_y, start_x - current_x + gl.width / 2.0F, i * 1.53F * -font.getCapHeight() + draw_y - current_y + -12.0F, 0.0F, true, Settings.CREAM_COLOR.cpy());
                                gl.setText(font, punctuation);
                                start_x += gl.width;
                            }
                            else if (tmp.equals("[R]"))
                            {
                                gl.width = card_energy_w * drawScale;
                                renderSmallEnergy.invoke(view, sb, ImageMaster.RED_ORB, (start_x - current_x) / Settings.scale / drawScale, -87.0F - ((card.description.size() - 4.0F) / 2.0F - i + 1.0F) * spacing);
                                start_x += gl.width;
                            }
                            else if (tmp.equals("[G]"))
                            {
                                gl.width = card_energy_w * drawScale;
                                renderSmallEnergy.invoke(view, sb, ImageMaster.GREEN_ORB, (start_x - current_x) / Settings.scale / drawScale, -87.0F - ((card.description.size() - 4.0F) / 2.0F - i + 1.0F) * spacing);
                                start_x += gl.width;
                            }
                            else if (tmp.equals("[B]"))
                            {
                                gl.width = card_energy_w * drawScale;
                                renderSmallEnergy.invoke(view, sb, ImageMaster.BLUE_ORB, (start_x - current_x) / Settings.scale / drawScale, -87.0F - ((card.description.size() - 4.0F) / 2.0F - i + 1.0F) * spacing);
                                start_x += gl.width;
                            }
                            else
                            {
                                gl.setText(font, tmp);
                                FontHelper.renderRotatedText(sb, font, tmp, current_x, current_y, start_x - current_x + gl.width / 2.0F, i * 1.53F * -font.getCapHeight() + draw_y - current_y + -12.0F, 0.0F, true, Settings.CREAM_COLOR);
                                start_x += gl.width;
                            }
                        }
                    }
                    font.getData().setScale(1.0F);
                }
                else
                {
                    FontHelper.renderFontCentered(sb, FontHelper.largeCardFont, "? ? ?", Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F - 195.0F * Settings.scale, Settings.CREAM_COLOR);
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
        public static SpireReturn<Object> Prefix(SingleCardViewPopup view, SpriteBatch sb) throws NoSuchFieldException, IllegalAccessException, InvocationTargetException, NoSuchMethodException
        {
            AbstractCard card = (AbstractCard) Utility.GetFieldByReflect(SingleCardViewPopup.class, "card").get(view);
            Utility.Logger.info(FontHelper.SCP_cardTitleFont_small);
            if (IsKSCard(card))
            {
                if (card.isLocked)
                {
                    RenderFontCentered(sb, FontHelper.SCP_cardTitleFont_small, TEXT[4], Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F + TITLE_HEIGHT_TO_CENTER * Settings.scale, Settings.CREAM_COLOR);
                }
                else if (card.isSeen)
                {
                    Method allowUpgradePreview = Utility.GetMethodByReflect(SingleCardViewPopup.class, "allowUpgradePreview");
                    if (SingleCardViewPopup.isViewingUpgrade && !((Boolean) allowUpgradePreview.invoke(view)))
                    {
                        RenderFontCentered(sb, FontHelper.SCP_cardTitleFont_small, card.name, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F + TITLE_HEIGHT_TO_CENTER * Settings.scale, Settings.GREEN_TEXT_COLOR);
                    }
                    else
                    {
                        RenderFontCentered(sb, FontHelper.SCP_cardTitleFont_small, card.name, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F + TITLE_HEIGHT_TO_CENTER * Settings.scale, Settings.CREAM_COLOR);
                    }
                }
                else
                {
                    RenderFontCentered(sb, FontHelper.SCP_cardTitleFont_small, TEXT[5], Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F + TITLE_HEIGHT_TO_CENTER * Settings.scale, Settings.CREAM_COLOR);
                }
                return SpireReturn.Return(null);
            }
            else
            {
                return SpireReturn.Continue();
            }
        }

        public static void RenderFontCentered(SpriteBatch sb, BitmapFont font, String msg, float x, float y, Color c)
        {
            FontHelper.layout.setText(font, msg);
            FontHelper.renderFont(sb, font, msg, x - FontHelper.layout.width / 2.0F, y + FontHelper.layout.height / 2.0F, c);
        }
    }

    @SpirePatch(clz = SingleCardViewPopup.class, method = "renderCost", paramtypez = {SpriteBatch.class})
    public static class renderCost
    {
        public static SpireReturn<Object> Prefix(SingleCardViewPopup view, SpriteBatch sb) throws NoSuchFieldException, IllegalAccessException, InvocationTargetException, NoSuchMethodException
        {
            AbstractCard card = (AbstractCard) Utility.GetFieldByReflect(SingleCardViewPopup.class, "card").get(view);
            if (IsKSCard(card))
            {
                if (!card.isLocked && card.isSeen && card.cost > -2)
                {
                    Texture img;
                    switch (card.type)
                    {
                        case ATTACK:
                            img = ImageConst.ORB_ATTACK_LARGE;
                            break;
                        case SKILL:
                            img = ImageConst.ORB_SKILL_LARGE;
                            break;
                        case POWER:
                            img = ImageConst.ORB_POWER_LARGE;
                            break;
                        default:
                            img = ImageConst.ORB_SKILL_LARGE;
                            break;
                    }
                    sb.draw(img, Settings.WIDTH / 2.0F - ENERGY_ICON_WIDTH / 2F + ENERGY_COST_OFFSET_X * Settings.scale, Settings.HEIGHT / 2.0F - ENERGY_ICON_WIDTH / 2F + ENERGY_COST_OFFSET_Y * Settings.scale, ENERGY_ICON_WIDTH / 2F, ENERGY_ICON_WIDTH / 2F, ENERGY_ICON_WIDTH, ENERGY_ICON_WIDTH, Settings.scale, Settings.scale, 0.0F, 0, 0, (int) ENERGY_ICON_WIDTH, (int) ENERGY_ICON_WIDTH, false, false);
                    Color color = card.isCostModified ? Settings.GREEN_TEXT_COLOR : Settings.CREAM_COLOR;
                    switch (card.cost)
                    {
                        case -1:
                            FontHelper.renderFont(sb, FontHelper.SCP_cardEnergyFont, "X", Settings.WIDTH / 2.0F + ENERGY_COST_OFFSET_X * Settings.scale, Settings.HEIGHT / 2.0F + ENERGY_COST_OFFSET_Y * Settings.scale, color);
                            break;
                        default:
                            FontHelper.renderFont(sb, FontHelper.SCP_cardEnergyFont, Integer.toString(card.cost), Settings.WIDTH / 2.0F + ENERGY_COST_OFFSET_X * Settings.scale, Settings.HEIGHT / 2.0F + ENERGY_COST_OFFSET_Y * Settings.scale, color);
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
}
