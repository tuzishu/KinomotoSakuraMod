package KinomotoSakuraMod.Actions;

import KinomotoSakuraMod.Cards.SpellCard.SpellCardFengHua;
import KinomotoSakuraMod.Cards.SpellCard.SpellCardHuoShen;
import KinomotoSakuraMod.Cards.SpellCard.SpellCardLeiDi;
import KinomotoSakuraMod.Cards.SpellCard.SpellCardShuiLong;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public class KSMOD_WriteEmptySpellAction extends AbstractGameAction
{
    public static final String ACTION_ID = "KSMOD_WriteEmptySpellAction";
    private static final String[] TEXT;
    private static final float DURATION = Settings.ACTION_DUR_FAST;
    private AbstractPlayer player;

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
        this.player = AbstractDungeon.player;
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
            CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            group.addToBottom(new SpellCardHuoShen());
            group.addToBottom(new SpellCardLeiDi());
            group.addToBottom(new SpellCardFengHua());
            group.addToBottom(new SpellCardShuiLong());
            AbstractDungeon.gridSelectScreen.open(group, 1, false, TEXT[0]);
            this.tickDuration();
            return;
        }
        if (AbstractDungeon.gridSelectScreen.selectedCards.size() > 0)
        {
            for (AbstractCard card:AbstractDungeon.gridSelectScreen.selectedCards)
            {
                AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(card.makeStatEquivalentCopy()));
            }
            this.isDone = true;
        }
        this.tickDuration();
    }
}
