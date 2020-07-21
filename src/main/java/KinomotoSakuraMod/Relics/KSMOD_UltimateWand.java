package KinomotoSakuraMod.Relics;

import KinomotoSakuraMod.Patches.KSMOD_CustomTag;
import KinomotoSakuraMod.Powers.KSMOD_CerberusMarkPower;
import KinomotoSakuraMod.Powers.KSMOD_MagickChargePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.common.UpgradeRandomCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class KSMOD_UltimateWand extends KSMOD_AbstractWand
{
    public static final String RELIC_ID = "KSMOD_UltimateWand";
    private static final String RELIC_IMG_PATH = "img/relics/icon/ultimate_wand.png";
    private static final String RELIC_IMG_OTL_PATH = "img/relics/outline/ultimate_wand.png";
    private static final RelicTier RELIC_TIER = RelicTier.SPECIAL;
    private static final LandingSound RELIC_SOUND = AbstractRelic.LandingSound.MAGICAL;
    private static final int GAIN_NUMBER = 5;
    private static final int EXTRA_GAIN_NUMBER = 5;
    private static final int BASE_TRIGGER_NUMBER = 35;
    private static final int UPDATE_TRIGGER_NUMBER = 20;
    private static final int START_CHARGE_NUMBER = 6;
    private static final int MAGICK_CHARGE_TRIGGER_NUMBER = 2;
    private static final int MAGICK_CHARGE_NUMBER = 1;
    private static final int MARKING_NUMBER = 1;
    private static final float DAMAGE_PROMOTION = 0.5F;
    private static final int UPGRADE_NUMBER = 2;
    private int elementCounter;

    public KSMOD_UltimateWand()
    {
        super(RELIC_ID, ImageMaster.loadImage(RELIC_IMG_PATH), ImageMaster.loadImage(RELIC_IMG_OTL_PATH), RELIC_TIER, RELIC_SOUND, BASE_TRIGGER_NUMBER, UPDATE_TRIGGER_NUMBER, GAIN_NUMBER, EXTRA_GAIN_NUMBER);
        this.elementCounter = 0;
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0] + START_CHARGE_NUMBER + DESCRIPTIONS[1] + UPGRADE_NUMBER + DESCRIPTIONS[2] + MARKING_NUMBER + DESCRIPTIONS[3] + (int) (DAMAGE_PROMOTION * 100) + DESCRIPTIONS[4] + MAGICK_CHARGE_TRIGGER_NUMBER + DESCRIPTIONS[5] + MAGICK_CHARGE_NUMBER + DESCRIPTIONS[6] + this.GetGainNumber() + DESCRIPTIONS[7] + this.GetExtraGainNumber() + DESCRIPTIONS[8] + this.GetTriggerNumber() + DESCRIPTIONS[9] + this.GetTriggerNumber() + DESCRIPTIONS[10] + this.GetUpdateTriggerNumber() + DESCRIPTIONS[11] + this.GetRestCounterNumber() + DESCRIPTIONS[12];
    }

    public boolean canSpawn()
    {
        return false;
    }

    public AbstractRelic makeCopy()
    {
        return new KSMOD_UltimateWand();
    }

    public void atBattleStart()
    {
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(KSMOD_SealedBook.RELIC_ID))
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new KSMOD_MagickChargePower(AbstractDungeon.player, START_CHARGE_NUMBER), START_CHARGE_NUMBER));
        }
        super.atBattleStart();
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

    public void atTurnStart()
    {
        this.flash();
        AbstractMonster monster = AbstractDungeon.getRandomMonster();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, AbstractDungeon.player, new KSMOD_CerberusMarkPower(monster, DAMAGE_PROMOTION)));
        this.elementCounter = 0;
        super.atTurnStart();
    }

    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction)
    {
        if (targetCard.hasTag(KSMOD_CustomTag.KSMOD_EARTHY_CARD) || targetCard.hasTag(KSMOD_CustomTag.KSMOD_WATERY_CARD) || targetCard.hasTag(KSMOD_CustomTag.KSMOD_FIREY_CARD) || targetCard.hasTag(KSMOD_CustomTag.KSMOD_WINDY_CARD))
        {
            this.elementCounter += 1;
        }
        if (this.elementCounter >= MAGICK_CHARGE_TRIGGER_NUMBER)
        {
            this.flash();
            if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(KSMOD_SealedBook.RELIC_ID))
            {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new KSMOD_MagickChargePower(AbstractDungeon.player, MAGICK_CHARGE_NUMBER), MAGICK_CHARGE_NUMBER));
            }
            this.elementCounter -= MAGICK_CHARGE_TRIGGER_NUMBER;
        }
    }
}