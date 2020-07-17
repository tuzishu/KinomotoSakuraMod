package KinomotoSakuraMod.Utility;

import com.megacrit.cardcrawl.core.Settings;

public class KSMOD_LocalizeTool
{
    /**
     * 获取本地化关键字路径
     *
     * @return
     */
    public static String GetKeywordFolderPath()
    {
        String path = "localization/";
        switch (Settings.language)
        {
            case ZHS:
                KSMOD_LoggerTool.Logger.info("language == zhs");
                path += "zhs/";
                break;
            default:
                KSMOD_LoggerTool.Logger.info("language == eng");
                path += "eng/";
                break;
        }
        return path;
    }

    /**
     * 获取本地化库洛牌Library名
     *
     * @return
     */
    public static String GetLibraryClowCardName()
    {
        String name;
        switch (Settings.language)
        {
            case ZHS:
                name = "库洛牌";
                break;
            default:
                name = "Clow Card";
                break;
        }
        return name;
    }

    /**
     * 获取本地化小樱牌Library名
     *
     * @return
     */
    public static String GetLibrarySakuraCardName()
    {
        String name;
        switch (Settings.language)
        {
            case ZHS:
                name = "小樱牌";
                break;
            default:
                name = "Sakura Card";
                break;
        }
        return name;
    }

    /**
     * 获取本地化符咒Library名
     *
     * @return
     */
    public static String GetLibrarySpellName()
    {
        String name;
        switch (Settings.language)
        {
            case ZHS:
                name = "符咒";
                break;
            default:
                name = "Spell";
                break;
        }
        return name;
    }
}
