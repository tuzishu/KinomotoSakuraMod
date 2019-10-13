package KinomotoSakuraMod.Powers;

import KinomotoSakuraMod.Utility.KSMOD_Utility;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class KSMOD_SleepPower extends KSMOD_CustomPower
{
    public static final String POWER_ID = "KSMOD_SleepPower";
    public static final String POWER_NAME;
    public static final String[] POWER_DESCRIPTIONS;
    private static final String POWER_IMG_PATH = "img/powers/default_power.png";
    private static final PowerType POWER_TYPE = PowerType.DEBUFF;
    private static final int WEAKENED_NUMBER = 2;
    private int recordHP;

    static
    {
        PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        POWER_NAME = powerStrings.NAME;
        POWER_DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }

    public KSMOD_SleepPower(AbstractMonster target, int amount)
    {
        super(POWER_ID, POWER_NAME, POWER_IMG_PATH, POWER_TYPE, target, amount);
        this.isTurnBased = true;
        this.updateDescription();
    }

    public void updateDescription()
    {
        this.description = POWER_DESCRIPTIONS[0] + this.amount + POWER_DESCRIPTIONS[1];
    }

    public void onInitialApplication()
    {
        DoStunAction();
    }

    public void atStartOfTurn()
    {
        DoStunAction();
    }

    public void atEndOfRound()
    {
        if (this.amount > 1)
        {
            DoStunAction();
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this, 1));
        }
        else
        {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this));
            AbstractDungeon.actionManager.addToTop(new HealAction(this.owner, this.owner, this.owner.maxHealth - this.owner.currentHealth));
        }
    }

    public void CustomOnAttacked(DamageInfo info)
    {
        if (info.output > 0)
        {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
    }

    public void onRemove()
    {
        if (this.owner instanceof AbstractMonster)
        {
            AbstractMonster monster = (AbstractMonster) this.owner;
            monster.rollMove();
            monster.createIntent();
            monster.applyPowers();
        }
    }

    private void DoStunAction()
    {
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction()
        {
            public void update()
            {
                KSMOD_SleepPower self = KSMOD_SleepPower.this;
                if (self.owner instanceof AbstractMonster && ((AbstractMonster) self.owner).type != AbstractMonster.EnemyType.BOSS)
                {
                    ((AbstractMonster) self.owner).setMove((byte) -1, AbstractMonster.Intent.SLEEP);
                    ((AbstractMonster) self.owner).createIntent();
                }
                this.isDone = true;
            }
        });
    }
}
