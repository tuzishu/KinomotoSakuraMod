package KinomotoSakuraMod.Patches;

import KinomotoSakuraMod.Utility.KSMOD_ImageConst;
import KinomotoSakuraMod.Utility.KSMOD_Utility;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.cardManip.CardGlowBorder;

import java.lang.reflect.Field;

@SpirePatch(clz = CardGlowBorder.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {AbstractCard.class})
public class KSMOD_CardGlowBorderPatch
{
    public static SpireReturn<Object> Prefix(CardGlowBorder border, AbstractCard card) throws NoSuchFieldException, IllegalAccessException
    {
        Field thisCard = KSMOD_Utility.GetFieldByReflect(CardGlowBorder.class, "card");
        if (card.color == KSMOD_CustomCardColor.CLOWCARD_COLOR || card.color == KSMOD_CustomCardColor.SAKURACARD_COLOR || card.color == KSMOD_CustomCardColor.SPELL_COLOR)
        {
            thisCard.set(border, card);
            Field img = KSMOD_Utility.GetFieldByReflect(CardGlowBorder.class, "img");
            img.set(border, new TextureAtlas.AtlasRegion(KSMOD_ImageConst.SILHOUETTE, 0, 0, KSMOD_ImageConst.SILHOUETTE.getWidth(), KSMOD_ImageConst.SILHOUETTE.getHeight()));
            border.duration = 1.2F;
            Field color = KSMOD_Utility.GetFieldByReflect(AbstractGameEffect.class, "color");
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
