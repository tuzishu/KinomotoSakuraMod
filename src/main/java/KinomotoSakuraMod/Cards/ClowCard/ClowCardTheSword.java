package KinomotoSakuraMod.Cards.ClowCard;

import KinomotoSakuraMod.Cards.KSMOD_AbstractMagicCard;
import KinomotoSakuraMod.Cards.SakuraCard.SakuraCardTheSword;
import KinomotoSakuraMod.Characters.KinomotoSakura;
import KinomotoSakuraMod.Patches.KSMOD_CustomCardColor;
import KinomotoSakuraMod.Patches.KSMOD_CustomTag;
import KinomotoSakuraMod.Relics.KSMOD_SealedBook;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ClowCardTheSword extends KSMOD_AbstractMagicCard
{
    public static final String ID = "ClowCardTheSword";
    private static final String NAME;
    private static final String DESCRIPTION;
    private static final String[] EXTENDED_DESCRIPTION;
    private static final String IMAGE_PATH = "img/cards/clowcard/the_sword.png";
    private static final int COST = 1;
    private static final CardType CARD_TYPE = CardType.ATTACK;
    private static final CardColor CARD_COLOR = KSMOD_CustomCardColor.CLOWCARD_COLOR;
    private static final CardRarity CARD_RARITY = CardRarity.BASIC;
    private static final CardTarget CARD_TARGET = CardTarget.ENEMY;
    public static final int BASE_DAMAGE = 6;
    public static final int UPGRADE_DAMAGE = 3;
    private static final float LONE_RATE = 0.2F;
    private static final float MAX_LONE_RATE = 0.6F;
    private static final int BASE_COUNT = 4;

    static
    {
        CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    }

    public ClowCardTheSword()
    {
        super(ID, NAME, IMAGE_PATH, COST, DESCRIPTION, CARD_TYPE, CARD_COLOR, CARD_RARITY, CARD_TARGET, true);
        this.tags.add(KSMOD_CustomTag.KSMOD_FIREY_CARD);
        this.tags.add(CardTags.STRIKE);
        this.tags.add(CardTags.STARTER_STRIKE);
        this.cardsToPreview = new SakuraCardTheSword();
        this.baseDamage = BASE_DAMAGE;
        this.misc = BASE_COUNT;
    }

    @Override
    public void upgrade()
    {
        if (!this.upgraded)
        {
            upgradeName();
            this.baseDamage = GetDamage(this);
            this.upgradedDamage = true;
            initializeDescription();
        }
    }

    @Override
    public KSMOD_AbstractMagicCard makeCopy()
    {
        return new ClowCardTheSword();
    }


    @Override
    public void applyNormalEffect(AbstractPlayer player, AbstractMonster monster)
    {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, new DamageInfo(player, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }

    @Override
    public void applyExtraEffect(AbstractPlayer player, AbstractMonster monster)
    {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, new DamageInfo(player, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, new DamageInfo(player, KSMOD_SealedBook.REAL_DAMAGE, DamageInfo.DamageType.HP_LOSS), AbstractGameAction.AttackEffect.SLASH_HEAVY));
    }

    @Override
    public String getExtraDescription()
    {
        return this.rawDescription + EXTENDED_DESCRIPTION[0] + KSMOD_SealedBook.REAL_DAMAGE + EXTENDED_DESCRIPTION[1];
    }

    public void onRemoveFromMasterDeck()
    {
        ResetMisc();
        SetDamage();
    }

    private void ResetMisc()
    {
        int amount = 0;
        if (AbstractDungeon.player instanceof KinomotoSakura)
        {
            for (AbstractCard c : AbstractDungeon.player.masterDeck.group)
            {
                if (c.cardID.equals(ID))
                {
                    amount += 1;
                }
            }
        }
        this.misc = MathUtils.clamp(amount, 1, 4);
    }

    private void SetDamage()
    {
        SetGroup(AbstractDungeon.player.masterDeck);
        SetGroup(AbstractDungeon.player.hand);
        SetGroup(AbstractDungeon.player.drawPile);
        SetGroup(AbstractDungeon.player.discardPile);
        SetGroup(AbstractDungeon.player.exhaustPile);
    }

    private void SetGroup(CardGroup group)
    {
        for (AbstractCard c : group.group)
        {
            if (c.cardID.contains(ID))
            {
                c.misc = this.misc;
                c.baseDamage = GetDamage(c);
            }
        }
    }

    public int GetDamage(AbstractCard card)
    {
        return MathUtils.floor((BASE_DAMAGE + (card.upgraded ? UPGRADE_DAMAGE : 0)) * (1F + MathUtils.clamp((BASE_COUNT - card.misc) * LONE_RATE, 0, MAX_LONE_RATE)));
    }
}
