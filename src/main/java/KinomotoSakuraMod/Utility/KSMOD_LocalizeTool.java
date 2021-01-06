package KinomotoSakuraMod.Utility;

import basemod.BaseMod;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.core.Settings;

import java.nio.charset.StandardCharsets;

public class KSMOD_LocalizeTool
{
    private static String localizationPath = null;

    /**
     * 获取本地化文本的文件夹路径
     *
     * @return 文件夹路径
     */
    public static String GetFolderPath()
    {
        if (localizationPath == null)
        {
            localizationPath = "localization/";
            switch (Settings.language)
            {
                case ZHS:
                    KSMOD_LoggerTool.Logger.info("language == zhs");
                    localizationPath = localizationPath + "zhs/";
                    break;
                case JPN:
                    KSMOD_LoggerTool.Logger.info("language == jpn");
                    localizationPath = localizationPath + "jpn/";
                    break;
                default:
                    KSMOD_LoggerTool.Logger.info("language == eng");
                    localizationPath = localizationPath + "eng/";
                    break;
            }
        }
        return localizationPath;
    }

    /**
     * 载入本地化文本
     *
     * @param fileName  文件名
     * @param classType 文本类型
     */
    public static void LoadStrings(String fileName, Class<?> classType)
    {
        LoadStrings(GetFolderPath(), fileName, classType);
    }

    /**
     * 载入本地化文本
     *
     * @param folderPath 文件夹路径
     * @param fileName   文件名
     * @param classType  文本类型
     */
    public static void LoadStrings(String folderPath, String fileName, Class<?> classType)
    {
        String filePath = folderPath + fileName;
        String json = Gdx.files.internal(filePath).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(classType, json);
    }
}
