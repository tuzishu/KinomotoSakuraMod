package KinomotoSakuraMod.Effects.Earthy;

import KinomotoSakuraMod.Utility.KSMOD_RenderTool;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class KSMOD_EarthyEffectElement extends AbstractGameEffect
{
    private static final TextureAtlas.AtlasRegion FROST_ORB_LEFT = KSMOD_RenderTool.GetAtlasRegion(ImageMaster.FROST_ORB_LEFT);
    private static final TextureAtlas.AtlasRegion FROST_ORB_MIDDLE = KSMOD_RenderTool.GetAtlasRegion(ImageMaster.FROST_ORB_MIDDLE);
    private static final TextureAtlas.AtlasRegion FROST_ORB_RIGHT = KSMOD_RenderTool.GetAtlasRegion(ImageMaster.FROST_ORB_RIGHT);

    private TextureAtlas.AtlasRegion img;
    private float brightness;
    private float x;
    private float y;
    private float vX;
    private float vY;
    private float startingDuration;
    private boolean flipX = MathUtils.randomBoolean();
    private float delayTimer = MathUtils.random(0.1F);

    public KSMOD_EarthyEffectElement(float startingDuration)
    {
        this.setImg();
        this.startingDuration = startingDuration;
        this.duration = this.startingDuration;
        this.scale = MathUtils.random(1F, 5F);
        this.rotation = MathUtils.random(-20F, 20F);
        this.x = MathUtils.random(0.0F, Settings.WIDTH) - (float) this.img.packedWidth * this.scale / 2.0F;
        this.y = MathUtils.random(-Settings.HEIGHT * 0.1F, Settings.HEIGHT * 0.1F) * Settings.scale - (float) this.img.packedHeight / 2.0F;
        this.vX = MathUtils.random(-Settings.WIDTH * 0.04F, Settings.WIDTH * 0.04F) * Settings.scale;
        this.vY = MathUtils.random(Settings.HEIGHT * 0.1F, Settings.HEIGHT * 0.4F) * Settings.scale;
        this.color = new Color(0x615724FF);
        this.color.r = MathUtils.random(this.color.r - 0.1F, this.color.r + 0.1F);
        this.color.g = MathUtils.random(this.color.g - 0.1F, this.color.g + 0.1F);
        this.brightness = MathUtils.random(0.7F, 1F);
    }

    public void update()
    {
        if (this.delayTimer > 0.0F)
        {
            this.delayTimer -= Gdx.graphics.getDeltaTime();
        }
        else
        {
            this.x += this.vX * Gdx.graphics.getDeltaTime();
            this.y += this.vY * Gdx.graphics.getDeltaTime();
            this.scale *= MathUtils.random(0.95F, 1.05F);
            this.duration -= Gdx.graphics.getDeltaTime();
            if (this.duration > this.startingDuration * 0.75F)
            {
                this.color.a = Interpolation.fade.apply(0F, this.brightness, (this.startingDuration - this.duration) / (this.startingDuration * 0.25F));
            }
            else if (this.duration > this.startingDuration * 0.25F)
            {
                this.color.a = Interpolation.fade.apply(this.brightness, 0.0F, (this.duration - this.startingDuration * 0.25F) / (this.startingDuration * 0.25F));
            }
            else if (this.duration < 0F)
            {
                this.isDone = true;
            }
        }
    }

    private void setImg()
    {
        int roll = MathUtils.random(2);
        switch (roll)
        {
            case 0:
                this.img = FROST_ORB_LEFT;
                break;
            case 1:
                this.img = FROST_ORB_MIDDLE;
                break;
            default:
                this.img = FROST_ORB_RIGHT;
                break;
        }

    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(this.color);
        sb.setBlendFunction(770, 1);
        if (this.flipX && !this.img.isFlipX())
        {
            this.img.flip(true, false);
        }
        else if (!this.flipX && this.img.isFlipX())
        {
            this.img.flip(true, false);
        }

        sb.draw(this.img, this.x, this.y, (float) this.img.packedWidth / 2.0F, (float) this.img.packedHeight / 2.0F, (float) this.img.packedWidth, (float) this.img.packedHeight, this.scale * Settings.scale, this.scale * Settings.scale, this.rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }
}