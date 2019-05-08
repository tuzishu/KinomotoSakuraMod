package KinomotoSakuraMod.Utility;

import KinomotoSakuraMod.KinomotoSakuraMod;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModUtility
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

    public static int[] GetDamageList(int damage)
    {
        int size = AbstractDungeon.getMonsters().monsters.size();
        int[] damageList = new int[size];
        for (int i = 0; i < size; i++)
        {
            damageList[0] = damage;
        }
        return damageList;
    }
}
