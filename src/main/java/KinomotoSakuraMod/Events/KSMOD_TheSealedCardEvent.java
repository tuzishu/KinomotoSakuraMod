package KinomotoSakuraMod.Events;

import KinomotoSakuraMod.Cards.ClowCard.ClowCardTheNothing;
import KinomotoSakuraMod.Cards.SakuraCard.SakuraCardTheHope;
import KinomotoSakuraMod.Cards.SakuraCard.SakuraCardTheLove;
import KinomotoSakuraMod.Effects.KSMOD_PurgeCardEffect;
import KinomotoSakuraMod.Monsters.KSMOD_TheNothingMonster;
import KinomotoSakuraMod.Utility.KSMOD_DataTool;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.RoomEventDialog;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class KSMOD_TheSealedCardEvent extends AbstractEvent
{
    public static final String ID = "KSMOD_TheSealedCardEvent";
    private static final String[] DESCRIPTIONS;
    private static final String[] OPTIONS;
    private static float LOSE_MAX_HP_RATE = 0.2F;
    private static int CARD_PURGE_NUMBER = 2;
    private static int MIN_GOLD = 32;
    private static int MAX_GOLD = 47;
    private AbstractCard theLoveCard;
    private AbstractCard theNothingCard;
    private AbstractCard theHopeCard;
    private int screen;
    private int loseHP;

    static
    {
        EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
    }

    public KSMOD_TheSealedCardEvent()
    {
        this.theLoveCard = new SakuraCardTheLove();
        theNothingCard = new ClowCardTheNothing();
        theHopeCard = new SakuraCardTheHope();

        this.screen = 0;
        this.loseHP = (int) (LOSE_MAX_HP_RATE * AbstractDungeon.player.maxHealth);
        this.body = DESCRIPTIONS[screen];
        this.roomEventText.clear();
        this.roomEventText.addDialogOption(OPTIONS[0], HasLoveCard(), theLoveCard);
        this.roomEventText.addDialogOption(OPTIONS[1], !HasLoveCard(), theLoveCard);
        this.roomEventText.addDialogOption(OPTIONS[2] + loseHP + OPTIONS[3] + CARD_PURGE_NUMBER + OPTIONS[4]);

        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.EVENT;
        this.hasDialog = true;
        this.hasFocus = true;
    }

    public void update()
    {
        super.update();
        if (!RoomEventDialog.waitForInput)
        {
            this.buttonEffect(this.roomEventText.getSelectedOption());
        }
    }

    @Override
    protected void buttonEffect(int buttonIndex)
    {
        switch (screen)
        {
            case 0:
                switch (buttonIndex)
                {
                    case 0:
                        Screen1FightWithoutLove();
                        return;
                    case 1:
                        Screen1FightWithLove();
                        return;
                    default:
                        Screen1Escape();
                        return;
                }
            case 1:
                if (buttonIndex == 0)
                {
                    Screen2Fight();
                }
                return;
            case 2:
                if (buttonIndex == 0)
                {
                    Screen3Fight();
                }
                return;
            default:
                Leave();
                return;
        }
    }

    private void Screen1FightWithoutLove()
    {
        screen = 1;
        this.roomEventText.updateBodyText(DESCRIPTIONS[screen]);
        this.roomEventText.removeDialogOption(2);
        this.roomEventText.removeDialogOption(1);
        this.roomEventText.removeDialogOption(0);
        this.roomEventText.addDialogOption(OPTIONS[5], theNothingCard);
        AbstractDungeon.getCurrRoom().monsters = MonsterHelper.getEncounter(KSMOD_TheNothingMonster.ID);
        ((KSMOD_TheNothingMonster) (AbstractDungeon.getCurrRoom().monsters.monsters.get(0))).SetReward(false, theNothingCard);
    }

    private void Screen1FightWithLove()
    {
        screen = 2;
        this.roomEventText.updateBodyText(DESCRIPTIONS[screen]);
        this.roomEventText.removeDialogOption(2);
        this.roomEventText.removeDialogOption(1);
        this.roomEventText.removeDialogOption(0);
        this.roomEventText.addDialogOption(OPTIONS[6], theHopeCard);
        AbstractDungeon.getCurrRoom().monsters = MonsterHelper.getEncounter(KSMOD_TheNothingMonster.ID);
        ((KSMOD_TheNothingMonster) (AbstractDungeon.getCurrRoom().monsters.monsters.get(0))).SetReward(true, theHopeCard);
    }

    private void Screen1Escape()
    {
        screen = 3;
        this.roomEventText.updateBodyText(DESCRIPTIONS[screen]);
        this.roomEventText.removeDialogOption(2);
        this.roomEventText.removeDialogOption(1);
        this.roomEventText.updateDialogOption(0, OPTIONS[7]);

        AbstractDungeon.player.damage(new DamageInfo(null, loseHP));
        PurgeRandomCard(CARD_PURGE_NUMBER);
    }

    private void Screen2Fight()
    {
        EnterFight();
    }

    private void Screen3Fight()
    {
        EnterFight();
    }

    private void Leave()
    {
        this.openMap();
    }

    private boolean HasLoveCard()
    {
        return AbstractDungeon.player.masterDeck.group.stream().anyMatch(c -> c.cardID.equals(SakuraCardTheLove.ID));
    }

    private void EnterFight()
    {
        AbstractDungeon.getCurrRoom().rewards.clear();
        AbstractDungeon.getCurrRoom().addGoldToRewards(AbstractDungeon.miscRng.random(MIN_GOLD, MAX_GOLD));
        AbstractDungeon.getCurrRoom().eliteTrigger = true;
        this.enterCombat();
        AbstractDungeon.lastCombatMetricKey = KSMOD_TheNothingMonster.ID;
        this.roomEventText.clear();
    }

    private void PurgeRandomCard(int amount)
    {
        if (amount <= 4)
        {
            for (int i = 0; i < amount; i++)
            {
                AbstractCard card = KSMOD_DataTool.GetRandomListElement(AbstractDungeon.player.masterDeck.group);
                AbstractDungeon.player.masterDeck.removeCard(card);
                if (card != null)
                {
                    card.setAngle(0);
                    AbstractDungeon.topLevelEffectsQueue.add(new KSMOD_PurgeCardEffect(card, Settings.WIDTH * (i + 1F) / (amount + 1F), Settings.HEIGHT * 0.5F));
                }
            }
        }
        else
        {
            for (int i = 0; i < amount; i++)
            {
                AbstractCard card = KSMOD_DataTool.GetRandomListElement(AbstractDungeon.player.masterDeck.group);
                AbstractDungeon.player.masterDeck.removeCard(card);
                if (card != null)
                {
                    card.setAngle(0);
                    AbstractDungeon.topLevelEffectsQueue.add(new KSMOD_PurgeCardEffect(card, MathUtils.random(Settings.WIDTH * 0.1F, Settings.WIDTH * 0.9F), MathUtils.random(Settings.HEIGHT * 0.1F, Settings.HEIGHT * 0.9F)));
                }
            }
        }
        AbstractDungeon.player.masterDeck.refreshHandLayout();
    }
}
