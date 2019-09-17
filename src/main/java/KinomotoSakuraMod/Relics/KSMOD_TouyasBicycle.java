package KinomotoSakuraMod.Relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class KSMOD_TouyasBicycle extends CustomRelic
{
    public static final String RELIC_ID = "KSMOD_TouyasBicycle";
    private static final String RELIC_IMG_PATH = "img/relics/icon/default.png";
    private static final String RELIC_IMG_OTL_PATH = "img/relics/outline/default.png";
    private static final RelicTier RELIC_TIER = RelicTier.COMMON;
    private static final LandingSound RELIC_SOUND = LandingSound.SOLID;
    public static final int CHARGE_NUMBER = 1;

    public KSMOD_TouyasBicycle()
    {
        super(RELIC_ID, ImageMaster.loadImage(RELIC_IMG_PATH), ImageMaster.loadImage(RELIC_IMG_OTL_PATH), RELIC_TIER, RELIC_SOUND);
    }

    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0] + CHARGE_NUMBER + this.DESCRIPTIONS[1];
    }

    public AbstractRelic makeCopy()
    {
        return new KSMOD_TouyasBicycle();
    }
}
