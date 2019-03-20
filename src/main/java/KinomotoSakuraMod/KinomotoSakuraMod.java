package KinomotoSakuraMod;

import KinomotoSakuraMod.Cards.ClowCard.ClowCardTheShield;
import KinomotoSakuraMod.Cards.ClowCard.ClowCardTheSword;
import KinomotoSakuraMod.Cards.SpellCard.SpellCardRelease;
import KinomotoSakuraMod.Cards.SpellCard.SpellCardSeal;
import KinomotoSakuraMod.Cards.SpellCard.SpellCardTurn;
import KinomotoSakuraMod.Characters.KinomotoSakura;
import KinomotoSakuraMod.Patches.CustomCardColor;
import KinomotoSakuraMod.Patches.CustomCharacter;
import KinomotoSakuraMod.Relics.SealedWand;
import basemod.BaseMod;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

@SpireInitializer
public class KinomotoSakuraMod implements ISubscriber, EditCharactersSubscriber, EditRelicsSubscriber, EditCardsSubscriber, EditStringsSubscriber
{
    // Logger静态字段
    public static final Logger logger = LogManager.getLogger(KinomotoSakuraMod.class.getName());
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
    private static final String SKILL_BG_PATH = "img/512/skill_card.png";
    private static final String POWER_BG_PATH = "img/512/power_card.png";
    private static final String ENERGYORB_BG_PATH = "img/512/card_orb.png";
    private static final String ATTACK_BG_1024_PATH = "img/1024/attack_card_1024.png";
    private static final String SKILL_BG_1024_PATH = "img/1024/skill_card_1024.png";
    private static final String POWER_BG_1024_PATH = "img/1024/power_card_1024.png";
    private static final String ENERGYORB_BG_1024_PATH = "img/1024/card_orb_1024.png";

    public KinomotoSakuraMod()
    {
        BaseMod.subscribe(this);
        BaseMod.addColor(CustomCardColor.CLOWCARD_COLOR, colorClowCard, colorClowCard, colorClowCard, colorClowCard, colorClowCard, colorClowCard, colorClowCard, ATTACK_BG_PATH, SKILL_BG_PATH, POWER_BG_PATH, ENERGYORB_BG_PATH, ATTACK_BG_1024_PATH, SKILL_BG_1024_PATH, POWER_BG_1024_PATH, ENERGYORB_BG_1024_PATH);
        BaseMod.addColor(CustomCardColor.SPELL_COLOR, colorSpellCard, colorSpellCard, colorSpellCard, colorSpellCard, colorSpellCard, colorSpellCard, colorSpellCard, ATTACK_BG_PATH, SKILL_BG_PATH, POWER_BG_PATH, ENERGYORB_BG_PATH, ATTACK_BG_1024_PATH, SKILL_BG_1024_PATH, POWER_BG_1024_PATH, ENERGYORB_BG_1024_PATH);
//        BaseMod.addColor(CustomCardColor.SAKURACARD_COLOR, colorSakuraCard, colorSakuraCard, colorSakuraCard, colorSakuraCard, colorSakuraCard, colorSakuraCard, colorSakuraCard, "img/512/bg_attack_MRS_s.png", "img/512/bg_skill_MRS_s.png", "img/512/bg_power_MRS_s.png", "img/512/card_orb_1024.png", "img/1024/bg_attack_MRS.png", "img/1024/bg_skill_MRS.png", "img/1024/bg_power_MRS.png", "img/1024/card_orb_1024.png");
//        BaseMod.addColor(CustomCardColor.SPELL_COLOR, colorSpellCard, colorSpellCard, colorSpellCard, colorSpellCard, colorSpellCard, colorSpellCard, colorSpellCard, "img/512/bg_attack_MRS_s.png", "img/512/bg_skill_MRS_s.png", "img/512/bg_power_MRS_s.png", "img/512/card_orb_1024.png", "img/1024/bg_attack_MRS.png", "img/1024/bg_skill_MRS.png", "img/1024/bg_power_MRS.png", "img/1024/card_orb_1024.png");
    }

    public static void initialize()
    {
        logger.info("开始初始化 KinomotoSakuraMod");

        new KinomotoSakuraMod();

        logger.info("完成初始化 KinomotoSakuraMod");
    }

    public void receiveEditCharacters()
    {
        logger.info("开始编辑角色");

        BaseMod.addCharacter(new KinomotoSakura(CHARACTER_NAME), SELECT_BUTTON_IMAGE_PATH, PORTRAIT_PATH, CustomCharacter.KINOMOTOSAKURA);

        logger.info("结束编辑角色");
    }

    public void receiveEditRelics()
    {
        logger.info("开始编辑遗物");

        BaseMod.addRelicToCustomPool(new SealedWand(), CustomCardColor.CLOWCARD_COLOR);

        logger.info("结束编辑遗物");
    }

    public void receiveEditCards()
    {
        logger.info("开始编辑卡牌");

        for (AbstractCard card : GetCardList())
        {
            BaseMod.addCard(card);
            UnlockTracker.unlockCard(card.cardID);
        }

        logger.info("结束编辑卡牌");
    }

    public void receiveEditStrings()
    {
        logger.info("开始编辑本地化文本");

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

        String relic = path + "relic.json";
        String relicStrings = Gdx.files.internal(relic).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(RelicStrings.class, relicStrings);

        String power = path + "power.json";
        String powerStrings = Gdx.files.internal(power).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(PowerStrings.class, powerStrings);

//        String potion = path + "potion.json";
//        String potionStrings = Gdx.files.internal(potion).readString(String.valueOf(StandardCharsets.UTF_8));
//        BaseMod.loadCustomStrings(PotionStrings.class, potionStrings);

        logger.info("结束编辑本地化文本");
    }

    private ArrayList<AbstractCard> GetCardList()
    {
        ArrayList<AbstractCard> cardList = new ArrayList<AbstractCard>();

        cardList.add(new ClowCardTheSword());
        cardList.add(new ClowCardTheShield());
        cardList.add(new SpellCardTurn());
        cardList.add(new SpellCardSeal());
        cardList.add(new SpellCardRelease());

        return cardList;
    }
}
