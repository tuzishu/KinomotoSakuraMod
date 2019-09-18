package KinomotoSakuraMod.Cards.SakuraCard;

import KinomotoSakuraMod.Cards.ClowCard.ClowCardTheDream;
import KinomotoSakuraMod.Cards.KSMOD_AbstractMagicCard;
import KinomotoSakuraMod.Characters.KinomotoSakura;
import KinomotoSakuraMod.Patches.KSMOD_CustomCardColor;
import KinomotoSakuraMod.Patches.KSMOD_CustomTag;
import KinomotoSakuraMod.Utility.KSMOD_Utility;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import com.megacrit.cardcrawl.vfx.combat.HeartBuffEffect;

import java.util.ArrayList;

public class SakuraCardTheDream extends KSMOD_AbstractMagicCard
{
    public static final String ID = "SakuraCardTheDream";
    private static final String NAME;
    private static final String DESCRIPTION;
    private static final String IMAGE_PATH = "img/cards/sakuracard/the_dream.png";
    private static final int COST = 1;
    private static final CardType CARD_TYPE = CardType.SKILL;
    private static final CardColor CARD_COLOR = KSMOD_CustomCardColor.SAKURACARD_COLOR;
    private static final CardRarity CARD_RARITY = CardRarity.SPECIAL;
    private static final CardTarget CARD_TARGET = CardTarget.NONE;
    private ArrayList<String> sakuraCardIDList = new ArrayList<>();

    static
    {
        CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
    }

    public SakuraCardTheDream()
    {
        super(ID, NAME, IMAGE_PATH, COST, DESCRIPTION, CARD_TYPE, CARD_COLOR, CARD_RARITY, CARD_TARGET);
        this.tags.add(KSMOD_CustomTag.KSMOD_WINDY_CARD);
        this.exhaust = true;
    }

    @Override
    public void upgrade()
    {

    }

    @Override
    public KSMOD_AbstractMagicCard makeCopy()
    {
        if (KSMOD_Utility.IsReallyCopyingCard() && this.hasSameSakuraCard())
        {
            return getSameNameClowCard();
        }
        return new SakuraCardTheDream();
    }

    public KSMOD_AbstractMagicCard getSameNameClowCard()
    {
        return new ClowCardTheDream();
    }

    @Override
    public void applyNormalEffect(AbstractPlayer player, AbstractMonster monster)
    {
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new HeartBuffEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY)));
        sakuraCardIDList.clear();
        RecordSakuraCardIDInGroup(AbstractDungeon.player.hand);
        RecordSakuraCardIDInGroup(AbstractDungeon.player.drawPile);
        RecordSakuraCardIDInGroup(AbstractDungeon.player.discardPile);
        RecordSakuraCardIDInGroup(AbstractDungeon.player.exhaustPile);
        ReplaceClowCardToSakuraCard(AbstractDungeon.player.hand);
        // ReplaceClowCardToSakuraCard(AbstractDungeon.player.drawPile);
        // ReplaceClowCardToSakuraCard(AbstractDungeon.player.discardPile);
        // ReplaceClowCardToSakuraCard(AbstractDungeon.player.exhaustPile);
    }

    private void RecordSakuraCardIDInGroup(CardGroup group)
    {
        for (AbstractCard card : group.group)
        {
            if (card.color == KSMOD_CustomCardColor.SAKURACARD_COLOR && !hasSameIDInList(card.cardID))
            {
                sakuraCardIDList.add(card.cardID);
            }
        }
    }

    private boolean hasSameIDInList(String targetID)
    {
        for (String id : sakuraCardIDList)
        {
            if (id.equals(targetID))
            {
                return true;
            }
        }
        return false;
    }

    private void ReplaceClowCardToSakuraCard(CardGroup group)
    {
        for (int i = 0; i < group.group.size(); i++)
        {
            AbstractCard clowCard = group.group.get(i);
            if (!hasSameIDInList(clowCard.cardID.replaceAll("Clow", "Sakura")))
            {
                group.group.remove(i);
                AbstractCard sakuraCard = GetSameNameSakuraCard(clowCard);
                sakuraCardIDList.add(sakuraCard.cardID);
                group.group.add(i, sakuraCard);
                group.refreshHandLayout();
            }
        }
    }

    private AbstractCard GetSameNameSakuraCard(AbstractCard clowCard)
    {
        AbstractCard sakuraCard = null;
        try
        {
            Class obj = Class.forName(clowCard.getClass().getName().replaceAll("Clow", "Sakura"));
            sakuraCard = (AbstractCard) obj.newInstance();
        }
        catch (Exception e)
        {
            KSMOD_Utility.Logger.info(clowCard.name + "，转换小樱牌失败。");
            AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, clowCard.name + "，" + KinomotoSakura.GetMessage(0), true));
            e.printStackTrace();
            sakuraCard = clowCard;
        }
        return sakuraCard;
    }
}
