package io.github.h911002.firenews.module.login;

import android.os.Bundle;
import android.view.View;

import io.github.h911002.firenews.R;
import io.github.h911002.firenews.base.BaseActivity;
import io.github.h911002.firenews.base.mvp.IPresenter;

public class RegisterActivity extends BaseActivity {


    @Override
    protected IPresenter onCreatePresenter() {
        return null;
    }

    @Override
    protected int onContentViewID() {
        return R.layout.activity_register;
    }

    @Override
    protected void onContentViewCreated(View contentView, Bundle savedInstanceState) {
        initWhiteTitle("注册", true);
    }
}
