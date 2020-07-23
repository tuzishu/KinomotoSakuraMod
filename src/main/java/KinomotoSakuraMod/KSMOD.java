package KinomotoSakuraMod;

import KinomotoSakuraMod.Cards.ClowCard.*;
import KinomotoSakuraMod.Cards.SakuraCard.*;
import KinomotoSakuraMod.Cards.SpellCard.*;
import KinomotoSakuraMod.Characters.KinomotoSakura;
import KinomotoSakuraMod.Patches.KSMOD_CustomCardColor;
import KinomotoSakuraMod.Patches.KSMOD_CustomCharacter;
import KinomotoSakuraMod.Patches.KSMOD_CustomKeywords;
import KinomotoSakuraMod.Potions.KSMOD_MagickBottle;
import KinomotoSakuraMod.Relics.*;
import KinomotoSakuraMod.Utility.KSMOD_ImageConst;
import KinomotoSakuraMod.Utility.KSMOD_LocalizeTool;
import KinomotoSakuraMod.Utility.KSMOD_LoggerTool;
import basemod.BaseMod;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

@SpireInitializer
public class KSMOD implements ISubscriber, PostInitializeSubscriber, EditCharactersSubscriber, EditRelicsSubscriber, EditCardsSubscriber, EditStringsSubscriber, EditKeywordsSubscriber, OnStartBattleSubscriber
{
    // CardColor卡片颜色，卡片总览中的tab按钮颜色
    public static final Color colorClowCard = CardHelper.getColor(255, 152, 74);
    public static final Color colorSakuraCard = CardHelper.getColor(255, 192, 203);
    public static final Color colorSpellCard = CardHelper.getColor(253, 220, 106);
    private static String localizationPath = null;

    public KSMOD()
    {
        BaseMod.subscribe(this);
        BaseMod.addColor(KSMOD_CustomCardColor.CLOWCARD_COLOR, colorClowCard, colorClowCard, colorClowCard, colorClowCard, colorClowCard, colorClowCard, colorClowCard, KSMOD_ImageConst.CLOWCARD_BG_PATH, KSMOD_ImageConst.CLOWCARD_BG_PATH, KSMOD_ImageConst.CLOWCARD_BG_PATH, KSMOD_ImageConst.ORB_CLOWCARD_PATH, KSMOD_ImageConst.CLOWCARD_BG_LARGE_PATH, KSMOD_ImageConst.CLOWCARD_BG_LARGE_PATH, KSMOD_ImageConst.CLOWCARD_BG_LARGE_PATH, KSMOD_ImageConst.ORB_CLOWCARD_LARGE_PATH);
        BaseMod.addColor(KSMOD_CustomCardColor.SAKURACARD_COLOR, colorSakuraCard, colorSakuraCard, colorSakuraCard, colorSakuraCard, colorSakuraCard, colorSakuraCard, colorSakuraCard, KSMOD_ImageConst.SAKURACARD_BG_PATH, KSMOD_ImageConst.SAKURACARD_BG_PATH, KSMOD_ImageConst.SAKURACARD_BG_PATH, KSMOD_ImageConst.ORB_SAKURACARD_PATH, KSMOD_ImageConst.SAKURACARD_BG_LARGE_PATH, KSMOD_ImageConst.SAKURACARD_BG_LARGE_PATH, KSMOD_ImageConst.SAKURACARD_BG_LARGE_PATH, KSMOD_ImageConst.ORB_SAKURACARD_LARGE_PATH);
        BaseMod.addColor(KSMOD_CustomCardColor.SPELL_COLOR, colorSpellCard, colorSpellCard, colorSpellCard, colorSpellCard, colorSpellCard, colorSpellCard, colorSpellCard, KSMOD_ImageConst.SPELLCARD_BG_PATH, KSMOD_ImageConst.SPELLCARD_BG_PATH, KSMOD_ImageConst.SPELLCARD_BG_PATH, KSMOD_ImageConst.ORB_SPELLCARD_PATH, KSMOD_ImageConst.SPELLCARD_BG_LARGE_PATH, KSMOD_ImageConst.SPELLCARD_BG_LARGE_PATH, KSMOD_ImageConst.SPELLCARD_BG_LARGE_PATH, KSMOD_ImageConst.ORB_SPELLCARD_LARGE_PATH);
    }

