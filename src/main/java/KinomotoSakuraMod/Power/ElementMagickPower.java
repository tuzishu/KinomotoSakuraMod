package KinomotoSakuraMod.Power;

import KinomotoSakuraMod.Cards.AbstractModCard;
import KinomotoSakuraMod.Cards.CardMagicalType;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class ElementMagickPower extends AbstractPower
{
    private static final String ID = "ElementMagickPower";
    private static final String NAME;
    private static final String[] DESCRIPTIONS;
    private static final String IMG = "img/powers/ElementMagickPower.png";
    private static final float CORRECTION_RATE = 0.05f;

    private boolean _isElementCardUsed = false;

    static
    {
        PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action)
    {
        _isElementCardUsed = card instanceof AbstractModCard && ((AbstractModCard)card).magicalType == CardMagicalType.PhysicsCard;
    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action)
    {
        _isElementCardUsed = false;
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type)
    {
        if (_isElementCardUsed)
        {
            damage *= CORRECTION_RATE * this.amount + 1f;
        }
        return super.atDamageGive(damage, type);
    }
}
