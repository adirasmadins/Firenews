package io.github.h911002.firenews.module.login;

import android.os.Bundle;
import android.view.View;

import io.github.h911002.firenews.R;
import io.github.h911002.firenews.base.BaseActivity;
import io.github.h911002.firenews.data.User;
import io.github.h911002.firenews.support.utils.ActivityUtils;
import io.github.h911002.firenews.support.utils.ToastUtils;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginPresenter
        .LoginView {


    @Override
    protected LoginPresenter onCreatePresenter() {
        return new LoginPresenter(this, null);
    }

    @Override
    protected int onContentViewID() {
        return R.layout.activity_login;
    }

    @Override
    protected void onContentViewCreated(View contentView, Bundle savedInstanceState) {
        initWhiteTitle("登录", true);
        setViewClickListener(R.id.btn_confirm, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadingView();
                presenter.login("lh", "");
            }
        });
        setViewClickListener(R.id.tv_register, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.startActivity(LoginActivity.this, RegisterActivity.class);
            }
        });
    }

    @Override
    public void loginSuccess(User user) {
        hideLoadingView();
        ToastUtils.showShort("登录成功:" + user.getUserName());
    }

    @Override
    public void loginFail() {
        showErrorView();
        ToastUtils.showShort("登录失败");
    }


}
