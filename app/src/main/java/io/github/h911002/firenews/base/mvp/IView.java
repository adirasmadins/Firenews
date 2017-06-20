package io.github.h911002.firenews.base.mvp;

/**
 * Created by athrun on 17/5/29.
 */

public interface IView {
    void showLoadingView();
    void hideLoadingView();

    void showErrorView();
    void hideErrorView();

    void showContentView();
    void hideContentView();
}
