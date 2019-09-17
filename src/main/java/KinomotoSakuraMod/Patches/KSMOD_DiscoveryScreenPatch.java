package KinomotoSakuraMod.Patches;

import KinomotoSakuraMod.Actions.KSMOD_CompassAction;
import KinomotoSakuraMod.Actions.KSMOD_WriteEmptySpellAction;
import KinomotoSakuraMod.Cards.SpellCard.SpellCardFengHua;
import KinomotoSakuraMod.Cards.SpellCard.SpellCardHuoShen;
import KinomotoSakuraMod.Cards.SpellCard.SpellCardLeiDi;
import KinomotoSakuraMod.Cards.SpellCard.SpellCardShuiLong;
import KinomotoSakuraMod.Utility.KSMOD_Utility;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.ui.buttons.SkipCardButton;
import javassist.CtBehavior;

public class KSMOD_DiscoveryScreenPatch
{
    @SpirePatch(clz = CardRewardScreen.class, method = "discoveryOpen", paramtypez = {})
    public static class CompassAction
    {
        @SpireInsertPatch(locator = Locator1.class)
        public static void Insert(CardRewardScreen screen) throws NoSuchFieldException, IllegalAccessException
        {
            StackTraceElement[] element = Thread.currentThread().getStackTrace();
            if (element[3].toString().contains(KSMOD_CompassAction.class.getSimpleName()))
            {
                SkipCardButton skipButton = (SkipCardButton) KSMOD_Utility.GetFieldByReflect(CardRewardScreen.class, "skipButton").get(screen);
                skipButton.show();
            }
        }
    }
    private static class Locator1 extends SpireInsertLocator
    {
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
        {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(CardRewardScreen.class, "onCardSelect");
            int[] loc = LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
            return new int[] {loc[0]};
        }
    }

    @SpirePatch(clz = CardRewardScreen.class, method = "discoveryOpen", paramtypez = {})
    public static class WriteEmptySpellAction
    {
        @SpireInsertPatch(locator = Locator2.class)
        public static void Insert(CardRewardScreen screen) throws NoSuchFieldException, IllegalAccessException
        {
            StackTraceElement[] element = Thread.currentThread().getStackTrace();
            if (element[3].toString().contains(KSMOD_WriteEmptySpellAction.class.getSimpleName()))
            {
                screen.rewardGroup.clear();
                screen.rewardGroup.add(new SpellCardHuoShen());
                screen.rewardGroup.add(new SpellCardLeiDi());
                screen.rewardGroup.add(new SpellCardFengHua());
                screen.rewardGroup.add(new SpellCardShuiLong());
            }
        }
    }
    private static class Locator2 extends SpireInsertLocator
    {
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
        {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractDungeon.class, "isScreenUp");
            int[] loc = LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
            return new int[] {loc[0]};
        }
    }
}
