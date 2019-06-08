package KinomotoSakuraMod.Powers;

import KinomotoSakuraMod.Cards.KSMOD_AbstractMagicCard;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class WavePower extends KSMOD_CustomPower
{
    public static final String POWER_ID = "WavePower";
    private static final String POWER_NAME;
    private static final String[] POWER_DESCRIPTIONS;
    private static final String POWER_IMG_PATH = "img/powers/default_power.png";
    private static final PowerType POWER_TYPE = PowerType.BUFF;
    private static final float MAX_RATE = 0.75F;
    private AbstractCard currentCard;

    static
    {
        PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        POWER_NAME = powerStrings.NAME;
        POWER_DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }

    public WavePower(int amount)
    {
        this(AbstractDungeon.player, amount);
    }

    public WavePower(AbstractCreature target, int amount)
    {
        super(POWER_ID, POWER_NAME, POWER_IMG_PATH, POWER_TYPE, target, amount);
        this.updateDescription();
    }

    public void updateDescription()
    {
        this.description = POWER_DESCRIPTIONS[0] + this.amount + POWER_DESCRIPTIONS[1];
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action)
    {
        if (card instanceof KSMOD_AbstractMagicCard && card.type == AbstractCard.CardType.ATTACK && card.target == AbstractCard.CardTarget.ENEMY)
        {
            currentCard = card;
        }
    }

    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target)
    {
        if (this.currentCard == null || info.type != DamageInfo.DamageType.NORMAL || damageAmount <= 0)
        {
            return;
        }
        ArrayList<AbstractMonster> monsters = AbstractDungeon.getMonsters().monsters;
        float rate = this.amount / 100F > MAX_RATE ? MAX_RATE : this.amount / 100F;
        int damage = MathUtils.ceil(damageAmount * rate);
        for (int i = 0; i < monsters.size(); i++)
        {
            if (monsters.get(i) != target)
            {
                AbstractDungeon.actionManager.addToBottom(new DamageAction(monsters.get(i), new DamageInfo(AbstractDungeon.player, damage, DamageInfo.DamageType.HP_LOSS), AbstractGameAction.AttackEffect.BLUNT_LIGHT, true));
            }
        }
        this.currentCard = null;
        this.flash();
    }
}
