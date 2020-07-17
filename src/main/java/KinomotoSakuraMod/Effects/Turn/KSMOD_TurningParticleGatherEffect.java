package KinomotoSakuraMod.Effects.Turn;

import KinomotoSakuraMod.Cards.KSMOD_AbstractMagicCard;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.IntenseZoomParticle;

public class KSMOD_TurningParticleGatherEffect extends AbstractGameEffect
{
    private Color color = new Color(0xBA46F666);
    private Vector2 targetPosition;
    private int generateAmount = 16; // 实际生成波数为一半
    private int particleAmount = 32;
    private boolean[] doneArray = new boolean[generateAmount];

    public KSMOD_TurningParticleGatherEffect(Vector2 targetPosition, float duration)
    {
        super();
        this.targetPosition = targetPosition.cpy();
        this.duration = duration;
        this.startingDuration = this.duration;
    }

    public void update()
    {
        if (this.duration == this.startingDuration)
        {
            AbstractDungeon.effectsQueue.add(new BorderLongFlashEffect(color));
            GenerateParticle();
        }
        for (int i = generateAmount - 1; i > generateAmount / 2; i--)
        {
            if (this.duration > this.startingDuration * (i - 1) / generateAmount && this.duration < this.startingDuration * i / generateAmount)
            {
                if (!doneArray[i - 1])
                {
                    GenerateParticle();
                    AbstractDungeon.effectsQueue.add(new IntenseZoomParticle(targetPosition.x, targetPosition.y - KSMOD_AbstractMagicCard.IMG_HEIGHT * 0.5F, false));
                    doneArray[i - 1] = true;
                }
            }
        }
        if (this.duration < 0.0F)
        {
            this.isDone = true;
        }
        this.duration -= Gdx.graphics.getDeltaTime();
    }

    private void GenerateParticle()
    {
        for (int i = 0; i < particleAmount; i++)
        {
            AbstractDungeon.effectsQueue.add(new KSMOD_TurningParticleGatherElement(targetPosition, this.duration));
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