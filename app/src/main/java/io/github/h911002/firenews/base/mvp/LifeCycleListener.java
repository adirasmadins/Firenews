package io.github.h911002.firenews.base.mvp;

/**
 * Created by athrun on 17/5/29.
 */

public interface LifeCycleListener {
    void onStart();
    void onResume();
    void onPause();
    void onStop();
    void onDestory();
}
