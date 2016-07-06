package com.l000phone.mygifttalk.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 自定义的ViewPager,
 */
public class NoScrollViewPager extends ViewPager {
    public NoScrollViewPager(Context context) {
        super(context);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //重新viewPager的onTouchEvent,返回false表示什么都不做
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //不让viewPager相应滑动事件
        return false;
    }


    //表示是否拦截事件,false是不拦截,让嵌套在viewPager内的viewPager可以滑动,
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }
}
