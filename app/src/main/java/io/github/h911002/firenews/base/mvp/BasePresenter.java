package io.github.h911002.firenews.base.mvp;

/**
 * Created by athrun on 17/5/29.
 */

public abstract class BasePresenter<T extends IView, R extends IModel> implements IPresenter {
    protected T mView;
    protected R mModel;

    public BasePresenter(T view, R model) {
        this.mView = view;
        this.mModel = model;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestory() {

    }
}
