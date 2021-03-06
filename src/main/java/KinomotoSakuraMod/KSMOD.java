package KinomotoSakuraMod;

import KinomotoSakuraMod.Cards.ClowCard.*;
import KinomotoSakuraMod.Cards.SakuraCard.*;
import KinomotoSakuraMod.Cards.SpellCard.*;
import KinomotoSakuraMod.Characters.KinomotoSakura;
import KinomotoSakuraMod.Events.KSMOD_TheSealedCardEvent;
import KinomotoSakuraMod.Events.KSMOD_XiaoLangsFeelingsEvent;
import KinomotoSakuraMod.Monsters.KSMOD_TheNothingMonster;
import KinomotoSakuraMod.Patches.KSMOD_CustomCardColor;
import KinomotoSakuraMod.Patches.KSMOD_CustomCharacter;
import KinomotoSakuraMod.Patches.KSMOD_CustomKeywords;
import KinomotoSakuraMod.Potions.KSMOD_MagickBottle;
import KinomotoSakuraMod.Relics.*;
import KinomotoSakuraMod.Utility.KSMOD_ImageConst;
import KinomotoSakuraMod.Utility.KSMOD_LocalizeTool;
import KinomotoSakuraMod.Utility.KSMOD_LoggerTool;
import basemod.BaseMod;
import basemod.eventUtil.AddEventParams;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.TheBeyond;
import com.megacrit.cardcrawl.dungeons.TheCity;
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
    public static final Color colorClowCard = CardHelper.getColor(255, 152, 74);
    public static final Color colorSakuraCard = CardHelper.getColor(255, 192, 203);
    public static final Color colorSpellCard = CardHelper.getColor(253, 220, 106);

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
            KSMOD_LoggerTool.Logger.info("载入遗物：" + relic.name);
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
            KSMOD_LoggerTool.Logger.info("解锁卡牌：" + card.name);
        }

        ArrayList<AbstractCard> lockedCardList = new ArrayList<>();
        // lockedCardList.addAll(GetSakuraCards());
        for (AbstractCard card : lockedCardList)
        {
            BaseMod.addCard(card);
            KSMOD_LoggerTool.Logger.info("载入锁定卡牌：" + card.name);
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
            clowCards.add(new ClowCardTheNothing());
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

            spellCards.add(new TestCard());
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
            sakuraCards.add(new SakuraCardTheHope());
            sakuraCards.add(new SakuraCardTheIllusion());
            sakuraCards.add(new SakuraCardTheJump());
            sakuraCards.add(new SakuraCardTheLibra());
            sakuraCards.add(new SakuraCardTheLight());
            sakuraCards.add(new SakuraCardTheLittle());
            sakuraCards.add(new SakuraCardTheLock());
            sakuraCards.add(new SakuraCardTheLoop());
            sakuraCards.add(new SakuraCardTheLove());
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

        BaseMod.addEvent(new AddEventParams.Builder(KSMOD_XiaoLangsFeelingsEvent.ID, KSMOD_XiaoLangsFeelingsEvent.class).dungeonID(TheCity.ID).playerClass(KSMOD_CustomCharacter.KINOMOTOSAKURA).create());
        BaseMod.addEvent(new AddEventParams.Builder(KSMOD_TheSealedCardEvent.ID, KSMOD_TheSealedCardEvent.class).dungeonID(TheBeyond.ID).playerClass(KSMOD_CustomCharacter.KINOMOTOSAKURA).create());

        KSMOD_LoggerTool.Logger.info("结束编辑事件");
    }


    public void receiveEditMonsters()
    {
        KSMOD_LoggerTool.Logger.info("开始编辑怪物");

        BaseMod.addMonster(KSMOD_TheNothingMonster.ID, KSMOD_TheNothingMonster::new);

        KSMOD_LoggerTool.Logger.info("结束编辑怪物");
    }

    @Override
    public void receiveEditStrings()
    {
        KSMOD_LoggerTool.Logger.info("开始编辑本地化文本");

        KSMOD_LocalizeTool.LoadStrings("sakura_card.json", CardStrings.class);
        KSMOD_LocalizeTool.LoadStrings("sakura_character.json", CharacterStrings.class);
        KSMOD_LocalizeTool.LoadStrings("sakura_event.json", EventStrings.class);
        KSMOD_LocalizeTool.LoadStrings("sakura_potion.json", PotionStrings.class);
        KSMOD_LocalizeTool.LoadStrings("sakura_power.json", PowerStrings.class);
        KSMOD_LocalizeTool.LoadStrings("sakura_relic.json", RelicStrings.class);
        KSMOD_LocalizeTool.LoadStrings("sakura_ui.json", UIStrings.class);
        KSMOD_LocalizeTool.LoadStrings("sakura_monster.json", MonsterStrings.class);

        KSMOD_LoggerTool.Logger.info("结束编辑本地化文本");
    }

    @Override
    public void receiveEditKeywords()
    {
        KSMOD_LoggerTool.Logger.info("开始编辑关键字");

        String path = KSMOD_LocalizeTool.GetFolderPath() + "sakura_keyword.json";
        String json = Gdx.files.internal(path).readString(String.valueOf(StandardCharsets.UTF_8));
        Keyword[] keywordList = new Gson().fromJson(json, KSMOD_CustomKeywords.class).keywords;

        for (int i = 0; i < keywordList.length; ++i)
        {
            Keyword word = keywordList[i];
            KSMOD_LoggerTool.Logger.info("加载关键字：" + word.NAMES[0]);
            BaseMod.addKeyword(word.NAMES, word.DESCRIPTION);
        }

        KSMOD_LoggerTool.Logger.info("结束编辑关键字");
    }
}
