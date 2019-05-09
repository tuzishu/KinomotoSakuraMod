package KinomotoSakuraMod.Powers;

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
import com.megacrit.cardcrawl.monsters.EnemyMoveInfo;

import java.lang.reflect.Field;

public class SleepPower extends CustomPower
{
    public static final String POWER_ID = "SleepPower";
    public static final String POWER_NAME;
    public static final String[] POWER_DESCRIPTIONS;
    private static final String POWER_IMG_PATH = "img/powers/default_power.png";
    private static final PowerType POWER_TYPE = PowerType.DEBUFF;
    private byte moveByte;
    private AbstractMonster.Intent moveIntent;
    private EnemyMoveInfo move;

    static
    {
        PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        POWER_NAME = powerStrings.NAME;
        POWER_DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }

    public SleepPower(AbstractMonster target, int amount)
    {
        super(POWER_ID, POWER_NAME, POWER_IMG_PATH, POWER_TYPE, target, amount);
        this.isTurnBased = true;
        this.updateDescription();
    }

    public void updateDescription()
    {
        this.description = POWER_DESCRIPTIONS[0] + this.amount + POWER_DESCRIPTIONS[1];

    }

    public void atEndOfRound()
    {
        if (this.amount <= 0)
        {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
        else
        {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this, 1));
        }

    }

    public void onInitialApplication()
    {
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction()
        {
            public void update()
            {
                if (SleepPower.this.owner instanceof AbstractMonster)
                {
                    SleepPower.this.moveByte = ((AbstractMonster) SleepPower.this.owner).nextMove;
                    SleepPower.this.moveIntent = ((AbstractMonster) SleepPower.this.owner).intent;

                    try
                    {
                        Field f = AbstractMonster.class.getDeclaredField("move");
                        f.setAccessible(true);
                        SleepPower.this.move = (EnemyMoveInfo) f.get(SleepPower.this.owner);
                        SleepPower.this.move.intent = AbstractMonster.Intent.SLEEP;
                        ((AbstractMonster) SleepPower.this.owner).createIntent();
                    }
                    catch (NoSuchFieldException | IllegalAccessException var2)
                    {
                        var2.printStackTrace();
                    }
                }

                this.isDone = true;
            }
        });
    }

    public void onRemove()
    {
        if (this.owner instanceof AbstractMonster)
        {
            AbstractMonster monster = (AbstractMonster) this.owner;
            if (this.move != null)
            {
                monster.setMove(this.moveByte, this.moveIntent, this.move.baseDamage, this.move.multiplier, this.move.isMultiDamage);
            }
            else
            {
                monster.setMove(this.moveByte, this.moveIntent);
            }

            monster.createIntent();
            monster.applyPowers();
            AbstractDungeon.actionManager.addToBottom(new HealAction(monster, monster, monster.maxHealth - monster.currentHealth));
        }
    }

    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target)
    {
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));
    }
}
