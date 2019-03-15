package KinomotoSakuraMod.Characters;

import KinomotoSakuraMod.KinomotoSakutaMod;
import KinomotoSakuraMod.Patches.CardColorEnum;
import KinomotoSakuraMod.Patches.CharacterEnum;
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
    private static final String[] ORB_TEXTURES = {"img/UI/EPanel/layer5.png", "img/UI/EPanel/layer4.png", "img/UI/EPanel/layer3.png", "img/UI/EPanel/layer2.png", "img/UI/EPanel/layer1.png", "img/UI/EPanel/layer0.png", "img/UI/EPanel/layer5d.png", "img/UI/EPanel/layer4d.png", "img/UI/EPanel/layer3d.png", "img/UI/EPanel/layer2d.png", "img/UI/EPanel/layer1d.png"};
    private static final String ORB_VFX = "img/UI/energyBlueVFX.png";
    private static final float[] LAYER_SPEED = {-40.0F, -32.0F, 20.0F, -20.0F, 0.0F, -10.0F, -8.0F, 5.0F, -5.0F, 0.0F};

    public KinomotoSakura(String playerName)
    {
        // 参数列表：角色名，角色类枚举，能量面板贴图路径列表，能量面板特效贴图路径，能量面板贴图旋转速度列表，能量面板，模型资源路径，动画资源路径
        super(playerName, CharacterEnum.KINOMOTOSAKURA, ORB_TEXTURES, ORB_VFX, LAYER_SPEED, null, null);

        this.dialogX = (this.drawX + 0.0F * Settings.scale);
        this.dialogY = (this.drawY + 220.0F * Settings.scale);
        // 参数列表：静态贴图路径，越肩视角2贴图路径，越肩视角贴图路径，失败时贴图路径，角色选择界面信息，意义不明的四个固定数字（20.0F, -10.0F, 220.0F, 290.0F），初始能量数
        initializeClass(null, "img/char/Marisa/shoulder2.png", "img/char/Marisa/shoulder1.png", "img/char/Marisa/fallen.png", getLoadout(), 20.0F, -10.0F, 220.0F, 290.0F, new EnergyManager(3));

        loadAnimation("img/char/Marisa/MarisaModelv3.atlas", "img/char/Marisa/MarisaModelv3.json", 2.0F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.stateData.setMix("Hit", "Idle", 0.1F);
        e.setTimeScale(1.0F);
    }

    public ArrayList<String> getStartingDeck()
    {
        ArrayList<String> startCards = new ArrayList<String>();
        startCards.add("ClowCardTheSword");
        startCards.add("ClowCardTheSword");
        startCards.add("ClowCardTheSword");
        startCards.add("ClowCardTheSword");
        startCards.add("ClowCardTheSword");
        return startCards;
    }

    public ArrayList<String> getStartingRelics()
    {
        ArrayList<String> startRelics = new ArrayList<String>();
        startRelics.add("Test 4");
        UnlockTracker.markRelicAsSeen("Test 4");
        return startRelics;
    }

    public CharSelectInfo getLoadout()
    {
        String title;
        String flavor;
        if (Settings.language == Settings.GameLanguage.ZHS)
        {
            title = "魔卡少女";
            flavor = "《魔拉少女樱》的主角";
        }
        else
        {
            title = "Card Capter";
            flavor = "The protagonist of animation Card Captor Sakura";
        }
        return new CharSelectInfo(title, flavor, 75, 75, 0, 99, 5, this, getStartingRelics(), getStartingDeck(), false);
    }

    public String getTitle(PlayerClass playerClass)
    {
        String title;
        if (Settings.language == Settings.GameLanguage.ZHS)
        {
            title = "魔卡少女";
        }
        else
        {
            title = "Card Capter";
        }
        return title;
    }

    public AbstractCard.CardColor getCardColor()
    {
        return CardColorEnum.CLOWCARD_COLOR;
    }

    public Color getCardRenderColor()
    {
        return null;
    }

    public AbstractCard getStartCardForEvent()
    {
        return null;
    }

    public Color getCardTrailColor()
    {
        return KinomotoSakutaMod.COLOR_SAKURA;
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
        return KinomotoSakutaMod.COLOR_SAKURA;
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
