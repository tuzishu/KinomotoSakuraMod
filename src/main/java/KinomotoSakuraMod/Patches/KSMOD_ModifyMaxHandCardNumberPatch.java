package KinomotoSakuraMod.Patches;

import KinomotoSakuraMod.Characters.KinomotoSakura;
import KinomotoSakuraMod.Powers.KSMOD_LightPower;
import KinomotoSakuraMod.Utility.KSMOD_LoggerTool;
import KinomotoSakuraMod.Utility.KSMOD_Utility;
import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.common.FastDrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.DrawPileToHandAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.SoulGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;

public class KSMOD_ModifyMaxHandCardNumberPatch
{
    public static int GetCurrentMaxHandSize()
    {
        return 10 + (AbstractDungeon.player.hasPower(KSMOD_LightPower.POWER_ID) ? 2 : 0);
    }

    @SpirePatch(clz = AbstractPlayer.class, method = "draw", paramtypez = {})
    public static class AbstractPlayer_draw
    {
        public static SpireReturn<Object> Prefix(AbstractPlayer player) throws NoSuchFieldException, IllegalAccessException
        {
            if (AbstractDungeon.player instanceof KinomotoSakura)
            {
                if (player.hand.size() == GetCurrentMaxHandSize())
                {
                    player.createHandIsFullDialog();
                }
                else
                {
                    CardCrawlGame.sound.playAV("CARD_DRAW_8", -0.12F, 0.25F);
                    player.draw(1);
                    player.onCardDrawOrDiscard();
                }
                return SpireReturn.Return(null);
            }
            else
            {
                return SpireReturn.Continue();
            }
        }
    }

