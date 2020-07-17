package KinomotoSakuraMod.Effects.Turn;

import KinomotoSakuraMod.Cards.KSMOD_AbstractMagicCard;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

public class KSMOD_TurningVFXEffect extends AbstractGameEffect
{
    ////////
    // Time Line:从上一个阶段到本阶段所需的时间
    ////////
    private static final float ENLARGE_INTERVAL = 0.25F;
    private static final float PARTICLE_GATHER_INTERVAL = 1.7F;
    private static final float SWITCH_INTERVAL = 2F;
    private static final float PARTICLE_DIFFUSION_INTERVAL = 0.5F;
    private static final float WAITING_INTERVAL = 0.5F;

    ////////
    // Duration:本阶段的持续时间
    ////////
    private static final float ENLARGE_DURATION = 0.25F;
    private static final float PARTICLE_GATHER_DURATION = 2F;
    private static final float SWITCH_DURATION = 2F;
    private static final float PARTICLE_DIFFUSION_DURATION = 3F;
    private static final float WAITING_DURATION = 1F;
    private static final float SHRINK_DURATION = 0.25F;

    private static final float SCALE = 1F;
    private static final float START_POSITION_X = Settings.WIDTH * 0.5F;
    private static final float START_POSITION_Y = Settings.HEIGHT * 0.6486F;
    private static final float TARGET_POSITION_X = Settings.WIDTH * 0.5F;
    private static final float TARGET_POSITION_Y = Settings.HEIGHT * 0.57F;
    private static final float START_SCALE = 0.666F;
    private static final float TARGET_SCALE = 1F;

    private KSMOD_AbstractMagicCard clowCard;
    private KSMOD_AbstractMagicCard sakuraCard;
    private Vector2 startPosition;
    private Vector2 targetPosition;

    private boolean[] doneArray = new boolean[]{true, true, true, true, true};

    public KSMOD_TurningVFXEffect(KSMOD_AbstractMagicCard clowCard, KSMOD_AbstractMagicCard sakuraCard)
    {
        this.startingDuration = GetTotalTime();
        this.duration = this.startingDuration;
        this.clowCard = (KSMOD_AbstractMagicCard) clowCard.makeCopy();
        this.clowCard.drawScale = SCALE;
        this.clowCard.SetTurningStatus(true);
        this.sakuraCard = sakuraCard;
        this.sakuraCard.drawScale = SCALE;
        this.sakuraCard.SetTurningStatus(true);
        this.startPosition = new Vector2(START_POSITION_X, START_POSITION_Y);
        this.targetPosition = new Vector2(TARGET_POSITION_X, TARGET_POSITION_Y);
        this.clowCard.current_x = this.sakuraCard.current_x = START_POSITION_X;
        this.clowCard.current_y = this.sakuraCard.current_y = START_POSITION_Y;
    }

