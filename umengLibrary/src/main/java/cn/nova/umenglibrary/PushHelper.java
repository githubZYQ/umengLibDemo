package cn.nova.umenglibrary;

import android.content.Context;

import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.PushAgent;

/**
 * PushSDK集成帮助类
 */
public class PushHelper {

    private static final String TAG = "PushHelper";

    /**
     * 初始化之前配置，延迟初始化方案
     * @param context
     * @param APP_KEY 应用申请的Appkey
     * @param MESSAGE_SECRET  应用申请的UmengMessageSecret
     * @param CHANNEL 渠道名称
     */
    public static void preInit(Context context,String APP_KEY,String MESSAGE_SECRET,String CHANNEL) {
        PushAgent.setup(context, APP_KEY, MESSAGE_SECRET);
        UMConfigure.preInit(context, APP_KEY, CHANNEL);
    }

}
