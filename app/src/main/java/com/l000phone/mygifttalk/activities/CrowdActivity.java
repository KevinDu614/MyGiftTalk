package com.l000phone.mygifttalk.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.l000phone.mygifttalk.R;
import com.l000phone.mygifttalk.utils.PreUtils;

public class CrowdActivity extends Activity implements View.OnClickListener {

    private TextView tvTitle;
    private Button btnBack;
    private LinearLayout llSelectGender;
    private LinearLayout llSelectBoy;
    private LinearLayout llSelectGirl;
    private RelativeLayout rlSelectIdentity;
    private Button midStu;
    private Button graStu;
    private Button uniStu;
    private Button freshWorker;
    private Button oldWorker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crowd);

        initViews();
    }

    private void initViews() {
        tvTitle = (TextView) findViewById(R.id.tv_slect_title);
        btnBack = (Button) findViewById(R.id.btn_back);
        llSelectGender = (LinearLayout) findViewById(R.id.ll_select_gender);
        llSelectBoy = (LinearLayout) findViewById(R.id.ll_select_boy);
        llSelectGirl = (LinearLayout) findViewById(R.id.ll_select_girl);
        rlSelectIdentity = (RelativeLayout) findViewById(R.id.rl_select_identity);
        midStu = (Button) findViewById(R.id.chuzhong);
        graStu = (Button) findViewById(R.id.gaozhong);
        uniStu = (Button) findViewById(R.id.daxue);
        freshWorker = (Button) findViewById(R.id.fresh);
        oldWorker = (Button) findViewById(R.id.old_work);

        llSelectBoy.setOnClickListener(this);
        llSelectGirl.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        midStu.setOnClickListener(this);
        graStu.setOnClickListener(this);
        uniStu.setOnClickListener(this);
        freshWorker.setOnClickListener(this);
        oldWorker.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_select_boy:
                PreUtils.setUserGender(CrowdActivity.this, "userGender", "1");
                llSelectGender.setVisibility(View.GONE);
                rlSelectIdentity.setVisibility(View.VISIBLE);
                btnBack.setVisibility(View.VISIBLE);
                tvTitle.setText("请选择您的职业");
                break;
            case R.id.ll_select_girl:
                PreUtils.setUserGender(CrowdActivity.this, "userGender", "2");
                llSelectGender.setVisibility(View.GONE);
                rlSelectIdentity.setVisibility(View.VISIBLE);
                btnBack.setVisibility(View.VISIBLE);
                tvTitle.setText("请选择您的职业");
                break;
            case R.id.btn_back:
                llSelectGender.setVisibility(View.VISIBLE);
                rlSelectIdentity.setVisibility(View.GONE);
                btnBack.setVisibility(View.INVISIBLE);
                tvTitle.setText("请选择您的性别");
                break;
            case R.id.chuzhong:
                PreUtils.setUserIdentity(CrowdActivity.this, "userIdentity", "1");
                startHomeAct();
                break;
            case R.id.gaozhong:
                PreUtils.setUserIdentity(CrowdActivity.this, "userIdentity", "2");
                startHomeAct();
                break;
            case R.id.daxue:
                PreUtils.setUserIdentity(CrowdActivity.this, "userIdentity", "3");
                startHomeAct();
                break;
            case R.id.fresh:
                PreUtils.setUserIdentity(CrowdActivity.this, "userIdentity", "4");
                startHomeAct();
                break;
            case R.id.old_work:
                PreUtils.setUserIdentity(CrowdActivity.this, "userIdentity", "5");
                startHomeAct();
                break;
        }


    }

    //开启主页面
    private void startHomeAct() {
        PreUtils.setBooleanUserInfo(CrowdActivity.this, "userGenderInfo", true);
        Intent intent = new Intent(CrowdActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}