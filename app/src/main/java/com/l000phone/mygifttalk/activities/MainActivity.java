package com.l000phone.mygifttalk.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.l000phone.mygifttalk.R;
import com.l000phone.mygifttalk.fragment.ContentFragment;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        //给主布局文件添加fragment
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();// 开启事务
        transaction.replace(R.id.fl_fragment, new ContentFragment());

        transaction.commit();// 提交事务
    }
}

