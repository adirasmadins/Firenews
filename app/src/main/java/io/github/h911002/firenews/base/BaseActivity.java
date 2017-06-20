package io.github.h911002.firenews.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;

import io.github.h911002.firenews.R;
import io.github.h911002.firenews.base.mvp.IPresenter;
import io.github.h911002.firenews.base.mvp.IView;
import io.github.h911002.firenews.support.utils.ToastUtils;
import io.github.h911002.firenews.support.view.CommonTitleBar;

/**
 * Created by liaoheng on 2017/5/27.
 */

public abstract class BaseActivity<T extends IPresenter> extends FragmentActivity implements IView,View.OnClickListener {

    protected T presenter;

    private CommonTitleBar commonTitleBar;
    private ViewGroup containerView;
    private View contentView;
    private ProgressDialog progressDialog = null;
    private View errorView = null;

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.layout_activity_container);
        onViewPrepare();
        hideLoadingView();
        hideErrorView();
        onContentViewCreated(contentView, savedInstanceState);
        presenter = onCreatePresenter();
    }

    protected abstract T onCreatePresenter();

    protected abstract int onContentViewID();

    protected abstract void onContentViewCreated(View contentView, Bundle savedInstanceState);

    private void onViewPrepare() {
        containerView = (ViewGroup) findViewById(R.id.layout_activity_container);
        commonTitleBar = (CommonTitleBar) findViewById(R.id.common_title);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading. Please wait...");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        getLayoutInflater().inflate(R.layout.view_error, containerView);
        errorView = containerView.getChildAt(containerView.getChildCount() - 1);
        errorView.setOnClickListener(this);
        getLayoutInflater().inflate(onContentViewID(), containerView);
        contentView = containerView.getChildAt(containerView.getChildCount() - 1);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (presenter != null) {
            presenter.onStart();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (presenter != null) {
            presenter.onResume();
        }
    }

    @Override
    protected void onPause() {
        if (presenter != null) {
            presenter.onPause();
        }
        if(progressDialog != null){
            progressDialog.dismiss();
        }

        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (presenter != null) {
            presenter.onStop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.onDestory();
        }
    }

    @Override
    public void showLoadingView() {
        hideContentView();
        hideErrorView();
        if (progressDialog != null) {
            progressDialog.show();
        }
    }

    @Override
    public void hideLoadingView() {
        showContentView();
        hideErrorView();
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void showErrorView() {
        hideContentView();
        if (errorView.getParent() == containerView) {
            errorView.setVisibility(View.VISIBLE);
        } else {
            containerView.addView(errorView);
        }
    }

    @Override
    public void hideErrorView() {
        showContentView();
        if (errorView.getParent() == containerView) {
            errorView.setVisibility(View.GONE);
        }
    }

    @Override
    public void showContentView() {
        if (contentView != null) {
            contentView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideContentView() {
        if (contentView != null) {
            contentView.setVisibility(View.GONE);
        }
    }

    protected void initBlueTitle(String title, boolean backFlag) {
        commonTitleBar.setVisibility(View.VISIBLE);
        //BarUtils.setColor(this, getResources().getColor(R.color.colorPrimary));
        if(backFlag){
            commonTitleBar.setTitle(title);
            commonTitleBar.setLeftActionImageView(getResources().getDrawable(R.mipmap
                    .ic_arrow_left_white));
            commonTitleBar.setOnTitleListener(new CommonTitleBar.OnTitleClickListener(){
                @Override
                public void onClickedLeftAction() {
                    super.onClickedLeftAction();
                    finish();
                }
            });
        }else{
            commonTitleBar.setTitle(title);
        }

    }

    protected void initWhiteTitle(String title, boolean backFlag) {
        commonTitleBar.setVisibility(View.VISIBLE);
        commonTitleBar.setBarBgColor(R.color.white);
        commonTitleBar.setTitleTextColorRes(R.color.text_color_black);
        //BarUtils.setColor(this, getResources().getColor(R.color.white));

        if(backFlag){
            commonTitleBar.setTitle(title);
            commonTitleBar.setLeftActionImageView(getResources().getDrawable(R.mipmap
                    .ic_arrow_left_black));
            commonTitleBar.setOnTitleListener(new CommonTitleBar.OnTitleClickListener(){
                @Override
                public void onClickedLeftAction() {
                    super.onClickedLeftAction();
                    finish();
                }
            });
        }else{
            commonTitleBar.setTitle(title);
        }

    }

    @Override
    public void onClick(View v) {
        if(v == errorView){
            ToastUtils.showLong("you have just clicked error view");
        }
    }

    protected void setViewClickListener(int id, View.OnClickListener onClickListener){
        View targetView = findViewById(id);
        if(targetView != null){
            targetView.setOnClickListener(onClickListener);
        }
    }
}
