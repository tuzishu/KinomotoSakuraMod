package KinomotoSakuraMod.Powers;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FloatPower extends KSMOD_CustomPower
{
    public static final String POWER_ID = "FloatPower";
    private static final String POWER_NAME;
    private static final String[] POWER_DESCRIPTIONS;
    private static final String POWER_IMG_PATH = "img/powers/default_power.png";
    private static final PowerType POWER_TYPE = PowerType.BUFF;
    private static final int GAIN_BLOCK = 3;
    private boolean upgraded = false;

    static
    {
        PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        POWER_NAME = powerStrings.NAME;
        POWER_DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }

    public FloatPower(int amount, boolean upgraded)
    {
        this(AbstractDungeon.player, amount, upgraded);
    }

    public FloatPower(AbstractCreature target, int amount, boolean upgraded)
    {
        super(POWER_ID, POWER_NAME, POWER_IMG_PATH, POWER_TYPE, target, amount);
        this.upgraded = upgraded ? upgraded : this.upgraded;
        this.updateDescription();
    }

    public void updateDescription()
    {
        this.description = POWER_DESCRIPTIONS[0] + this.amount + POWER_DESCRIPTIONS[1];
        if (upgraded)
        {
            this.description += POWER_DESCRIPTIONS[2];
        }
    }

    public int onAttacked(DamageInfo info, int damage)
    {
        boolean needFlash = false;
        AbstractPlayer player = AbstractDungeon.player;
        if (info.owner instanceof AbstractMonster && info.type == DamageInfo.DamageType.NORMAL)
        {
            if (damage >= (player.currentBlock + this.amount))
            {
                damage -= this.amount;
                needFlash = true;
            }
            else if (damage >= player.currentBlock)
            {
                damage = player.currentBlock;
                needFlash = true;
            }
            if (this.upgraded)
            {
                needFlash = true;
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(player, player, GAIN_BLOCK));
            }
        }
        if (needFlash)
        {
            this.flash();
        }
        return damage;
    }
}
