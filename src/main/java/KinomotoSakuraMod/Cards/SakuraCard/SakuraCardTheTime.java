package KinomotoSakuraMod.Cards.SakuraCard;

import KinomotoSakuraMod.Cards.ClowCard.ClowCardTheTime;
import KinomotoSakuraMod.Cards.KSMOD_AbstractMagicCard;
import KinomotoSakuraMod.Patches.KSMOD_CustomCardColor;
import KinomotoSakuraMod.Patches.KSMOD_CustomTag;
import KinomotoSakuraMod.Powers.KSMOD_TimePower;
import KinomotoSakuraMod.Utility.KSMOD_ReflectTool;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;

import java.util.ArrayList;

public class SakuraCardTheTime extends KSMOD_AbstractMagicCard
{
    public static final String ID = "SakuraCardTheTime";
    private static final String NAME;
    private static final String DESCRIPTION;
    private static final String IMAGE_PATH = "img/cards/sakuracard/the_time.png";
    private static final int COST = 1;
    private static final AbstractCard.CardType CARD_TYPE = CardType.SKILL;
    private static final AbstractCard.CardColor CARD_COLOR = KSMOD_CustomCardColor.SAKURACARD_COLOR;
    private static final CardRarity CARD_RARITY = CardRarity.SPECIAL;
    private static final CardTarget CARD_TARGET = CardTarget.ALL_ENEMY;
    private static final int BASE_MAGIC_NUMBER = 3;
    private static final int VOID_NUMBER = 1;

    static
    {
        CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
    }

    public SakuraCardTheTime()
    {
        super(ID, NAME, IMAGE_PATH, COST, DESCRIPTION, CARD_TYPE, CARD_COLOR, CARD_RARITY, CARD_TARGET);
        this.tags.add(KSMOD_CustomTag.KSMOD_WATERY_CARD);
        this.setBaseMagicNumber(BASE_MAGIC_NUMBER);
        this.isInnate = true;
        this.exhaust = true;
    }

    @Override
    public void upgrade()
    {

    }

    @Override
    public KSMOD_AbstractMagicCard makeCopy()
    {
        if (KSMOD_ReflectTool.IsReallyCopyingCard() && this.hasSameSakuraCard())
        {
            return getSameNameClowCard();
        }
        return new SakuraCardTheTime();
    }

    public KSMOD_AbstractMagicCard getSameNameClowCard()
    {
        return new ClowCardTheTime();
    }

    @Override
    public void applyNormalEffect(AbstractPlayer player, AbstractMonster monster)
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
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(player, this.magicNumber));
        }
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new VoidCard(), VOID_NUMBER));
    }
}
