package KinomotoSakuraMod.Patches;

import KinomotoSakuraMod.Powers.KSMOD_LoopPower_SakuraCard;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class KSMOD_ManualDiscardHook
{
    @SpirePatch(clz = AbstractCard.class, method = "triggerOnManualDiscard", paramtypez = {})
    public static class triggerOnManualDiscard
    {
        public static void Postfix(AbstractCard card)
        {
            if (AbstractDungeon.player != null && AbstractDungeon.player.hasPower(KSMOD_LoopPower_SakuraCard.POWER_ID))
            {
                KSMOD_LoopPower_SakuraCard power = (KSMOD_LoopPower_SakuraCard) AbstractDungeon.player.getPower(KSMOD_LoopPower_SakuraCard.POWER_ID);
                power.onManualDiscard();
            }
        }
    }
}
