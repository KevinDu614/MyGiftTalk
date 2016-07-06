package com.l000phone.mygifttalk.base;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.l000phone.mygifttalk.R;

/**
 * ViewPager中4个界面pager的基类
 */
public class BasePager{

    public Activity mActivity;
    public View mRootView;// 布局对象

    public TextView tvTitle;// 标题对象

    public FrameLayout flContent;// 内容

    public ImageButton btnScanner;// 扫描二维码btn
    public ImageButton btnSearch;// 搜索按钮
    public ImageButton btnMore;// "我"的界面标题栏中的更多
    public RadioGroup rgGroup;
    public ViewPager categoryViewPager;

    public BasePager(Activity activity) {
        mActivity = activity;
        initViews();
    }

    /**
     * 初始化布局
     */
    public void initViews() {
        mRootView = View.inflate(mActivity, R.layout.base_pager, null);

        tvTitle = (TextView) mRootView.findViewById(R.id.tv_title);
        rgGroup = (RadioGroup) mRootView.findViewById(R.id.category_group);
        flContent = (FrameLayout) mRootView.findViewById(R.id.fl_content);
        btnScanner = (ImageButton) mRootView.findViewById(R.id.btn_scanner);
        btnSearch = (ImageButton) mRootView.findViewById(R.id.btn_search);
        btnMore = (ImageButton) mRootView.findViewById(R.id.btn_more);
        categoryViewPager = (ViewPager) mRootView.findViewById(R.id.ct_view_pager);
        btnScanner.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });
        btnSearch.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });
        btnMore.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });
    }


    /**
     * 初始化数据
     */
    public void initData() {

    }

}