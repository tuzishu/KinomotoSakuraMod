package KinomotoSakuraMod.Relics;

import KinomotoSakuraMod.Cards.SpellCard.SpellCardTurn;
import KinomotoSakuraMod.Characters.KinomotoSakura;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

public class KSMOD_StarWand extends KSMOD_AbstractWand
{
    public static final String RELIC_ID = "KSMOD_StarWand";
    private static final String RELIC_IMG_PATH = "img/relics/icon/star_wand.png";
    private static final String RELIC_IMG_OTL_PATH = "img/relics/outline/star_wand.png";
    private static final RelicTier RELIC_TIER = RelicTier.BOSS;
    private static final LandingSound RELIC_SOUND = AbstractRelic.LandingSound.MAGICAL;
    private static final int GAIN_NUMBER = 4;
    private static final int BASE_TRIGGER_NUMBER = 35;
    private static final int UPDATE_TRIGGER_NUMBER = 20;

    public KSMOD_StarWand()
    {
        super(RELIC_ID, ImageMaster.loadImage(RELIC_IMG_PATH), ImageMaster.loadImage(RELIC_IMG_OTL_PATH), RELIC_TIER, RELIC_SOUND, BASE_TRIGGER_NUMBER, UPDATE_TRIGGER_NUMBER, GAIN_NUMBER);
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0] + this.GetGainNumber() + DESCRIPTIONS[1] + GetTriggerNumber() + DESCRIPTIONS[2] + GetTriggerNumber() + DESCRIPTIONS[3] + this.GetUpdateTriggerNumber() + DESCRIPTIONS[4] + this.GetRestCounterNumber() + DESCRIPTIONS[5];
    }

    public boolean canSpawn()
    {
        return AbstractDungeon.player instanceof KinomotoSakura && AbstractDungeon.player.hasRelic(KSMOD_SealedWand.RELIC_ID);
    }

    public AbstractRelic makeCopy()
    {
        return new KSMOD_StarWand();
    }

    public void instantObtain()
    {
        AbstractRelic oldWand = AbstractDungeon.player.getRelic(KSMOD_SealedWand.RELIC_ID);
        if (oldWand == null)
        {
            super.instantObtain();
        }
        int targetIndex = AbstractDungeon.player.relics.indexOf(oldWand);
        if (AbstractDungeon.player.hasRelic(KSMOD_SealedWand.RELIC_ID))
        {
            if (AbstractDungeon.player.hasRelic(KSMOD_Cerberus.RELIC_ID) && AbstractDungeon.player.hasRelic(KSMOD_Yue.RELIC_ID))
            {
                KSMOD_UltimateWand wand = new KSMOD_UltimateWand();
                wand.counter = oldWand.counter;
                wand.instantObtain(AbstractDungeon.player, targetIndex, true);
                AbstractDungeon.player.loseRelic(KSMOD_Cerberus.RELIC_ID);
                AbstractDungeon.player.loseRelic(KSMOD_Yue.RELIC_ID);
            }
            else
            {
                this.counter = oldWand.counter;
                this.instantObtain(AbstractDungeon.player, targetIndex, false);
            }
        }
        else
        {
            super.instantObtain();
        }
    }

    public void obtain()
    {
        AbstractRelic oldWand = AbstractDungeon.player.getRelic(KSMOD_SealedWand.RELIC_ID);
        int targetIndex = AbstractDungeon.player.relics.indexOf(oldWand);
        if (AbstractDungeon.player.hasRelic(KSMOD_SealedWand.RELIC_ID))
        {
            if (AbstractDungeon.player.hasRelic(KSMOD_Cerberus.RELIC_ID) && AbstractDungeon.player.hasRelic(KSMOD_Yue.RELIC_ID))
            {
                KSMOD_UltimateWand wand = new KSMOD_UltimateWand();
                wand.counter = oldWand.counter;
                wand.instantObtain(AbstractDungeon.player, targetIndex, true);
                AbstractDungeon.player.loseRelic(KSMOD_Cerberus.RELIC_ID);
                AbstractDungeon.player.loseRelic(KSMOD_Yue.RELIC_ID);
            }
            else
            {
                this.counter = oldWand.counter;
                this.instantObtain(AbstractDungeon.player, targetIndex, false);
            }
        }
        else
        {
            super.obtain();
        }
    }

    @Override
    public void ActiveRelic()
    {
        this.setCounter(this.counter -= GetTriggerNumber());
        if (this.counter < 0)
        {
            this.setCounter(0);
        }
        AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractCard card = new SpellCardTurn();
        AbstractDungeon.player.masterDeck.addToBottom(card.makeSameInstanceOf());
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(card));
    }
}
