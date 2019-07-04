package KinomotoSakuraMod.Patches;

import KinomotoSakuraMod.Utility.KSMOD_Utility;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import javassist.CtBehavior;

public class KSMOD_LogHook
{
    // @SpirePatch(clz = DrawCardAction.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {
    //         AbstractCreature.class,
    //         int.class,
    //         boolean.class
    // })
    // public static class Log
    // {
    //     @SpireInsertPatch(locator = Locator.class)
    //     public static void Insert(DrawCardAction player, AbstractCreature source, int amount, boolean endTurnDraw) throws NoSuchFieldException, IllegalAccessException
    //     {
    //         KSMOD_Utility.Logger.info(amount);
    //         KSMOD_Utility.ShowStacktrace();
    //     }
    // }
    //
    // private static class Locator extends SpireInsertLocator
    // {
    //     public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
    //     {
    //         Matcher finalMatcher = new Matcher.FieldAccessMatcher(DrawCardAction.class, "shuffleCheck");
    //         int[] loc = LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
    //         return new int[] {loc[0]};
    //     }
    // }
}