    public static void initialize()
    {
        KSMOD_LoggerTool.Logger.info("开始初始化 KSMOD");

        new KSMOD();

        KSMOD_LoggerTool.Logger.info("完成初始化 KSMOD");
    }

    @SuppressWarnings("deprecation")
    @Override
    public void receivePostInitialize()
    {
        receiveEditPotions();
        receiveEditEvents();
        receiveEditMonsters();
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
        KSMOD_LoggerTool.Logger.info("开始编辑角色");

        BaseMod.addCharacter(new KinomotoSakura(), KSMOD_ImageConst.SELECT_BUTTON_PATH, KSMOD_ImageConst.PORTRAIT_PATH, KSMOD_CustomCharacter.KINOMOTOSAKURA);

        KSMOD_LoggerTool.Logger.info("结束编辑角色");
    }

    @Override
    public void receiveEditRelics()
    {
        KSMOD_LoggerTool.Logger.info("开始编辑遗物");

        for (AbstractRelic relic : GetRelics())
        {
            BaseMod.addRelicToCustomPool(relic, KSMOD_CustomCardColor.CLOWCARD_COLOR);
            KSMOD_LoggerTool.Logger.info("Loading relic : " + relic.name);
        }

        KSMOD_LoggerTool.Logger.info("结束编辑遗物");
    }

    public static ArrayList<AbstractRelic> relics = null;
    public static ArrayList<AbstractRelic> GetRelics()
    {
        if (relics == null)
        {
            relics = new ArrayList<>();
            relics.add(new KSMOD_SealedWand());
            relics.add(new KSMOD_SealedBook());
            relics.add(new KSMOD_StarWand());
            relics.add(new KSMOD_UltimateWand());
            relics.add(new KSMOD_DarknessWand());
            relics.add(new KSMOD_Cerberus());
            relics.add(new KSMOD_Yue());
            relics.add(new KSMOD_TaoistSuit());
            relics.add(new KSMOD_SwordJade());
            relics.add(new KSMOD_TeddyBear());
            relics.add(new KSMOD_MoonBell());
            relics.add(new KSMOD_YukitosBentoBox());
            relics.add(new KSMOD_RollerSkates());
            relics.add(new KSMOD_Compass());
            relics.add(new KSMOD_GemBrooch());
            relics.add(new KSMOD_TouyasBicycle());
            relics.add(new KSMOD_TomoyosHeart());
        }
        return relics;
    }

    @Override
    public void receiveEditCards()
    {
        KSMOD_LoggerTool.Logger.info("开始编辑卡牌");

        ArrayList<AbstractCard> unlockedCardList = new ArrayList<>();
        unlockedCardList.addAll(GetClowCards());
        unlockedCardList.addAll(GetSpellCards());
        unlockedCardList.addAll(GetSakuraCards());
        for (AbstractCard card : unlockedCardList)
        {
            BaseMod.addCard(card);
            UnlockTracker.unlockCard(card.cardID);
            KSMOD_LoggerTool.Logger.info("Loading Unlocked Card : " + card.name);
        }

        ArrayList<AbstractCard> lockedCardList = new ArrayList<>();
        // lockedCardList.addAll(GetSakuraCards());
        for (AbstractCard card : lockedCardList)
        {
            BaseMod.addCard(card);
            KSMOD_LoggerTool.Logger.info("Loading Locked Card : " + card.name);
        }

        KSMOD_LoggerTool.Logger.info("结束编辑卡牌");
    }

    private static ArrayList<AbstractCard> clowCards = null;

