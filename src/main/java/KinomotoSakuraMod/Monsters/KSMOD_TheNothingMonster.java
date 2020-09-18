package KinomotoSakuraMod.Monsters;

import KinomotoSakuraMod.Cards.SakuraCard.SakuraCardTheLove;
import KinomotoSakuraMod.Effects.KSMOD_PurgeCardEffect;
import KinomotoSakuraMod.Powers.KSMOD_NothingPower_Monster;
import KinomotoSakuraMod.Utility.KSMOD_DataTool;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.RemoveAllPowersAction;
import com.megacrit.cardcrawl.actions.utility.HideHealthBarAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.combat.HeartBuffEffect;
import com.megacrit.cardcrawl.vfx.combat.ViceCrushEffect;

public class KSMOD_TheNothingMonster extends AbstractMonster
{
    public static final String ID = "KSMOD_TheNothingMonster";
    public static final String NAME;
    private static final String[] MOVES;
    private static final String[] DIALOG;
    private static final String IMAGE_PATH = "img/monsters/the_nothing/image.png";
    private static final String MODEL_ATLAS_PATH = "img/monsters/Orin/Orin.atlas";
    private static final String MODEL_JSON_PATH = "img/monsters/Orin/Orin.json";
    private static final String MODEL_CAT_ATLAS = "img/monsters/Orin/rincat.atlas";
    private static final String MODEL_CAT_JSON = "img/monsters/Orin/rincat.json";
    private static final int HP = 187;
    private static final int HP_ADV = 214;
    private static final int START_VOID_NUMBER = 3;
    private static final int START_VOID_NUMBER_ADV = 5;
    private static final int THUMP_DAMAGE = 32;
    private static final int THUMP_DAMAGE_ADV = 44;
    private static final int BATTER_DAMAGE = 7;
    private static final int BATTER_DAMAGE_ADV = 9;
    private static final int BATTER_NUMBER = 3;
    private static final int BATTER_NUMBER_ADV = 4;
    private static final int DESTRUCTION_DAMAGE = 12;
    private static final int DESTRUCTION_DAMAGE_ADV = 19;
    private static final int DESTRUCTION_VOID_NUMBER = 2;
    private static final int DESTRUCTION_VOID_NUMBER_ADV = 3;
    private static final int DESTRUCTION_EXHAUST_NUMBER = 1;
    private static final int DESTRUCTION_EXHAUST_NUMBER_ADV = 2;
    private static final int STRENGTHEN_NUMBER = 2;
    private static final int STRENGTHEN_NUMBER_ADV = 4;
    private static final int WEAKEN_NUMBER = 3;
    private static final int WEAKEN_NUMBER_ADV = 4;

    private int startVoidNumber;
    private int thumpDamage;
    private int batterDamage;
    private int batterNumber;
    private int destructionDamage;
    private int destructionVoidNumber;
    private int destructionExhaustNumber;
    private int strengthenNumber;
    private int weakenNumber;

    private boolean isFirstMovement;
    private boolean isLastAttackTurn;

    private boolean hasLoveCard;
    private AbstractCard rewardCard;

    static
    {
        MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }

    public KSMOD_TheNothingMonster()
    {
        super(NAME, ID, HP, -84F, -20F, 426, 395, IMAGE_PATH, -20F, -10F);
        this.type = EnemyType.ELITE;
        InitMonsterValue();
        this.damage.add(0, new DamageInfo(this, thumpDamage));
        this.damage.add(1, new DamageInfo(this, batterDamage));
        this.damage.add(2, new DamageInfo(this, destructionDamage));
        this.isFirstMovement = true;
        this.isLastAttackTurn = false;
    }

    private void InitMonsterValue()
    {
        // 伤害
        if (AbstractDungeon.ascensionLevel < 3)
        {
            thumpDamage = THUMP_DAMAGE;
            batterDamage = BATTER_DAMAGE;
            destructionDamage = DESTRUCTION_DAMAGE;
        }
        else
        {
            thumpDamage = THUMP_DAMAGE_ADV;
            batterDamage = BATTER_DAMAGE_ADV;
            destructionDamage = DESTRUCTION_DAMAGE_ADV;
        }
        // 生命
        this.setHp(AbstractDungeon.ascensionLevel < 8 ? HP : HP_ADV);
        // 行动与能力
        if (AbstractDungeon.ascensionLevel < 18)
        {
            startVoidNumber = START_VOID_NUMBER;
            batterNumber = BATTER_NUMBER;
            destructionVoidNumber = DESTRUCTION_VOID_NUMBER;
            destructionExhaustNumber = DESTRUCTION_EXHAUST_NUMBER;
            strengthenNumber = STRENGTHEN_NUMBER;
            weakenNumber = WEAKEN_NUMBER;
        }
        else
        {
            startVoidNumber = START_VOID_NUMBER_ADV;
            batterNumber = BATTER_NUMBER_ADV;
            destructionVoidNumber = DESTRUCTION_VOID_NUMBER_ADV;
            destructionExhaustNumber = DESTRUCTION_EXHAUST_NUMBER_ADV;
            strengthenNumber = STRENGTHEN_NUMBER_ADV;
            weakenNumber = WEAKEN_NUMBER_ADV;
        }
    }

    public void usePreBattleAction()
    {
        AbstractDungeon.getCurrRoom().cannotLose = false;
    }

