package KinomotoSakuraMod.Cards.ClowCard;

import KinomotoSakuraMod.Actions.ApplyElementAction;
import KinomotoSakuraMod.Cards.AbstractMagicCard;
import KinomotoSakuraMod.Patches.CustomCardColor;
import KinomotoSakuraMod.Patches.CustomTag;
import KinomotoSakuraMod.Powers.DarkElementPower;
import KinomotoSakuraMod.Powers.FireyElementPower;
import KinomotoSakuraMod.Powers.LightElementPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ClowCardTheGlow extends AbstractMagicCard
{
    public static final String ID = "ClowCardTheGlow";
    private static final String NAME;
    private static final String DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION;
    private static final String IMAGE_PATH = "img/cards/clowcard/the_glow.png";
    private static final int COST = 0;
    private static final CardType CARD_TYPE = CardType.SKILL;
    private static final CardColor CARD_COLOR = CustomCardColor.CLOWCARD_COLOR;
    private static final CardRarity CARD_RARITY = CardRarity.UNCOMMON;
    private static final CardTarget CARD_TARGET = CardTarget.ALL_ENEMY;
    private static final int BASE_MAGIC_NUMBER = 3;
    private static final int LIGHT_NUMBER = 1;
    private static final int BASE_ACTIVE_NUMBER = 3;
    private static final int UPGRADED_ACTIVE_NUMBER = 2;
    private int activeNumber;

    static
    {
        CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }

    public ClowCardTheGlow()
    {
        super(ID, NAME, IMAGE_PATH, COST, DESCRIPTION, CARD_TYPE, CARD_COLOR, CARD_RARITY, CARD_TARGET, CustomTag.ELEMENT_CARD);
        this.setBaseMagicNumber(BASE_MAGIC_NUMBER);
        this.activeNumber = BASE_ACTIVE_NUMBER;
    }

    @Override
    public void upgrade()
    {
        if (!this.upgraded)
        {
            this.upgradeName();
            this.activeNumber = UPGRADED_ACTIVE_NUMBER;
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public AbstractMagicCard makeCopy()
    {
        return new ClowCardTheGlow();
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        for (AbstractMonster mon : AbstractDungeon.getMonsters().monsters)
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyElementAction(mon, player, new FireyElementPower(mon, this.correctMagicNumber()), this.correctMagicNumber(), true));
            if (this.upgraded)
            {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mon, player, new LightElementPower(mon, LIGHT_NUMBER), LIGHT_NUMBER, true));
            }

            if (mon.hasPower(DarkElementPower.POWER_ID))
            {
                int amount = mon.getPower(DarkElementPower.POWER_ID).amount;
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(mon, player, DarkElementPower.POWER_ID));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mon, player, new LightElementPower(mon, amount / this.activeNumber), this.activeNumber, true));
            }
        }
    }
}
