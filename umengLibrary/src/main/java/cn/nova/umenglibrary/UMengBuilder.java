package cn.nova.umenglibrary;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.taobao.accs.ACCSClient;
import com.taobao.accs.AccsClientConfig;
import com.taobao.agoo.TaobaoRegister;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;

import org.android.agoo.huawei.HuaWeiRegister;
import org.android.agoo.mezu.MeizuRegister;
import org.android.agoo.oppo.OppoRegister;
import org.android.agoo.vivo.VivoRegister;
import org.android.agoo.xiaomi.MiPushRegistar;

/**
 * 友盟配置工具类
 *
 * @author Zyq
 * @date 2020/9/14　17:35.
 */
public class UMengBuilder {
    private Application context;
    /**
     * 是否输出日志
     */
    private boolean openLog = false;
    /**
     * 在友盟注册的appkey
     */
    private String appkey;
    /**
     * 在友盟注册的secretString
     */
    private String secretString;
    /**
     * 取到标志
     */
    private String appChannel = "Umeng";
    /**
     * 初始化友盟推送的结果回调
     */
    private IUmengRegisterCallback registerCallback = null;
    /**
     * 自定义通知栏样式.点击统计等
     */
    private UmengMessageHandler messageHandler;
    /**
     * 自定义点击后的打开动作
     */
    private UmengNotificationClickHandler notificationClickHandler;

    /**
     * 友盟初始化,在Application的onCreate调用
     */
    public void build(Application application) {
        if (application == null) {
            return;
        }
        this.context = application;
        //建议在线程中执行初始化
        new Thread(new Runnable() {
            @Override
            public void run() {
                initPushSDK();
            }
        }).start();
    }

    /**
     * 初始化推送SDK，在用户隐私政策协议同意后，再做初始化（不能将此逻辑放入Splash界面）。
     */
    private void initPushSDK(){
        // 在此处调用基础组件包提供的初始化函数 相应信息可在应用管理 -> 应用信息 中找到 http://message.umeng.com/list/apps
        // 参数一：当前上下文context；
        // 参数二：应用申请的Appkey（需替换）；
        // 参数三：渠道名称；
        // 参数四：设备类型，必须参数，传参数为UMConfigure.DEVICE_TYPE_PHONE则表示手机；传参数为UMConfigure.DEVICE_TYPE_BOX则表示盒子；默认为手机；
        // 参数五：Push推送业务的secret 填充Umeng Message Secret对应信息（需替换）
        UMConfigure.init(context, appkey, appChannel, UMConfigure.DEVICE_TYPE_PHONE, secretString);
        //是否打开日志
        UMConfigure.setLogEnabled(openLog);
        // 选用AUTO页面采集模式
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
        //获取消息推送代理示例
        PushAgent mPushAgent = PushAgent.getInstance(context);

        //以下事故天宇是不过
        if (TextUtils.isEmpty(secretString)) {
            return;
        }
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回deviceToken deviceToken是推送消息的唯一标志
                UmengUtil.mLog("友盟注册成功：deviceToken：-------->  " + deviceToken);
                if (registerCallback != null) {
                    registerCallback.onSuccess(deviceToken);
                }
            }

