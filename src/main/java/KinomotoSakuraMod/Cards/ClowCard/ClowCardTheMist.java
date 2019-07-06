package KinomotoSakuraMod.Cards.ClowCard;

import KinomotoSakuraMod.Cards.KSMOD_AbstractMagicCard;
import KinomotoSakuraMod.Patches.KSMOD_CustomCardColor;
import KinomotoSakuraMod.Patches.KSMOD_CustomTag;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

public class ClowCardTheMist extends KSMOD_AbstractMagicCard
{
    public static final String ID = "ClowCardTheMist";
    private static final String NAME;
    private static final String DESCRIPTION;
    private static final String[] EXTENDED_DESCRIPTION;
    private static final String IMAGE_PATH = "img/cards/clowcard/the_mist.png";
    private static final int COST = 1;
    private static final CardType CARD_TYPE = CardType.SKILL;
    private static final CardColor CARD_COLOR = KSMOD_CustomCardColor.CLOWCARD_COLOR;
    private static final CardRarity CARD_RARITY = CardRarity.COMMON;
    private static final CardTarget CARD_TARGET = CardTarget.ALL_ENEMY;
    private static final int BASE_MAGIC_NUMBER = 4;
    private static final int UPGRADE_MAGIC_NUMBER = 2;

    static
    {
        CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    }

    public ClowCardTheMist()
    {
        super(ID, NAME, IMAGE_PATH, COST, DESCRIPTION, CARD_TYPE, CARD_COLOR, CARD_RARITY, CARD_TARGET, true);
        this.tags.add(KSMOD_CustomTag.KSMOD_WATERY_CARD);
        this.setBaseMagicNumber(BASE_MAGIC_NUMBER);
        this.exhaust = true;
    }

    @Override
    public void upgrade()
    {
        if (!this.upgraded)
        {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_MAGIC_NUMBER);
        }
    }

    @Override
    public KSMOD_AbstractMagicCard makeCopy()
    {
        return new ClowCardTheMist();
    }

    @Override
    public void applyNormalEffect(AbstractPlayer player, AbstractMonster monster)
    {
        for (AbstractMonster mon : AbstractDungeon.getMonsters().monsters)
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mon, player, new PoisonPower(mon, player, this.magicNumber), this.magicNumber));
        }
    }

    public void applyExtraEffect(AbstractPlayer player, AbstractMonster monster)
    {
        for (AbstractMonster mon : AbstractDungeon.getMonsters().monsters)
        {
            AbstractDungeon.actionManager.addToBottom(new RemoveAllBlockAction(mon, player));
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(mon, player, MetallicizePower.POWER_ID));
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(mon, player, PlatedArmorPower.POWER_ID));
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(mon, player, ArtifactPower.POWER_ID));
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(mon, player, ThornsPower.POWER_ID));
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(mon, player, BufferPower.POWER_ID));
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(mon, player, IntangiblePower.POWER_ID));
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(mon, player, MalleablePower.POWER_ID));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mon, player, new PoisonPower(mon, player, this.magicNumber), this.magicNumber));
        }
    }

    public String getExtraDescription()
    {
        return this.rawDescription + EXTENDED_DESCRIPTION[0];
    }
}
