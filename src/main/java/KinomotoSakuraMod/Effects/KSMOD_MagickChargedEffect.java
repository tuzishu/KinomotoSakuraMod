package KinomotoSakuraMod.Effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class KSMOD_MagickChargedEffect extends AbstractGameEffect
{
    private float x;
    private float y;
    private TextureAtlas.AtlasRegion img;
    public float fadeDuration;

    public KSMOD_MagickChargedEffect()
    {
        if (this.img == null)
        {
            this.img = ImageMaster.CRYSTAL_IMPACT;
        }

        this.x = AbstractDungeon.player.hb.cX - (float) this.img.packedWidth * 0.5F;
        this.y = AbstractDungeon.player.hb.cY - (float) this.img.packedHeight * 0.5F + (float) this.img.packedHeight * 0.09F;
        this.startingDuration = 9999F;
        this.fadeDuration = 0.3F;
        this.duration = this.startingDuration;
        this.scale = Settings.scale;
        this.color = Color.CYAN.cpy();
        this.color.a = 0.0F;
        this.renderBehind = true;
    }

    public void update()
    {
        if (this.duration == this.startingDuration)
        {
            CardCrawlGame.sound.playA("HEAL_3", 0.5F);
        }

        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration > this.startingDuration - this.fadeDuration)
        {
            this.color.a = Interpolation.fade.apply(0.01F, 1.0F, (this.startingDuration - this.duration) / this.fadeDuration) * Settings.scale;
        }
        else if (this.duration < this.fadeDuration)
        {
            this.color.a = Interpolation.fade.apply(0.01F, 1.0F, this.duration / this.fadeDuration) * Settings.scale;
        }
        else
        {
            this.color.a = 1F;
        }

        this.scale = 3.5F * Settings.scale;
        if (this.duration < 0.0F)
        {
            this.isDone = true;
        }

    }

    public void render(SpriteBatch sb)
    {
        sb.setBlendFunction(770, 1);

        sb.setColor(new Color(0.93F, 0.41F, 0.46F, this.color.a));
        sb.draw(this.img, this.x, this.y, (float) this.img.packedWidth * 0.5F, (float) this.img.packedHeight * 0.5F, (float) this.img.packedWidth, (float) this.img.packedHeight, this.scale * 1.15F, this.scale * 1.15F, 0.0F);

        sb.setColor(new Color(1.0F, 0.57F, 0.64F, this.color.a));
        sb.draw(this.img, this.x, this.y, (float) this.img.packedWidth * 0.5F, (float) this.img.packedHeight * 0.5F, (float) this.img.packedWidth, (float) this.img.packedHeight, this.scale, this.scale, 0.0F);

        sb.setColor(new Color(1.0F, 0.75F, 0.79F, this.color.a));
        sb.draw(this.img, this.x, this.y, (float) this.img.packedWidth * 0.5F, (float) this.img.packedHeight * 0.5F, (float) this.img.packedWidth, (float) this.img.packedHeight, this.scale * 0.85F, this.scale * 0.85F, 0.0F);

        sb.setColor(new Color(1.0F, 0.57F, 0.64F, this.color.a));
        sb.draw(this.img, this.x, this.y, (float) this.img.packedWidth * 0.5F, (float) this.img.packedHeight * 0.5F, (float) this.img.packedWidth, (float) this.img.packedHeight, this.scale * 0.7F, this.scale * 0.7F, 0.0F);

        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }
}
