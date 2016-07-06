package com.l000phone.mygifttalk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lidroid.xutils.ViewUtils;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        IllegalStateException illegalStateException;
        ViewUtils.inject(this);
    }
}
