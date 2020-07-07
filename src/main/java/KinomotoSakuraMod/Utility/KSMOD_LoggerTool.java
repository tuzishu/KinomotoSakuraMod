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
}
