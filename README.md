# umengLibDemo
**目的：** </br>
快速集成友盟统计和推送（包含厂商推送、离线推送功能）。由于多个公司项目需要集成友盟统计和推送，
每次友盟有新的版本变更需要挨个去维护升级，加之项目需要使用，故抽取友盟统计和推送公共内容和配置抽取为工具库，
方便后续集成和维护升级。
本库适用于已经注册、集成过友盟推进和推送者。
> **Umeng官方文档**：https://developer.umeng.com/sdk/android </br>
> **统计**：https://developer.umeng.com/docs/119267/detail/118584 </br>
> **Push**：https://developer.umeng.com/docs/67966/detail/153908 </br>
> **Push高阶使用**（自定义图标、通知音、通知栏样式、打开动作等）：https://developer.umeng.com/docs/67966/detail/98583 </br>
> **厂商通道**（离线推送、厂商push信息申请注册，包含：小米，华为，OPPO,VIVO,魅族）：https://developer.umeng.com/docs/67966/detail/98589 </br>
## 第一步：配置maven库,并引入库地址
1. 添加mavern库地址和jitpack地址
在主工程build.gradle配置脚本中buildscript和allprojects段中添加【友盟+】SDK新maven仓库地址。
```
maven { url 'https://dl.bintray.com/umsdk/release' }
```
```
buildscript {
    repositories {
        google()
        jcenter()
        maven { url 'https://dl.bintray.com/umsdk/release' }
        maven { url 'https://www.jitpack.io' }
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:3.6.2'
    }
}
allprojects {
    repositories {
        google()
        jcenter()
        maven { url 'https://dl.bintray.com/umsdk/release' }
        maven { url 'https://www.jitpack.io' }
    }
}
```
2. 引用
在自己的app主工程gradle文件中添加
```
dependencies {
	        implementation 'com.github.githubZYQ:umengLibDemo:1.0.0'
	}
```
[![](https://www.jitpack.io/v/githubZYQ/umengLibDemo.svg)](https://www.jitpack.io/#githubZYQ/umengLibDemo)

## 第二步：清单配置
（${applicationId}换为自己主工程的包名）
```
<receiver
android:name="com.taobao.agoo.AgooCommondReceiver"
android:exported="true"
android:process=":channel">
<intent-filter>
<action android:name="${applicationId}.intent.action.COMMAND" />
</intent-filter>
<intent-filter>
<action android:name="android.intent.action.PACKAGE_REMOVED" />
<data android:scheme="package" />
</intent-filter>
</receiver>

<provider
android:name="com.umeng.message.provider.MessageProvider"
android:authorities="${applicationId}.umeng.message"
android:exported="false">
<grant-uri-permission android:pathPattern=".*" />
</provider>
```

## 第三步：Push资源配置（可选，不需要可跳过）
1. 需要自定义默认的logo图片、通知音等可参考官方文档放置资源
https://developer.umeng.com/docs/67966/detail/98583

2. 魅族手机配置系统通知图标（可选，不集成厂商推送魅族push可跳过）
如有需要请在drawable目录下添加一个图标，命名为stat_sys_third_app_notify.png，建议尺寸64px * 64px，图标四周留有透明。若不添加此图标，可能在部分魅族手机上无法弹出通知。

## 第四步：初始化配置
1. Application的onCreate中增加initPush();
创建UMengBuilder去初始化配置信息，
其中厂商推送和自定义通知栏样式、自定义通知栏点击后的打开动作可根据需求配置，
最后build()开始执行初始化；
IUmengRegisterCallback回调会传回注册成功和失败的信息,设备标志deviceToken可在onSuccess中获取。
```
private void initPush(){
    String appKey ="友盟平台注册获取的appKey";
    String appSecret ="友盟平台注册获取的appSecret";
    new UMengBuilder()
          //关闭日志输出
          .setOpenLog(false)
          //配置app信息
          .setAppkey(appKey,appSecret)
          //推送注册回调
           .setRegisterCallback(new IUmengRegisterCallback() {
                @Override
                public void onSuccess(String deviceToken) {
                }
                @Override
                public void onFailure(String s, String s1) {
                }
           })
           //（可选）开启厂商推送（请参考官方文档去申请配置各平台的id和key）https://developer.umeng.com/docs/67966/detail/98589
           //华为 Push
           .huaweiPush(true)
            //vivo Push
            .vivoPush(true)
            //小米 Push
            .setMiId("小米ID","小米Key")
            //魅族 Push
            .setMeizuId("魅族ID","魅族Key")
            //oppo Push
            .setOPPOKey("oppoKey","oppoSecret")
            //厂商推送初始化结束

            //设置自定义通知栏样式.点击统计等 https://developer.umeng.com/docs/67966/detail/98583
            .setMessageHandler(messageHandler)
            //自定义点击后的打开动作 https://developer.umeng.com/docs/67966/detail/98583
            .setNotificationClickHandler(notificationClickHandler)

            //最后执行初始化操作
            .build(application);
 }
```
2.（有推送才需要）注：该方法是【友盟+】Push后台进行日活统计及多维度推送的必调用方法，请务必调用！
在所有的Activity 的onCreate 方法或在应用的BaseActivity的onCreate方法中添加：
```
PushAgent.getInstance(this).onAppStart();
```
## 第五步：混淆配置
如果项目开启打包混淆，需要添加如下混淆代码，如果未开启混淆可忽略跳过。
```
-keep public class [您的应用包名].R$*{
public static final int *;
}
-keep class com.umeng.** {*;}

-keep class com.uc.** {*;}

-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep class com.zui.** {*;}
-keep class com.miui.** {*;}
-keep class com.heytap.** {*;}
-keep class a.** {*;}
-keep class com.vivo.** {*;}

-dontwarn com.umeng.**
-dontwarn com.taobao.**
-dontwarn anet.channel.**
-dontwarn anetwork.channel.**
-dontwarn org.android.**
-dontwarn org.apache.thrift.**
-dontwarn com.xiaomi.**
-dontwarn com.huawei.**
-dontwarn com.meizu.**

-keepattributes *Annotation*

-keep class com.taobao.** {*;}
-keep class org.android.** {*;}
-keep class anet.channel.** {*;}
-keep class com.umeng.** {*;}
-keep class com.xiaomi.** {*;}
-keep class com.huawei.** {*;}
-keep class com.meizu.** {*;}
-keep class org.apache.thrift.** {*;}
-keep class org.android.agoo.* {*;}

-keep class com.alibaba.sdk.android.**{*;}
-keep class com.ut.**{*;}
-keep class com.ta.**{*;}

-keep public class **.R$*{
   public static final int *;
}
```
>此时，已经完成了统计和push的集成了，
可以在友盟产品-友盟push-测试模式，选择测试的应用，添加测试设备，发送测试消息
测试模式：https://developer.umeng.com/docs/67966/detail/153908#h3--1

## 第六步：厂商、离线推送配置(不需要厂商推送可以忽略)
厂商push,可根据集成注册了哪些厂商自由选择（信息申请注册官方指导文档）：https://developer.umeng.com/docs/67966/detail/98589

//魅族厂商通道（不需要可跳过）
 1. 自定义Recevier组件受魅族接入方式限制，必须在包名目录实现一个自定义Recevier，继承自MeizuPushReceiver，例如：
 ```
 public class MeizuTestReceiver extends MeizuPushReceiver {
 }
 ```
 2. 配置系统通知图标
请在drawable目录下添加一个图标，命名为stat_sys_third_app_notify.png，建议尺寸64px * 64px，图标四周留有透明。若不添加此图标，可能在部分魅族手机上无法弹出通知。
```
<!-- 厂商通道清单配置，不需要时可不配置 ${applicationId}更换为自己包名-->
 <!-- 华为push -->
<meta-data
 android:name="com.huawei.hms.client.appid"
android:value="appid=你的华为pushid" />
<!-- vivo push参数声明 -->
<!-- vivo start -->
<meta-data
android:name="com.vivo.push.api_key"
android:value="你的VIVO appkey" />
<meta-data
android:name="com.vivo.push.app_id"
android:value="你的VIVO appID" />
<!-- VIVO end -->
<!--魅族push应用定义消息receiver声明 start -->
<receiver android:name="${applicationId}.MeizuTestReceiver">
<intent-filter>
<!-- 接收push消息 -->
<action android:name="com.meizu.flyme.push.intent.MESSAGE" />
<!-- 接收register消息 -->
<action android:name="com.meizu.flyme.push.intent.REGISTER.FEEDBACK" />
<!-- 接收unregister消息-->
<action android:name="com.meizu.flyme.push.intent.UNREGISTER.FEEDBACK" />
<!-- 兼容低版本Flyme3推送服务配置 -->
<action android:name="com.meizu.c2dm.intent.REGISTRATION" />
<action android:name="com.meizu.c2dm.intent.RECEIVE" />
<category android:name="${applicationId}"></category>
</intent-filter>
</receiver>
<!--魅族push应用定义消息receiver声明 end-->
```
3. 接收离线推送消息：
-第一步项目中新建TestMiPushActivity extends UmengNotifyClickActivity;
-第二步重写onMessage获取MESSAGE_BODY，解析成UmengMessageBody对象，即可获取相关离线信息，之后根据MessageBody信息自行处理
```
@Override
public void onMessage(Intent intent) {
    //此方法必须调用，否则无法统计打开数
    super.onMessage(intent);
    try {
        //body示例
        String body = intent.getStringExtra(AgooConstants.MESSAGE_BODY);
        // UmengMessageBody messageBody 可以通过body解析成UmengMessageBody，完成自定义的一系列动作
        finish();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
```
-第三步，当然是不可少的将它TestMiPushActivity注册到清单文件中
```
<!--离线推送注册接收消息的界面-->
<activity
    android:name=".TestMiPushActivity"
    android:exported="true"
    android:launchMode="singleTask"
    android:theme="@style/AppTheme.NoActionBar"/>
```