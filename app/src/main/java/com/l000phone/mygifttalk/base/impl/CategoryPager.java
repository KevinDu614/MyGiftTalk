package com.l000phone.mygifttalk.base.impl;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

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
        categoryViewPager.setVisibility(View.VISIBLE);
        btnScanner.setVisibility(View.GONE);
        rgGroup.check(R.id.rb_strategy);//设置攻略为默认选中项
        //添加攻略和单品页面
        mPagerList = new ArrayList<>();
        mPagerList.add(new StrategyPager(mActivity));
        mPagerList.add(new GiftPager(mActivity));
        categoryViewPager.setAdapter(new CategoryContentPagerAdapter());
        //给RadioGroup设置选择监听器
        rgGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_category:
                        categoryViewPager.setCurrentItem(0);
                        break;
                    case R.id.rb_gift:
                        categoryViewPager.setCurrentItem(1);
                        break;
                }
            }
        });

        //给ViewPager添加界面改变监听器
        categoryViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        rgGroup.check(R.id.rb_strategy);
                        break;
                    case 1:
                        rgGroup.check(R.id.rb_gift);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

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
