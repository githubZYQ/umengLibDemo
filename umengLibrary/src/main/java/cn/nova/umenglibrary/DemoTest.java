package cn.nova.umenglibrary;

import android.app.Application;

import com.umeng.message.IUmengRegisterCallback;

/**
 * @author Zyq
 * @date 2020/9/15　10:59.
 */
public class DemoTest {
    public void testInit(Application application){
        String appKey ="";
        String appSecret ="";
        new UMengBuilder()
                //关闭日志输出
                .setOpenLog(false)
                //配置app信息
                .setAppkey(appKey,appSecret)
                //推送注册回调
                .setRegisterCallback(new IUmengRegisterCallback() {
                    @Override
                    public void onSuccess(String s) {

                    }

                    @Override
                    public void onFailure(String s, String s1) {

                    }
                })
                //（可选）开启厂商推送（请参考官方文档去申请配置各平台的id和key）
                //华为
                .huaweiPush(true)
                //vivo
                .vivoPush(true)
                //小米
                .setMiId("小米ID","小米Key")
                //魅族
                .setMeizuId("魅族ID","魅族Key")
                //oppo
                .setOPPOKey("oppoKey","oppoSecret")

                //最后执行初始化操作
                .build(application);

    }
}
