package io.github.h911002.firenews.module.me.bind.phone;

import android.os.Bundle;
import android.view.View;

import io.github.h911002.firenews.R;
import io.github.h911002.firenews.base.BaseActivity;
import io.github.h911002.firenews.base.mvp.IPresenter;

public class BindPhoneActivity extends BaseActivity {


    @Override
    protected IPresenter onCreatePresenter() {
        return null;
    }

    @Override
    protected int onContentViewID() {
        return R.layout.activity_bind_phone;
    }

    @Override
    protected void onContentViewCreated(View contentView, Bundle savedInstanceState) {
        initWhiteTitle("绑定手机", true);
    }
}
