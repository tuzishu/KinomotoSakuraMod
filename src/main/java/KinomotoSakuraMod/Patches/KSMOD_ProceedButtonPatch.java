package KinomotoSakuraMod.Patches;

import KinomotoSakuraMod.Events.KSMOD_TheSealedCardEvent;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.buttons.ProceedButton;
import javassist.CtBehavior;

public class KSMOD_ProceedButtonPatch
{
    @SpirePatch(clz = ProceedButton.class, method = "update", paramtypez = {})
    public static class Update
    {
        @SpireInsertPatch(locator = Locator.class)
        public static void Insert(ProceedButton button) throws NoSuchFieldException, IllegalAccessException
        {
            if (AbstractDungeon.getCurrRoom().event instanceof KSMOD_TheSealedCardEvent)
            {
                AbstractDungeon.dungeonMapScreen.open(false);
                AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.COMBAT_REWARD;
            }
        }
    }

    private static class Locator extends SpireInsertLocator
    {
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
        {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(ProceedButton.class, "hide");
            int[] loc = LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
            return new int[]{loc[0]};
        }
    }
}
