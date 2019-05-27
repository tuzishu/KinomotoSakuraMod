package KinomotoSakuraMod.Patches;

import KinomotoSakuraMod.Utility.ImageConst;
import KinomotoSakuraMod.Utility.Utility;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;

public class SingleCardViewPopupPatch
{
    private static final float ENERGY_COST_OFFSET_X = -90;
    private static final float ENERGY_COST_OFFSET_Y = 222;
    private static final Color ENERGY_COST_RESTRICTED_COLOR = new Color(1.0F, 0.3F, 0.3F, 1.0F);
    private static final Color ENERGY_COST_MODIFIED_COLOR = new Color(0.4F, 1.0F, 0.4F, 1.0F);
    private static final float IMG_WIDTH = 220F * Settings.scale;
    private static final float IMG_HEIGHT = 500F * Settings.scale;
    private static final float DESC_LINE_WIDTH = 190F * Settings.scale;
    private static final float DESC_SCALE_RATE_X = 0.83F;
    private static final float DESC_OFFSET_TO_BOTTOM_Y = 0.367F;
    private static final float CARD_ENERGY_IMG_WIDTH = 24.0F * Settings.scale;
    private static final float HB_W = IMG_WIDTH;
    private static final float HB_H = IMG_HEIGHT;
    private static final float TITLE_HEIGHT_TO_CENTER = 222.0F;
    private static final float PORTRAIT_WIDTH = 303F;
    private static final float PORTRAIT_HEIGHT = 786F;
    private static final float PORTRAIT_ORIGIN_X = 151F;
    private static final float PORTRAIT_ORIGIN_Y = 356F;

    public static boolean IsKSCard(AbstractCard card)
    {
        return card.color == CustomCardColor.CLOWCARD_COLOR || card.color == CustomCardColor.SAKURACARD_COLOR || card.color == CustomCardColor.SPELL_COLOR;
    }

    @SpirePatch(clz = SingleCardViewPopup.class, method = "renderCardBack", paramtypez = {SpriteBatch.class})
    public static class renderCardBack
    {
        public static SpireReturn<Object> Prefix(SingleCardViewPopup view, SpriteBatch sb) throws NoSuchFieldException, IllegalAccessException
        {
            AbstractCard card = (AbstractCard) Utility.GetFieldByReflect(view, SingleCardViewPopup.class, "card").get(view);
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
                    sb.draw(img, (float) Settings.WIDTH / 2.0F - 512.0F, (float) Settings.HEIGHT / 2.0F - 512.0F, 512.0F, 512.0F, 1024.0F, 1024.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 1024, 1024, false, false);
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
            AbstractCard card = (AbstractCard) Utility.GetFieldByReflect(view, SingleCardViewPopup.class, "card").get(view);
            if (IsKSCard(card))
            {
                Texture portraitImg = (Texture) Utility.GetFieldByReflect(view, SingleCardViewPopup.class, "portraitImg").get(view);
                sb.draw(portraitImg, (float) Settings.WIDTH / 2.0F - PORTRAIT_ORIGIN_X, (float) Settings.HEIGHT / 2.0F - PORTRAIT_ORIGIN_Y, PORTRAIT_ORIGIN_X, PORTRAIT_ORIGIN_Y, PORTRAIT_WIDTH, PORTRAIT_HEIGHT, Settings.scale, Settings.scale, 0.0F, 0, 0, (int) PORTRAIT_WIDTH, (int) PORTRAIT_HEIGHT, false, false);
                return SpireReturn.Return(null);
            }
            else
            {
                return SpireReturn.Continue();
            }
        }
    }
}
