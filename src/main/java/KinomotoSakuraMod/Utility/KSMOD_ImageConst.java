package KinomotoSakuraMod.Utility;

import com.badlogic.gdx.graphics.Texture;
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
    public static final String BANNER_SAKURACARD_COMMON_PATH = "img/cardui/sakuracard/banner/common.png";
    public static final String BANNER_SAKURACARD_COMMON_LARGE_PATH = "img/cardui/sakuracard/banner/common_p.png";
    public static final String BANNER_SAKURACARD_UNCOMMON_PATH = "img/cardui/sakuracard/banner/uncommon.png";
    public static final String BANNER_SAKURACARD_UNCOMMON_LARGE_PATH = "img/cardui/sakuracard/banner/uncommon_p.png";
    public static final String BANNER_SAKURACARD_RARE_PATH = "img/cardui/sakuracard/banner/rare.png";
    public static final String BANNER_SAKURACARD_RARE_LARGE_PATH = "img/cardui/sakuracard/banner/rare_p.png";
    // 卡牌稀有框
    public static final String FRAME_CLOWCARD_COMMON_PATH = "img/cardui/clowcard/frame/common.png";
    public static final String FRAME_CLOWCARD_COMMON_LARGE_PATH = "img/cardui/clowcard/frame/common_p.png";
    public static final String FRAME_CLOWCARD_UNCOMMON_PATH = "img/cardui/clowcard/frame/uncommon.png";
    public static final String FRAME_CLOWCARD_UNCOMMON_LARGE_PATH = "img/cardui/clowcard/frame/uncommon_p.png";
    public static final String FRAME_CLOWCARD_RARE_PATH = "img/cardui/clowcard/frame/rare.png";
    public static final String FRAME_CLOWCARD_RARE_LARGE_PATH = "img/cardui/clowcard/frame/rare_p.png";
    public static final String FRAME_SAKURACARD_COMMON_PATH = "img/cardui/sakuracard/frame/common.png";
    public static final String FRAME_SAKURACARD_COMMON_LARGE_PATH = "img/cardui/sakuracard/frame/common_p.png";
    public static final String FRAME_SAKURACARD_UNCOMMON_PATH = "img/cardui/sakuracard/frame/uncommon.png";
    public static final String FRAME_SAKURACARD_UNCOMMON_LARGE_PATH = "img/cardui/sakuracard/frame/uncommon_p.png";
    public static final String FRAME_SAKURACARD_RARE_PATH = "img/cardui/sakuracard/frame/rare.png";
    public static final String FRAME_SAKURACARD_RARE_LARGE_PATH = "img/cardui/sakuracard/frame/rare_p.png";
    // 卡牌描述遮罩
    public static final String MASK_PATH = "img/cardui/general/mask/mask.png";
    // 卡牌轮廓
    public static final String SILHOUETTE_PATH = "img/cardui/general/silhouette/silhouette.png";
    // 卡牌闪光效果
    public static final String FLASH_PATH = "img/cardui/general/flash/flash.png";
    // 角色图片素材路径
    public static final String SELECT_BUTTON_PATH = "img/charSelect/MarisaButton.png";
    public static final String PORTRAIT_PATH = "img/charSelect/marisaPortrait.jpg";

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
    public static final Texture BANNER_SAKURACARD_COMMON;
    public static final Texture BANNER_SAKURACARD_COMMON_LARGE;
    public static final Texture BANNER_SAKURACARD_UNCOMMON;
    public static final Texture BANNER_SAKURACARD_UNCOMMON_LARGE;
    public static final Texture BANNER_SAKURACARD_RARE;
    public static final Texture BANNER_SAKURACARD_RARE_LARGE;
    // 卡牌稀有框
    public static final Texture FRAME_CLOWCARD_COMMON;
    public static final Texture FRAME_CLOWCARD_COMMON_LARGE;
    public static final Texture FRAME_CLOWCARD_UNCOMMON;
    public static final Texture FRAME_CLOWCARD_UNCOMMON_LARGE;
    public static final Texture FRAME_CLOWCARD_RARE;
    public static final Texture FRAME_CLOWCARD_RARE_LARGE;
    public static final Texture FRAME_SAKURACARD_COMMON;
    public static final Texture FRAME_SAKURACARD_COMMON_LARGE;
    public static final Texture FRAME_SAKURACARD_UNCOMMON;
    public static final Texture FRAME_SAKURACARD_UNCOMMON_LARGE;
    public static final Texture FRAME_SAKURACARD_RARE;
    public static final Texture FRAME_SAKURACARD_RARE_LARGE;
    // 卡牌描述遮罩
    public static final Texture MASK;
    // 卡牌轮廓
    public static final Texture SILHOUETTE;
    // 卡牌闪光效果
    public static final Texture FLASH;

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
        BANNER_SAKURACARD_COMMON = ImageMaster.loadImage(BANNER_SAKURACARD_COMMON_PATH);
        BANNER_SAKURACARD_COMMON_LARGE = ImageMaster.loadImage(BANNER_SAKURACARD_COMMON_LARGE_PATH);
        BANNER_SAKURACARD_UNCOMMON = ImageMaster.loadImage(BANNER_SAKURACARD_UNCOMMON_PATH);
        BANNER_SAKURACARD_UNCOMMON_LARGE = ImageMaster.loadImage(BANNER_SAKURACARD_UNCOMMON_LARGE_PATH);
        BANNER_SAKURACARD_RARE = ImageMaster.loadImage(BANNER_SAKURACARD_RARE_PATH);
        BANNER_SAKURACARD_RARE_LARGE = ImageMaster.loadImage(BANNER_SAKURACARD_RARE_LARGE_PATH);
        // 卡牌稀有框
        FRAME_CLOWCARD_COMMON = ImageMaster.loadImage(FRAME_CLOWCARD_COMMON_PATH);
        FRAME_CLOWCARD_COMMON_LARGE = ImageMaster.loadImage(FRAME_CLOWCARD_COMMON_LARGE_PATH);
        FRAME_CLOWCARD_UNCOMMON = ImageMaster.loadImage(FRAME_CLOWCARD_UNCOMMON_PATH);
        FRAME_CLOWCARD_UNCOMMON_LARGE = ImageMaster.loadImage(FRAME_CLOWCARD_UNCOMMON_LARGE_PATH);
        FRAME_CLOWCARD_RARE = ImageMaster.loadImage(FRAME_CLOWCARD_RARE_PATH);
        FRAME_CLOWCARD_RARE_LARGE = ImageMaster.loadImage(FRAME_CLOWCARD_RARE_LARGE_PATH);
        FRAME_SAKURACARD_COMMON = ImageMaster.loadImage(FRAME_SAKURACARD_COMMON_PATH);
        FRAME_SAKURACARD_COMMON_LARGE = ImageMaster.loadImage(FRAME_SAKURACARD_COMMON_LARGE_PATH);
        FRAME_SAKURACARD_UNCOMMON = ImageMaster.loadImage(FRAME_SAKURACARD_UNCOMMON_PATH);
        FRAME_SAKURACARD_UNCOMMON_LARGE = ImageMaster.loadImage(FRAME_SAKURACARD_UNCOMMON_LARGE_PATH);
        FRAME_SAKURACARD_RARE = ImageMaster.loadImage(FRAME_SAKURACARD_RARE_PATH);
        FRAME_SAKURACARD_RARE_LARGE = ImageMaster.loadImage(FRAME_SAKURACARD_RARE_LARGE_PATH);
        // 卡牌描述遮罩
        MASK = ImageMaster.loadImage(MASK_PATH);
        // 卡牌轮廓
        SILHOUETTE = ImageMaster.loadImage(SILHOUETTE_PATH);
        // 卡牌闪光效果
        FLASH = ImageMaster.loadImage(FLASH_PATH);
    }
}
