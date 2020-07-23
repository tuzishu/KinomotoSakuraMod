package KinomotoSakuraMod.Patches;

import KinomotoSakuraMod.Cards.ClowCard.ClowCardTheShield;
import KinomotoSakuraMod.Cards.ClowCard.ClowCardTheSword;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import javassist.CtBehavior;

public class KSMOD_SwordShieldMISCPatch
{
    @SpirePatch(clz = CardLibrary.class, method = "getCopy", paramtypez = {String.class, int.class, int.class})
    public static class CardLibraryPatch
    {
        @SpireInsertPatch(locator = Locator.class, localvars = "retVal")
        public static void Insert(String key, int upgradeTime, int misc, AbstractCard retVal)
        {
            if (retVal.cardID.equals(ClowCardTheSword.ID))
            {
                retVal.baseDamage = ((ClowCardTheSword) retVal).GetDamage(retVal);
                retVal.damage = retVal.baseDamage;
                retVal.initializeDescription();
            }
            if (retVal.cardID.equals(ClowCardTheShield.ID))
            {
                retVal.baseBlock = ((ClowCardTheShield) retVal).GetBlock(retVal);
                retVal.block = retVal.baseBlock;
                retVal.initializeDescription();
            }
        }
    }

    private static class Locator extends SpireInsertLocator
    {
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
        {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(String.class, "equals");
            int[] loc = LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
            return new int[]{loc[0]};
        }
    }
}
