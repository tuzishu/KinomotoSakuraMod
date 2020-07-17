package KinomotoSakuraMod.Effects.Turn;

import KinomotoSakuraMod.Cards.KSMOD_AbstractMagicCard;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class KSMOD_TurningShowCardEffect extends AbstractGameEffect
{
    private KSMOD_AbstractMagicCard card;
    private Vector2 position;

    KSMOD_TurningShowCardEffect(KSMOD_AbstractMagicCard card, Vector2 position, float scale, float duration)
    {
        this.card = card;
        this.position = position.cpy();
        this.card.drawScale = scale;
        this.startingDuration = duration;
        this.duration = this.startingDuration;
    }

    @Override
    public void update()
    {
        if (this.duration == this.startingDuration)
        {
            this.card.current_x = position.x;
            this.card.current_y = position.y;
        }
        else if (this.duration < 0F)
        {
            if (this.duration < 0F)
            {
                this.isDone = true;
            }
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
