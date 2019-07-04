package KinomotoSakuraMod.Powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.random.Random;

import java.util.ArrayList;

public class KSMOD_JumpPower_SakuraCard extends KSMOD_CustomPower
{
    public static final String POWER_ID = "KSMOD_JumpPower_SakuraCard";
    private static final String POWER_NAME;
    private static final String[] POWER_DESCRIPTIONS;
    private static final String POWER_IMG_PATH = "img/powers/default_power.png";
    private static final PowerType POWER_TYPE = PowerType.BUFF;

    static
    {
        PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        POWER_NAME = powerStrings.NAME;
        POWER_DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }

    public KSMOD_JumpPower_SakuraCard(AbstractCreature target, int amount)
    {
        super(POWER_ID, POWER_NAME, POWER_IMG_PATH, POWER_TYPE, target, amount);
        this.updateDescription();
    }

    public void updateDescription()
    {
        this.description = POWER_DESCRIPTIONS[0] + this.amount + POWER_DESCRIPTIONS[1];
    }

    public void atStartOfTurn()
    {
        ArrayList<AbstractPower> debuffList = GetDebuffList();
        if (debuffList.size() > 0)
        {
            int index = new Random().random(debuffList.size() - 1);
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, debuffList.get(index)));
        }
    }

    private ArrayList<AbstractPower> GetDebuffList()
    {
        ArrayList<AbstractPower> debuffList = new ArrayList<>();
        for (int i = 0; i < AbstractDungeon.player.powers.size(); i++)
        {
            AbstractPower power = AbstractDungeon.player.powers.get(i);
            if (power.type == AbstractPower.PowerType.DEBUFF)
            {
                debuffList.add(power);
            }
        }
        return debuffList;
    }
}
