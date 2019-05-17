package KinomotoSakuraMod.Powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class CreatePower extends CustomPower
{

    public static final String POWER_ID = "CreatePower";
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

    public CreatePower()
    {
        this(AbstractDungeon.player, 1);
    }

    public CreatePower(AbstractCreature target, int amount)
    {
        super(POWER_ID, POWER_NAME, POWER_IMG_PATH, POWER_TYPE, target, amount);
        this.updateDescription();
    }

    public void updateDescription()
    {
        this.description = POWER_DESCRIPTIONS[0] + this.amount + POWER_DESCRIPTIONS[1];
    }

    public void onInitialApplication()
    {
        for (int i = 0; i < this.amount; i++)
        {
            AbstractDungeon.getCurrRoom().addRelicToRewards(GetRandomTier());
        }
    }

    private AbstractRelic.RelicTier GetRandomTier()
    {
        int randNum = new Random().random(0,2);
        switch (randNum)
        {
            case 2:
                return AbstractRelic.RelicTier.RARE;
            case 1:
                return AbstractRelic.RelicTier.UNCOMMON;
            default:
                return AbstractRelic.RelicTier.COMMON;
        }
    }
}
