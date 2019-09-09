package KinomotoSakuraMod.Relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class KSMOD_RollerSkates extends CustomRelic
{
    public static final String RELIC_ID = "KSMOD_RollerSkates";
    private static final String RELIC_IMG_PATH = "img/relics/icon/default.png";
    private static final String RELIC_IMG_OTL_PATH = "img/relics/outline/default.png";
    private static final RelicTier RELIC_TIER = RelicTier.RARE;
    private static final LandingSound RELIC_SOUND = LandingSound.CLINK;
    private static final int DEXTERITY_NUMBER = 1;

    public KSMOD_RollerSkates()
    {
        super(RELIC_ID, ImageMaster.loadImage(RELIC_IMG_PATH), ImageMaster.loadImage(RELIC_IMG_OTL_PATH), RELIC_TIER, RELIC_SOUND);
    }

    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0] + DEXTERITY_NUMBER + this.DESCRIPTIONS[1];
    }

    public AbstractRelic makeCopy()
    {
        return new KSMOD_RollerSkates();
    }

    public void atTurnStart()
    {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, DEXTERITY_NUMBER), DEXTERITY_NUMBER));
    }
}