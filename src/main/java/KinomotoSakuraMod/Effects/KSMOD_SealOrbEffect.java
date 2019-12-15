package KinomotoSakuraMod.Effects;

import KinomotoSakuraMod.Utility.KSMOD_Utility;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.vfx.combat.FlyingOrbEffect;

import java.lang.reflect.Field;

public class KSMOD_SealOrbEffect extends FlyingOrbEffect
{
    public KSMOD_SealOrbEffect(float start_x, float start_y, float end_x, float end_y)
    {
        super(start_x, start_y);
        try
        {
            Field target = KSMOD_Utility.GetFieldByReflect(FlyingOrbEffect.class, "target");
            target.set(this, new Vector2(end_x, end_y));
            this.color = new Color(0.93F, 0.41F, 0.46F, 0.4F);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
