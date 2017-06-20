package io.github.h911002.firenews.module.coin;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import io.github.h911002.firenews.R;
import io.github.h911002.firenews.base.BaseFragment;
import io.github.h911002.firenews.base.mvp.BasePresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class CoinFragment extends BaseFragment {


    public CoinFragment() {
        // Required empty public constructor
    }


    @Override
    protected BasePresenter onCreatePresenter() {
        return null;
    }

    @Override
    protected int onContentViewID() {
        return R.layout.fragment_coin;
    }

    @Override
    protected void onContentViewCreated(View contentView, Bundle savedInstanceState) {
        initBlueTitle(getString(R.string.coin_tag),false);
    }
}
