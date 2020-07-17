package KinomotoSakuraMod.Effects.Turn;

import KinomotoSakuraMod.Cards.KSMOD_AbstractMagicCard;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class KSMOD_TurningScalingEffect extends AbstractGameEffect
{
    private KSMOD_AbstractMagicCard card;
    private Vector2 startPosition;
    private Vector2 targetPosition;
    private float startScale;
    private float targetScale;

    KSMOD_TurningScalingEffect(KSMOD_AbstractMagicCard card, Vector2 startPosition, Vector2 targetPosition, float startScale, float targetScale, float duration)
    {
        this.card = card;
        this.startPosition = startPosition.cpy();
        this.targetPosition = targetPosition.cpy();
        this.startScale = startScale;
        this.targetScale = targetScale;
        this.startingDuration = duration;
        this.duration = this.startingDuration;
    }

    @Override
    public void update()
    {
        if (this.duration > 0F)
        {
            this.card.current_x = Interpolation.fade.apply(startPosition.x, targetPosition.x, (this.startingDuration - this.duration) / this.startingDuration);
            this.card.current_y = Interpolation.fade.apply(startPosition.y, targetPosition.y, (this.startingDuration - this.duration) / this.startingDuration);
            this.card.drawScale = Interpolation.fade.apply(startScale, targetScale, (this.startingDuration - this.duration) / this.startingDuration);
        }
        else if (this.duration < 0F)
        {
            this.isDone = true;
        }
        this.duration -= Gdx.graphics.getDeltaTime();
    }

    @Override
    public void render(SpriteBatch sb)
    {
        sb.setBlendFunction(770, 1);
        this.card.render(sb);
        sb.setBlendFunction(770, 771);
    }

    @Override
    public void dispose()
    {
    }
}
