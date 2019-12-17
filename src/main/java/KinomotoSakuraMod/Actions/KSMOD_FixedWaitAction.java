package KinomotoSakuraMod.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

import java.lang.reflect.Method;

public class KSMOD_FixedWaitAction extends AbstractGameAction
{
    private Method method;
    private Object obj;
    private Object[] args;

    public KSMOD_FixedWaitAction(float delay, Method method, Object obj, Object... args)
    {
        this.setValues((AbstractCreature) null, (AbstractCreature) null, 0);
        this.duration = delay;
        this.method = method;
        this.obj = obj;
        this.args = args;
        this.actionType = ActionType.WAIT;
    }

    public void update()
    {
        this.tickDuration();
        try
        {
            if (this.isDone && this.obj != null)
            {
                method.invoke(obj, args);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}