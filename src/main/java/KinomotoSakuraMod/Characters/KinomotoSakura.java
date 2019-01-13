package KinomotoSakuraMod.Characters;

import KinomotoSakuraMod.Patches.CharacterEnum;
import basemod.abstracts.CustomPlayer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.events.city.Vampires;
import com.megacrit.cardcrawl.screens.CharSelectInfo;

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
        return null;
    }

    public ArrayList<String> getStartingRelics()
    {
        return null;
    }

    public CharSelectInfo getLoadout()
    {
        return null;
    }

    public String getTitle(PlayerClass playerClass)
    {
        return null;
    }

    public AbstractCard.CardColor getCardColor()
    {
        return null;
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
        return null;
    }

    public int getAscensionMaxHPLoss()
    {
        return 0;
    }

    public BitmapFont getEnergyNumFont()
    {
        return null;
    }

    public void doCharSelectScreenSelectEffect()
    {

    }

    public String getCustomModeCharacterButtonSoundKey()
    {
        return null;
    }

    public String getLocalizedCharacterName()
    {
        return null;
    }

    public AbstractPlayer newInstance()
    {
        return null;
    }

    public String getSpireHeartText()
    {
        return null;
    }

    public Color getSlashAttackColor()
    {
        return null;
    }

    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect()
    {
        return new AbstractGameAction.AttackEffect[] { AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.SLASH_DIAGONAL, AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.SLASH_DIAGONAL };
    }

    public String getVampireText()
    {
        return Vampires.DESCRIPTIONS[1];
    }
}