    @SpirePatch(clz = DrawCardAction.class, method = "update", paramtypez = {})
    public static class DrawCardAction_upgrade
    {
        public static SpireReturn<Object> Prefix(DrawCardAction action) throws NoSuchFieldException, IllegalAccessException
        {
            if (AbstractDungeon.player instanceof KinomotoSakura)
            {
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
                        else if (AbstractDungeon.player.hand.size() == GetCurrentMaxHandSize())
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
                                if (action.amount + AbstractDungeon.player.hand.size() > GetCurrentMaxHandSize())
                                {
                                    tmp = GetCurrentMaxHandSize() - (action.amount + AbstractDungeon.player.hand.size());
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
                                    KSMOD_LoggerTool.Logger.warn("Player attempted to draw from an empty drawpile mid-DrawAction?MASTER DECK: " + AbstractDungeon.player.masterDeck.getCardNames());
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

    @SpirePatch(clz = FastDrawCardAction.class, method = "update", paramtypez = {})
    public static class FastDrawCardAction_update
    {
        public static SpireReturn<Object> Prefix(FastDrawCardAction action) throws NoSuchFieldException, IllegalAccessException
        {
            if (AbstractDungeon.player instanceof KinomotoSakura)
            {
                int deckSize = AbstractDungeon.player.drawPile.size();
                int discardSize = AbstractDungeon.player.discardPile.size();
                if (!SoulGroup.isActive())
                {
                    if (deckSize + discardSize == 0)
                    {
                        action.isDone = true;
                    }
                    else
                    {
                        Field shuffleCheck = KSMOD_Utility.GetFieldByReflect(DrawCardAction.class, "shuffleCheck");
                        if (!shuffleCheck.getBoolean(action))
                        {
                            int tmp;
                            if (action.amount + AbstractDungeon.player.hand.size() > GetCurrentMaxHandSize())
                            {
                                tmp = GetCurrentMaxHandSize() - (action.amount + AbstractDungeon.player.hand.size());
                                action.amount += tmp;
                                AbstractDungeon.player.createHandIsFullDialog();
                            }

                            if (action.amount > deckSize)
                            {
                                tmp = action.amount - deckSize;
                                AbstractDungeon.actionManager.addToTop(new FastDrawCardAction(AbstractDungeon.player, tmp));
                                AbstractDungeon.actionManager.addToTop(new EmptyDeckShuffleAction());
                                if (deckSize != 0)
                                {
                                    AbstractDungeon.actionManager.addToTop(new FastDrawCardAction(AbstractDungeon.player, deckSize));
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
                            duration.set(action, Settings.ACTION_DUR_XFAST);
                            --action.amount;
                            AbstractDungeon.player.draw();
                            AbstractDungeon.player.hand.refreshHandLayout();
                            if (action.amount == 0)
                            {
                                action.isDone = true;
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

    @SpirePatch(clz = DrawPileToHandAction.class, method = "update", paramtypez = {})
    public static class DrawPileToHandAction_update
    {
        public static SpireReturn<Object> Prefix(DrawPileToHandAction action) throws NoSuchFieldException, IllegalAccessException, InvocationTargetException, NoSuchMethodException
        {
            if (AbstractDungeon.player instanceof KinomotoSakura)
            {
                float duration = KSMOD_Utility.GetFieldByReflect(AbstractGameAction.class, "duration").getFloat(action);
                AbstractPlayer p = (AbstractPlayer) KSMOD_Utility.GetFieldByReflect(DrawPileToHandAction.class, "p").get(action);
                if (duration == Settings.ACTION_DUR_MED)
                {
                    if (p.drawPile.isEmpty())
                    {
                        action.isDone = true;
                        return SpireReturn.Return(null);
                    }

                    CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                    Iterator var2 = p.drawPile.group.iterator();

                    AbstractCard card;
                    while (var2.hasNext())
                    {
                        card = (AbstractCard) var2.next();
                        AbstractCard.CardType typeToCheck = (AbstractCard.CardType) KSMOD_Utility.GetFieldByReflect(DrawPileToHandAction.class, "typeToCheck").get(action);
                        if (card.type == typeToCheck)
                        {
                            tmp.addToRandomSpot(card);
                        }
                    }
                    if (tmp.size() == 0)
                    {
                        action.isDone = true;
                        return SpireReturn.Return(null);
                    }
                    for (int i = 0; i < action.amount; ++i)
                    {
                        if (!tmp.isEmpty())
                        {
                            tmp.shuffle();
                            card = tmp.getBottomCard();
                            tmp.removeCard(card);
                            if (p.hand.size() == GetCurrentMaxHandSize())
                            {
                                p.drawPile.moveToDiscardPile(card);
                                p.createHandIsFullDialog();
                            }
                            else
                            {
                                card.unhover();
                                card.lighten(true);
                                card.setAngle(0.0F);
                                card.drawScale = 0.12F;
                                card.targetDrawScale = 0.75F;
                                card.current_x = CardGroup.DRAW_PILE_X;
                                card.current_y = CardGroup.DRAW_PILE_Y;
                                p.drawPile.removeCard(card);
                                AbstractDungeon.player.hand.addToTop(card);
                                AbstractDungeon.player.hand.refreshHandLayout();
                                AbstractDungeon.player.hand.applyPowers();
                            }
                        }
                    }

                    action.isDone = true;
                }
                Method tickDuration = KSMOD_Utility.GetMethodByReflect(AbstractGameAction.class, "tickDuration");
                tickDuration.invoke(action);
                return SpireReturn.Return(null);
            }
            else
            {
                return SpireReturn.Continue();
            }
        }
    }

    @SpirePatch(clz = MakeTempCardInHandAction.class, method = "update", paramtypez = {})
    public static class MakeTempCardInHandAction_update
    {
        public static SpireReturn<Object> Prefix(MakeTempCardInHandAction action) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
        {
            if (AbstractDungeon.player instanceof KinomotoSakura)
            {
                if (action.amount == 0)
                {
                    action.isDone = true;
                }
                else
                {
                    int discardAmount = 0;
                    int handAmount = action.amount;
                    if (action.amount + AbstractDungeon.player.hand.size() > GetCurrentMaxHandSize())
                    {
                        AbstractDungeon.player.createHandIsFullDialog();
                        discardAmount = action.amount + AbstractDungeon.player.hand.size() - GetCurrentMaxHandSize();
                        handAmount -= discardAmount;
                    }
                    Method addToHand = KSMOD_Utility.GetMethodByReflect(MakeTempCardInHandAction.class, "addToHand", int.class);
                    Method addToDiscard = KSMOD_Utility.GetMethodByReflect(MakeTempCardInHandAction.class, "addToDiscard", int.class);
                    addToHand.invoke(action, handAmount);
                    addToDiscard.invoke(action, discardAmount);
                    if (action.amount > 0)
                    {
                        AbstractDungeon.actionManager.addToTop(new WaitAction(0.8F));
                    }
                    action.isDone = true;
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
