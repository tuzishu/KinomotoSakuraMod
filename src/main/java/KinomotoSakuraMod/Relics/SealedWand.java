package KinomotoSakuraMod.Relics;

import KinomotoSakuraMod.Cards.AbstrackSakuraCard;
import KinomotoSakuraMod.Cards.AbstractClowCard;
import KinomotoSakuraMod.Cards.SpellCard.SpellCardTurn;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.QueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
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
        if (card instanceof AbstractClowCard || card instanceof AbstrackSakuraCard)
        {
            ++this.counter;
            if (this.counter % 100 == 0)
            {
                active();
            }
        }
    }

    public void active()
    {
        this.flash();
        this.counter = 0;
        AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, 4));
         AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new SpellCardTurn()));
    }
}