    public static ArrayList<AbstractCard> GetClowCards()
    {
        if (clowCards == null)
        {
            clowCards = new ArrayList<>();
            clowCards.add(new ClowCardTheArrow());
            clowCards.add(new ClowCardTheBig());
            clowCards.add(new ClowCardTheBubbles());
            clowCards.add(new ClowCardTheChange());
            clowCards.add(new ClowCardTheCloud());
            clowCards.add(new ClowCardTheCreate());
            clowCards.add(new ClowCardTheDark());
            clowCards.add(new ClowCardTheDash());
            clowCards.add(new ClowCardTheDream());
            clowCards.add(new ClowCardTheEarthy());
            clowCards.add(new ClowCardTheErase());
            clowCards.add(new ClowCardTheFight());
            clowCards.add(new ClowCardTheFirey());
            clowCards.add(new ClowCardTheFloat());
            clowCards.add(new ClowCardTheFlower());
            clowCards.add(new ClowCardTheFly());
            clowCards.add(new ClowCardTheFreeze());
            clowCards.add(new ClowCardTheGlow());
            clowCards.add(new ClowCardTheIllusion());
            clowCards.add(new ClowCardTheJump());
            clowCards.add(new ClowCardTheLibra());
            clowCards.add(new ClowCardTheLight());
            clowCards.add(new ClowCardTheLittle());
            clowCards.add(new ClowCardTheLock());
            clowCards.add(new ClowCardTheLoop());
            clowCards.add(new ClowCardTheMaze());
            clowCards.add(new ClowCardTheMirror());
            clowCards.add(new ClowCardTheMist());
            clowCards.add(new ClowCardTheMove());
            clowCards.add(new ClowCardThePower());
            clowCards.add(new ClowCardTheRain());
            clowCards.add(new ClowCardTheReturn());
            clowCards.add(new ClowCardTheSand());
            clowCards.add(new ClowCardTheShadow());
            clowCards.add(new ClowCardTheShield());
            clowCards.add(new ClowCardTheShot());
            clowCards.add(new ClowCardTheSilent());
            clowCards.add(new ClowCardTheSleep());
            clowCards.add(new ClowCardTheSnow());
            clowCards.add(new ClowCardTheSong());
            clowCards.add(new ClowCardTheStorm());
            clowCards.add(new ClowCardTheSweet());
            clowCards.add(new ClowCardTheSword());
            clowCards.add(new ClowCardTheThrough());
            clowCards.add(new ClowCardTheThunder());
            clowCards.add(new ClowCardTheTime());
            clowCards.add(new ClowCardTheTwin());
            clowCards.add(new ClowCardTheVoice());
            clowCards.add(new ClowCardTheWatery());
            clowCards.add(new ClowCardTheWave());
            clowCards.add(new ClowCardTheWindy());
            clowCards.add(new ClowCardTheWood());
        }
        return clowCards;
    }

    private static ArrayList<AbstractCard> spellCards = null;

    public static ArrayList<AbstractCard> GetSpellCards()
    {
        if (spellCards == null)
        {
            spellCards = new ArrayList<>();
            spellCards.add(new SpellCardRelease());
            spellCards.add(new SpellCardSeal());
            spellCards.add(new SpellCardTurn());
            spellCards.add(new SpellCardEmptySpell());
            spellCards.add(new SpellCardHuoShen());
            spellCards.add(new SpellCardLeiDi());
            spellCards.add(new SpellCardFengHua());
            spellCards.add(new SpellCardShuiLong());

            // spellCards.add(new TestCard());
        }
        return spellCards;
    }

    private static ArrayList<AbstractCard> sakuraCards = null;

