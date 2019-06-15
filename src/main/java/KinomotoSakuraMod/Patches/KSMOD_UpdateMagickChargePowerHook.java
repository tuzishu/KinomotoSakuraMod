package KinomotoSakuraMod.Patches;

import KinomotoSakuraMod.Cards.KSMOD_AbstractMagicCard;
import KinomotoSakuraMod.Utility.KSMOD_Utility;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class KSMOD_UpdateMagickChargePowerHook
{
    @SpirePatch(clz = ReducePowerAction.class, method = "update", paramtypez = {})
    public static class ReducePowerActionHook
    {
        public static void Postfix(ReducePowerAction action) throws NoSuchFieldException, IllegalAccessException
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

    @SpirePatch(clz = RemoveSpecificPowerAction.class, method = "update", paramtypez = {})
    public static class RemoveSpecificPowerActionHook
    {
        public static void Postfix(RemoveSpecificPowerAction action) throws NoSuchFieldException, IllegalAccessException
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

        public static void PostMessage(RemoveSpecificPowerAction action, AbstractCard card) throws NoSuchFieldException, IllegalAccessException
        {
            if (card instanceof KSMOD_AbstractMagicCard)
            {
                AbstractPower reduceMe = null;
                String powerToRemove = (String) KSMOD_Utility.GetFieldByReflect(RemoveSpecificPowerAction.class, "powerToRemove").get(action);
                AbstractPower powerInstance = (AbstractPower) KSMOD_Utility.GetFieldByReflect(RemoveSpecificPowerAction.class, "powerInstance").get(action);
                if (powerToRemove != null)
                {
                    reduceMe = action.target.getPower(powerToRemove);
                }
                else if (powerInstance != null)
                {
                    reduceMe = powerInstance;
                }
                ((KSMOD_AbstractMagicCard) card).receivePostPowerRemoveSubscriber(reduceMe);
            }
        }
    }
}
