package KinomotoSakuraMod.Cards.ClowCard;

import KinomotoSakuraMod.Cards.AbstractMagicCard;
import KinomotoSakuraMod.Patches.CustomCardColor;
import KinomotoSakuraMod.Powers.CreatePower;
import KinomotoSakuraMod.Utility.ModUtility;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ClowCardTheCreate extends AbstractMagicCard
{

    public static final String ID = "ClowCardTheCreate";
    private static final String NAME;
    private static final String DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION;
    private static final String IMAGE_PATH = "img/cards/default_power_card.png";
    private static final int COST = 4;
    private static final CardType CARD_TYPE = CardType.POWER;
    private static final CardColor CARD_COLOR = CustomCardColor.CLOWCARD_COLOR;
    private static final CardRarity CARD_RARITY = CardRarity.RARE;
    private static final CardTarget CARD_TARGET = CardTarget.SELF;

    static
    {
        CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }

    public ClowCardTheCreate()
    {
        super(ID, NAME, IMAGE_PATH, COST, DESCRIPTION, CARD_TYPE, CARD_COLOR, CARD_RARITY, CARD_TARGET);
    }

    @Override
    public void upgrade()
    {
        if (!this.upgraded)
        {
            upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.retain = true;
        }
    }

    @Override
    public AbstractMagicCard makeCopy()
    {
        return new ClowCardTheCreate();
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new CreatePower(), 1));
        int size = AbstractDungeon.player.masterDeck.group.size();
        for (int i = 0; i < size; i++)
        {
            AbstractCard card = AbstractDungeon.player.masterDeck.group.get(i);
            ModUtility.Logger.info("masterDeck: "+card.name);
            if (card == this)
            {
                AbstractDungeon.player.masterDeck.removeCard(this);
            }
        }
    }
}