    public static ArrayList<AbstractCard> GetSakuraCards()
    {
        if (sakuraCards == null)
        {
            sakuraCards = new ArrayList<>();
            sakuraCards.add(new SakuraCardTheArrow());
            sakuraCards.add(new SakuraCardTheBig());
            sakuraCards.add(new SakuraCardTheBubbles());
            sakuraCards.add(new SakuraCardTheChange());
            sakuraCards.add(new SakuraCardTheCloud());
            sakuraCards.add(new SakuraCardTheCreate());
            sakuraCards.add(new SakuraCardTheDark());
            sakuraCards.add(new SakuraCardTheDash());
            sakuraCards.add(new SakuraCardTheDream());
            sakuraCards.add(new SakuraCardTheEarthy());
            sakuraCards.add(new SakuraCardTheErase());
            sakuraCards.add(new SakuraCardTheFight());
            sakuraCards.add(new SakuraCardTheFirey());
            sakuraCards.add(new SakuraCardTheFloat());
            sakuraCards.add(new SakuraCardTheFlower());
            sakuraCards.add(new SakuraCardTheFly());
            sakuraCards.add(new SakuraCardTheFreeze());
            sakuraCards.add(new SakuraCardTheGlow());
            sakuraCards.add(new SakuraCardTheIllusion());
            sakuraCards.add(new SakuraCardTheJump());
            sakuraCards.add(new SakuraCardTheLibra());
            sakuraCards.add(new SakuraCardTheLight());
            sakuraCards.add(new SakuraCardTheLittle());
            sakuraCards.add(new SakuraCardTheLock());
            sakuraCards.add(new SakuraCardTheLoop());
            sakuraCards.add(new SakuraCardTheMaze());
            sakuraCards.add(new SakuraCardTheMirror());
            sakuraCards.add(new SakuraCardTheMist());
            sakuraCards.add(new SakuraCardTheMove());
            sakuraCards.add(new SakuraCardThePower());
            sakuraCards.add(new SakuraCardTheRain());
            sakuraCards.add(new SakuraCardTheReturn());
            sakuraCards.add(new SakuraCardTheSand());
            sakuraCards.add(new SakuraCardTheShadow());
            sakuraCards.add(new SakuraCardTheShield());
            sakuraCards.add(new SakuraCardTheShot());
            sakuraCards.add(new SakuraCardTheSilent());
            sakuraCards.add(new SakuraCardTheSleep());
            sakuraCards.add(new SakuraCardTheSnow());
            sakuraCards.add(new SakuraCardTheSong());
            sakuraCards.add(new SakuraCardTheStorm());
            sakuraCards.add(new SakuraCardTheSweet());
            sakuraCards.add(new SakuraCardTheSword());
            sakuraCards.add(new SakuraCardTheThrough());
            sakuraCards.add(new SakuraCardTheThunder());
            sakuraCards.add(new SakuraCardTheTime());
            sakuraCards.add(new SakuraCardTheTwin());
            sakuraCards.add(new SakuraCardTheVoice());
            sakuraCards.add(new SakuraCardTheWatery());
            sakuraCards.add(new SakuraCardTheWave());
            sakuraCards.add(new SakuraCardTheWindy());
            sakuraCards.add(new SakuraCardTheWood());
        }
        return sakuraCards;
    }

    public void receiveEditPotions()
    {
        KSMOD_LoggerTool.Logger.info("开始编辑药水");

        BaseMod.addPotion(KSMOD_MagickBottle.class, Color.NAVY.cpy(), Color.BLUE.cpy(), Color.SKY.cpy(), KSMOD_MagickBottle.POTION_ID, KSMOD_CustomCharacter.KINOMOTOSAKURA);

        KSMOD_LoggerTool.Logger.info("结束编辑药水");
    }

    public void receiveEditEvents()
    {
        KSMOD_LoggerTool.Logger.info("开始编辑事件");

        KSMOD_LoggerTool.Logger.info("结束编辑事件");
    }

    public void receiveEditMonsters()
    {
        KSMOD_LoggerTool.Logger.info("开始编辑怪物");

        KSMOD_LoggerTool.Logger.info("结束编辑怪物");
    }

