package KinomotoSakuraMod;

import KinomotoSakuraMod.Characters.KinomotoSakura;
import KinomotoSakuraMod.Patches.CharacterEnum;
import basemod.BaseMod;
import basemod.interfaces.EditCharactersSubscriber;
import basemod.interfaces.ISubscriber;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;

@SpireInitializer
public class KinomotoSakutaMod implements ISubscriber, EditCharactersSubscriber
{
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
}
