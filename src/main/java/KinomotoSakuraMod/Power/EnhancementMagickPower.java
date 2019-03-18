package KinomotoSakuraMod.Power;

import KinomotoSakuraMod.Cards.AbstractModCard;
import KinomotoSakuraMod.Cards.CardMagicalType;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class EnhancementMagickPower extends AbstractPower
{
    private static final String ID = "EnhancementMagickPower";
    private static final String NAME;
    private static final String[] DESCRIPTIONS;
    private static final String IMG = "img/powers/Sp.png";

    static
    {
        PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }

    public EnhancementMagickPower()
    {
        super();
    }

    public void updateDescription()
    {
        this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]);
    }

    public float atDamageGive(float damage, DamageInfo.DamageType type)
    {
        damage *= 0.05 * this.amount + 1;
        return super.atDamageGive(damage, type);
    }
}
