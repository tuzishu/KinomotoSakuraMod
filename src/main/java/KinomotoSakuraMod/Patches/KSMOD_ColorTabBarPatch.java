package KinomotoSakuraMod.Patches;

import KinomotoSakuraMod.Characters.KinomotoSakura;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.screens.mainMenu.ColorTabBar;
import javassist.CtBehavior;

import static KinomotoSakuraMod.Patches.KSMOD_CustomCharacter.KINOMOTOSAKURA;

public class KSMOD_ColorTabBarPatch
{
    @SpirePatch(cls = "basemod.patches.com.megacrit.cardcrawl.screens.mainMenu.ColorTabBar.ColorTabBarFix$Render", method = "Insert", paramtypez = {ColorTabBar.class, SpriteBatch.class, float.class, ColorTabBar.CurrentTab.class}, optional = true)
    public static class Render
    {
        @SpireInsertPatch(locator = Locator.class, localvars = {"tabName", "playerClass"})
        public static void Insert(ColorTabBar _instance, SpriteBatch sb, float y, ColorTabBar.CurrentTab curTab, @ByRef String[] tabName, AbstractPlayer.PlayerClass playerClass)
        {
            if (playerClass == KINOMOTOSAKURA)
            {
                tabName[0] = KinomotoSakura.GetCharactorStrings().TEXT[1];
            }
            else
            {
                tabName[0] = tabName[0].replace("Sakuracard_color", KinomotoSakura.GetCharactorStrings().TEXT[2]);
                tabName[0] = tabName[0].replace("Spell_color", KinomotoSakura.GetCharactorStrings().TEXT[3]);
            }
        }
    }

    private static class Locator extends SpireInsertLocator
    {
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
        {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(FontHelper.class, "renderFontCentered");
            int[] loc = LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
            return new int[]{loc[0]};
        }
    }
}
