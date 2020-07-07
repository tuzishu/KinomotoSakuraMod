package KinomotoSakuraMod.Utility;

import org.apache.logging.log4j.LogManager;

public class KSMOD_LoggerTool
{
    /**
     * 日志管理器
     */
    public static final org.apache.logging.log4j.Logger Logger = LogManager.getLogger("KSMOD");

    /**
     * 显示函数调用栈
     */
    public static void ShowStacktrace()
    {
        String log = "";
        StackTraceElement[] element = Thread.currentThread().getStackTrace();
        for (int i = 2; i < element.length; i++)
        {
            log = log + "\n    " + (i - 2) + " : " + element[i];
        }
        Logger.info(log);
    }

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
}
