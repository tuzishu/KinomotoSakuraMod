package KinomotoSakuraMod.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.powers.EnergizedBluePower;

public class KSMOD_DarkAction extends AbstractGameAction
{
    private static final String ACTION_ID = "KSMOD_DarkAction";
    private static final String[] TEXT;
    private static final float DURATION = Settings.ACTION_DUR_XFAST;
    private AbstractPlayer player;

    static
    {
        UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ACTION_ID);
        TEXT = uiStrings.TEXT;
    }

    public KSMOD_DarkAction(int amount)
    {
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.player = AbstractDungeon.player;
        this.amount = amount;
        this.duration = DURATION;
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
            if (this.player.hand.isEmpty())
            {
                this.isDone = true;
                return;
            }
            AbstractDungeon.handCardSelectScreen.open(TEXT[0], this.amount, true, true);
            tickDuration();
            return;
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved)
        {
            int count = AbstractDungeon.handCardSelectScreen.selectedCards.size();
            for (AbstractCard card : AbstractDungeon.handCardSelectScreen.selectedCards.group)
            {
                this.player.hand.moveToExhaustPile(card);
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            if (count > 0)
            {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new EnergizedBluePower(player, 1)));
            }
        }

        tickDuration();
    }
}
