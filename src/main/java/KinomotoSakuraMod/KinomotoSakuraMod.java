package KinomotoSakuraMod;

import KinomotoSakuraMod.Cards.ClowCard.ClowCardThePower;
import KinomotoSakuraMod.Cards.ClowCard.ClowCardTheShield;
import KinomotoSakuraMod.Cards.ClowCard.ClowCardTheShoot;
import KinomotoSakuraMod.Cards.ClowCard.ClowCardTheSword;
import KinomotoSakuraMod.Cards.SpellCard.SpellCardRelease;
import KinomotoSakuraMod.Cards.SpellCard.SpellCardSeal;
import KinomotoSakuraMod.Cards.SpellCard.SpellCardTurn;
import KinomotoSakuraMod.Characters.KinomotoSakura;
import KinomotoSakuraMod.Patches.CustomCardColor;
import KinomotoSakuraMod.Patches.CustomCharacter;
import KinomotoSakuraMod.Relics.SealedWand;
import KinomotoSakuraMod.Utility.ModLogger;
import basemod.BaseMod;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.CharacterStrings;
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
    // CardColor卡片颜色，卡片总览中的tab按钮颜色
    public static final Color colorClowCard = CardHelper.getColor(255f, 152f, 74f);
    public static final Color colorSakuraCard = CardHelper.getColor(255f, 192f, 203f);
    public static final Color colorSpellCard = CardHelper.getColor(253f, 220f, 106f);
    // 角色图片素材路径
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
    }

    public static void initialize()
    {
        ModLogger.logger.info("开始初始化 KinomotoSakuraMod");

        new KinomotoSakuraMod();

        ModLogger.logger.info("完成初始化 KinomotoSakuraMod");
    }

    public void receiveEditCharacters()
    {
        ModLogger.logger.info("开始编辑角色");

        BaseMod.addCharacter(new KinomotoSakura(), SELECT_BUTTON_IMAGE_PATH, PORTRAIT_PATH, CustomCharacter.KINOMOTOSAKURA);

        ModLogger.logger.info("结束编辑角色");
    }

    public void receiveEditRelics()
    {
        ModLogger.logger.info("开始编辑遗物");

        BaseMod.addRelicToCustomPool(new SealedWand(), CustomCardColor.CLOWCARD_COLOR);

        ModLogger.logger.info("结束编辑遗物");
    }

    public void receiveEditCards()
    {
        ModLogger.logger.info("开始编辑卡牌");

        for (AbstractCard card : GetCardList())
        {
            BaseMod.addCard(card);
            UnlockTracker.unlockCard(card.cardID);
        }

        ModLogger.logger.info("结束编辑卡牌");
    }

    private ArrayList<AbstractCard> GetCardList()
    {
        ArrayList<AbstractCard> cardList = new ArrayList<AbstractCard>();

        cardList.add(new ClowCardTheSword());
        cardList.add(new ClowCardTheShield());
        cardList.add(new SpellCardTurn());
        cardList.add(new SpellCardSeal());
        cardList.add(new SpellCardRelease());
        cardList.add(new ClowCardThePower());
        cardList.add(new ClowCardTheShoot());

        return cardList;
    }

    public void receiveEditStrings()
    {
        ModLogger.logger.info("开始编辑本地化文本");

        String path = "localization/";
        //        switch (Settings.language)
        //        {
        //            case ZHS:
        //                ModLogger.logger.info("language == zhs");
        path += "zhs/";
        //                break;
        //            default:
        //                ModLogger.logger.info("language == eng");
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

        String character = path + "character.json";
        String charStrings = Gdx.files.internal(character).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(CharacterStrings.class, charStrings);

        //        String potion = path + "potion.json";
        //        String potionStrings = Gdx.files.internal(potion).readString(String.valueOf(StandardCharsets.UTF_8));
        //        BaseMod.loadCustomStrings(PotionStrings.class, potionStrings);

        ModLogger.logger.info("结束编辑本地化文本");
    }
}
