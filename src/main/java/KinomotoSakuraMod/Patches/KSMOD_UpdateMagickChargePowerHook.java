package KinomotoSakuraMod.Patches;

import KinomotoSakuraMod.Cards.KSMOD_AbstractMagicCard;
import KinomotoSakuraMod.Utility.KSMOD_Utility;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import javassist.CtBehavior;

public class KSMOD_UpdateMagickChargePowerHook
{
    public static class ApplyPowerActionHook
    {
        @SpirePatch(clz = ApplyPowerAction.class, method = "update", paramtypez = {})
        public static class update
        {
            @SpireInsertPatch(locator = Locator.class)
            public static void Insert(ApplyPowerAction action) throws NoSuchFieldException, IllegalAccessException
            {
                float duration = KSMOD_Utility.GetFieldByReflect(AbstractGameAction.class, "duration").getFloat(action);
                float startingDuration = KSMOD_Utility.GetFieldByReflect(ApplyPowerAction.class, "startingDuration").getFloat(action);
                if (duration == startingDuration)
                {
                    for (AbstractCard card : AbstractDungeon.player.hand.group)
                    {
                        PostMessage(action, card);
                    }
                    for (AbstractCard card : AbstractDungeon.player.drawPile.group)
                    {
                        PostMessage(action, card);
                    }
                    for (AbstractCard card : AbstractDungeon.player.discardPile.group)
                    {
                        PostMessage(action, card);
                    }
                    for (AbstractCard card : AbstractDungeon.player.exhaustPile.group)
                    {
                        PostMessage(action, card);
                    }
                }
            }
        }

        private static class Locator extends SpireInsertLocator
        {
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
            {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(ApplyPowerAction.class, "tickDuration");
                int[] loc = LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
                return new int[] {loc[loc.length - 1]};
            }
        }

        public static void PostMessage(ApplyPowerAction action, AbstractCard card) throws NoSuchFieldException, IllegalAccessException
        {
            if (card instanceof KSMOD_AbstractMagicCard)
            {
                AbstractPower applyMe = null;
                AbstractPower powerToApply = (AbstractPower) KSMOD_Utility.GetFieldByReflect(ApplyPowerAction.class, "powerToApply").get(action);
                if (powerToApply != null)
                {
                    applyMe = powerToApply;
                }
                ((KSMOD_AbstractMagicCard) card).receivePostPowerApplySubscriber(applyMe);
            }
        }
    }

    public static class ReducePowerActionHook
    {
        @SpirePatch(clz = ReducePowerAction.class, method = "update", paramtypez = {})
        public static class update
        {
            @SpireInsertPatch(locator = Locator.class)
            public static void Insert(ReducePowerAction action) throws NoSuchFieldException, IllegalAccessException
            {
                float duration = KSMOD_Utility.GetFieldByReflect(AbstractGameAction.class, "duration").getFloat(action);
                if (duration == Settings.ACTION_DUR_FAST)
                {
                    for (AbstractCard card : AbstractDungeon.player.hand.group)
                    {
                        PostMessage(action, card);
                    }
                    for (AbstractCard card : AbstractDungeon.player.drawPile.group)
                    {
                        PostMessage(action, card);
                    }
                    for (AbstractCard card : AbstractDungeon.player.discardPile.group)
                    {
                        PostMessage(action, card);
                    }
                    for (AbstractCard card : AbstractDungeon.player.exhaustPile.group)
                    {
                        PostMessage(action, card);
                    }
                }
            }
        }

        private static class Locator extends SpireInsertLocator
        {
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
            {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(ReducePowerAction.class, "tickDuration");
                int[] loc = LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
                return new int[] {loc[loc.length - 1]};
            }
        }

        public static void PostMessage(ReducePowerAction action, AbstractCard card) throws NoSuchFieldException, IllegalAccessException
        {
            if (card instanceof KSMOD_AbstractMagicCard)
            {
                AbstractPower reduceMe = null;
                String powerID = (String) KSMOD_Utility.GetFieldByReflect(ReducePowerAction.class, "powerID").get(action);
                AbstractPower powerInstance = (AbstractPower) KSMOD_Utility.GetFieldByReflect(ReducePowerAction.class, "powerInstance").get(action);
                if (powerID != null)
                {
                    reduceMe = action.target.getPower(powerID);
                }
                else if (powerInstance != null)
                {
                    reduceMe = powerInstance;
                }
                ((KSMOD_AbstractMagicCard) card).receivePostPowerReduceSubscriber(reduceMe);
            }
        }
    }

    public static class RemoveSpecificPowerActionHook
    {
        @SpirePatch(clz = RemoveSpecificPowerAction.class, method = "update", paramtypez = {})
        public static class update
        {
            @SpireInsertPatch(locator = Locator.class)
            public static void Insert(RemoveSpecificPowerAction action) throws NoSuchFieldException, IllegalAccessException
            {
                float duration = KSMOD_Utility.GetFieldByReflect(AbstractGameAction.class, "duration").getFloat(action);
                if (duration == 0.1F)
                {
                    for (AbstractCard card : AbstractDungeon.player.hand.group)
                    {
                        PostMessage(action, card);
                    }
                    for (AbstractCard card : AbstractDungeon.player.drawPile.group)
                    {
                        PostMessage(action, card);
                    }
                    for (AbstractCard card : AbstractDungeon.player.discardPile.group)
                    {
                        PostMessage(action, card);
                    }
                    for (AbstractCard card : AbstractDungeon.player.exhaustPile.group)
                    {
                        PostMessage(action, card);
                    }
                }
            }
        }

        private static class Locator extends SpireInsertLocator
        {
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
            {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(RemoveSpecificPowerAction.class, "tickDuration");
                int[] loc = LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
                return new int[] {loc[loc.length - 1]};
            }
        }

        public static void PostMessage(RemoveSpecificPowerAction action, AbstractCard card) throws NoSuchFieldException, IllegalAccessException
        {
            if (card instanceof KSMOD_AbstractMagicCard)
            {
                AbstractPower removeMe = null;
                String powerID = (String) KSMOD_Utility.GetFieldByReflect(RemoveSpecificPowerAction.class, "powerToRemove").get(action);
                AbstractPower powerInstance = (AbstractPower) KSMOD_Utility.GetFieldByReflect(RemoveSpecificPowerAction.class, "powerInstance").get(action);
                if (powerID != null)
                {
                    removeMe = action.target.getPower(powerID);
                }
                else if (powerInstance != null)
                {
                    removeMe = powerInstance;
                }
                ((KSMOD_AbstractMagicCard) card).receivePostPowerRemoveSubscriber(removeMe);
            }
        }
    }
}
