package KinomotoSakuraMod.Patches;

import KinomotoSakuraMod.Utility.KSMOD_Utility;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.FlashPowerEffect;
import javassist.CtBehavior;

public class KSMOD_LogHook
{
    // @SpirePatch(clz = FlashPowerEffect.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {
    //         AbstractPower.class
    // })
    // public static class Log
    // {
    //     @SpireInsertPatch(locator = Locator.class)
    //     public static void Insert(FlashPowerEffect effect, AbstractPower power) throws NoSuchFieldException, IllegalAccessException
    //     {
    //         TextureAtlas.AtlasRegion img = (TextureAtlas.AtlasRegion) KSMOD_Utility.GetFieldByReflect(FlashPowerEffect.class, "region128").get(effect);
    //         KSMOD_Utility.Logger.info(power.ID);
    //         KSMOD_Utility.Logger.info(power.region128);
    //     }
    // }
    //
    // private static class Locator extends SpireInsertLocator
    // {
    //     public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
    //     {
    //         Matcher finalMatcher = new Matcher.FieldAccessMatcher(FlashPowerEffect.class, "region128");
    //         int[] loc = LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
    //         return new int[] {loc[1]};
    //     }
    // }
}
