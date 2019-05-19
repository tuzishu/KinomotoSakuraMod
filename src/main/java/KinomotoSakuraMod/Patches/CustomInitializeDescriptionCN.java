package KinomotoSakuraMod.Patches;

import KinomotoSakuraMod.Cards.AbstractMagicCard;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DescriptionLine;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.GameDictionary;

import java.util.ArrayList;

@SpirePatch(clz = AbstractCard.class, method = "initializeDescriptionCN", paramtypez = {})
public class CustomInitializeDescriptionCN
{
    private static final float DESCRIPTION_WIDTH = 220F;

    public static SpireReturn<Object> Prefix(AbstractCard card) throws NoSuchFieldException, IllegalAccessException
    {
        if (card instanceof AbstractMagicCard)
        {
            boolean hovered = card.hb.hovered;
            if (hovered)
            {
                card.description.clear();
                int numLines = 1;
                StringBuilder currentLine = new StringBuilder("");
                float currentWidth = 0.0F;
                String[] desList = card.rawDescription.split(" ");

                for (int i = 0; i < desList.length; ++i)
                {
                    String word = desList[i];
                    word = word.trim();
                    if (!word.contains("NL"))
                    {
                        String keywordTmp = word.toLowerCase();
                        String retVal = (String) GameDictionary.parentWord.get(keywordTmp);
                        keywordTmp = (retVal != null ? retVal : keywordTmp);

                        GlyphLayout gl;
                        if (GameDictionary.keywords.containsKey(keywordTmp))
                        {
                            if (!card.keywords.contains(keywordTmp))
                            {
                                card.keywords.add(keywordTmp);
                            }

                            gl = new GlyphLayout(FontHelper.cardDescFont_N, word);
                            if (currentWidth + gl.width > DESCRIPTION_WIDTH * Settings.scale)
                            {
                                ++numLines;
                                card.description.add(new DescriptionLine(currentLine.toString(), currentWidth));
                                currentLine = new StringBuilder("");
                                currentWidth = gl.width;
                                currentLine.append(" *").append(word).append(" ");
                            }
                            else
                            {
                                currentLine.append(" *").append(word).append(" ");
                                currentWidth += gl.width;
                            }
                        }
                        else if (!word.equals("[R]") && !word.equals("[G]") && !word.equals("[B]"))
                        {
                            {
                                word = word.trim();
                                char[] charList = word.toCharArray();

                                for (int j = 0; j < charList.length; ++j)
                                {
                                    char c = charList[j];
                                    gl = new GlyphLayout(FontHelper.cardDescFont_N, Character.toString(c));
                                    currentLine.append(c);
                                    if (currentWidth + gl.width > DESCRIPTION_WIDTH * Settings.scale)
                                    {
                                        ++numLines;
                                        card.description.add(new DescriptionLine(currentLine.toString(), currentWidth));
                                        currentLine = new StringBuilder("");
                                        currentWidth = gl.width;
                                    }
                                    else
                                    {
                                        currentWidth += gl.width;
                                    }
                                }
                            }
                        }
                        else
                        {
                            switch (card.color)
                            {
                                case RED:
                                    if (!card.keywords.contains("[R]"))
                                    {
                                        card.keywords.add("[R]");
                                    }
                                    break;
                                case GREEN:
                                    if (!card.keywords.contains("[G]"))
                                    {
                                        card.keywords.add("[G]");
                                    }
                                    break;
                                case BLUE:
                                    if (!card.keywords.contains("[B]"))
                                    {
                                        card.keywords.add("[B]");
                                    }
                                    break;
                                default:
                            }

                            if (currentWidth + 24.0F * Settings.scale > DESCRIPTION_WIDTH * Settings.scale)
                            {
                                ++numLines;
                                card.description.add(new DescriptionLine(currentLine.toString(), currentWidth));
                                currentLine = new StringBuilder("");
                                currentWidth = 24.0F * Settings.scale;
                                currentLine.append(" ").append(word).append(" ");
                            }
                            else
                            {
                                currentLine.append(" ").append(word).append(" ");
                                currentWidth += 24.0F * Settings.scale;
                            }
                        }
                    }
                }

                String lastLine = currentLine.toString();
                if (!lastLine.isEmpty())
                {
                    card.description.add(new DescriptionLine(lastLine, currentWidth));
                }

                if (lastLine.equals("。"))
                {
                    ArrayList var10000 = card.description;
                    int var10001 = card.description.size() - 2;
                    StringBuilder var10004 = new StringBuilder();
                    DescriptionLine var10006 = (DescriptionLine) card.description.get(card.description.size() - 2);
                    var10000.set(var10001, new DescriptionLine(var10006.text = var10004.append(var10006.text).append("。").toString(), ((DescriptionLine) card.description.get(card.description.size() - 2)).width));
                    card.description.remove(card.description.size() - 1);
                }
            }
            return SpireReturn.Return((Object) null);
        }
        else
        {
            return SpireReturn.Continue();
        }
    }
}
