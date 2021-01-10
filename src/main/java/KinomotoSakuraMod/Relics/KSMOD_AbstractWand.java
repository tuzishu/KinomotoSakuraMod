package KinomotoSakuraMod.Relics;

import KinomotoSakuraMod.Cards.KSMOD_AbstractMagicCard;
import KinomotoSakuraMod.Cards.SakuraCard.SakuraCardTheHope;
import KinomotoSakuraMod.Cards.SakuraCard.SakuraCardTheLove;
import KinomotoSakuraMod.Cards.SpellCard.SpellCardTurn;
import KinomotoSakuraMod.Characters.KinomotoSakura;
import KinomotoSakuraMod.Effects.KSMOD_SealOrbEffect;
import KinomotoSakuraMod.Patches.KSMOD_CustomCardColor;
import KinomotoSakuraMod.Utility.KSMOD_DataTool;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MinionPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class KSMOD_AbstractWand extends CustomRelic
{
    private static final int START_COUNT = 0;
    private int baseTriggerNumber;
    private int updateTriggerNumber;
    private int gainNumber;
    private int extraGainNumber;
    private ArrayList<AbstractMonster> sealedMonsters = new ArrayList<>();
    private static ArrayList<String> sakuraCardBlackList = new ArrayList<>();
    private static HashMap<String, Integer> extraRechargeRelics = new HashMap<>();
    private static HashMap<String, Integer> extraRechargeCards = new HashMap<>();

    static
    {
        sakuraCardBlackList.add(SakuraCardTheLove.ID);
        sakuraCardBlackList.add(SakuraCardTheHope.ID);
    }

    public KSMOD_AbstractWand(String id, Texture texture, Texture outline, RelicTier tier, LandingSound sfx, int baseTriggerNumber, int updateTriggerNumber, int gainNumber, int extraGainNumber)
    {
        super(id, texture, outline, tier, sfx);
        this.counter = START_COUNT;
        this.baseTriggerNumber = baseTriggerNumber;
        this.updateTriggerNumber = updateTriggerNumber;
        this.gainNumber = gainNumber;
        this.extraGainNumber = extraGainNumber;
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
            ShowOrbEffect();
            ActiveRelic();
        }
    }

    public void onMonsterDeath(AbstractMonster monster)
    {
        if (!monster.hasPower(MinionPower.POWER_ID) && !HasMonsterSealed(monster))
        {
            int chargeNumber = gainNumber;
            if (monster.type == AbstractMonster.EnemyType.ELITE || monster.type == AbstractMonster.EnemyType.BOSS)
            {
                chargeNumber += extraGainNumber;
            }
            for (HashMap.Entry<String, Integer> entry : extraRechargeRelics.entrySet())
            {
                if (AbstractDungeon.player.hasRelic(entry.getKey()))
                {
                    chargeNumber += entry.getValue();
                    AbstractRelic relic = AbstractDungeon.player.getRelic(entry.getKey());
                    relic.flash();
                }
            }
            if (extraRechargeCards.containsKey(AbstractDungeon.player.cardInUse.cardID))
            {
                chargeNumber += extraRechargeCards.get(AbstractDungeon.player.cardInUse.cardID);
            }
            GainCharge(chargeNumber, monster);
            sealedMonsters.add(monster);
        }
    }

    public boolean HasMonsterSealed(AbstractMonster monster)
    {
        return sealedMonsters.contains(monster);
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

    public int GetExtraGainNumber()
    {
        return extraGainNumber;
    }

    public int GetUpdateTriggerNumber()
    {
        return updateTriggerNumber;
    }

    public int GetRestCounterNumber()
    {
        return this.GetTriggerNumber() > this.counter ? this.GetTriggerNumber() - this.counter : 0;
    }

    public void GainCharge(int chargeNumber, AbstractMonster monster)
    {
        for (int i = 0; i < chargeNumber; i++)
        {
            AbstractDungeon.effectList.add(new KSMOD_SealOrbEffect(monster.hb.cX, monster.hb.cY, this.hb.cX, this.hb.cY));
        }
        if (chargeNumber > 0)
        {
            this.flash();
            this.setCounter(this.counter + chargeNumber);
        }
    }

    public void setCounter(int counter)
    {
        super.setCounter(counter);
        this.updateTips();
    }

    public void ActiveRelic()
    {
        this.setCounter(this.counter -= GetTriggerNumber());
        if (this.counter < 0)
        {
            this.setCounter(0);
        }
        AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new SpellCardTurn()));
        this.flash();
    }

    public void ShowOrbEffect()
    {
        for (int i = 0; i < GetTriggerNumber(); i++)
        {
            if (AbstractDungeon.player.hand.size() == 0)
            {
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new KSMOD_SealOrbEffect(this.hb.cX, this.hb.cY, Settings.WIDTH * 0.5F, Settings.HEIGHT * 0.13F)));
            }
            else
            {
                AbstractCard c = AbstractDungeon.player.hand.getTopCard();
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new KSMOD_SealOrbEffect(this.hb.cX, this.hb.cY, c.hb.cX, c.hb.cY)));
            }
        }
    }

    public int GetTriggerNumber()
    {
        int sakuraCardAmount = 0;
        if (AbstractDungeon.player instanceof KinomotoSakura)
        {
            for (AbstractCard card : AbstractDungeon.player.masterDeck.group)
            {
                if (KSMOD_DataTool.IsStringListContains(sakuraCardBlackList, card.cardID))
                {
                    break;
                }
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
                if (KSMOD_DataTool.IsStringListContains(sakuraCardList, cardClassName))
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

    public static void RegisterExtraRechargeRelics(String relicId, int rechargeNumber)
    {
        extraRechargeRelics.put(relicId, rechargeNumber);
    }

    public static void RegisterExtraRechargeCards(String cardId, int rechargeNumber)
    {
        extraRechargeCards.put(cardId, rechargeNumber);
    }
}
