package KinomotoSakuraMod.Cards.SpellCard;

import KinomotoSakuraMod.Actions.KSMOD_TurnAction;
import KinomotoSakuraMod.Cards.KSMOD_AbstractSpellCard;
import KinomotoSakuraMod.Patches.KSMOD_CustomCardColor;
import KinomotoSakuraMod.Relics.KSMOD_SealedWand;
import KinomotoSakuraMod.Utility.KSMOD_Utility;
import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SpellCardTurn extends KSMOD_AbstractSpellCard
{
    public static final String ID = "SpellCardTurn";
    private static final String NAME;
    private static final String DESCRIPTION;
    private static final String IMAGE_PATH = "img/cards/default_card.png";
    private static final int COST = -2;
    private static final CardType CARD_TYPE = CardType.POWER;
    private static final CardColor CARD_COLOR = KSMOD_CustomCardColor.SPELL_COLOR;
    private static final CardRarity CARD_RARITY = CardRarity.SPECIAL;
    private static final CardTarget CARD_TARGET = CardTarget.NONE;

    static
    {
        CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
    }

    public SpellCardTurn()
    {
        super(ID, NAME, IMAGE_PATH, COST, DESCRIPTION, CARD_TYPE, CARD_COLOR, CARD_RARITY, CARD_TARGET);
        this.retain = true;
    }

    public boolean canUpgrade()
    {
        return false;
    }

    @Override
    public void upgrade()
    {

    }

    @Override
    public AbstractCard makeCopy()
    {
        if (!IsFromWand())
        {
            return new VoidCard();
        }
        return new SpellCardTurn();
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        AbstractDungeon.actionManager.addToBottom(new KSMOD_TurnAction());
        TryRemoveThisFromMasterDeck();
    }

    private boolean IsFromWand()
    {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        for (int i = 0; i < elements.length; i++)
        {
            if (elements[i].getClassName().contains(KSMOD_SealedWand.class.getName()))
            {
                return true;
            }
        }
        return false;
    }

    private void TryRemoveThisFromMasterDeck()
    {
        for (AbstractCard card : AbstractDungeon.player.masterDeck.group)
        {
            if (card.uuid == this.uuid)
            {
                AbstractDungeon.player.masterDeck.removeCard(card);
                break;
            }
        }
    }
}
