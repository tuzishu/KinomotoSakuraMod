package KinomotoSakuraMod.Effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class KSMOD_WateryEffectElement extends AbstractGameEffect
{
    private TextureAtlas.AtlasRegion img;
    private float x;
    private float y;
    private float scaleL;
    private float vX;
    private float vY;
    private float startingDuration;
    private float delayTimer = MathUtils.random(0.1F);

    public KSMOD_WateryEffectElement(float startingDuration)
    {
        this.setImg();
        this.startingDuration = startingDuration;
        this.duration = this.startingDuration;
        do
        {
            InitStartPoint();
        } while (!CheckPointIsInside());
        this.rotation = MathUtils.atan2(4, 1) * MathUtils.radiansToDegrees;
        this.scale = MathUtils.random(0.15F, 0.3F);
        this.scaleL = MathUtils.random(3F, 8F);
        this.color = Color.GREEN;
        this.color.r = MathUtils.random(0F, 1F);
        this.color.b = MathUtils.random(0.6F, 0.8F);
        this.color.a = MathUtils.random(0.44F, 0.66F);
        this.vY = Settings.HEIGHT / MathUtils.random(0.1F, 0.5F);
        this.vX = vY / 4F;
    }

    private void InitStartPoint()
    {
        this.x = MathUtils.random(Settings.WIDTH, Settings.HEIGHT * 8F + Settings.WIDTH * 18F) / 17F;
        this.y = MathUtils.random(Settings.HEIGHT * 16F + Settings.WIDTH * 8F, Settings.HEIGHT * 33F + Settings.WIDTH * 8F) / 17F;
    }

    private boolean CheckPointIsInside()
    {
        return this.x + this.y * 4F >= Settings.HEIGHT * 4F + Settings.WIDTH && this.x + this.y * 4F <= Settings.HEIGHT * 8F + Settings.WIDTH * 2F && this.y - this.x * 4F >= -Settings.WIDTH * 4F && this.y - this.x * 4F <= Settings.HEIGHT;
    }

    public void update()
    {
        if (this.delayTimer > 0.0F)
        {
            this.delayTimer -= Gdx.graphics.getDeltaTime();
        }
        else
        {
            this.x -= this.vX * Gdx.graphics.getDeltaTime();
            this.y -= this.vY * Gdx.graphics.getDeltaTime();
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