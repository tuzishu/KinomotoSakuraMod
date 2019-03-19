package KinomotoSakuraMod;

import KinomotoSakuraMod.Cards.ClowCard.ClowCardTheSword;
import KinomotoSakuraMod.Characters.KinomotoSakura;
import KinomotoSakuraMod.Patches.CardColorEnum;
import KinomotoSakuraMod.Patches.CharacterEnum;
import basemod.BaseMod;
import basemod.interfaces.EditCardsSubscriber;
import basemod.interfaces.EditCharactersSubscriber;
import basemod.interfaces.EditStringsSubscriber;
import basemod.interfaces.ISubscriber;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;

@SpireInitializer
public class KinomotoSakutaMod implements ISubscriber, EditCharactersSubscriber, EditCardsSubscriber, EditStringsSubscriber
{
    // Logger静态字段
    public static final Logger logger = LogManager.getLogger(KinomotoSakutaMod.class.getName());
    // CardColor卡片颜色，卡片总览中的tab按钮颜色
    public static final Color colorClowCard = CardHelper.getColor(255f, 152f, 74f);
    public static final Color colorSakuraCard = CardHelper.getColor(255f, 192f, 203f);
    public static final Color colorSpellCard = CardHelper.getColor(253f, 220f, 106f);
    // 角色图片素材路径
    private static final String CHARACTER_NAME = "KinomotoSakura";
    private static final String SELECT_BUTTON_IMAGE_PATH = "img/charSelect/MarisaButton.png";
    private static final String PORTRAIT_PATH = "img/charSelect/marisaPortrait.jpg";
    // 卡片图片素材路径
    private static final String ATTACK_BG_PATH = "img/512/attack_card.png";
    private static final String ATTACK_BG_1024_PATH = "img/1024/attack_card_1024.png";
    private static final String SKILL_BG_PATH = "img/512/skill_card.png";
    private static final String SKILL_BG_1024_PATH = "img/1024/skill_card_1024.png";
    private static final String POWER_BG_PATH = "img/512/power_card.png";
    private static final String POWER_BG_1024_PATH = "img/1024/power_card_1024.png";
    private static final String ENERGYORB_BG_PATH = "img/512/cardOrb.png";
    private static final String ENERGYORB_BG_1024_PATH = "img/1024/cardOrb.png";

    public KinomotoSakutaMod()
    {
        BaseMod.subscribe(this);
        BaseMod.addColor(CardColorEnum.CLOWCARD_COLOR, colorClowCard, colorClowCard, colorClowCard, colorClowCard, colorClowCard, colorClowCard, colorClowCard, ATTACK_BG_PATH, SKILL_BG_PATH, POWER_BG_PATH, ENERGYORB_BG_PATH, ATTACK_BG_1024_PATH, SKILL_BG_1024_PATH, POWER_BG_1024_PATH, ENERGYORB_BG_1024_PATH);
//        BaseMod.addColor(CardColorEnum.SAKURACARD_COLOR, colorSakuraCard, colorSakuraCard, colorSakuraCard, colorSakuraCard, colorSakuraCard, colorSakuraCard, colorSakuraCard, "img/512/bg_attack_MRS_s.png", "img/512/bg_skill_MRS_s.png", "img/512/bg_power_MRS_s.png", "img/512/cardOrb.png", "img/1024/bg_attack_MRS.png", "img/1024/bg_skill_MRS.png", "img/1024/bg_power_MRS.png", "img/1024/cardOrb.png");
//        BaseMod.addColor(CardColorEnum.SPELL_COLOR, colorSpellCard, colorSpellCard, colorSpellCard, colorSpellCard, colorSpellCard, colorSpellCard, colorSpellCard, "img/512/bg_attack_MRS_s.png", "img/512/bg_skill_MRS_s.png", "img/512/bg_power_MRS_s.png", "img/512/cardOrb.png", "img/1024/bg_attack_MRS.png", "img/1024/bg_skill_MRS.png", "img/1024/bg_power_MRS.png", "img/1024/cardOrb.png");
    }

    public static void initialize()
    {
        logger.info("start KinomotoSakuraMod initialize");

        new KinomotoSakutaMod();

        logger.info("done KinomotoSakuraMod initialize");
    }

    public void receiveEditCharacters()
    {
        logger.info("start editing characters");

        BaseMod.addCharacter(new KinomotoSakura(CHARACTER_NAME), SELECT_BUTTON_IMAGE_PATH, PORTRAIT_PATH, CharacterEnum.KINOMOTOSAKURA);

        logger.info("start editing characters");
    }

    public void receiveEditCards()
    {
        logger.info("start editing cards");

        BaseMod.addCard(new ClowCardTheSword());
        UnlockTracker.unlockCard(ClowCardTheSword.class.getSimpleName());

        KinomotoSakutaMod.logger.info("done editing cards");
    }

    public void receiveEditStrings()
    {
        logger.info("start editing strings");

        String path = "localization/";
//        switch (Settings.language)
//        {
//            case ZHS:
//                logger.info("language == zhs");
                path += "zhs/";
//                break;
//            default:
//                logger.info("language == eng");
//                path += "eng/";
//                break;
//        }
        String card = path + "card.json";
        String cardStrings = Gdx.files.internal(card).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(CardStrings.class, cardStrings);

//        String relic = path + "relic.json";
//        String relicStrings = Gdx.files.internal(relic).readString(String.valueOf(StandardCharsets.UTF_8));
//        BaseMod.loadCustomStrings(RelicStrings.class, relicStrings);

        String power = path + "power.json";
        String powerStrings = Gdx.files.internal(power).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(PowerStrings.class, powerStrings);

//        String potion = path + "potion.json";
//        String potionStrings = Gdx.files.internal(potion).readString(String.valueOf(StandardCharsets.UTF_8));
//        BaseMod.loadCustomStrings(PotionStrings.class, potionStrings);

        logger.info("done editing strings");
    }
}
