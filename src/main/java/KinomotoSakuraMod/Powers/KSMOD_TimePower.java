package KinomotoSakuraMod.Powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class KSMOD_TimePower extends KSMOD_CustomPower
{
    public static final String POWER_ID = "KSMOD_TimePower";
    public static final String POWER_NAME;
    public static final String[] POWER_DESCRIPTIONS;
    private static final String POWER_IMG_PATH = "time";
    private static final PowerType POWER_TYPE = PowerType.DEBUFF;

    static
    {
        PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        POWER_NAME = powerStrings.NAME;
        POWER_DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }

    public KSMOD_TimePower(AbstractMonster target, int amount)
    {
        super(POWER_ID, POWER_NAME, POWER_IMG_PATH, POWER_TYPE, target, amount, false);
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
                KSMOD_TimePower self = KSMOD_TimePower.this;
                if (self.owner instanceof AbstractMonster)
                {
                    ((AbstractMonster) self.owner).setMove((byte) -1, AbstractMonster.Intent.STUN);
                    ((AbstractMonster) self.owner).createIntent();
                }
                this.isDone = true;
            }
        });
    }
}
