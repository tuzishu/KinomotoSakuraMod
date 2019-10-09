package KinomotoSakuraMod.Relics;

import KinomotoSakuraMod.Patches.KSMOD_CustomTag;
import KinomotoSakuraMod.Powers.*;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class KSMOD_SealedBook extends CustomRelic
{
    public static final String RELIC_ID = "KSMOD_SealedBook";
    private static final String RELIC_IMG_PATH = "img/relics/icon/sealed_book.png";
    private static final String RELIC_IMG_OTL_PATH = "img/relics/outline/sealed_book.png";
    private static final RelicTier RELIC_TIER = RelicTier.STARTER;
    private static final LandingSound RELIC_SOUND = AbstractRelic.LandingSound.MAGICAL;
    public static final int ACTIVE_NUMBER = 10;
    public static final int EARTHY_BLOCK_NUMBER = 4;
    public static final int FIREY_DAMAGE_NUMBER = 3;
    public static final int WATERY_ENERGY_TRIGGER = 2;
    public static final int WINDY_DRAW_TRIGGER = 2;

    public static final int REAL_DAMAGE = 15;
    public static final int EXTRA_BLOCK = 14;
    public static final int BASE_BLOCK = 12;
    public static final int ENTIRETY_DAMAGE = 9;
    public static final int EXTRA_POISON = 7;
    public static final int METALLICIZE_NUMBER = 4;
    public static final int WEAKENED_NUMBER = 3;
    public static final int VULNERABLE_NUMBER = 3;
    public static final int SONG_NUMBER = 3;
    public static final int DRAW_NUMBER = 2;
    public static final int STRENGTH_NUMBER = 2;
    public static final int ENERGY_NUMBER = 2;
    public static final int EMPTYSPELL_NUMBER = 2;
    public static final int BLUR_NUMBER = 2;
    public static final int TEMPCARD_NUMBER = 1;
    public static final int EXTRA_COPY_NUMBER = 1;
    public static final float EXTRA_KILL_LINE = 0.5F;
    public static final float PERCENTAGE_RATE = 0.2F;

    public KSMOD_SealedBook()
    {
        super(RELIC_ID, ImageMaster.loadImage(RELIC_IMG_PATH), ImageMaster.loadImage(RELIC_IMG_OTL_PATH), RELIC_TIER, RELIC_SOUND);
    }

    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0] + ACTIVE_NUMBER + this.DESCRIPTIONS[1];
    }

    public AbstractRelic makeCopy()
    {
        return new KSMOD_SealedBook();
    }

    public int applyPowerNumberOnce(AbstractCard card)
    {
        int amount = card.type == AbstractCard.CardType.POWER ? 2 : 1;
        if (AbstractDungeon.player.hasPower(KSMOD_EarthyPower.POWER_ID) && card.hasTag(KSMOD_CustomTag.KSMOD_EARTHY_CARD))
        {
            amount += 1;
        }
        if (AbstractDungeon.player.hasPower(KSMOD_WateryPower.POWER_ID) && card.hasTag(KSMOD_CustomTag.KSMOD_WATERY_CARD))
        {
            amount += 1;
        }
        if (AbstractDungeon.player.hasPower(KSMOD_FireyPower.POWER_ID) && card.hasTag(KSMOD_CustomTag.KSMOD_FIREY_CARD))
        {
            amount += 1;
        }
        if (AbstractDungeon.player.hasPower(KSMOD_WindyPower.POWER_ID) && card.hasTag(KSMOD_CustomTag.KSMOD_WINDY_CARD))
        {
            amount += 1;
        }
        if (AbstractDungeon.player.hasPower(KSMOD_EarthyPower_SakuraCard.POWER_ID) && card.hasTag(KSMOD_CustomTag.KSMOD_EARTHY_CARD))
        {
            amount += 1;
        }
        if (AbstractDungeon.player.hasPower(KSMOD_WateryPower_SakuraCard.POWER_ID) && card.hasTag(KSMOD_CustomTag.KSMOD_WATERY_CARD))
        {
            amount += 1;
        }
        if (AbstractDungeon.player.hasPower(KSMOD_FireyPower_SakuraCard.POWER_ID) && card.hasTag(KSMOD_CustomTag.KSMOD_FIREY_CARD))
        {
            amount += 1;
        }
        if (AbstractDungeon.player.hasPower(KSMOD_WindyPower_SakuraCard.POWER_ID) && card.hasTag(KSMOD_CustomTag.KSMOD_WINDY_CARD))
        {
            amount += 1;
        }
        return amount;
    }
}
