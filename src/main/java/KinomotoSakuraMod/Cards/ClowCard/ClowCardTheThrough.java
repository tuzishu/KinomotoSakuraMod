package KinomotoSakuraMod.Cards.ClowCard;

import KinomotoSakuraMod.Cards.KSMOD_AbstractMagicCard;
import KinomotoSakuraMod.Patches.KSMOD_CustomCardColor;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;

public class ClowCardTheThrough extends KSMOD_AbstractMagicCard
{
    public static final String ID = "ClowCardTheThrough";
    private static final String NAME;
    private static final String DESCRIPTION;
    private static final String IMAGE_PATH = "img/cards/clowcard/the_through.png";
    private static final int COST = 1;
    private static final CardType CARD_TYPE = CardType.ATTACK;
    private static final CardColor CARD_COLOR = KSMOD_CustomCardColor.CLOWCARD_COLOR;
    private static final CardRarity CARD_RARITY = CardRarity.RARE;
    private static final CardTarget CARD_TARGET = CardTarget.ENEMY;
    private static final int BASE_MAGIC_NUMBER = 1;
    private static final int UPGRADE_MAGIC_NUMBER = 1;
    private static final float CLEAVE_DURATION = 0.1F;
    private static final String SOUND_KEY = "ATTACK_DAGGER_";

    static
    {
        CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
    }

    public ClowCardTheThrough()
    {
        super(ID, NAME, IMAGE_PATH, COST, DESCRIPTION, CARD_TYPE, CARD_COLOR, CARD_RARITY, CARD_TARGET);
        this.setBaseMagicNumber(BASE_MAGIC_NUMBER);
    }

    @Override
    public void upgrade()
    {
        if (!this.upgraded)
        {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_MAGIC_NUMBER);
        }
    }

    @Override
    public KSMOD_AbstractMagicCard makeCopy()
    {
        return new ClowCardTheThrough();
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        int count = 0;
        if (monster.hasPower(EarthyElementPower.POWER_ID))
        {
            int amount = monster.getPower(EarthyElementPower.POWER_ID).amount;
            count += amount;
            EarthyElementPower.TryActiveEarthyElement(monster, amount, true);
        }
        if (monster.hasPower(WateryElementPower.POWER_ID))
        {
            int amount = monster.getPower(WateryElementPower.POWER_ID).amount;
            count += amount;
            WateryElementPower.TryActiveWateryElement(monster, amount, true);
        }
        if (monster.hasPower(FireyElementPower.POWER_ID))
        {
            int amount = monster.getPower(FireyElementPower.POWER_ID).amount;
            count += amount;
            FireyElementPower.TryActiveFireyElement(monster, amount, true);
        }
        if (monster.hasPower(WindyElementPower.POWER_ID))
        {
            int amount = monster.getPower(WindyElementPower.POWER_ID).amount;
            count += amount;
            WindyElementPower.TryActiveWindyElement(monster, amount, true);
        }
        if (monster.hasPower(LightElementPower.POWER_ID))
        {
            int amount = monster.getPower(LightElementPower.POWER_ID).amount;
            count += amount;
            LightElementPower.TryActiveLightElement(monster, amount, true);
        }
        if (monster.hasPower(DarkElementPower.POWER_ID))
        {
            int amount = monster.getPower(DarkElementPower.POWER_ID).amount;
            count += amount;
            DarkElementPower.TryActiveDarkElement(monster, amount, true);
        }

        CardCrawlGame.sound.play(SOUND_KEY + MathUtils.random(1, 6));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(player, new CleaveEffect(), CLEAVE_DURATION));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, new DamageInfo(player, count * this.magicNumber, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }
}
