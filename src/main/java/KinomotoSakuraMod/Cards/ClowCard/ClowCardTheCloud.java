package KinomotoSakuraMod.Cards.ClowCard;

import KinomotoSakuraMod.Actions.ApplyElementAction;
import KinomotoSakuraMod.Cards.AbstractMagicCard;
import KinomotoSakuraMod.Patches.CustomCardColor;
import KinomotoSakuraMod.Patches.CustomTag;
import KinomotoSakuraMod.Powers.CloudPower;
import KinomotoSakuraMod.Powers.DarkElementPower;
import KinomotoSakuraMod.Powers.WateryElementPower;
import KinomotoSakuraMod.Powers.WindyElementPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class ClowCardTheCloud extends AbstractMagicCard
{
    public static final String ID = "ClowCardTheCloud";
    private static final String NAME;
    private static final String DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION;
    private static final String IMAGE_PATH = "img/cards/clowcard/the_cloud.png";
    private static final int COST = 1;
    private static final CardType CARD_TYPE = CardType.SKILL;
    private static final CardColor CARD_COLOR = CustomCardColor.CLOWCARD_COLOR;
    private static final CardRarity CARD_RARITY = CardRarity.COMMON;
    private static final CardTarget CARD_TARGET = CardTarget.SELF;
    private static final int BASE_MAGIC_NUMBER = 1;
    private static final int ELEMENT_NUMBER = 2;
    private static final int ACTIVE_NUMBER = 12;

    static
    {
        CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }

    public ClowCardTheCloud()
    {
        super(ID, NAME, IMAGE_PATH, COST, DESCRIPTION, CARD_TYPE, CARD_COLOR, CARD_RARITY, CARD_TARGET, CustomTag.ELEMENT_CARD);
        this.setBaseMagicNumber(BASE_MAGIC_NUMBER);
        this.exhaust = true;
    }

    @Override
    public void upgrade()
    {
        if (!this.upgraded)
        {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public AbstractMagicCard makeCopy()
    {
        return new ClowCardTheCloud();
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new CloudPower(player, this.correctMagicNumber()), this.correctMagicNumber()));

        if (upgraded)
        {
            int windyCount = 0;
            for (AbstractMonster mon: AbstractDungeon.getMonsters().monsters)
            {
                int index = AbstractDungeon.getMonsters().monsters.indexOf(mon);
                if (mon.hasPower(WindyElementPower.POWER_ID))
                {
                    int amount = mon.getPower(WindyElementPower.POWER_ID).amount;
                    windyCount += amount;
                }
            }
            if (windyCount >= ACTIVE_NUMBER)
            {
                int size = AbstractDungeon.getMonsters().monsters.size();
                int[] countList = new int[size];
                for (int time = 0, count = 0; count < ACTIVE_NUMBER; time++)
                {
                    AbstractMonster mon = AbstractDungeon.getMonsters().monsters.get(time % size);
                    if (mon.hasPower(WindyElementPower.POWER_ID))
                    {
                        if (mon.getPower(WindyElementPower.POWER_ID).amount > countList[time % size])
                        {
                            countList[time % size] += 1;
                            count += 1;
                        }
                    }
                }

                for (int i = 0; i < countList.length; i++)
                {
                    AbstractMonster mon = AbstractDungeon.getMonsters().monsters.get(i);
                    if (countList[i] > 0)
                    {
                        WindyElementPower.TryActiveWindyElement(mon, countList[i], true);
                    }
                }
            }
        }

        for (AbstractMonster mon : AbstractDungeon.getMonsters().monsters)
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyElementAction(mon, player, new WateryElementPower(mon, ELEMENT_NUMBER), ELEMENT_NUMBER, true));
        }
        for (AbstractMonster mon : AbstractDungeon.getMonsters().monsters)
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyElementAction(mon, player, new WindyElementPower(mon, ELEMENT_NUMBER), ELEMENT_NUMBER, true));
        }
        for (AbstractMonster mon : AbstractDungeon.getMonsters().monsters)
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyElementAction(mon, player, new DarkElementPower(mon, ELEMENT_NUMBER), ELEMENT_NUMBER, true));
        }
    }
}
