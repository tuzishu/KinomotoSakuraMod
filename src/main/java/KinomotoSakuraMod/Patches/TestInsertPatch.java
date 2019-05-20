package KinomotoSakuraMod.Patches;

import KinomotoSakuraMod.Relics.SealedBook;
import KinomotoSakuraMod.Utility.KSMOD_Utility;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import javassist.CannotCompileException;
import javassist.CtBehavior;

@SpirePatch(clz = SealedBook.class, method = "atBattleStart", paramtypez = {})
public class TestInsertPatch
{
    @SpireInsertPatch(locator = TestInsertPatch.Locator.class, localvars = {})
    public static void Insert(SealedBook sealedBook)
    {
        // Matcher m = Pattern.compile("int").matcher(tmp[0]);
        KSMOD_Utility.Logger.info("Insert");
    }

    private static class Locator extends SpireInsertLocator
    {
        public int[] Locate(CtBehavior method) throws CannotCompileException, PatchingException
        {
            Matcher matcher = new Matcher.MethodCallMatcher(SealedBook.class, "addToBottom");
            return LineFinder.findInOrder(method, matcher);
        }
    }
}
