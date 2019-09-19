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
    public static final String SPELLCARD_BG_PATH = "img/cardui/spellcard/bg/attack.png";
    public static final String SPELLCARD_BG_LARGE_PATH = "img/cardui/spellcard/bg/attack_p.png";
    // 卡牌能量标识
    public static final String ORB_CLOWCARD_ATTACK_PATH = "img/cardui/clowcard/orb/attack.png";
    public static final String ORB_CLOWCARD_ATTACK_LARGE_PATH = "img/cardui/clowcard/orb/attack_p.png";
    public static final String ORB_CLOWCARD_SKILL_PATH = "img/cardui/clowcard/orb/skill.png";
    public static final String ORB_CLOWCARD_SKILL_LARGE_PATH = "img/cardui/clowcard/orb/skill_p.png";
    public static final String ORB_CLOWCARD_POWER_PATH = "img/cardui/clowcard/orb/power.png";
    public static final String ORB_CLOWCARD_POWER_LARGE_PATH = "img/cardui/clowcard/orb/power_p.png";
    public static final String ORB_SAKURACARD_ATTACK_PATH = "img/cardui/sakuracard/orb/attack.png";
    public static final String ORB_SAKURACARD_ATTACK_LARGE_PATH = "img/cardui/sakuracard/orb/attack_p.png";
    public static final String ORB_SAKURACARD_SKILL_PATH = "img/cardui/sakuracard/orb/skill.png";
    public static final String ORB_SAKURACARD_SKILL_LARGE_PATH = "img/cardui/sakuracard/orb/skill_p.png";
    public static final String ORB_SAKURACARD_POWER_PATH = "img/cardui/sakuracard/orb/power.png";
    public static final String ORB_SAKURACARD_POWER_LARGE_PATH = "img/cardui/sakuracard/orb/power_p.png";
    // 卡牌稀有旗
    public static final String BANNER_CLOWCARD_COMMON_PATH = "img/cardui/clowcard/banner/common.png";
    public static final String BANNER_CLOWCARD_COMMON_LARGE_PATH = "img/cardui/clowcard/banner/common_p.png";
    public static final String BANNER_CLOWCARD_UNCOMMON_PATH = "img/cardui/clowcard/banner/uncommon.png";
    public static final String BANNER_CLOWCARD_UNCOMMON_LARGE_PATH = "img/cardui/clowcard/banner/uncommon_p.png";
    public static final String BANNER_CLOWCARD_RARE_PATH = "img/cardui/clowcard/banner/rare.png";
    public static final String BANNER_CLOWCARD_RARE_LARGE_PATH = "img/cardui/clowcard/banner/rare_p.png";
    public static final String BANNER_SAKURACARD_PATH = "img/cardui/sakuracard/banner/banner.png";
    public static final String BANNER_SAKURACARD_LARGE_PATH = "img/cardui/sakuracard/banner/banner_p.png";
    // 卡牌稀有框
    public static final String FRAME_CLOWCARD_COMMON_PATH = "img/cardui/clowcard/frame/common.png";
    public static final String FRAME_CLOWCARD_COMMON_LARGE_PATH = "img/cardui/clowcard/frame/common_p.png";
    public static final String FRAME_CLOWCARD_UNCOMMON_PATH = "img/cardui/clowcard/frame/uncommon.png";
    public static final String FRAME_CLOWCARD_UNCOMMON_LARGE_PATH = "img/cardui/clowcard/frame/uncommon_p.png";
    public static final String FRAME_CLOWCARD_RARE_PATH = "img/cardui/clowcard/frame/rare.png";
    public static final String FRAME_CLOWCARD_RARE_LARGE_PATH = "img/cardui/clowcard/frame/rare_p.png";
    public static final String FRAME_SAKURACARD_PATH = "img/cardui/sakuracard/frame/frame.png";
    public static final String FRAME_SAKURACARD_LARGE_PATH = "img/cardui/sakuracard/frame/frame_p.png";
    // 卡牌描述遮罩
    public static final String MASK_PATH = "img/cardui/general/mask/mask.png";
    // 卡牌轮廓
    public static final String SILHOUETTE_PATH = "img/cardui/general/silhouette/silhouette.png";
    // 卡牌闪光效果
    public static final String FLASH_PATH = "img/cardui/general/flash/flash.png";
    // 角色图片素材路径
    public static final String SELECT_BUTTON_PATH = "img/charSelect/button.png";
    public static final String PORTRAIT_PATH = "img/charSelect/portrait.png";
    // 能量栏
    public static final String[] ORB_TEXTURES = {
            "img/UI/EPanel/layer5.png",
            "img/UI/EPanel/layer4.png",
            "img/UI/EPanel/layer3.png",
            "img/UI/EPanel/layer2.png",
            "img/UI/EPanel/layer1.png",
            "img/UI/EPanel/layer0.png",
            "img/UI/EPanel/layer5d.png",
            "img/UI/EPanel/layer4d.png",
            "img/UI/EPanel/layer3d.png",
            "img/UI/EPanel/layer2d.png",
            "img/UI/EPanel/layer1d.png"
    };
    public static final String ORB_VFX = "img/UI/energyBlueVFX.png";
    // 角色资源
    public static final String IDLE_IMAGE_PATH = "img/char/Sakura/sakura_idle_static.png";
    public static final String CORPSE_IMAGE_PATH = "img/char/Marisa/fallen.png";
    public static final String SHOULDER_1_IMAGE_PATH = "img/char/Marisa/shoulder1.png";
    public static final String SHOULDER_2_IMAGE_PATH = "img/char/Marisa/shoulder2.png";
    public static final String ANIMA_ATLAS_PATH = "img/char/Marisa/MarisaModelv3.atlas";
    public static final String ANIMA_SKELETON_PATH = "img/char/Marisa/MarisaModelv3.json";

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
    public static final Texture ORB_CLOWCARD_ATTACK;
    public static final Texture ORB_CLOWCARD_ATTACK_LARGE;
    public static final Texture ORB_CLOWCARD_SKILL;
    public static final Texture ORB_CLOWCARD_SKILL_LARGE;
    public static final Texture ORB_CLOWCARD_POWER;
    public static final Texture ORB_CLOWCARD_POWER_LARGE;
    public static final Texture ORB_SAKURACARD_ATTACK;
    public static final Texture ORB_SAKURACARD_ATTACK_LARGE;
    public static final Texture ORB_SAKURACARD_SKILL;
    public static final Texture ORB_SAKURACARD_SKILL_LARGE;
    public static final Texture ORB_SAKURACARD_POWER;
    public static final Texture ORB_SAKURACARD_POWER_LARGE;
    // 卡牌稀有旗
    public static final Texture BANNER_CLOWCARD_COMMON;
    public static final Texture BANNER_CLOWCARD_COMMON_LARGE;
    public static final Texture BANNER_CLOWCARD_UNCOMMON;
    public static final Texture BANNER_CLOWCARD_UNCOMMON_LARGE;
    public static final Texture BANNER_CLOWCARD_RARE;
    public static final Texture BANNER_CLOWCARD_RARE_LARGE;
    public static final Texture BANNER_SAKURACARD;
    public static final Texture BANNER_SAKURACARD_LARGE;
    // 卡牌稀有框
    public static final Texture FRAME_CLOWCARD_COMMON;
    public static final Texture FRAME_CLOWCARD_COMMON_LARGE;
    public static final Texture FRAME_CLOWCARD_UNCOMMON;
    public static final Texture FRAME_CLOWCARD_UNCOMMON_LARGE;
    public static final Texture FRAME_CLOWCARD_RARE;
    public static final Texture FRAME_CLOWCARD_RARE_LARGE;
    public static final Texture FRAME_SAKURACARD;
    public static final Texture FRAME_SAKURACARD_LARGE;
    // 卡牌描述遮罩
    public static final Texture MASK;
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
        ORB_CLOWCARD_ATTACK = ImageMaster.loadImage(ORB_CLOWCARD_ATTACK_PATH);
        ORB_CLOWCARD_ATTACK_LARGE = ImageMaster.loadImage(ORB_CLOWCARD_ATTACK_LARGE_PATH);
        ORB_CLOWCARD_SKILL = ImageMaster.loadImage(ORB_CLOWCARD_SKILL_PATH);
        ORB_CLOWCARD_SKILL_LARGE = ImageMaster.loadImage(ORB_CLOWCARD_SKILL_LARGE_PATH);
        ORB_CLOWCARD_POWER = ImageMaster.loadImage(ORB_CLOWCARD_POWER_PATH);
        ORB_CLOWCARD_POWER_LARGE = ImageMaster.loadImage(ORB_CLOWCARD_POWER_LARGE_PATH);
        ORB_SAKURACARD_ATTACK = ImageMaster.loadImage(ORB_SAKURACARD_ATTACK_PATH);
        ORB_SAKURACARD_ATTACK_LARGE = ImageMaster.loadImage(ORB_SAKURACARD_ATTACK_LARGE_PATH);
        ORB_SAKURACARD_SKILL = ImageMaster.loadImage(ORB_SAKURACARD_SKILL_PATH);
        ORB_SAKURACARD_SKILL_LARGE = ImageMaster.loadImage(ORB_SAKURACARD_SKILL_LARGE_PATH);
        ORB_SAKURACARD_POWER = ImageMaster.loadImage(ORB_SAKURACARD_POWER_PATH);
        ORB_SAKURACARD_POWER_LARGE = ImageMaster.loadImage(ORB_SAKURACARD_POWER_LARGE_PATH);
        // 卡牌稀有旗
        BANNER_CLOWCARD_COMMON = ImageMaster.loadImage(BANNER_CLOWCARD_COMMON_PATH);
        BANNER_CLOWCARD_COMMON_LARGE = ImageMaster.loadImage(BANNER_CLOWCARD_COMMON_LARGE_PATH);
        BANNER_CLOWCARD_UNCOMMON = ImageMaster.loadImage(BANNER_CLOWCARD_UNCOMMON_PATH);
        BANNER_CLOWCARD_UNCOMMON_LARGE = ImageMaster.loadImage(BANNER_CLOWCARD_UNCOMMON_LARGE_PATH);
        BANNER_CLOWCARD_RARE = ImageMaster.loadImage(BANNER_CLOWCARD_RARE_PATH);
        BANNER_CLOWCARD_RARE_LARGE = ImageMaster.loadImage(BANNER_CLOWCARD_RARE_LARGE_PATH);
        BANNER_SAKURACARD = ImageMaster.loadImage(BANNER_SAKURACARD_PATH);
        BANNER_SAKURACARD_LARGE = ImageMaster.loadImage(BANNER_SAKURACARD_LARGE_PATH);
        // 卡牌稀有框
        FRAME_CLOWCARD_COMMON = ImageMaster.loadImage(FRAME_CLOWCARD_COMMON_PATH);
        FRAME_CLOWCARD_COMMON_LARGE = ImageMaster.loadImage(FRAME_CLOWCARD_COMMON_LARGE_PATH);
        FRAME_CLOWCARD_UNCOMMON = ImageMaster.loadImage(FRAME_CLOWCARD_UNCOMMON_PATH);
        FRAME_CLOWCARD_UNCOMMON_LARGE = ImageMaster.loadImage(FRAME_CLOWCARD_UNCOMMON_LARGE_PATH);
        FRAME_CLOWCARD_RARE = ImageMaster.loadImage(FRAME_CLOWCARD_RARE_PATH);
        FRAME_CLOWCARD_RARE_LARGE = ImageMaster.loadImage(FRAME_CLOWCARD_RARE_LARGE_PATH);
        FRAME_SAKURACARD = ImageMaster.loadImage(FRAME_SAKURACARD_PATH);
        FRAME_SAKURACARD_LARGE = ImageMaster.loadImage(FRAME_SAKURACARD_LARGE_PATH);
        // 卡牌描述遮罩
        MASK = ImageMaster.loadImage(MASK_PATH);
        // 卡牌轮廓
        SILHOUETTE = ImageMaster.loadImage(SILHOUETTE_PATH);
        SILHOUETTE_ATLAS = new TextureAtlas.AtlasRegion(SILHOUETTE, 0, 0, SILHOUETTE.getWidth(), SILHOUETTE.getHeight());
        // 卡牌闪光效果
        FLASH = ImageMaster.loadImage(FLASH_PATH);
        FLASH_ATLAS = new TextureAtlas.AtlasRegion(FLASH, 0, 0, FLASH.getWidth(), FLASH.getHeight());
    }
}
