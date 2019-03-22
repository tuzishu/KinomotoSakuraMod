package KinomotoSakuraMod.Powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class FlyPower extends CustomPower
{
    public static final String POWER_ID = "FlyPower";
    private static final String POWER_NAME;
    private static final String[] POWER_DESCRIPTIONS;
    private static final String POWER_IMG_PATH = "img/powers/default_power.png";
    private static final PowerType POWER_TYPE = PowerType.BUFF;
    private static final float DAMAGE_REDUCE_RATE = 0.5F;
    private int storedAmount;

    static
    {
        PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        POWER_NAME = powerStrings.NAME;
        POWER_DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }

    public FlyPower(AbstractCreature target, int amount)
    {
        super(POWER_ID, POWER_NAME, POWER_IMG_PATH, POWER_TYPE, target, amount);
        this.updateDescription();
        this.storedAmount = this.amount;
    }

    public void playApplyPowerSfx()
    {
        CardCrawlGame.sound.play("POWER_FLIGHT", 0.05F);
    }

    public void updateDescription()
    {
        this.description = POWER_DESCRIPTIONS[0] + this.amount + POWER_DESCRIPTIONS[1];
    }

    public void atStartOfTurn()
    {
        this.amount = this.storedAmount;
        this.updateDescription();
    }

    public float atDamageFinalReceive(float damage, DamageInfo.DamageType type)
    {
        return this.calculateDamageTakenAmount(damage, type);
    }

    private float calculateDamageTakenAmount(float damage, DamageInfo.DamageType type)
    {
        return type != DamageInfo.DamageType.HP_LOSS && type != DamageInfo.DamageType.THORNS ? damage * DAMAGE_REDUCE_RATE : damage;
    }

    public int onAttacked(DamageInfo info, int damageAmount)
    {
        Boolean willLive = this.calculateDamageTakenAmount((float) damageAmount, info.type) < (float) this.owner.currentHealth;
        if (info.owner != null && info.type != DamageInfo.DamageType.HP_LOSS && info.type != DamageInfo.DamageType.THORNS && damageAmount > 0 && willLive)
        {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, FlyPower.POWER_ID, 1));
            this.updateDescription();
        }
        return damageAmount;
    }
}
