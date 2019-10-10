package KinomotoSakuraMod.Potions;

import KinomotoSakuraMod.Powers.KSMOD_MagickChargePower;
import basemod.abstracts.CustomPotion;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class KSMOD_MagickBottle extends CustomPotion
{
    public static final String POTION_ID = "KSMOD_MagickBottle";
    private static final String NAME;
    private static final String[] DESCRIPTIONS;
    private static final PotionRarity POTION_RARITY = PotionRarity.COMMON;
    private static final PotionSize POTION_SIZE = PotionSize.JAR;
    private static final PotionColor POTION_COLOR = PotionColor.BLUE;
    private static final int MAGIC_NUMBER = 12;

    static
    {
        PotionStrings potionString = CardCrawlGame.languagePack.getPotionString(POTION_ID);
        NAME = potionString.NAME;
        DESCRIPTIONS = potionString.DESCRIPTIONS;
    }

    public KSMOD_MagickBottle()
    {
        super(NAME, POTION_ID, POTION_RARITY, POTION_SIZE, POTION_COLOR);
        this.potency = this.getPotency();
        updateDescription();
    }

    private void updateDescription()
    {
        this.description = DESCRIPTIONS[0] + MAGIC_NUMBER + DESCRIPTIONS[1];
        this.tips.add(new PowerTip(this.name, this.description));
    }

    @Override
    public int getPotency(int ascensionLevel)
    {
        return 1;
    }

    @Override
    public AbstractPotion makeCopy()
    {
        return new KSMOD_MagickBottle();
    }

    @Override
    public void use(AbstractCreature abstractCreature)
    {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT)
        {
            this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new KSMOD_MagickChargePower(AbstractDungeon.player, MAGIC_NUMBER), MAGIC_NUMBER));
        }
    }
}
