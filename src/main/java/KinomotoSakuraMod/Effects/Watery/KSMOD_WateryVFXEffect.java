package KinomotoSakuraMod.Effects.Watery;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;

public class KSMOD_WateryVFXEffect extends AbstractGameEffect
{
    private float timer = 0.0F;
    private Color color = new Color(0xD281F5CD);

    public KSMOD_WateryVFXEffect(float duration)
    {
        super();
        this.duration = duration;
        this.startingDuration = this.duration;
    }

    public void update()
    {
        if (this.duration == this.startingDuration)
        {
            AbstractDungeon.effectsQueue.add(new BorderLongFlashEffect(color));
        }

        this.duration -= Gdx.graphics.getDeltaTime();
        this.timer -= Gdx.graphics.getDeltaTime();
        if (this.timer < 0.0F)
        {
            for (int i = 0; i < 24; i++)
            {
                AbstractDungeon.effectsQueue.add(new KSMOD_WateryEffectElement(this.startingDuration));
            }
            this.timer = 0.05F;
        }

        if (this.duration < 0.0F)
        {
            this.isDone = true;
        }
    }

    public void render(SpriteBatch sb)
    {
    }

    public void dispose()
    {
    }
}
