package KinomotoSakuraMod.Cards.ClowCard;

import KinomotoSakuraMod.Actions.ApplyElementAction;
import KinomotoSakuraMod.Cards.KSMOD_AbstractMagicCard;
import KinomotoSakuraMod.Patches.CustomCardColor;
import KinomotoSakuraMod.Patches.CustomTag;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.random.Random;

import java.util.ArrayList;

public class ClowCardTheBubbles extends KSMOD_AbstractMagicCard
{
    public static final String ID = "ClowCardTheBubbles";
    private static final String NAME;
    private static final String DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION;
    private static final String IMAGE_PATH = "img/cards/clowcard/the_bubbles.png";
    private static final int COST = 1;
    private static final CardType CARD_TYPE = CardType.SKILL;
    private static final CardColor CARD_COLOR = CustomCardColor.CLOWCARD_COLOR;
    private static final CardRarity CARD_RARITY = CardRarity.UNCOMMON;
    private static final CardTarget CARD_TARGET = CardTarget.ENEMY;
    private static final int BASE_MAGIC_NUMBER = 2;
    private static final int ACTIVE_NUMBER = 18;

    static
    {
        CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }

    public ClowCardTheBubbles()
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
            this.exhaust = false;
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public KSMOD_AbstractMagicCard makeCopy()
    {
        return new ClowCardTheBubbles();
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        ArrayList<AbstractPower> buffs = new ArrayList<AbstractPower>();
        for (AbstractPower power : monster.powers)
        {
            if (power.type == AbstractPower.PowerType.BUFF)
            {
                buffs.add(power);
            }
        }
        if (buffs.size() > 0)
        {
            if (WateryElementPower.TryActiveWateryElement(monster, ACTIVE_NUMBER, true))
            {
                for (AbstractPower power : buffs)
                {
                    AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(monster, player, power));
                }
            }
            else
            {
                int sub = new Random().random(0, buffs.size() - 1);
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(monster, player, buffs.get(sub)));
            }
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyElementAction(monster, player, new WateryElementPower(monster, this.magicNumber), this.magicNumber, true));
    }
}
