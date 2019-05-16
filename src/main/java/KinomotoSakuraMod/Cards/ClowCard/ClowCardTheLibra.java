package KinomotoSakuraMod.Cards.ClowCard;

import KinomotoSakuraMod.Cards.AbstractMagicCard;
import KinomotoSakuraMod.Patches.CustomCardColor;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class ClowCardTheLibra extends AbstractMagicCard
{
    public static final String ID = "ClowCardTheLibra";
    private static final String NAME;
    private static final String DESCRIPTION;
    private static final String IMAGE_PATH = "img/cards/default_skill_card.png";
    private static final int COST = 0;
    private static final CardType CARD_TYPE = CardType.SKILL;
    private static final CardColor CARD_COLOR = CustomCardColor.CLOWCARD_COLOR;
    private static final CardRarity CARD_RARITY = CardRarity.COMMON;
    private static final CardTarget CARD_TARGET = CardTarget.SELF;
    private static final int BASE_BLOCK = 3;
    private static final int UPGRADE_BLOCK = 2;
    private static final int BASE_MAGIC_NUMBER = 1;
    private static final int UPGRADED_MAGIC_NUMBER = 1;

    static
    {
        CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
    }

    public ClowCardTheLibra()
    {
        super(ID, NAME, IMAGE_PATH, COST, DESCRIPTION, CARD_TYPE, CARD_COLOR, CARD_RARITY, CARD_TARGET);
        this.baseBlock = BASE_BLOCK;
        this.baseMagicNumber = BASE_MAGIC_NUMBER;
    }

    @Override
    public void upgrade()
    {
        if (!this.upgraded)
        {
            this.upgradeName();
            this.upgradeBlock(UPGRADE_BLOCK);
            this.upgradeMagicNumber(UPGRADED_MAGIC_NUMBER);
        }
    }

    @Override
    public AbstractMagicCard makeCopy()
    {
        return new ClowCardTheLibra();
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(player, player, this.correctBlock()));
        if (CheckBalance())
        {
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(this.correctMagicNumber()));
        }
    }

    private boolean CheckBalance()
    {
        int cardDiff = AbstractDungeon.player.drawPile.size() - AbstractDungeon.player.discardPile.size();
        return Math.abs(cardDiff) <= EnergyPanel.getCurrentEnergy();
    }
}
