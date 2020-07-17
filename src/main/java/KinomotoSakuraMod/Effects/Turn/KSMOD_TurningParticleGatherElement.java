package KinomotoSakuraMod.Effects.Turn;

import KinomotoSakuraMod.Cards.KSMOD_AbstractMagicCard;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.ArrayList;

public class KSMOD_TurningParticleGatherElement extends AbstractGameEffect
{
    private static final TextureAtlas.AtlasRegion IMAGE = ImageMaster.GLOW_SPARK_2;
    private static final float START_VELOCITY = 1000.0F * Settings.scale;
    private static final float MAX_VELOCITY = 6000.0F * Settings.scale;
    private static final float VELOCITY_RAMP_RATE = 2000.0F * Settings.scale;
    private static final float DST_THRESHOLD = 0.1F * Settings.scale;
    private static final int CONTROL_POINT_AMOUNT = 20;
    private static final int TWEENER_AMOUNT = 120;

    private CatmullRomSpline<Vector2> crs = new CatmullRomSpline();
    private ArrayList<Vector2> controlPoints = new ArrayList();
    private ArrayList<Vector2> points = new ArrayList<>();
    private ArrayList<Vector2> endingPoints = new ArrayList<>();
    private Vector2 currentPosition;
    private Vector2 startPosition;
    private Vector2 targetPosition;
    private float currentSpeed;

    public KSMOD_TurningParticleGatherElement(Vector2 targetPosition, float duration)
    {
        this.startPosition = GetRandomStartPosition(targetPosition);
        this.targetPosition = GetTargetPosition(targetPosition);
        this.currentPosition = this.startPosition.cpy();
        this.crs.controlPoints = new Vector2[1];
        this.controlPoints.clear();
        this.currentSpeed = START_VELOCITY * MathUtils.random(0.2F, 1.0F);
        this.color = new Color(0xBA46F666);
        this.duration = duration;
    }

    private Vector2 GetRandomStartPosition(Vector2 anchor)
    {
        float x, y;
        do
        {
            x = MathUtils.random(-anchor.x, anchor.x * 3);
            y = MathUtils.random(-anchor.y, anchor.y * 3);
        } while (x > anchor.x - Settings.WIDTH * 0.25 && x < anchor.x + Settings.WIDTH * 0.25 && y > anchor.y - Settings.HEIGHT * 0.25 && y < anchor.y + Settings.HEIGHT * 0.25);
        return new Vector2(x, y);
    }

    private Vector2 GetTargetPosition(Vector2 anchor)
    {
        float x = anchor.x - KSMOD_AbstractMagicCard.IMG_WIDTH * 0.25F * Settings.scale + startPosition.x * KSMOD_AbstractMagicCard.IMG_WIDTH * 0.5F / Settings.WIDTH;
        float y = anchor.y - KSMOD_AbstractMagicCard.IMG_HEIGHT * 0.5F * Settings.scale;
        return new Vector2(x, y);
    }

    public void update()
    {
        if (this.duration == this.startingDuration)
        {
            controlPoints.add(currentPosition.cpy());
        }

        this.currentSpeed = this.currentSpeed > MAX_VELOCITY ? MAX_VELOCITY : this.currentSpeed + Gdx.graphics.getDeltaTime() * VELOCITY_RAMP_RATE * 3.0F;

        if (this.currentPosition.dst(this.targetPosition) >= DST_THRESHOLD)
        {
            Vector2 movementVector = new Vector2(this.currentPosition.x - this.targetPosition.x, this.currentPosition.y - this.targetPosition.y);
            movementVector.nor();
            movementVector.x *= Gdx.graphics.getDeltaTime() * this.currentSpeed;
            movementVector.y *= Gdx.graphics.getDeltaTime() * this.currentSpeed;
            this.currentPosition.sub(movementVector);
            if (controlPoints.size() >= CONTROL_POINT_AMOUNT)
            {
                controlPoints.remove(0);
            }
            controlPoints.add(this.currentPosition.cpy());
        }
        else
        {
            this.currentPosition = this.targetPosition.cpy();
            this.currentSpeed = 0F;
            if (controlPoints.size() > 0)
            {
                controlPoints.add(controlPoints.remove(0).set(targetPosition));
            }
        }

        for (int i = 0; i < controlPoints.size(); i++)
        {
            if (controlPoints.get(i).dst(targetPosition) <= DST_THRESHOLD)
            {
                endingPoints.add(controlPoints.remove(i));
            }
        }

        if (controlPoints.size() > 3)
        {
            Vector2[] vec2Array = new Vector2[0];
            crs.set(controlPoints.toArray(vec2Array), false);

            for (int i = 0; i < TWEENER_AMOUNT; ++i)
            {
                if (i < this.points.size())
                {
                    this.points.set(i, new Vector2());
                }
                else
                {
                    this.points.add(new Vector2());
                }
                this.crs.valueAt(this.points.get(i), (float) i / TWEENER_AMOUNT);
            }
        }
        else
        {
            for (int i = 0; i < controlPoints.size(); i++)
            {
                points.add(controlPoints.get(i).cpy());
            }
        }

        if (this.duration < 0)
        {
            this.isDone = true;
        }
        this.duration -= Gdx.graphics.getDeltaTime();
    }

    public void render(SpriteBatch sb)
    {
        if (!this.isDone)
        {
            sb.setBlendFunction(770, 1);
            sb.setColor(this.color);
            float scale = Settings.scale * 1.5F;
            for (int i = this.endingPoints.size() - 1; i > 0; --i)
            {
                if (this.endingPoints.get(i) != null)
                {
                    sb.draw(IMAGE, this.endingPoints.get(i).x - (float) (IMAGE.packedWidth / 2), this.endingPoints.get(i).y - (float) (IMAGE.packedHeight / 2), (float) IMAGE.packedWidth / 2.0F, (float) IMAGE.packedHeight / 2.0F, (float) IMAGE.packedWidth, (float) IMAGE.packedHeight, scale, scale, this.rotation);
                    scale *= 0.975F;
                }
            }
            for (int i = this.points.size() - 1; i > 0; --i)
            {
                if (this.points.get(i) != null)
                {
                    sb.draw(IMAGE, this.points.get(i).x - (float) (IMAGE.packedWidth / 2), this.points.get(i).y - (float) (IMAGE.packedHeight / 2), (float) IMAGE.packedWidth / 2.0F, (float) IMAGE.packedHeight / 2.0F, (float) IMAGE.packedWidth, (float) IMAGE.packedHeight, scale, scale, this.rotation);
                    scale *= 0.975F;
                }
            }
            sb.setBlendFunction(770, 771);
        }
    }

    public void dispose()
    {
    }

    private Vector2 GetRandomPosition(Vector2 anchor, float radius)
    {
        float x, y;
        Vector2 v;
        do
        {
            x = MathUtils.random(anchor.x - radius, anchor.x + radius);
            y = MathUtils.random(anchor.y - radius, anchor.y + radius);
            v = new Vector2(x, y);
        } while (v.dst(anchor) > radius);
        return v;
    }
}