    public static String GetLocalizationPath()
    {
        if (localizationPath == null)
        {
            localizationPath = "localization/";
            switch (Settings.language)
            {
                case ZHS:
                    KSMOD_LoggerTool.Logger.info("language == zhs");
                    localizationPath = localizationPath + "zhs/";
                    break;
                default:
                    KSMOD_LoggerTool.Logger.info("language == eng");
                    localizationPath = localizationPath + "eng/";
                    break;
            }
        }
        return localizationPath;
    }

    @Override
    public void receiveEditStrings()
    {
        KSMOD_LoggerTool.Logger.info("开始编辑本地化文本");

        String card = GetLocalizationPath() + "sakura_card.json";
        String cardStrings = Gdx.files.internal(card).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(CardStrings.class, cardStrings);

        String character = GetLocalizationPath() + "sakura_character.json";
        String charStrings = Gdx.files.internal(character).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(CharacterStrings.class, charStrings);

        String power = GetLocalizationPath() + "sakura_power.json";
        String powerStrings = Gdx.files.internal(power).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(PowerStrings.class, powerStrings);

        String relic = GetLocalizationPath() + "sakura_relic.json";
        String relicStrings = Gdx.files.internal(relic).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(RelicStrings.class, relicStrings);

        String ui = GetLocalizationPath() + "sakura_ui.json";
        String uiStrings = Gdx.files.internal(ui).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(UIStrings.class, uiStrings);

        String potion = GetLocalizationPath() + "sakura_potion.json";
        String potionStrings = Gdx.files.internal(potion).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(PotionStrings.class, potionStrings);

        KSMOD_LoggerTool.Logger.info("结束编辑本地化文本");
    }

    @Override
    public void receiveEditKeywords()
    {
        KSMOD_LoggerTool.Logger.info("开始编辑关键字");

        String path = KSMOD_LocalizeTool.GetKeywordFolderPath();

        path += "sakura_keyword.json";
        Gson gson = new Gson();
        String json = Gdx.files.internal(path).readString(String.valueOf(StandardCharsets.UTF_8));
        KSMOD_CustomKeywords keywords = gson.fromJson(json, KSMOD_CustomKeywords.class);
        Keyword[] keywordList = keywords.keywords;

        for (int i = 0; i < keywordList.length; ++i)
        {
            Keyword key = keywordList[i];
            KSMOD_LoggerTool.Logger.info("加载关键字：" + key.NAMES[0]);
            BaseMod.addKeyword(key.NAMES, key.DESCRIPTION);
        }

        KSMOD_LoggerTool.Logger.info("结束编辑关键字");
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom)
    {
        // KSMOD_LoggerTool.Logger.info("common relic pool");
        // ShowRelicList(AbstractDungeon.commonRelicPool);
        //
        // KSMOD_LoggerTool.Logger.info("uncommon relic pool");
        // ShowRelicList(AbstractDungeon.uncommonRelicPool);
        //
        // KSMOD_LoggerTool.Logger.info("rare relic pool");
        // ShowRelicList(AbstractDungeon.rareRelicPool);
        //
        // KSMOD_LoggerTool.Logger.info("boss relic pool");
        // ShowRelicList(AbstractDungeon.bossRelicPool);
        //
        // KSMOD_LoggerTool.Logger.info("shop relic pool");
        // ShowRelicList(AbstractDungeon.shopRelicPool);
    }

    public void ShowRelicList(ArrayList<String> relics)
    {
        for (int i = 0; i < relics.size(); i++)
        {
            KSMOD_LoggerTool.Logger.info(i + ": " + relics.get(i) + (istargetrelic(relics.get(i)) ? "<=========" : ""));
        }
    }

    public boolean istargetrelic(String relic)
    {
        if (relic.contains(KSMOD_StarWand.RELIC_ID))
        {
            return true;
        }
        else if (relic.contains(KSMOD_Cerberus.RELIC_ID))
        {
            return true;
        }
        else if (relic.contains(KSMOD_Yue.RELIC_ID))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
