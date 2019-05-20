package KinomotoSakuraMod.Patches;

import KinomotoSakuraMod.Cards.AbstractMagicCard;
import KinomotoSakuraMod.Utility.KSMOD_ImageConst;
import KinomotoSakuraMod.Utility.KSMOD_Utility;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;

import java.lang.reflect.Field;

@SpirePatch(clz = AbstractCard.class, method = "renderAttackPortrait", paramtypez = {
        SpriteBatch.class,
        float.class,
        float.class
})
public class CustomRenderAttackPortrait
{
    public static SpireReturn<Object> Prefix(AbstractCard card, SpriteBatch sb, float x, float y) throws NoSuchFieldException, IllegalAccessException
    {
        if (card instanceof AbstractMagicCard && card.color == CustomCardColor.CLOWCARD_COLOR)
        {
            Texture texture;
            switch (card.rarity)
            {
                case RARE:
                    texture = KSMOD_ImageConst.FRAME_COMMON;
                    break;
                case UNCOMMON:
                    texture = KSMOD_ImageConst.FRAME_UNCOMMON;
                    break;
                default:
                    texture = KSMOD_ImageConst.FRAME_COMMON;
                    break;
            }
            Field renderColor = KSMOD_Utility.GetFieldByReflect(card, AbstractCard.class, "renderColor");
            sb.setColor((Color) renderColor.get(card));
            try
            {
                sb.draw(texture, x, y, 256.0F, 256.0F, 512.0F, 512.0F, card.drawScale * Settings.scale, card.drawScale * Settings.scale, card.angle, 0, 0, 512, 512, false, false);
            }
            catch (Exception var7)
            {
                KSMOD_Utility.Logger.error(var7);
            }
            return SpireReturn.Return(null);
        }
        else
        {
            return SpireReturn.Continue();
        }
    }
}
