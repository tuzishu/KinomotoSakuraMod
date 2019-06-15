package KinomotoSakuraMod;

import KinomotoSakuraMod.Cards.ClowCard.*;
import KinomotoSakuraMod.Cards.SpellCard.SpellCardRelease;
import KinomotoSakuraMod.Cards.SpellCard.SpellCardSeal;
import KinomotoSakuraMod.Cards.SpellCard.SpellCardTurn;
import KinomotoSakuraMod.Cards.SpellCard.TestCard;
import KinomotoSakuraMod.Characters.KinomotoSakura;
import KinomotoSakuraMod.Patches.KSMOD_CustomCardColor;
import KinomotoSakuraMod.Patches.KSMOD_CustomCharacter;
import KinomotoSakuraMod.Patches.KSMOD_CustomKeywords;
import KinomotoSakuraMod.Relics.KSMOD_SealedBook;
import KinomotoSakuraMod.Relics.KSMOD_SealedWand;
import KinomotoSakuraMod.Utility.KSMOD_ImageConst;
import KinomotoSakuraMod.Utility.KSMOD_Utility;
import basemod.BaseMod;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

@SpireInitializer
public class KSMOD implements ISubscriber, PostInitializeSubscriber, EditCharactersSubscriber, EditRelicsSubscriber, EditCardsSubscriber, EditStringsSubscriber, EditKeywordsSubscriber
{
    // CardColor卡片颜色，卡片总览中的tab按钮颜色
    public static final Color colorClowCard = CardHelper.getColor(255f, 152f, 74f);
    public static final Color colorSakuraCard = CardHelper.getColor(255f, 192f, 203f);
    public static final Color colorSpellCard = CardHelper.getColor(253f, 220f, 106f);

    public KSMOD()
    {
        BaseMod.subscribe(this);
        BaseMod.addColor(KSMOD_CustomCardColor.CLOWCARD_COLOR, colorClowCard, colorClowCard, colorClowCard, colorClowCard, colorClowCard, colorClowCard, colorClowCard, KSMOD_ImageConst.CLOWCARD_BG_PATH, KSMOD_ImageConst.CLOWCARD_BG_PATH, KSMOD_ImageConst.CLOWCARD_BG_PATH, KSMOD_ImageConst.ORB_ATTACK_PATH, KSMOD_ImageConst.CLOWCARD_BG_LARGE_PATH, KSMOD_ImageConst.CLOWCARD_BG_LARGE_PATH, KSMOD_ImageConst.CLOWCARD_BG_LARGE_PATH, KSMOD_ImageConst.ORB_ATTACK_LARGE_PATH);
        BaseMod.addColor(KSMOD_CustomCardColor.SPELL_COLOR, colorSpellCard, colorSpellCard, colorSpellCard, colorSpellCard, colorSpellCard, colorSpellCard, colorSpellCard, KSMOD_ImageConst.CLOWCARD_BG_PATH, KSMOD_ImageConst.CLOWCARD_BG_PATH, KSMOD_ImageConst.CLOWCARD_BG_PATH, KSMOD_ImageConst.ORB_ATTACK_PATH, KSMOD_ImageConst.CLOWCARD_BG_LARGE_PATH, KSMOD_ImageConst.CLOWCARD_BG_LARGE_PATH, KSMOD_ImageConst.CLOWCARD_BG_LARGE_PATH, KSMOD_ImageConst.ORB_ATTACK_LARGE_PATH);
    }

    public static void initialize()
    {
        KSMOD_Utility.Logger.info("开始初始化 KSMOD");

        new KSMOD();

        KSMOD_Utility.Logger.info("完成初始化 KSMOD");
    }

    @SuppressWarnings("deprecation")
    @Override
    public void receivePostInitialize()
    {
        // Texture badgeTexture = new Texture("mod图标路径");
        // ModPanel settingsPanel = new ModPanel();
        // settingsPanel.addLabel("config里的mod描叙", 400.0f, 700.0f, (me) -> {});
        // BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);
        //
        // Settings.isDailyRun = false;
        // Settings.isTrial = false;
        // Settings.isDemo = false;
    }

    @Override
    public void receiveEditCharacters()
    {
        KSMOD_Utility.Logger.info("开始编辑角色");

        BaseMod.addCharacter(new KinomotoSakura(), KSMOD_ImageConst.SELECT_BUTTON_PATH, KSMOD_ImageConst.PORTRAIT_PATH, KSMOD_CustomCharacter.KINOMOTOSAKURA);

        KSMOD_Utility.Logger.info("结束编辑角色");
    }

    @Override
    public void receiveEditRelics()
    {
        KSMOD_Utility.Logger.info("开始编辑遗物");

        for (AbstractRelic relic : GetRelicList())
        {
            BaseMod.addRelicToCustomPool(relic, KSMOD_CustomCardColor.CLOWCARD_COLOR);
            KSMOD_Utility.Logger.info("Loading relic : " + relic.name);
        }

        KSMOD_Utility.Logger.info("结束编辑遗物");
    }

    private ArrayList<AbstractRelic> GetRelicList()
    {
        ArrayList<AbstractRelic> relicList = new ArrayList<AbstractRelic>();

        relicList.add(new KSMOD_SealedWand());
        relicList.add(new KSMOD_SealedBook());

        return relicList;
    }

    @Override
    public void receiveEditCards()
    {
        KSMOD_Utility.Logger.info("开始编辑卡牌");

        for (AbstractCard card : GetUnlockedCardList())
        {
            BaseMod.addCard(card);
            UnlockTracker.unlockCard(card.cardID);
            KSMOD_Utility.Logger.info("Loading Unlocked Card : " + card.name);
        }

        for (AbstractCard card : GetLockedCardList())
        {
            BaseMod.addCard(card);
            KSMOD_Utility.Logger.info("Loading Locked Card : " + card.name);
        }

        KSMOD_Utility.Logger.info("结束编辑卡牌");
    }

