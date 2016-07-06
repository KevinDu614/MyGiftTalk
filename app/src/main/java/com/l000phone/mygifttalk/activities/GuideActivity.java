package com.l000phone.mygifttalk.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.BoringLayout;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.l000phone.mygifttalk.R;
import com.l000phone.mygifttalk.utils.DensityUtils;
import com.l000phone.mygifttalk.utils.PreUtils;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends Activity {
    private ViewPager vpGuide;
    private View viewBlackPoint;
    private LinearLayout llPointGroup;
    private int[] mImageIds = {R.mipmap.walkthrough_1, R.mipmap.walkthrough_2, R.mipmap.walkthrough_3
            , R.mipmap.walkthrough_4};
    private List<ImageView> mImageViewList;
    private int pointWidth;
    private Button btnStart;
    private ImageView point;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_guide);
        vpGuide = (ViewPager) findViewById(R.id.vp_guide);
        viewBlackPoint = findViewById(R.id.view_black_point);
        viewBlackPoint.setBackgroundResource(R.drawable.guide_point_black);
        llPointGroup = (LinearLayout) findViewById(R.id.ll_point_group);
        btnStart = (Button) findViewById(R.id.btn_start);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreUtils.setBoolean(GuideActivity.this, "is_user_guide_showed", true);

                Intent intent = new Intent(GuideActivity.this, CrowdActivity.class);
                startActivity(intent);

                finish();
            }
        });

        initViews();

        vpGuide.setAdapter(new MyGuideAdapter());
        vpGuide.setOnPageChangeListener(new MyListener());

    }

    //初始化控件
    private void initViews() {
        //初始化4个引导页
        mImageViewList = new ArrayList<>();
        for (int i = 0; i < mImageIds.length; i++) {
            ImageView image = new ImageView(this);
            image.setScaleType(ImageView.ScaleType.FIT_XY);
            image.setImageResource(mImageIds[i]);
            mImageViewList.add(image);

        }

        //初始化小圆点
        for (int i = 0; i < mImageIds.length; i++) {
            point = new ImageView(this);
            point.setBackgroundResource(R.drawable.guide_point_gray);

            //float density = getResources().getDisplayMetrics().density;

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtils.dp2px(this, 8), DensityUtils.dp2px(this, 8));
            point.setLayoutParams(params);
            if (i > 0) {
                params.leftMargin = DensityUtils.dp2px(this, 10);
            }
            point.setLayoutParams(params);
            llPointGroup.addView(point);

        }

        llPointGroup.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                pointWidth = llPointGroup.getChildAt(1).getLeft() - llPointGroup.getChildAt(0).getLeft();
            }
        });
    }

    private class MyGuideAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mImageIds.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mImageViewList.get(position));
            return mImageViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            container.removeView((View) object);
        }
    }

    private class MyListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            int len = (int) (pointWidth * positionOffset + position * pointWidth);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) viewBlackPoint.getLayoutParams();
            params.leftMargin = len;
            viewBlackPoint.setLayoutParams(params);

        }

        @Override
        public void onPageSelected(int position) {
            if (position == mImageIds.length-1) {
                btnStart.setVisibility(View.VISIBLE);
                llPointGroup.setVisibility(View.GONE);
                viewBlackPoint.setVisibility(View.GONE);
                //point.setVisibility(View.GONE);

            } else {
                llPointGroup.setVisibility(View.VISIBLE);
                //point.setVisibility(View.VISIBLE);
                btnStart.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
