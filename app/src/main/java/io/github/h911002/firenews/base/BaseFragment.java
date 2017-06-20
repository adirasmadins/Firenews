package io.github.h911002.firenews.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.github.h911002.firenews.R;
import io.github.h911002.firenews.base.mvp.BasePresenter;
import io.github.h911002.firenews.base.mvp.IView;
import io.github.h911002.firenews.support.utils.ToastUtils;
import io.github.h911002.firenews.support.view.CommonTitleBar;

/**
 * Created by liaoheng on 2017/5/27.
 */

public abstract class BaseFragment<T extends BasePresenter> extends Fragment implements IView, View.OnClickListener {
    protected T presenter;


    private CommonTitleBar commonTitleBar;
    private ViewGroup containerView;
    private View contentView;
    private ProgressDialog progressDialog = null;
    private View errorView = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = onCreatePresenter();
    }

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                                   @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_fragment_container, container, false);
        onViewPrepare(inflater, rootView);
        hideLoadingView();
        hideErrorView();
        onContentViewCreated(contentView, savedInstanceState);
        return rootView;
    }

    protected abstract T onCreatePresenter();

    protected abstract int onContentViewID();

    protected abstract void onContentViewCreated(View contentView, Bundle savedInstanceState);


    private void onViewPrepare(LayoutInflater inflater, View container) {
        containerView = (ViewGroup) container.findViewById(R.id.layout_fragment_container);
        commonTitleBar = (CommonTitleBar) container.findViewById(R.id.common_title);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading. Please wait...");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        getActivity().getLayoutInflater().inflate(R.layout.view_error, containerView);
        errorView = containerView.getChildAt(containerView.getChildCount() - 1);
        errorView.setOnClickListener(this);
        inflater.inflate(onContentViewID(), containerView);
        contentView = containerView.getChildAt(containerView.getChildCount() - 1);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (presenter != null) {
            presenter.onStart();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (presenter != null) {
            presenter.onResume();
        }
    }

    @Override
    public void onPause() {
        if(progressDialog != null){
            progressDialog.dismiss();
        }
        if (presenter != null) {
            presenter.onPause();
        }

        super.onPause();
    }


    @Override
    public void onStop() {
        super.onStop();
        if (presenter != null) {
            presenter.onStop();
        }
    }

    @Override
    public void onDestroy() {
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
        //BarUtils.setColor(getActivity(), getResources().getColor(R.color.colorPrimary));

        if(backFlag){
            commonTitleBar.setTitle(title);
            commonTitleBar.setLeftActionImageView(getResources().getDrawable(R.mipmap
                    .ic_arrow_left_white));
            commonTitleBar.setOnTitleListener(new CommonTitleBar.OnTitleClickListener(){
                @Override
                public void onClickedLeftAction() {
                    super.onClickedLeftAction();
                    getActivity().finish();
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
        //BarUtils.setColor(getActivity(), getResources().getColor(R.color.white));

        if(backFlag){
            commonTitleBar.setTitle(title);
            commonTitleBar.setLeftActionImageView(getResources().getDrawable(R.mipmap
                    .ic_arrow_left_black));
            commonTitleBar.setOnTitleListener(new CommonTitleBar.OnTitleClickListener(){
                @Override
                public void onClickedLeftAction() {
                    super.onClickedLeftAction();
                    getActivity().finish();
                }
            });
        }else{
            commonTitleBar.setTitle(title);
        }

    }

    protected View findViewById(int id) {
        if (containerView != null) {
            return containerView.findViewById(id);
        } else return null;
    }

    protected void setViewClickListener(int id, View.OnClickListener onClickListener){
        View targetView = findViewById(id);
        if(targetView != null){
            targetView.setOnClickListener(onClickListener);
        }
    }

    @Override
    public void onClick(View v) {
        if(v == errorView){
            ToastUtils.showLong("you have just clicked error view");
        }
    }
}
