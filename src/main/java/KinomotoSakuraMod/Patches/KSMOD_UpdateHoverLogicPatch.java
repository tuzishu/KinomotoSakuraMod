package KinomotoSakuraMod.Patches;


import KinomotoSakuraMod.Cards.AbstractMagicCard;
import KinomotoSakuraMod.Utility.KSMOD_Utility;
import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import java.lang.reflect.Field;

@SpirePatch(clz = AbstractCard.class, method = "updateHoverLogic", paramtypez = {})
public class KSMOD_UpdateHoverLogicPatch
{
    public static SpireReturn<Object> Prefix(AbstractCard card) throws NoSuchFieldException, IllegalAccessException
    {
        if (card instanceof AbstractMagicCard && card.type == AbstractCard.CardType.ATTACK)
        {
            Field hoverDuration = KSMOD_Utility.GetFieldByReflect(card, AbstractCard.class, "hoverDuration");
            Field renderTip = KSMOD_Utility.GetFieldByReflect(card, AbstractCard.class, "renderTip");
            Field hovered = KSMOD_Utility.GetFieldByReflect(card, AbstractCard.class, "hovered");
            boolean justHovered = hovered.getBoolean(card);
            boolean justUnhovered = false;
            card.hb.update();
            if (card.hb.hovered)
            {
                card.hover();
                hoverDuration.setFloat(card, hoverDuration.getFloat(card) + Gdx.graphics.getDeltaTime());
                if (hoverDuration.getFloat(card) > 0.2F && !Settings.hideCards)
                {
                    renderTip.setBoolean(card, true);
                }
            }
            else
            {
                card.unhover();
                if (justHovered)
                {
                    justUnhovered = true;
                }
            }

            if (card.hb.justHovered)    // 鼠标进入hitbox时做什么
            {
                card.initializeDescription();
            }

            if (justUnhovered)  // 鼠标离开hitbox时做什么
            {
                card.description.clear();
            }

            return SpireReturn.Return(null);
        }
        else
        {
            return SpireReturn.Continue();
        }
    }
}
