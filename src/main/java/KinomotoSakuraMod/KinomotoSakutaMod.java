package KinomotoSakuraMod;

import KinomotoSakuraMod.Cards.ClowCardTheSword;
import KinomotoSakuraMod.Characters.KinomotoSakura;
import KinomotoSakuraMod.Patches.CharacterEnum;
import basemod.BaseMod;
import basemod.interfaces.EditCardsSubscriber;
import basemod.interfaces.EditCharactersSubscriber;
import basemod.interfaces.ISubscriber;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SpireInitializer
public class KinomotoSakutaMod implements ISubscriber, EditCharactersSubscriber, EditCardsSubscriber
{
    public static final Logger logger = LogManager.getLogger(KinomotoSakutaMod.class.getName());
    private static final String CHAR_SELECT_BUTTON_PATH = "img/charSelect/MarisaButton.png";
    private static final String CHAR_PORTRAIT_PATH = "img/charSelect/marisaPortrait.jpg";

    public KinomotoSakutaMod()
    {
        BaseMod.subscribe(this);
    }

    public static void initialize()
    {
        new KinomotoSakutaMod();
    }

    public void receiveEditCharacters()
    {
        BaseMod.addCharacter(new KinomotoSakura("KinomotoSakura"), this.CHAR_SELECT_BUTTON_PATH, this.CHAR_PORTRAIT_PATH, CharacterEnum.KINOMOTOSAKURA);
    }

    public void receiveEditCards()
    {
        KinomotoSakutaMod.logger.info("begin editting ClowCards");

        BaseMod.addCard(new ClowCardTheSword());
        UnlockTracker.unlockCard("ClowCardTheSword");

        KinomotoSakutaMod.logger.info("done editting ClowCards");
    }
}
