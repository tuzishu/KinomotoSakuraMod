package KinomotoSakuraMod.Actions;

import KinomotoSakuraMod.Relics.KSMOD_SealedWand;
import KinomotoSakuraMod.Relics.KSMOD_StarWand;
import KinomotoSakuraMod.Relics.KSMOD_UltimateWand;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class KSMOD_SealAction extends AbstractGameAction
{
    private int chargeAmount;
    private DamageInfo info;
    private AttackEffect attackEffect;

    public KSMOD_SealAction(AbstractCreature target, DamageInfo info, AttackEffect attackEffect, int chargeAmount)
    {
        this.info = info;
        this.setValues(target, info);
        this.chargeAmount = chargeAmount;
        this.actionType = ActionType.DAMAGE;
        this.duration = 0.1F;
        this.attackEffect = attackEffect;
    }

    public void update()
    {
        if (this.duration == 0.1F && this.target != null)
        {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect));
            this.target.damage(this.info);
            if ((this.target.isDying || this.target.currentHealth <= 0) && !this.target.halfDead)
            {
                if (AbstractDungeon.player.hasRelic(KSMOD_SealedWand.RELIC_ID))
                {
                    KSMOD_SealedWand wand = (KSMOD_SealedWand) AbstractDungeon.player.getRelic(KSMOD_SealedWand.RELIC_ID);
                    wand.GainCharge(chargeAmount, (AbstractMonster) this.target);
                }
                else if (AbstractDungeon.player.hasRelic(KSMOD_StarWand.RELIC_ID))
                {
                    KSMOD_StarWand wand = (KSMOD_StarWand) AbstractDungeon.player.getRelic(KSMOD_StarWand.RELIC_ID);
                    wand.GainCharge(chargeAmount, (AbstractMonster) this.target);
                }
                else if (AbstractDungeon.player.hasRelic(KSMOD_UltimateWand.RELIC_ID))
                {
                    KSMOD_UltimateWand wand = (KSMOD_UltimateWand) AbstractDungeon.player.getRelic(KSMOD_UltimateWand.RELIC_ID);
                    wand.GainCharge(chargeAmount, (AbstractMonster) this.target);
                }
            }
            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead())
            {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }
        this.tickDuration();
    }
}
