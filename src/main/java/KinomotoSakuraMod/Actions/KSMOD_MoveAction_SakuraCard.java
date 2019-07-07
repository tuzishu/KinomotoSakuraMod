package KinomotoSakuraMod.Actions;

import KinomotoSakuraMod.Powers.KSMOD_LightPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public class KSMOD_MoveAction_SakuraCard extends AbstractGameAction
{
    public static final String ACTION_ID = "KSMOD_MoveAction_SakuraCard";
    private static final String[] TEXT;
    private static final float DURATION = Settings.ACTION_DUR_FASTER;
    private AbstractPlayer player;

    static
    {
        UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ACTION_ID);
        TEXT = uiStrings.TEXT;
    }

    public KSMOD_MoveAction_SakuraCard(int amount)
    {
        this.player = AbstractDungeon.player;
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = DURATION;
        this.amount = amount;
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
            if (this.player.drawPile.isEmpty())
            {
                this.isDone = true;
                return;
            }
            if (this.player.hand.size() == GetCurrentMaxHandSize())
            {
                this.player.createHandIsFullDialog();
                this.isDone = true;
                return;
            }

            if (this.player.drawPile.size() == 1)
            {
                AbstractCard card = this.player.drawPile.group.get(0);
                card.unfadeOut();
                card.unhover();
                card.fadingOut = false;
                this.player.hand.addToHand(card);
                this.player.drawPile.removeCard(card);
                this.isDone = true;
            }
            else
            {
                for (AbstractCard card : this.player.drawPile.group)
                {
                    card.stopGlowing();
                    card.unhover();
                    card.unfadeOut();
                }
                AbstractDungeon.gridSelectScreen.open(this.player.drawPile, this.amount, false, TEXT[0]);
                this.tickDuration();
            }
        }
        else
        {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty())
            {
                for (AbstractCard card : AbstractDungeon.gridSelectScreen.selectedCards)
                {
                    this.player.hand.addToHand(card);
                    this.player.drawPile.removeCard(card);
                    card.unhover();
                }
                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                this.player.hand.refreshHandLayout();
                for (AbstractCard card : this.player.drawPile.group)
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
