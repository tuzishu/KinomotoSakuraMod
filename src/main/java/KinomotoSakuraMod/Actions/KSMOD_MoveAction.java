package KinomotoSakuraMod.Actions;

import KinomotoSakuraMod.Powers.KSMOD_LightPower;
import KinomotoSakuraMod.Utility.KSMOD_Utility;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

public class KSMOD_MoveAction extends AbstractGameAction
{
    public static final String ACTION_ID = "KSMOD_MoveAction";
    private static final String[] TEXT;
    private static final float DURATION = Settings.ACTION_DUR_FASTER;
    private boolean isCardToHand;
    private CardGroup targetGroup;
    private AbstractPlayer player;

    static
    {
        UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ACTION_ID);
        TEXT = uiStrings.TEXT;
    }

    public KSMOD_MoveAction(int amount, boolean isCardToHand)
    {
        this.player = AbstractDungeon.player;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = DURATION;
        this.amount = amount;
        this.isCardToHand = isCardToHand;
        this.targetGroup = this.isCardToHand ? player.hand : player.drawPile;
    }

    public void update()
    {
        if (AbstractDungeon.getCurrRoom().isBattleEnding())
        {
            this.isDone = true;
            return;
        }
        if (this.duration == DURATION)
        {
            if (this.player.exhaustPile.isEmpty())
            {
                this.isDone = true;
                return;
            }
            if (isCardToHand && this.player.hand.size() == GetCurrentMaxHandSize())
            {
                this.player.createHandIsFullDialog();
                this.isDone = true;
                return;
            }

            if (this.player.exhaustPile.size() == 1)
            {
                AbstractCard card = this.player.exhaustPile.group.get(0);
                if (isCardToHand)
                {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(card, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                    this.targetGroup.moveToHand(card, this.targetGroup);
                }
                else
                {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(card, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F, true));
                    this.targetGroup.moveToDeck(card, true);
                }
                card.unfadeOut();
                card.unhover();
                card.fadingOut = false;
                this.targetGroup.refreshHandLayout();
                this.isDone = true;
            }
            else
            {
                for (AbstractCard card : this.player.exhaustPile.group)
                {
                    card.stopGlowing();
                    card.unhover();
                    card.unfadeOut();
                }
                AbstractDungeon.gridSelectScreen.open(this.player.exhaustPile, this.amount, false, TEXT[0]);
                this.tickDuration();
            }
        }
        else
        {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty())
            {
                for (AbstractCard card : AbstractDungeon.gridSelectScreen.selectedCards)
                {
                    if (isCardToHand)
                    {
                        AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(card, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                        this.targetGroup.addToHand(card);
                    }
                    else
                    {
                        AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(card, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F, true));
                        this.targetGroup.addToRandomSpot(card);
                    }
                    this.player.exhaustPile.removeCard(card);
                    card.unhover();
                }
                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                this.targetGroup.refreshHandLayout();
                for (AbstractCard card : this.player.exhaustPile.group)
                {
                    card.unhover();
                    card.target_x = (float) CardGroup.DISCARD_PILE_X;
                    card.target_y = 0.0F;
                }
                this.isDone = true;
            }
            this.tickDuration();
        }
    }

    public int GetCurrentMaxHandSize()
    {
        return 10 + (AbstractDungeon.player.hasPower(KSMOD_LightPower.POWER_ID) ? 2 : 0);
    }
}
