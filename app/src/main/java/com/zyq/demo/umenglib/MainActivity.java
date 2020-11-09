package com.zyq.demo.umenglib;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.zyq.demo.umenglib.base.BaseActivity;
import com.zyq.demo.umenglib.base.MyApplication;
import com.zyq.demo.umenglib.util.NotificationTool;
import com.zyq.demo.umenglib.util.StringUtil;

/**
 * 测试主页
 * @author Zyq
 * @time 2020/11/9 11:48
 */
public class MainActivity extends BaseActivity {

    private TextView tv_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_show = (TextView) findViewById(R.id.tv_show);
        findViewById(R.id.btn_show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //判断当前通知权限（通知权限并不影响token的获取，但是会印象推送消息的送达显示）
                if (!NotificationTool.isNotificationEnabled(MainActivity.this)) {
                    //判断通知权限，否则引导去设置
                    NotificationTool.showDialog(MainActivity.this);
                    return;
                }
                //展示token
                String token = MyApplication.getAPP().getDeviceToken();
                if(TextUtils.isEmpty(token)){
                    Snackbar.make(view, "未获取到设备标识，请重新检查配置", Snackbar.LENGTH_LONG).show();
                }else {
                    tv_show.setText(token);
                }
            }
        });
        findViewById(R.id.tv_show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String token = tv_show.getText().toString();
                if(TextUtils.isEmpty(token)){
                    return;
                }else {
                    StringUtil.textToClip(getApplicationContext(),token);
                    Snackbar.make(view, "已复制到剪切板", Snackbar.LENGTH_LONG).show();
                }
            }
        });
        //判断当前通知权限
        if (!NotificationTool.isNotificationEnabled(this)) {
            //判断通知权限，否则引导去设置
            NotificationTool.showDialog(this);
        }

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }


}
