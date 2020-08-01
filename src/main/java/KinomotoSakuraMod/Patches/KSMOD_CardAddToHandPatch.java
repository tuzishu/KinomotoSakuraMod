package KinomotoSakuraMod.Patches;

import KinomotoSakuraMod.Cards.KSMOD_AbstractMagicCard;
import KinomotoSakuraMod.Characters.KinomotoSakura;
import KinomotoSakuraMod.Powers.KSMOD_NothingPower;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

public class KSMOD_CardAddToHandPatch
{
    @SpirePatch(clz = ShowCardAndAddToHandEffect.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {AbstractCard.class, float.class, float.class})
    public static class Constructor_0
    {
        public static void Postfix(ShowCardAndAddToHandEffect effect, AbstractCard card, float x, float y)
        {
            TrySetCost(card);
        }
    }

    @SpirePatch(clz = ShowCardAndAddToHandEffect.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {AbstractCard.class})
    public static class Constructor_1
    {
        public static void Postfix(ShowCardAndAddToHandEffect effect, AbstractCard card)
        {
            TrySetCost(card);
        }
    }

    public static void TrySetCost(AbstractCard card)
    {
        if (AbstractDungeon.player.hasPower(KSMOD_NothingPower.POWER_ID) && card instanceof KSMOD_AbstractMagicCard && card.cost > 0)
        {
            card.setCostForTurn(-9);
        }
    }
}