            @Override
            public void onFailure(String s, String s1) {
                UmengUtil.mLog("友盟注册失败：-------->  " + "s:" + s + ",s1:" + s1);
                if (registerCallback != null) {
                    registerCallback.onFailure(s, s1);
                }
            }
        });
        //自定义消息样式
        if (messageHandler != null) {
            mPushAgent.setMessageHandler(messageHandler);
        }
        //自定义打开动作
        if (notificationClickHandler != null) {
            mPushAgent.setNotificationClickHandler(notificationClickHandler);
        }

        // 初始化厂商通道
        registerDeviceChannel();
    }

    /**
     * 注册设备推送通道（小米、华为等设备的推送）
     */
    private void registerDeviceChannel(){
        //小米通道
        if (!TextUtils.isEmpty(miId) && !TextUtils.isEmpty(miKey)) {
            MiPushRegistar.register(context, miId, miKey);
        }
        //华为通道
        if(huaweiPush){
            HuaWeiRegister.register(context);
        }

        //魅族通道
        if (!TextUtils.isEmpty(meizuId) && !TextUtils.isEmpty(meizuKey)) {
            MeizuRegister.register(context, meizuId, meizuKey);
        }
        //OPPO通道
        if (!TextUtils.isEmpty(oppoKey) && !TextUtils.isEmpty(oppoSecret)) {
            OppoRegister.register(context, oppoKey, oppoSecret);
        }

        //VIVO 通道，注意VIVO通道的初始化参数在minifest中配置
        if(vivoPush){
            VivoRegister.register(context);
        }
    }

    /**
     * OPPO Push id
     */
    private String oppoKey;
    /**
     * OPPO Push secret
     */
    private String oppoSecret;

    /**
     * 设置OPPO push通道
     *
     * @param oppoKey  填写您在OPPO后台APP对应的app key
     * @param oppoSecret 填写您在魅族后台APP对应的app secret
     */
    public UMengBuilder setOPPOKey(String oppoKey, String oppoSecret) {
        this.oppoKey = oppoKey;
        this.oppoSecret = oppoSecret;
        return this;
    }

    /**
     * 魅族Push id
     */
    private String meizuId;
    /**
     * 魅族Push key
     */
    private String meizuKey;

    /**
     * 设置魅族push通道
     *
     * @param mzId  填写您在魅族后台APP对应的app id
     * @param mzKey 填写您在魅族后台APP对应的app key
     */
    public UMengBuilder setMeizuId(String mzId, String mzKey) {
        this.meizuId = mzId;
        this.meizuKey = mzKey;
        return this;
    }

    /**
     * 小米Push id
     */
    private String miId;
    /**
     * 小米Push key
     */
    private String miKey;

    /**
     * 设置小米push通道
     *
     * @param miId  "填写您在小米后台APP对应的xiaomi id"
     * @param miKey 填写您在小米后台APP对应的xiaomi key
     */
    public UMengBuilder setMiId(String miId, String miKey) {
        this.miId = miId;
        this.miKey = miKey;
        return this;
    }

    /**
     * 华为push通道是否开启
     */
    private boolean huaweiPush;
    /**
     * 开启华为通道,注意华为通道的初始化参数在minifest中配置
     * @param openPush
     * @return
     */
    public UMengBuilder huaweiPush(boolean openPush){
        this.huaweiPush = openPush;
        return this;
    }

    /**
     * VIVO push通道是否开启
     */
    private boolean vivoPush;
    /**
     * 开启VIVO通道,注意VIVO通道的初始化参数在minifest中配置
     * @param vivoPush
     * @return
     */
    public UMengBuilder vivoPush(boolean vivoPush){
        this.vivoPush = vivoPush;
        return this;
    }

    /**
     * 是否打印友盟相关日志，建议上线时关闭
     *
     * @param openLog 默认关闭
     */
    public UMengBuilder setOpenLog(boolean openLog) {
        this.openLog = openLog;
        UmengUtil.openLog = openLog;
        return this;
    }

    /**
     * 设置友盟注册信息
     *
     * @param appkey       友盟appkey
     * @param secretString 友盟推送的secretString 无推送可传null
     */
    public UMengBuilder setAppkey(String appkey, String secretString) {
        this.appkey = appkey;
        this.secretString = secretString;
        return this;
    }

    /**
     * 设置渠道号
     *
     * @param appChannel
     */
    public UMengBuilder setAppChannel(String appChannel) {
        this.appChannel = appChannel;
        return this;
    }

    /**
     * (非必须)设置推送注册回调,获取devicetoken
     *
     * @param registerCallback
     */
    public UMengBuilder setRegisterCallback(IUmengRegisterCallback registerCallback) {
        this.registerCallback = registerCallback;
        return this;
    }

    /**
     * 是否开启检测
     */
    private boolean openPushCheck = false;

    /**
     * 为了便于开发者更好的集成配置文件，我们提供了对于AndroidManifest配置文件的检查工具，可以自行检查开发者的配置问题。SDK默认是不检查集成配置文件的，在线上版本请关掉该配置检查，或者去掉这行检查代码（sdk默认是不做该项检查的）
     * @param openPushCheck
     * @return
     */
    public UMengBuilder setPushCheck(boolean openPushCheck){
        this.openPushCheck = openPushCheck;
        return this;
    }

    /**
     * 设置自定义通知栏样式.点击统计等
     * @param messageHandler
     */
    public UMengBuilder setMessageHandler(UmengMessageHandler messageHandler) {
        this.messageHandler = messageHandler;
        return this;
    }

    /**
     * 自定义点击后的打开动作
     * @param notificationClickHandler
     * @return
     */
    public UMengBuilder setNotificationClickHandler(UmengNotificationClickHandler notificationClickHandler) {
        this.notificationClickHandler = notificationClickHandler;
        return this;
    }

    /**
     *
     * @param context
     * @param appKey
     * @param appSecret
     * @param appChannel
     */
    public static void preInit(Context context,String appKey,String appSecret,String appChannel) {
        try {
            //解决推送消息显示乱码的问题
            AccsClientConfig.Builder builder = new AccsClientConfig.Builder();
            builder.setAppKey(appKey);
            builder.setAppSecret(appSecret);
            builder.setTag(AccsClientConfig.DEFAULT_CONFIGTAG);
            ACCSClient.init(context, builder.build());
            TaobaoRegister.setAccsConfigTag(context, AccsClientConfig.DEFAULT_CONFIGTAG);
        } catch (Exception e) {
            e.printStackTrace();
        }
        UMConfigure.preInit(context, appKey, appChannel);
    }

}
