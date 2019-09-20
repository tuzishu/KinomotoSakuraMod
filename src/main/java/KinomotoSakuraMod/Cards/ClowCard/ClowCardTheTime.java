package KinomotoSakuraMod.Cards.ClowCard;

import KinomotoSakuraMod.Cards.KSMOD_AbstractMagicCard;
import KinomotoSakuraMod.Patches.KSMOD_CustomCardColor;
import KinomotoSakuraMod.Patches.KSMOD_CustomTag;
import KinomotoSakuraMod.Powers.KSMOD_TimePower;
import KinomotoSakuraMod.Utility.KSMOD_Utility;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;

import java.util.ArrayList;

public class ClowCardTheTime extends KSMOD_AbstractMagicCard
{
    public static final String ID = "ClowCardTheTime";
    private static final String NAME;
    private static final String DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION;
    private static final String[] EXTENDED_DESCRIPTION;
    private static final String IMAGE_PATH = "img/cards/clowcard/the_time.png";
    private static final int COST = 2;
    private static final AbstractCard.CardType CARD_TYPE = CardType.SKILL;
    private static final AbstractCard.CardColor CARD_COLOR = KSMOD_CustomCardColor.CLOWCARD_COLOR;
    private static final CardRarity CARD_RARITY = CardRarity.RARE;
    private static final CardTarget CARD_TARGET = CardTarget.ALL_ENEMY;
    private static final int BASE_DAMAGE = 9;
    private static final int VOID_NUMBER = 2;

    static
    {
        CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
        UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }

    public ClowCardTheTime()
    {
        super(ID, NAME, IMAGE_PATH, COST, DESCRIPTION, CARD_TYPE, CARD_COLOR, CARD_RARITY, CARD_TARGET, true);
        this.tags.add(KSMOD_CustomTag.KSMOD_WATERY_CARD);
        this.baseDamage = BASE_DAMAGE;
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
    public KSMOD_AbstractMagicCard makeCopy()
    {
        return new ClowCardTheTime();
    }

    @Override
    public void applyNormalEffect(AbstractPlayer player, AbstractMonster monster)
    {
        applyExtraEffect(player, monster);
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new VoidCard(), VOID_NUMBER));
    }

    @Override
    public void applyExtraEffect(AbstractPlayer player, AbstractMonster monster)
    {
        ArrayList<AbstractMonster> monsters = AbstractDungeon.getMonsters().monsters;
        for (int i = 0; i < monsters.size(); i++)
        {
            AbstractMonster mon = monsters.get(i);
            if (mon.hasPower(ArtifactPower.POWER_ID))
            {
                int artiAmount = mon.getPower(ArtifactPower.POWER_ID).amount;
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(mon, player, ArtifactPower.POWER_ID));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mon, player, new KSMOD_TimePower(mon, 1), 1));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mon, player, new ArtifactPower(mon, artiAmount), artiAmount));
            }
            else
            {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mon, player, new KSMOD_TimePower(mon, 1), 1));
            }
        }
        if (upgraded)
        {
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(player, KSMOD_Utility.GetDamageList(this.damage), DamageInfo.DamageType.HP_LOSS, AbstractGameAction.AttackEffect.POISON));
        }
    }

    @Override
    public String getExtraDescription()
    {
        return (this.upgraded ? EXTENDED_DESCRIPTION[0] : EXTENDED_DESCRIPTION[1]);
    }
}
