package KinomotoSakuraMod.Cards.SpellCard;

import KinomotoSakuraMod.Actions.KSMOD_ReleaseAction;
import KinomotoSakuraMod.Cards.KSMOD_AbstractSpellCard;
import KinomotoSakuraMod.Characters.KinomotoSakura;
import KinomotoSakuraMod.Patches.KSMOD_CustomCardColor;
import KinomotoSakuraMod.Relics.KSMOD_SwordJade;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerToRandomEnemyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

public class SpellCardRelease extends KSMOD_AbstractSpellCard
{
    public static final String ID = "SpellCardRelease";
    private static final String NAME;
    private static final String DESCRIPTION;
    private static final String IMAGE_PATH = "img/cards/default_card.png";
    private static final int COST = 1;
    private static final CardType CARD_TYPE = CardType.SKILL;
    private static final CardColor CARD_COLOR = KSMOD_CustomCardColor.SPELL_COLOR;
    private static final CardRarity CARD_RARITY = CardRarity.BASIC;
    private static final CardTarget CARD_TARGET = CardTarget.NONE;
    private static final int BASE_MAGIC_NUMBER = 1;
    public static final float BASE_RELEASE_UPGRADE_RATE = 0.5F;
    public static final float UPGRADE_RELEASE_UPGRADE_RATE = 1.0F;

    static
    {
        CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
    }

    public SpellCardRelease()
    {
        super(ID, NAME, IMAGE_PATH, COST, DESCRIPTION, CARD_TYPE, CARD_COLOR, CARD_RARITY, CARD_TARGET);
        this.setBaseMagicNumber(BASE_MAGIC_NUMBER);
    }

    public boolean canUpgrade()
    {
        return false;
    }

    @Override
    public void upgrade()
    {

    }

    @Override
    public KSMOD_AbstractSpellCard makeCopy()
    {
        return new SpellCardRelease();
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        if (player.hasRelic(KSMOD_SwordJade.RELIC_ID))
        {
            AbstractDungeon.actionManager.addToBottom(new KSMOD_ReleaseAction(UPGRADE_RELEASE_UPGRADE_RATE));
            player.getRelic(KSMOD_SwordJade.RELIC_ID).flash();
        }
        else
        {
            AbstractDungeon.actionManager.addToBottom(new KSMOD_ReleaseAction(BASE_RELEASE_UPGRADE_RATE));
        }
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters)
        {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(m, player, new VulnerablePower(monster, this.magicNumber, false), this.magicNumber, true));
        }
        AbstractDungeon.actionManager.addToBottom(new TalkAction(true, KinomotoSakura.GetMessage(3), 1.0F, 2.0F));
    }
}
