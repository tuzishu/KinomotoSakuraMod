package KinomotoSakuraMod.Cards.SpellCard;

import KinomotoSakuraMod.Actions.KSMOD_ThunderAction;
import KinomotoSakuraMod.Cards.KSMOD_AbstractSpellCard;
import KinomotoSakuraMod.Patches.KSMOD_CustomCardColor;
import KinomotoSakuraMod.Patches.KSMOD_CustomTag;
import com.megacrit.cardcrawl.actions.defect.ThunderStrikeAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SpellCardLeiDi extends KSMOD_AbstractSpellCard
{

    public static final String ID = "SpellCardLeiDi";
    private static final String NAME;
    private static final String DESCRIPTION;
    private static final String IMAGE_PATH = "img/cards/default_card.png";
    private static final int COST = -2;
    private static final CardType CARD_TYPE = CardType.ATTACK;
    private static final CardColor CARD_COLOR = KSMOD_CustomCardColor.SPELL_COLOR;
    private static final CardRarity CARD_RARITY = CardRarity.SPECIAL;
    private static final CardTarget CARD_TARGET = CardTarget.NONE;
    private static final int BASE_DAMAGE = 7;

    static
    {
        CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
    }

    public SpellCardLeiDi()
    {
        super(ID, NAME, IMAGE_PATH, COST, DESCRIPTION, CARD_TYPE, CARD_COLOR, CARD_RARITY, CARD_TARGET);
        this.tags.add(KSMOD_CustomTag.KSMOD_EARTHY_CARD);
        this.exhaust = true;
        this.isEthereal = true;
        this.baseDamage = BASE_DAMAGE;
    }

    @Override
    public void upgrade()
    {

    }

    @Override
    public boolean canUpgrade()
    {
        return false;
    }

    @Override
    public KSMOD_AbstractSpellCard makeCopy()
    {
        return new SpellCardLeiDi();
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        AbstractDungeon.actionManager.addToBottom(new KSMOD_ThunderAction(new DamageInfo(player, this.damage, DamageInfo.DamageType.NORMAL)));
    }
}