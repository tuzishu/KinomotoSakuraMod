package KinomotoSakuraMod.Effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.DamageImpactCurvyEffect;

public class KSMOD_PurgeCardEffect extends AbstractGameEffect
{
    private AbstractCard card;
    private float scaleY;
    private Color rarityColor;

    public KSMOD_PurgeCardEffect(AbstractCard card, float targetX, float targetY)
    {
        this(card, Settings.WIDTH - 96.0F * Settings.scale, Settings.HEIGHT - 32.0F * Settings.scale, targetX, targetY);
    }

    public KSMOD_PurgeCardEffect(AbstractCard card, float startX, float startY, float targetX, float targetY)
    {
        this.card = card;
        this.startingDuration = 2.0F;
        this.duration = this.startingDuration;
        this.card.current_x = startX;
        this.card.current_y = startY;
        this.card.target_x = targetX;
        this.card.target_y = targetY;
        card.drawScale = 0.01F;
        card.targetDrawScale = 1.0F;
        CardCrawlGame.sound.play("CARD_BURN");
        this.initializeVfx();
    }

    private void initializeVfx()
    {
        switch (this.card.rarity)
        {
            case UNCOMMON:
                this.rarityColor = new Color(0.2F, 0.8F, 0.8F, 0.01F);
                break;
            case RARE:
                this.rarityColor = new Color(0.8F, 0.7F, 0.2F, 0.01F);
                break;
            case BASIC:
            case COMMON:
            case CURSE:
            case SPECIAL:
            default:
                this.rarityColor = new Color(0.6F, 0.6F, 0.6F, 0.01F);
        }

        switch (this.card.color)
        {
            case BLUE:
                this.color = new Color(0.1F, 0.4F, 0.7F, 0.01F);
                break;
            case COLORLESS:
                this.color = new Color(0.4F, 0.4F, 0.4F, 0.01F);
                break;
            case GREEN:
                this.color = new Color(0.2F, 0.7F, 0.2F, 0.01F);
                break;
            case RED:
                this.color = new Color(0.9F, 0.3F, 0.2F, 0.01F);
                break;
            case CURSE:
            default:
                this.color = new Color(0.2F, 0.15F, 0.2F, 0.01F);
        }

        this.scale = Settings.scale;
        this.scaleY = Settings.scale;
    }

    public void update()
    {
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.5F)
        {
            if (!this.card.fadingOut)
            {
                this.card.fadingOut = true;
                if (!Settings.DISABLE_EFFECTS)
                {
                    int i;
                    for (i = 0; i < 16; ++i)
                    {
                        AbstractDungeon.topLevelEffectsQueue.add(new DamageImpactCurvyEffect(this.card.current_x, this.card.current_y, this.color, false));
                    }

                    for (i = 0; i < 8; ++i)
                    {
                        AbstractDungeon.effectsQueue.add(new DamageImpactCurvyEffect(this.card.current_x, this.card.current_y, this.rarityColor, false));
                    }
                }
            }

            this.updateVfx();
        }

        this.card.update();
        if (this.duration < 0.0F)
        {
            this.isDone = true;
        }

    }

    private void updateVfx()
    {
        this.color.a = MathHelper.fadeLerpSnap(this.color.a, 0.5F);
        this.rarityColor.a = this.color.a;
        this.scale = Interpolation.swingOut.apply(1.6F, 1.0F, this.duration * 2.0F) * Settings.scale;
        this.scaleY = Interpolation.fade.apply(0.005F, 1.0F, this.duration * 2.0F) * Settings.scale;
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(Color.WHITE);
        this.card.render(sb);
        this.renderVfx(sb);
    }

    private void renderVfx(SpriteBatch sb)
    {
        sb.setColor(this.color);
        TextureAtlas.AtlasRegion img = ImageMaster.CARD_POWER_BG_SILHOUETTE;
        sb.draw(img, this.card.current_x + img.offsetX - (float) img.originalWidth / 2.0F, this.card.current_y + img.offsetY - (float) img.originalHeight / 2.0F, (float) img.originalWidth / 2.0F - img.offsetX, (float) img.originalHeight / 2.0F - img.offsetY, (float) img.packedWidth, (float) img.packedHeight, this.scale * MathUtils.random(0.95F, 1.05F), this.scaleY * MathUtils.random(0.95F, 1.05F), this.rotation);
        sb.setBlendFunction(770, 1);
        sb.setColor(this.rarityColor);
        img = ImageMaster.CARD_SUPER_SHADOW;
        sb.draw(img, this.card.current_x + img.offsetX - (float) img.originalWidth / 2.0F, this.card.current_y + img.offsetY - (float) img.originalHeight / 2.0F, (float) img.originalWidth / 2.0F - img.offsetX, (float) img.originalHeight / 2.0F - img.offsetY, (float) img.packedWidth, (float) img.packedHeight, this.scale * 0.75F * MathUtils.random(0.95F, 1.05F), this.scaleY * 0.75F * MathUtils.random(0.95F, 1.05F), this.rotation);
        sb.draw(img, this.card.current_x + img.offsetX - (float) img.originalWidth / 2.0F, this.card.current_y + img.offsetY - (float) img.originalHeight / 2.0F, (float) img.originalWidth / 2.0F - img.offsetX, (float) img.originalHeight / 2.0F - img.offsetY, (float) img.packedWidth, (float) img.packedHeight, this.scale * 0.5F * MathUtils.random(0.95F, 1.05F), this.scaleY * 0.5F * MathUtils.random(0.95F, 1.05F), this.rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }
}
