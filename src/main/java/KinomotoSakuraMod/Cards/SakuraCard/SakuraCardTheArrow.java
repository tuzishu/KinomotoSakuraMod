package KinomotoSakuraMod.Cards.SakuraCard;

import KinomotoSakuraMod.Actions.KSMOD_ArrowAttackAction;
import KinomotoSakuraMod.Cards.ClowCard.ClowCardTheArrow;
import KinomotoSakuraMod.Cards.KSMOD_AbstractMagicCard;
import KinomotoSakuraMod.Patches.KSMOD_CustomCardColor;
import KinomotoSakuraMod.Patches.KSMOD_CustomTag;
import KinomotoSakuraMod.Utility.KSMOD_Utility;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SakuraCardTheArrow extends KSMOD_AbstractMagicCard
{
    public static final String ID = "SakuraCardTheArrow";
    private static final String NAME;
    private static final String DESCRIPTION;
    private static final String IMAGE_PATH = "img/cards/sakuracard/the_arrow.png";
    private static final int COST = 1;
    private static final CardType CARD_TYPE = CardType.ATTACK;
    private static final CardColor CARD_COLOR = KSMOD_CustomCardColor.SAKURACARD_COLOR;
    private static final CardRarity CARD_RARITY = CardRarity.SPECIAL;
    private static final CardTarget CARD_TARGET = CardTarget.NONE;
    private static final int BASE_DAMAGE = 7;

    static
    {
        CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
    }

    public SakuraCardTheArrow()
    {
        super(ID, NAME, IMAGE_PATH, COST, DESCRIPTION, CARD_TYPE, CARD_COLOR, CARD_RARITY, CARD_TARGET);
        this.tags.add(KSMOD_CustomTag.KSMOD_FIREY_CARD);
        this.baseDamage = BASE_DAMAGE;
    }

    @Override
    public void upgrade()
    {

    }

    @Override
    public KSMOD_AbstractMagicCard makeCopy()
    {
        if (KSMOD_Utility.IsReallyCopyingCard() && this.hasSameSakuraCard())
        {
            return getSameNameClowCard();
        }
        return new SakuraCardTheArrow();
    }

    public KSMOD_AbstractMagicCard getSameNameClowCard()
    {
        return new ClowCardTheArrow();
    }

    @Override
    public void applyNormalEffect(AbstractPlayer player, AbstractMonster monster)
    {
        int count = player.drawPile.size();
        if (!player.drawPile.isEmpty())
        {
            AbstractDungeon.actionManager.addToBottom(new DiscardSpecificCardAction(player.drawPile.getTopCard()));
        }
        while (!player.drawPile.isEmpty())
        {
            AbstractCard card = player.drawPile.getTopCard();
            player.drawPile.moveToDiscardPile(card);
            GameActionManager.incrementDiscard(false);
            card.triggerOnManualDiscard();
            player.drawPile.removeCard(card);
        }
        AbstractDungeon.actionManager.addToBottom(new KSMOD_ArrowAttackAction(null, this.damage, count));
    }
}
