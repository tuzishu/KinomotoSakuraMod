package KinomotoSakuraMod.Cards.ClowCard;

import KinomotoSakuraMod.Actions.ApplyElementAction;
import KinomotoSakuraMod.Cards.AbstractMagicCard;
import KinomotoSakuraMod.Patches.CustomCardColor;
import KinomotoSakuraMod.Patches.CustomTag;
import KinomotoSakuraMod.Powers.EarthyElementPower;
import KinomotoSakuraMod.Powers.FireyElementPower;
import KinomotoSakuraMod.Powers.WateryElementPower;
import KinomotoSakuraMod.Powers.WindyElementPower;
import KinomotoSakuraMod.Utility.ModUtility;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ClowCardTheStorm extends AbstractMagicCard
{
    public static final String ID = "ClowCardTheStorm";
    private static final String NAME;
    private static final String DESCRIPTION;
    private static final String IMAGE_PATH = "img/cards/default_attack_card.png";
    private static final int COST = 2;
    private static final int UPGRADED_COST = 1;
    private static final AbstractCard.CardType CARD_TYPE = AbstractCard.CardType.ATTACK;
    private static final AbstractCard.CardColor CARD_COLOR = CustomCardColor.CLOWCARD_COLOR;
    private static final CardRarity CARD_RARITY = CardRarity.UNCOMMON;
    private static final CardTarget CARD_TARGET = CardTarget.ENEMY;
    private static final int BASE_MAGIC_NUMBER = 3;

    static
    {
        CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
    }

    public ClowCardTheStorm()
    {
        super(ID, NAME, IMAGE_PATH, COST, DESCRIPTION, CARD_TYPE, CARD_COLOR, CARD_RARITY, CARD_TARGET, CustomTag.ELEMENT_CARD);
        this.setBaseMagicNumber(BASE_MAGIC_NUMBER);
    }

    @Override
    public void upgrade()
    {
        if (!this.upgraded)
        {
            this.upgradeName();
            this.upgradeBaseCost(UPGRADED_COST);
        }
    }

    @Override
    public AbstractMagicCard makeCopy()
    {
        return new ClowCardTheStorm();
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        int count = 0;
        if (monster.hasPower(EarthyElementPower.POWER_ID))
        {
            int amount = monster.getPower(EarthyElementPower.POWER_ID).amount;
            count += amount;
            EarthyElementPower.TryActiveEarthyElement(monster, amount, true);
        }
        if (monster.hasPower(WateryElementPower.POWER_ID))
        {
            int amount = monster.getPower(WateryElementPower.POWER_ID).amount;
            count += amount;
            WateryElementPower.TryActiveWateryElement(monster, amount, true);
        }
        if (monster.hasPower(FireyElementPower.POWER_ID))
        {
            int amount = monster.getPower(FireyElementPower.POWER_ID).amount;
            count += amount;
            FireyElementPower.TryActiveFireyElement(monster, amount, true);
        }
        if (monster.hasPower(WindyElementPower.POWER_ID))
        {
            int amount = monster.getPower(WindyElementPower.POWER_ID).amount;
            count += amount;
            WindyElementPower.TryActiveWindyElement(monster, amount, true);
        }

        if (count > 0)
        {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, new DamageInfo(player, this.getCorrentValue(count), DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.POISON));
        }

        AbstractDungeon.actionManager.addToBottom(new ApplyElementAction(monster, player, new WindyElementPower(monster, this.correctMagicNumber()), correctMagicNumber(), true));
    }
}
