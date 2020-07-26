package KinomotoSakuraMod.Powers;

import KinomotoSakuraMod.Cards.ClowCard.ClowCardTheNothing;
import KinomotoSakuraMod.Cards.KSMOD_AbstractMagicCard;
import KinomotoSakuraMod.Patches.KSMOD_CustomCardColor;
import KinomotoSakuraMod.Utility.KSMOD_DataTool;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;

import java.util.ArrayList;

public class KSMOD_NothingPower extends KSMOD_CustomPower
{
    public static final String POWER_ID = "KSMOD_NothingPower";
    private static final String POWER_NAME;
    private static final String[] POWER_DESCRIPTIONS;
    private static final String POWER_IMG_PATH = "retain";
    private static final PowerType POWER_TYPE = PowerType.BUFF;
    private int damage;
    private int block;
    private boolean upgraded = false;

    static
    {
        PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        POWER_NAME = powerStrings.NAME;
        POWER_DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }

    public KSMOD_NothingPower(AbstractCreature target, int damage, int block, boolean upgraded)
    {
        super(POWER_ID, POWER_NAME, POWER_IMG_PATH, POWER_TYPE, target, 1, false);
        SetValue(damage, block, upgraded);
    }

    public void SetValue(int damage, int block, boolean upgraded)
    {
        if (!this.upgraded)
        {
            this.damage = damage;
            this.block = block;
            this.upgraded = upgraded;
        }
        this.updateDescription();
    }

    public void updateDescription()
    {
        this.description = POWER_DESCRIPTIONS[0] + damage + POWER_DESCRIPTIONS[1] + block + (upgraded ? POWER_DESCRIPTIONS[3] : POWER_DESCRIPTIONS[2]);
    }

    public void onInitialApplication()
    {
        SetCost(AbstractDungeon.player.hand);
        SetCost(AbstractDungeon.player.drawPile);
        SetCost(AbstractDungeon.player.discardPile);
        SetCost(AbstractDungeon.player.exhaustPile);
    }

    private void SetCost(CardGroup group)
    {
        for (AbstractCard c : group.group)
        {
            if (c instanceof KSMOD_AbstractMagicCard && c.cost > 0)
            {
                c.setCostForTurn(-9);
            }
        }
    }

    public void onCardDraw(AbstractCard card)
    {
        if (card instanceof KSMOD_AbstractMagicCard && card.cost > 0)
        {
            card.setCostForTurn(-9);
        }
    }

    public void onUseCard(AbstractCard card, UseCardAction action)
    {
        if (card.color == KSMOD_CustomCardColor.CLOWCARD_COLOR || card.color == KSMOD_CustomCardColor.SAKURACARD_COLOR)
        {
            this.flash();
            action.exhaustCard = true;
        }
    }

    public void onAfterUseCard(AbstractCard card, UseCardAction action)
    {
        if (card.color == KSMOD_CustomCardColor.CLOWCARD_COLOR || card.color == KSMOD_CustomCardColor.SAKURACARD_COLOR)
        {
            AbstractDungeon.actionManager.addToTop(new GainBlockAction(this.owner, block, true));
            AbstractDungeon.actionManager.addToTop(new DamageAllEnemiesAction(this.owner, KSMOD_DataTool.GetDamageList(damage), DamageInfo.DamageType.HP_LOSS, AbstractGameAction.AttackEffect.BLUNT_LIGHT, true));
        }
    }

    public void onVictory()
    {
        if (!upgraded)
        {
            ArrayList<AbstractCard> cards = new ArrayList<>();
            for (AbstractCard c : AbstractDungeon.player.masterDeck.group)
            {
                if (!c.cardID.equals(ClowCardTheNothing.ID))
                {
                    cards.add(c);
                }
            }
            AbstractCard card2Purge = KSMOD_DataTool.GetRandomListElement(cards);
            if (card2Purge != null)
            {
                AbstractDungeon.topLevelEffectsQueue.add(new PurgeCardEffect(card2Purge, Settings.WIDTH * 0.5F, Settings.HEIGHT * 0.5F));
                AbstractDungeon.player.masterDeck.removeCard(card2Purge);
                AbstractDungeon.player.masterDeck.refreshHandLayout();
            }
        }
    }
}