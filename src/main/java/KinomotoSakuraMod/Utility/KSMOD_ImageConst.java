package KinomotoSakuraMod.Utility;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class KSMOD_ImageConst
{
    //////////
    // 资源路径
    //////////
    // 卡牌背景
    public static final String CLOWCARD_BG_PATH = "img/cardui/clowcard/bg/bg.png";
    public static final String CLOWCARD_BG_LARGE_PATH = "img/cardui/clowcard/bg/bg_p.png";
    public static final String SAKURACARD_BG_PATH = "img/cardui/sakuracard/bg/bg.png";
    public static final String SAKURACARD_BG_LARGE_PATH = "img/cardui/sakuracard/bg/bg_p.png";
    public static final String SPELLCARD_BG_PATH = "img/cardui/spellcard/bg/bg.png";
    public static final String SPELLCARD_BG_LARGE_PATH = "img/cardui/spellcard/bg/bg_p.png";
    // 卡牌能量标识
    public static final String ORB_CLOWCARD_PATH = "img/cardui/clowcard/orb/orb.png";
    public static final String ORB_CLOWCARD_LARGE_PATH = "img/cardui/clowcard/orb/orb_p.png";
    public static final String ORB_SAKURACARD_PATH = "img/cardui/sakuracard/orb/orb.png";
    public static final String ORB_SAKURACARD_LARGE_PATH = "img/cardui/sakuracard/orb/orb_p.png";
    public static final String ORB_SPELLCARD_PATH = "img/cardui/spellcard/orb/orb.png";
    public static final String ORB_SPELLCARD_LARGE_PATH = "img/cardui/spellcard/orb/orb_p.png";
    // 卡牌稀有旗
    public static final String BANNER_CLOWCARD_COMMON_PATH = "img/cardui/clowcard/banner/common.png";
    public static final String BANNER_CLOWCARD_COMMON_LARGE_PATH = "img/cardui/clowcard/banner/common_p.png";
    public static final String BANNER_CLOWCARD_UNCOMMON_PATH = "img/cardui/clowcard/banner/uncommon.png";
    public static final String BANNER_CLOWCARD_UNCOMMON_LARGE_PATH = "img/cardui/clowcard/banner/uncommon_p.png";
    public static final String BANNER_CLOWCARD_RARE_PATH = "img/cardui/clowcard/banner/rare.png";
    public static final String BANNER_CLOWCARD_RARE_LARGE_PATH = "img/cardui/clowcard/banner/rare_p.png";
    public static final String BANNER_SAKURACARD_PATH = "img/cardui/sakuracard/banner/banner.png";
    public static final String BANNER_SAKURACARD_LARGE_PATH = "img/cardui/sakuracard/banner/banner_p.png";
    public static final String BANNER_SPELLCARD_PATH = "img/cardui/spellcard/banner/banner.png";
    public static final String BANNER_SPELLCARD_LARGE_PATH = "img/cardui/spellcard/banner/banner_p.png";
    public static final String BANNER_EMPTY_PATH = "img/cardui/general/empty/empty.png";
    public static final String BANNER_EMPTY_LARGE_PATH = "img/cardui/general/empty/empty_p.png";
    // 卡牌稀有框
    public static final String FRAME_CLOWCARD_COMMON_PATH = "img/cardui/clowcard/frame/common.png";
    public static final String FRAME_CLOWCARD_COMMON_LARGE_PATH = "img/cardui/clowcard/frame/common_p.png";
    public static final String FRAME_CLOWCARD_UNCOMMON_PATH = "img/cardui/clowcard/frame/uncommon.png";
    public static final String FRAME_CLOWCARD_UNCOMMON_LARGE_PATH = "img/cardui/clowcard/frame/uncommon_p.png";
    public static final String FRAME_CLOWCARD_RARE_PATH = "img/cardui/clowcard/frame/rare.png";
    public static final String FRAME_CLOWCARD_RARE_LARGE_PATH = "img/cardui/clowcard/frame/rare_p.png";
    public static final String FRAME_SAKURACARD_PATH = "img/cardui/sakuracard/frame/frame.png";
    public static final String FRAME_SAKURACARD_LARGE_PATH = "img/cardui/sakuracard/frame/frame_p.png";
    public static final String FRAME_SPELLCARD_PATH = "img/cardui/spellcard/frame/frame.png";
    public static final String FRAME_SPELLCARD_LARGE_PATH = "img/cardui/spellcard/frame/frame_p.png";
    public static final String FRAME_EMPTY_PATH = "img/cardui/general/empty/empty.png";
    public static final String FRAME_EMPTY_LARGE_PATH = "img/cardui/general/empty/empty_p.png";
    // 卡牌描述遮罩
    public static final String MASK_ATTACK_PATH = "img/cardui/general/mask/attack_mask.png";
    public static final String MASK_POWER_PATH = "img/cardui/general/mask/power_mask.png";
    public static final String MASK_SKILL_PATH = "img/cardui/general/mask/skill_mask.png";
    // 卡牌轮廓
    public static final String SILHOUETTE_PATH = "img/cardui/general/silhouette/silhouette.png";
    // 卡牌闪光效果
    public static final String FLASH_PATH = "img/cardui/general/flash/flash.png";
    // 角色图片素材路径
    public static final String SELECT_BUTTON_PATH = "img/charSelect/button.png";
    public static final String PORTRAIT_PATH = "img/charSelect/portrait.png";
    // 能量栏
    public static final String[] ORB_TEXTURES = {
            "img/UI/energypanel/layer_5.png",
            "img/UI/energypanel/layer_4.png",
            "img/UI/energypanel/layer_3.png",
            "img/UI/energypanel/layer_2.png",
            "img/UI/energypanel/layer_1.png",
            "img/UI/energypanel/layer_0.png",
            "img/UI/energypanel/layer_5_d.png",
            "img/UI/energypanel/layer_4_d.png",
            "img/UI/energypanel/layer_3_d.png",
            "img/UI/energypanel/layer_2_d.png",
            "img/UI/energypanel/layer_1_d.png"
    };
    public static final String ORB_VFX = "img/UI/energypanel/orb_vfx.png";
    // 角色资源
    public static final String IDLE_IMAGE_PATH = "img/char/Sakura/sakura_idle.png";
    public static final String CORPSE_IMAGE_PATH = "img/char/Sakura/sakura_fallen.png";
    public static final String SHOULDER_1_IMAGE_PATH = "img/char/Sakura/shoulder_empty.png";
    public static final String SHOULDER_2_IMAGE_PATH = "img/char/Sakura/shoulder_empty.png";
    // public static final String ANIMA_ATLAS_PATH = "img/char/Marisa/MarisaModelv3.atlas";
    // public static final String ANIMA_SKELETON_PATH = "img/char/Marisa/MarisaModelv3.json";

    //////////
    // 资源贴图
    //////////
    // 卡牌背景
    public static final Texture CLOWCARD_BG;
    public static final Texture CLOWCARD_BG_LARGE;
    public static final Texture SAKURACARD_BG;
    public static final Texture SAKURACARD_BG_LARGE;
    public static final Texture SPELLCARD_BG;
    public static final Texture SPELLCARD_BG_LARGE;
    // 卡牌能量标识
    public static final Texture ORB_CLOWCARD;
    public static final Texture ORB_CLOWCARD_LARGE;
    public static final Texture ORB_SAKURACARD;
    public static final Texture ORB_SAKURACARD_LARGE;
    public static final Texture ORB_SPELLCARD;
    public static final Texture ORB_SPELLCARD_LARGE;
    // 卡牌稀有旗
    public static final Texture BANNER_CLOWCARD_COMMON;
    public static final Texture BANNER_CLOWCARD_COMMON_LARGE;
    public static final Texture BANNER_CLOWCARD_UNCOMMON;
    public static final Texture BANNER_CLOWCARD_UNCOMMON_LARGE;
    public static final Texture BANNER_CLOWCARD_RARE;
    public static final Texture BANNER_CLOWCARD_RARE_LARGE;
    public static final Texture BANNER_SAKURACARD;
    public static final Texture BANNER_SAKURACARD_LARGE;
    public static final Texture BANNER_SPELLCARD;
    public static final Texture BANNER_SPELLCARD_LARGE;
    public static final Texture BANNER_EMPTY;
    public static final Texture BANNER_EMPTY_LARGE;
    // 卡牌稀有框
    public static final Texture FRAME_CLOWCARD_COMMON;
    public static final Texture FRAME_CLOWCARD_COMMON_LARGE;
    public static final Texture FRAME_CLOWCARD_UNCOMMON;
    public static final Texture FRAME_CLOWCARD_UNCOMMON_LARGE;
    public static final Texture FRAME_CLOWCARD_RARE;
    public static final Texture FRAME_CLOWCARD_RARE_LARGE;
    public static final Texture FRAME_SAKURACARD;
    public static final Texture FRAME_SAKURACARD_LARGE;
    public static final Texture FRAME_SPELLCARD;
    public static final Texture FRAME_SPELLCARD_LARGE;
    public static final Texture FRAME_EMPTY;
    public static final Texture FRAME_EMPTY_LARGE;
    // 卡牌描述遮罩
    public static final Texture MASK_ATTACK;
    public static final Texture MASK_POWER;
    public static final Texture MASK_SKILL;
    // 卡牌轮廓
    public static final Texture SILHOUETTE;
    public static final TextureAtlas.AtlasRegion SILHOUETTE_ATLAS;
    // 卡牌闪光效果
    public static final Texture FLASH;
    public static final TextureAtlas.AtlasRegion FLASH_ATLAS;

    static
    {
        // 卡牌背景
        CLOWCARD_BG = ImageMaster.loadImage(CLOWCARD_BG_PATH);
        CLOWCARD_BG_LARGE = ImageMaster.loadImage(CLOWCARD_BG_LARGE_PATH);
        SAKURACARD_BG = ImageMaster.loadImage(SAKURACARD_BG_PATH);
        SAKURACARD_BG_LARGE = ImageMaster.loadImage(SAKURACARD_BG_LARGE_PATH);
        SPELLCARD_BG = ImageMaster.loadImage(SPELLCARD_BG_PATH);
        SPELLCARD_BG_LARGE = ImageMaster.loadImage(SPELLCARD_BG_LARGE_PATH);
        // 卡牌能量标识
        ORB_CLOWCARD = ImageMaster.loadImage(ORB_CLOWCARD_PATH);
        ORB_CLOWCARD_LARGE = ImageMaster.loadImage(ORB_CLOWCARD_LARGE_PATH);
        ORB_SAKURACARD = ImageMaster.loadImage(ORB_SAKURACARD_PATH);
        ORB_SAKURACARD_LARGE = ImageMaster.loadImage(ORB_SAKURACARD_LARGE_PATH);
        ORB_SPELLCARD = ImageMaster.loadImage(ORB_SPELLCARD_PATH);
        ORB_SPELLCARD_LARGE = ImageMaster.loadImage(ORB_SPELLCARD_LARGE_PATH);
        // 卡牌稀有旗
        BANNER_CLOWCARD_COMMON = ImageMaster.loadImage(BANNER_CLOWCARD_COMMON_PATH);
        BANNER_CLOWCARD_COMMON_LARGE = ImageMaster.loadImage(BANNER_CLOWCARD_COMMON_LARGE_PATH);
        BANNER_CLOWCARD_UNCOMMON = ImageMaster.loadImage(BANNER_CLOWCARD_UNCOMMON_PATH);
        BANNER_CLOWCARD_UNCOMMON_LARGE = ImageMaster.loadImage(BANNER_CLOWCARD_UNCOMMON_LARGE_PATH);
        BANNER_CLOWCARD_RARE = ImageMaster.loadImage(BANNER_CLOWCARD_RARE_PATH);
        BANNER_CLOWCARD_RARE_LARGE = ImageMaster.loadImage(BANNER_CLOWCARD_RARE_LARGE_PATH);
        BANNER_SAKURACARD = ImageMaster.loadImage(BANNER_SAKURACARD_PATH);
        BANNER_SAKURACARD_LARGE = ImageMaster.loadImage(BANNER_SAKURACARD_LARGE_PATH);
        BANNER_SPELLCARD = ImageMaster.loadImage(BANNER_SPELLCARD_PATH);
        BANNER_SPELLCARD_LARGE = ImageMaster.loadImage(BANNER_SPELLCARD_LARGE_PATH);
        BANNER_EMPTY = ImageMaster.loadImage(BANNER_EMPTY_PATH);
        BANNER_EMPTY_LARGE = ImageMaster.loadImage(BANNER_EMPTY_LARGE_PATH);
        // 卡牌稀有框
        FRAME_CLOWCARD_COMMON = ImageMaster.loadImage(FRAME_CLOWCARD_COMMON_PATH);
        FRAME_CLOWCARD_COMMON_LARGE = ImageMaster.loadImage(FRAME_CLOWCARD_COMMON_LARGE_PATH);
        FRAME_CLOWCARD_UNCOMMON = ImageMaster.loadImage(FRAME_CLOWCARD_UNCOMMON_PATH);
        FRAME_CLOWCARD_UNCOMMON_LARGE = ImageMaster.loadImage(FRAME_CLOWCARD_UNCOMMON_LARGE_PATH);
        FRAME_CLOWCARD_RARE = ImageMaster.loadImage(FRAME_CLOWCARD_RARE_PATH);
        FRAME_CLOWCARD_RARE_LARGE = ImageMaster.loadImage(FRAME_CLOWCARD_RARE_LARGE_PATH);
        FRAME_SAKURACARD = ImageMaster.loadImage(FRAME_SAKURACARD_PATH);
        FRAME_SAKURACARD_LARGE = ImageMaster.loadImage(FRAME_SAKURACARD_LARGE_PATH);
        FRAME_SPELLCARD = ImageMaster.loadImage(FRAME_SPELLCARD_PATH);
        FRAME_SPELLCARD_LARGE = ImageMaster.loadImage(FRAME_SPELLCARD_LARGE_PATH);
        FRAME_EMPTY = ImageMaster.loadImage(FRAME_EMPTY_PATH);
        FRAME_EMPTY_LARGE = ImageMaster.loadImage(FRAME_EMPTY_LARGE_PATH);
        // 卡牌描述遮罩
        MASK_ATTACK = ImageMaster.loadImage(MASK_ATTACK_PATH);
        MASK_POWER = ImageMaster.loadImage(MASK_POWER_PATH);
        MASK_SKILL = ImageMaster.loadImage(MASK_SKILL_PATH);
        // 卡牌轮廓
        SILHOUETTE = ImageMaster.loadImage(SILHOUETTE_PATH);
        SILHOUETTE_ATLAS = KSMOD_Utility.GetAtlasRegion(SILHOUETTE);
        // 卡牌闪光效果
        FLASH = ImageMaster.loadImage(FLASH_PATH);
        FLASH_ATLAS = KSMOD_Utility.GetAtlasRegion(FLASH);
    }
}
