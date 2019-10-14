package KinomotoSakuraMod.Relics;

import KinomotoSakuraMod.Cards.KSMOD_AbstractMagicCard;
import KinomotoSakuraMod.Cards.SpellCard.SpellCardTurn;
import KinomotoSakuraMod.Characters.KinomotoSakura;
import KinomotoSakuraMod.Patches.KSMOD_CustomCardColor;
import KinomotoSakuraMod.Utility.KSMOD_Utility;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.beyond.Darkling;
import com.megacrit.cardcrawl.powers.MinionPower;

import java.util.ArrayList;

public abstract class KSMOD_AbstractWand extends CustomRelic
{
    private static final int START_COUNT = 0;
    private int baseTriggerNumber;
    private int updateTriggerNumber;
    private int gainNumber;
    public ArrayList<AbstractMonster> sealedMonsters = new ArrayList<>();

    public KSMOD_AbstractWand(String id, Texture texture, Texture outline, RelicTier tier, LandingSound sfx, int baseTriggerNumber, int updateTriggerNumber, int gainNumber)
    {
        super(id, texture, outline, tier, sfx);
        this.counter = START_COUNT;
        this.baseTriggerNumber = baseTriggerNumber;
        this.updateTriggerNumber = updateTriggerNumber;
        this.gainNumber = gainNumber;
        updateTips();
    }

    public void atPreBattle()
    {
        if (AbstractDungeon.player instanceof KinomotoSakura)
        {
            CheckSakuraCardRepeat(AbstractDungeon.player.masterDeck.group);
            CheckSakuraCardRepeat(AbstractDungeon.player.drawPile.group);
        }
    }

    public void atBattleStart()
    {
        sealedMonsters.clear();
    }

    public void atTurnStart()
    {
        if (this.counter >= GetTriggerNumber() && !AbstractDungeon.getCurrRoom().isBattleEnding() && !AbstractDungeon.getMonsters().areMonstersBasicallyDead())
        {
            this.flash();
            ActiveRelic();
        }
    }

    public void onMonsterDeath(AbstractMonster monster)
    {
        if (!monster.hasPower(MinionPower.POWER_ID) && !sealedMonsters.contains(monster))
        {
            GainCharge(gainNumber);
        }
        if (monster.id == Darkling.ID)
        {
            sealedMonsters.add(monster);
        }
    }

    public void updateTips()
    {
        this.description = this.getUpdatedDescription();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }

    public int GetGainNumber()
    {
        return gainNumber;
    }

    public int GetUpdateTriggerNumber()
    {
        return updateTriggerNumber;
    }

    public int GetRestCounterNumber()
    {
        return this.GetTriggerNumber() > this.counter ? this.GetTriggerNumber() - this.counter : 0;
    }

    public void GainCharge(int chargeNumber)
    {
        this.setCounter(this.counter + chargeNumber);
    }

    public void setCounter(int counter)
    {
        super.setCounter(counter);
        this.updateTips();
    }

    private void ActiveRelic()
    {
        this.setCounter(this.counter -= GetTriggerNumber());
        if (this.counter < 0)
        {
            this.setCounter(0);
        }
        AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new SpellCardTurn()));
    }

    public int GetTriggerNumber()
    {
        int sakuraCardAmount = 0;
        if (AbstractDungeon.player instanceof KinomotoSakura)
        {
            for (AbstractCard card : AbstractDungeon.player.masterDeck.group)
            {
                if (card.cardID.contains("SakuraCardThe") || card.cardID.contains("SpellCardTurn"))
                {
                    sakuraCardAmount += 1;
                }
            }
        }
        return baseTriggerNumber + updateTriggerNumber * sakuraCardAmount;
    }

    private void CheckSakuraCardRepeat(ArrayList<AbstractCard> arrayList)
    {
        ArrayList<String> sakuraCardList = new ArrayList<>();
        ArrayList<Integer> cardsIndexList = new ArrayList<>();
        ArrayList<AbstractCard> cardsToRemove = new ArrayList<>();
        ArrayList<AbstractCard> cardsToAdd = new ArrayList<>();
        for (AbstractCard card : arrayList)
        {
            if (card.color == KSMOD_CustomCardColor.SAKURACARD_COLOR)
            {
                String cardClassName = card.getClass().getName();
                if (KSMOD_Utility.IsStringListContains(sakuraCardList, cardClassName))
                {
                    cardsToRemove.add(card);
                    cardsToAdd.add(((KSMOD_AbstractMagicCard) card).getSameNameClowCard());
                    cardsIndexList.add(arrayList.indexOf(card));
                }
                else
                {
                    sakuraCardList.add(cardClassName);
                }
            }
        }
        arrayList.removeAll(cardsToRemove);
        for (int i = 0; i < cardsToAdd.size(); i++)
        {
            arrayList.add(i, cardsToAdd.get(i));
        }
    }
}
