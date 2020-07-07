package KinomotoSakuraMod.Patches;

import KinomotoSakuraMod.Utility.KSMOD_ImageConst;
import KinomotoSakuraMod.Utility.KSMOD_ReflectTool;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.cardManip.CardFlashVfx;

import java.lang.reflect.Field;

public class KSMOD_CardFlashVfxPatch
{
    @SpirePatch(clz = CardFlashVfx.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {
            AbstractCard.class,
            Color.class,
            boolean.class
    })
    public static class CardFlashVfx_1
    {
        public static SpireReturn<Object> Prefix(CardFlashVfx vfx, AbstractCard card, Color color, boolean isSuper) throws NoSuchFieldException, IllegalAccessException
        {
            if (card.color == KSMOD_CustomCardColor.CLOWCARD_COLOR || card.color == KSMOD_CustomCardColor.SAKURACARD_COLOR || card.color == KSMOD_CustomCardColor.SPELL_COLOR)
            {
                Field thisColor = KSMOD_ReflectTool.GetFieldByReflect(AbstractGameEffect.class, "color");
                Field yScale = KSMOD_ReflectTool.GetFieldByReflect(CardFlashVfx.class, "yScale");
                Field thisCard = KSMOD_ReflectTool.GetFieldByReflect(CardFlashVfx.class, "card");
                Field thisIsSuper = KSMOD_ReflectTool.GetFieldByReflect(CardFlashVfx.class, "isSuper");
                Field duration = KSMOD_ReflectTool.GetFieldByReflect(AbstractGameEffect.class, "duration");
                Field img = KSMOD_ReflectTool.GetFieldByReflect(CardFlashVfx.class, "img");
                thisColor.set(vfx, color);
                yScale.setFloat(vfx, 0.0F);
                thisCard.set(vfx, card);
                thisIsSuper.setBoolean(vfx, isSuper);
                duration.setFloat(vfx, 0.5F);
                img.set(vfx, KSMOD_ImageConst.FLASH_ATLAS);
                return SpireReturn.Return(null);
            }
            else
            {
                return SpireReturn.Continue();
            }
        }
    }

    @SpirePatch(clz = CardFlashVfx.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {
            AbstractCard.class,
            Color.class
    })
    public static class CardFlashVfx_2
    {
        public static SpireReturn<Object> Prefix(CardFlashVfx vfx, AbstractCard card, Color c) throws NoSuchFieldException, IllegalAccessException
        {
            if (card.color == KSMOD_CustomCardColor.CLOWCARD_COLOR || card.color == KSMOD_CustomCardColor.SAKURACARD_COLOR || card.color == KSMOD_CustomCardColor.SPELL_COLOR)
            {
                CardFlashVfx_1.Prefix(vfx, card, c, false);
                return SpireReturn.Return(null);
            }
            else
            {
                return SpireReturn.Continue();
            }
        }
    }
}
