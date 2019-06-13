package KinomotoSakuraMod.Patches;

import KinomotoSakuraMod.Cards.KSMOD_AbstractMagicCard;
import KinomotoSakuraMod.Utility.KSMOD_Utility;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.MasterDeckViewScreen;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class MasterDeckViewScreenPatch
{
    private static final int CARDS_PER_LINE = 5;
    private static float DRAW_START_X = ((float) Settings.WIDTH - 5.0F * AbstractCard.IMG_WIDTH * 0.75F - 4.0F * Settings.CARD_VIEW_PAD_X) / 2.0F + AbstractCard.IMG_WIDTH * 0.75F / 2.0F;
    private static float DRAW_START_Y = Settings.HEIGHT * 0.66F;
    private static float PAD_X = AbstractCard.IMG_WIDTH * 0.75F + Settings.CARD_VIEW_PAD_X;
    private static float PAD_Y = AbstractCard.IMG_HEIGHT * 0.75F + Settings.CARD_VIEW_PAD_Y;
    private static float PAD_LONG_Y = KSMOD_AbstractMagicCard.IMG_HEIGHT * 0.79F + Settings.CARD_VIEW_PAD_Y;

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

    @SpirePatch(clz = MasterDeckViewScreen.class, method = "updatePositions", paramtypez = {})
    public static class updatePositions
    {
        public static SpireReturn<Object> Prefix(MasterDeckViewScreen deck) throws NoSuchFieldException, IllegalAccessException
        {
            ArrayList<AbstractCard> cards = AbstractDungeon.player.masterDeck.group;
            if (HasLongCard(cards))
            {
                Field hoveredCard = KSMOD_Utility.GetFieldByReflect(MasterDeckViewScreen.class, "hoveredCard");
                hoveredCard.set(deck, null);
                for (int i = 0; i < cards.size(); ++i)
                {
                    AbstractCard card = cards.get(i);
                    int mod = i % CARDS_PER_LINE;
                    Float currentDiffY = KSMOD_Utility.GetFieldByReflect(MasterDeckViewScreen.class, "currentDiffY").getFloat(deck);
                    card.target_x = DRAW_START_X + (float) mod * PAD_X;
                    card.target_y = DRAW_START_Y + currentDiffY - GetPadHeight(i, false);
                    card.update();
                    card.updateHoverLogic();
                    if (card.hb.hovered)
                    {
                        hoveredCard.set(deck, cards.get(i));
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

    @SpirePatch(clz = MasterDeckViewScreen.class, method = "calculateScrollBounds", paramtypez = {})
    public static class calculateScrollBounds
    {
        public static SpireReturn<Object> Prefix(MasterDeckViewScreen deck) throws NoSuchFieldException, IllegalAccessException
        {
            ArrayList<AbstractCard> cards = AbstractDungeon.player.masterDeck.group;
            if (HasLongCard(cards))
            {
                Field scrollUpperBound = KSMOD_Utility.GetFieldByReflect(MasterDeckViewScreen.class, "scrollUpperBound");
                if (cards.size() > CARDS_PER_LINE && cards.size() <= CARDS_PER_LINE * 2 && HasLongCard(cards) || cards.size() > CARDS_PER_LINE * 2)
                {
                    scrollUpperBound.setFloat(deck, Settings.DEFAULT_SCROLL_LIMIT + GetPadHeight(cards.size() - 1, true));
                }
                else
                {
                    scrollUpperBound.setFloat(deck, Settings.DEFAULT_SCROLL_LIMIT);
                }
                Field prevDeckSize = KSMOD_Utility.GetFieldByReflect(MasterDeckViewScreen.class, "prevDeckSize");
                prevDeckSize.setInt(deck, AbstractDungeon.player.masterDeck.size());
                return SpireReturn.Return(null);
            }
            else
            {
                return SpireReturn.Continue();
            }
        }
    }

    @SpirePatch(clz = MasterDeckViewScreen.class, method = "hideCards", paramtypez = {})
    public static class hideCards
    {
        public static SpireReturn<Object> Prefix(MasterDeckViewScreen deck) throws NoSuchFieldException, IllegalAccessException
        {
            ArrayList<AbstractCard> cards = AbstractDungeon.player.masterDeck.group;
            if (HasLongCard(cards))
            {
                for (int i = 0; i < cards.size(); ++i)
                {
                    AbstractCard card = cards.get(i);
                    int mod = i % 5;
                    Float currentDiffY = KSMOD_Utility.GetFieldByReflect(MasterDeckViewScreen.class, "currentDiffY").getFloat(deck);
                    card.current_x = DRAW_START_X + mod * PAD_X;
                    card.current_y = DRAW_START_Y + currentDiffY - GetPadHeight(i, false) - MathUtils.random(100.0F * Settings.scale, 200.0F * Settings.scale);
                    card.targetDrawScale = 0.75F;
                    card.drawScale = 0.75F;
                    card.setAngle(0.0F, true);
                }
                return SpireReturn.Return(null);
            }
            else
            {
                return SpireReturn.Continue();
            }
        }
    }
}
