package KinomotoSakuraMod.Relics;

import KinomotoSakuraMod.Cards.ClowCard.ClowCardTheShield;
import KinomotoSakuraMod.Cards.ClowCard.ClowCardTheSword;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import java.util.ArrayList;

public class KSMOD_GemBrooch extends CustomRelic
{
    public static final String RELIC_ID = "KSMOD_GemBrooch";
    private static final String RELIC_IMG_PATH = "img/relics/icon/gem_brooch.png";
    private static final String RELIC_IMG_OTL_PATH = "img/relics/outline/gem_brooch.png";
    private static final RelicTier RELIC_TIER = RelicTier.SHOP;
    private static final LandingSound RELIC_SOUND = LandingSound.CLINK;

    public KSMOD_GemBrooch()
    {
        super(RELIC_ID,
                ImageMaster.loadImage(RELIC_IMG_PATH),
                ImageMaster.loadImage(RELIC_IMG_OTL_PATH),
                RELIC_TIER,
                RELIC_SOUND);
    }

    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy()
    {
        return new KSMOD_GemBrooch();
    }

    public void onEquip()
    {
        AbstractCard sword = null;
        AbstractCard shield = null;
        ArrayList<AbstractCard> cards = new ArrayList<>();
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group)
        {
            if (c.cardID.equals(ClowCardTheSword.ID))
            {
                if (sword == null)
                {
                    sword = c;
                }
                else
                {
                    cards.add(c);
                }
            }
            else if (c.cardID.equals(ClowCardTheShield.ID))
            {
                if (shield == null)
                {
                    shield = c;
                }
                else
                {
                    cards.add(c);
                }
            }
        }
        if (cards.size() == 1)
        {
            AbstractDungeon.effectList.add(new PurgeCardEffect(cards.get(0),
                    Settings.WIDTH * 0.5F,
                    Settings.HEIGHT * 0.5F));
            AbstractDungeon.player.masterDeck.removeCard(cards.get(0));
        }
        else if (cards.size() > 1)
        {
            for (int i = 0; i < cards.size(); i++)
            {
                AbstractDungeon.effectList.add(new PurgeCardEffect(cards.get(i),
                        MathUtils.random(0.1F, 0.9F) * Settings.WIDTH,
                        MathUtils.random(0.1F, 0.9F) * Settings.HEIGHT));
                AbstractDungeon.player.masterDeck.removeCard(cards.get(i));
            }
        }
        if (sword != null)
        {
            UpgradeCard(sword);
        }
        if (sword != null)
        {
            UpgradeCard(shield);
        }
    }

    private void UpgradeCard(AbstractCard card)
    {
        card.upgrade();
        AbstractDungeon.player.bottledCardUpgradeCheck(card);
        AbstractDungeon.topLevelEffectsQueue.add(new ShowCardBrieflyEffect(card.makeStatEquivalentCopy(),
                MathUtils.random(0.1F, 0.9F) * Settings.WIDTH,
                MathUtils.random(0.25F, 0.75F) * Settings.HEIGHT));
    }
}
