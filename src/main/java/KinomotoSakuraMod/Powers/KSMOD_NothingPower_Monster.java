package KinomotoSakuraMod.Powers;

import KinomotoSakuraMod.Cards.KSMOD_AbstractMagicCard;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class KSMOD_NothingPower_Monster extends KSMOD_CustomPower
{
    public static final String POWER_ID = "KSMOD_NothingPower_Monster";
    private static final String POWER_NAME;
    private static final String[] POWER_DESCRIPTIONS;
    private static final String POWER_IMG_PATH = "blur";
    private static final PowerType POWER_TYPE = PowerType.BUFF;
    private static final float EXHAUST_RATE = 0.1F;

    static
    {
        PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        POWER_NAME = powerStrings.NAME;
        POWER_DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }

    public KSMOD_NothingPower_Monster(AbstractCreature target)
    {
        super(POWER_ID, POWER_NAME, POWER_IMG_PATH, POWER_TYPE, target, 1, false);
        this.updateDescription();
    }

    public void updateDescription()
    {
        this.description = POWER_DESCRIPTIONS[0] + (int) (EXHAUST_RATE * 100) + POWER_DESCRIPTIONS[1];
    }

    public void onUseCard(AbstractCard card, UseCardAction action)
    {
        if (card instanceof KSMOD_AbstractMagicCard && MathUtils.random(0F, 1F) < EXHAUST_RATE)
        {
            this.flash();
            action.exhaustCard = true;
        }
    }
}