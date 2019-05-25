package KinomotoSakuraMod.Patches;

import KinomotoSakuraMod.Utility.ImageConst;
import KinomotoSakuraMod.Utility.Utility;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.cardManip.CardGlowBorder;

import java.lang.reflect.Field;

@SpirePatch(clz = CardGlowBorder.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {AbstractCard.class})
public class CardGlowBorderPatch
{
    public static SpireReturn<Object> Prefix(CardGlowBorder border, AbstractCard card) throws NoSuchFieldException, IllegalAccessException
    {
        Field thisCard = Utility.GetFieldByReflect(border, CardGlowBorder.class, "card");
        if (card.color == CustomCardColor.CLOWCARD_COLOR || card.color == CustomCardColor.SAKURACARD_COLOR || card.color == CustomCardColor.SPELL_COLOR)
        {
            thisCard.set(border, card);
            Field img = Utility.GetFieldByReflect(border, CardGlowBorder.class, "img");
            img.set(border, ImageConst.SILHOUETTE);
            border.duration = 1.2F;
            Field color = Utility.GetFieldByReflect(border, AbstractGameEffect.class, "color");
            if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT)
            {
                color.set(border, Color.valueOf("30c8dcff"));
            }
            else
            {
                color.set(border, Color.GREEN.cpy());
            }
            return SpireReturn.Return(null);
        }
        else
        {
            return SpireReturn.Continue();
        }
    }
}
