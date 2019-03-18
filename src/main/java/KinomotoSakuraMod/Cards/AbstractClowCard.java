package KinomotoSakuraMod.Cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public abstract class AbstractClowCard extends CustomCard
{
    public CardMagicalType magicalType;

    public AbstractClowCard(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target, CardMagicalType magicaltype)
    {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
        this.magicalType = magicaltype;
    }

    public abstract void upgrade();

    public abstract void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster);
}
