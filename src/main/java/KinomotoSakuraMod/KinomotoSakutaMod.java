package KinomotoSakuraMod;

import basemod.BaseMod;
import basemod.interfaces.ISubscriber;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;

@SpireInitializer
public class KinomotoSakutaMod implements ISubscriber
{
    public KinomotoSakutaMod()
    {
        BaseMod.subscribe(this);
    }

    public static void initialize()
    {
        new KinomotoSakutaMod();
    }
}
