package io.github.h911002.firenews.base;

import android.app.Application;

import io.github.h911002.firenews.support.utils.Utils;

/**
 * Created by liaoheng on 2017/5/27.
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}
