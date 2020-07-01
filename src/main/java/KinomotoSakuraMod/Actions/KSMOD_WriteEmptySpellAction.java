package KinomotoSakuraMod.Actions;

import KinomotoSakuraMod.Cards.SpellCard.SpellCardFengHua;
import KinomotoSakuraMod.Cards.SpellCard.SpellCardHuoShen;
import KinomotoSakuraMod.Cards.SpellCard.SpellCardLeiDi;
import KinomotoSakuraMod.Cards.SpellCard.SpellCardShuiLong;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.unique.DiscoveryAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.colorless.Discovery;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;
import java.util.Iterator;

public class KSMOD_WriteEmptySpellAction extends AbstractGameAction
{
    public static final String ACTION_ID = "KSMOD_WriteEmptySpellAction";
    private static final String[] TEXT;
    private static final float DURATION = Settings.ACTION_DUR_FAST;

    static
    {
        UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ACTION_ID);
        TEXT = uiStrings.TEXT;
    }

    public KSMOD_WriteEmptySpellAction()
    {
        this.setValues(AbstractDungeon.player, AbstractDungeon.player);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = DURATION;
    }

    public void update()
    {
        if (AbstractDungeon.getCurrRoom().isBattleEnding())
        {
            this.isDone = true;
            return;
        }
        if (this.duration == DURATION)
        {
            AbstractDungeon.cardRewardScreen.customCombatOpen(GetSpellList(), TEXT[0], false);
            this.tickDuration();
            return;
        }
        if (AbstractDungeon.cardRewardScreen.discoveryCard != null)
        {
            AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy()));
            this.isDone = true;
        }
        this.tickDuration();
    }

    private ArrayList<AbstractCard> GetSpellList()
    {
        ArrayList<AbstractCard> spellCards = new ArrayList<>();
        spellCards.add(new SpellCardHuoShen());
        spellCards.add(new SpellCardLeiDi());
        spellCards.add(new SpellCardFengHua());
        spellCards.add(new SpellCardShuiLong());
        return spellCards;
    }
}
