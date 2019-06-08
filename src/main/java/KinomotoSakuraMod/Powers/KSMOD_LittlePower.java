package KinomotoSakuraMod.Powers;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class KSMOD_LittlePower extends KSMOD_CustomPower
{
    public static final String POWER_ID = "KSMOD_LittlePower";
    private static final String POWER_NAME;
    private static final String[] POWER_DESCRIPTIONS;
    private static final String POWER_IMG_PATH = "img/powers/default_power.png";
    private static final PowerType POWER_TYPE = PowerType.BUFF;
    private static final float DAMAGE_CORRECTION = 0.01F;
    private static final float DEFENCE_CORRECTION = 0.01F;

    static
    {
        PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        POWER_NAME = powerStrings.NAME;
        POWER_DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }

    public KSMOD_LittlePower(int amount)
    {
        this(AbstractDungeon.player, amount);
    }

    public KSMOD_LittlePower(AbstractCreature target, int amount)
    {
        super(POWER_ID, POWER_NAME, POWER_IMG_PATH, POWER_TYPE, target, amount);
        this.updateDescription();
    }

    public void updateDescription()
    {
        this.description = POWER_DESCRIPTIONS[0] + (int) (this.amount * DEFENCE_CORRECTION * 100) + POWER_DESCRIPTIONS[1] + (int) (this.amount * DAMAGE_CORRECTION * 100) + POWER_DESCRIPTIONS[2];
    }

    public float atDamageGive(float damage, DamageInfo.DamageType type)
    {
        damage = (int) (damage * (1 - DAMAGE_CORRECTION * this.amount));
        return damage;
    }

    public int onAttacked(DamageInfo info, int damage)
    {
        AbstractPlayer player = AbstractDungeon.player;
        if (info.owner instanceof AbstractMonster && info.type == DamageInfo.DamageType.NORMAL)
        {
            damage = (int) (damage * (1 - DEFENCE_CORRECTION * this.amount));
        }
        return damage;
    }
}
