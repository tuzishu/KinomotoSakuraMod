package KinomotoSakuraMod.Relics;

import KinomotoSakuraMod.Cards.SpellCard.SpellCardRelease;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MinionPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class KSMOD_DarknessWand extends CustomRelic
{
    public static final String RELIC_ID = "KSMOD_DarknessWand";
    private static final String RELIC_IMG_PATH = "img/relics/icon/darkness_wand.png";
    private static final String RELIC_IMG_OTL_PATH = "img/relics/outline/darkness_wand.png";
    private static final RelicTier RELIC_TIER = RelicTier.BOSS;
    private static final LandingSound RELIC_SOUND = AbstractRelic.LandingSound.MAGICAL;
    private static final int CHARGE_NUMBER = 2;
    private static final int STRENGTH_NUMBER = 1;

    public KSMOD_DarknessWand()
    {
        super(RELIC_ID, ImageMaster.loadImage(RELIC_IMG_PATH), ImageMaster.loadImage(RELIC_IMG_OTL_PATH), RELIC_TIER, RELIC_SOUND);
    }

    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0] + CHARGE_NUMBER + this.DESCRIPTIONS[1] + STRENGTH_NUMBER + this.DESCRIPTIONS[2];
    }

    public AbstractRelic makeCopy()
    {
        return new KSMOD_DarknessWand();
    }

    public void onEquip()
    {
        AbstractDungeon.player.energy.energyMaster += 1;
    }

    public void onUnequip()
    {
        AbstractDungeon.player.energy.energyMaster -= 1;
    }

    public void atBattleStart()
    {
        this.flash();
        for (AbstractMonster monster: AbstractDungeon.getMonsters().monsters)
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, AbstractDungeon.player, new StrengthPower(monster, STRENGTH_NUMBER), STRENGTH_NUMBER));
        }
    }

    public void onMonsterDeath(AbstractMonster monster)
    {
        if (!monster.hasPower(MinionPower.POWER_ID))
        {
            if (AbstractDungeon.player.hasRelic(KSMOD_SealedWand.RELIC_ID))
            {
                this.flash();
                KSMOD_SealedWand wand = (KSMOD_SealedWand) AbstractDungeon.player.getRelic(KSMOD_SealedWand.RELIC_ID);
                wand.GainCharge(CHARGE_NUMBER, monster);
            }
            else if (AbstractDungeon.player.hasRelic(KSMOD_StarWand.RELIC_ID))
            {
                this.flash();
                KSMOD_StarWand wand = (KSMOD_StarWand) AbstractDungeon.player.getRelic(KSMOD_StarWand.RELIC_ID);
                wand.GainCharge(CHARGE_NUMBER, monster);
            }
            else if (AbstractDungeon.player.hasRelic(KSMOD_UltimateWand.RELIC_ID))
            {
                this.flash();
                KSMOD_UltimateWand wand = (KSMOD_UltimateWand) AbstractDungeon.player.getRelic(KSMOD_UltimateWand.RELIC_ID);
                wand.GainCharge(CHARGE_NUMBER, monster);
            }
        }
    }
}
