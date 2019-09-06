package KinomotoSakuraMod.Relics;

import KinomotoSakuraMod.Cards.SpellCard.SpellCardRelease;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class KSMOD_SwordJade extends CustomRelic
{
    public static final String RELIC_ID = "KSMOD_SwordJade";
    private static final String RELIC_IMG_PATH = "img/relics/icon/sword_jade.png";
    private static final String RELIC_IMG_OTL_PATH = "img/relics/outline/sword_jade.png";
    private static final RelicTier RELIC_TIER = RelicTier.UNCOMMON;
    private static final LandingSound RELIC_SOUND = AbstractRelic.LandingSound.MAGICAL;

    public KSMOD_SwordJade()
    {
        super(RELIC_ID, ImageMaster.loadImage(RELIC_IMG_PATH), ImageMaster.loadImage(RELIC_IMG_OTL_PATH), RELIC_TIER, RELIC_SOUND);
    }

    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0] + (int) (SpellCardRelease.BASE_RELEASE_UPGRADE_RATE * 100) + this.DESCRIPTIONS[1] + (int) (SpellCardRelease.UPGRADE_RELEASE_UPGRADE_RATE * 100) + this.DESCRIPTIONS[2];
    }

    public AbstractRelic makeCopy()
    {
        return new KSMOD_SwordJade();
    }
}
