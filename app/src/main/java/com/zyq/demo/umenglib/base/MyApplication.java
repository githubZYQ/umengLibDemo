package com.zyq.demo.umenglib.base;

import android.app.Application;

import com.umeng.commonsdk.utils.UMUtils;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.api.UPushRegisterCallback;

import cn.nova.umenglibrary.PushHelper;
import cn.nova.umenglibrary.UMengBuilder;

/**
 * @author Zyq
 * @date 2020/11/9　14:09.
 */
public class MyApplication extends Application {
    private static MyApplication myAPP;
    private String mDeviceToken;

    /**
     * Getter for property 'mAPP'.
     *
     * @return Value for property 'mAPP'.
     */
    public static MyApplication getAPP() {
        return myAPP;
    }


    /**
     * Getter for property 'mDeviceToken'.
     */
    public String getDeviceToken() {
        return mDeviceToken;
    }





    @Override
    public void onCreate() {
        super.onCreate();
        myAPP = this;
        // 程序崩溃时触发
        Thread.setDefaultUncaughtExceptionHandler(new OwnUncaughtExceptionHandler());
        //友盟push信息初始化
        PushHelper.preInit(this,appKey,appSecret,appChannel);
        //判断用户是否已经同意隐私政策
        boolean agree = true;
        if(agree){
            //初始化友盟推送、统计
            boolean isMainProcess = UMUtils.isMainProgress(this);
            if (isMainProcess) {
                //启动优化：建议在子线程中执行初始化
                new Thread(() -> initPush()).start();
            } else {
                //若不是主进程（":channel"结尾的进程），直接初始化sdk，不可在子线程中执行
                initPush();
            }
        }
    }
    private final static String appChannel ="默认渠道";
    private final static String appKey ="5fa4e6c71c520d3073a1d6f2";
    private final static String appSecret ="baa0630e64efec1beeb43eed90fbed77";


    /**
     * 友盟push信息初始化
     */
    private void initPush(){
        new UMengBuilder()
                //关闭日志输出
                .setOpenLog(false)
                //配置app信息
                .setAppkey(appKey,appSecret)
                //若和build.gradle中applicationId不一致，需调用此方法设置资源包名
                .setAppResourcePackageName("配置资源包名，和applicationId一致时不需要设置")
                //推送注册回调
                .setRegisterCallback(new UPushRegisterCallback() {
                    @Override
                    public void onSuccess(String deviceToken) {
                        mDeviceToken = deviceToken;
                    }
                    @Override
                    public void onFailure(String s, String s1) {
                    }
                })
                //（可选）开启厂商推送（请参考官方文档去申请配置各平台的id和key）https://developer.umeng.com/docs/67966/detail/98589
                //华为 Push
//                .huaweiPush(true)
//                //vivo Push
//                .vivoPush(true)
//                //小米 Push
//                .setMiId("小米ID","小米Key")
//                //魅族 Push
//                .setMeizuId("魅族ID","魅族Key")
//                //oppo Push
//                .setOPPOKey("oppoKey","oppoSecret")
                //厂商推送初始化结束

                //设置自定义通知栏样式.点击统计等 https://developer.umeng.com/docs/67966/detail/98583
                .setMessageHandler(new UmengMessageHandler())
                //自定义点击后的打开动作 https://developer.umeng.com/docs/67966/detail/98583
                .setNotificationClickHandler(new UmengNotificationClickHandler())

                //最后执行初始化操作
                .build(this);
    }
}
