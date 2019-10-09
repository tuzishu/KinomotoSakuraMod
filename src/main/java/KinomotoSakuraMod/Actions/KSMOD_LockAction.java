package KinomotoSakuraMod.Actions;

import KinomotoSakuraMod.Powers.KSMOD_MagickChargePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public class KSMOD_LockAction extends AbstractGameAction
{
    public static final String ACTION_ID = "KSMOD_LockAction";
    private static final String[] TEXT;
    private static final float DURATION = Settings.ACTION_DUR_FAST;
    private AbstractPlayer player;
    private AbstractCard lockCard;

    static
    {
        UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ACTION_ID);
        TEXT = uiStrings.TEXT;
    }

    public KSMOD_LockAction(int amount, AbstractCard lockCard)
    {
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
        this.player = AbstractDungeon.player;
        this.duration = DURATION;
        this.amount = amount;
        this.lockCard = lockCard;
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
            int count = 0;
            if (AbstractDungeon.handCardSelectScreen.selectedCards.size() == 0)
            {
                AbstractDungeon.player.hand.moveToExhaustPile(lockCard);
            }
            else
            {
                for (AbstractCard card : AbstractDungeon.handCardSelectScreen.selectedCards.group)
                {
                    this.player.hand.moveToExhaustPile(card);
                    count += 1;
                }
            }
            if (count > 0)
            {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new KSMOD_MagickChargePower(player, count), count));
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
        }
        tickDuration();
    }
}
