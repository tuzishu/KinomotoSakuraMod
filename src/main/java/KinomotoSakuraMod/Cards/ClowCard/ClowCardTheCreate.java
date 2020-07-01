package KinomotoSakuraMod.Cards.ClowCard;

import KinomotoSakuraMod.Cards.KSMOD_AbstractMagicCard;
import KinomotoSakuraMod.Cards.SakuraCard.SakuraCardTheCreate;
import KinomotoSakuraMod.Characters.KinomotoSakura;
import KinomotoSakuraMod.Patches.KSMOD_CustomCardColor;
import KinomotoSakuraMod.Patches.KSMOD_CustomTag;
import KinomotoSakuraMod.Powers.KSMOD_CreatePower;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class ClowCardTheCreate extends KSMOD_AbstractMagicCard
{
    public static final String ID = "ClowCardTheCreate";
    private static final String NAME;
    private static final String DESCRIPTION;
    private static final String IMAGE_PATH = "img/cards/clowcard/the_create.png";
    private static final int COST = 5;
    private static final CardType CARD_TYPE = CardType.POWER;
    private static final CardColor CARD_COLOR = KSMOD_CustomCardColor.CLOWCARD_COLOR;
    private static final CardRarity CARD_RARITY = CardRarity.RARE;
    private static final CardTarget CARD_TARGET = CardTarget.SELF;
    private static final float RARE_RELIC_RATE = 0.125F;
    private static final float UNCOMMON_RELIC_RATE = 0.375F;

    static
    {
        CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
    }

    public ClowCardTheCreate()
    {
        super(ID, NAME, IMAGE_PATH, COST, DESCRIPTION, CARD_TYPE, CARD_COLOR, CARD_RARITY, CARD_TARGET);
        this.tags.add(KSMOD_CustomTag.KSMOD_EARTHY_CARD);
        this.tags.add(KSMOD_CustomTag.KSMOD_WINDY_CARD);
        this.cardsToPreview = new SakuraCardTheCreate();
        this.timesUpgraded = 0;
    }

    public ClowCardTheCreate(int upgrades)
    {
        this();
        this.timesUpgraded = upgrades;
    }

    @Override
    public boolean canUpgrade()
    {
        return false;
    }

    @Override
    public void upgrade()
    {
        this.timesUpgraded += this.cost > 0 ? 1 : 0;
        this.upgradeBaseCost(COST - this.timesUpgraded);
    }

    public void receiveOnBattleStart(AbstractRoom room)
    {
        super.receiveOnBattleStart(room);
        if (this.cost > 0 && (AbstractDungeon.player.masterDeck.contains(this) || AbstractDungeon.player.drawPile.contains(this)))
        {
            this.upgrade();
        }
    }

    @Override
    public AbstractCard makeCopy()
    {
        return new ClowCardTheCreate(this.timesUpgraded);
    }

    @Override
    public void applyNormalEffect(AbstractPlayer player, AbstractMonster monster)
    {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new KSMOD_CreatePower(), 1));
        if (TryRemoveThisFromMasterDeck())
        {
            AbstractDungeon.getCurrRoom().addRelicToRewards(GetRandomTier());
        }
    }

    private AbstractRelic.RelicTier GetRandomTier()
    {
        float randNum = new Random().random(0F, 1F);
        if (randNum <= RARE_RELIC_RATE)
        {
            return AbstractRelic.RelicTier.RARE;
        }
        else if (randNum <= RARE_RELIC_RATE + UNCOMMON_RELIC_RATE)
        {
            return AbstractRelic.RelicTier.UNCOMMON;
        }
        else
        {
            return AbstractRelic.RelicTier.COMMON;
        }
    }

    private boolean TryRemoveThisFromMasterDeck()
    {
        for (AbstractCard card : AbstractDungeon.player.masterDeck.group)
        {
            if (card.uuid == this.uuid)
            {
                AbstractDungeon.player.masterDeck.removeCard(card);
                return true;
            }
        }
        AbstractDungeon.actionManager.addToBottom(new TalkAction(true, KinomotoSakura.GetMessage(2), 1.0F, 2.0F));
        return false;
    }
}
