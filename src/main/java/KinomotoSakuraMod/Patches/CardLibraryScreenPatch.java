package KinomotoSakuraMod.Patches;

import KinomotoSakuraMod.Cards.AbstractMagicCard;
import KinomotoSakuraMod.Utility.Utility;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.screens.compendium.CardLibSortHeader;
import com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen;

import java.lang.reflect.Field;
import java.util.ArrayList;

@SpirePatch(clz = CardLibraryScreen.class, method = "updateCards", paramtypez = {})
public class CardLibraryScreenPatch
{
    private static final int CARD_NUMBER_PER_LINE = 6;
    private static float DRAW_START_X = ((float) Settings.WIDTH - 5.0F * AbstractCard.IMG_WIDTH * 0.75F - 4.0F * Settings.CARD_VIEW_PAD_X) / 2.0F + AbstractCard.IMG_WIDTH * 0.75F / 2.0F;
    private static float DRAW_START_Y = Settings.HEIGHT * 0.66F;
    private static float PAD_X = AbstractMagicCard.CARD_WIDTH * 0.778F + Settings.CARD_VIEW_PAD_X;
    private static float PAD_Y = AbstractMagicCard.CARD_HEIGHT * 0.75F + Settings.CARD_VIEW_PAD_Y;

    public static SpireReturn<Object> Prefix(CardLibraryScreen lib) throws NoSuchFieldException, IllegalAccessException
    {
        CardGroup visibleCards = (CardGroup) Utility.GetFieldByReflect(lib, CardLibraryScreen.class, "visibleCards").get(lib);
        ArrayList<AbstractCard> cards = visibleCards.group;
        if (cards.get(0).color == CustomCardColor.CLOWCARD_COLOR || cards.get(0).color == CustomCardColor.SAKURACARD_COLOR || cards.get(0).color == CustomCardColor.SPELL_COLOR)
        {
            Field hoveredCard = Utility.GetFieldByReflect(lib, CardLibraryScreen.class, "hoveredCard");
            hoveredCard.set(lib, null);
            int lineNum = 0;
            for (int i = 0; i < cards.size(); ++i)
            {
                AbstractCard card = cards.get(i);
                int mod = i % CARD_NUMBER_PER_LINE;
                if (mod == 0 && i != 0)
                {
                    ++lineNum;
                }
                float currentDiffY = Utility.GetFieldByReflect(lib, CardLibraryScreen.class, "currentDiffY").getFloat(lib);
                card.target_x = DRAW_START_X + (float) mod * PAD_X;
                card.target_y = DRAW_START_Y + currentDiffY - (float) lineNum * PAD_Y;
                card.update();
                card.updateHoverLogic();
                if (card.hb.hovered)
                {
                    hoveredCard.set(lib, card);
                }
            }
            CardLibSortHeader sortHeader = (CardLibSortHeader) Utility.GetFieldByReflect(lib, CardLibraryScreen.class, "sortHeader").get(lib);
            if (sortHeader.justSorted)
            {
                for (AbstractCard card : cards)
                {
                    card.current_x = card.target_x;
                }
                sortHeader.justSorted = false;
            }
            return SpireReturn.Return(null);
        }
        else
        {
            return SpireReturn.Continue();
        }
    }
}