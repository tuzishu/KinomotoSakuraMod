package KinomotoSakuraMod.Patches;

import KinomotoSakuraMod.Cards.AbstractMagicCard;
import KinomotoSakuraMod.Utility.ModUtility;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;

@SpirePatch(clz = AbstractCard.class, method = "renderBannerImage", paramtypez = {SpriteBatch.class, float.class, float.class})
public class CustomRenderBannerImage
{
    private static final String BANNER_COMMON_IMAGE_PATH = "img/banner/common.png";
    private static final String BANNER_UNCOMMON_IMAGE_PATH = "img/banner/uncommon.png";
    private static final String BANNER_RARE_IMAGE_PATH = "img/banner/rare.png";
    private static final String BANNER_COMMON_IMAGE_MASK_PATH = "img/banner/common_mask.png";
    private static final String BANNER_UNCOMMON_IMAGE_MASK_PATH = "img/banner/uncommon_mask.png";
    private static final String BANNER_RARE_IMAGE_MASK_PATH = "img/banner/rare_mask.png";
    private static final Texture BANNER_COMMON;
    private static final Texture BANNER_UNCOMMON;
    private static final Texture BANNER_RARE;
    private static final Texture BANNER_COMMON_MASK;
    private static final Texture BANNER_UNCOMMON_MASK;
    private static final Texture BANNER_RARE_MASK;

    static
    {
        BANNER_COMMON = ImageMaster.loadImage(BANNER_COMMON_IMAGE_PATH);
        BANNER_UNCOMMON = ImageMaster.loadImage(BANNER_UNCOMMON_IMAGE_PATH);
        BANNER_RARE = ImageMaster.loadImage(BANNER_RARE_IMAGE_PATH);
        BANNER_COMMON_MASK = ImageMaster.loadImage(BANNER_COMMON_IMAGE_MASK_PATH);
        BANNER_UNCOMMON_MASK = ImageMaster.loadImage(BANNER_UNCOMMON_IMAGE_MASK_PATH);
        BANNER_RARE_MASK = ImageMaster.loadImage(BANNER_RARE_IMAGE_MASK_PATH);
    }

    public static SpireReturn<Object> Prefix(AbstractCard card, SpriteBatch sb, float drawX, float drawY)
    {
        ModUtility.Logger.info((card instanceof AbstractMagicCard) +", "+card.color);
        if (card instanceof AbstractMagicCard && card.color == CustomCardColor.CLOWCARD_COLOR)
        {
            boolean ishovered = false;
            Texture texture;

            switch (card.rarity)
            {
                case RARE:
                    texture = ishovered ? BANNER_RARE_MASK : BANNER_RARE;
                    break;
                case UNCOMMON:
                    texture = ishovered ? BANNER_UNCOMMON_MASK : BANNER_UNCOMMON;
                    break;
                default:
                    texture = ishovered ? BANNER_COMMON_MASK : BANNER_COMMON;
                    break;
            }
            sb.setColor(Color.WHITE.cpy());
            try
            {
                sb.draw(texture, drawX, drawY, 256.0F, 256.0F, 512.0F, 512.0F, card.drawScale * Settings.scale, card.drawScale * Settings.scale, card.angle, 0, 0, 512, 512, false, false);
            }
            catch (Exception var7)
            {
                ModUtility.Logger.error(var7);
            }
            return SpireReturn.Return(null);
        }
        else
        {
            return SpireReturn.Continue();
        }

    }
}
