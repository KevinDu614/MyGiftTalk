package com.l000phone.mygifttalk.base.impl;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.l000phone.mygifttalk.R;
import com.l000phone.mygifttalk.base.BaseContentPager;
import com.l000phone.mygifttalk.base.BasePager;

import java.util.ArrayList;
import java.util.List;

/**
 * 分类界面
 */
public class CategoryPager extends BasePager {
    List<BaseContentPager> mPagerList;

    public CategoryPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        tvTitle.setVisibility(View.GONE);
        rgGroup.setVisibility(View.VISIBLE);
        btnScanner.setVisibility(View.GONE);
        rgGroup.check(R.id.rb_strategy);//设置攻略为默认选中项
        //添加攻略和单品页面
        mPagerList = new ArrayList<>();
        mPagerList.add(new StrategyPager(mActivity));
        mPagerList.add(new GiftPager(mActivity));
        categoryViewPager.setAdapter(new CategoryContentPagerAdapter());

    }
    //分类界面的适配器
    private class CategoryContentPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return mPagerList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            BaseContentPager baseContentPager = mPagerList.get(position);
            container.addView(baseContentPager.mRootView);
            return baseContentPager.mRootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
