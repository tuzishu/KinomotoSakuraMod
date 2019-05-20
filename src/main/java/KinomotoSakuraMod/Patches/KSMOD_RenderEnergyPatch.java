package KinomotoSakuraMod.Patches;


import KinomotoSakuraMod.Cards.AbstractMagicCard;
import KinomotoSakuraMod.Utility.KSMOD_ImageConst;
import KinomotoSakuraMod.Utility.KSMOD_Utility;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@SpirePatch(clz = AbstractCard.class, method = "renderEnergy", paramtypez = {SpriteBatch.class})
public class KSMOD_RenderEnergyPatch
{
    private static final float OFFSET_X = -92;
    private static final float OFFSET_Y = 222;
    private static final Color ENERGY_COST_RESTRICTED_COLOR = new Color(1.0F, 0.3F, 0.3F, 1.0F);
    private static final Color ENERGY_COST_MODIFIED_COLOR = new Color(0.4F, 1.0F, 0.4F, 1.0F);

    public static SpireReturn<Object> Prefix(AbstractCard card, SpriteBatch sb) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException
    {
        if (card instanceof AbstractMagicCard)
        {
            boolean darken = KSMOD_Utility.GetFieldByReflect(card, AbstractCard.class, "darken").getBoolean(card);
            Color renderColor = (Color) KSMOD_Utility.GetFieldByReflect(card, AbstractCard.class, "renderColor").get(card);
            Method renderHelper = KSMOD_Utility.GetMethodByReflect(card, AbstractCard.class, "renderHelper", SpriteBatch.class, Color.class, Texture.class, float.class, float.class);
            if (card.cost > -2 && !darken && !card.isLocked && card.isSeen)
            {
                float drawX = card.current_x - 256.0F;
                float drawY = card.current_y - 256.0F;
                renderHelper.invoke(card, sb, renderColor, KSMOD_ImageConst.ORB, drawX, drawY);

                Color costColor = Color.WHITE.cpy();
                if (AbstractDungeon.player != null && AbstractDungeon.player.hand.contains(card) && !card.hasEnoughEnergy())
                {
                    costColor = ENERGY_COST_RESTRICTED_COLOR;
                }
                else if (card.isCostModified || card.isCostModifiedForTurn || card.freeToPlayOnce)
                {
                    costColor = ENERGY_COST_MODIFIED_COLOR;
                }

                costColor.a = card.transparency;

                Method getCost = KSMOD_Utility.GetMethodByReflect(card, AbstractCard.class, "getCost");
                String text = (String) getCost.invoke(card);

                Method getEnergyFont = KSMOD_Utility.GetMethodByReflect(card, AbstractCard.class, "getEnergyFont");
                BitmapFont font = (BitmapFont) getEnergyFont.invoke(card);

                if ((card.type != AbstractCard.CardType.STATUS || card.cardID.equals("Slimed")) && (card.color != AbstractCard.CardColor.CURSE || card.cardID.equals("Pride")))
                {
                    FontHelper.renderRotatedText(sb, font, text, card.current_x, card.current_y, OFFSET_X * card.drawScale * Settings.scale, OFFSET_Y * card.drawScale * Settings.scale, card.angle, false, costColor);
                }

            }
            return SpireReturn.Return((Object) null);
        }
        else
        {
            return SpireReturn.Continue();
        }
    }
}
