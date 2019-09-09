package KinomotoSakuraMod.Patches;

import KinomotoSakuraMod.Relics.KSMOD_MoonBell;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import javassist.CtBehavior;

public class KSMOD_MoonBellPatch
{
    @SpirePatch(clz = AbstractPlayer.class, method = "damage", paramtypez = {
            DamageInfo.class
    })
    public static class damage
    {
        @SpireInsertPatch(locator = Locator.class)
        public static SpireReturn<Object> Insert(AbstractPlayer player, DamageInfo info) throws NoSuchFieldException, IllegalAccessException
        {
            if (player.hasRelic(KSMOD_MoonBell.RELIC_ID) && !((KSMOD_MoonBell) player.getRelic(KSMOD_MoonBell.RELIC_ID)).hasUsed())
            {
                player.currentHealth = 0;
                player.getRelic(KSMOD_MoonBell.RELIC_ID).onTrigger();
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    private static class Locator extends SpireInsertLocator
    {
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
        {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "isDead");
            int[] loc = LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
            return new int[] {loc[0]};
        }
    }
}
