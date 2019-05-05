package KinomotoSakuraMod.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class LoopAction extends AbstractGameAction
{
    private static final float DURATION = Settings.ACTION_DUR_XFAST;
    private AbstractPlayer player;
    private AbstractCard targetCard;

    public LoopAction(AbstractCard card)
    {
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.player = AbstractDungeon.player;
        this.targetCard = card;
        this.duration = DURATION;
    }

    public void update()
    {
        if (player.discardPile.contains(targetCard))
        {
            AbstractDungeon.actionManager.addToBottom(new DiscardToHandAction(targetCard));
            this.isDone = true;
        }
        tickDuration();
    }
}
