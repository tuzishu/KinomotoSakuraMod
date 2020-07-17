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

public class KSMOD_TurningParticleDiffusionElement extends AbstractGameEffect
{
    private static final TextureAtlas.AtlasRegion IMAGE = ImageMaster.GLOW_SPARK_2;
    private static final float MOVE_TIME_RATE = 0.2F;
    private static final float MAX_IMAGE_ALPHA = 0.4F;
    private static final float MIN_IMAGE_ALPHA = 0.05F;
    private static final int CONTROL_POINT_AMOUNT = 20;
    private static final int TWEENER_AMOUNT = 100;
    private static final float DST_THRESHOLD = 0.1F * Settings.scale;
    private ArrayList<Vector2> endingPoints = new ArrayList<>();
    private ArrayList<Vector2> points = new ArrayList<>();
    private Vector2 currentPosition;
    private Vector2 startPosition;
    private Vector2 targetPosition;
    private Vector2 direction;
    private float distance;
    private float alpha = 1F;
    private ArrayList<Vector2> controlPoints = new ArrayList<>();
    private CatmullRomSpline<Vector2> crs = new CatmullRomSpline();

    public KSMOD_TurningParticleDiffusionElement(Vector2 startPosition, float duration)
    {
        this.color = new Color(0xBA46F666);
        this.startPosition = startPosition.cpy();
        this.targetPosition = GetTargetPosition(startPosition);
        this.direction = new Vector2(this.targetPosition.x - this.startPosition.x, this.targetPosition.y - this.startPosition.y).nor();
        this.currentPosition = this.startPosition.cpy();
        this.distance = this.targetPosition.dst(this.startPosition);
        this.startingDuration = duration;
        this.duration = this.startingDuration;
    }

    private Vector2 GetTargetPosition(Vector2 anchor)
    {
        float x, y;
        do
        {
            x = MathUtils.random(0F, Settings.WIDTH);
            y = MathUtils.random(0F, Settings.HEIGHT);
        } while (x > anchor.x - KSMOD_AbstractMagicCard.IMG_WIDTH && x < anchor.x + KSMOD_AbstractMagicCard.IMG_WIDTH && y > anchor.y - KSMOD_AbstractMagicCard.IMG_HEIGHT * 0.6F && y < anchor.y + KSMOD_AbstractMagicCard.IMG_HEIGHT * 0.6F);
        return new Vector2(x, y);
    }

//    public void update()
//    {
//        if (this.duration == this.startingDuration)
//        {
//            pathPoints.add(currentPosition.cpy());
//        }
//
//        if (this.duration < this.startingDuration && this.duration > this.startingDuration * (1F - MOVE_TIME_RATE))
//        {
//            float a = 1F - (this.startingDuration - this.duration) / (this.startingDuration * MOVE_TIME_RATE);
//            float movement = distance * (1F - a * a * a * a);
//            currentPosition.x = startPosition.x + direction.x * movement;
//            currentPosition.y = startPosition.y + direction.y * movement;
//            if (pathPoints.size() >= 20)
//            {
//                pathPoints.remove(0);
//            }
//            pathPoints.add(this.currentPosition.cpy());
//        }
//        else if (this.duration < this.startingDuration * (1 - MOVE_TIME_RATE) && this.duration > 0)
//        {
//            alpha = this.duration / (this.startingDuration * (1 - MOVE_TIME_RATE)) * (MAX_IMAGE_ALPHA - MIN_IMAGE_ALPHA) + MIN_IMAGE_ALPHA;
//            pathPoints.add(pathPoints.remove(0).set(targetPosition));
//        }
//
//        if (this.duration < 0)
//        {
//            this.isDone = true;
//        }
//
//        this.duration -= Gdx.graphics.getDeltaTime();
//    }


    public void update()
    {
        if (this.duration == this.startingDuration)
        {
            controlPoints.add(currentPosition.cpy());
        }

        if (this.duration < this.startingDuration && this.duration > this.startingDuration * (1F - MOVE_TIME_RATE))
        {
            float a = 1F - (this.startingDuration - this.duration) / (this.startingDuration * MOVE_TIME_RATE);
            float movement = distance * (1F - a * a * a * a);
            currentPosition.x = startPosition.x + direction.x * movement;
            currentPosition.y = startPosition.y + direction.y * movement;
            if (controlPoints.size() >= CONTROL_POINT_AMOUNT)
            {
                controlPoints.remove(0);
            }
            controlPoints.add(this.currentPosition.cpy());
        }
        else if (this.duration < this.startingDuration * (1 - MOVE_TIME_RATE) && this.duration > 0)
        {
            alpha = this.duration / (this.startingDuration * (1 - MOVE_TIME_RATE)) * (MAX_IMAGE_ALPHA - MIN_IMAGE_ALPHA) + MIN_IMAGE_ALPHA;
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
        sb.setBlendFunction(770, 1);
        this.color.a = alpha;
        sb.setColor(this.color);
        float scale = Settings.scale * 1.5F;
        for (int i = endingPoints.size() - 1; i > 0; --i)
        {
            if (endingPoints.get(i) != null)
            {
                sb.draw(IMAGE, endingPoints.get(i).x - (float) (IMAGE.packedWidth / 2), endingPoints.get(i).y - (float) (IMAGE.packedHeight / 2), (float) IMAGE.packedWidth / 2.0F, (float) IMAGE.packedHeight / 2.0F, (float) IMAGE.packedWidth, (float) IMAGE.packedHeight, scale, scale, this.rotation);
                scale *= 0.975F;
            }
        }
        for (int i = points.size() - 1; i > 0; --i)
        {
            if (points.get(i) != null)
            {
                sb.draw(IMAGE, points.get(i).x - (float) (IMAGE.packedWidth / 2), points.get(i).y - (float) (IMAGE.packedHeight / 2), (float) IMAGE.packedWidth / 2.0F, (float) IMAGE.packedHeight / 2.0F, (float) IMAGE.packedWidth, (float) IMAGE.packedHeight, scale, scale, this.rotation);
                scale *= 0.975F;
            }
        }
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }
}

