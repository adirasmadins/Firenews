package io.github.h911002.firenews.module.main;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import io.github.h911002.firenews.R;
import io.github.h911002.firenews.base.BaseActivity;
import io.github.h911002.firenews.base.mvp.IPresenter;
import io.github.h911002.firenews.support.utils.ActivityUtils;

/**
 * Created by liaoheng on 2017/6/5.
 */

public class SplashActivity extends BaseActivity {
    @Override
    protected IPresenter onCreatePresenter() {
        return null;
    }

    @Override
    protected int onContentViewID() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onContentViewCreated(View contentView, Bundle savedInstanceState) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                ActivityUtils.startActivity(SplashActivity.this, MainActivity.class);
                SplashActivity.this.finish();
            }
        }, 500);
    }
}
