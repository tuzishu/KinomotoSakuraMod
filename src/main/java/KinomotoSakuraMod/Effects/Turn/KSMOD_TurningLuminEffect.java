package KinomotoSakuraMod.Effects.Turn;

import KinomotoSakuraMod.Cards.KSMOD_AbstractMagicCard;
import KinomotoSakuraMod.Utility.KSMOD_ImageConst;
import KinomotoSakuraMod.Utility.KSMOD_RenderTool;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class KSMOD_TurningLuminEffect extends AbstractGameEffect
{
    private static TextureAtlas.AtlasRegion atl = KSMOD_RenderTool.GetAtlasRegion(KSMOD_ImageConst.TURNING_LUMIN);
    private static int controlPointAmountPerWidth = 8;
    private static float driftTimePerWidth = 0.25F;
    private static int frequency = 9;//9每线3个像素，14每线2个像素
    private static float amplitude = 160F * Settings.scale;
    private static float minIntercept = 40F * Settings.scale;
    private static float anchorShake = 4F * Settings.scale;
    private static float scaleX = 1.2F;
    private static float scaleY = 1F;
    private static Color lumin_color = new Color(0xF77BFCFF);

    private int currentOffset = 0;
    private KSMOD_AbstractMagicCard card;
    private float[] controlPoints;
    private float[] pathPoints;

    public KSMOD_TurningLuminEffect(KSMOD_AbstractMagicCard card, float duration)
    {
        this.startingDuration = duration;
        this.duration = this.startingDuration;
        this.card = card;
        this.controlPoints = GetControlPoints();
        this.pathPoints = GetPoints();
    }

    @Override
    public void update()
    {
        if (this.duration > 0)
        {
            float dt = this.startingDuration - this.duration;
            currentOffset = MathUtils.ceil(dt / driftTimePerWidth * controlPointAmountPerWidth * frequency);
        }

        if (this.duration < 0)
        {
            this.isDone = true;
        }
        this.duration -= Gdx.graphics.getDeltaTime();
    }

    @Override
    public void render(SpriteBatch sb)
    {
        if (this.duration > 0)
        {
            sb.setColor(lumin_color);
            sb.setBlendFunction(770, 1);
            float offsetY = KSMOD_AbstractMagicCard.IMG_HEIGHT * (0.5F - card.renderedPortionProportionToTop) * Settings.scale + MathUtils.random(
                    -anchorShake,
                    anchorShake);// - atl.getRegionHeight() * 0.5F
            for (int i = 0; i < controlPointAmountPerWidth * frequency; i++)
            {
                float propX = ((float) i / (controlPointAmountPerWidth * frequency) - 0.5F);
                float offsetX = KSMOD_AbstractMagicCard.IMG_WIDTH * Settings.scale * propX - atl.getRegionWidth() * 0.5F;
                float angle = -MathUtils.atan2(offsetX, offsetY) * MathUtils.radiansToDegrees;
                sb.draw(atl,
                        card.current_x + offsetX,
                        card.current_y + offsetY,
                        atl.packedWidth * 0.5F,
                        0F,
                        atl.packedWidth,
                        pathPoints[i + currentOffset],
                        scaleX * Settings.scale,
                        scaleY * Settings.scale * GetScaleByX(propX),
                        angle);
            }
            sb.setBlendFunction(770, 771);
        }
    }

    @Override
    public void dispose()
    {
    }

    private float[] GetControlPoints()
    {
        int pointAmount = MathUtils.ceil(startingDuration / driftTimePerWidth + 2F) * controlPointAmountPerWidth;
        float[] cps = new float[pointAmount];
        for (int i = 0; i < cps.length; i++)
        {
            cps[i] = MathUtils.random(0F, 1F);
        }
        return cps;
    }

    private float[] GetPoints()
    {
        float[] ps = new float[(controlPoints.length - 1) * frequency + 1];
        for (int i = 0; i < controlPoints.length - 2; i++)
        {
            for (int j = 0; j < frequency; j++)
            {
                ps[i * frequency + j] = CosineInterpolate(controlPoints[i],
                        controlPoints[i + 1],
                        ((float) j) / frequency) + minIntercept;
            }
        }
        ps[ps.length - 1] = controlPoints[controlPoints.length - 1];
        return ps;
    }

    private float CosineInterpolate(float start, float end, float x)
    {
        double ft = x * Math.PI;
        double f = (1 - Math.cos(ft)) * 0.5F;
        return (float) (start * (1 - f) + end * f) * amplitude;
    }

    private float GetScaleByX(float x)
    {
        return MathUtils.clamp((float) Math.sqrt(1 - 0.9F * x * x), 0F, 1F);
    }
}