    @Override
    public void update()
    {
        if (this.duration == this.startingDuration)
        {
            if (doneArray[0])
            {
                doneArray[0] = false;
                // 放大卡片
                AbstractDungeon.effectsQueue.add(new KSMOD_TurningScalingEffect(clowCard,
                        startPosition,
                        targetPosition,
                        START_SCALE,
                        TARGET_SCALE,
                        ENLARGE_DURATION));
            }
        }
        else if (this.duration < this.startingDuration - GetTimeLinePoint(1) && this.duration > this.startingDuration - GetTimeLinePoint(
                2))
        {
            if (doneArray[1])
            {
                doneArray[1] = false;
                // 播放音效
                CardCrawlGame.sound.playA("TURN_EFFECT", -0.6F);
                CardCrawlGame.sound.playA("BUFF_2", -0.6F);
                // 显示clow card
                AbstractDungeon.effectsQueue.add(new KSMOD_TurningShowCardEffect(clowCard,
                        targetPosition,
                        TARGET_SCALE,
                        PARTICLE_GATHER_DURATION));
                // 粒子聚集
                AbstractDungeon.effectsQueue.add(new KSMOD_TurningParticleGatherEffect(targetPosition,
                        PARTICLE_GATHER_DURATION));

            }
        }
        else if (this.duration < this.startingDuration - GetTimeLinePoint(2) && this.duration > this.startingDuration - GetTimeLinePoint(
                3))
        {
            if (doneArray[2])
            {
                doneArray[2] = false;
                // 播放转变音效
                CardCrawlGame.sound.play("MONSTER_GUARDIAN_DESTROY");
                // 显示sakura card
                AbstractDungeon.effectsQueue.add(new KSMOD_TurningShowCardEffect(sakuraCard,
                        targetPosition,
                        TARGET_SCALE,
                        SWITCH_DURATION + WAITING_DURATION));
                // 转变
                AbstractDungeon.effectsQueue.add(new KSMOD_TurningSwitchCardEffect(clowCard,
                        targetPosition,
                        SWITCH_DURATION));
                // 迷雾显示
                AbstractDungeon.effectsQueue.add(new KSMOD_TurningMistEffect(clowCard, SWITCH_DURATION));
                // 发光
                AbstractDungeon.effectsQueue.add(new KSMOD_TurningLuminEffect(clowCard, SWITCH_DURATION));
            }
        }
        else if (this.duration < this.startingDuration - GetTimeLinePoint(3) && this.duration > this.startingDuration - GetTimeLinePoint(
                4))
        {
            if (doneArray[3])
            {
                doneArray[3] = false;
                // 播放获得卡牌音效
                CardCrawlGame.sound.play("BUFF_" + MathUtils.random(1, 3));
                // 等待展示
                AbstractDungeon.effectsQueue.add(new KSMOD_TurningShowCardEffect(sakuraCard,
                        targetPosition,
                        TARGET_SCALE,
                        WAITING_DURATION));
                // 粒子扩散
                AbstractDungeon.effectsQueue.add(new KSMOD_TurningParticleDiffusionEffect(targetPosition,
                        PARTICLE_DIFFUSION_DURATION));
            }
        }
        else if (this.duration < this.startingDuration - GetTimeLinePoint(4) && this.duration > 0)
        {
            if (doneArray[4])
            {
                doneArray[4] = false;
                // 卡片缩小
                AbstractDungeon.effectsQueue.add(new KSMOD_TurningScalingEffect(sakuraCard,
                        targetPosition,
                        startPosition,
                        TARGET_SCALE,
                        START_SCALE,
                        SHRINK_DURATION));
            }
        }
        // 特效结束
        else if (this.duration <= 0)
        {
            sakuraCard.SetTurningStatus(false);
            // 加入卡组
            AbstractDungeon.player.masterDeck.addToBottom(sakuraCard);
            // 加入手牌
            AbstractDungeon.effectsQueue.add(new ShowCardAndAddToHandEffect(sakuraCard));
            this.isDone = true;
        }

        this.duration -= Gdx.graphics.getDeltaTime();
    }

    private float GetTimeLinePoint(int index)
    {
        float time = 0F;
        switch (index)
        {
            case 5:
                time += WAITING_INTERVAL;
            case 4:
                time += PARTICLE_DIFFUSION_INTERVAL;
            case 3:
                time += SWITCH_INTERVAL;
            case 2:
                time += PARTICLE_GATHER_INTERVAL;
            case 1:
                time += ENLARGE_INTERVAL;
        }
        return time;
    }

    public static float GetTotalTime()
    {
        return ENLARGE_INTERVAL + PARTICLE_GATHER_INTERVAL + SWITCH_INTERVAL + PARTICLE_DIFFUSION_INTERVAL + WAITING_INTERVAL;
    }

    @Override
    public void render(SpriteBatch spriteBatch)
    {
    }

    @Override
    public void dispose()
    {
    }
}
