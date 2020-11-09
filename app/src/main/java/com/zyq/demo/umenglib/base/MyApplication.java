package com.zyq.demo.umenglib.base;

import android.app.Application;

import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;

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
        initPush();
    }

    /**
     * 友盟push信息初始化
     */
    private void initPush(){
        String appKey ="5fa4e6c71c520d3073a1d6f2";
        String appSecret ="baa0630e64efec1beeb43eed90fbed77";
        new UMengBuilder()
                //关闭日志输出
                .setOpenLog(false)
                //配置app信息
                .setAppkey(appKey,appSecret)
                //推送注册回调
                .setRegisterCallback(new IUmengRegisterCallback() {
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
