package KinomotoSakuraMod.Relics;

import KinomotoSakuraMod.Cards.SpellCard.SpellCardEmptySpell;
import KinomotoSakuraMod.KSMOD;
import KinomotoSakuraMod.Patches.KSMOD_CustomCardColor;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class KSMOD_TaoistSuit extends CustomRelic
{
    public static final String RELIC_ID = "KSMOD_TaoistSuit";
    private static final String RELIC_IMG_PATH = "img/relics/icon/default.png";
    private static final String RELIC_IMG_OTL_PATH = "img/relics/outline/default.png";
    private static final AbstractRelic.RelicTier RELIC_TIER = AbstractRelic.RelicTier.UNCOMMON;
    private static final AbstractRelic.LandingSound RELIC_SOUND = AbstractRelic.LandingSound.FLAT;
    private static final int TRIGGER_NUMBER = 4;

    public KSMOD_TaoistSuit()
    {
        super(RELIC_ID, ImageMaster.loadImage(RELIC_IMG_PATH), ImageMaster.loadImage(RELIC_IMG_OTL_PATH), RELIC_TIER, RELIC_SOUND);
        this.setCounter(0);
    }

    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0] + TRIGGER_NUMBER + this.DESCRIPTIONS[1];
    }

    public AbstractRelic makeCopy()
    {
        return new KSMOD_TaoistSuit();
    }

    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction)
    {
        if (targetCard.color == KSMOD_CustomCardColor.CLOWCARD_COLOR || targetCard.color == KSMOD_CustomCardColor.SAKURACARD_COLOR)
        {
            this.counter += 1;
            if (this.counter >= TRIGGER_NUMBER)
            {
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new SpellCardEmptySpell()));
                this.counter -= TRIGGER_NUMBER;
            }
        }
    }
}

