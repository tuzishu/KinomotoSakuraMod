package KinomotoSakuraMod.Actions;

import KinomotoSakuraMod.Utility.ModUtility;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
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
import com.megacrit.cardcrawl.vfx.combat.ThrowDaggerEffect;

public class ArrowAction extends AbstractGameAction
{
    public static final String ACTION_ID = "ArrowAction";
    private static final String[] TEXT;
    private static final float DURATION = Settings.ACTION_DUR_FAST;
    private static final float DURATION_ATTACK = 0.02F;
    private AbstractPlayer player;
    private int damage;

    static
    {
        UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ACTION_ID);
        TEXT = uiStrings.TEXT;
    }

    public ArrowAction(int damage)
    {
        this.actionType = ActionType.DAMAGE;
        this.player = AbstractDungeon.player;
        this.duration = DURATION;
        this.damage = damage;
    }

    public void update()
    {
        if (this.duration == Settings.ACTION_DUR_FAST)
        {
            if (this.player.hand.isEmpty() && EnergyPanel.getCurrentEnergy() == 0)
            {
                this.isDone = true;
                return;
            }
            else if (this.player.hand.isEmpty())
            {
                int count = EnergyPanel.getCurrentEnergy();
                this.player.energy.use(EnergyPanel.totalCount);
                AttackRandomTarget(count);
                this.isDone = true;
                return;
            }
            AbstractDungeon.handCardSelectScreen.open(TEXT[0], this.player.gameHandSize, true, true);
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
            count += EnergyPanel.getCurrentEnergy();
            this.player.energy.use(EnergyPanel.totalCount);
            AttackRandomTarget(count);
        }
        tickDuration();
    }

    private void AttackRandomTarget(int count)
    {
        AbstractMonster monster = AbstractDungeon.getRandomMonster();
        if (count > 0)
        {
            for (int i = 0; i < count; i++)
            {

                AbstractDungeon.actionManager.addToBottom(new VFXAction(this.player, new ThrowDaggerEffect(monster.hb.cX, monster.hb.cY), DURATION_ATTACK));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, new DamageInfo(player, this.damage), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
            }
        }
    }
}
