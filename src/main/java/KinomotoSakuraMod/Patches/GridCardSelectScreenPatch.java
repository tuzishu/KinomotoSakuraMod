package KinomotoSakuraMod.Patches;

import KinomotoSakuraMod.Cards.AbstractMagicCard;
import KinomotoSakuraMod.Utility.Utility;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class GridCardSelectScreenPatch
{
    private static final int CARDS_PER_LINE = 5;
    private static float DRAW_START_X = ((float) Settings.WIDTH - 5.0F * AbstractCard.IMG_WIDTH * 0.75F - 4.0F * Settings.CARD_VIEW_PAD_X) / 2.0F + AbstractCard.IMG_WIDTH * 0.75F / 2.0F;
    private static float DRAW_START_Y;
    private static float PAD_X = AbstractCard.IMG_WIDTH * 0.75F + Settings.CARD_VIEW_PAD_X;
    private static float PAD_Y = AbstractCard.IMG_HEIGHT * 0.75F + Settings.CARD_VIEW_PAD_Y;
    private static float PAD_LONG_Y = AbstractMagicCard.IMG_HEIGHT * 0.79F + Settings.CARD_VIEW_PAD_Y;

    public static boolean IsLongCard(AbstractCard card)
    {
        return card.color == CustomCardColor.CLOWCARD_COLOR || card.color == CustomCardColor.SAKURACARD_COLOR || card.color == CustomCardColor.SPELL_COLOR;
    }

    public static boolean HasLongCard(ArrayList<AbstractCard> cards)
    {
        if (cards.size() > 0)
        {
            for (AbstractCard card : cards)
            {
                if (IsLongCard(card))
                {
                    return true;
                }
            }
        }
        return false;
    }

    public static float GetPadHeight(int index, boolean includeLastLine)
    {
        float pad = 0F;
        ArrayList<AbstractCard> cards = AbstractDungeon.player.masterDeck.group;
        int lineNum = index / CARDS_PER_LINE;
        for (int i = 0; i < lineNum; i++)
        {
            boolean hasLongCard = false;
            for (int j = 0; j < CARDS_PER_LINE; j++)
            {
                if (IsLongCard(cards.get(i * CARDS_PER_LINE + j)))
                {
                    hasLongCard = true;
                    break;
                }
            }
            pad += hasLongCard ? PAD_LONG_Y : PAD_Y;
        }
        if (!includeLastLine)
        {
            return pad;
        }
        boolean hasLongCard = false;
        for (int i = lineNum * CARDS_PER_LINE - 1; i < index; i++)
        {
            if (IsLongCard(cards.get(i)))
            {
                hasLongCard = true;
                break;
            }
        }
        pad += hasLongCard ? PAD_LONG_Y : PAD_Y;
        pad -= PAD_Y * 2;
        return pad;
    }

    @SpirePatch(clz = GridCardSelectScreen.class, method = "updateCardPositionsAndHoverLogic", paramtypez = {})
    public static class updateCardPositionsAndHoverLogic
    {
        public static SpireReturn<Object> Prefix(GridCardSelectScreen grid) throws NoSuchFieldException, IllegalAccessException
        {
            DRAW_START_Y = Utility.GetFieldByReflect(grid, GridCardSelectScreen.class, "drawStartY").getFloat(grid);
            CardGroup cardGroup = (CardGroup) Utility.GetFieldByReflect(grid, GridCardSelectScreen.class, "targetGroup").get(grid);
            ArrayList<AbstractCard> cards = cardGroup.group;
            float currentDiffY = Utility.GetFieldByReflect(grid, GridCardSelectScreen.class, "currentDiffY").getFloat(grid);
            Field hoveredCard = Utility.GetFieldByReflect(grid, GridCardSelectScreen.class, "hoveredCard");
            if (HasLongCard(cards))
            {
                boolean isJustForConfirming = Utility.GetFieldByReflect(grid, GridCardSelectScreen.class, "isJustForConfirming").getBoolean(grid);
                if (isJustForConfirming && cardGroup.size() <= 4)
                {
                    switch (cardGroup.size())
                    {
                        case 1:
                            cardGroup.getBottomCard().current_x = (float) Settings.WIDTH / 2.0F;
                            cardGroup.getBottomCard().target_x = (float) Settings.WIDTH / 2.0F;
                            break;
                        case 2:
                            cards.get(0).current_x = (float) Settings.WIDTH / 2.0F - PAD_X / 2.0F;
                            cards.get(0).target_x = (float) Settings.WIDTH / 2.0F - PAD_X / 2.0F;
                            cards.get(1).current_x = (float) Settings.WIDTH / 2.0F + PAD_X / 2.0F;
                            cards.get(1).target_x = (float) Settings.WIDTH / 2.0F + PAD_X / 2.0F;
                            break;
                        case 3:
                            cards.get(0).current_x = DRAW_START_X + PAD_X;
                            cards.get(1).current_x = DRAW_START_X + PAD_X * 2.0F;
                            cards.get(2).current_x = DRAW_START_X + PAD_X * 3.0F;
                            cards.get(0).target_x = DRAW_START_X + PAD_X;
                            cards.get(1).target_x = DRAW_START_X + PAD_X * 2.0F;
                            cards.get(2).target_x = DRAW_START_X + PAD_X * 3.0F;
                            break;
                        case 4:
                            cards.get(0).current_x = (float) Settings.WIDTH / 2.0F - PAD_X / 2.0F - PAD_X;
                            cards.get(0).target_x = (float) Settings.WIDTH / 2.0F - PAD_X / 2.0F - PAD_X;
                            cards.get(1).current_x = (float) Settings.WIDTH / 2.0F - PAD_X / 2.0F;
                            cards.get(1).target_x = (float) Settings.WIDTH / 2.0F - PAD_X / 2.0F;
                            cards.get(2).current_x = (float) Settings.WIDTH / 2.0F + PAD_X / 2.0F;
                            cards.get(2).target_x = (float) Settings.WIDTH / 2.0F + PAD_X / 2.0F;
                            cards.get(3).current_x = (float) Settings.WIDTH / 2.0F + PAD_X / 2.0F + PAD_X;
                            cards.get(3).target_x = (float) Settings.WIDTH / 2.0F + PAD_X / 2.0F + PAD_X;
                    }
                    for (int i = 0; i < cards.size(); ++i)
                    {
                        AbstractCard card = cards.get(i);
                        card.target_y = DRAW_START_Y + currentDiffY;
                        card.fadingOut = false;
                        card.update();
                        card.updateHoverLogic();
                        hoveredCard.set(grid, null);
                        for (AbstractCard c : cards)
                        {
                            if (c.hb.hovered)
                            {
                                hoveredCard.set(grid, c);
                            }
                        }
                    }
                }
                else
                {
                    for (int i = 0; i < cards.size(); ++i)
                    {
                        AbstractCard card = cards.get(i);
                        int mod = i % CARDS_PER_LINE;
                        card.target_x = DRAW_START_X + (float) mod * PAD_X;
                        card.target_y = DRAW_START_Y + currentDiffY - GetPadHeight(i, false);
                        card.fadingOut = false;
                        card.update();
                        card.updateHoverLogic();
                        hoveredCard.set(grid, null);
                        for (AbstractCard c : cards)
                        {
                            if (c.hb.hovered)
                            {
                                hoveredCard.set(grid, c);
                            }
                        }
                    }
                }
                return SpireReturn.Return(null);
            }
            else
            {
                return SpireReturn.Continue();
            }
        }
    }

    @SpirePatch(clz = GridCardSelectScreen.class, method = "calculateScrollBounds", paramtypez = {})
    public static class calculateScrollBounds
    {
        public static SpireReturn<Object> Prefix(GridCardSelectScreen grid) throws NoSuchFieldException, IllegalAccessException
        {
            CardGroup targetGroup = (CardGroup) Utility.GetFieldByReflect(grid, GridCardSelectScreen.class, "targetGroup").get(grid);
            ArrayList<AbstractCard> cards = targetGroup.group;
            if (HasLongCard(cards))
            {
                Field scrollUpperBound = Utility.GetFieldByReflect(grid, GridCardSelectScreen.class, "scrollUpperBound");
                float padY = 0;
                if (cards.size() > 10)
                {
                    padY = Settings.DEFAULT_SCROLL_LIMIT + GetPadHeight(cards.size() - 1, true);
                }
                else
                {
                    padY = Settings.DEFAULT_SCROLL_LIMIT;
                }
                scrollUpperBound.setFloat(grid, padY);
                Field prevDeckSize = Utility.GetFieldByReflect(grid, GridCardSelectScreen.class, "prevDeckSize");
                prevDeckSize.setInt(grid, targetGroup.size());
                return SpireReturn.Return(null);
            }
            else
            {
                return SpireReturn.Continue();
            }
        }
    }

    @SpirePatch(clz = GridCardSelectScreen.class, method = "hideCards", paramtypez = {})
    public static class hideCards
    {
        public static SpireReturn<Object> Prefix(GridCardSelectScreen grid) throws NoSuchFieldException, IllegalAccessException
        {
            CardGroup targetGroup = (CardGroup) Utility.GetFieldByReflect(grid, GridCardSelectScreen.class, "targetGroup").get(grid);
            ArrayList<AbstractCard> cards = targetGroup.group;
            if (HasLongCard(cards))
            {
                for (int i = 0; i < cards.size(); ++i)
                {
                    AbstractCard card = cards.get(i);
                    int mod = i % 5;
                    card.setAngle(0.0F, true);
                    card.lighten(true);
                    card.current_x = DRAW_START_X + (float) mod * PAD_X;
                    float currentDiffY = Utility.GetFieldByReflect(grid, GridCardSelectScreen.class, "currentDiffY").getFloat(grid);
                    card.current_y = DRAW_START_Y + currentDiffY - GetPadHeight(i, false) - MathUtils.random(100.0F * Settings.scale, 200.0F * Settings.scale);
                    card.targetDrawScale = 0.75F;
                    card.drawScale = 0.75F;
                }
                return SpireReturn.Return(null);
            }
            else
            {
                return SpireReturn.Continue();
            }
        }
    }

    @SpirePatch(clz = GridCardSelectScreen.class, method = "cancelUpgrade", paramtypez = {})
    public static class cancelUpgrade
    {
        public static SpireReturn<Object> Prefix(GridCardSelectScreen grid) throws NoSuchFieldException, IllegalAccessException
        {
            CardGroup targetGroup = (CardGroup) Utility.GetFieldByReflect(grid, GridCardSelectScreen.class, "targetGroup").get(grid);
            ArrayList<AbstractCard> cards = targetGroup.group;
            if (HasLongCard(cards))
            {
                Field cardSelectAmount = Utility.GetFieldByReflect(grid, GridCardSelectScreen.class, "cardSelectAmount");
                Field hoveredCard = Utility.GetFieldByReflect(grid, GridCardSelectScreen.class, "hoveredCard");
                cardSelectAmount.setInt(grid, 0);
                grid.confirmScreenUp = false;
                grid.confirmButton.hide();
                grid.confirmButton.isDisabled = true;
                hoveredCard.set(grid, null);
                grid.upgradePreviewCard = null;
                AbstractCard controllerCard = (AbstractCard) Utility.GetFieldByReflect(grid, GridCardSelectScreen.class, "hoveredCard").get(grid);
                if (Settings.isControllerMode && controllerCard != null)
                {
                    hoveredCard.set(grid, controllerCard);
                }
                boolean canCancel = Utility.GetFieldByReflect(grid, GridCardSelectScreen.class, "canCancel").getBoolean(grid);
                if ((grid.forUpgrade || grid.forTransform || grid.forPurge || AbstractDungeon.previousScreen == AbstractDungeon.CurrentScreen.SHOP) && canCancel)
                {
                    AbstractDungeon.overlayMenu.cancelButton.show(GridCardSelectScreen.TEXT[1]);
                }
                for (int i = 0; i < cards.size(); ++i)
                {
                    AbstractCard card = cards.get(i);
                    int mod = i % 5;
                    float currentDiffY = Utility.GetFieldByReflect(grid, GridCardSelectScreen.class, "currentDiffY").getFloat(grid);
                    card.current_x = DRAW_START_X + (float) mod * PAD_X;
                    card.current_y = DRAW_START_Y + currentDiffY - GetPadHeight(i, false);
                }
                Field tipMsg = Utility.GetFieldByReflect(grid, GridCardSelectScreen.class, "tipMsg");
                String lastTip = (String) Utility.GetFieldByReflect(grid, GridCardSelectScreen.class, "lastTip").get(grid);
                tipMsg.set(grid, lastTip);
                return SpireReturn.Return(null);
            }
            else
            {
                return SpireReturn.Continue();
            }
        }
    }
}
