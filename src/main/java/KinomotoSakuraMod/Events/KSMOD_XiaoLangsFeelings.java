package KinomotoSakuraMod.Events;

import KinomotoSakuraMod.Cards.SakuraCard.SakuraCardTheLove;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

public class KSMOD_XiaoLangsFeelings extends AbstractImageEvent
{
    public static final String ID = "KSMOD_XiaoLangsFeelings";
    private static final String NAME;
    private static final String[] DESCRIPTIONS;
    private static final String[] OPTIONS;
    private static final String IMAGE_PATH = "img/events/xiaolangs_feelings.jpg";
    private boolean hasSelected = false;
    private AbstractCard card;

    static
    {
        EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
    }

    public KSMOD_XiaoLangsFeelings()
    {
        super(NAME, DESCRIPTIONS[0], IMAGE_PATH);
        this.imageEventText.setDialogOption(OPTIONS[0], new SakuraCardTheLove());
        this.imageEventText.setDialogOption(OPTIONS[1]);
        card = new SakuraCardTheLove();
    }

    @Override
    protected void buttonEffect(int buttonIndex)
    {
        if (!hasSelected)
        {
            switch (buttonIndex)
            {
                case 0:
                    Accept();
                    break;
                default:
                    Reject();
                    break;
            }
        }
        else
        {
            Leave();
        }
    }

    private void Accept()
    {
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(card, Settings.WIDTH * 0.5F, Settings.HEIGHT * 0.5F));
        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
        this.imageEventText.updateDialogOption(0, OPTIONS[2]);
        this.imageEventText.removeDialogOption(1);
        this.hasSelected = true;
    }

    private void Reject()
    {
        AbstractDungeon.player.heal(AbstractDungeon.player.maxHealth - AbstractDungeon.player.currentHealth);
        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
        this.imageEventText.updateDialogOption(0, OPTIONS[2]);
        this.imageEventText.removeDialogOption(1);
        this.hasSelected = true;
    }

    private void Leave()
    {
        this.openMap();
    }
}
