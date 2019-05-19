package KinomotoSakuraMod.Patches;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DescriptionLine;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;

@SpirePatch(clz = AbstractCard.class, method = "renderDescriptionCN", paramtypez = {SpriteBatch.class})
public class CustomRenderDescriptionCN
{
    private static final float SCALE_RATE = 1F;
    private static final float DES_OFFSET_Y = 0.173F;
    private static final Color TEXT_COLOR = Settings.CREAM_COLOR.cpy();

    public static SpireReturn<Object> Prefix(AbstractCard card, SpriteBatch sb)
    {
        if (false)
        // if (card instanceof AbstractMagicCard && card.type == AbstractCard.CardType.ATTACK)
        {
            if (card.isSeen && !card.isLocked)
            {
                BitmapFont font = null;
                if (card.angle == 0.0F && card.drawScale == 1.0F)
                {
                    font = FontHelper.cardDescFont_N;
                }
                else
                {
                    font = FontHelper.cardDescFont_L;
                }
                font.getData().setScale(card.drawScale);
                float offset = card.IMG_HEIGHT * DES_OFFSET_Y;
                float draw_y = card.current_y - card.IMG_HEIGHT * card.drawScale / 2.0F + offset * card.drawScale;
                draw_y += (float) card.description.size() * font.getCapHeight() * 0.775F - font.getCapHeight() * 0.375F;
                float spacing = 1.45F * -font.getCapHeight() / Settings.scale / card.drawScale;
                GlyphLayout gl = new GlyphLayout();
                float maxline = 0;
                for (int i = 0; i < card.description.size(); ++i)
                {
                    if (maxline < card.description.get(i).width)
                    {
                        maxline = card.description.get(i).width;
                    }
                }
                for (int i = 0; i < card.description.size(); ++i)
                {
                    float start_x = 0.0F;
                    start_x = card.current_x - (((DescriptionLine) card.description.get(i)).width) * 0.67f * card.drawScale / 2.0F - 14.0F * Settings.scale;
                    start_x = card.current_x - maxline * SCALE_RATE * card.drawScale / 2.0F - 14.0F * Settings.scale;
                    String desc = ((DescriptionLine) card.description.get(i)).text;
                    String[] descList = desc.split(" ");
                    for (int j = 0; j < descList.length; ++j)
                    {
                        String tmp = descList[j];
                        tmp = tmp.replace("!", "");

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
                            FontHelper.renderRotatedText(sb, font, tmp, card.current_x, card.current_y, start_x - card.current_x + gl.width / 2.0F, (float) i * 1.45F * -font.getCapHeight() + draw_y - card.current_y + -6.0F, card.angle, true, TEXT_COLOR);
                            start_x = (float) Math.round(start_x + gl.width);
                            gl.setText(font, punctuation);
                            FontHelper.renderRotatedText(sb, font, punctuation, card.current_x, card.current_y, start_x - card.current_x + gl.width / 2.0F, (float) i * 1.45F * -font.getCapHeight() + draw_y - card.current_y + -6.0F, card.angle, true, TEXT_COLOR);
                            gl.setText(font, punctuation);
                            start_x += gl.width;
                        }
                        else
                        {
                            gl.setText(font, tmp);
                            FontHelper.renderRotatedText(sb, font, tmp, card.current_x, card.current_y, start_x - card.current_x + gl.width / 2.0F, (float) i * 1.45F * -font.getCapHeight() + draw_y - card.current_y + -6.0F, card.angle, true, TEXT_COLOR);
                            start_x += gl.width;
                        }
                    }
                }
                font.getData().setScale(1.0F);
            }
            else
            {
                FontHelper.menuBannerFont.getData().setScale(card.drawScale * 1.25F);
                FontHelper.renderRotatedText(sb, FontHelper.menuBannerFont, "? ? ?", card.current_x, card.current_y, 0.0F, -200.0F * Settings.scale * card.drawScale / 2.0F, card.angle, true, TEXT_COLOR);
                FontHelper.menuBannerFont.getData().setScale(1.0F);
            }
            return SpireReturn.Return((Object) null);
        }
        else
        {
            return SpireReturn.Continue();
        }
    }
}
