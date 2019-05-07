package KinomotoSakuraMod.Cards.SpellCard;

import KinomotoSakuraMod.Cards.AbstractSpellCard;
import KinomotoSakuraMod.Patches.CustomCardColor;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class TestCard extends AbstractSpellCard
{
    public static final String ID = "TestCard";
    private static final String NAME = "测试卡";
    private static final String DESCRIPTION = "对所有敌人造成 !D! 点伤害。";
    private static final String IMAGE_PATH = "img/cards/default_attack_card.png";
    private static final int COST = 0;
    private static final CardType CARD_TYPE = CardType.ATTACK;
    private static final CardColor CARD_COLOR = CustomCardColor.SPELL_COLOR;
    private static final CardRarity CARD_RARITY = CardRarity.SPECIAL;
    private static final CardTarget CARD_TARGET = CardTarget.ALL_ENEMY;
    private static final int BASE_DAMAGE = 99999;

    public TestCard()
    {
        super(ID, NAME, IMAGE_PATH, COST, DESCRIPTION, CARD_TYPE, CARD_COLOR, CARD_RARITY, CARD_TARGET);
        this.baseDamage = BASE_DAMAGE;
    }

    @Override
    public void upgrade()
    {

    }

    @Override
    public AbstractSpellCard makeCopy()
    {
        return new TestCard();
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        int size = AbstractDungeon.getMonsters().monsters.size();
        int[] damageList = new int[size];
        for (int i = 0; i < size; i++)
        {
            damageList[i] = this.damage;
        }
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(player, damageList, this.damageType, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }
}
