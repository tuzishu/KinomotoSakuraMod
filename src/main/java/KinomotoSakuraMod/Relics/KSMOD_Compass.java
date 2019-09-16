package KinomotoSakuraMod.Relics;

import KinomotoSakuraMod.Actions.KSMOD_CompassAction;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.unique.DiscoveryAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class KSMOD_Compass extends CustomRelic
{
    public static final String RELIC_ID = "KSMOD_Compass";
    private static final String RELIC_IMG_PATH = "img/relics/icon/default.png";
    private static final String RELIC_IMG_OTL_PATH = "img/relics/outline/default.png";
    private static final RelicTier RELIC_TIER = RelicTier.COMMON;
    private static final LandingSound RELIC_SOUND = AbstractRelic.LandingSound.MAGICAL;
    private static final int VOID_NUMBER = 3;

    public KSMOD_Compass()
    {
        super(RELIC_ID, ImageMaster.loadImage(RELIC_IMG_PATH), ImageMaster.loadImage(RELIC_IMG_OTL_PATH), RELIC_TIER, RELIC_SOUND);
    }

    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0] + VOID_NUMBER + this.DESCRIPTIONS[1];
    }

    public AbstractRelic makeCopy()
    {
        return new KSMOD_Compass();
    }

    public void atBattleStart()
    {
        AbstractDungeon.actionManager.addToBottom(new KSMOD_CompassAction(VOID_NUMBER));
    }
}
