package KinomotoSakuraMod.Powers;

import KinomotoSakuraMod.Cards.ClowCard.ClowCardTheRain;
import KinomotoSakuraMod.Cards.ClowCard.ClowCardTheSnow;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CloudPower extends CustomPower
{
    public static final String POWER_ID = "CloudPower";
    private static final String POWER_NAME;
    private static final String[] POWER_DESCRIPTIONS;
    private static final String POWER_IMG = "img/powers/default_power.png";
    private static final PowerType POWER_TYPE = PowerType.BUFF;

    static
    {
        PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        POWER_NAME = powerStrings.NAME;
        POWER_DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }

    public CloudPower(AbstractCreature target, int amount)
    {
        super(POWER_ID, POWER_NAME, POWER_IMG, POWER_TYPE, target, amount);
        updateDescription();
    }

    public void updateDescription()
    {
        this.description = POWER_DESCRIPTIONS[0] + this.amount + POWER_DESCRIPTIONS[1];
    }

    public void onUseCard(AbstractCard card, UseCardAction action)
    {
        if (this.amount <= 0)
        {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));
            return;
        }
        if (!Check(card))
        {
            return;
        }

        this.flash();
        AbstractMonster monster = null;
        if (action.target != null)
        {
            monster = (AbstractMonster) action.target;
        }
        AbstractCard tempCard = card.makeSameInstanceOf();
        AbstractDungeon.player.limbo.addToBottom(tempCard);
        tempCard.current_x = card.current_x;
        tempCard.current_y = card.current_y;
        tempCard.target_x = (float) Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
        tempCard.target_y = (float) Settings.HEIGHT / 2.0F;
        tempCard.freeToPlayOnce = true;
        if (monster != null)
        {
            tempCard.calculateCardDamage(monster);
        }
        tempCard.purgeOnUse = true;
        AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(tempCard, monster, card.energyOnUse));
        if (this.amount > 1)
        {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this, 1));
        }
        else
        {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
    }

    private boolean Check(AbstractCard card)
    {
        if (card instanceof ClowCardTheRain)
        {
            return true;
        }
        if (card instanceof ClowCardTheSnow)
        {
            return true;
        }
        // if (card instanceof ClowCardTheStorm)
        // {
        //     return true;
        // }
        // if (card instanceof ClowCardTheThunder)
        // {
        //     return true;
        // }
        // if (card instanceof SakuraCardTheRain)
        // {
        //     return true;
        // }
        // if (card instanceof SakuraCardTheSnow)
        // {
        //     return true;
        // }
        // if (card instanceof SakuraCardTheStorm)
        // {
        //     return true;
        // }
        // if (card instanceof SakuraCardTheThunder)
        // {
        //     return true;
        // }
        // if (card instanceof SakuraCardTheStorm)
        // {
        //     return true;
        // }
        return false;
    }
}
