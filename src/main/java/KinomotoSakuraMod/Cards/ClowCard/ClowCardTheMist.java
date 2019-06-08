package KinomotoSakuraMod.Cards.ClowCard;

import KinomotoSakuraMod.Cards.KSMOD_AbstractMagicCard;
import KinomotoSakuraMod.Patches.CustomCardColor;
import KinomotoSakuraMod.Patches.CustomTag;
import KinomotoSakuraMod.Powers.MistPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.powers.ThornsPower;

public class ClowCardTheMist extends KSMOD_AbstractMagicCard
{
    public static final String ID = "ClowCardTheMist";
    private static final String NAME;
    private static final String DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION;
    private static final String IMAGE_PATH = "img/cards/clowcard/the_mist.png";
    private static final int COST = 0;
    private static final CardType CARD_TYPE = CardType.SKILL;
    private static final CardColor CARD_COLOR = CustomCardColor.CLOWCARD_COLOR;
    private static final CardRarity CARD_RARITY = CardRarity.RARE;
    private static final CardTarget CARD_TARGET = CardTarget.ALL_ENEMY;
    private static final int ACTIVE_NUMBER = 10;
    private static final int UPGRADE_MAGIC_NUMBER = 3;
    private static final int ELEMENT_NUMBER = 2;

    static
    {
        CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }

    public ClowCardTheMist()
    {
        super(ID, NAME, IMAGE_PATH, COST, DESCRIPTION, CARD_TYPE, CARD_COLOR, CARD_RARITY, CARD_TARGET, CustomTag.ELEMENT_CARD);
        this.exhaust = true;
    }

    @Override
    public void upgrade()
    {
        if (!this.upgraded)
        {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_MAGIC_NUMBER);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public KSMOD_AbstractMagicCard makeCopy()
    {
        return new ClowCardTheMist();
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        for (AbstractMonster mon : AbstractDungeon.getMonsters().monsters)
        {
            AbstractDungeon.actionManager.addToBottom(new RemoveAllBlockAction(mon, player));
            if (this.upgraded)
            {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mon, player, new MistPower(mon, this.magicNumber), this.magicNumber, true));
            }
            if (DarkElementPower.TryActiveDarkElement(mon, ACTIVE_NUMBER, true))
            {
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(mon, player, PlatedArmorPower.POWER_ID));
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(mon, player, MetallicizePower.POWER_ID));
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(mon, player, ThornsPower.POWER_ID));
            }
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mon, player, new DarkElementPower(mon, ELEMENT_NUMBER), ELEMENT_NUMBER, true));
        }
    }
}
