package com.zyq.demo.umenglib;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.umeng.message.UmengNotifyClickActivity;

import org.android.agoo.common.AgooConstants;

/**
 * 接收离线推送消息
 * @author Zyq
 * @date 2020/11/9　14:40.
 */
public class TestMiPushActivity extends UmengNotifyClickActivity {

    private TextView tv_body;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_mipush);
        tv_body = (TextView) findViewById(R.id.tv_body);
    }

    @Override
    public void onMessage(Intent intent) {
        //此方法必须调用，否则无法统计打开数
        super.onMessage(intent);
        try {
            //body示例
            String body = intent.getStringExtra(AgooConstants.MESSAGE_BODY);
            if(!TextUtils.isEmpty(body)){
                tv_body.setText(body);
            }
            // UmengMessageBody messageBody 可以通过body解析成UmengMessageBody，完成自定义的一系列动作
//            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
