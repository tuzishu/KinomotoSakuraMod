package KinomotoSakuraMod.Powers;

import KinomotoSakuraMod.Effects.Windy.KSMOD_WindyVFXEffect;
import KinomotoSakuraMod.Patches.KSMOD_CustomTag;
import KinomotoSakuraMod.Relics.KSMOD_SealedBook;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class KSMOD_WindyPower extends KSMOD_CustomPower
{
    public static final String POWER_ID = "KSMOD_WindyPower";
    private static final String POWER_NAME;
    private static final String[] POWER_DESCRIPTIONS;
    private static final String POWER_IMG_PATH = "img/powers/windy_power.png";
    private static final AbstractPower.PowerType POWER_TYPE = AbstractPower.PowerType.BUFF;
    private static final float GAIN_POWER_VFX_DURATION = 0.8F;
    private static final float ATTACK_VFX_DURATION = 0.4F;
    private int counter = 0;

    static
    {
        PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        POWER_NAME = powerStrings.NAME;
        POWER_DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }

    public KSMOD_WindyPower(AbstractCreature target, int amount)
    {
        super(POWER_ID, POWER_NAME, POWER_IMG_PATH, POWER_TYPE, target, amount, true);
        this.updateDescription();
    }

    public void updateDescription()
    {
        this.description = POWER_DESCRIPTIONS[0] + KSMOD_SealedBook.WINDY_DRAW_TRIGGER + POWER_DESCRIPTIONS[1] + ((this.counter == KSMOD_SealedBook.WATERY_ENERGY_TRIGGER - 1) ? POWER_DESCRIPTIONS[2] : "");
    }

    public void onInitialApplication()
    {
        AbstractDungeon.actionManager.addToBottom(new VFXAction(this.owner, new KSMOD_WindyVFXEffect(GAIN_POWER_VFX_DURATION), GAIN_POWER_VFX_DURATION));
    }

    public void onUseCard(AbstractCard card, UseCardAction action)
    {
        if (card.hasTag(KSMOD_CustomTag.KSMOD_WINDY_CARD))
        {
            counter += 1;
            AbstractDungeon.actionManager.addToBottom(new VFXAction(this.owner, new KSMOD_WindyVFXEffect(ATTACK_VFX_DURATION), ATTACK_VFX_DURATION));
            if (counter >= KSMOD_SealedBook.WINDY_DRAW_TRIGGER)
            {
                counter -= KSMOD_SealedBook.WINDY_DRAW_TRIGGER;
                AbstractDungeon.actionManager.addToBottom(new DrawCardAction(this.owner, 1));
            }
        }
        this.updateDescription();
    }

    public void atEndOfTurn(boolean isPlayer)
    {
        if (this.owner.hasPower(KSMOD_WindyPower_SakuraCard.POWER_ID))
        {
            return;
        }
        if (this.amount > 1)
        {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this, 1));
        }
        else
        {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
    }
}
