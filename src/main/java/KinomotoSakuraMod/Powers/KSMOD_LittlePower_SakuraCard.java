package KinomotoSakuraMod.Powers;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class KSMOD_LittlePower_SakuraCard extends KSMOD_CustomPower
{
    public static final String POWER_ID = "KSMOD_LittlePower_SakuraCard";
    private static final String POWER_NAME;
    private static final String[] POWER_DESCRIPTIONS;
    private static final String POWER_IMG_PATH = "img/powers/default_power.png";
    private static final PowerType POWER_TYPE = PowerType.BUFF;
    private static final float REDUCE_RATE = 0.33F;

    static
    {
        PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        POWER_NAME = powerStrings.NAME;
        POWER_DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }

    public KSMOD_LittlePower_SakuraCard(AbstractCreature target, int amount)
    {
        super(POWER_ID, POWER_NAME, POWER_IMG_PATH, POWER_TYPE, target, amount);
        this.updateDescription();
    }

    public void updateDescription()
    {
        this.description = POWER_DESCRIPTIONS[0] + (int) (REDUCE_RATE * 100) + POWER_DESCRIPTIONS[1];
    }

    public float atDamageReceive(float damage, DamageInfo.DamageType damageType)
    {
        return damage * (1F - REDUCE_RATE);
    }
}
