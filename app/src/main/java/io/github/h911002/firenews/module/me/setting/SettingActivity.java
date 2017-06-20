package io.github.h911002.firenews.module.me.setting;

import android.os.Bundle;
import android.view.View;

import io.github.h911002.firenews.R;
import io.github.h911002.firenews.base.BaseActivity;
import io.github.h911002.firenews.base.mvp.IPresenter;

/**
 * Created by liaoheng on 2017/6/5.
 */

public class SettingActivity extends BaseActivity {
    @Override
    protected IPresenter onCreatePresenter() {
        return null;
    }

    @Override
    protected int onContentViewID() {
        return R.layout.activity_setting;
    }

    @Override
    protected void onContentViewCreated(View contentView, Bundle savedInstanceState) {
        initWhiteTitle("设置",true);

    }

}
