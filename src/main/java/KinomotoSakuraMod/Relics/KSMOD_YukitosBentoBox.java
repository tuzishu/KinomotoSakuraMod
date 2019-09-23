package KinomotoSakuraMod.Relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class KSMOD_YukitosBentoBox extends CustomRelic
{
    public static final String RELIC_ID = "KSMOD_YukitosBentoBox";
    private static final String RELIC_IMG_PATH = "img/relics/icon/default.png";
    private static final String RELIC_IMG_OTL_PATH = "img/relics/outline/default.png";
    private static final RelicTier RELIC_TIER = RelicTier.UNCOMMON;
    private static final LandingSound RELIC_SOUND = LandingSound.HEAVY;
    private static final int ENERGY_NUMBER = 1;
    private boolean active;

    public KSMOD_YukitosBentoBox()
    {
        super(RELIC_ID, ImageMaster.loadImage(RELIC_IMG_PATH), ImageMaster.loadImage(RELIC_IMG_OTL_PATH), RELIC_TIER, RELIC_SOUND);
        active = false;
    }

    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0] + ENERGY_NUMBER + this.DESCRIPTIONS[1];
    }

    public AbstractRelic makeCopy()
    {
        return new KSMOD_YukitosBentoBox();
    }

    public void onPlayerEndTurn()
    {
        active = EnergyPanel.getCurrentEnergy() == 0;
    }

    public void atTurnStart()
    {
        if (active)
        {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(ENERGY_NUMBER));
        }
    }
}
