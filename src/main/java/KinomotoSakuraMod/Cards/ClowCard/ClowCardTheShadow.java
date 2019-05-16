package KinomotoSakuraMod.Cards.ClowCard;

import KinomotoSakuraMod.Cards.AbstractMagicCard;
import KinomotoSakuraMod.Patches.CustomCardColor;
import KinomotoSakuraMod.Patches.CustomTag;
import KinomotoSakuraMod.Powers.DarkElementPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;

public class ClowCardTheShadow extends AbstractMagicCard
{
    public static final String ID = "ClowCardTheShadow";
    private static final String NAME;
    private static final String DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION;
    private static final String IMAGE_PATH = "img/cards/default_skill_card.png";
    private static final int COST = 1;
    private static final CardType CARD_TYPE = CardType.SKILL;
    private static final CardColor CARD_COLOR = CustomCardColor.CLOWCARD_COLOR;
    private static final CardRarity CARD_RARITY = CardRarity.COMMON;
    private static final CardTarget CARD_TARGET = CardTarget.SELF;
    private static final int BASE_BLOCK = 5;
    private static final int UPGRADE_BLOCK = 3;
    private static final float BASE_ACTIVE_RATE = 0.5F;
    private static final float UPGRADED_ACTIVE_RATE = 1F;
    private float activeRate;

    static
    {
        CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }

    public ClowCardTheShadow()
    {
        super(ID, NAME, IMAGE_PATH, COST, DESCRIPTION, CARD_TYPE, CARD_COLOR, CARD_RARITY, CARD_TARGET, CustomTag.ELEMENT_CARD);
        this.baseBlock = BASE_BLOCK;
        this.activeRate = BASE_ACTIVE_RATE;
    }

    @Override
    public void upgrade()
    {
        if (!this.upgraded)
        {
            this.upgradeName();
            this.upgradeBlock(UPGRADE_BLOCK);
            this.activeRate = UPGRADED_ACTIVE_RATE;
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public AbstractMagicCard makeCopy()
    {
        return new ClowCardTheShadow();
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        int amount = 0;
        for (AbstractMonster mon : AbstractDungeon.getMonsters().monsters)
        {
            if (mon.hasPower(DarkElementPower.POWER_ID))
            {
                int powerCount = mon.getPower(DarkElementPower.POWER_ID).amount;
                amount += powerCount;
                DarkElementPower.TryActiveDarkElement(mon, powerCount, true);
            }
        }
        amount *= this.activeRate;
        if (amount > 0)
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new PlatedArmorPower(player, amount), amount));
        }
    }
}
