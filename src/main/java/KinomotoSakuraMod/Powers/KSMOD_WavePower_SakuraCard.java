package KinomotoSakuraMod.Powers;

import KinomotoSakuraMod.Patches.KSMOD_CustomCardColor;
import KinomotoSakuraMod.Patches.KSMOD_CustomTag;
import KinomotoSakuraMod.Utility.KSMOD_Utility;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

import java.util.ArrayList;

public class KSMOD_WavePower_SakuraCard extends KSMOD_CustomPower
{
    public static final String POWER_ID = "KSMOD_WavePower_SakuraCard";
    private static final String POWER_NAME;
    private static final String[] POWER_DESCRIPTIONS;
    private static final String POWER_IMG_PATH = "img/powers/default_power.png";
    private static final PowerType POWER_TYPE = PowerType.BUFF;
    private ArrayList<AbstractCard.CardTags> lastCardTagList = new ArrayList<>();
    private static final int DAMAGE = 5;
    private static final int BLOCK = 4;
    private static final int ENERGY = 1;
    private static final int DRAW = 1;

    static
    {
        PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        POWER_NAME = powerStrings.NAME;
        POWER_DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }

    public KSMOD_WavePower_SakuraCard(int amount)
    {
        this(AbstractDungeon.player, amount);
    }

    public KSMOD_WavePower_SakuraCard(AbstractCreature target, int amount)
    {
        super(POWER_ID, POWER_NAME, POWER_IMG_PATH, POWER_TYPE, target, amount);
        this.updateDescription();
    }

    public void updateDescription()
    {
        this.description = POWER_DESCRIPTIONS[0] + GetElememtDesc() + POWER_DESCRIPTIONS[1];
    }

    public void atEndOfTurn(boolean isPlayer)
    {
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));
    }

    public void onUseCard(AbstractCard card, UseCardAction action)
    {
        if (card.color != KSMOD_CustomCardColor.CLOWCARD_COLOR && card.color != KSMOD_CustomCardColor.SAKURACARD_COLOR)
        {
            lastCardTagList.clear();
            return;
        }
        if (lastCardTagList.contains(KSMOD_CustomTag.KSMOD_EARTHY_CARD) && !card.tags.contains(KSMOD_CustomTag.KSMOD_EARTHY_CARD))
        {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this.owner, this.owner, BLOCK));
        }
        if (lastCardTagList.contains(KSMOD_CustomTag.KSMOD_WATERY_CARD) && !card.tags.contains(KSMOD_CustomTag.KSMOD_WATERY_CARD))
        {
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(ENERGY));
        }
        if (lastCardTagList.contains(KSMOD_CustomTag.KSMOD_FIREY_CARD) && !card.tags.contains(KSMOD_CustomTag.KSMOD_FIREY_CARD))
        {
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(this.owner, KSMOD_Utility.GetDamageList(DAMAGE), DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.FIRE));
        }
        if (lastCardTagList.contains(KSMOD_CustomTag.KSMOD_WINDY_CARD) && !card.tags.contains(KSMOD_CustomTag.KSMOD_WINDY_CARD))
        {
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(this.owner, DRAW));
        }
        lastCardTagList.clear();

        if (card.tags.contains(KSMOD_CustomTag.KSMOD_EARTHY_CARD))
        {
            lastCardTagList.add(KSMOD_CustomTag.KSMOD_EARTHY_CARD);
        }
        if (card.tags.contains(KSMOD_CustomTag.KSMOD_WATERY_CARD))
        {
            lastCardTagList.add(KSMOD_CustomTag.KSMOD_WATERY_CARD);
        }
        if (card.tags.contains(KSMOD_CustomTag.KSMOD_FIREY_CARD))
        {
            lastCardTagList.add(KSMOD_CustomTag.KSMOD_FIREY_CARD);
        }
        if (card.tags.contains(KSMOD_CustomTag.KSMOD_WINDY_CARD))
        {
            lastCardTagList.add(KSMOD_CustomTag.KSMOD_WINDY_CARD);
        }
        updateDescription();
    }

    private String GetElememtDesc()
    {
        String desc = "";
        if (lastCardTagList.contains(KSMOD_CustomTag.KSMOD_EARTHY_CARD))
        {
            desc = desc + POWER_DESCRIPTIONS[2];
        }
        if (lastCardTagList.contains(KSMOD_CustomTag.KSMOD_WATERY_CARD))
        {
            desc = desc + POWER_DESCRIPTIONS[3];
        }
        if (lastCardTagList.contains(KSMOD_CustomTag.KSMOD_FIREY_CARD))
        {
            desc = desc + POWER_DESCRIPTIONS[4];
        }
        if (lastCardTagList.contains(KSMOD_CustomTag.KSMOD_WINDY_CARD))
        {
            desc = desc + POWER_DESCRIPTIONS[5];
        }
        if (desc.equals(""))
        {
            desc = POWER_DESCRIPTIONS[6];
        }
        return desc;
    }
}
