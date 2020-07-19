package KinomotoSakuraMod.Cards;

import KinomotoSakuraMod.Actions.KSMOD_ReleaseAction;
import KinomotoSakuraMod.Characters.KinomotoSakura;
import KinomotoSakuraMod.Effects.KSMOD_MagickChargedEffect;
import KinomotoSakuraMod.Patches.KSMOD_CustomCardColor;
import KinomotoSakuraMod.Patches.KSMOD_CustomTag;
import KinomotoSakuraMod.Powers.*;
import KinomotoSakuraMod.Relics.KSMOD_SealedBook;
import KinomotoSakuraMod.Utility.KSMOD_ImageConst;
import KinomotoSakuraMod.Utility.KSMOD_LoggerTool;
import KinomotoSakuraMod.Utility.KSMOD_ReflectTool;
import KinomotoSakuraMod.Utility.KSMOD_RenderTool;
import basemod.BaseMod;
import basemod.abstracts.CustomCard;
import basemod.abstracts.DynamicVariable;
import basemod.interfaces.ISubscriber;
import basemod.interfaces.OnStartBattleSubscriber;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DescriptionLine;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class KSMOD_AbstractMagicCard extends CustomCard implements ISubscriber, OnStartBattleSubscriber
{
    //////////
    // Override Method Usage
    //////////
    private static final float ENERGY_COST_OFFSET_X = -90F;
    private static final float ENERGY_COST_OFFSET_Y = 222F;
    private static final Color ENERGY_COST_RESTRICTED_COLOR = new Color(1.0F, 0.3F, 0.3F, 1.0F);
    private static final Color ENERGY_COST_MODIFIED_COLOR = new Color(0.4F, 1.0F, 0.4F, 1.0F);
    public static final float IMG_WIDTH = 221F;// * Settings.scale;
    public static final float IMG_HEIGHT = 491F;// * Settings.scale;
    private static final float DESC_LINE_WIDTH = 190F * Settings.scale;
    private static final float DESC_SCALE_RATE_X = 0.83F;
    private static final float DESC_OFFSET_TO_BOTTOM_Y = 0.15F * Settings.scale;
    private static final float CARD_ENERGY_IMG_WIDTH = 24.0F;// * Settings.scale;
    private static final float HB_W = IMG_WIDTH;
    private static final float HB_H = IMG_HEIGHT;
    private static final float TITLE_HEIGHT_TO_CENTER = 222.0F;// * Settings.scale;
    private static final float TITLE_HEIGHT_SAKURA_TO_CENTER = 218.0F;// * Settings.scale;
    private static final float TITLE_BOTTOM_HEIGHT_TO_CENTER = -205.0F;// * Settings.scale;
    private static final float TITLE_BOTTOM_HEIGHT_SAKURA_TO_CENTER = -197.0F;// * Settings.scale;
    private static final float PORTRAIT_WIDTH = 152F;// * Settings.scale;
    private static final float PORTRAIT_HEIGHT = 393F;// * Settings.scale;
    private static final float PORTRAIT_ORIGIN_X = 76F;// * Settings.scale;
    private static final float PORTRAIT_ORIGIN_Y = 178F;// * Settings.scale;
    public static boolean isHandSelectScreenOpened = false;
    private String BOTTOM_TITLE = "";
    private static int useChargeTimes = 0;
    private static KSMOD_MagickChargedEffect magickChargeEffect = null;

    //////////
    // Custom Value
    //////////
    public String unreleasedDesc = "";
    public boolean originExhaust = false;
    public boolean originEthereal = false;
    private boolean hasReleased = false;
    private float releaseRate = 0F;
    private int[] valueBuffer = new int[3];
    private boolean hasExtraEffect = false;
    private boolean isCharging = false;
    private boolean isTurning = false;
    public float renderedPortionProportionToTop = 1F;

    public KSMOD_AbstractMagicCard(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target)
    {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
        BaseMod.subscribe(this);
        InitMagicCard();
    }

    public KSMOD_AbstractMagicCard(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target, boolean hasExtraEffect)
    {
        this(id, name, img, cost, rawDescription, type, color, rarity, target);
        this.hasExtraEffect = hasExtraEffect;
    }

    public boolean hasExtraEffect()
    {
        return hasExtraEffect;
    }

    public abstract void upgrade();

    public abstract AbstractCard makeCopy();

    public KSMOD_AbstractMagicCard getSameNameClowCard()
    {
        return null;
    }

    public void triggerOnExhaust()
    {
        if (hasReleased)
        {
            this.hasReleased = false;
            this.upgradeDamage(-this.valueBuffer[0]);
            this.upgradeBlock(-this.valueBuffer[1]);
            this.upgradeMagicNumber(-this.valueBuffer[2]);
            this.valueBuffer = new int[3];
            this.exhaust = originExhaust;
            this.isEthereal = originEthereal;
            this.rawDescription = unreleasedDesc;
            this.initializeDescription();
        }
    }

    public boolean canUpgrade()
    {
        if (this.color == KSMOD_CustomCardColor.SAKURACARD_COLOR)
        {
            return false;
        }
        return super.canUpgrade();
    }

    public final void use(AbstractPlayer player, AbstractMonster monster)
    {
        if (this.hasExtraEffect && isThisCardCharged() && !hasLockPower())
        {
            AbstractPower power = player.getPower(KSMOD_MagickChargePower.POWER_ID);
            power.flash();
            if (!hasLockPowerSakuraCard())
            {
                if (power.amount == KSMOD_SealedBook.ACTIVE_NUMBER)
                {
                    AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(player, player, power));
                }
                else
                {
                    AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(player,
                            player,
                            power,
                            KSMOD_SealedBook.ACTIVE_NUMBER));
                }
            }
            this.applyExtraEffect(player, monster);
            applyElementPowers(player);
            setUseChargeTimes(useChargeTimes + 1);
        }
        else
        {
            this.applyNormalEffect(player, monster);
            if (this.color == KSMOD_CustomCardColor.SAKURACARD_COLOR)
            {
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new VoidCard(),
                        1,
                        true,
                        true,
                        false));
            }
        }
        if (player.hasRelic(KSMOD_SealedBook.RELIC_ID))
        {
            KSMOD_SealedBook book = (KSMOD_SealedBook) player.getRelic(KSMOD_SealedBook.RELIC_ID);
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player,
                    player,
                    new KSMOD_MagickChargePower(player, book.applyPowerNumberOnce(this)),
                    book.applyPowerNumberOnce(this)));
        }
    }

    private void applyElementPowers(AbstractPlayer player)
    {
        if (this.hasTag(KSMOD_CustomTag.KSMOD_EARTHY_CARD) && !player.hasPower(KSMOD_EarthyPower.POWER_ID))
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player,
                    player,
                    new KSMOD_EarthyPower(player, 1)));
        }
        if (this.hasTag(KSMOD_CustomTag.KSMOD_FIREY_CARD) && !player.hasPower(KSMOD_FireyPower.POWER_ID))
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player,
                    player,
                    new KSMOD_FireyPower(player, 1)));
        }
        if (this.hasTag(KSMOD_CustomTag.KSMOD_WATERY_CARD) && !player.hasPower(KSMOD_WateryPower.POWER_ID))
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player,
                    player,
                    new KSMOD_WateryPower(player, 1)));
        }
        if (this.hasTag(KSMOD_CustomTag.KSMOD_WINDY_CARD) && !player.hasPower(KSMOD_WindyPower.POWER_ID))
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player,
                    player,
                    new KSMOD_WindyPower(player, 1)));
        }
    }

    public void receiveOnBattleStart(AbstractRoom room)
    {
        useChargeTimes = 0;
        magickChargeEffect = null;
    }

    private void setUseChargeTimes(int useTimes)
    {
        useChargeTimes = useTimes;
        broadcast(useTimes, AbstractDungeon.player.hand);
        broadcast(useTimes, AbstractDungeon.player.drawPile);
        broadcast(useTimes, AbstractDungeon.player.discardPile);
        broadcast(useTimes, AbstractDungeon.player.exhaustPile);
    }

    private void broadcast(int useTimes, CardGroup group)
    {
        for (AbstractCard card : group.group)
        {
            if (card instanceof KSMOD_AbstractMagicCard)
            {
                ((KSMOD_AbstractMagicCard) card).onUseMaigckCharge(useTimes);
            }
        }
    }

    public void onUseMaigckCharge(int useTimes)
    {
    }

    private void InitMagicCard()
    {
        if (this.color == KSMOD_CustomCardColor.SAKURACARD_COLOR)
        {
            this.upgraded = true;
        }
        String bottomTitle = this.getClass().getSimpleName();
        if (BOTTOM_TITLE.isEmpty() && bottomTitle.contains("ClowCardThe"))
        {
            bottomTitle = bottomTitle.replaceAll("ClowCardThe", "THE ");
            BOTTOM_TITLE = bottomTitle.toUpperCase();
        }
        if (BOTTOM_TITLE.isEmpty() && bottomTitle.contains("SakuraCardThe"))
        {
            bottomTitle = bottomTitle.replaceAll("SakuraCardThe", "THE ");
            BOTTOM_TITLE = bottomTitle.toUpperCase();
        }
    }

    public void receivePostPowerApplySubscriber(AbstractPower power)
    {
        if (power instanceof KSMOD_MagickChargePower && this.hasExtraEffect && !isCharging)
        {
            if (isThisCardCharged() && !hasLockPower())
            {
                applyChargeEffect();
                this.onCharged();
                this.initializeDescription();
            }
        }
        if (power instanceof KSMOD_LockPower && this.hasExtraEffect && isCharging)
        {
            if (isThisCardCharged())
            {
                removeChargeEffect();
                this.onDischarged();
                this.initializeDescription();
            }
        }
    }

    public void receivePostPowerReduceSubscriber(AbstractPower power)
    {
        if (power instanceof KSMOD_MagickChargePower && this.hasExtraEffect && isCharging)
        {
            if (!isThisCardCharged() || hasLockPower())
            {
                removeChargeEffect();
                this.onDischarged();
                this.initializeDescription();
            }
        }
    }

    public void receivePostPowerRemoveSubscriber(AbstractPower power)
    {
        if (power instanceof KSMOD_MagickChargePower && this.hasExtraEffect && isCharging)
        {
            if (!isThisCardCharged() || hasLockPower())
            {
                removeChargeEffect();
                this.onDischarged();
                this.initializeDescription();
            }
        }
        if (power instanceof KSMOD_LockPower && this.hasExtraEffect && !isCharging)
        {
            if (isThisCardCharged())
            {
                applyChargeEffect();
                this.onCharged();
                this.initializeDescription();
            }
        }
    }

    public boolean isThisCardCharged()
    {
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasPower(KSMOD_MagickChargePower.POWER_ID))
        {
            if (AbstractDungeon.player.getPower(KSMOD_MagickChargePower.POWER_ID).amount >= KSMOD_SealedBook.ACTIVE_NUMBER)
            {
                this.isCharging = true;
                return true;
            }
        }
        this.isCharging = false;
        return false;
    }

    private boolean hasLockPower()
    {
        return AbstractDungeon.player != null && AbstractDungeon.player.hasPower(KSMOD_LockPower.POWER_ID);
    }

    private boolean hasLockPowerSakuraCard()
    {
        return AbstractDungeon.player != null && AbstractDungeon.player.hasPower(KSMOD_LockPower_SakuraCard.POWER_ID);
    }

    private void applyChargeEffect()
    {
        if (magickChargeEffect == null)
        {
            magickChargeEffect = new KSMOD_MagickChargedEffect();
            AbstractDungeon.effectList.add(magickChargeEffect);
        }
    }

    public void onCharged()
    {

    }

    private void removeChargeEffect()
    {
        if (magickChargeEffect != null)
        {
            magickChargeEffect.duration = magickChargeEffect.fadeDuration;
            magickChargeEffect = null;
        }
    }

    public void onDischarged()
    {

    }

    public abstract void applyNormalEffect(AbstractPlayer player, AbstractMonster monster);

    public void applyExtraEffect(AbstractPlayer player, AbstractMonster monster)
    {

    }

    public String getExtraDescription()
    {
        return this.rawDescription;
    }

    public void setBaseMagicNumber(int value)
    {
        this.baseMagicNumber = value;
        this.magicNumber = value;
    }

    public void release(float releaseRate)
    {
        if (!hasReleased)
        {
            this.releaseRate = releaseRate;
            this.valueBuffer[0] = (int) (this.damage * this.releaseRate);
            this.valueBuffer[1] = (int) (this.block * this.releaseRate);
            this.valueBuffer[2] = (int) (this.magicNumber * this.releaseRate);
            this.upgradeDamage(this.valueBuffer[0]);
            this.upgradeBlock(this.valueBuffer[1]);
            this.upgradeMagicNumber(this.valueBuffer[2]);
            hasReleased = true;
        }
    }

    public boolean hasSameSakuraCard()
    {
        if (AbstractDungeon.player instanceof KinomotoSakura)
        {
            for (AbstractCard card : AbstractDungeon.player.drawPile.group)
            {
                if (card.cardID.equals(this.cardID))
                {
                    return true;
                }
            }
            for (AbstractCard card : AbstractDungeon.player.hand.group)
            {
                if (card.cardID.equals(this.cardID))
                {
                    return true;
                }
            }
            for (AbstractCard card : AbstractDungeon.player.discardPile.group)
            {
                if (card.cardID.equals(this.cardID))
                {
                    return true;
                }
            }
            for (AbstractCard card : AbstractDungeon.player.exhaustPile.group)
            {
                if (card.cardID.equals(this.cardID))
                {
                    return true;
                }
            }
        }
        return false;
    }

    //////////
    // Method Override
    //////////

    @Override
    public boolean isHoveredInHand(float scale)
    {
        if (this.hoverTimer > 0.0F)
        {
            return false;
        }
        else
        {
            int x = InputHelper.mX;
            int y = InputHelper.mY;
            return (float) x > this.current_x - IMG_WIDTH * scale / 2.0F && (float) x < this.current_x + IMG_WIDTH * scale / 2.0F && (float) y > this.current_y - IMG_HEIGHT * scale / 2.0F && (float) y < this.current_y + IMG_HEIGHT * scale / 2.0F;
        }
    }

    private Texture GetEnergyImage()
    {
        Texture texture;
        if (this.color == KSMOD_CustomCardColor.CLOWCARD_COLOR)
        {
            texture = KSMOD_ImageConst.ORB_CLOWCARD;
        }
        else
        {
            texture = KSMOD_ImageConst.ORB_SAKURACARD;
        }
        return texture;
    }

    @SpireOverride
    public void renderEnergy(SpriteBatch sb) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException
    {
        if (isTurning)
        {
            return;
        }
        boolean darken = KSMOD_ReflectTool.GetFieldByReflect(AbstractCard.class, "darken").getBoolean(this);
        if (this.cost > -2 && !darken && !this.isLocked && this.isSeen)
        {
            Method renderHelper = KSMOD_ReflectTool.GetMethodByReflect(AbstractCard.class,
                    "renderHelper",
                    SpriteBatch.class,
                    Color.class,
                    Texture.class,
                    float.class,
                    float.class);
            Color renderColor = (Color) KSMOD_ReflectTool.GetFieldByReflect(AbstractCard.class,
                    "renderColor").get(this);
            renderHelper.invoke(this, sb, renderColor, GetEnergyImage(), this.current_x - 512F, this.current_y - 512F);

            Color costColor = Color.WHITE.cpy();
            if (AbstractDungeon.player != null && AbstractDungeon.player.hand.contains(this) && !this.hasEnoughEnergy())
            {
                costColor = ENERGY_COST_RESTRICTED_COLOR;
            }
            else if (this.isCostModified || this.isCostModifiedForTurn || this.freeToPlayOnce)
            {
                costColor = ENERGY_COST_MODIFIED_COLOR;
            }

            costColor.a = this.transparency;

            Method getCost = KSMOD_ReflectTool.GetMethodByReflect(AbstractCard.class, "getCost");
            String text = (String) getCost.invoke(this);

            Method getEnergyFont = KSMOD_ReflectTool.GetMethodByReflect(AbstractCard.class, "getEnergyFont");
            BitmapFont font = (BitmapFont) getEnergyFont.invoke(this);

            if ((this.type != AbstractCard.CardType.STATUS || this.cardID.equals("Slimed")) && (this.color != AbstractCard.CardColor.CURSE || this.cardID.equals(
                    "Pride")))
            {
                FontHelper.renderRotatedText(sb,
                        font,
                        text,
                        this.current_x,
                        this.current_y,
                        ENERGY_COST_OFFSET_X * this.drawScale * Settings.scale,
                        ENERGY_COST_OFFSET_Y * this.drawScale * Settings.scale,
                        this.angle,
                        false,
                        costColor);
            }
        }
    }

    public void SetTurningStatus(boolean isTurning)
    {
        this.isTurning = isTurning;
    }

    private Texture GetFrameImage()
    {
        Texture texture;
        if (this.color == KSMOD_CustomCardColor.CLOWCARD_COLOR)
        {
            switch (this.rarity)
            {
                case BASIC:
                case COMMON:
                    texture = KSMOD_ImageConst.FRAME_CLOWCARD_COMMON;
                    break;
                case UNCOMMON:
                    texture = KSMOD_ImageConst.FRAME_CLOWCARD_UNCOMMON;
                    break;
                default:
                    texture = KSMOD_ImageConst.FRAME_CLOWCARD_RARE;
                    break;
            }
        }
        else
        {
            texture = KSMOD_ImageConst.FRAME_SAKURACARD;
        }
        return texture;
    }

    @SpireOverride
    public void renderPortraitFrame(SpriteBatch sb, float x, float y) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException
    {
        Color renderColor = (Color) KSMOD_ReflectTool.GetFieldByReflect(AbstractCard.class, "renderColor").get(this);
        sb.setColor(renderColor);
        TextureAtlas.AtlasRegion img = KSMOD_RenderTool.GetAtlasRegion(GetFrameImage(), renderedPortionProportionToTop);
        sb.draw(img,
                x + img.offsetX - img.packedWidth / 2.0F,
                y + img.offsetY - img.packedHeight / 2.0F + KSMOD_ImageConst.CLOWCARD_BG.getHeight() * 0.5F * (1F - renderedPortionProportionToTop) * Settings.scale,
                img.packedWidth / 2.0F - img.offsetX,
                img.packedHeight / 2.0F - img.offsetY,
                img.packedWidth,
                img.packedHeight,
                this.drawScale * Settings.scale,
                this.drawScale * Settings.scale,
                this.angle);
    }

    private Texture GetBannerImage()
    {
        Texture texture;
        if (this.color == KSMOD_CustomCardColor.CLOWCARD_COLOR)
        {
            switch (this.rarity)
            {
                case BASIC:
                case COMMON:
                    texture = KSMOD_ImageConst.BANNER_CLOWCARD_COMMON;
                    break;
                case UNCOMMON:
                    texture = KSMOD_ImageConst.BANNER_CLOWCARD_UNCOMMON;
                    break;
                default:
                    texture = KSMOD_ImageConst.BANNER_CLOWCARD_RARE;
                    break;
            }
        }
        else
        {
            texture = KSMOD_ImageConst.BANNER_SAKURACARD;
        }
        return texture;
    }

    @SpireOverride
    public void renderBannerImage(SpriteBatch sb, float drawX, float drawY) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException
    {
        Color renderColor = (Color) KSMOD_ReflectTool.GetFieldByReflect(AbstractCard.class, "renderColor").get(this);
        sb.setColor(renderColor);
        TextureAtlas.AtlasRegion img = KSMOD_RenderTool.GetAtlasRegion(GetBannerImage(),
                renderedPortionProportionToTop);
        sb.draw(img,
                drawX + img.offsetX - (float) img.packedWidth / 2.0F,
                drawY + img.offsetY - (float) img.packedHeight / 2.0F + KSMOD_ImageConst.CLOWCARD_BG.getHeight() * 0.5F * (1F - renderedPortionProportionToTop) * Settings.scale,
                (float) img.packedWidth / 2.0F - img.offsetX,
                (float) img.packedHeight / 2.0F - img.offsetY,
                (float) img.packedWidth,
                (float) img.packedHeight,
                this.drawScale * Settings.scale,
                this.drawScale * Settings.scale,
                this.angle);

    }

    public void renderMask(SpriteBatch sb)
    {
        Texture texture;
        switch (this.type)
        {
            case ATTACK:
                texture = KSMOD_ImageConst.MASK_ATTACK;
                break;
            case SKILL:
                texture = KSMOD_ImageConst.MASK_SKILL;
                break;
            case POWER:
                texture = KSMOD_ImageConst.MASK_POWER;
                break;
            default:
                texture = KSMOD_ImageConst.MASK_SKILL;
                break;
        }
        sb.draw(texture,
                this.current_x - 256.0F,
                this.current_y - 256.0F,
                256.0F,
                256.0F,
                512.0F,
                512.0F,
                this.drawScale * Settings.scale,
                this.drawScale * Settings.scale,
                this.angle,
                0,
                0,
                512,
                512,
                false,
                false);
    }

    @SpireOverride
    public void renderDescription(SpriteBatch sb) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException
    {
        if (CheckIsInHand() || isTurning)
        {
            return;
        }
        if (this.isSeen && !this.isLocked)
        {
            renderMask(sb);
            Method getDescFont = KSMOD_ReflectTool.GetMethodByReflect(AbstractCard.class, "getDescFont");
            BitmapFont font = (BitmapFont) getDescFont.invoke(this);
            float draw_y = this.current_y - IMG_HEIGHT * this.drawScale / 2.0F + IMG_HEIGHT * (0.5F - DESC_OFFSET_TO_BOTTOM_Y) * this.drawScale;
            draw_y += (float) this.description.size() * font.getCapHeight() * 0.775F - font.getCapHeight() * 0.375F;
            float spacing = 1.45F * -font.getCapHeight() / Settings.scale / this.drawScale;

            for (int i = 0; i < this.description.size(); ++i)
            {
                float start_x;
                if (Settings.leftAlignCards)
                {
                    start_x = this.current_x - IMG_WIDTH * DESC_SCALE_RATE_X * this.drawScale / 2.0F + 2.0F * Settings.scale;
                }
                else
                {
                    start_x = this.current_x - this.description.get(i).width * this.drawScale / 2.0F - 4.0F * Settings.scale;
                }

                String[] var7 = ((DescriptionLine) this.description.get(i)).getCachedTokenizedText();
                int var8 = var7.length;
                GlyphLayout gl = (GlyphLayout) KSMOD_ReflectTool.GetFieldByReflect(AbstractCard.class, "gl").get(this);
                for (int var9 = 0; var9 < var8; ++var9)
                {
                    String tmp = var7[var9];
                    Color textColor = (Color) KSMOD_ReflectTool.GetFieldByReflect(AbstractCard.class, "textColor").get(
                            this);
                    if (tmp.length() > 0 && tmp.charAt(0) == '*')
                    {
                        tmp = tmp.substring(1);
                        String punctuation = "";
                        if (tmp.length() > 1 && !Character.isLetter(tmp.charAt(tmp.length() - 2)))
                        {
                            punctuation = punctuation + tmp.charAt(tmp.length() - 2);
                            tmp = tmp.substring(0, tmp.length() - 2);
                            punctuation = punctuation + ' ';
                        }

                        gl.setText(font, tmp);
                        Color goldColor = (Color) KSMOD_ReflectTool.GetFieldByReflect(AbstractCard.class,
                                "goldColor").get(this);
                        FontHelper.renderRotatedText(sb,
                                font,
                                tmp,
                                this.current_x,
                                this.current_y,
                                start_x - this.current_x + gl.width / 2.0F,
                                (float) i * 1.45F * -font.getCapHeight() + draw_y - this.current_y + -6.0F,
                                this.angle,
                                true,
                                goldColor);
                        start_x = (float) Math.round(start_x + gl.width);
                        gl.setText(font, punctuation);
                        FontHelper.renderRotatedText(sb,
                                font,
                                punctuation,
                                this.current_x,
                                this.current_y,
                                start_x - this.current_x + gl.width / 2.0F,
                                (float) i * 1.45F * -font.getCapHeight() + draw_y - this.current_y + -6.0F,
                                this.angle,
                                true,
                                textColor);
                        gl.setText(font, punctuation);
                        start_x += gl.width;
                    }
                    else if (tmp.length() > 0 && tmp.charAt(0) == '!')
                    {
                        Method renderDynamicVariable = KSMOD_ReflectTool.GetMethodByReflect(AbstractCard.class,
                                "renderDynamicVariable",
                                char.class,
                                float.class,
                                float.class,
                                int.class,
                                BitmapFont.class,
                                SpriteBatch.class,
                                Character.class);
                        if (tmp.length() == 4)
                        {
                            start_x += (float) renderDynamicVariable.invoke(this,
                                    tmp.charAt(1),
                                    start_x,
                                    draw_y,
                                    i,
                                    font,
                                    sb,
                                    (Character) null);
                        }
                        else if (tmp.length() == 5)
                        {
                            start_x += (float) renderDynamicVariable.invoke(this,
                                    tmp.charAt(1),
                                    start_x,
                                    draw_y,
                                    i,
                                    font,
                                    sb,
                                    tmp.charAt(3));
                        }
                    }
                    else if (tmp.equals("[R] "))
                    {
                        gl.width = CARD_ENERGY_IMG_WIDTH * this.drawScale;
                        this.renderSmallEnergy(sb,
                                orb_red,
                                (start_x - this.current_x) / Settings.scale / this.drawScale,
                                -100.0F - (((float) this.description.size() - 4.0F) / 2.0F - (float) i + 1.0F) * spacing);
                        start_x += gl.width;
                    }
                    else if (tmp.equals("[R]. "))
                    {
                        gl.width = CARD_ENERGY_IMG_WIDTH * this.drawScale / Settings.scale;
                        this.renderSmallEnergy(sb,
                                orb_red,
                                (start_x - this.current_x) / Settings.scale / this.drawScale,
                                -100.0F - (((float) this.description.size() - 4.0F) / 2.0F - (float) i + 1.0F) * spacing);
                        FontHelper.renderRotatedText(sb,
                                font,
                                LocalizedStrings.PERIOD,
                                this.current_x,
                                this.current_y,
                                start_x - this.current_x + CARD_ENERGY_IMG_WIDTH * this.drawScale,
                                (float) i * 1.45F * -font.getCapHeight() + draw_y - this.current_y + -6.0F,
                                this.angle,
                                true,
                                textColor);
                        start_x += gl.width;
                        gl.setText(font, LocalizedStrings.PERIOD);
                        start_x += gl.width;
                    }
                    else if (tmp.equals("[G] "))
                    {
                        gl.width = CARD_ENERGY_IMG_WIDTH * this.drawScale;
                        this.renderSmallEnergy(sb,
                                orb_green,
                                (start_x - this.current_x) / Settings.scale / this.drawScale,
                                -100.0F - (((float) this.description.size() - 4.0F) / 2.0F - (float) i + 1.0F) * spacing);
                        start_x += gl.width;
                    }
                    else if (tmp.equals("[G]. "))
                    {
                        gl.width = CARD_ENERGY_IMG_WIDTH * this.drawScale;
                        this.renderSmallEnergy(sb,
                                orb_green,
                                (start_x - this.current_x) / Settings.scale / this.drawScale,
                                -100.0F - (((float) this.description.size() - 4.0F) / 2.0F - (float) i + 1.0F) * spacing);
                        FontHelper.renderRotatedText(sb,
                                font,
                                LocalizedStrings.PERIOD,
                                this.current_x,
                                this.current_y,
                                start_x - this.current_x + CARD_ENERGY_IMG_WIDTH * this.drawScale,
                                (float) i * 1.45F * -font.getCapHeight() + draw_y - this.current_y + -6.0F,
                                this.angle,
                                true,
                                textColor);
                        start_x += gl.width;
                    }
                    else if (tmp.equals("[B] "))
                    {
                        gl.width = CARD_ENERGY_IMG_WIDTH * this.drawScale;
                        this.renderSmallEnergy(sb,
                                orb_blue,
                                (start_x - this.current_x) / Settings.scale / this.drawScale,
                                -100.0F - (((float) this.description.size() - 4.0F) / 2.0F - (float) i + 1.0F) * spacing);
                        start_x += gl.width;
                    }
                    else if (tmp.equals("[B]. "))
                    {
                        gl.width = CARD_ENERGY_IMG_WIDTH * this.drawScale;
                        this.renderSmallEnergy(sb,
                                orb_blue,
                                (start_x - this.current_x) / Settings.scale / this.drawScale,
                                -100.0F - (((float) this.description.size() - 4.0F) / 2.0F - (float) i + 1.0F) * spacing);
                        FontHelper.renderRotatedText(sb,
                                font,
                                LocalizedStrings.PERIOD,
                                this.current_x,
                                this.current_y,
                                start_x - this.current_x + CARD_ENERGY_IMG_WIDTH * this.drawScale,
                                (float) i * 1.45F * -font.getCapHeight() + draw_y - this.current_y + -6.0F,
                                this.angle,
                                true,
                                textColor);
                        start_x += gl.width;
                    }
                    else
                    {
                        gl.setText(font, tmp);
                        FontHelper.renderRotatedText(sb,
                                font,
                                tmp,
                                this.current_x,
                                this.current_y,
                                start_x - this.current_x + gl.width / 2.0F,
                                (float) i * 1.45F * -font.getCapHeight() + draw_y - this.current_y + -6.0F,
                                this.angle,
                                true,
                                textColor);
                        start_x += gl.width;
                    }
                }
            }

            font.getData().setScale(1.0F);
        }
        // else
        // {
        //     FontHelper.menuBannerFont.getData().setScale(this.drawScale * 1.25F);
        //     Color textColor = (Color) KSMOD_ReflectTool.GetFieldByReflect(AbstractCard.class, "textColor").get(this);
        //     FontHelper.renderRotatedText(sb, FontHelper.menuBannerFont, "? ? ?", this.current_x, this.current_y, 0.0F, -200.0F * Settings.scale * this.drawScale / 2.0F, this.angle, true, textColor);
        //     FontHelper.menuBannerFont.getData().setScale(1.0F);
        // }
    }

    @SpireOverride
    public void renderDescriptionCN(SpriteBatch sb) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException
    {
        if (CheckIsInHand() || isTurning)
        {
            return;
        }
        if (this.isSeen && !this.isLocked)
        {
            renderMask(sb);
            Method getDescFont = KSMOD_ReflectTool.GetMethodByReflect(AbstractCard.class, "getDescFont");
            BitmapFont font = (BitmapFont) getDescFont.invoke(this);
            float draw_y = this.current_y - IMG_HEIGHT * this.drawScale / 2.0F + IMG_HEIGHT * (0.5F - DESC_OFFSET_TO_BOTTOM_Y) * this.drawScale;
            draw_y += (float) this.description.size() * font.getCapHeight() * 0.775F - font.getCapHeight() * 0.375F;
            float spacing = 1.45F * -font.getCapHeight() / Settings.scale / this.drawScale;

            for (int i = 0; i < this.description.size(); ++i)
            {
                float start_x;
                if (Settings.leftAlignCards)
                {
                    start_x = this.current_x - IMG_WIDTH * DESC_SCALE_RATE_X * this.drawScale / 2.0F + 2.0F * Settings.scale;
                }
                else
                {
                    start_x = this.current_x - this.description.get(i).width * this.drawScale / 2.0F - 14.0F * Settings.scale;
                }

                String[] var7 = this.description.get(i).getCachedTokenizedTextCN();
                int var8 = var7.length;
                Method getDynamicValue = KSMOD_ReflectTool.GetMethodByReflect(AbstractCard.class,
                        "getDynamicValue",
                        char.class);
                GlyphLayout gl = (GlyphLayout) KSMOD_ReflectTool.GetFieldByReflect(AbstractCard.class, "gl").get(this);
                for (int var9 = 0; var9 < var8; ++var9)
                {
                    String tmp = var7[var9];
                    String updateTmp = null;

                    ////// Patch From RenderCustomDynamicVariableCN
                    if (tmp.startsWith("$"))
                    {
                        String key = tmp;
                        Pattern pattern = Pattern.compile("\\$(.+)\\$\\$");
                        Matcher matcher = pattern.matcher(key);
                        if (matcher.find())
                        {
                            key = matcher.group(1);
                        }

                        DynamicVariable dv = (DynamicVariable) BaseMod.cardDynamicVariableMap.get(key);
                        if (dv != null)
                        {
                            if (dv.isModified(this))
                            {
                                if (dv.value(this) >= dv.baseValue(this))
                                {
                                    tmp = "[#" + dv.getIncreasedValueColor().toString() + "]" + Integer.toString(dv.value(
                                            this)) + "[]";
                                }
                                else
                                {
                                    tmp = "[#" + dv.getDecreasedValueColor().toString() + "]" + Integer.toString(dv.value(
                                            this)) + "[]";
                                }
                            }
                            else
                            {
                                tmp = Integer.toString(dv.baseValue(this));
                            }
                        }
                    }
                    ////// Patch End

                    int j;
                    for (j = 0; j < tmp.length(); ++j)
                    {
                        if (tmp.charAt(j) == 'D' || tmp.charAt(j) == 'B' && !tmp.contains("[B]") || tmp.charAt(j) == 'M')
                        {
                            updateTmp = tmp.substring(0, j);
                            updateTmp = updateTmp + getDynamicValue.invoke(this, tmp.charAt(j));
                            updateTmp = updateTmp + tmp.substring(j + 1);
                            break;
                        }
                    }

                    if (updateTmp != null)
                    {
                        tmp = updateTmp;
                    }

                    for (j = 0; j < tmp.length(); ++j)
                    {
                        if (tmp.charAt(j) == 'D' || tmp.charAt(j) == 'B' && !tmp.contains("[B]") || tmp.charAt(j) == 'M')
                        {
                            updateTmp = tmp.substring(0, j);
                            updateTmp = updateTmp + getDynamicValue.invoke(this, tmp.charAt(j));
                            updateTmp = updateTmp + tmp.substring(j + 1);
                            break;
                        }
                    }

                    if (updateTmp != null)
                    {
                        tmp = updateTmp;
                    }

                    if (tmp.length() > 0 && tmp.charAt(0) == '*')
                    {
                        tmp = tmp.substring(1);
                        String punctuation = "";
                        if (tmp.length() > 1 && !Character.isLetter(tmp.charAt(tmp.length() - 2)))
                        {
                            punctuation = punctuation + tmp.charAt(tmp.length() - 2);
                            tmp = tmp.substring(0, tmp.length() - 2);
                            punctuation = punctuation + ' ';
                        }

                        gl.setText(font, tmp);
                        Color goldColor = (Color) KSMOD_ReflectTool.GetFieldByReflect(AbstractCard.class,
                                "goldColor").get(this);
                        FontHelper.renderRotatedText(sb,
                                font,
                                tmp,
                                this.current_x,
                                this.current_y,
                                start_x - this.current_x + gl.width / 2.0F,
                                (float) i * 1.45F * -font.getCapHeight() + draw_y - this.current_y + -6.0F,
                                this.angle,
                                true,
                                goldColor);
                        start_x = (float) Math.round(start_x + gl.width);
                        gl.setText(font, punctuation);
                        Color textColor = (Color) KSMOD_ReflectTool.GetFieldByReflect(AbstractCard.class,
                                "textColor").get(this);
                        FontHelper.renderRotatedText(sb,
                                font,
                                punctuation,
                                this.current_x,
                                this.current_y,
                                start_x - this.current_x + gl.width / 2.0F,
                                (float) i * 1.45F * -font.getCapHeight() + draw_y - this.current_y + -6.0F,
                                this.angle,
                                true,
                                textColor);
                        gl.setText(font, punctuation);
                        start_x += gl.width;
                    }
                    else if (tmp.equals("[R]"))
                    {
                        gl.width = CARD_ENERGY_IMG_WIDTH * this.drawScale;
                        this.renderSmallEnergy(sb,
                                orb_red,
                                (start_x - this.current_x) / Settings.scale / this.drawScale,
                                -100.0F - (((float) this.description.size() - 4.0F) / 2.0F - (float) i + 1.0F) * spacing);
                        start_x += gl.width;
                    }
                    else if (tmp.equals("[G]"))
                    {
                        gl.width = CARD_ENERGY_IMG_WIDTH * this.drawScale;
                        this.renderSmallEnergy(sb,
                                orb_green,
                                (start_x - this.current_x) / Settings.scale / this.drawScale,
                                -100.0F - (((float) this.description.size() - 4.0F) / 2.0F - (float) i + 1.0F) * spacing);
                        start_x += gl.width;
                    }
                    else if (tmp.equals("[B]"))
                    {
                        gl.width = CARD_ENERGY_IMG_WIDTH * this.drawScale;
                        this.renderSmallEnergy(sb,
                                orb_blue,
                                (start_x - this.current_x) / Settings.scale / this.drawScale,
                                -100.0F - (((float) this.description.size() - 4.0F) / 2.0F - (float) i + 1.0F) * spacing);
                        start_x += gl.width;
                    }
                    else
                    {
                        gl.setText(font, tmp);
                        Color textColor = (Color) KSMOD_ReflectTool.GetFieldByReflect(AbstractCard.class,
                                "textColor").get(this);
                        FontHelper.renderRotatedText(sb,
                                font,
                                tmp,
                                this.current_x,
                                this.current_y,
                                start_x - this.current_x + gl.width / 2.0F,
                                (float) i * 1.45F * -font.getCapHeight() + draw_y - this.current_y + -6.0F,
                                this.angle,
                                true,
                                textColor);
                        start_x += gl.width;
                    }
                }
            }

            font.getData().setScale(1.0F);
        }
        // else
        // {
        //     FontHelper.menuBannerFont.getData().setScale(this.drawScale * 1.25F);
        //     Color textColor = (Color) KSMOD_ReflectTool.GetFieldByReflect(AbstractCard.class, "textColor").get(this);
        //     FontHelper.renderRotatedText(sb, FontHelper.menuBannerFont, "? ? ?", this.current_x, this.current_y, 0.0F, -200.0F * Settings.scale * this.drawScale / 2.0F, this.angle, true, textColor);
        //     FontHelper.menuBannerFont.getData().setScale(1.0F);
        // }
    }

    private boolean CheckIsInHand()
    {
        return AbstractDungeon.player != null && // player存在
                AbstractDungeon.player.hand.contains(this) && //此牌在手牌中
                !this.isHandSelectScreenOpened && // 卡牌选择界面未打开
                AbstractDungeon.player.hoveredCard != this; // 鼠标没有悬停在牌上
    }

    private String GetCurrentDescription()
    {
        String desc;
        if (this.hasExtraEffect && isThisCardCharged() && !hasLockPower())
        {
            desc = this.getExtraDescription();
            if (this.hasReleased)
            {
                desc = KSMOD_ReleaseAction.reloadReleasedCardDescription(desc, !this.isEthereal, !this.exhaust);
            }
        }
        else
        {
            desc = this.rawDescription;
        }
        return desc;
    }

    @Override
    public void initializeDescription()
    {
        try
        {
            this.keywords.clear();
            if (Settings.lineBreakViaCharacter)
            {
                this.initializeDescriptionCN();
            }
            else
            {
                this.description.clear();
                int numLines = 1;
                StringBuilder sbuilder = (StringBuilder) KSMOD_ReflectTool.GetFieldByReflect(AbstractCard.class,
                        "sbuilder").get(this);
                sbuilder.setLength(0);
                float currentWidth = 0.0F;
                String[] var4 = GetCurrentDescription().split(" ");
                int var5 = var4.length;

                for (int var6 = 0; var6 < var5; ++var6)
                {
                    String word = var4[var6];
                    boolean isKeyword = false;
                    StringBuilder sbuilder2 = (StringBuilder) KSMOD_ReflectTool.GetFieldByReflect(AbstractCard.class,
                            "sbuilder2").get(this);
                    sbuilder2.setLength(0);
                    sbuilder2.append(" ");
                    if (word.length() > 0 && word.charAt(word.length() - 1) != ']' && !Character.isLetterOrDigit(word.charAt(
                            word.length() - 1)))
                    {
                        sbuilder2.insert(0, word.charAt(word.length() - 1));
                        word = word.substring(0, word.length() - 1);
                    }

                    GlyphLayout gl = (GlyphLayout) KSMOD_ReflectTool.GetFieldByReflect(AbstractCard.class, "gl").get(
                            this);
                    Method dedupeKeyword = KSMOD_ReflectTool.GetMethodByReflect(AbstractCard.class,
                            "dedupeKeyword",
                            String.class);
                    String keywordTmp = word.toLowerCase();
                    keywordTmp = (String) dedupeKeyword.invoke(this, keywordTmp);
                    GlyphLayout var10000;
                    if (GameDictionary.keywords.containsKey(keywordTmp))
                    {
                        if (!this.keywords.contains(keywordTmp))
                        {
                            this.keywords.add(keywordTmp);
                        }

                        gl.reset();
                        gl.setText(FontHelper.cardDescFont_N, sbuilder2);
                        float tmp = gl.width;
                        gl.setText(FontHelper.cardDescFont_N, word);
                        var10000 = gl;
                        var10000.width += tmp;
                        isKeyword = true;
                    }
                    else if (!word.equals("[R]") && !word.equals("[G]") && !word.equals("[B]"))
                    {
                        if (word.equals("!D"))
                        {
                            gl.setText(FontHelper.cardDescFont_N, word);
                        }
                        else if (word.equals("!B"))
                        {
                            gl.setText(FontHelper.cardDescFont_N, word);
                        }
                        else if (word.equals("!M"))
                        {
                            gl.setText(FontHelper.cardDescFont_N, word);
                        }
                        else if (word.equals("NL"))
                        {
                            gl.width = 0.0F;
                            word = "";
                            this.description.add(new DescriptionLine(sbuilder.toString().trim(), currentWidth));
                            currentWidth = 0.0F;
                            ++numLines;
                            sbuilder.setLength(0);
                        }
                        else
                        {
                            gl.setText(FontHelper.cardDescFont_N, word + sbuilder2);
                        }
                    }
                    else
                    {
                        gl.reset();
                        gl.setText(FontHelper.cardDescFont_N, sbuilder2);
                        var10000 = gl;
                        var10000.width += CARD_ENERGY_IMG_WIDTH;
                        switch (this.color)
                        {
                            case RED:
                                if (!this.keywords.contains("[R]"))
                                {
                                    this.keywords.add("[R]");
                                }
                                break;
                            case GREEN:
                                if (!this.keywords.contains("[G]"))
                                {
                                    this.keywords.add("[G]");
                                }
                                break;
                            case BLUE:
                                if (!this.keywords.contains("[B]"))
                                {
                                    this.keywords.add("[B]");
                                }
                                break;
                            default:
                                KSMOD_LoggerTool.Logger.info("ERROR: Tried to display an invalid energy type");
                        }
                    }

                    if (currentWidth + gl.width > DESC_LINE_WIDTH * 1.1F)
                    {
                        this.description.add(new DescriptionLine(sbuilder.toString().trim(), currentWidth));
                        ++numLines;
                        sbuilder.setLength(0);
                        currentWidth = gl.width;
                    }
                    else
                    {
                        currentWidth += gl.width;
                    }

                    if (isKeyword)
                    {
                        sbuilder.append('*');
                    }

                    sbuilder.append(word).append(sbuilder2);
                }

                if (!sbuilder.toString().trim().isEmpty())
                {
                    this.description.add(new DescriptionLine(sbuilder.toString().trim(), currentWidth));
                }

                if (numLines > 5)
                {
                    KSMOD_LoggerTool.Logger.info("WARNING: Card " + this.name + " has lots of text");
                }

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void initializeDescriptionCN()
    {
        try
        {
            this.description.clear();
            int numLines = 1;
            StringBuilder sbuilder = (StringBuilder) KSMOD_ReflectTool.GetFieldByReflect(AbstractCard.class,
                    "sbuilder").get(this);
            sbuilder.setLength(0);
            float currentWidth = 0.0F;
            String[] var3 = GetCurrentDescription().split(" ");
            int var4 = var3.length;

            for (int var5 = 0; var5 < var4; ++var5)
            {
                String word = var3[var5];
                word = word.trim();
                if (Settings.manualLineBreak || !word.contains("NL"))
                {
                    Method dedupeKeyword = KSMOD_ReflectTool.GetMethodByReflect(AbstractCard.class,
                            "dedupeKeyword",
                            String.class);
                    GlyphLayout gl = (GlyphLayout) KSMOD_ReflectTool.GetFieldByReflect(AbstractCard.class, "gl").get(
                            this);
                    String keywordTmp = word.toLowerCase();
                    keywordTmp = (String) dedupeKeyword.invoke(this, keywordTmp);
                    if (GameDictionary.keywords.containsKey(keywordTmp))
                    {
                        if (!this.keywords.contains(keywordTmp))
                        {
                            this.keywords.add(keywordTmp);
                        }

                        gl.setText(FontHelper.cardDescFont_N, word);
                        if (currentWidth + gl.width > DESC_LINE_WIDTH)
                        {
                            ++numLines;
                            this.description.add(new DescriptionLine(sbuilder.toString(), currentWidth));
                            sbuilder.setLength(0);
                            currentWidth = gl.width;
                            sbuilder.append(" *").append(word).append(" ");
                        }
                        else
                        {
                            sbuilder.append(" *").append(word).append(" ");
                            currentWidth += gl.width;
                        }
                    }
                    else if (!word.equals("[R]") && !word.equals("[G]") && !word.equals("[B]"))
                    {
                        if (word.equals("!D!"))
                        {
                            gl.setText(FontHelper.cardDescFont_N, word);
                            if (currentWidth + gl.width > DESC_LINE_WIDTH)
                            {
                                ++numLines;
                                this.description.add(new DescriptionLine(sbuilder.toString(), currentWidth));
                                sbuilder.setLength(0);
                                currentWidth = gl.width;
                                sbuilder.append(" D ");
                            }
                            else
                            {
                                sbuilder.append(" D ");
                                currentWidth += gl.width;
                            }
                        }
                        else if (word.equals("!B!"))
                        {
                            gl.setText(FontHelper.cardDescFont_N, word);
                            if (currentWidth + gl.width > DESC_LINE_WIDTH)
                            {
                                ++numLines;
                                this.description.add(new DescriptionLine(sbuilder.toString(), currentWidth));
                                sbuilder.setLength(0);
                                currentWidth = gl.width;
                                sbuilder.append(" ").append(word).append("! ");
                            }
                            else
                            {
                                sbuilder.append(" ").append(word).append("! ");
                                currentWidth += gl.width;
                            }
                        }
                        else if (word.equals("!M!"))
                        {
                            gl.setText(FontHelper.cardDescFont_N, word);
                            if (currentWidth + gl.width > DESC_LINE_WIDTH)
                            {
                                ++numLines;
                                this.description.add(new DescriptionLine(sbuilder.toString(), currentWidth));
                                sbuilder.setLength(0);
                                currentWidth = gl.width;
                                sbuilder.append(" ").append(word).append("! ");
                            }
                            else
                            {
                                sbuilder.append(" ").append(word).append("! ");
                                currentWidth += gl.width;
                            }
                        }
                        else if (Settings.manualLineBreak && word.equals("NL"))
                        {
                            gl.width = 0.0F;
                            word = "";
                            this.description.add(new DescriptionLine(sbuilder.toString().trim(), currentWidth));
                            currentWidth = 0.0F;
                            ++numLines;
                            sbuilder.setLength(0);
                        }
                        else
                        {
                            word = word.trim();
                            char[] var8 = word.toCharArray();
                            int var9 = var8.length;

                            for (int var10 = 0; var10 < var9; ++var10)
                            {
                                char c = var8[var10];
                                gl.setText(FontHelper.cardDescFont_N, String.valueOf(c));
                                sbuilder.append(c);
                                if (!Settings.manualLineBreak)
                                {
                                    if (currentWidth + gl.width > DESC_LINE_WIDTH)
                                    {
                                        ++numLines;
                                        this.description.add(new DescriptionLine(sbuilder.toString(), currentWidth));
                                        sbuilder.setLength(0);
                                        currentWidth = gl.width;
                                    }
                                    else
                                    {
                                        currentWidth += gl.width;
                                    }
                                }
                            }
                        }
                    }
                    else
                    {
                        switch (this.color)
                        {
                            case RED:
                                if (!this.keywords.contains("[R]"))
                                {
                                    this.keywords.add("[R]");
                                }
                                break;
                            case GREEN:
                                if (!this.keywords.contains("[G]"))
                                {
                                    this.keywords.add("[G]");
                                }
                                break;
                            case BLUE:
                                if (!this.keywords.contains("[B]"))
                                {
                                    this.keywords.add("[B]");
                                }
                                break;
                            default:
                                KSMOD_LoggerTool.Logger.info("ERROR: Tried to display an invalid energy type");
                        }

                        if (currentWidth + CARD_ENERGY_IMG_WIDTH > DESC_LINE_WIDTH)
                        {
                            ++numLines;
                            this.description.add(new DescriptionLine(sbuilder.toString(), currentWidth));
                            sbuilder.setLength(0);
                            currentWidth = CARD_ENERGY_IMG_WIDTH;
                            sbuilder.append(" ").append(word).append(" ");
                        }
                        else
                        {
                            sbuilder.append(" ").append(word).append(" ");
                            currentWidth += CARD_ENERGY_IMG_WIDTH;
                        }
                    }
                }
            }

            if (sbuilder.length() != 0)
            {
                this.description.add(new DescriptionLine(sbuilder.toString(), currentWidth));
            }

            if (sbuilder.toString().equals(LocalizedStrings.PERIOD))
            {
                this.description.set(this.description.size() - 2,
                        new DescriptionLine(this.description.get(this.description.size() - 2).getText() + LocalizedStrings.PERIOD,
                                this.description.get(this.description.size() - 2).width));
                this.description.remove(this.description.size() - 1);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void update()
    {
        try
        {
            Method updateFlashVfx = KSMOD_ReflectTool.GetMethodByReflect(AbstractCard.class, "updateFlashVfx");
            updateFlashVfx.invoke(this);
            if (this.hoverTimer != 0.0F)
            {
                this.hoverTimer -= Gdx.graphics.getDeltaTime();
                if (this.hoverTimer < 0.0F)
                {
                    this.hoverTimer = 0.0F;
                }
            }

            if (AbstractDungeon.player != null && AbstractDungeon.player.isDraggingCard && this == AbstractDungeon.player.hoveredCard)
            {
                this.current_x = MathHelper.cardLerpSnap(this.current_x, this.target_x);
                this.current_y = MathHelper.cardLerpSnap(this.current_y, this.target_y);
                if (AbstractDungeon.player.hasRelic("Necronomicon"))
                {
                    if (this.cost >= 2 && this.type == AbstractCard.CardType.ATTACK && AbstractDungeon.player.getRelic(
                            "Necronomicon").checkTrigger())
                    {
                        AbstractDungeon.player.getRelic("Necronomicon").beginLongPulse();
                    }
                    else
                    {
                        AbstractDungeon.player.getRelic("Necronomicon").stopPulse();
                    }
                }
            }

            if (Settings.FAST_MODE)
            {
                this.current_x = MathHelper.cardLerpSnap(this.current_x, this.target_x);
                this.current_y = MathHelper.cardLerpSnap(this.current_y, this.target_y);
            }

            this.current_x = MathHelper.cardLerpSnap(this.current_x, this.target_x);
            this.current_y = MathHelper.cardLerpSnap(this.current_y, this.target_y);
            this.hb.move(this.current_x, this.current_y);
            this.hb.resize(HB_W * this.drawScale, HB_H * this.drawScale);
            if (this.hb.clickStarted && this.hb.hovered)
            {
                this.drawScale = MathHelper.cardScaleLerpSnap(this.drawScale, this.targetDrawScale * 0.9F);
                this.drawScale = MathHelper.cardScaleLerpSnap(this.drawScale, this.targetDrawScale * 0.9F);
            }
            else
            {
                this.drawScale = MathHelper.cardScaleLerpSnap(this.drawScale, this.targetDrawScale);
            }

            if (this.angle != this.targetAngle)
            {
                this.angle = MathHelper.angleLerpSnap(this.angle, this.targetAngle);
            }

            Method updateTransparency = KSMOD_ReflectTool.GetMethodByReflect(AbstractCard.class, "updateTransparency");
            Method updateColor = KSMOD_ReflectTool.GetMethodByReflect(AbstractCard.class, "updateColor");
            updateTransparency.invoke(this);
            updateColor.invoke(this);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @SpireOverride
    public void renderTitle(SpriteBatch sb)
    {
        try
        {
            BitmapFont font;
            Color renderColor = (Color) KSMOD_ReflectTool.GetFieldByReflect(AbstractCard.class,
                    "renderColor").get(this);
            boolean useSmallTitleFont = KSMOD_ReflectTool.GetFieldByReflect(AbstractCard.class,
                    "useSmallTitleFont").getBoolean(this);
            font = useSmallTitleFont ? FontHelper.cardTitleFont_small : FontHelper.cardTitleFont;
            font.getData().setScale(this.drawScale);

            // render Title
            float offsetToTop = this.color == KSMOD_CustomCardColor.CLOWCARD_COLOR ? TITLE_HEIGHT_TO_CENTER : TITLE_HEIGHT_SAKURA_TO_CENTER;
            if (renderedPortionProportionToTop > 0.5F - (offsetToTop - font.getLineHeight() * 0.5F) / IMG_HEIGHT)
            {
                if (this.upgraded)
                {
                    Color color = Settings.GREEN_TEXT_COLOR.cpy();
                    color.a = renderColor.a;
                    FontHelper.renderRotatedText(sb,
                            font,
                            this.name,
                            this.current_x,
                            this.current_y,
                            0.0F,
                            offsetToTop * this.drawScale * Settings.scale,
                            this.angle,
                            false,
                            color);
                }
                else
                {
                    FontHelper.renderRotatedText(sb,
                            font,
                            this.name,
                            this.current_x,
                            this.current_y,
                            0.0F,
                            offsetToTop * this.drawScale * Settings.scale,
                            this.angle,
                            false,
                            renderColor);
                }
            }

            // render Bottom Title
            float offsetToBottom = this.color == KSMOD_CustomCardColor.CLOWCARD_COLOR ? TITLE_BOTTOM_HEIGHT_TO_CENTER : TITLE_BOTTOM_HEIGHT_SAKURA_TO_CENTER;
            if (renderedPortionProportionToTop > 0.5F - (offsetToBottom - font.getLineHeight() * 0.5F) / IMG_HEIGHT)
            {
                if (this.upgraded)
                {
                    Color color = Settings.GREEN_TEXT_COLOR.cpy();
                    color.a = renderColor.a;
                    FontHelper.renderRotatedText(sb,
                            font,
                            BOTTOM_TITLE,
                            this.current_x,
                            this.current_y,
                            0.0F,
                            offsetToBottom * this.drawScale * Settings.scale,
                            this.angle,
                            false,
                            color);
                }
                else
                {
                    FontHelper.renderRotatedText(sb,
                            font,
                            BOTTOM_TITLE,
                            this.current_x,
                            this.current_y,
                            0.0F,
                            offsetToBottom * this.drawScale * Settings.scale,
                            this.angle,
                            false,
                            renderColor);
                }
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @SpireOverride
    public void renderPortrait(SpriteBatch sb) throws NoSuchFieldException, IllegalAccessException
    {
        KSMOD_RenderTool.SetAtlasRegion(this.portrait, KSMOD_ImageConst.CLOWCARD_BG.getHeight(), PORTRAIT_ORIGIN_Y, renderedPortionProportionToTop);
        sb.draw(this.portrait,
                this.current_x - PORTRAIT_ORIGIN_X,
                this.current_y - PORTRAIT_ORIGIN_Y + (PORTRAIT_HEIGHT - this.portrait.getRegionHeight()) * Settings.scale,
                PORTRAIT_ORIGIN_X,
                PORTRAIT_ORIGIN_Y,
                PORTRAIT_WIDTH,
                this.portrait.getRegionHeight(),
                this.drawScale * Settings.scale,
                this.drawScale * Settings.scale,
                this.angle);
    }

    @SpireOverride
    public void renderType(SpriteBatch sb)
    {
    }

    @SpireOverride
    public void renderDynamicFrame(SpriteBatch sb, float x, float y, float typeOffset, float typeWidth)
    {
    }

    @Override
    public Texture getCardBg()
    {
        switch (this.type)
        {
            case ATTACK:
            case SKILL:
            case POWER:
                return KSMOD_ImageConst.SILHOUETTE;
            default:
                return null;
        }
    }

    @Override
    public AtlasRegion getCardBgAtlas()
    {
        return KSMOD_ImageConst.SILHOUETTE_ATLAS;
    }

    @SpireOverride
    public void renderMainBorder(SpriteBatch sb) throws NoSuchFieldException, IllegalAccessException
    {
        if (this.isGlowing)
        {
            sb.setBlendFunction(770, 1);
            AtlasRegion img = KSMOD_ImageConst.SILHOUETTE_ATLAS;
            if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT)
            {
                Color BLUE_BORDER_GLOW_COLOR = (Color) KSMOD_ReflectTool.GetFieldByReflect(AbstractCard.class,
                        "BLUE_BORDER_GLOW_COLOR").get(this);
                sb.setColor(hasExtraEffect && isThisCardCharged() ? Color.GOLD : BLUE_BORDER_GLOW_COLOR);
            }
            else
            {
                Color GREEN_BORDER_GLOW_COLOR = (Color) KSMOD_ReflectTool.GetFieldByReflect(AbstractCard.class,
                        "GREEN_BORDER_GLOW_COLOR").get(this);
                sb.setColor(GREEN_BORDER_GLOW_COLOR);
            }

            sb.draw(img,
                    this.current_x + img.offsetX - (float) img.originalWidth / 2.0F,
                    this.current_y + img.offsetY - (float) img.originalWidth / 2.0F,
                    (float) img.originalWidth / 2.0F - img.offsetX,
                    (float) img.originalWidth / 2.0F - img.offsetY,
                    (float) img.packedWidth,
                    (float) img.packedHeight,
                    this.drawScale * Settings.scale * 1.04F,
                    this.drawScale * Settings.scale * 1.03F,
                    this.angle);
        }
    }

    @SpireOverride
    public void renderImage(SpriteBatch sb, boolean hovered, boolean selected) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException
    {
        if (!isTurning)
        {
            Method renderHelper1408 = KSMOD_ReflectTool.GetMethodByReflect(AbstractCard.class,
                    "renderHelper",
                    SpriteBatch.class,
                    Color.class,
                    AtlasRegion.class,
                    float.class,
                    float.class);
            Method renderHelper1413 = KSMOD_ReflectTool.GetMethodByReflect(AbstractCard.class,
                    "renderHelper",
                    SpriteBatch.class,
                    Color.class,
                    AtlasRegion.class,
                    float.class,
                    float.class,
                    float.class);
            if (AbstractDungeon.player != null)
            {
                if (selected)
                {
                    renderHelper1413.invoke(this,
                            sb,
                            Color.SKY,
                            this.getCardBgAtlas(),
                            this.current_x,
                            this.current_y,
                            1.03F);
                }
                Color frameShadowColor = (Color) KSMOD_ReflectTool.GetFieldByReflect(AbstractCard.class,
                        "frameShadowColor").get(this);
                float SHADOW_OFFSET_X = KSMOD_ReflectTool.GetFieldByReflect(AbstractCard.class,
                        "SHADOW_OFFSET_X").getFloat(this);
                float SHADOW_OFFSET_Y = KSMOD_ReflectTool.GetFieldByReflect(AbstractCard.class,
                        "SHADOW_OFFSET_Y").getFloat(this);
                renderHelper1408.invoke(this,
                        sb,
                        frameShadowColor,
                        this.getCardBgAtlas(),
                        this.current_x + SHADOW_OFFSET_X * this.drawScale,
                        this.current_y - SHADOW_OFFSET_Y * this.drawScale);
                if (AbstractDungeon.player.hoveredCard == this && (AbstractDungeon.player.isDraggingCard && AbstractDungeon.player.isHoveringDropZone || AbstractDungeon.player.inSingleTargetMode))
                {
                    Color HOVER_IMG_COLOR = (Color) KSMOD_ReflectTool.GetFieldByReflect(AbstractCard.class,
                            "HOVER_IMG_COLOR").get(this);
                    renderHelper1408.invoke(this,
                            sb,
                            HOVER_IMG_COLOR,
                            this.getCardBgAtlas(),
                            this.current_x,
                            this.current_y);
                }
                else if (selected)
                {
                    Color SELECTED_CARD_COLOR = (Color) KSMOD_ReflectTool.GetFieldByReflect(AbstractCard.class,
                            "SELECTED_CARD_COLOR").get(this);
                    renderHelper1408.invoke(this,
                            sb,
                            SELECTED_CARD_COLOR,
                            this.getCardBgAtlas(),
                            this.current_x,
                            this.current_y);
                }
            }
        }

        Method renderCardBg = KSMOD_ReflectTool.GetMethodByReflect(AbstractCard.class,
                "renderCardBg",
                SpriteBatch.class,
                float.class,
                float.class);
        renderCardBg.invoke(this, sb, this.current_x, this.current_y);
        if (!UnlockTracker.betaCardPref.getBoolean(this.cardID, false) && !Settings.PLAYTESTER_ART_MODE)
        {
            this.renderPortrait(sb);
        }
        else
        {
            Method renderJokePortrait = KSMOD_ReflectTool.GetMethodByReflect(AbstractCard.class,
                    "renderJokePortrait",
                    SpriteBatch.class);
            renderJokePortrait.invoke(this, sb);
        }

        this.renderPortraitFrame(sb, this.current_x, this.current_y);
        this.renderBannerImage(sb, this.current_x, this.current_y);
    }
}
