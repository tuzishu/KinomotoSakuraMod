package KinomotoSakuraMod.Relics;

import KinomotoSakuraMod.Patches.KSMOD_CustomTag;
import KinomotoSakuraMod.Powers.KSMOD_MagickChargePower;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.common.UpgradeRandomCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class KSMOD_Yue extends CustomRelic
{
    public static final String RELIC_ID = "KSMOD_Yue";
    private static final String RELIC_IMG_PATH = "img/relics/icon/yue.png";
    private static final String RELIC_IMG_OTL_PATH = "img/relics/outline/yue.png";
    private static final RelicTier RELIC_TIER = RelicTier.UNCOMMON;
    private static final LandingSound RELIC_SOUND = AbstractRelic.LandingSound.MAGICAL;
    private static final int START_COUNT = 0;
    private static final int TRIGGER_NUMBER = 3;
    private static final int CHARGE_NUMBER = 1;
    private static final int UPGRADE_NUMBER = 1;

    public KSMOD_Yue()
    {
        super(RELIC_ID, ImageMaster.loadImage(RELIC_IMG_PATH), ImageMaster.loadImage(RELIC_IMG_OTL_PATH), RELIC_TIER, RELIC_SOUND);
        this.counter = START_COUNT;
    }

    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0] + TRIGGER_NUMBER + this.DESCRIPTIONS[1] + UPGRADE_NUMBER + this.DESCRIPTIONS[2];
    }

    public AbstractRelic makeCopy()
    {
        return new KSMOD_Yue();
    }

    public void atBattleStart()
    {
        this.setCounter(0);
    }

    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction)
    {
        if (targetCard.hasTag(KSMOD_CustomTag.KSMOD_WATERY_CARD) || targetCard.hasTag(KSMOD_CustomTag.KSMOD_WINDY_CARD))
        {
            this.flash();
            this.counter += 1;
        }
        if (this.counter >= TRIGGER_NUMBER)
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new KSMOD_MagickChargePower(AbstractDungeon.player, CHARGE_NUMBER), CHARGE_NUMBER));
            this.counter -= TRIGGER_NUMBER;
        }
    }

    public void atTurnStartPostDraw()
    {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        for (int i = 0; i < UPGRADE_NUMBER; i++)
        {
            AbstractDungeon.actionManager.addToBottom(new UpgradeRandomCardAction());
        }
    }
}