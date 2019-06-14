package KinomotoSakuraMod.Powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class KSMOD_LightPower extends KSMOD_CustomPower
{
    public static final String POWER_ID = "KSMOD_LightPower";
    private static final String POWER_NAME;
    private static final String[] POWER_DESCRIPTIONS;
    private static final String POWER_IMG_PATH = "img/powers/default_power.png";
    private static final AbstractPower.PowerType POWER_TYPE = AbstractPower.PowerType.BUFF;
    public boolean upgraded;
    private int handSize;

    static
    {
        PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        POWER_NAME = powerStrings.NAME;
        POWER_DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }

    public KSMOD_LightPower(boolean upgraded)
    {
        this(AbstractDungeon.player, upgraded);
    }

    public KSMOD_LightPower(AbstractCreature target, boolean upgraded)
    {
        super(POWER_ID, POWER_NAME, POWER_IMG_PATH, POWER_TYPE, target, 1);
        this.updateDescription();
        this.upgraded = upgraded;
    }

    public void updateDescription()
    {
        this.description = POWER_DESCRIPTIONS[0] + (this.upgraded ? POWER_DESCRIPTIONS[1] : "");
    }

    public void onInitialApplication()
    {
        handSize = AbstractDungeon.player.gameHandSize;
        AbstractDungeon.player.gameHandSize += 2;
    }

    public void onRemove()
    {
        AbstractDungeon.player.gameHandSize = handSize;
    }

    public void upgrade(boolean upgraded)
    {
        if (upgraded)
        {
            this.upgraded = upgraded;
        }
    }
}
