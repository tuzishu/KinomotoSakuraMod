package KinomotoSakuraMod.Cards;

import KinomotoSakuraMod.Power.EnhancementMagickPower;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public abstract class AbstractModCard extends CustomCard
{
    public CardMagicalType magicalType;

    public AbstractModCard(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target, CardMagicalType magicaltype)
    {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
        this.magicalType = magicaltype;
    }

    public abstract void upgrade();

    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        this.onUsed(player, monster);
        switch (this.magicalType)
        {
            case PhysicsCard:
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new EnhancementMagickPower()));
                break;
            case ElementCard:
                break;
            default:
                break;
        }
    }

    public abstract void onUsed(AbstractPlayer player, AbstractMonster monster);
}