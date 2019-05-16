package KinomotoSakuraMod.Cards.ClowCard;

import KinomotoSakuraMod.Actions.ApplyElementAction;
import KinomotoSakuraMod.Cards.AbstractMagicCard;
import KinomotoSakuraMod.Patches.CustomCardColor;
import KinomotoSakuraMod.Patches.CustomTag;
import KinomotoSakuraMod.Powers.FireyElementPower;
import KinomotoSakuraMod.Powers.LightElementPower;
import com.megacrit.cardcrawl.actions.defect.ThunderStrikeAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class ClowCardTheThunder extends AbstractMagicCard
{
    public static final String ID = "ClowCardTheThunder";
    private static final String NAME;
    private static final String DESCRIPTION;
    private static final String IMAGE_PATH = "img/cards/default_attack_card.png";
    private static final int COST = 3;
    private static final AbstractCard.CardType CARD_TYPE = AbstractCard.CardType.ATTACK;
    private static final AbstractCard.CardColor CARD_COLOR = CustomCardColor.CLOWCARD_COLOR;
    private static final CardRarity CARD_RARITY = CardRarity.RARE;
    private static final CardTarget CARD_TARGET = CardTarget.NONE;
    private static final int BASE_DAMAGE = 7;
    private static final int UPGRADE_DAMAGE = 4;
    private static final int BASE_MAGIC_NUMBER = 1;
    private static final int UPGRADE_MAGIC_NUMBER = 1;
    private static final int ATTACK_COUNT = 3;

    static
    {
        CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
    }

    public ClowCardTheThunder()
    {
        super(ID, NAME, IMAGE_PATH, COST, DESCRIPTION, CARD_TYPE, CARD_COLOR, CARD_RARITY, CARD_TARGET, CustomTag.ELEMENT_CARD);
        this.baseDamage = BASE_DAMAGE;
        this.setBaseMagicNumber(BASE_MAGIC_NUMBER);
    }

    @Override
    public void upgrade()
    {
        if (!this.upgraded)
        {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_DAMAGE);
            this.upgradeMagicNumber(UPGRADE_MAGIC_NUMBER);
        }
    }

    @Override
    public AbstractMagicCard makeCopy()
    {
        return new ClowCardTheThunder();
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        ArrayList<AbstractMonster> monList = new ArrayList<>();
        for (int i = 0; i < ATTACK_COUNT; i++)
        {
            AbstractMonster mon = AbstractDungeon.getRandomMonster();
            AbstractDungeon.actionManager.addToBottom(new ThunderStrikeAction(mon, new DamageInfo(player, this.correctDamage(), DamageInfo.DamageType.HP_LOSS), 1));
            if (!monList.contains(mon) && mon.hasPower(LightElementPower.POWER_ID))
            {
                int amount = mon.getPower(LightElementPower.POWER_ID).amount;
                amount *= this.correctMagicNumber();
                LightElementPower.TryActiveLightElement(mon, amount, true);
                for (int j = 0; j < amount; j++)
                {
                    if (mon.isDeadOrEscaped())
                    {
                        break;
                    }
                    AbstractDungeon.actionManager.addToBottom(new ThunderStrikeAction(mon, new DamageInfo(player, this.correctDamage(), DamageInfo.DamageType.HP_LOSS), 1));
                }
            }
            AbstractDungeon.actionManager.addToBottom(new ApplyElementAction(mon, player, new FireyElementPower(mon, this.correctMagicNumber()), this.correctMagicNumber(), true));
            if (!monList.contains(mon))
            {
                monList.add(mon);
            }
        }
    }
}
