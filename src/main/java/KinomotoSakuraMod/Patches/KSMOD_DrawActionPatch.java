package KinomotoSakuraMod.Patches;

import KinomotoSakuraMod.Characters.KinomotoSakura;
import KinomotoSakuraMod.Powers.KSMOD_LightPower;
import KinomotoSakuraMod.Utility.KSMOD_Utility;
import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.cards.SoulGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.lang.reflect.Field;

public class KSMOD_DrawActionPatch
{
    @SpirePatch(clz = DrawCardAction.class, method = "update", paramtypez = {})
    public static class update
    {
        public static SpireReturn<Object> Prefix(DrawCardAction action) throws NoSuchFieldException, IllegalAccessException
        {
            if (AbstractDungeon.player instanceof KinomotoSakura)
            {
                int maxHandSize = 10 + (AbstractDungeon.player.hasPower(KSMOD_LightPower.POWER_ID) ? 2 : 0);
                if (action.amount <= 0)
                {
                    action.isDone = true;
                }
                else
                {
                    int deckSize = AbstractDungeon.player.drawPile.size();
                    int discardSize = AbstractDungeon.player.discardPile.size();
                    if (!SoulGroup.isActive())
                    {
                        if (deckSize + discardSize == 0)
                        {
                            action.isDone = true;
                        }
                        else if (AbstractDungeon.player.hand.size() == maxHandSize)
                        {
                            AbstractDungeon.player.createHandIsFullDialog();
                            action.isDone = true;
                        }
                        else
                        {
                            Field shuffleCheck = KSMOD_Utility.GetFieldByReflect(DrawCardAction.class, "shuffleCheck");
                            if (!shuffleCheck.getBoolean(action))
                            {
                                int tmp;
                                if (action.amount + AbstractDungeon.player.hand.size() > maxHandSize)
                                {
                                    tmp = maxHandSize - (action.amount + AbstractDungeon.player.hand.size());
                                    action.amount += tmp;
                                    AbstractDungeon.player.createHandIsFullDialog();
                                }
                                if (action.amount > deckSize)
                                {
                                    tmp = action.amount - deckSize;
                                    AbstractDungeon.actionManager.addToTop(new DrawCardAction(AbstractDungeon.player, tmp));
                                    AbstractDungeon.actionManager.addToTop(new EmptyDeckShuffleAction());
                                    if (deckSize != 0)
                                    {
                                        AbstractDungeon.actionManager.addToTop(new DrawCardAction(AbstractDungeon.player, deckSize));
                                    }

                                    action.amount = 0;
                                    action.isDone = true;
                                }
                                shuffleCheck.setBoolean(action, true);
                            }
                            Field duration = KSMOD_Utility.GetFieldByReflect(AbstractGameAction.class, "duration");
                            duration.set(action, duration.getFloat(action) - Gdx.graphics.getDeltaTime());
                            if (action.amount != 0 && duration.getFloat(action) < 0.0F)
                            {
                                if (Settings.FAST_MODE)
                                {
                                    duration.set(action, Settings.ACTION_DUR_XFAST);
                                }
                                else
                                {
                                    duration.set(action, Settings.ACTION_DUR_FASTER);
                                }
                                --action.amount;
                                if (!AbstractDungeon.player.drawPile.isEmpty())
                                {
                                    AbstractDungeon.player.draw();
                                    AbstractDungeon.player.hand.refreshHandLayout();
                                }
                                else
                                {
                                    KSMOD_Utility.Logger.warn("Player attempted to draw from an empty drawpile mid-DrawAction?MASTER DECK: " + AbstractDungeon.player.masterDeck.getCardNames());
                                    action.isDone = true;
                                }
                                if (action.amount == 0)
                                {
                                    action.isDone = true;
                                }
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
}
