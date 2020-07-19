package KinomotoSakuraMod.Patches;

import KinomotoSakuraMod.Cards.KSMOD_AbstractMagicCard;
import KinomotoSakuraMod.Utility.KSMOD_ImageConst;
import KinomotoSakuraMod.Utility.KSMOD_ReflectTool;
import KinomotoSakuraMod.Utility.KSMOD_RenderTool;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;

@SpirePatch(clz = AbstractCard.class, method = "renderCardBg", paramtypez = {SpriteBatch.class, float.class, float.class})
public class KSMOD_RenderCardBgPatch
{
    public static SpireReturn<Object> Prefix(AbstractCard card, SpriteBatch sb, float x, float y) throws NoSuchFieldException, IllegalAccessException
    {
        if (!(card instanceof KSMOD_AbstractMagicCard))
        {
            return SpireReturn.Continue();
        }

        float renderedPortionProportionToTop = KSMOD_ReflectTool.GetFieldByReflect(KSMOD_AbstractMagicCard.class,
                "renderedPortionProportionToTop").getFloat(card);
        if (card.color == KSMOD_CustomCardColor.CLOWCARD_COLOR && renderedPortionProportionToTop < 1F)
        {
            sb.setColor(Color.WHITE);
            TextureAtlas.AtlasRegion img = KSMOD_RenderTool.GetAtlasRegion(KSMOD_ImageConst.CLOWCARD_BG,
                    renderedPortionProportionToTop);
            sb.draw(img,
                    x + img.offsetX - img.packedWidth / 2.0F,
                    y + img.offsetY - img.packedHeight / 2.0F + KSMOD_ImageConst.CLOWCARD_BG.getHeight() * 0.5F * (1F - renderedPortionProportionToTop) * Settings.scale,
                    img.packedWidth / 2.0F - img.offsetX,
                    img.packedHeight / 2.0F - img.offsetY,
                    img.packedWidth,
                    img.packedHeight,
                    card.drawScale * Settings.scale,
                    card.drawScale * Settings.scale,
                    card.angle);

            return SpireReturn.Return(null);
        }
        else
        {
            return SpireReturn.Continue();
        }
    }
}
