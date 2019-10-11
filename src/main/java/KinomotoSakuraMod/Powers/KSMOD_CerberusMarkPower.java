package KinomotoSakuraMod.Powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class KSMOD_CerberusMarkPower extends KSMOD_CustomPower
{
    public static final String POWER_ID = "KSMOD_CerberusMarkPower";
    public static final String POWER_NAME;
    public static final String[] POWER_DESCRIPTIONS;
    private static final String POWER_IMG_PATH = "lockon";
    private static final PowerType POWER_TYPE = PowerType.DEBUFF;
    private float promotion;

    static
    {
        PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        POWER_NAME = powerStrings.NAME;
        POWER_DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }

    public KSMOD_CerberusMarkPower(AbstractMonster target, float promotion)
    {
        super(POWER_ID, POWER_NAME, POWER_IMG_PATH, POWER_TYPE, target, 1, false);
        this.isTurnBased = true;
        this.promotion = promotion;
        this.updateDescription();
    }

    public void updateDescription()
    {
        this.description = POWER_DESCRIPTIONS[0] + (int) (promotion * 100) + POWER_DESCRIPTIONS[1];
    }

    public void atStartOfTurn()
    {
        if (this.amount > 1)
        {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this, 1));
        }
        else
        {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
    }

    public float atDamageReceive(float damage, DamageInfo.DamageType damageType)
    {
        return damageType == DamageInfo.DamageType.NORMAL ? damage * (1F + promotion) : damage;
    }
}