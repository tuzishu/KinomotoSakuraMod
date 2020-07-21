package KinomotoSakuraMod.Actions;

import KinomotoSakuraMod.Cards.KSMOD_AbstractMagicCard;
import KinomotoSakuraMod.Effects.Turn.KSMOD_TurningEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class KSMOD_TurnVFXAction extends AbstractGameAction
{
    private AbstractGameEffect effect;

    public KSMOD_TurnVFXAction(KSMOD_AbstractMagicCard clowCard, KSMOD_AbstractMagicCard sakuraCard)
    {
        effect = new KSMOD_TurningEffect(clowCard, sakuraCard);
        AbstractDungeon.effectsQueue.add(effect);
    }

    @Override
    public void update()
    {
        if (effect.isDone)
        {
            this.isDone = true;
        }
    }
}
