package KinomotoSakuraMod.Utility;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class KSMOD_ImageConst
{
    private static final String BANNER_COMMON_IMAGE_PATH = "img/banner/common.png";
    private static final String BANNER_UNCOMMON_IMAGE_PATH = "img/banner/uncommon.png";
    private static final String BANNER_RARE_IMAGE_PATH = "img/banner/rare.png";
    private static final String BANNER_COMMON_IMAGE_MASK_PATH = "img/banner/common_mask.png";
    private static final String BANNER_UNCOMMON_IMAGE_MASK_PATH = "img/banner/uncommon_mask.png";
    private static final String BANNER_RARE_IMAGE_MASK_PATH = "img/banner/rare_mask.png";
    private static final String FRAME_COMMON_IMAGE_PATH = "img/frame/common.png";
    private static final String FRAME_UNCOMMON_IMAGE_PATH = "img/frame/uncommon.png";
    private static final String FRAME_RARE_IMAGE_PATH = "img/frame/rare.png";

    public static final Texture BANNER_COMMON;
    public static final Texture BANNER_UNCOMMON;
    public static final Texture BANNER_RARE;
    public static final Texture BANNER_COMMON_MASK;
    public static final Texture BANNER_UNCOMMON_MASK;
    public static final Texture BANNER_RARE_MASK;
    public static final Texture FRAME_COMMON;
    public static final Texture FRAME_UNCOMMON;
    public static final Texture FRAME_RARE;

    static
    {
        BANNER_COMMON = ImageMaster.loadImage(BANNER_COMMON_IMAGE_PATH);
        BANNER_UNCOMMON = ImageMaster.loadImage(BANNER_UNCOMMON_IMAGE_PATH);
        BANNER_RARE = ImageMaster.loadImage(BANNER_RARE_IMAGE_PATH);
        BANNER_COMMON_MASK = ImageMaster.loadImage(BANNER_COMMON_IMAGE_MASK_PATH);
        BANNER_UNCOMMON_MASK = ImageMaster.loadImage(BANNER_UNCOMMON_IMAGE_MASK_PATH);
        BANNER_RARE_MASK = ImageMaster.loadImage(BANNER_RARE_IMAGE_MASK_PATH);
        FRAME_COMMON = ImageMaster.loadImage(FRAME_COMMON_IMAGE_PATH);
        FRAME_UNCOMMON = ImageMaster.loadImage(FRAME_UNCOMMON_IMAGE_PATH);
        FRAME_RARE = ImageMaster.loadImage(FRAME_RARE_IMAGE_PATH);
    }
}
