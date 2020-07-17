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
    public static TextureAtlas.AtlasRegion atl = KSMOD_RenderTool.GetAtlasRegion(KSMOD_ImageConst.TURNING_LUMIN);
    public static int controlPointAmountPerWidth = 8;
    public static float driftTimePerWidth = 0.25F;
    public static int frequency = 9;//9每线3个像素，14每线2个像素
    public static float amplitude = 200F * Settings.scale;
    public static float minIntercept = 100F * Settings.scale;
    public static float anchorShake = 2.5F * Settings.scale;
    public static float scaleX = 1F;
    public static float scaleY = 1F;

    private KSMOD_AbstractMagicCard card;
    private float[] controlPoints;
    private float[] pathPoints;
    private int currentOffset = 0;

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
        sb.setColor(Color.WHITE);
        sb.setBlendFunction(770, 1);
        for (int i = 0; i < controlPointAmountPerWidth * frequency; i++)
        {
            sb.draw(atl,
                    card.current_x - atl.getRegionWidth() * 0.5F + i * KSMOD_AbstractMagicCard.IMG_WIDTH / (controlPointAmountPerWidth * frequency),
                    card.current_y - atl.getRegionWidth() * 0.5F + MathUtils.random(-anchorShake, anchorShake),
                    atl.packedWidth * 0.5F,
                    atl.packedHeight * 0.5F,
                    atl.packedWidth,
                    pathPoints[i + currentOffset],
                    scaleX * Settings.scale,
                    scaleY * Settings.scale,
                    0F);
        }
        sb.setBlendFunction(770, 771);
    }

    @Override
    public void dispose()
    {
    }

    private float[] GetControlPoints()
    {
        int pointAmount = MathUtils.ceil(startingDuration / driftTimePerWidth + 1F) * controlPointAmountPerWidth;
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
}