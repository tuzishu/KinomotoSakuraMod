package KinomotoSakuraMod.Actions;

import KinomotoSakuraMod.Cards.ClowCard.ClowCardTheVoice;
import KinomotoSakuraMod.Utility.ModUtility;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;

public class SongAction extends AbstractGameAction
{
    public static final String ACTION_ID = "SongAction";
    private static final String[] TEXT;
    private static final float DURATION = Settings.ACTION_DUR_FAST;
    private static final float WAVE_EFFECT_DURATION = 0.6F;
    private static final int VOICE_EXTRA_COUNT = 2;
    private AbstractPlayer player;
    private int damage;
    private int block;

    static
    {
        UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ACTION_ID);
        TEXT = uiStrings.TEXT;
    }

    public SongAction(int damage, int block)
    {
        this.actionType = ActionType.DAMAGE;
        this.player = AbstractDungeon.player;
        this.duration = DURATION;
        this.damage = damage;
        this.block = block;
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
            AbstractDungeon.handCardSelectScreen.open(TEXT[0], EnergyPanel.getCurrentEnergy() + 1, true, true);
            tickDuration();
            return;
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved)
        {
            int count = 0;
            for (AbstractCard card : AbstractDungeon.handCardSelectScreen.selectedCards.group)
            {
                count += card instanceof ClowCardTheVoice ? 1 + VOICE_EXTRA_COUNT : 1;
                this.player.hand.moveToExhaustPile(card);
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.player.energy.use(EnergyPanel.totalCount);
            AttackTargetForTimes(count);
        }
        tickDuration();
    }

    private void AttackTargetForTimes(int count)
    {
        if (count > 0)
        {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("THUNDERCLAP"));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(player, new ShockWaveEffect(player.hb.cX, player.hb.cY, Settings.CREAM_COLOR, ShockWaveEffect.ShockWaveType.CHAOTIC), WAVE_EFFECT_DURATION));
            for (int i = 0; i < count; i++)
            {
                AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(player, ModUtility.GetDamageList(this.damage), DamageInfo.DamageType.NORMAL, AttackEffect.BLUNT_LIGHT, true));
            }
            for (int i = 0; i < count; i++)
            {
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(player, player, block));
            }
        }
    }
}
