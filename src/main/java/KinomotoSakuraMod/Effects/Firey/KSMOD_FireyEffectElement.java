package KinomotoSakuraMod.Effects.Firey;

import KinomotoSakuraMod.Utility.KSMOD_ReflectTool;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.combat.GiantFireEffect;

import java.lang.reflect.Field;

public class KSMOD_FireyEffectElement extends GiantFireEffect
{
    public KSMOD_FireyEffectElement()
    {
        super();
        try
        {
            Field vX = KSMOD_ReflectTool.GetFieldByReflect(GiantFireEffect.class, "vX");
            Field vY = KSMOD_ReflectTool.GetFieldByReflect(GiantFireEffect.class, "vY");
            vX.set(this, MathUtils.random(-70.0F, 70.0F) * Settings.scale);
            vY.set(this, MathUtils.random(500.0F, 900.0F) * Settings.scale);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
