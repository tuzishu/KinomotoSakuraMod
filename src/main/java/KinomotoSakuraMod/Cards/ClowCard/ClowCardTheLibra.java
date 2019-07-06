package KinomotoSakuraMod.Cards.ClowCard;

import KinomotoSakuraMod.Cards.KSMOD_AbstractMagicCard;
import KinomotoSakuraMod.Patches.KSMOD_CustomCardColor;
import KinomotoSakuraMod.Patches.KSMOD_CustomTag;
import KinomotoSakuraMod.Relics.KSMOD_SealedBook;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class ClowCardTheLibra extends KSMOD_AbstractMagicCard
{
    public static final String ID = "ClowCardTheLibra";
    private static final String NAME;
    private static final String DESCRIPTION;
    private static final String[] EXTENDED_DESCRIPTION;
    private static final String IMAGE_PATH = "img/cards/clowcard/the_libra.png";
    private static final int COST = 0;
    private static final CardType CARD_TYPE = CardType.SKILL;
    private static final CardColor CARD_COLOR = KSMOD_CustomCardColor.CLOWCARD_COLOR;
    private static final CardRarity CARD_RARITY = CardRarity.COMMON;
    private static final CardTarget CARD_TARGET = CardTarget.NONE;
    private static final int BASE_BLOCK = 4;
    private static final int UPGRADE_BLOCK = 3;

    static
    {
        CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    }

    public ClowCardTheLibra()
    {
        super(ID, NAME, IMAGE_PATH, COST, DESCRIPTION, CARD_TYPE, CARD_COLOR, CARD_RARITY, CARD_TARGET, true);
        this.tags.add(KSMOD_CustomTag.KSMOD_EARTHY_CARD);
        this.baseBlock = BASE_BLOCK;
    }

    @Override
    public void upgrade()
    {
        if (!this.upgraded)
        {
            this.upgradeName();
            this.upgradeBlock(UPGRADE_BLOCK);
        }
    }

    @Override
    public KSMOD_AbstractMagicCard makeCopy()
    {
        return new ClowCardTheLibra();
    }

    @Override
    public void applyNormalEffect(AbstractPlayer player, AbstractMonster monster)
    {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(player, player, this.block));
        if (CheckBalance())
        {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(player, player, this.block));
        }
    }

    @Override
    public void applyExtraEffect(AbstractPlayer player, AbstractMonster monster)
    {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(player, player, this.block));
        if (CheckBalance())
        {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(player, player, this.block));
        }
        AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(KSMOD_SealedBook.BASE_BLOCK));
    }

    @Override
    public String getExtraDescription()
    {
        return this.rawDescription + EXTENDED_DESCRIPTION[0] + KSMOD_SealedBook.BASE_BLOCK + EXTENDED_DESCRIPTION[1];
    }

    private boolean CheckBalance()
    {
        int cardDiff = AbstractDungeon.player.drawPile.size() - AbstractDungeon.player.discardPile.size();
        return Math.abs(cardDiff) <= AbstractDungeon.player.energy.energy;
    }
}