    public void takeTurn()
    {
        switch (this.nextMove)
        {
            case 0:
                ApplyNothingPower();
                break;
            case 1:
                ThumpAttack();
                break;
            case 2:
                BatterAttack();
                break;
            case 3:
                Destruction();
                break;
            case 4:
                Strengthen();
                break;
            case 5:
                Weakened();
                break;
            default:
                Weakened();
                break;
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    private void ApplyNothingPower()
    {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new KSMOD_NothingPower_Monster(AbstractDungeon.player)));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new VoidCard(), startVoidNumber));
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
    }

    private void ThumpAttack()
    {
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new ViceCrushEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY), 0.5F));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_HEAVY, true));
    }

    private void BatterAttack()
    {
        for (int i = 0; i < batterNumber; i++)
        {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.POISON));
        }
    }

    private void Destruction()
    {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(2), AbstractGameAction.AttackEffect.BLUNT_LIGHT, true));
        PurgeRandomCards(destructionExhaustNumber);
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new VoidCard(), destructionVoidNumber));
    }

    private void Strengthen()
    {
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new HeartBuffEffect(this.hb.cX, this.hb.cY)));
        AbstractDungeon.actionManager.addToBottom(new RemoveAllPowersAction(this, true));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new IntangiblePower(this, 1), 1));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, strengthenNumber), strengthenNumber));
    }

    private void Weakened()
    {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new VulnerablePower(AbstractDungeon.player, weakenNumber, true), weakenNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, weakenNumber, true), weakenNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, weakenNumber, true), weakenNumber));
    }

    protected void getMove(int num)
    {
        if (isFirstMovement)
        {
            this.setMove((byte) 0, Intent.STRONG_DEBUFF);
            isFirstMovement = false;
        }
        else if (!isLastAttackTurn)
        {
            if (num < 50)
            {
                this.setMove((byte) 1, Intent.ATTACK, thumpDamage);
            }
            else
            {
                this.setMove((byte) 2, Intent.ATTACK, batterDamage, batterNumber, true);
            }
            isLastAttackTurn = true;
        }
        else
        {
            if (num < 33)
            {
                this.setMove((byte) 3, Intent.ATTACK_DEBUFF, destructionDamage);
            }
            else if (num < 66)
            {
                this.setMove((byte) 4, Intent.BUFF);
            }
            else
            {
                this.setMove((byte) 5, Intent.DEBUFF);
            }
            isLastAttackTurn = false;
        }
    }

    public void die()
    {
        if (!AbstractDungeon.getCurrRoom().cannotLose)
        {
            for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters)
            {
                if ((!m.isDead) && (!m.isDying))
                {
                    if (hasLoveCard)
                    {
                        AbstractCard card = AbstractDungeon.player.masterDeck.group.stream().filter(c -> c.cardID.equals(SakuraCardTheLove.ID)).findFirst().get();
                        AbstractDungeon.topLevelEffectsQueue.add(new PurgeCardEffect(card));
                        AbstractDungeon.player.masterDeck.removeCard(card);
                        AbstractDungeon.player.masterDeck.refreshHandLayout();
                        AbstractDungeon.topLevelEffectsQueue.add(new ShowCardAndObtainEffect(rewardCard, Settings.WIDTH * 0.5F, Settings.HEIGHT * 0.5F));
                    }
                    else
                    {
                        AbstractDungeon.topLevelEffectsQueue.add(new ShowCardAndObtainEffect(rewardCard, Settings.WIDTH * 0.5F, Settings.HEIGHT * 0.5F));
                    }
                    AbstractDungeon.actionManager.addToTop(new HideHealthBarAction(m));
                    AbstractDungeon.actionManager.addToTop(new SuicideAction(m));
                }
            }
        }
    }

    private void PurgeRandomCards(int purgeAmount)
    {
        int cardsAmount = AbstractDungeon.player.hand.size() + AbstractDungeon.player.drawPile.size() + AbstractDungeon.player.discardPile.size();
        if (cardsAmount == 0 || purgeAmount == 0)
        {
            return;
        }
        purgeAmount = purgeAmount < cardsAmount ? purgeAmount : cardsAmount;
        for (int i = 0; i < purgeAmount; i++)
        {
            float roll = MathUtils.random(0F, cardsAmount);
            CardGroup group;
            AbstractCard card;
            float startX, startY;
            if (roll < AbstractDungeon.player.hand.size())
            {
                group = AbstractDungeon.player.hand;
                card = KSMOD_DataTool.GetRandomListElement(group.group);
                startX = card.current_x;
                startY = card.current_y;
            }
            else if (roll < AbstractDungeon.player.hand.size() + AbstractDungeon.player.drawPile.size())
            {
                group = AbstractDungeon.player.drawPile;
                card = KSMOD_DataTool.GetRandomListElement(group.group);
                startX = 60F * Settings.scale;
                startY = 60F * Settings.scale;
            }
            else
            {
                group = AbstractDungeon.player.discardPile;
                card = KSMOD_DataTool.GetRandomListElement(group.group);
                startX = (Settings.WIDTH - 60F) * Settings.scale;
                startY = 60F * Settings.scale;
            }
            group.removeCard(card);
            group.refreshHandLayout();
            card.setAngle(0);
            if (purgeAmount < 4)
            {
                AbstractDungeon.topLevelEffectsQueue.add(new KSMOD_PurgeCardEffect(card, startX, startY, Settings.WIDTH * (i + 1F) / (purgeAmount + 1F), Settings.HEIGHT * 0.5F));
            }
            else
            {
                AbstractDungeon.topLevelEffectsQueue.add(new KSMOD_PurgeCardEffect(card, startX, startY, MathUtils.random(Settings.WIDTH * 0.1F, Settings.WIDTH * 0.9F), MathUtils.random(Settings.HEIGHT * 0.1F, Settings.HEIGHT * 0.9F)));
            }
        }
    }

    public void SetReward(boolean hasLoveCard, AbstractCard rewardCard)
    {
        this.hasLoveCard = hasLoveCard;
        this.rewardCard = rewardCard;
    }
}
