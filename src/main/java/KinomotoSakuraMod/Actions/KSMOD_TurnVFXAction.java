package KinomotoSakuraMod.Actions;

import KinomotoSakuraMod.Cards.KSMOD_AbstractMagicCard;
import KinomotoSakuraMod.Effects.Turn.KSMOD_TurningEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class KSMOD_TurnVFXAction extends AbstractGameAction
{
    private KSMOD_AbstractMagicCard clowCard;
    private KSMOD_AbstractMagicCard sakuraCard;

    public KSMOD_TurnVFXAction(KSMOD_AbstractMagicCard clowCard, KSMOD_AbstractMagicCard sakuraCard)
    {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.startDuration = KSMOD_TurningEffect.GetTotalTime();
        this.duration = this.startDuration;
        this.clowCard = clowCard;
        this.sakuraCard = sakuraCard;
    }

    @Override
    public void update()
    {
        if (this.duration == this.startDuration)
        {
            AbstractDungeon.effectsQueue.add(new KSMOD_TurningEffect(clowCard, sakuraCard));
        }

        if (this.duration < 0)
        {
            this.isDone = true;
        }
        this.tickDuration();
    }
}
