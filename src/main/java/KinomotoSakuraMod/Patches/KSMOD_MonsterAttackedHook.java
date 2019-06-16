package KinomotoSakuraMod.Patches;

import KinomotoSakuraMod.Powers.KSMOD_SleepPower;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import javassist.CtBehavior;

public class KSMOD_MonsterAttackedHook
{
    @SpirePatch(clz = AbstractMonster.class, method = "damage", paramtypez = {DamageInfo.class})
    public static class damage
    {
        @SpireInsertPatch(locator = Locator.class)
        public static void Insert(AbstractMonster monster, DamageInfo info)
        {
            if (monster.hasPower(KSMOD_SleepPower.POWER_ID))
            {
                ((KSMOD_SleepPower) monster.getPower(KSMOD_SleepPower.POWER_ID)).CustomAttacked(info);
            }
        }
    }

    private static class Locator extends SpireInsertLocator
    {
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
        {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractMonster.class, "currentHealth");
            int[] loc = LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
            return new int[] {
                    loc[0]
            };
        }
    }
}
