package KinomotoSakuraMod.Patches;

import KinomotoSakuraMod.Cards.KSMOD_AbstractMagicCard;
import KinomotoSakuraMod.Characters.KinomotoSakura;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.select.HandCardSelectScreen;
import javassist.CtBehavior;

public class KSMOD_HandSelectScreenHook
{
    @SpirePatch(clz = HandCardSelectScreen.class, method = "open", paramtypez = {
            String.class,
            int.class,
            boolean.class,
            boolean.class,
            boolean.class,
            boolean.class,
            boolean.class
    })
    public static class open_0
    {
        public static SpireReturn<Object> Prefix(HandCardSelectScreen screen, String msg, int amount, boolean anyNumber, boolean canPickZero, boolean forTransform, boolean forUpgrade, boolean upTo)
        {
            KSMOD_AbstractMagicCard.isHandSelectScreenOpened = true;
            if (AbstractDungeon.player != null && AbstractDungeon.player instanceof KinomotoSakura && AbstractDungeon.player.hand != null)
            {
               for (AbstractCard card: AbstractDungeon.player.hand.group)
               {
                   card.initializeDescription();
               }
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = HandCardSelectScreen.class, method = "open", paramtypez = {
            String.class,
            int.class,
            boolean.class,
            boolean.class
    })
    public static class open_1
    {
        public static SpireReturn<Object> Prefix(HandCardSelectScreen screen, String msg, int amount, boolean anyNumber, boolean canPickZero)
        {
            KSMOD_AbstractMagicCard.isHandSelectScreenOpened = true;
            if (AbstractDungeon.player != null && AbstractDungeon.player instanceof KinomotoSakura && AbstractDungeon.player.hand != null)
            {
                for (AbstractCard card: AbstractDungeon.player.hand.group)
                {
                    card.initializeDescription();
                }
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = HandCardSelectScreen.class, method = "update", paramtypez = {})
    public static class closeCurrentScreen
    {
        @SpireInsertPatch(locator = Locator.class)
        public static void Insert(HandCardSelectScreen screen)
        {
            KSMOD_AbstractMagicCard.isHandSelectScreenOpened = false;
        }
    }

    private static class Locator extends SpireInsertLocator
    {
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
        {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractDungeon.class, "closeCurrentScreen");
            int[] loc = LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
            return new int[] {
                    loc[1],
                    loc[2],
                    loc[3]
            };
        }
    }
}
