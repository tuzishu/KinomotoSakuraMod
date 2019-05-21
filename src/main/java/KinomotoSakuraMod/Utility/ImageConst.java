package KinomotoSakuraMod.Utility;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class ImageConst
{
    private static final String ORB_IMAGE_PATH = "img/512/card_orb.png";
    private static final String ORB_LARGE_IMAGE_PATH = "img/1024/card_orb_1024.png";
    private static final String BANNER_COMMON_IMAGE_PATH = "img/banner/common.png";
    private static final String BANNER_UNCOMMON_IMAGE_PATH = "img/banner/uncommon.png";
    private static final String BANNER_RARE_IMAGE_PATH = "img/banner/rare.png";
    private static final String BANNER_MASK_COMMON_IMAGE_PATH = "img/banner/mask_common.png";
    private static final String BANNER_MASK_UNCOMMON_IMAGE_PATH = "img/banner/mask_uncommon.png";
    private static final String BANNER_MASK_RARE_IMAGE_PATH = "img/banner/mask_rare.png";
    private static final String FRAME_COMMON_IMAGE_PATH = "img/frame/common.png";
    private static final String FRAME_UNCOMMON_IMAGE_PATH = "img/frame/uncommon.png";
    private static final String FRAME_RARE_IMAGE_PATH = "img/frame/rare.png";

    public static final Texture ORB;
    public static final Texture ORB_LARGE;
    public static final Texture BANNER_COMMON;
    public static final Texture BANNER_UNCOMMON;
    public static final Texture BANNER_RARE;
    public static final Texture BANNER_MASK_COMMON;
    public static final Texture BANNER_MASK_UNCOMMON;
    public static final Texture BANNER_MASK_RARE;
    public static final Texture FRAME_COMMON;
    public static final Texture FRAME_UNCOMMON;
    public static final Texture FRAME_RARE;

    static
    {
        ORB = ImageMaster.loadImage(ORB_IMAGE_PATH);
        ORB_LARGE = ImageMaster.loadImage(ORB_LARGE_IMAGE_PATH);
        BANNER_COMMON = ImageMaster.loadImage(BANNER_COMMON_IMAGE_PATH);
        BANNER_UNCOMMON = ImageMaster.loadImage(BANNER_UNCOMMON_IMAGE_PATH);
        BANNER_RARE = ImageMaster.loadImage(BANNER_RARE_IMAGE_PATH);
        BANNER_MASK_COMMON = ImageMaster.loadImage(BANNER_MASK_COMMON_IMAGE_PATH);
        BANNER_MASK_UNCOMMON = ImageMaster.loadImage(BANNER_MASK_UNCOMMON_IMAGE_PATH);
        BANNER_MASK_RARE = ImageMaster.loadImage(BANNER_MASK_RARE_IMAGE_PATH);
        FRAME_COMMON = ImageMaster.loadImage(FRAME_COMMON_IMAGE_PATH);
        FRAME_UNCOMMON = ImageMaster.loadImage(FRAME_UNCOMMON_IMAGE_PATH);
        FRAME_RARE = ImageMaster.loadImage(FRAME_RARE_IMAGE_PATH);
    }
}
