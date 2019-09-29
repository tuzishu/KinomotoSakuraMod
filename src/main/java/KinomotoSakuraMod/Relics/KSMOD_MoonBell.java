package KinomotoSakuraMod.Relics;

import KinomotoSakuraMod.Cards.SpellCard.SpellCardTurn;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class KSMOD_MoonBell extends CustomRelic
{
    public static final String RELIC_ID = "KSMOD_MoonBell";
    private static final String RELIC_IMG_PATH = "img/relics/icon/moon_bell.png";
    private static final String RELIC_IMG_INVALID_PATH = "img/relics/icon/moon_bell_invalid.png";
    private static final String RELIC_IMG_OTL_PATH = "img/relics/outline/moon_bell.png";
    private static final RelicTier RELIC_TIER = RelicTier.RARE;
    private static final LandingSound RELIC_SOUND = LandingSound.SOLID;
    private static final int TRIGGER_COST = 2;
    private static final int HEAL_NUMBER = 1;
    private static final float HEAL_RATE = 0.3F;

    public KSMOD_MoonBell()
    {
        super(RELIC_ID, ImageMaster.loadImage(RELIC_IMG_PATH), ImageMaster.loadImage(RELIC_IMG_OTL_PATH), RELIC_TIER, RELIC_SOUND);
    }

    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0] + TRIGGER_COST + this.DESCRIPTIONS[1] + HEAL_NUMBER + this.DESCRIPTIONS[2] + (int) (HEAL_RATE * 100) + this.DESCRIPTIONS[3];
    }

    public AbstractRelic makeCopy()
    {
        return new KSMOD_MoonBell();
    }

    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction)
    {
        if (targetCard.cost >= TRIGGER_COST && !usedUp)
        {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new HealAction(AbstractDungeon.player, AbstractDungeon.player, HEAL_NUMBER));
        }
    }

    public void onTrigger()
    {
        this.flash();
        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        int healAmt = (int) (AbstractDungeon.player.maxHealth * HEAL_RATE);
        if (healAmt < 1)
        {
            healAmt = 1;
        }

        AbstractDungeon.player.heal(healAmt, true);
        AbstractCard card = new SpellCardTurn();
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(card));
        AbstractDungeon.player.masterDeck.addToBottom(card);
        this.usedUp = true;
        this.description = DESCRIPTIONS[4];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
        this.img = ImageMaster.loadImage(RELIC_IMG_INVALID_PATH);
    }

    public boolean hasUsed()
    {
        return usedUp;
    }
}
