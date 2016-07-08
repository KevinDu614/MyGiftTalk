package com.l000phone.mygifttalk.base;


import android.app.Activity;
import android.view.View;

/**
 * ViewPager中每个pager显示内容的基类.用来给子控件提供初始化根view
 */
public abstract class BaseContentPager {

    public Activity mActivity;

    public View mRootView;// 根布局对象

    public BaseContentPager(Activity activity) {
        mActivity = activity;
        mRootView = initViews();
        System.out.println("====到了basecontentpager了===");
    }

    /**
     * 初始化界面
     */
    public abstract View initViews();

    /**
     * 初始化数据
     */
    public void initData() {

    }

}
