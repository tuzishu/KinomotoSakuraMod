package KinomotoSakuraMod.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class ArrowAction extends AbstractGameAction
{
    public static final String POWER_ID = "ArrowAction";
    private static final String[] TEXT;
    private AbstractPlayer player;
    private int damage;

    static
    {
        UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(POWER_ID);
        TEXT = uiStrings.TEXT;
    }

    public ArrowAction(int damage)
    {
        this.actionType = ActionType.DAMAGE;
        this.player = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
        this.damage = damage;
    }

    public void update()
    {
        if (this.duration == Settings.ACTION_DUR_FAST)
        {
            if (this.player.hand.isEmpty())
            {
                this.isDone = true;
                return;
            }
            AbstractDungeon.handCardSelectScreen.open(TEXT[0], 99, true, true);
            tickDuration();
            return;
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved)
        {
            int count = 0;
            for (AbstractCard card : AbstractDungeon.handCardSelectScreen.selectedCards.group)
            {
                count += 1;
                this.player.hand.moveToDiscardPile(card);
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();

            AbstractMonster mon = AbstractDungeon.getCurrRoom().monsters.getRandomMonster();
            count += player.energy.energy;
            this.player.energy.use(EnergyPanel.totalCount);
            if (count > 0)
            {
                for (int i = 0; i < count; i++)
                {
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(mon, new DamageInfo(player, this.damage), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
                }
            }
        }
        tickDuration();
    }
}
