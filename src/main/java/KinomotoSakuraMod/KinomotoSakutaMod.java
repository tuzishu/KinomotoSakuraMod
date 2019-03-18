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
import com.megacrit.cardcrawl.core.Settings;
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
    public static final Logger logger = LogManager.getLogger(KinomotoSakutaMod.class.getName());
    private static final String SELECT_BUTTON_IMAGE_PATH = "img/charSelect/MarisaButton.png";
    private static final String PORTRAIT_PATH = "img/charSelect/marisaPortrait.jpg";
    public static final Color COLOR_CLOWCARD = CardHelper.getColor(255f, 152f, 74f);
    public static final Color COLOR_SAKURA = CardHelper.getColor(255f, 192f, 203f);
    public static final Color COLOR_SPELL = CardHelper.getColor(253f, 220f, 106f);
//    private static final String RELIC_STRING;
//    private static final String RELIC_STRING_ZH;
//    private static final String POWER_STRING;
//    private static final String POWER_STRING_ZH;
//    private static final String POTION_STRING;
//    private static final String POTION_STRING_ZH;

    public KinomotoSakutaMod()
    {
        BaseMod.subscribe(this);
        BaseMod.addColor(CardColorEnum.CLOWCARD_COLOR, COLOR_CLOWCARD, COLOR_CLOWCARD, COLOR_CLOWCARD, COLOR_CLOWCARD, COLOR_CLOWCARD, COLOR_CLOWCARD, COLOR_CLOWCARD, "img/512/bg_attack_MRS_s.png", "img/512/bg_skill_MRS_s.png", "img/512/bg_power_MRS_s.png", "img/512/cardOrb.png", "img/1024/bg_attack_MRS.png", "img/1024/bg_skill_MRS.png", "img/1024/bg_power_MRS.png", "img/1024/cardOrb.png");
        BaseMod.addColor(CardColorEnum.SAKURACARD_COLOR, COLOR_SAKURA, COLOR_SAKURA, COLOR_SAKURA, COLOR_SAKURA, COLOR_SAKURA, COLOR_SAKURA, COLOR_SAKURA, "img/512/bg_attack_MRS_s.png", "img/512/bg_skill_MRS_s.png", "img/512/bg_power_MRS_s.png", "img/512/cardOrb.png", "img/1024/bg_attack_MRS.png", "img/1024/bg_skill_MRS.png", "img/1024/bg_power_MRS.png", "img/1024/cardOrb.png");
        BaseMod.addColor(CardColorEnum.SPELL_COLOR, COLOR_SPELL, COLOR_SPELL, COLOR_SPELL, COLOR_SPELL, COLOR_SPELL, COLOR_SPELL, COLOR_SPELL, "img/512/bg_attack_MRS_s.png", "img/512/bg_skill_MRS_s.png", "img/512/bg_power_MRS_s.png", "img/512/cardOrb.png", "img/1024/bg_attack_MRS.png", "img/1024/bg_skill_MRS.png", "img/1024/bg_power_MRS.png", "img/1024/cardOrb.png");
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

        BaseMod.addCharacter(new KinomotoSakura("KinomotoSakura"), this.SELECT_BUTTON_IMAGE_PATH, this.PORTRAIT_PATH, CharacterEnum.KINOMOTOSAKURA);

        logger.info("start editing characters");
    }

    public void receiveEditCards()
    {
        logger.info("start editing cards");

        BaseMod.addCard(new ClowCardTheSword());
        UnlockTracker.unlockCard("ClowCardTheSword");

        KinomotoSakutaMod.logger.info("done editing cards");
    }

    public void receiveEditStrings()
    {
        logger.info("start editing strings");
        String path = "localization/";
        switch (Settings.language)
        {

            case ZHS:
                logger.info("lang == zhs");
                path += "zhs/";
                break;
            default:
                logger.info("lang == eng");
                path += "eng/";
                break;
        }

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
