package KinomotoSakuraMod.Effects.Turn;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.GainPowerEffect;

public class KSMOD_TurningParticleDiffusionEffect extends AbstractGameEffect
{
    private Vector2 startPosition;

    public KSMOD_TurningParticleDiffusionEffect(Vector2 startPosition, float duration)
    {
        super();
        this.startPosition = startPosition.cpy();
        this.duration = duration;
        this.startingDuration = this.duration;
    }

    public void update()
    {
        if (this.duration == this.startingDuration)
        {
            for (int i = 0; i < 48; i++)
            {
                AbstractDungeon.effectsQueue.add(new KSMOD_TurningParticleDiffusionElement(startPosition, this.startingDuration));
            }
        }

        this.duration -= Gdx.graphics.getDeltaTime();

        if (this.duration < 0.0F)
        {
            this.isDone = true;
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch)
    {
    }

    @Override
    public void dispose()
    {
    }
}