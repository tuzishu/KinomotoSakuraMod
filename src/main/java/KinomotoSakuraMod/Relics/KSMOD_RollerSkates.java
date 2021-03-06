package KinomotoSakuraMod.Relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class KSMOD_RollerSkates extends CustomRelic
{
    public static final String RELIC_ID = "KSMOD_RollerSkates";
    private static final String RELIC_IMG_PATH = "img/relics/icon/roller_skates.png";
    private static final String RELIC_IMG_OTL_PATH = "img/relics/outline/roller_skates.png";
    private static final RelicTier RELIC_TIER = RelicTier.COMMON;
    private static final LandingSound RELIC_SOUND = LandingSound.CLINK;
    private static final int DEXTERITY_NUMBER = 1;
    private static final int MAX_DEX_NUMBER = 3;

    public KSMOD_RollerSkates()
    {
        super(RELIC_ID, ImageMaster.loadImage(RELIC_IMG_PATH), ImageMaster.loadImage(RELIC_IMG_OTL_PATH), RELIC_TIER, RELIC_SOUND);
    }

    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0] + MAX_DEX_NUMBER + this.DESCRIPTIONS[1] + DEXTERITY_NUMBER + this.DESCRIPTIONS[2];
    }

    public AbstractRelic makeCopy()
    {
        return new KSMOD_RollerSkates();
    }

    public void atTurnStart()
    {
        if (AbstractDungeon.player.hasPower(DexterityPower.POWER_ID))
        {
            AbstractPower power = AbstractDungeon.player.getPower(DexterityPower.POWER_ID);
            if (power.amount >= MAX_DEX_NUMBER)
            return;
        }
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, DEXTERITY_NUMBER), DEXTERITY_NUMBER));
    }
}