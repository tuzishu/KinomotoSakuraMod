package KinomotoSakuraMod.Actions;

import KinomotoSakuraMod.Cards.ClowCard.ClowCardTheNothing;
import KinomotoSakuraMod.KSMOD;
import KinomotoSakuraMod.Utility.KSMOD_DataTool;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class KSMOD_MirrorAction_SakuraCard extends AbstractGameAction
{
    public static final String ACTION_ID = "KSMOD_MirrorAction_SakuraCard";
    private static final String[] TEXT;
    private static final float DURATION = Settings.ACTION_DUR_FAST;
    private static CardGroup clowCardGroup;
    private static ArrayList<String> BlackListID = new ArrayList<>();

    static
    {
        UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ACTION_ID);
        TEXT = uiStrings.TEXT;
        BlackListID.add(ClowCardTheNothing.ID);
    }

    public KSMOD_MirrorAction_SakuraCard()
    {
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
            AbstractDungeon.gridSelectScreen.open(GetClowCardGroup(), 1, false, TEXT[0]);
            this.tickDuration();
            return;
        }

        if (AbstractDungeon.gridSelectScreen.selectedCards.size() != 0)
        {
            for (AbstractCard card : AbstractDungeon.gridSelectScreen.selectedCards)
            {
                AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(card.makeStatEquivalentCopy()));
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            this.isDone = true;
        }

        this.tickDuration();
    }

    private CardGroup GetClowCardGroup()
    {
        if (clowCardGroup == null)
        {
            clowCardGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            clowCardGroup.group = KSMOD.GetClowCards().stream().filter(card -> !KSMOD_DataTool.IsStringListContains(BlackListID, card.cardID)).collect(Collectors.toCollection(ArrayList::new));
        }
        return clowCardGroup;
    }
}
