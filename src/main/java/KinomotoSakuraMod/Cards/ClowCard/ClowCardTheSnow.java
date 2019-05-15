package KinomotoSakuraMod.Cards.ClowCard;

import KinomotoSakuraMod.Actions.ApplyElementAction;
import KinomotoSakuraMod.Cards.AbstractMagicCard;
import KinomotoSakuraMod.Patches.CustomCardColor;
import KinomotoSakuraMod.Patches.CustomTag;
import KinomotoSakuraMod.Powers.FreezePower;
import KinomotoSakuraMod.Powers.WateryElementPower;
import KinomotoSakuraMod.Powers.WindyElementPower;
import KinomotoSakuraMod.Utility.ModUtility;
import com.evacipated.cardcrawl.mod.stslib.powers.StunMonsterPower;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.WeakPower;

import java.util.ArrayList;

public class ClowCardTheSnow extends AbstractMagicCard
{
    public static final String ID = "ClowCardTheSnow";
    private static final String NAME;
    private static final String DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION;
    private static final String IMAGE_PATH = "img/cards/default_attack_card.png";
    private static final int COST = 2;
    private static final AbstractCard.CardType CARD_TYPE = AbstractCard.CardType.ATTACK;
    private static final AbstractCard.CardColor CARD_COLOR = CustomCardColor.CLOWCARD_COLOR;
    private static final CardRarity CARD_RARITY = CardRarity.COMMON;
    private static final CardTarget CARD_TARGET = CardTarget.ALL_ENEMY;
    private static final int BASE_DAMAGE = 9;
    private static final int UPGRADE_DAMAGE = 3;
    private static final int BASE_MAGIC_NUMBER = 2;
    private static final int FREEZE_ACTIVE_NUMBER = 16;
    private static final int FREEZE_COUNT = 1;
    private static final int WEAKENED_ACTIVE_NUMBER = 18;
    private static final int WEAKENED_COUNT = 2;

    static
    {
        CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }

    public ClowCardTheSnow()
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
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public AbstractMagicCard makeCopy()
    {
        return new ClowCardTheSnow();
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(player, ModUtility.GetDamageList(this.correctDamage()), DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.POISON, true));

        if (upgraded)
        {
            for (AbstractMonster mon: AbstractDungeon.getMonsters().monsters)
            {
                if (WateryElementPower.TryActiveWateryElement(mon, FREEZE_ACTIVE_NUMBER, true))
                {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mon, player, new FreezePower(mon, FREEZE_COUNT), FREEZE_COUNT, true));
                }
            }
            for (AbstractMonster mon: AbstractDungeon.getMonsters().monsters)
            {
                if (WindyElementPower.TryActiveWindyElement(mon, WEAKENED_ACTIVE_NUMBER, true))
                {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mon, player, new WeakPower(mon, WEAKENED_COUNT, false), WEAKENED_COUNT, true));
                }
            }
        }

        for (AbstractMonster mon: AbstractDungeon.getMonsters().monsters)
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyElementAction(mon, player, new WateryElementPower(mon, this.correctMagicNumber()), this.correctMagicNumber(), true));
            AbstractDungeon.actionManager.addToBottom(new ApplyElementAction(mon, player, new WindyElementPower(mon, this.correctMagicNumber()), this.correctMagicNumber(), true));
        }
    }
}
