package KinomotoSakuraMod.Relics;

import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class KSMOD_SealedWand extends KSMOD_AbstractWand
{
    public static final String RELIC_ID = "KSMOD_SealedWand";
    private static final String RELIC_IMG_PATH = "img/relics/icon/sealed_wand.png";
    private static final String RELIC_IMG_OTL_PATH = "img/relics/outline/sealed_wand.png";
    private static final RelicTier RELIC_TIER = RelicTier.STARTER;
    private static final LandingSound RELIC_SOUND = AbstractRelic.LandingSound.MAGICAL;
    private static final int GAIN_NUMBER = 3;
    private static final int EXTRA_GAIN_NUMBER = 3;
    private static final int BASE_TRIGGER_NUMBER = 40;
    private static final int UPDATE_TRIGGER_NUMBER = 20;

    public KSMOD_SealedWand()
    {
        super(RELIC_ID, ImageMaster.loadImage(RELIC_IMG_PATH), ImageMaster.loadImage(RELIC_IMG_OTL_PATH), RELIC_TIER, RELIC_SOUND, BASE_TRIGGER_NUMBER, UPDATE_TRIGGER_NUMBER, GAIN_NUMBER, EXTRA_GAIN_NUMBER);
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0] + this.GetGainNumber() + DESCRIPTIONS[1] + this.GetExtraGainNumber() + DESCRIPTIONS[2] + this.GetTriggerNumber() + DESCRIPTIONS[3] + this.GetTriggerNumber() + DESCRIPTIONS[4] + this.GetUpdateTriggerNumber() + DESCRIPTIONS[5] + this.GetRestCounterNumber() + DESCRIPTIONS[6];
    }

    public AbstractRelic makeCopy()
    {
        return new KSMOD_SealedWand();
    }
}
