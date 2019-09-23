package KinomotoSakuraMod.Patches;

import KinomotoSakuraMod.Powers.KSMOD_FloatPower;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import javassist.CtBehavior;

public class KSMOD_PlayerAttackedHook
{
    @SpirePatch(clz = AbstractPlayer.class, method = "damage", paramtypez = {DamageInfo.class})
    public static class damage
    {
        @SpireInsertPatch(locator = Locator.class)
        public static void Insert(AbstractPlayer player, DamageInfo info)
        {
            // if (player.hasPower(KSMOD_FloatPower.POWER_ID))
            // {
            //     ((KSMOD_FloatPower) player.getPower(KSMOD_FloatPower.POWER_ID)).CustomOnAttacked(info);
            // }
        }
    }

    private static class Locator extends SpireInsertLocator
    {
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
        {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "currentHealth");
            int[] loc = LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
            return new int[] {
                    loc[0]
            };
        }
    }
}
