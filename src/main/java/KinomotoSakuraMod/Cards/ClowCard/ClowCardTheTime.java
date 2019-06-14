package KinomotoSakuraMod.Cards.ClowCard;

import KinomotoSakuraMod.Cards.KSMOD_AbstractMagicCard;
import KinomotoSakuraMod.Patches.KSMOD_CustomCardColor;
import KinomotoSakuraMod.Powers.TimePower;
import KinomotoSakuraMod.Utility.KSMOD_Utility;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
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
    private static final String IMAGE_PATH = "img/cards/clowcard/the_time.png";
    private static final int COST = 3;
    private static final AbstractCard.CardType CARD_TYPE = AbstractCard.CardType.ATTACK;
    private static final AbstractCard.CardColor CARD_COLOR = KSMOD_CustomCardColor.CLOWCARD_COLOR;
    private static final CardRarity CARD_RARITY = CardRarity.RARE;
    private static final CardTarget CARD_TARGET = CardTarget.ALL_ENEMY;
    private static final int BASE_DAMAGE = 8;
    private static final int UPGRADE_DAMAGE = 10;

    static
    {
        CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
    }

    public ClowCardTheTime()
    {
        super(ID, NAME, IMAGE_PATH, COST, DESCRIPTION, CARD_TYPE, CARD_COLOR, CARD_RARITY, CARD_TARGET);
        this.baseDamage = BASE_DAMAGE;
        this.exhaust = true;
    }

    @Override
    public void upgrade()
    {
        if (!this.upgraded)
        {
            upgradeName();
            upgradeDamage(UPGRADE_DAMAGE);
        }
    }

    @Override
    public KSMOD_AbstractMagicCard makeCopy()
    {
        return new ClowCardTheTime();
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        ArrayList<AbstractMonster> monsters = AbstractDungeon.getMonsters().monsters;
        for (int i = 0; i < monsters.size(); i++)
        {
            AbstractMonster mon = monsters.get(i);
            if (mon.hasPower(ArtifactPower.POWER_ID))
            {
                int artiAmount = mon.getPower(ArtifactPower.POWER_ID).amount;
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(mon, player, ArtifactPower.POWER_ID));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mon, player, new TimePower(mon, 1), 1));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mon, player, new ArtifactPower(mon, artiAmount), artiAmount));
            }
            else
            {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mon, player, new TimePower(mon, 1), 1));
            }
        }
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(player, KSMOD_Utility.GetDamageList(this.damage), DamageInfo.DamageType.HP_LOSS, AbstractGameAction.AttackEffect.POISON));
    }
}
