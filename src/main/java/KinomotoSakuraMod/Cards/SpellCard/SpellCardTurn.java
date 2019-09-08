package KinomotoSakuraMod.Cards.SpellCard;

import KinomotoSakuraMod.Actions.KSMOD_TurnAction;
import KinomotoSakuraMod.Cards.KSMOD_AbstractSpellCard;
import KinomotoSakuraMod.Characters.KinomotoSakura;
import KinomotoSakuraMod.Patches.KSMOD_CustomCardColor;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

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
        return new SpellCardTurn();
    }

    @Override
    public boolean canUse(AbstractPlayer player, AbstractMonster monster)
    {
        ArrayList<AbstractCard> cardList = new ArrayList<>();
        for (AbstractCard card : AbstractDungeon.player.hand.group)
        {
            if (card.color == KSMOD_CustomCardColor.CLOWCARD_COLOR && !hasTargetSakuraCardInMasterDeck(card.cardID))
            {
                cardList.add(card);
            }
        }
        return cardList.size() > 0;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        AbstractDungeon.actionManager.addToBottom(new KSMOD_TurnAction());
        TryRemoveThisFromMasterDeck();
    }

    private void TryRemoveThisFromMasterDeck()
    {
        for (AbstractCard card : AbstractDungeon.player.masterDeck.group)
        {
            if (card.cardID == this.cardID)
            {
                AbstractDungeon.player.masterDeck.removeCard(card);
                break;
            }
        }
    }

    private boolean hasTargetSakuraCardInMasterDeck(String cardID)
    {
        if (AbstractDungeon.player instanceof KinomotoSakura)
        {
            for (AbstractCard card : AbstractDungeon.player.masterDeck.group)
            {
                cardID = cardID.replaceAll("ClowCardThe", "SakuraCardThe");
                if (cardID.equals(card.cardID))
                {
                    return true;
                }
            }
        }
        return false;
    }
}