    private ArrayList<AbstractCard> GetUnlockedCardList()
    {
        ArrayList<AbstractCard> cardList = new ArrayList<AbstractCard>();

        cardList.add(new SpellCardTurn());
        cardList.add(new SpellCardSeal());
        cardList.add(new SpellCardRelease());

        cardList.add(new ClowCardTheArrow());
        cardList.add(new ClowCardTheBig());
        cardList.add(new ClowCardTheBubbles());
        cardList.add(new ClowCardTheChange());
        cardList.add(new ClowCardTheCloud());
        cardList.add(new ClowCardTheCreate());
        cardList.add(new ClowCardTheDark());
        cardList.add(new ClowCardTheDash());
        cardList.add(new ClowCardTheDream());
        cardList.add(new ClowCardTheEarthy());
        cardList.add(new ClowCardTheErase());
        cardList.add(new ClowCardTheFight());
        cardList.add(new ClowCardTheFirey());
        cardList.add(new ClowCardTheFloat());
        cardList.add(new ClowCardTheFlower());
        cardList.add(new ClowCardTheFly());
        cardList.add(new ClowCardTheFreeze());
        cardList.add(new ClowCardTheGlow());
        cardList.add(new ClowCardTheIllusion());
        cardList.add(new ClowCardTheJump());
        cardList.add(new ClowCardTheLibra());
        cardList.add(new ClowCardTheLight());
        cardList.add(new ClowCardTheLittle());
        cardList.add(new ClowCardTheLock());
        cardList.add(new ClowCardTheLoop());
        cardList.add(new ClowCardTheMaze());
        cardList.add(new ClowCardTheMirror());
        cardList.add(new ClowCardTheMist());
        cardList.add(new ClowCardTheMove());
        cardList.add(new ClowCardThePower());
        cardList.add(new ClowCardTheRain());
        cardList.add(new ClowCardTheReturn());
        cardList.add(new ClowCardTheSand());
        cardList.add(new ClowCardTheShadow());
        cardList.add(new ClowCardTheShield());
        cardList.add(new ClowCardTheShot());
        cardList.add(new ClowCardTheSilent());
        cardList.add(new ClowCardTheSleep());
        cardList.add(new ClowCardTheSnow());
        cardList.add(new ClowCardTheSong());
        cardList.add(new ClowCardTheStorm());
        cardList.add(new ClowCardTheSweet());
        cardList.add(new ClowCardTheSword());
        cardList.add(new ClowCardTheThrough());
        cardList.add(new ClowCardTheThunder());
        cardList.add(new ClowCardTheTime());
        cardList.add(new ClowCardTheTwin());
        cardList.add(new ClowCardTheVoice());
        cardList.add(new ClowCardTheWatery());
        cardList.add(new ClowCardTheWave());
        cardList.add(new ClowCardTheWindy());
        cardList.add(new ClowCardTheWood());

        cardList.add(new TestCard());

        return cardList;
    }

    private ArrayList<AbstractCard> GetLockedCardList()
    {
        ArrayList<AbstractCard> cardList = new ArrayList<AbstractCard>();

        cardList.add(new TestCard());
        return cardList;
    }

    @Override
    public void receiveEditStrings()
    {
        KSMOD_Utility.Logger.info("开始编辑本地化文本");

        String path = "localization/";
        //        switch (Settings.language)
        //        {
        //            case ZHS:
        //                KSMOD_Utility.Logger.info("language == zhs");
        path += "zhs/";
        //                break;
        //            default:
        //                KSMOD_Utility.Logger.info("language == eng");
        //                path += "eng/";
        //                break;
        //        }

        String card = path + "sakura_card.json";
        String cardStrings = Gdx.files.internal(card).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(CardStrings.class, cardStrings);

        String character = path + "sakura_character.json";
        String charStrings = Gdx.files.internal(character).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(CharacterStrings.class, charStrings);

        String power = path + "sakura_power.json";
        String powerStrings = Gdx.files.internal(power).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(PowerStrings.class, powerStrings);

        String relic = path + "sakura_relic.json";
        String relicStrings = Gdx.files.internal(relic).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(RelicStrings.class, relicStrings);

        String ui = path + "sakura_ui.json";
        String uiStrings = Gdx.files.internal(ui).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(UIStrings.class, uiStrings);

        KSMOD_Utility.Logger.info("结束编辑本地化文本");
    }

    @Override
    public void receiveEditKeywords()
    {
        KSMOD_Utility.Logger.info("开始编辑关键字");

        String path = "localization/";
        //        switch (Settings.language)
        //        {
        //            case ZHS:
        //                KSMOD_Utility.Logger.info("language == zhs");
        path += "zhs/";
        //                break;
        //            default:
        //                KSMOD_Utility.Logger.info("language == eng");
        //                path += "eng/";
        //                break;
        //        }

        path += "sakura_keyword.json";
        Gson gson = new Gson();
        String json = Gdx.files.internal(path).readString(String.valueOf(StandardCharsets.UTF_8));
        KSMOD_CustomKeywords keywords = (KSMOD_CustomKeywords) gson.fromJson(json, KSMOD_CustomKeywords.class);
        Keyword[] keywordList = keywords.keywords;

        for (int i = 0; i < keywordList.length; ++i)
        {
            Keyword key = keywordList[i];
            KSMOD_Utility.Logger.info("加载关键字：" + key.NAMES[0]);
            BaseMod.addKeyword(key.NAMES, key.DESCRIPTION);
        }

        KSMOD_Utility.Logger.info("结束编辑关键字");
    }
}
