package KinomotoSakuraMod.Powers;

import KinomotoSakuraMod.Effects.KSMOD_FireyVFXEffect;
import KinomotoSakuraMod.Patches.KSMOD_CustomTag;
import KinomotoSakuraMod.Relics.KSMOD_SealedBook;
import KinomotoSakuraMod.Utility.KSMOD_Utility;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class KSMOD_FireyPower extends KSMOD_CustomPower
{
    public static final String POWER_ID = "KSMOD_FireyPower";
    private static final String POWER_NAME;
    private static final String[] POWER_DESCRIPTIONS;
    private static final String POWER_IMG_PATH = "img/powers/firey_power.png";
    private static final AbstractPower.PowerType POWER_TYPE = AbstractPower.PowerType.BUFF;
    private static final float GAIN_POWER_VFX_DURATION = 0.5F;
    private static final float ATTACK_VFX_DURATION = 0.2F;

    static
    {
        PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        POWER_NAME = powerStrings.NAME;
        POWER_DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }

    public KSMOD_FireyPower(AbstractCreature target, int amount)
    {
        super(POWER_ID, POWER_NAME, POWER_IMG_PATH, POWER_TYPE, target, amount, true);
        this.updateDescription();
    }

    public void updateDescription()
    {
        this.description = POWER_DESCRIPTIONS[0] + KSMOD_SealedBook.FIREY_DAMAGE_NUMBER + POWER_DESCRIPTIONS[1];
    }

    public void onInitialApplication()
    {
        AbstractDungeon.actionManager.addToBottom(new VFXAction(this.owner, new KSMOD_FireyVFXEffect(GAIN_POWER_VFX_DURATION), GAIN_POWER_VFX_DURATION));
    }

    public void onUseCard(AbstractCard card, UseCardAction action)
    {
        if (card.hasTag(KSMOD_CustomTag.KSMOD_FIREY_CARD))
        {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(this.owner, new KSMOD_FireyVFXEffect(ATTACK_VFX_DURATION), ATTACK_VFX_DURATION));
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(this.owner, KSMOD_Utility.GetDamageList(KSMOD_SealedBook.FIREY_DAMAGE_NUMBER), DamageInfo.DamageType.HP_LOSS, AbstractGameAction.AttackEffect.FIRE));
        }
    }

    public void atEndOfTurn(boolean isPlayer)
    {
        if (this.owner.hasPower(KSMOD_FireyPower_SakuraCard.POWER_ID))
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
