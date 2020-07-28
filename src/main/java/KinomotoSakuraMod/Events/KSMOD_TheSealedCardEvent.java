package KinomotoSakuraMod.Events;

import KinomotoSakuraMod.Cards.ClowCard.ClowCardTheNothing;
import KinomotoSakuraMod.Cards.SakuraCard.SakuraCardTheHope;
import KinomotoSakuraMod.Cards.SakuraCard.SakuraCardTheLove;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.RoomEventDialog;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class KSMOD_TheSealedCardEvent extends AbstractEvent
{
    public static final String ID = "KSMOD_TheSealedCardEvent";
    private static final String[] DESCRIPTIONS;
    private static final String[] OPTIONS;
    private static final String IMAGE_PATH = "img/events/the_sealed_card.jpg";
    private static float LOSE_MAX_HP_RATE = 0.2F;
    private static int CARD_PURGE_NUMBER = 2;
    private AbstractCard clowCard;
    private AbstractCard sakuraCard;
    private int screen;

    static
    {
        EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
    }

    public KSMOD_TheSealedCardEvent()
    {
        clowCard = new ClowCardTheNothing();
        sakuraCard = new SakuraCardTheHope();

        this.screen = 0;
        this.body = DESCRIPTIONS[screen];
        this.roomEventText.clear();
        this.roomEventText.addDialogOption(OPTIONS[0], !HasLoveCard(), clowCard);
        this.roomEventText.addDialogOption(OPTIONS[1], HasLoveCard(), sakuraCard);
        this.roomEventText.addDialogOption(OPTIONS[2] + OPTIONS[3] + OPTIONS[4]);

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
                        break;
                    case 1:
                        Screen1FightWithLove();
                        break;
                    default:
                        Screen1Escape();
                        break;
                }
                break;
            case 1:
                if (buttonIndex == 0)
                {
                    Screen2Fight();
                }
                break;
            case 2:
                if (buttonIndex == 0)
                {
                    Screen3Fight();
                }
                break;
            default:
                Leave();
                break;
        }
    }

    private void Screen1FightWithoutLove()
    {
        screen = 1;
        this.roomEventText.updateBodyText(DESCRIPTIONS[screen]);
        this.roomEventText.updateDialogOption(0, OPTIONS[5]);
        this.roomEventText.removeDialogOption(1);
        this.roomEventText.removeDialogOption(2);
    }

    private void Screen1FightWithLove()
    {
        screen = 2;
        this.roomEventText.updateBodyText(DESCRIPTIONS[screen]);
        this.roomEventText.updateDialogOption(0, OPTIONS[5]);
        this.roomEventText.removeDialogOption(1);
        this.roomEventText.removeDialogOption(2);
    }

    private void Screen1Escape()
    {
        screen = 3;
        this.roomEventText.updateBodyText(DESCRIPTIONS[screen]);
        this.roomEventText.updateDialogOption(0, OPTIONS[6]);
        this.roomEventText.removeDialogOption(1);
        this.roomEventText.removeDialogOption(2);

    }

    private void Screen2Fight()
    {

    }

    private void Screen3Fight()
    {

    }

    private void Leave()
    {
        this.openMap();
    }

    private boolean HasLoveCard()
    {
        return AbstractDungeon.player.masterDeck.group.stream().anyMatch(c -> c.cardID.equals(SakuraCardTheLove.ID));
    }
}
