package KinomotoSakuraMod.Cards;

import KinomotoSakuraMod.Patches.AbstractClowCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ClowCardTheSword extends AbstractClowCard
{

    public ClowCardTheSword(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target)
    {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
    }

    public void upgrade()
    {

    }

    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster)
    {

    }
}
