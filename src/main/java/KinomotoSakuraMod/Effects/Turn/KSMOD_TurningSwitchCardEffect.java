package KinomotoSakuraMod.Effects.Turn;

import KinomotoSakuraMod.Cards.KSMOD_AbstractMagicCard;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class KSMOD_TurningSwitchCardEffect extends AbstractGameEffect
{
    public KSMOD_AbstractMagicCard clowCard;
    public KSMOD_AbstractMagicCard sakuraCard;
    public Vector2 position;

    public KSMOD_TurningSwitchCardEffect(KSMOD_AbstractMagicCard clowCard, Vector2 position, float duration)
    {
        this.startingDuration = duration;
        this.duration = this.startingDuration;
        this.clowCard = clowCard;
        this.clowCard.drawScale = 1F;
        this.position = position.cpy();
    }

    @Override
    public void update()
    {
        this.clowCard.current_x = position.x;
        this.clowCard.current_y = position.y;

        if (this.duration < startingDuration && this.duration > 0F)
        {
            float a = (this.startingDuration - this.duration) / this.startingDuration;
            float transition = 1F - (float) (5.99 * a * a * a * a * a - 15.82 * a * a * a * a + 18.84 * a * a * a - 10.68 * a * a + 2.68 * a);
            this.clowCard.renderedPortionProportionToTop = MathUtils.clamp(transition, 0F, 1F);
        }

        if (this.duration < 0.0F)
        {
            this.isDone = true;
        }
        this.duration -= Gdx.graphics.getDeltaTime();
    }

    @Override
    public void render(SpriteBatch sb)
    {
        sb.setBlendFunction(770, 1);
        this.clowCard.render(sb);
        sb.setBlendFunction(770, 771);
    }

    @Override
    public void dispose()
    {
    }
}