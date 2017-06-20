package io.github.h911002.firenews.module.me;

import io.github.h911002.firenews.base.mvp.BasePresenter;
import io.github.h911002.firenews.base.mvp.IModel;
import io.github.h911002.firenews.base.mvp.IView;

/**
 * Created by athrun on 17/6/4.
 */

public class MeFragmentPresenter extends BasePresenter<MeFragmentPresenter.MeFragmentView, IModel> {

    public MeFragmentPresenter(MeFragmentView view, IModel model) {
        super(view, model);
    }

    interface MeFragmentView extends IView{

    }
}
