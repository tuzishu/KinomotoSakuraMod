package KinomotoSakuraMod.Patches;

import KinomotoSakuraMod.Cards.KSMOD_AbstractMagicCard;
import KinomotoSakuraMod.Utility.KSMOD_Utility;
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
public class KSMOD_CardLibraryScreenPatch
{
    private static final int CARDS_PER_LINE = 6;
    private static float DRAW_START_X = ((float) Settings.WIDTH - 5.0F * AbstractCard.IMG_WIDTH * 0.75F - 4.0F * Settings.CARD_VIEW_PAD_X) / 2.0F + AbstractCard.IMG_WIDTH * 0.75F / 2.0F;
    private static float DRAW_START_Y = Settings.HEIGHT * 0.66F;
    private static float PAD_X = KSMOD_AbstractMagicCard.IMG_WIDTH * 0.778F + Settings.CARD_VIEW_PAD_X;
    private static float PAD_Y = KSMOD_AbstractMagicCard.IMG_HEIGHT * 0.79F + Settings.CARD_VIEW_PAD_Y;

    public static SpireReturn<Object> Prefix(CardLibraryScreen lib) throws NoSuchFieldException, IllegalAccessException
    {
        CardGroup visibleCards = (CardGroup) KSMOD_Utility.GetFieldByReflect(CardLibraryScreen.class, "visibleCards").get(lib);
        ArrayList<AbstractCard> cards = visibleCards.group;
        if (Check(cards))
        {
            Field hoveredCard = KSMOD_Utility.GetFieldByReflect(CardLibraryScreen.class, "hoveredCard");
            hoveredCard.set(lib, null);
            int lineNum = 0;
            for (int i = 0; i < cards.size(); ++i)
            {
                AbstractCard card = cards.get(i);
                int mod = i % CARDS_PER_LINE;
                if (mod == 0 && i != 0)
                {
                    ++lineNum;
                }
                float currentDiffY = KSMOD_Utility.GetFieldByReflect(CardLibraryScreen.class, "currentDiffY").getFloat(lib);
                card.target_x = DRAW_START_X + (float) mod * PAD_X;
                card.target_y = DRAW_START_Y + currentDiffY - (float) lineNum * PAD_Y;
                card.update();
                card.updateHoverLogic();
                if (card.hb.hovered)
                {
                    hoveredCard.set(lib, card);
                }
            }
            CardLibSortHeader sortHeader = (CardLibSortHeader) KSMOD_Utility.GetFieldByReflect(CardLibraryScreen.class, "sortHeader").get(lib);
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

    public static boolean Check(ArrayList<AbstractCard> cards)
    {
        if (cards.size() > 0)
        {
            AbstractCard card = cards.get(0);
            return card.color == KSMOD_CustomCardColor.CLOWCARD_COLOR || card.color == KSMOD_CustomCardColor.SAKURACARD_COLOR || card.color == KSMOD_CustomCardColor.SPELL_COLOR;
        }
        return false;
    }
}