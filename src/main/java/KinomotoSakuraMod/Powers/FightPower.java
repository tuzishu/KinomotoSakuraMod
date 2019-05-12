package KinomotoSakuraMod.Powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class FightPower extends CustomPower
{
    public static final String POWER_ID = "FightPower";
    private static final String POWER_NAME;
    private static final String[] POWER_DESCRIPTIONS;
    private static final String POWER_IMG_PATH = "img/powers/default_power.png";
    private static final PowerType POWER_TYPE = PowerType.BUFF;
    private static final int DAMAGE_TRIGGER = 12;
    private static final int UPGRADE_DAMAGE_TRIGGER = 8;
    public int damageTrigger = DAMAGE_TRIGGER;

    static
    {
        PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        POWER_NAME = powerStrings.NAME;
        POWER_DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }

    public FightPower(int amount, boolean upgraded)
    {
        this(AbstractDungeon.player, amount, upgraded);
    }

    public FightPower(AbstractCreature target, int amount, boolean upgraded)
    {
        super(POWER_ID, POWER_NAME, POWER_IMG_PATH, POWER_TYPE, target, amount);
        this.damageTrigger = upgraded ? UPGRADE_DAMAGE_TRIGGER : this.damageTrigger;
        this.updateDescription();
    }

    public void updateDescription()
    {
        this.description = POWER_DESCRIPTIONS[0] + damageTrigger + POWER_DESCRIPTIONS[1] + this.amount + POWER_DESCRIPTIONS[2];
    }

    @Override
    public void onAttack(DamageInfo info, int damage, AbstractCreature target)
    {
        if (info.type == DamageInfo.DamageType.NORMAL && info.output - target.currentBlock >= damageTrigger)
        {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(info.owner, info.owner, new StrengthPower(info.owner, this.amount), this.amount));
        }
    }
}
