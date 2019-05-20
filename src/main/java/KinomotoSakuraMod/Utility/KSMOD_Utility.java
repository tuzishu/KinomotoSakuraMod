package KinomotoSakuraMod.Utility;

import KinomotoSakuraMod.KinomotoSakuraMod;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.sun.org.apache.xerces.internal.xs.datatypes.ObjectList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class KSMOD_Utility
{
    /**
     * 日志管理器
     */
    public static final Logger Logger = LogManager.getLogger(KinomotoSakuraMod.class.getName());

    /**
     * 获取该语句所在的类名
     *
     * @return String 该语句所在的类名
     */
    public static String GetClassName()
    {
        StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
        StackTraceElement e = stacktrace[2];
        return e.getClassName();
    }

    /**
     * 获取该语句所在的方法名
     *
     * @return String 该语句所在的方法名
     */
    public static String GetMethodName()
    {
        StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
        StackTraceElement e = stacktrace[2];
        return e.getMethodName();
    }

    /**
     * 获取该语句所在的文件名
     *
     * @return String 该语句所在的文件名
     */
    public static String GetFileName()
    {
        StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
        StackTraceElement e = stacktrace[2];
        return e.getFileName();
    }

    /**
     * 获取该语句执行的代码行数
     *
     * @return String 该语句执行的代码行数
     */
    public static int GetLineNumber()
    {
        StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
        StackTraceElement e = stacktrace[2];
        return e.getLineNumber();
    }

    /**
     * 通过输入一个伤害值，来返回一个用于DamageAllEnemy的伤害列表
     *
     * @param damage int 伤害值
     * @return int[] 伤害列表
     */
    public static int[] GetDamageList(int damage)
    {
        int size = AbstractDungeon.getMonsters().monsters.size();
        int[] damageList = new int[size];
        for (int i = 0; i < size; i++)
        {
            damageList[i] = damage;
        }
        return damageList;
    }

    private static HashMap<Object, HashMap<Class, HashMap<String, Field>>> fieldMap = new HashMap<Object, HashMap<Class, HashMap<String, Field>>>();

    /**
     * 通过反射的方法获取实例包括基类中的变量
     *
     * @param obj         实例
     * @param fieldName   变量名
     * @param targetClass 目标类型
     * @return 目标变量
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static Field GetFieldByReflect(Object obj, Class targetClass, String fieldName) throws NoSuchFieldException, IllegalAccessException
    {
        HashMap<Class, HashMap<String, Field>> classMap;
        HashMap<String, Field> nameMap;

        if (!fieldMap.containsKey(obj))
        {
            fieldMap.put(obj, new HashMap<>());
        }
        classMap = fieldMap.get(obj);

        if (!classMap.containsKey(targetClass))
        {
            classMap.put(targetClass, new HashMap<>());
        }
        nameMap = classMap.get(targetClass);

        if (!nameMap.containsKey(fieldName))
        {
            Class cls = obj.getClass();
            while (cls.getName() != targetClass.getName())
            {
                cls = cls.getSuperclass();
            }
            Field field = cls.getDeclaredField(fieldName);
            field.setAccessible(true);
            nameMap.put(fieldName, field);
            return field;
        }
        else
        {
            return nameMap.get(fieldName);
        }
    }

    private static HashMap<Object, HashMap<Class, HashMap<String, HashMap<Type[], Method>>>> methodMap = new HashMap<Object, HashMap<Class, HashMap<String, HashMap<Type[], Method>>>>();

    /**
     * 通过反射的方法获取实例包括基类中的方法
     *
     * @param obj            实例
     * @param methodName     方法名
     * @param parameterTypes 方法参数列表
     * @return 目标函数
     * @throws NoSuchMethodException
     */
    public static Method GetMethodByReflect(Object obj, Class targetClass, String methodName, Class<?>... parameterTypes) throws NoSuchMethodException
    {
        // HashMap<Class, HashMap<String, HashMap<Type[], Method>>> classMap;
        // HashMap<String, HashMap<Type[], Method>> nameMap;
        // HashMap<Type[], Method> paramMap;
        //
        // if (!methodMap.containsKey(obj))
        // {
        //     methodMap.put(obj, new HashMap<>());
        // }
        // classMap = methodMap.get(obj);
        //
        // if (!classMap.containsKey(targetClass))
        // {
        //     classMap.put(targetClass, new HashMap<>());
        // }
        // nameMap = classMap.get(targetClass);
        //
        // if (!nameMap.containsKey(methodName))
        // {
        //     paramMap = new HashMap<>();
        //     nameMap.put(methodName, paramMap);
        //     Class cls = obj.getClass();
        //     while (cls != targetClass)
        //     {
        //         cls = cls.getSuperclass();
        //     }
        //     Method[] methods = cls.getMethods();
        //     for (Method method: methods)
        //     {
        //         method.setAccessible(true);
        //         paramMap.put(method.getGenericParameterTypes(), method);
        //         Logger.info("Put Method Map => Object: " + obj + ", Class: " + targetClass + ", Method: " + methodName + ", Param: " + parameterTypes);
        //     }
        // }
        //
        // paramMap = nameMap.get(methodName);
        // return paramMap.get(parameterTypes);

        // if (!paramMap.containsKey(parameterTypes))
        // {
            Class cls = obj.getClass();
            while (cls != targetClass)
            {
                cls = cls.getSuperclass();
            }
            Method method = cls.getDeclaredMethod(methodName, parameterTypes);
            method.setAccessible(true);
            // paramMap.put(parameterTypes, method);
            // Logger.info("Put Method Map => Object: " + obj + ", Class: " + targetClass + ", Method: " + methodName + ", Param: " + parameterTypes);
            return method;
        // }
        // else
        // {
        //     return paramMap.get(parameterTypes);
        // }
    }
}
