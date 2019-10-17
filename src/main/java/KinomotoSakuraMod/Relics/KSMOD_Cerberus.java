package KinomotoSakuraMod.Relics;

import KinomotoSakuraMod.Powers.KSMOD_CerberusMarkPower;
import KinomotoSakuraMod.Powers.KSMOD_MagickChargePower;
import KinomotoSakuraMod.Utility.KSMOD_Utility;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class KSMOD_Cerberus extends CustomRelic
{
    public static final String RELIC_ID = "KSMOD_Cerberus";
    private static final String RELIC_IMG_PATH = "img/relics/icon/cerberus.png";
    private static final String RELIC_IMG_OTL_PATH = "img/relics/outline/cerberus.png";
    private static final RelicTier RELIC_TIER = RelicTier.RARE;
    private static final LandingSound RELIC_SOUND = AbstractRelic.LandingSound.MAGICAL;
    private static final int CHARGE_NUMBER = 6;
    private static final float DAMAGE_PROMOTION = 0.25F;

    public KSMOD_Cerberus()
    {
        super(RELIC_ID, ImageMaster.loadImage(RELIC_IMG_PATH), ImageMaster.loadImage(RELIC_IMG_OTL_PATH), RELIC_TIER, RELIC_SOUND);
    }

    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0] + CHARGE_NUMBER + this.DESCRIPTIONS[1] + (int) (DAMAGE_PROMOTION * 100) + this.DESCRIPTIONS[2];
    }

    public AbstractRelic makeCopy()
    {
        return new KSMOD_Cerberus();
    }

    public void instantObtain()
    {
        if (AbstractDungeon.player.hasRelic(KSMOD_StarWand.RELIC_ID) && AbstractDungeon.player.hasRelic(KSMOD_Yue.RELIC_ID))
        {
            replaceWand();
        }
        else
        {
            super.instantObtain();
        }
    }

    public void obtain()
    {
        if (AbstractDungeon.player.hasRelic(KSMOD_StarWand.RELIC_ID) && AbstractDungeon.player.hasRelic(KSMOD_Yue.RELIC_ID))
        {
            replaceWand();
        }
        else
        {
            super.obtain();
        }
    }

    private void replaceWand()
    {
        AbstractRelic oldWand = AbstractDungeon.player.getRelic(KSMOD_StarWand.RELIC_ID);
        int targetIndex = AbstractDungeon.player.relics.indexOf(oldWand);
        KSMOD_UltimateWand wand = new KSMOD_UltimateWand();
        wand.counter = oldWand.counter;
        wand.instantObtain(AbstractDungeon.player, targetIndex, true);
        AbstractDungeon.player.loseRelic(KSMOD_StarWand.RELIC_ID);
        AbstractDungeon.player.loseRelic(KSMOD_Yue.RELIC_ID);
    }

    public void atBattleStart()
    {
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(KSMOD_SealedBook.RELIC_ID))
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new KSMOD_MagickChargePower(AbstractDungeon.player, CHARGE_NUMBER), CHARGE_NUMBER));
        }
    }

    public void atTurnStart()
    {
        this.flash();
        AbstractMonster monster = AbstractDungeon.getRandomMonster();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, AbstractDungeon.player, new KSMOD_CerberusMarkPower(monster, DAMAGE_PROMOTION)));
    }
}