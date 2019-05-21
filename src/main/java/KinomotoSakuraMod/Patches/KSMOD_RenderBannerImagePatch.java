package KinomotoSakuraMod.Patches;

import KinomotoSakuraMod.Cards.AbstractMagicCard;
import KinomotoSakuraMod.Utility.ImageConst;
import KinomotoSakuraMod.Utility.Utility;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;

import java.lang.reflect.Field;

@SpirePatch(clz = AbstractCard.class, method = "renderBannerImage", paramtypez = {
        SpriteBatch.class,
        float.class,
        float.class
})
public class KSMOD_RenderBannerImagePatch
{
    public static SpireReturn<Object> Prefix(AbstractCard card, SpriteBatch sb, float drawX, float drawY) throws NoSuchFieldException, IllegalAccessException
    {
        if (card instanceof AbstractMagicCard && card.color == CustomCardColor.CLOWCARD_COLOR)
        {
            Texture texture;
            boolean hovered = card.hb.hovered;
            switch (card.rarity)
            {
                case RARE:
                    texture = hovered ? ImageConst.BANNER_MASK_RARE : ImageConst.BANNER_RARE;
                    break;
                case UNCOMMON:
                    texture = hovered ? ImageConst.BANNER_MASK_UNCOMMON : ImageConst.BANNER_UNCOMMON;
                    break;
                default:
                    texture = hovered ? ImageConst.BANNER_MASK_COMMON : ImageConst.BANNER_COMMON;
                    break;
            }
            Field renderColor = Utility.GetFieldByReflect(card, AbstractCard.class, "renderColor");
            sb.setColor((Color) renderColor.get(card));
            try
            {
                sb.draw(texture, drawX, drawY, 256.0F, 256.0F, 512.0F, 512.0F, card.drawScale * Settings.scale, card.drawScale * Settings.scale, card.angle, 0, 0, 512, 512, false, false);
            }
            catch (Exception var7)
            {
                Utility.Logger.error(var7);
            }
            return SpireReturn.Return(null);
        }
        else
        {
            return SpireReturn.Continue();
        }

    }
}
