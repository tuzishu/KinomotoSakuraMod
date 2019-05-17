package KinomotoSakuraMod.Patches;

import KinomotoSakuraMod.Cards.AbstractMagicCard;
import KinomotoSakuraMod.Characters.KinomotoSakura;
import KinomotoSakuraMod.Utility.ModUtility;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DescriptionLine;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.GameDictionary;

import java.util.ArrayList;

@SpirePatch(clz = AbstractCard.class, method = "initializeDescriptionCN", paramtypez = {})
public class OverrideInitializeDescriptionCN
{
    private static final float DESCRIPTION_WIDTH = 220F;

    public static SpireReturn<Object> Prefix(AbstractCard card)
    {
        if (false)
        // if (card instanceof AbstractMagicCard && card.type == AbstractCard.CardType.ATTACK)
        {
            // Invoker.setField(the_AbstractCard, "CN_DESC_BOX_WIDTH",  the_AbstractCard.IMG_WIDTH*1.05);
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

                        // MyNewMethod = Invoker.getMethod(the_AbstractCard, "dedupeKeyword", String.class);
                        // keywordTmp = the_AbstractCard.dedupeKeyword(keywordTmp);
                        // Invoker.getMethod(the_AbstractCard, "dedupeKeyword", Class...paramTypes)
                        // keywordTmp = the_AbstractCard.dedupeKeyword(keywordTmp);

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
                            // Field Iff = Invoker.getField(the_AbstractCard, "CN_DESC_BOX_WIDTH");
                            // Iff.setAccessible(true);
                            // Iff.get(the_AbstractCard);
                            // (float) Iff.get(the_AbstractCard);
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
                                    // the_AbstractCard.logger.info("ERROR: Tried to display an invalid energy type");
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

                // if (numLines > 5)
                // {
                //     the_AbstractCard.logger.info("WARNING: Card " + the_AbstractCard.name + " has lots of text");
                // }

            }

            // Invoker.setField(the_AbstractCard, "CN_DESC_BOX_WIDTH",  the_AbstractCard.IMG_WIDTH*0.72);
            return SpireReturn.Return((Object) null);
        }
        else
        {
            return SpireReturn.Continue();
        }
    }
}
