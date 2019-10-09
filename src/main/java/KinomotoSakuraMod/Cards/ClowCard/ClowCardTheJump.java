package KinomotoSakuraMod.Cards.ClowCard;

import KinomotoSakuraMod.Cards.KSMOD_AbstractMagicCard;
import KinomotoSakuraMod.Patches.KSMOD_CustomCardColor;
import KinomotoSakuraMod.Patches.KSMOD_CustomTag;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.random.Random;

import java.util.ArrayList;

public class ClowCardTheJump extends KSMOD_AbstractMagicCard
{
    public static final String ID = "ClowCardTheJump";
    private static final String NAME;
    private static final String DESCRIPTION;
    private static final String[] EXTENDED_DESCRIPTION;
    private static final String IMAGE_PATH = "img/cards/clowcard/the_jump.png";
    private static final int COST = 1;
    private static final CardType CARD_TYPE = CardType.SKILL;
    private static final CardColor CARD_COLOR = KSMOD_CustomCardColor.CLOWCARD_COLOR;
    private static final CardRarity CARD_RARITY = CardRarity.UNCOMMON;
    private static final CardTarget CARD_TARGET = CardTarget.SELF;
    private static final int BASE_BLOCK = 7;
    private static final int UPGRADE_BLOCK = 4;

    static
    {
        CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    }

    public ClowCardTheJump()
    {
        super(ID, NAME, IMAGE_PATH, COST, DESCRIPTION, CARD_TYPE, CARD_COLOR, CARD_RARITY, CARD_TARGET, true);
        this.tags.add(KSMOD_CustomTag.KSMOD_WINDY_CARD);
        this.baseBlock = BASE_BLOCK;
        this.exhaust = true;
    }

    @Override
    public void upgrade()
    {
        if (!this.upgraded)
        {
            upgradeName();
            upgradeBlock(UPGRADE_BLOCK);
        }
    }

    @Override
    public KSMOD_AbstractMagicCard makeCopy()
    {
        return new ClowCardTheJump();
    }

    @Override
    public void applyNormalEffect(AbstractPlayer player, AbstractMonster monster)
    {
        ArrayList<AbstractPower> debuffList = GetDebuffList();
        if (debuffList.size() > 0)
        {
            int index = new Random().random(debuffList.size() - 1);
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(player, player, debuffList.get(index)));
        }
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(player, player, this.block));
    }

    @Override
    public void applyExtraEffect(AbstractPlayer player, AbstractMonster monster)
    {
        ArrayList<AbstractPower> debuffList = GetDebuffList();
        for (AbstractPower power : debuffList)
        {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(player, player, power));
        }
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(player, player, this.block));
    }

    @Override
    public String getExtraDescription()
    {
        return EXTENDED_DESCRIPTION[0];
    }

    private ArrayList<AbstractPower> GetDebuffList()
    {
        ArrayList<AbstractPower> debuffList = new ArrayList<>();
        for (int i = 0; i < AbstractDungeon.player.powers.size(); i++)
        {
            AbstractPower power = AbstractDungeon.player.powers.get(i);
            if (power.type == AbstractPower.PowerType.DEBUFF)
            {
                debuffList.add(power);
            }
        }
        return debuffList;
    }
}
