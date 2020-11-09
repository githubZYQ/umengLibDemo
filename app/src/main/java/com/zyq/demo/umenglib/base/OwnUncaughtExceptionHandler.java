package com.zyq.demo.umenglib.base;

import android.util.Log;

/**
 * 捕获UncaughtException，打印错误信息，方便开发调试
 * @author Zyq
 * @date 2019/5/14　15:12.
 */
public class OwnUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        StackTraceElement[] elements = ex.getStackTrace();
        StringBuilder reason = new StringBuilder(ex.toString());
        if (elements != null && elements.length > 0) {
            for (StackTraceElement element : elements) {
                reason.append("\n");
                reason.append(element.toString());
            }
        }
        Log.e("zyq", reason.toString());
        android.os.Process.killProcess(android.os.Process.myPid());
        //下面为调试用的代码，发布时可注释
//        Writer info = new StringWriter();
//        PrintWriter printWriter = new PrintWriter(info);
//        ex.printStackTrace(printWriter);
//        Throwable cause = ex.getCause();
//        while (cause != null) {
//            cause.printStackTrace(printWriter);
//            cause = cause.getCause();
//        }
//        printWriter.flush();
//        printWriter.close();
//        String result = info.toString();
//        Logger.i("sss", result);
//        android.os.Process.killProcess(android.os.Process.myPid());
    }
};
