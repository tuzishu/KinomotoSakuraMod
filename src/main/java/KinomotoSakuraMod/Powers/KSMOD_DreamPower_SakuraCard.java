package KinomotoSakuraMod.Powers;

import KinomotoSakuraMod.Characters.KinomotoSakura;
import KinomotoSakuraMod.Patches.KSMOD_CustomCardColor;
import KinomotoSakuraMod.Utility.KSMOD_LoggerTool;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;

import java.util.ArrayList;

public class KSMOD_DreamPower_SakuraCard extends KSMOD_CustomPower
{
    public static final String POWER_ID = "KSMOD_DreamPower_SakuraCard";
    public static final String POWER_NAME;
    public static final String[] POWER_DESCRIPTIONS;
    private static final String POWER_IMG_PATH = "establishment";
    private static final PowerType POWER_TYPE = PowerType.BUFF;
    private ArrayList<String> sakuraCardIDList = new ArrayList<>();
    private ArrayList<AbstractCard> originalCards = new ArrayList<>();
    private ArrayList<AbstractCard> templateCards = new ArrayList<>();

    static
    {
        PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        POWER_NAME = powerStrings.NAME;
        POWER_DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }

    public KSMOD_DreamPower_SakuraCard(AbstractCreature target)
    {
        super(POWER_ID, POWER_NAME, POWER_IMG_PATH, POWER_TYPE, target, 1, false);
        this.isTurnBased = true;
    }

    public void updateDescription()
    {
        this.description = GetCurrentDescription();
    }

    private String GetCurrentDescription()
    {
        String desc = POWER_DESCRIPTIONS[0];
        boolean isFirst = true;
        if (originalCards != null && originalCards.size() > 0)
        {
            for (AbstractCard c : originalCards)
            {
                if (isFirst)
                {
                    desc += c.name;
                    isFirst = false;
                }
                else
                {
                    desc += POWER_DESCRIPTIONS[1] + c.name;
                }
            }
        }
        else
        {
            desc += POWER_DESCRIPTIONS[2];
        }
        return desc;
    }

    public void onInitialApplication()
    {
        sakuraCardIDList.clear();
        originalCards.clear();
        templateCards.clear();
        GetCardInfomation();
        updateDescription();
    }

    public void atEndOfTurn(boolean isPlayer)
    {
        if (!isPlayer)
        {
            return;
        }
        ReturnOriginalClowCards(AbstractDungeon.player.hand);
        ReturnOriginalClowCards(AbstractDungeon.player.drawPile);
        ReturnOriginalClowCards(AbstractDungeon.player.discardPile);
        ReturnOriginalClowCards(AbstractDungeon.player.exhaustPile);
        sakuraCardIDList.clear();
        originalCards.clear();
        templateCards.clear();
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));
    }

    public void GetCardInfomation()
    {
        RecordSakuraCardIDInGroup(AbstractDungeon.player.hand);
        RecordSakuraCardIDInGroup(AbstractDungeon.player.drawPile);
        RecordSakuraCardIDInGroup(AbstractDungeon.player.discardPile);
        RecordSakuraCardIDInGroup(AbstractDungeon.player.exhaustPile);

        GetTemplateSakuraCards(AbstractDungeon.player.hand);
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

    private void GetTemplateSakuraCards(CardGroup group)
    {
        for (int i = 0; i < group.group.size(); i++)
        {
            AbstractCard original = group.group.get(i);
            if (original.cardID.contains("ClowCardThe") && !hasSameIDInList(original.cardID.replaceAll("ClowCardThe",
                    "SakuraCardThe")))
            {
                AbstractCard template = GetSameNameSakuraCard(original);
                originalCards.add(group.group.get(i));
                templateCards.add(template);
                sakuraCardIDList.add(template.cardID);
                group.group.remove(i);
                group.group.add(i, template);
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
            KSMOD_LoggerTool.Logger.error(clowCard.name + "，转换小樱牌失败。");
            AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX,
                    AbstractDungeon.player.dialogY,
                    3.0F,
                    clowCard.name + "，" + KinomotoSakura.GetMessage(0),
                    true));
            e.printStackTrace();
            sakuraCard = clowCard;
        }
        return sakuraCard;
    }

    private void ReturnOriginalClowCards(CardGroup group)
    {
        for (int i = 0; i < group.group.size(); i++)
        {
            AbstractCard template = group.group.get(i);
            if (templateCards.contains(template))
            {
                AbstractCard original = null;
                for (AbstractCard c : originalCards)
                {
                    if (c.cardID.contains(template.cardID.replace("SakuraCardThe", "ClowCardThe")))
                    {
                        original = c;
                    }
                }
                templateCards.remove(template);
                originalCards.remove(original);
                sakuraCardIDList.remove(template.cardID);
                group.group.remove(i);
                group.group.add(i, original);
                group.refreshHandLayout();
            }
        }
    }
}