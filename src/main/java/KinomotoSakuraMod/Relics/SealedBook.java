package KinomotoSakuraMod.Relics;

import KinomotoSakuraMod.Patches.CustomTag;
import KinomotoSakuraMod.Powers.ElementMagickPower;
import KinomotoSakuraMod.Powers.EnhancementMagickPower;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class SealedBook extends CustomRelic
{
    public static final String RELIC_ID = "SealedBook";
    private static final String RELIC_IMG_PATH = "img/relics/icon/SealedBook.png";
    private static final String RELIC_IMG_OTL_PATH = "img/relics/outline/SealedBook.png";
    private static final RelicTier RELIC_TIER = RelicTier.STARTER;
    private static final LandingSound RELIC_SOUND = AbstractRelic.LandingSound.MAGICAL;

    public SealedBook()
    {
        super(RELIC_ID, ImageMaster.loadImage(RELIC_IMG_PATH), ImageMaster.loadImage(RELIC_IMG_OTL_PATH), RELIC_TIER, RELIC_SOUND);
    }

    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy()
    {
        return new SealedBook();
    }

    public void atBattleStart()
    {
        int countEnhancement = 0;
        int countElement = 0;
        for (AbstractCard card : AbstractDungeon.player.masterDeck.group)
        {
            countEnhancement += card.hasTag(CustomTag.PHYSICS_CARD) ? 1 : 0;
            countElement += card.hasTag(CustomTag.ELEMENT_CARD) ? 1 : 0;
        }
        AbstractPlayer player = AbstractDungeon.player;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new EnhancementMagickPower(player, countEnhancement), countEnhancement));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new ElementMagickPower(player, countElement), countElement));
    }
}
