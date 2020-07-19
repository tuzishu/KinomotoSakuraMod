package KinomotoSakuraMod.Cards.ClowCard;

import KinomotoSakuraMod.Cards.KSMOD_AbstractMagicCard;
import KinomotoSakuraMod.Cards.SakuraCard.SakuraCardTheShield;
import KinomotoSakuraMod.Characters.KinomotoSakura;
import KinomotoSakuraMod.Patches.KSMOD_CustomCardColor;
import KinomotoSakuraMod.Patches.KSMOD_CustomTag;
import KinomotoSakuraMod.Relics.KSMOD_SealedBook;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MetallicizePower;

public class ClowCardTheShield extends KSMOD_AbstractMagicCard
{
    public static final String ID = "ClowCardTheShield";
    private static final String NAME;
    private static final String DESCRIPTION;
    private static final String[] EXTENDED_DESCRIPTION;
    private static final String IMAGE_PATH = "img/cards/clowcard/the_shield.png";
    private static final int COST = 1;
    private static final CardType CARD_TYPE = CardType.SKILL;
    private static final CardColor CARD_COLOR = KSMOD_CustomCardColor.CLOWCARD_COLOR;
    private static final CardRarity CARD_RARITY = CardRarity.BASIC;
    private static final CardTarget CARD_TARGET = CardTarget.SELF;
    private static final int BASE_BLOCK = 5;
    private static final int UPGRADE_BLOCK = 3;

    static
    {
        CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    }

    public ClowCardTheShield()
    {
        super(ID, NAME, IMAGE_PATH, COST, DESCRIPTION, CARD_TYPE, CARD_COLOR, CARD_RARITY, CARD_TARGET, true);
        this.tags.add(KSMOD_CustomTag.KSMOD_FIREY_CARD);
        this.tags.add(CardTags.STARTER_DEFEND);
        this.cardsToPreview = new SakuraCardTheShield();
        this.baseBlock = BASE_BLOCK;
    }

    @Override
    public void upgrade()
    {
        if (!this.upgraded)
        {
            upgradeName();
            this.baseBlock = MathUtils.floor((BASE_BLOCK + UPGRADE_BLOCK) * GetCorrection());
            this.upgradedBlock = true;
        }
    }

    @Override
    public KSMOD_AbstractMagicCard makeCopy()
    {
        return new ClowCardTheShield();
    }

    @Override
    public void applyNormalEffect(AbstractPlayer player, AbstractMonster monster)
    {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(player, player, this.block));
    }

    @Override
    public void applyExtraEffect(AbstractPlayer player, AbstractMonster monster)
    {
        applyNormalEffect(player, monster);
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player,
                player,
                new MetallicizePower(player, KSMOD_SealedBook.METALLICIZE_NUMBER),
                KSMOD_SealedBook.METALLICIZE_NUMBER));
    }

    @Override
    public String getExtraDescription()
    {
        return this.rawDescription + EXTENDED_DESCRIPTION[0] + KSMOD_SealedBook.METALLICIZE_NUMBER + EXTENDED_DESCRIPTION[1];
    }

    public void onRemoveFromMasterDeck()
    {
        SetBlock(GetCorrection());
    }

    private float GetCorrection()
    {
        int amount = 0;
        if (AbstractDungeon.player instanceof KinomotoSakura)
        {
            for (AbstractCard c : AbstractDungeon.player.masterDeck.group)
            {
                if (c.cardID.equals(ID))
                {
                    amount += 1;
                }
            }
            amount = MathUtils.clamp(amount, 0, 4);
        }
        else
        {
            amount = 4;
        }
        switch (amount)
        {
            case 0:
            case 1:
                return 8F / 5F;
            case 2:
                return 7F / 5F;
            case 3:
                return 6F / 5F;
            default:
                return 5F / 5F;
        }
    }

    private void SetBlock(float correction)
    {
        SetGroup(AbstractDungeon.player.masterDeck, correction);
        SetGroup(AbstractDungeon.player.hand, correction);
        SetGroup(AbstractDungeon.player.drawPile, correction);
        SetGroup(AbstractDungeon.player.discardPile, correction);
        SetGroup(AbstractDungeon.player.exhaustPile, correction);
    }

    private void SetGroup(CardGroup group, float correction)
    {
        for (AbstractCard c : group.group)
        {
            if (c.cardID.equals(ID))
            {
                c.baseBlock = MathUtils.floor((c.upgraded ? BASE_BLOCK + UPGRADE_BLOCK : BASE_BLOCK) * correction);
            }
        }
    }
}
