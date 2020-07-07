package KinomotoSakuraMod.Cards.SakuraCard;

import KinomotoSakuraMod.Cards.ClowCard.ClowCardTheReturn;
import KinomotoSakuraMod.Cards.KSMOD_AbstractMagicCard;
import KinomotoSakuraMod.Patches.KSMOD_CustomCardColor;
import KinomotoSakuraMod.Patches.KSMOD_CustomTag;
import KinomotoSakuraMod.Relics.KSMOD_AbstractWand;
import KinomotoSakuraMod.Relics.KSMOD_SealedWand;
import KinomotoSakuraMod.Relics.KSMOD_StarWand;
import KinomotoSakuraMod.Relics.KSMOD_UltimateWand;
import KinomotoSakuraMod.Utility.KSMOD_ReflectTool;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class SakuraCardTheReturn extends KSMOD_AbstractMagicCard
{
    public static final String ID = "SakuraCardTheReturn";
    private static final String NAME;
    private static final String DESCRIPTION;
    private static final String IMAGE_PATH = "img/cards/sakuracard/the_return.png";
    private static final int COST = 0;
    private static final CardType CARD_TYPE = CardType.POWER;
    private static final CardColor CARD_COLOR = KSMOD_CustomCardColor.SAKURACARD_COLOR;
    private static final CardRarity CARD_RARITY = CardRarity.SPECIAL;
    private static final CardTarget CARD_TARGET = CardTarget.SELF;
    private static final float RECHARGE_RATE = 0.75F;

    static
    {
        CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
    }

    public SakuraCardTheReturn()
    {
        super(ID, NAME, IMAGE_PATH, COST, DESCRIPTION, CARD_TYPE, CARD_COLOR, CARD_RARITY, CARD_TARGET);
        this.tags.add(KSMOD_CustomTag.KSMOD_WATERY_CARD);
        this.retain = true;
        this.setBaseMagicNumber(0);
    }

    @Override
    public KSMOD_AbstractMagicCard makeCopy()
    {
        if (KSMOD_ReflectTool.IsReallyCopyingCard() && this.hasSameSakuraCard())
        {
            return getSameNameClowCard();
        }
        return new SakuraCardTheReturn();
    }

    public KSMOD_AbstractMagicCard getSameNameClowCard()
    {
        return new ClowCardTheReturn();
    }

    public void upgrade()
    {

    }

    @Override
    public void applyNormalEffect(AbstractPlayer player, AbstractMonster monster)
    {
        AbstractDungeon.actionManager.addToBottom(new HealAction(player, player, player.maxHealth - player.currentHealth));
        TryRemoveThisFromMasterDeck();
    }

    public void atTurnStart()
    {
        this.retain = true;
    }

    private void TryRemoveThisFromMasterDeck()
    {
        for (AbstractCard card : AbstractDungeon.player.masterDeck.group)
        {
            if (card.cardID == this.cardID)
            {
                for (AbstractRelic r : AbstractDungeon.player.relics)
                {
                    if (r instanceof KSMOD_AbstractWand)
                    {
                        KSMOD_AbstractWand wand = (KSMOD_AbstractWand)r;
                        r.setCounter(r.counter + (int)((wand.GetTriggerNumber() - wand.GetUpdateTriggerNumber()) * RECHARGE_RATE));
                        break;
                    }
                }
                AbstractDungeon.player.masterDeck.removeCard(card);
                break;
            }
        }
    }

    @Override
    public void applyPowers()
    {
        super.applyPowers();
        if (AbstractDungeon.player != null)
        {
            if (AbstractDungeon.player.hasRelic(KSMOD_SealedWand.RELIC_ID))
            {
                KSMOD_AbstractWand wand = (KSMOD_AbstractWand) AbstractDungeon.player.getRelic((KSMOD_SealedWand.RELIC_ID));
                this.baseMagicNumber = (int)((wand.GetTriggerNumber() - wand.GetUpdateTriggerNumber()) * RECHARGE_RATE);
            }
            else if (AbstractDungeon.player.hasRelic(KSMOD_StarWand.RELIC_ID))
            {
                KSMOD_AbstractWand wand = (KSMOD_AbstractWand) AbstractDungeon.player.getRelic((KSMOD_StarWand.RELIC_ID));
                this.baseMagicNumber = (int)((wand.GetTriggerNumber() - wand.GetUpdateTriggerNumber()) * RECHARGE_RATE);
            }
            else if (AbstractDungeon.player.hasRelic(KSMOD_UltimateWand.RELIC_ID))
            {
                KSMOD_AbstractWand wand = (KSMOD_AbstractWand) AbstractDungeon.player.getRelic((KSMOD_UltimateWand.RELIC_ID));
                this.baseMagicNumber = (int)((wand.GetTriggerNumber() - wand.GetUpdateTriggerNumber()) * RECHARGE_RATE);
            }
            if (this.baseBlock != 0)
            {
                this.rawDescription = DESCRIPTION;
                this.initializeDescription();
            }
        }
    }
}
