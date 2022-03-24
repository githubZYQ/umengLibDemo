package com.zyq.demo.umenglib.base;

import android.os.Bundle;

import com.umeng.message.PushAgent;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Acticity基类
 * @author Zyq
 * @time 2020/11/9 11:47
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean hasAgreementAgreed = true;
        if(hasAgreementAgreed){
            PushAgent.getInstance(this).onAppStart();
        }
    }
}
