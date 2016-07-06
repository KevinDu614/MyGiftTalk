package com.l000phone.mygifttalk.fragment;


import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.l000phone.mygifttalk.R;
import com.l000phone.mygifttalk.base.BasePager;
import com.l000phone.mygifttalk.base.impl.CategoryPager;
import com.l000phone.mygifttalk.base.impl.HomePager;
import com.l000phone.mygifttalk.base.impl.MySelfPager;
import com.l000phone.mygifttalk.base.impl.SinglePager;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

/**
 * 主Activity上挂载的fragment,如果有侧边栏还可以继续挂载fragment.
 */
public class ContentFragment extends BaseFragment {
    //给RadioGroup注解
    @ViewInject(R.id.rg_group)
    private RadioGroup rgGroup;

    //给ViewPager注解id
    @ViewInject(R.id.vp_pager)
    private ViewPager mViewPager;

    private ArrayList<BasePager> mPagerList;

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.fragment_content, null);
        // rgGroup = (RadioGroup) view.findViewById(R.id.rg_group);
        ViewUtils.inject(this, view); // 注入view和事件
        return view;
    }

    @Override
    public void initData() {
        Log.e("====","==ContentFragment==");

        rgGroup.check(R.id.rb_home);// 默认勾选首页

        // 初始化4个子页面
        mPagerList = new ArrayList<BasePager>();

        mPagerList.add(new HomePager(mActivity));
        mPagerList.add(new SinglePager(mActivity));
        mPagerList.add(new CategoryPager(mActivity));

        mPagerList.add(new MySelfPager(mActivity));


        mViewPager.setAdapter(new ContentAdapter());

        // 监听RadioGroup的选择事件
        rgGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home:
                        mViewPager.setCurrentItem(0, false);// 去掉切换页面的动画
                        break;
                    case R.id.rb_single:
                        mViewPager.setCurrentItem(1, false);// 设置当前页面
                        break;
                    case R.id.rb_category:
                        System.out.println("点击了第三个分类页面");
                        // mPagerList.get(2).initData();
                        mViewPager.setCurrentItem(2, false);// 设置当前页面
                        break;
                    case R.id.rb_me:
                        mViewPager.setCurrentItem(3, false);// 设置当前页面
                        break;

                    default:
                        break;
                }
            }
        });

        mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                mPagerList.get(arg0).initData();// 获取当前被选中的页面, 初始化该页面数据
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });

        mPagerList.get(0).initData();// 初始化首页数据
    }

    class ContentAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mPagerList.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            BasePager pager = mPagerList.get(position);
            //给ViewPager容器添加view
            container.addView(pager.mRootView);

            return pager.mRootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }
}