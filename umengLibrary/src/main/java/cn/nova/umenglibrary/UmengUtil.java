package cn.nova.umenglibrary;

import android.util.Log;

/**
 * @author Zyq
 * @date 2020/9/15　11:01.
 */
public class UmengUtil {
    private static final String TAG = "umeng";
    /**
     * 是否输出日志
     */
    public static boolean openLog = false;
    /**
     * 打印日志
     *
     * @param message
     */
    public static void mLog(String message) {
        if (openLog) {
            Log.e(TAG, message);
        }
    }
}
