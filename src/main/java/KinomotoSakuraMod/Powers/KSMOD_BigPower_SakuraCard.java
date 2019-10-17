package KinomotoSakuraMod.Powers;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class KSMOD_BigPower_SakuraCard extends KSMOD_CustomPower
{
    public static final String POWER_ID = "KSMOD_BigPower_SakuraCard";
    private static final String POWER_NAME;
    private static final String[] POWER_DESCRIPTIONS;
    private static final String POWER_IMG_PATH = "img/powers/big_power_sakuracard.png";
    private static final PowerType POWER_TYPE = PowerType.BUFF;
    private static final float INCREASE_RATE = 0.33F;

    static
    {
        PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        POWER_NAME = powerStrings.NAME;
        POWER_DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }

    public KSMOD_BigPower_SakuraCard(AbstractCreature target, int amount)
    {
        super(POWER_ID, POWER_NAME, POWER_IMG_PATH, POWER_TYPE, target, amount, true);
        this.updateDescription();
    }

    public void updateDescription()
    {
        this.description = POWER_DESCRIPTIONS[0] + (int) (INCREASE_RATE * 100) + POWER_DESCRIPTIONS[1];
    }

    public float atDamageGive(float damage, DamageInfo.DamageType type)
    {
        return damage * (1F + INCREASE_RATE);
    }
}
