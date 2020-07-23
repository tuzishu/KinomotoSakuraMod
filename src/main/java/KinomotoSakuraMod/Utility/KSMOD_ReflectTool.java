package KinomotoSakuraMod.Utility;

import com.megacrit.cardcrawl.cards.CardGroup;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class KSMOD_ReflectTool
{
    private static HashMap<String, Field> fieldMap = new HashMap<>();

    /**
     * 通过反射的方法获取实例包括基类中的变量
     *
     * @param targetClass 目标变量所在类，一般为当前类或其父类
     * @param fieldName   变量名
     * @return 目标变量
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static Field GetFieldByReflect(Class targetClass, String fieldName) throws NoSuchFieldException, IllegalAccessException
    {
        String key = targetClass.getName() + "_" + fieldName;
        Field field = null;
        if (!fieldMap.containsKey(key))
        {
            field = targetClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            fieldMap.put(key, field);
        }
        else
        {
            field = fieldMap.get(key);
        }
        return field;
    }

    private static HashMap<String, Method> methodMap = new HashMap<>();

    /**
     * 通过反射的方法获取实例包括基类中的方法
     *
     * @param targetClass 目标函数所在类，一般为当前类或其父类
     * @param methodName  方法名
     * @param paramTypes  方法参数列表
     * @return 目标函数
     * @throws NoSuchMethodException
     */
    public static Method GetMethodByReflect(Class targetClass, String methodName, Class<?>... paramTypes) throws NoSuchMethodException
    {
        String key = targetClass.getName() + "_" + methodName;
        for (Class cls : paramTypes)
        {
            key += "_" + cls.getName();
        }

        Method method = null;
        if (!methodMap.containsKey(key))
        {
            method = targetClass.getDeclaredMethod(methodName, paramTypes);
            method.setAccessible(true);
            methodMap.put(key, method);
        }
        else
        {
            method = methodMap.get(key);
        }
        return method;
    }

    public static ArrayList<String> copyingFakeList = new ArrayList<>(Arrays.asList( //
            "preBattlePrep",//
            "loadPlayerSave",//
            "ExhaustPileViewScreen",//
            "SingleCardViewPopup"//
    ));
//    public static ArrayList<String> copyingRealList = new ArrayList<>(Arrays.asList( //
//            "Duplicator",// 祭坛复制事件
//            "DollysMirror"// 多利之镜遗物
//    ));
    /**
     * 是否正在由拷贝卡牌动作调用makecopy函数
     *
     * @return 是否为真卡牌复制（如：双持、多利之镜、复制祭坛的复制效果）
     */
    public static boolean IsReallyCopyingCard()
    {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        for (int i = 0; i < elements.length; i++)
        {
            // Logger.info(i+": "+elements[i]);
            for (String strR : copyingFakeList)
            {
                if (elements[i].toString().contains(strR))
                {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 是否由奖励和事件调用makecopy函数
     *
     * @return 是否为奖励和事件的卡牌获得
     */
    public static boolean IsCopyingCardFromRewardAndEvent()
    {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        for (int i = 0; i < elements.length; i++)
        {
            // Logger.info(i+": "+elements[i]);
            if (elements[i].toString().contains("Reward") || elements[i].toString().contains("event"))
            {
                return true;
            }
        }
        return false;
    }
}
