package KinomotoSakuraMod.Cards.SakuraCard;

import KinomotoSakuraMod.Cards.KSMOD_AbstractMagicCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SakuraCardTheVoice extends KSMOD_AbstractMagicCard
{

    public SakuraCardTheVoice(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target)
    {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
    }

    @Override
    public void upgrade()
    {

    }

    @Override
    public AbstractCard makeCopy()
    {
        return null;
    }

    @Override
    public void applyNormalEffect(AbstractPlayer player, AbstractMonster monster)
    {

    }
}
