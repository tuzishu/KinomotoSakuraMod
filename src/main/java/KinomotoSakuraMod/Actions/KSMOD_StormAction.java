package KinomotoSakuraMod.Actions;

import KinomotoSakuraMod.Utility.KSMOD_Utility;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;

public class KSMOD_StormAction extends AbstractGameAction
{
    public static final String ACTION_ID = "KSMOD_StormAction";
    private static final float DURATION = Settings.ACTION_DUR_FAST;
    private static final String SFX_EFFECT_ID = "STANCE_ENTER_CALM";
    private AbstractPlayer player;
    private DamageInfo damageInfo;
    private AbstractMonster monster;
    private int attackCount;

    public KSMOD_StormAction(DamageInfo damageInfo, int attackCount)
    {
        this(null, damageInfo, attackCount);
    }

    public KSMOD_StormAction(AbstractMonster monster, DamageInfo damageInfo, int attackCount)
    {
        this.actionType = ActionType.DAMAGE;
        this.player = AbstractDungeon.player;
        this.duration = DURATION;
        this.monster = monster;
        this.damageInfo = damageInfo;
        this.attackCount = attackCount;
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
            if (monster == null)
            {
                AbstractDungeon.actionManager.addToBottom(new SFXAction(SFX_EFFECT_ID));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new WhirlwindEffect(), 0.0F));
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new VoidCard(), 1));
                for (int i = 0; i < this.attackCount; ++i)
                {
                    if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead() || AbstractDungeon.getCurrRoom().monsters.haveMonstersEscaped() || AbstractDungeon.getCurrRoom().monsters.isMonsterEscaping())
                    {
                        this.isDone = true;
                        return;
                    }
                    AbstractDungeon.actionManager.addToBottom(new VFXAction(player, new CleaveEffect(), 0.0F));
                    AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(player, KSMOD_Utility.GetDamageList(this.damageInfo.output), DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, true));
                }
            }
            else
            {
                AbstractDungeon.actionManager.addToBottom(new SFXAction(SFX_EFFECT_ID));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new WhirlwindEffect(), 0.0F));
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new VoidCard(), 1));
                for (int i = 0; i < this.attackCount; ++i)
                {
                    if (this.monster.isDying || this.monster.currentHealth <= 0 || this.monster.halfDead)
                    {
                        this.isDone = true;
                        return;
                    }
                    AbstractDungeon.actionManager.addToBottom(new VFXAction(player, new CleaveEffect(), 0.0F));
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, damageInfo, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, true));
                }
            }
        }

        tickDuration();
    }
}
