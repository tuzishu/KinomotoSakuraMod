package KinomotoSakuraMod.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

public class KSMOD_ThunderAction extends AbstractGameAction
{
    public static final String ACTION_ID = "KSMOD_ThunderAction";
    private static final String SOUND_KEY = "ORB_LIGHTNING_EVOKE";
    private DamageInfo info;
    private int numTimes;

    public KSMOD_ThunderAction(DamageInfo info)
    {
        this(info, 1);
    }

    public KSMOD_ThunderAction(DamageInfo info, int numTimes)
    {
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
        this.info = info;
        this.numTimes = numTimes;
    }

    public void update()
    {
        if (AbstractDungeon.getCurrRoom().isBattleEnding() || AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead())
        {
            AbstractDungeon.actionManager.clearPostCombatActions();
            this.isDone = true;
            return;
        }
        this.target = AbstractDungeon.getRandomMonster();
        if (this.target != null && this.target.currentHealth > 0)
        {
            this.target.damageFlash = true;
            this.target.damageFlashFrames = 4;
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect));
            AbstractDungeon.effectList.add(new LightningEffect(this.target.drawX, this.target.drawY));
            CardCrawlGame.sound.play(SOUND_KEY, 0.1F);
            DamageInfo info = new DamageInfo(this.info.owner, this.info.base, this.info.type);
            // info.applyPowers(info.owner, this.target);
            this.target.damage(info);
            if (this.numTimes > 1 && !AbstractDungeon.getMonsters().areMonstersBasicallyDead())
            {
                AbstractDungeon.actionManager.addToTop(new KSMOD_ThunderAction(this.info, this.numTimes - 1));
            }
            AbstractDungeon.actionManager.addToTop(new WaitAction(0.2F));
        }
        this.isDone = true;
    }
}
