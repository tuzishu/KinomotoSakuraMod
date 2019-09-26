package KinomotoSakuraMod.Relics;

import KinomotoSakuraMod.Cards.SpellCard.SpellCardEmptySpell;
import KinomotoSakuraMod.Cards.SpellCard.SpellCardRelease;
import KinomotoSakuraMod.Patches.KSMOD_CustomCardColor;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class KSMOD_TeddyBear extends CustomRelic
{
    public static final String RELIC_ID = "KSMOD_TeddyBear";
    private static final String RELIC_IMG_PATH = "img/relics/icon/default.png";
    private static final String RELIC_IMG_OTL_PATH = "img/relics/outline/default.png";
    private static final AbstractRelic.RelicTier RELIC_TIER = RelicTier.COMMON;
    private static final AbstractRelic.LandingSound RELIC_SOUND = LandingSound.FLAT;
    private static final int BLOCK_NUMBER = 3;

    public KSMOD_TeddyBear()
    {
        super(RELIC_ID, ImageMaster.loadImage(RELIC_IMG_PATH), ImageMaster.loadImage(RELIC_IMG_OTL_PATH), RELIC_TIER, RELIC_SOUND);
    }

    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0] + BLOCK_NUMBER + this.DESCRIPTIONS[1];
    }

    public AbstractRelic makeCopy()
    {
        return new KSMOD_TeddyBear();
    }

    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction)
    {
        if (targetCard.color == KSMOD_CustomCardColor.SPELL_COLOR)
        {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, BLOCK_NUMBER));
        }
    }
}
