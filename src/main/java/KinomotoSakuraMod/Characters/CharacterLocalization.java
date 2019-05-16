package KinomotoSakuraMod.Characters;

import com.megacrit.cardcrawl.core.Settings.GameLanguage;

import java.util.HashMap;

public class CharacterLocalization
{
    public static final HashMap<GameLanguage, KinomotoSakuraLocalizationData> KINOMOTO_SAKURA_MAP = new HashMap<GameLanguage, KinomotoSakuraLocalizationData>()
    {
        {
            put(GameLanguage.ZHS, new KinomotoSakuraLocalizationData("木之本樱", "魔卡少女", "“一定没问题的！”（模组处于早期测试阶段，有建议可以私聊 兔子舒。）"));
        }
    };
}