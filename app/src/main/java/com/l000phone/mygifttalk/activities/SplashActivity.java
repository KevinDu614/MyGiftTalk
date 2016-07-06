package com.l000phone.mygifttalk.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import com.l000phone.mygifttalk.R;
import com.l000phone.mygifttalk.utils.PreUtils;


public class SplashActivity extends Activity {

    private RelativeLayout rlSplah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        rlSplah = (RelativeLayout) findViewById(R.id.rl_splah);

        startAnim();
    }

    private void startAnim() {
        AnimationSet set = new AnimationSet(false);
        ScaleAnimation scale = new ScaleAnimation(0, 1f, 0, 1f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scale.setDuration(1000);
        scale.setFillAfter(true);

        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(1000);
        alphaAnimation.setFillAfter(true);

        set.addAnimation(scale);
        set.addAnimation(alphaAnimation);

        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                jumpNextPager();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        rlSplah.startAnimation(set);
    }

    private void jumpNextPager() {
        boolean userGuide = PreUtils.getBoolean(SplashActivity.this, "is_user_guide_showed", false);
        boolean userGenderInfo = PreUtils.getBooleanUserInfo(SplashActivity.this, "userGenderInfo", false);
        if (userGuide && userGenderInfo) {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
        } else if (userGuide && !userGenderInfo) {
            Intent intent = new Intent(SplashActivity.this, CrowdActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(SplashActivity.this, GuideActivity.class);
            startActivity(intent);
        }
        finish();
    }
}
