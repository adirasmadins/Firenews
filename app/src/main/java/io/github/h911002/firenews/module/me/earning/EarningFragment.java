package io.github.h911002.firenews.module.me.earning;

import android.os.Bundle;
import android.view.View;

import io.github.h911002.firenews.R;
import io.github.h911002.firenews.base.BaseFragment;
import io.github.h911002.firenews.base.mvp.BasePresenter;

/**
 * Created by athrun on 17/6/6.
 */

public class EarningFragment extends BaseFragment {
    @Override
    protected BasePresenter onCreatePresenter() {
        return null;
    }

    @Override
    protected int onContentViewID() {
        return R.layout.fragment_earning_list;
    }

    @Override
    protected void onContentViewCreated(View contentView, Bundle savedInstanceState) {

    }
}
