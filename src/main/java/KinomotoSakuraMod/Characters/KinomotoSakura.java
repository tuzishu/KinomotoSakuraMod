package KinomotoSakuraMod.Characters;

import KinomotoSakuraMod.Cards.ClowCard.ClowCardTheShield;
import KinomotoSakuraMod.Cards.ClowCard.ClowCardTheSword;
import KinomotoSakuraMod.Cards.SpellCard.SpellCardRelease;
import KinomotoSakuraMod.Cards.SpellCard.SpellCardSeal;
import KinomotoSakuraMod.KSMOD;
import KinomotoSakuraMod.Patches.KSMOD_CustomCardColor;
import KinomotoSakuraMod.Patches.KSMOD_CustomCharacter;
import KinomotoSakuraMod.Relics.KSMOD_SealedBook;
import KinomotoSakuraMod.Relics.KSMOD_SealedWand;
import KinomotoSakuraMod.Utility.KSMOD_ImageConst;
import basemod.BaseMod;
import basemod.abstracts.CustomPlayer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.events.beyond.SpireHeart;
import com.megacrit.cardcrawl.events.city.Vampires;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.localization.TutorialStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class KinomotoSakura extends CustomPlayer
{
    // 角色数据
    public static final String ID = "KinomotoSakura";
    private static CharacterStrings characterStrings = null;
    private static String[] MESSAGES = null;
    private static final int START_HP = 65;
    private static final int START_GOLD = 99;
    private static final int MAX_ORBS = 0;
    private static final int CARD_DRAW = 5;
    private static final int START_ENERGY = 3;
    private static final float[] LAYER_SPEED = {
            -40.0F,
            -32.0F,
            20.0F,
            -20.0F,
            0.0F,
            -10.0F,
            -8.0F,
            5.0F,
            -5.0F,
            0.0F
    };
    // 角色资源
    public static final float CHARACTER_SCALE_RATE = 2.0f;

    public KinomotoSakura()
    {
        // 参数列表：角色名，角色类枚举，能量面板贴图路径列表，能量面板特效贴图路径，能量面板贴图旋转速度列表，能量面板，模型资源路径，动画资源路径
        super(ID, KSMOD_CustomCharacter.KINOMOTOSAKURA, KSMOD_ImageConst.ORB_TEXTURES, KSMOD_ImageConst.ORB_VFX, LAYER_SPEED, null, null);

        // 对话框位置，默认就好
        this.dialogX = (this.drawX + 0.0F * Settings.scale);
        this.dialogY = (this.drawY + 220.0F * Settings.scale);

        // 参数列表：静态贴图路径，越肩视角2贴图路径，越肩视角贴图路径，失败时贴图路径，角色选择界面信息，碰撞箱XY宽高，初始能量数
        initializeClass(KSMOD_ImageConst.IDLE_IMAGE_PATH, KSMOD_ImageConst.SHOULDER_2_IMAGE_PATH,  KSMOD_ImageConst.SHOULDER_1_IMAGE_PATH, KSMOD_ImageConst.CORPSE_IMAGE_PATH, getLoadout(), 20.0F, -10.0F, 220.0F, 290.0F, new EnergyManager(START_ENERGY));

        // loadAnimation(KSMOD_ImageConst.ANIMA_ATLAS_PATH, KSMOD_ImageConst.ANIMA_SKELETON_PATH, CHARACTER_SCALE_RATE);
        //
        // AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        // e.setTime(e.getEndTime() * MathUtils.random());
        // this.stateData.setMix("Hit", "Idle", 0.1F);
        // e.setTimeScale(1.0F);
    }

    public ArrayList<String> getStartingDeck()
    {
        // 正式初始牌
        ArrayList<String> startCards = new ArrayList<String>();

        startCards.add(ClowCardTheSword.ID);
        startCards.add(ClowCardTheSword.ID);
        startCards.add(ClowCardTheSword.ID);
        startCards.add(ClowCardTheSword.ID);
        startCards.add(ClowCardTheShield.ID);
        startCards.add(ClowCardTheShield.ID);
        startCards.add(ClowCardTheShield.ID);
        startCards.add(ClowCardTheShield.ID);
        startCards.add(SpellCardSeal.ID);
        startCards.add(SpellCardRelease.ID);

        return startCards;
    }

    public ArrayList<String> getStartingRelics()
    {
        ArrayList<String> startRelics = new ArrayList<String>();
        startRelics.add(KSMOD_SealedBook.RELIC_ID);
        startRelics.add(KSMOD_SealedWand.RELIC_ID);
        UnlockTracker.markRelicAsSeen(KSMOD_SealedWand.class.getSimpleName());
        return startRelics;
    }

    public CharSelectInfo getLoadout()
    {
        return new CharSelectInfo(GetCharactorLocalization().NAMES[0], GetCharactorLocalization().TEXT[0], START_HP, START_HP, MAX_ORBS, START_GOLD, CARD_DRAW, this, getStartingRelics(), getStartingDeck(), false);
    }

    public static CharacterStrings GetCharactorLocalization()
    {
        if (characterStrings == null)
        {
            String path = KSMOD.GetLocalizationPath() + "sakura_character.json";
            String str = Gdx.files.internal(path).readString(String.valueOf(StandardCharsets.UTF_8));
            BaseMod.loadCustomStrings(TutorialStrings.class, str);
            characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
        }
        return characterStrings;
    }

    public String getTitle(PlayerClass playerClass)
    {
        return GetCharactorLocalization().NAMES[1];
    }

    public static String GetMessage(int msgNumber)
    {
        if (MESSAGES == null)
        {
            String path = KSMOD.GetLocalizationPath() + "sakura_tutorial.json";
            String tutorialStrings = Gdx.files.internal(path).readString(String.valueOf(StandardCharsets.UTF_8));
            BaseMod.loadCustomStrings(TutorialStrings.class, tutorialStrings);
            TutorialStrings tut = CardCrawlGame.languagePack.getTutorialString("Message");
            MESSAGES = tut.TEXT;
        }
        if (msgNumber >= MESSAGES.length)
        {
            return "Error Message Number Over Length.";
        }
        return MESSAGES[msgNumber];
    }

    public AbstractCard.CardColor getCardColor()
    {
        return KSMOD_CustomCardColor.CLOWCARD_COLOR;
    }

    public Color getCardRenderColor()
    {
        return KSMOD.colorSakuraCard;
    }

    public AbstractCard getStartCardForEvent()
    {
        return new ClowCardTheSword();
    }

    public Color getCardTrailColor()
    {
        return KSMOD.colorSakuraCard;
    }

    public int getAscensionMaxHPLoss()
    {
        return 5;
    }

    public BitmapFont getEnergyNumFont()
    {
        return FontHelper.energyNumFontBlue;
    }

    public void doCharSelectScreenSelectEffect()
    {
        CardCrawlGame.sound.playA("ATTACK_MAGIC_BEAM_SHORT", MathUtils.random(-0.2F, 0.2F));
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, false);
    }

    public String getCustomModeCharacterButtonSoundKey()
    {
        return "ATTACK_MAGIC_BEAM_SHORT";
    }

    public String getLocalizedCharacterName()
    {
        return GetCharactorLocalization().NAMES[0];
    }

    public AbstractPlayer newInstance()
    {
        return new KinomotoSakura();
    }

    public String getSpireHeartText()
    {
        return SpireHeart.DESCRIPTIONS[10];
    }

    public Color getSlashAttackColor()
    {
        return KSMOD.colorSakuraCard;
    }

    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect()
    {
        return new AbstractGameAction.AttackEffect[] {
                AbstractGameAction.AttackEffect.SLASH_HEAVY,
                AbstractGameAction.AttackEffect.FIRE,
                AbstractGameAction.AttackEffect.SLASH_DIAGONAL,
                AbstractGameAction.AttackEffect.SLASH_HEAVY,
                AbstractGameAction.AttackEffect.FIRE,
                AbstractGameAction.AttackEffect.SLASH_DIAGONAL
        };
    }

    public String getVampireText()
    {
        return Vampires.DESCRIPTIONS[1];
    }
}
