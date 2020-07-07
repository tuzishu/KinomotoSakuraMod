package KinomotoSakuraMod.Patches;

import KinomotoSakuraMod.Characters.KinomotoSakura;
import KinomotoSakuraMod.Powers.KSMOD_TwinPower_SakuraCard;
import KinomotoSakuraMod.Utility.KSMOD_ReflectTool;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAndDeckAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class KSMOD_MakeTempCardPatch
{
    public static boolean HasTwinPower()
    {
        AbstractPower power = AbstractDungeon.player.getPower(KSMOD_TwinPower_SakuraCard.POWER_ID);
        if (AbstractDungeon.player != null && AbstractDungeon.player instanceof KinomotoSakura)
        {
            return AbstractDungeon.player.hasPower(KSMOD_TwinPower_SakuraCard.POWER_ID);
        }
        return false;
    }

    public static KSMOD_TwinPower_SakuraCard GetTwinPower()
    {
        KSMOD_TwinPower_SakuraCard power = (KSMOD_TwinPower_SakuraCard) AbstractDungeon.player.getPower(KSMOD_TwinPower_SakuraCard.POWER_ID);
        return power;
    }

    public static boolean CheckCard(AbstractCard card)
    {
        return card.color == KSMOD_CustomCardColor.CLOWCARD_COLOR && card.cost >= 0;
    }

    @SpirePatch(clz = MakeTempCardInHandAction.class, method = "makeNewCard", paramtypez = {})
    public static class MakeTempCardInHandAction_makeNewCard
    {
        public static SpireReturn<AbstractCard> Perfix(MakeTempCardInHandAction action) throws NoSuchFieldException, IllegalAccessException
        {
            AbstractCard c = (AbstractCard) KSMOD_ReflectTool.GetFieldByReflect(MakeTempCardInHandAction.class, "c").get(action);
            if (HasTwinPower() && CheckCard(c))
            {
                boolean sameUUID = KSMOD_ReflectTool.GetFieldByReflect(MakeTempCardInHandAction.class, "sameUUID").getBoolean(action);
                AbstractCard card = sameUUID ? c.makeSameInstanceOf() : c.makeStatEquivalentCopy();
                GetTwinPower().onMakeTempCard(card);
                return SpireReturn.Return(card);
            }
            else
            {
                return SpireReturn.Continue();
            }
        }
    }

    @SpirePatch(clz = MakeTempCardInDiscardAction.class, method = "makeNewCard", paramtypez = {})
    public static class MakeTempCardInDiscardAction_makeNewCard
    {
        public static SpireReturn<AbstractCard> Perfix(MakeTempCardInDiscardAction action) throws NoSuchFieldException, IllegalAccessException
        {
            AbstractCard cardToMake = (AbstractCard) KSMOD_ReflectTool.GetFieldByReflect(MakeTempCardInDiscardAction.class, "cardToMake").get(action);
            if (HasTwinPower() && CheckCard(cardToMake))
            {
                boolean sameUUID = KSMOD_ReflectTool.GetFieldByReflect(MakeTempCardInDiscardAction.class, "sameUUID").getBoolean(action);
                AbstractCard card = sameUUID ? cardToMake.makeSameInstanceOf() : cardToMake.makeStatEquivalentCopy();
                GetTwinPower().onMakeTempCard(card);
                return SpireReturn.Return(card);
            }
            else
            {
                return SpireReturn.Continue();
            }
        }
    }

    @SpirePatch(clz = MakeTempCardInDiscardAndDeckAction.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {AbstractCard.class})
    public static class MakeTempCardInDiscardAndDeckAction_CONSTRUCTOR
    {
        public static void Postfix(MakeTempCardInDiscardAndDeckAction action, AbstractCard card) throws NoSuchFieldException, IllegalAccessException
        {
            AbstractCard cardToMake = (AbstractCard) KSMOD_ReflectTool.GetFieldByReflect(MakeTempCardInDiscardAndDeckAction.class, "cardToMake").get(action);
            if (HasTwinPower() && CheckCard(cardToMake))
            {
                GetTwinPower().onMakeTempCard(cardToMake);
            }
        }
    }

    @SpirePatch(clz = MakeTempCardInDrawPileAction.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {
            AbstractCard.class,
            int.class,
            boolean.class,
            boolean.class,
            boolean.class,
            float.class,
            float.class
    })
    public static class MakeTempCardInDrawPileAction_CONSTRUCTOR
    {
        public static void Postfix(MakeTempCardInDrawPileAction action, AbstractCard card, int amount, boolean randomSpot, boolean autoPosition, boolean toBottom, float cardX, float cardY) throws NoSuchFieldException, IllegalAccessException
        {
            AbstractCard cardToMake = (AbstractCard) KSMOD_ReflectTool.GetFieldByReflect(MakeTempCardInDrawPileAction.class, "cardToMake").get(action);
            if (HasTwinPower() && CheckCard(cardToMake))
            {
                GetTwinPower().onMakeTempCard(cardToMake);
            }
        }
    }
}
