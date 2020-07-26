package KinomotoSakuraMod.Powers;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class KSMOD_HopePower_SakuraCard extends KSMOD_CustomPower
{
    public static final String POWER_ID = "KSMOD_HopePower_SakuraCard";
    private static final String POWER_NAME;
    private static final String[] POWER_DESCRIPTIONS;
    private static final String POWER_IMG_PATH = "img/powers/glow_power_sakuracard.png";
    private static final AbstractPower.PowerType POWER_TYPE = AbstractPower.PowerType.BUFF;

    static
    {
        PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        POWER_NAME = powerStrings.NAME;
        POWER_DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }

    public KSMOD_HopePower_SakuraCard(AbstractCreature target)
    {
        super(POWER_ID, POWER_NAME, POWER_IMG_PATH, POWER_TYPE, target, 1, true);
        this.updateDescription();
    }

    public void updateDescription()
    {
        this.description = POWER_DESCRIPTIONS[0];
    }

    public float atDamageGive(float damage, DamageInfo.DamageType type)
    {
        return type == DamageInfo.DamageType.NORMAL ? damage * 2F : damage;
    }

    public float modifyBlock(float blockAmount)
    {
        return blockAmount * 2F;
    }
}
