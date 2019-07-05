package KinomotoSakuraMod.Powers;

import KinomotoSakuraMod.Patches.KSMOD_CustomCardColor;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class KSMOD_TwinPower_SakuraCard extends KSMOD_CustomPower
{
    public static final String POWER_ID = "KSMOD_TwinPower_SakuraCard";
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

    public KSMOD_TwinPower_SakuraCard(AbstractCreature target, int amount)
    {
        super(POWER_ID, POWER_NAME, POWER_IMG, POWER_TYPE, target, amount);
        updateDescription();
    }

    public void updateDescription()
    {
        this.description = POWER_DESCRIPTIONS[0];
    }

    public void onInitialApplication()
    {
        ModifyAllClowCardsCostThisCombat(AbstractDungeon.player.hand, this.amount);
        ModifyAllClowCardsCostThisCombat(AbstractDungeon.player.drawPile, this.amount);
        ModifyAllClowCardsCostThisCombat(AbstractDungeon.player.discardPile, this.amount);
        ModifyAllClowCardsCostThisCombat(AbstractDungeon.player.exhaustPile, this.amount);
        AbstractDungeon.player.hand.refreshHandLayout();
    }

    public void onMakeTempCard(AbstractCard card)
    {
        if (card.color == KSMOD_CustomCardColor.CLOWCARD_COLOR && card.cost >= 0)
        {
            card.cost += this.amount;
            card.costForTurn = card.cost;
            card.isCostModified = true;
        }
    }

    public void onUseCard(AbstractCard card, UseCardAction action)
    {
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead())
        {
            return;
        }
        if (this.amount <= 0)
        {
            return;
        }
        if (card.purgeOnUse)
        {
            return;
        }
        if (card.color != KSMOD_CustomCardColor.CLOWCARD_COLOR)
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
        this.flash();
    }

    private void ModifyAllClowCardsCostThisCombat(CardGroup group, int costAddition)
    {
        for (AbstractCard card : group.group)
        {
            if (card.color == KSMOD_CustomCardColor.CLOWCARD_COLOR && card.cost >= 0)
            {
                card.cost += this.amount;
                card.costForTurn = card.cost;
                card.isCostModified = true;
            }
        }
    }
}