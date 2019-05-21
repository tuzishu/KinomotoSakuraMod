package KinomotoSakuraMod.Patches;

import KinomotoSakuraMod.Cards.AbstractMagicCard;
import KinomotoSakuraMod.Utility.Utility;
import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.MathHelper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@SpirePatch(clz = AbstractCard.class, method = "update", paramtypez = {})
public class KSMOD_AbstractCardUpdatePatch
{
    private static final float HB_W = 220F * Settings.scale;
    private static final float HB_H = 500F * Settings.scale;

    public static SpireReturn<Object> Prefix(AbstractCard card) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException
    {
        if (card instanceof AbstractMagicCard)
        {
            Method updateFlashVfx = Utility.GetMethodByReflect(card, AbstractCard.class, "updateFlashVfx");
            updateFlashVfx.invoke(card);
            if (card.hoverTimer != 0.0F)
            {
                card.hoverTimer -= Gdx.graphics.getDeltaTime();
                if (card.hoverTimer < 0.0F)
                {
                    card.hoverTimer = 0.0F;
                }
            }

            if (AbstractDungeon.player != null && AbstractDungeon.player.isDraggingCard && card == AbstractDungeon.player.hoveredCard)
            {
                card.current_x = MathHelper.cardLerpSnap(card.current_x, card.target_x);
                card.current_y = MathHelper.cardLerpSnap(card.current_y, card.target_y);
                if (AbstractDungeon.player.hasRelic("Necronomicon"))
                {
                    if (card.cost >= 2 && card.type == AbstractCard.CardType.ATTACK && AbstractDungeon.player.getRelic("Necronomicon").checkTrigger())
                    {
                        AbstractDungeon.player.getRelic("Necronomicon").beginLongPulse();
                    }
                    else
                    {
                        AbstractDungeon.player.getRelic("Necronomicon").stopPulse();
                    }
                }
            }

            if (Settings.FAST_MODE)
            {
                card.current_x = MathHelper.cardLerpSnap(card.current_x, card.target_x);
                card.current_y = MathHelper.cardLerpSnap(card.current_y, card.target_y);
            }

            card.current_x = MathHelper.cardLerpSnap(card.current_x, card.target_x);
            card.current_y = MathHelper.cardLerpSnap(card.current_y, card.target_y);
            card.hb.move(card.current_x, card.current_y);
            card.hb.resize(HB_W * card.drawScale, HB_H * card.drawScale);
            if (card.hb.clickStarted && card.hb.hovered)
            {
                card.drawScale = MathHelper.cardScaleLerpSnap(card.drawScale, card.targetDrawScale * 0.9F);
                card.drawScale = MathHelper.cardScaleLerpSnap(card.drawScale, card.targetDrawScale * 0.9F);
            }
            else
            {
                card.drawScale = MathHelper.cardScaleLerpSnap(card.drawScale, card.targetDrawScale);
            }

            if (card.angle != card.targetAngle)
            {
                card.angle = MathHelper.angleLerpSnap(card.angle, card.targetAngle);
            }

            Method updateTransparency = Utility.GetMethodByReflect(card, AbstractCard.class, "updateTransparency");
            Method updateColor = Utility.GetMethodByReflect(card, AbstractCard.class, "updateColor");
            updateTransparency.invoke(card);
            updateColor.invoke(card);
            return SpireReturn.Return(null);
        }
        else
        {
            return SpireReturn.Continue();
        }
    }
}
