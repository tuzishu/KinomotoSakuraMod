package KinomotoSakuraMod.Relics;

import KinomotoSakuraMod.Patches.KSMOD_CustomTag;
import KinomotoSakuraMod.Powers.KSMOD_CerberusMarkPower;
import KinomotoSakuraMod.Powers.KSMOD_MagickChargePower;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class KSMOD_Cerberus extends CustomRelic
{
    public static final String RELIC_ID = "KSMOD_Cerberus";
    private static final String RELIC_IMG_PATH = "img/relics/icon/cerberus.png";
    private static final String RELIC_IMG_OTL_PATH = "img/relics/outline/cerberus.png";
    private static final RelicTier RELIC_TIER = RelicTier.UNCOMMON;
    private static final LandingSound RELIC_SOUND = AbstractRelic.LandingSound.MAGICAL;
    private static final int START_COUNT = 0;
    private static final int TRIGGER_NUMBER = 3;
    private static final int CHARGE_NUMBER = 1;
    private static final float DAMAGE_PROMOTION = 0.25F;

    public KSMOD_Cerberus()
    {
        super(RELIC_ID, ImageMaster.loadImage(RELIC_IMG_PATH), ImageMaster.loadImage(RELIC_IMG_OTL_PATH), RELIC_TIER, RELIC_SOUND);
        this.counter = START_COUNT;
    }

    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0] + TRIGGER_NUMBER + this.DESCRIPTIONS[1] + (int) (DAMAGE_PROMOTION * 100) + this.DESCRIPTIONS[2];
    }

    public AbstractRelic makeCopy()
    {
        return new KSMOD_Cerberus();
    }

    public void obtain()
    {
        if (AbstractDungeon.player.hasRelic(KSMOD_StarWand.RELIC_ID) && AbstractDungeon.player.hasRelic(KSMOD_Yue.RELIC_ID))
        {
            AbstractRelic oldWand = AbstractDungeon.player.getRelic(KSMOD_StarWand.RELIC_ID);
            int targetIndex = AbstractDungeon.player.relics.indexOf(oldWand);
            KSMOD_UltimateWand wand = new KSMOD_UltimateWand();
            wand.counter = oldWand.counter;
            wand.instantObtain(AbstractDungeon.player, targetIndex, true);
            AbstractDungeon.player.loseRelic(KSMOD_StarWand.RELIC_ID);
            AbstractDungeon.player.loseRelic(KSMOD_Yue.RELIC_ID);
        }
        else
        {
            super.obtain();
        }
    }

    public void atBattleStart()
    {
        this.setCounter(0);
    }

    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction)
    {
        if (targetCard.hasTag(KSMOD_CustomTag.KSMOD_FIREY_CARD) || targetCard.hasTag(KSMOD_CustomTag.KSMOD_EARTHY_CARD))
        {
            this.counter += 1;
        }
        if (this.counter >= TRIGGER_NUMBER)
        {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new KSMOD_MagickChargePower(AbstractDungeon.player, CHARGE_NUMBER), CHARGE_NUMBER));
            this.counter -= TRIGGER_NUMBER;
        }
    }

    public void atTurnStart()
    {
        this.flash();
        AbstractMonster monster = AbstractDungeon.getRandomMonster();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, AbstractDungeon.player, new KSMOD_CerberusMarkPower(monster, DAMAGE_PROMOTION)));
    }
}