package KinomotoSakuraMod.Relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class SealedWand extends CustomRelic
{
    public static final String ID = "SealedWand";
    private static final String IMG = "img/relics/icon/SealedWand.png";
    private static final String IMG_OTL = "img/relics/outline/SealedWand.png";

    public SealedWand()
    {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), AbstractRelic.RelicTier.STARTER, AbstractRelic.LandingSound.MAGICAL);
    }

    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy()
    {
        return new SealedWand();
    }

    public void onUseCard(AbstractCard card, UseCardAction action)
    {

    }
}
