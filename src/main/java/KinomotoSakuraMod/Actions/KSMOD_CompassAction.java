package KinomotoSakuraMod.Actions;

import KinomotoSakuraMod.Patches.KSMOD_ModifyMaxHandCardNumberPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import java.util.ArrayList;
import java.util.Iterator;

public class KSMOD_CompassAction extends AbstractGameAction
{
    public static final String ACTION_ID = "KSMOD_CompassAction";
    private static final float DURATION = Settings.ACTION_DUR_FAST;
    private boolean retrieveCard = false;
    private int voidAmount;

    public KSMOD_CompassAction(int voidAmount)
    {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = DURATION;
        this.voidAmount = voidAmount;
    }

    public void update()
    {
        if (this.duration == Settings.ACTION_DUR_FAST)
        {
            AbstractDungeon.cardRewardScreen.customCombatOpen(generateCardChoices(null), CardRewardScreen.TEXT[1], true);
            this.tickDuration();
        }
        else
        {
            if (!this.retrieveCard)
            {
                if (AbstractDungeon.cardRewardScreen.discoveryCard != null)
                {
                    AbstractCard card = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();
                    card.current_x = -1000.0F * Settings.scale;
                    if (AbstractDungeon.player.hand.size() < KSMOD_ModifyMaxHandCardNumberPatch.GetCurrentMaxHandSize())
                    {
                        AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(card, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                    }
                    else
                    {
                        AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(card, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                    }

                    card.setCostForTurn(0);
                    if (voidAmount > 0)
                    {
                        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new VoidCard(), voidAmount));
                        AbstractDungeon.player.masterDeck.addToBottom(card.makeCopy());
                    }
                    AbstractDungeon.cardRewardScreen.discoveryCard = null;
                }

                this.retrieveCard = true;
            }

            this.tickDuration();
        }
    }

    private ArrayList<AbstractCard> generateCardChoices(AbstractCard.CardType type)
    {
        ArrayList derp = new ArrayList<>();
        while (derp.size() != 3)
        {
            boolean dupe = false;
            AbstractCard tmp = null;
            if (type == null)
            {
                tmp = AbstractDungeon.returnTrulyRandomCardInCombat();
            }
            else
            {
                tmp = AbstractDungeon.returnTrulyRandomCardInCombat(type);
            }
            Iterator var5 = derp.iterator();
            while (var5.hasNext())
            {
                AbstractCard c = (AbstractCard) var5.next();
                if (c.cardID.equals(tmp.cardID))
                {
                    dupe = true;
                    break;
                }
            }
            if (!dupe)
            {
                derp.add(tmp.makeCopy());
            }
        }

        return derp;
    }
}