package com.xuyong.circleprogress;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private ArcProgressBar mArcProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mArcProgressBar = (ArcProgressBar) findViewById(R.id.arcProgressBar);
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 50);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mArcProgressBar.setProgress((int) animation.getAnimatedValue());
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
//                mArcProgressBar.setProgressDesc("1530");
            }
        });
        valueAnimator.setDuration(5000);
        valueAnimator.start();


        ValueAnimator valueAnimator1 = ValueAnimator.ofInt(0, 43);
        valueAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
mArcProgressBar.setScoreProgress((Integer) animation.getAnimatedValue());
            }
        });
        valueAnimator1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
//                mArcProgressBar.setProgressDesc("1530");
            }
        });
        valueAnimator1.setDuration(5000);
        valueAnimator1.start();
    }
}
