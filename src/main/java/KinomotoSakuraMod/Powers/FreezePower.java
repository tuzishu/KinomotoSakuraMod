package KinomotoSakuraMod.Powers;

import KinomotoSakuraMod.Actions.ApplyElementAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FreezePower extends CustomPower
{
    public static final String POWER_ID = "FreezePower";
    public static final String POWER_NAME;
    public static final String[] POWER_DESCRIPTIONS;
    private static final String POWER_IMG_PATH = "img/powers/default_power.png";
    private static final PowerType POWER_TYPE = PowerType.DEBUFF;

    static
    {
        PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        POWER_NAME = powerStrings.NAME;
        POWER_DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }

    public FreezePower(AbstractMonster target, int amount)
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
        if (!(this.owner instanceof AbstractMonster))
        {
            return;
        }
        AbstractMonster monster = (AbstractMonster) this.owner;
        if (monster.type == AbstractMonster.EnemyType.BOSS || monster.type == AbstractMonster.EnemyType.ELITE)
        {
            return;
        }
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction()
        {
            public void update()
            {
                FreezePower self = FreezePower.this;
                if (self.owner instanceof AbstractMonster)
                {
                    ((AbstractMonster) self.owner).setMove((byte) -1, AbstractMonster.Intent.STUN);
                    ((AbstractMonster) self.owner).createIntent();
                }
                this.isDone = true;
            }
        });
    }

    public void atEndOfRound()
    {
        AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this));
    }

    public void onRemove()
    {
        if (this.owner instanceof AbstractMonster)
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyElementAction(this.owner, AbstractDungeon.player, new WateryElementPower(this.owner, this.amount), this.amount, true));
        }
    }
}
