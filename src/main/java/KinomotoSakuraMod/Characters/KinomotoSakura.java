package KinomotoSakuraMod.Characters;

import KinomotoSakuraMod.Cards.ClowCard.ClowCardTheShield;
import KinomotoSakuraMod.Cards.ClowCard.ClowCardTheSword;
import KinomotoSakuraMod.Cards.SpellCard.SpellCardRelease;
import KinomotoSakuraMod.Cards.SpellCard.SpellCardSeal;
import KinomotoSakuraMod.KinomotoSakuraMod;
import KinomotoSakuraMod.Patches.CustomCardColor;
import KinomotoSakuraMod.Patches.CustomCharacter;
import KinomotoSakuraMod.Relics.SealedWand;
import basemod.abstracts.CustomPlayer;
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
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.util.ArrayList;

public class KinomotoSakura extends CustomPlayer
{
    // 能量栏
    private static final String[] ORB_TEXTURES = {"img/UI/EPanel/layer5.png", "img/UI/EPanel/layer4.png", "img/UI/EPanel/layer3.png", "img/UI/EPanel/layer2.png", "img/UI/EPanel/layer1.png", "img/UI/EPanel/layer0.png", "img/UI/EPanel/layer5d.png", "img/UI/EPanel/layer4d.png", "img/UI/EPanel/layer3d.png", "img/UI/EPanel/layer2d.png", "img/UI/EPanel/layer1d.png"};
    private static final String ORB_VFX = "img/UI/energyBlueVFX.png";
    private static final float[] LAYER_SPEED = {-40.0F, -32.0F, 20.0F, -20.0F, 0.0F, -10.0F, -8.0F, 5.0F, -5.0F, 0.0F};
    private static final int START_ENERGY = 3;
    // 角色资源
    private static final String SHOULDER_1_IMAGE_PATH = "img/char/Marisa/shoulder1.png";
    private static final String SHOULDER_2_IMAGE_PATH = "img/char/Marisa/shoulder2.png";
    private static final String CORPSE_IMAGE_PATH = "img/char/Marisa/fallen.png";
    private static final String ATLAS_PATH = "img/char/Marisa/MarisaModelv3.atlas";
    private static final String SKELETON_PATH = "img/char/Marisa/MarisaModelv3.json";
    private static final float CHARACTER_SCALE_RATE = 2.0f;

    public KinomotoSakura(String playerName)
    {
        // 参数列表：角色名，角色类枚举，能量面板贴图路径列表，能量面板特效贴图路径，能量面板贴图旋转速度列表，能量面板，模型资源路径，动画资源路径
        super(playerName, CustomCharacter.KINOMOTOSAKURA, ORB_TEXTURES, ORB_VFX, LAYER_SPEED, null, null);

        // 对话框位置，默认就好
        this.dialogX = (this.drawX + 0.0F * Settings.scale);
        this.dialogY = (this.drawY + 220.0F * Settings.scale);

        // 参数列表：静态贴图路径，越肩视角2贴图路径，越肩视角贴图路径，失败时贴图路径，角色选择界面信息，意义不明的四个固定数字（20.0F, -10.0F, 220.0F, 290.0F），初始能量数
        initializeClass(null, SHOULDER_2_IMAGE_PATH, SHOULDER_1_IMAGE_PATH, CORPSE_IMAGE_PATH, getLoadout(), 20.0F, -10.0F, 220.0F, 290.0F, new EnergyManager(START_ENERGY));

        loadAnimation(ATLAS_PATH, SKELETON_PATH, CHARACTER_SCALE_RATE);

        AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.stateData.setMix("Hit", "Idle", 0.1F);
        e.setTimeScale(1.0F);
    }

    public ArrayList<String> getStartingDeck()
    {
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
        startRelics.add(SealedWand.class.getSimpleName());
        UnlockTracker.markRelicAsSeen(SealedWand.class.getSimpleName());
        return startRelics;
    }

    public CharSelectInfo getLoadout()
    {
        String title;
        String flavor;
        switch (Settings.language)
        {
            case ZHS:
                title = "魔卡少女";
                flavor = "《魔卡少女樱》的主角";
                break;
            default:
                title = "Card Capter";
                flavor = "The protagonist of animation Card Captor Sakura";
                break;
        }
        return new CharSelectInfo(title, flavor, 75, 75, 0, 99, 5, this, getStartingRelics(), getStartingDeck(), false);
    }

    public String getTitle(PlayerClass playerClass)
    {
        String title;
        switch (Settings.language)
        {
            case ZHS:
                title = "魔卡少女";
                break;
            default:
                title = "Card Capter";
                break;
        }
        return title;
    }

    public AbstractCard.CardColor getCardColor()
    {
        return CustomCardColor.CLOWCARD_COLOR;
    }

    public Color getCardRenderColor()
    {
        return KinomotoSakuraMod.colorSakuraCard;
    }

    public AbstractCard getStartCardForEvent()
    {
        return null;
    }

    public Color getCardTrailColor()
    {
        return KinomotoSakuraMod.colorSakuraCard;
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
        return "Sakura";
    }

    public AbstractPlayer newInstance()
    {
        return new KinomotoSakura(this.name);
    }

    public String getSpireHeartText()
    {
        return SpireHeart.DESCRIPTIONS[10];
    }

    public Color getSlashAttackColor()
    {
        return KinomotoSakuraMod.colorSakuraCard;
    }

    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect()
    {
        return new AbstractGameAction.AttackEffect[]{AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.SLASH_DIAGONAL, AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.SLASH_DIAGONAL};
    }

    public String getVampireText()
    {
        return Vampires.DESCRIPTIONS[1];
    }
}
