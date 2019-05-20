package KinomotoSakuraMod.Patches;

import KinomotoSakuraMod.Relics.SealedBook;
import KinomotoSakuraMod.Utility.KSMOD_Utility;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.util.ArrayList;

@SpirePatch(clz = SealedBook.class, method = "atBattleStart")
public class TestInsertPatch
{
    @SpireInsertPatch(locator = Locator.class)
    public static void Insert(SealedBook instance)
    {
        KSMOD_Utility.Logger.info("InsertTest success.");
    }

    private static class Locator extends SpireInsertLocator
    {
        public int[] Locate(CtBehavior method) throws CannotCompileException, PatchingException
        {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(SealedBook.class, "STR2");
            int[] lines = LineFinder.findAllInOrder(method, new ArrayList<Matcher>(), finalMatcher);
            // return new int[]{lines[0]};
            return  lines;
        }
    }

}
