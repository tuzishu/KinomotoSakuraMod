package KinomotoSakuraMod.Effects.Turn;

import KinomotoSakuraMod.Cards.KSMOD_AbstractMagicCard;
import KinomotoSakuraMod.Utility.KSMOD_ImageConst;
import KinomotoSakuraMod.Utility.KSMOD_LoggerTool;
import KinomotoSakuraMod.Utility.KSMOD_RenderTool;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class KSMOD_TurningMistEffect extends AbstractGameEffect
{
    private static Texture img = KSMOD_ImageConst.SILHOUETTE;
    private KSMOD_AbstractMagicCard card;
//    private float a;
//    private float b;

    public KSMOD_TurningMistEffect(KSMOD_AbstractMagicCard card, float duration)
    {
        super();
        this.color = new Color(0xFFFFFF66);
        this.card = card;
//        this.a = MathUtils.random(0.3F,0.8F);
//        this.b = MathUtils.random(0.1F,0.6F);
        this.duration = duration;
        this.startingDuration = this.duration;
    }

    public void update()
    {
        if (this.duration < 0.0F)
        {
            this.isDone = true;
        }
        this.duration -= Gdx.graphics.getDeltaTime();
    }

    @Override
    public void render(SpriteBatch sb)
    {
        sb.setBlendFunction(770, 1);
        sb.setColor(this.color);
        TextureAtlas.AtlasRegion atl = KSMOD_RenderTool.GetAtlasRegionBottom(img, 1F - card.renderedPortionProportionToTop);
        sb.draw(atl,
                card.current_x - img.getWidth() * 0.5F,
                card.current_y - img.getHeight() * 0.5F,
                (float) atl.getRegionWidth() / 2.0F,
                (float) atl.getRegionHeight() / 2.0F,
                (float) atl.getRegionWidth(),
                (float) atl.getRegionHeight(),
                1F,
                1F,
                0F);
        sb.setBlendFunction(770, 771);
    }

    @Override
    public void dispose()
    {
    }

//    private float cosineInterpolate(float a, float b, float x)
//    {
//        double f = (1F - Math.cos(x * Math.PI)) * 0.5F;
//        return (float) (a * (1F - f) + b * f);
//    }
}
