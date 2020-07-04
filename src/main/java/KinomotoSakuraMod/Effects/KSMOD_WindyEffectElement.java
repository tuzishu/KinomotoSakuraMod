package KinomotoSakuraMod.Effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class KSMOD_WindyEffectElement extends AbstractGameEffect
{
    private TextureAtlas.AtlasRegion img;
    private float pA;
    private float pB;
    private float pC;
    private float x;
    private float y;
    private float scaleL;
    private float speedX;
    private float startingDuration;
    private float delayTimer = MathUtils.random(0.1F);

    public KSMOD_WindyEffectElement(float startingDuration)
    {
        this.setImg();
        this.startingDuration = startingDuration;
        this.duration = this.startingDuration;
        do
        {
            GetRandomPath();
        } while (!CheckPathIsInside());
        this.x = MathUtils.random(-Settings.WIDTH * 0.2F, Settings.WIDTH * 0.4F);
        this.y = getY(this.x);
        this.rotation = getRotation(this.x);
        this.scale = MathUtils.random(0.3F, 0.6F);
        this.scaleL = MathUtils.random(1.5F, 3F);
        this.color = Color.SKY;
        this.color.a = MathUtils.random(0.1F, 0.6F);
        this.speedX = Settings.WIDTH / MathUtils.random(0.5F, 1.2F);
    }

    private void GetRandomPath()
    {
        this.pA = MathUtils.random(2F, 5F);
        this.pB = MathUtils.random(3F, 6F);
        this.pC = MathUtils.random(8F, 16F);
    }

    private boolean CheckPathIsInside()
    {
        float y0 = getY(0);
        float y1 = getY(1920);
        if ((y0 >= 0 && y0 <= 1080) || (y1 >= 0 && y1 <= 1080))
        {
            return true;
        }
        return false;
    }

    private float getY(float x)
    {
        return (-pA * MathUtils.log(MathUtils.E, 0.01F * x + pB) + pC) * 100F;
    }

    private float getRotation(float x)
    {
        return MathUtils.atan2(-pA / (x * 0.01F + pB), 1) * MathUtils.radiansToDegrees;
    }

    public void update()
    {
        if (this.delayTimer > 0.0F)
        {
            this.delayTimer -= Gdx.graphics.getDeltaTime();
        }
        else
        {
            this.x += this.speedX * Gdx.graphics.getDeltaTime();
            this.y = getY(this.x);
            this.rotation = getRotation(this.x);
            this.duration -= Gdx.graphics.getDeltaTime();

            if (this.duration < 0.0F)
            {
                this.isDone = true;
            }
        }
    }

    private void setImg()
    {
        this.img = ImageMaster.STRIKE_LINE;
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(this.color);
        sb.setBlendFunction(770, 1);
        sb.draw(this.img, this.x, this.y, (float) this.img.packedWidth / 2.0F, (float) this.img.packedHeight / 2.0F, (float) this.img.packedWidth * this.scaleL, (float) this.img.packedHeight, this.scale * Settings.scale, this.scale * Settings.scale, this.rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }
}
