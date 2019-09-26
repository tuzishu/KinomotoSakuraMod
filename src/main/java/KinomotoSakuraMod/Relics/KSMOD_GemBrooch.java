package KinomotoSakuraMod.Relics;

import KinomotoSakuraMod.Cards.ClowCard.ClowCardTheSword;
import KinomotoSakuraMod.Cards.SakuraCard.SakuraCardTheSword;
import KinomotoSakuraMod.Characters.KinomotoSakura;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class KSMOD_GemBrooch extends CustomRelic
{
    public static final String RELIC_ID = "KSMOD_GemBrooch";
    private static final String RELIC_IMG_PATH = "img/relics/icon/gem_brooch.png";
    private static final String RELIC_IMG_OTL_PATH = "img/relics/outline/gem_brooch.png";
    private static final RelicTier RELIC_TIER = RelicTier.SHOP;
    private static final LandingSound RELIC_SOUND = LandingSound.CLINK;
    private static final int TRIGGER_NUMBER = 4;
    private static final int DAMAGE_PROMOTE = 1;
    private static final int STRENGTH_NUMBER = 2;

    public KSMOD_GemBrooch()
    {
        super(RELIC_ID, ImageMaster.loadImage(RELIC_IMG_PATH), ImageMaster.loadImage(RELIC_IMG_OTL_PATH), RELIC_TIER, RELIC_SOUND);
    }

    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0] + TRIGGER_NUMBER + this.DESCRIPTIONS[1] + DAMAGE_PROMOTE + this.DESCRIPTIONS[2] + STRENGTH_NUMBER + this.DESCRIPTIONS[3];
    }

    public AbstractRelic makeCopy()
    {
        return new KSMOD_GemBrooch();
    }

    public void atBattleStart()
    {
        if (getDamagePromote() >= TRIGGER_NUMBER * DAMAGE_PROMOTE)
        {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, STRENGTH_NUMBER), STRENGTH_NUMBER));
        }
    }

    public int getDamagePromote()
    {
        if (AbstractDungeon.player instanceof KinomotoSakura)
        {
            int count = 0;
            for (AbstractCard card : AbstractDungeon.player.masterDeck.group)
            {
                if (card.cardID == ClowCardTheSword.ID || card.cardID == SakuraCardTheSword.ID)
                {
                    count += 1;
                }
            }
            if (count < TRIGGER_NUMBER)
            {
                return (TRIGGER_NUMBER - count) * DAMAGE_PROMOTE;
            }
        }
        return 0;
    }
}
