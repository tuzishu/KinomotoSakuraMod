package KinomotoSakuraMod.Patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import oilfishmod.OilFishMod;
import oilfishmod.characters.DragonServant;

@SpirePatch(clz = AbstractCard.class, method = "renderBannerImage", paramtypez = {
        SpriteBatch.class,
        float.class,
        float.class
})

public class CustomBannerPatch
{
    public static SpireReturn<Object> Prefix(AbstractCard the_AbstractCard, SpriteBatch sb, float x, float y)
    {
        if (!(AbstractDungeon.player instanceof DragonServant) || !the_AbstractCard.hasTag(OilFishMod.monstercard) && !the_AbstractCard.hasTag(OilFishMod.maho) && !the_AbstractCard.hasTag(OilFishMod.trapcard))
        {
            return  SpireReturn.Continue();
        }
        else
        {
            return SpireReturn.Return((Object) null);
        }
    }
}
