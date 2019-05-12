package KinomotoSakuraMod.Powers;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class WindyElementPower extends CustomPower
{
    public static final String POWER_ID = "EarthyElementPower";
    private static final String POWER_NAME;
    private static final String[] POWER_DESCRIPTIONS;
    private static final String POWER_IMG_PATH = "img/powers/default_power.png";
    private static final PowerType POWER_TYPE = PowerType.DEBUFF;
    private static final int DRAW_AMOUNT = 1;

    static
    {
        PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        POWER_NAME = powerStrings.NAME;
        POWER_DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }

    public WindyElementPower(AbstractCreature target, int amount)
    {
        super(POWER_ID, POWER_NAME, POWER_IMG_PATH, POWER_TYPE, target, amount);
        this.updateDescription();
    }

    public void updateDescription()
    {
        this.description = POWER_DESCRIPTIONS[0] + DRAW_AMOUNT + POWER_DESCRIPTIONS[1];
    }

    public static boolean TryActiveWindyElement(AbstractMonster target, int needAmount, boolean isExhaust)
    {
        AbstractPower power;
        if (target.hasPower(WindyElementPower.POWER_ID))
        {
            power = target.getPower(WindyElementPower.POWER_ID);
        }
        else
        {
            return false;
        }
        if (power.amount >= needAmount)
        {
            AbstractPlayer player = AbstractDungeon.player;
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(player, DRAW_AMOUNT));
            if (isExhaust)
            {
                if (target.hasPower(LockPower.POWER_ID))
                {
                    LockPower lockpower = (LockPower) target.getPower(LockPower.POWER_ID);
                    lockpower.OnActived();
                }
                else
                {
                    if (power.amount == needAmount)
                    {
                        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(target, player, power));
                    }
                    else
                    {
                        AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(target, player, power, needAmount));
                    }
                }
            }
            power.flash();
            return true;
        }
        return false;
    }
}
