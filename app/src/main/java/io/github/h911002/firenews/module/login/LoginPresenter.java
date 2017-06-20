package io.github.h911002.firenews.module.login;

import io.github.h911002.firenews.api.LoginAPI;
import io.github.h911002.firenews.base.mvp.BasePresenter;
import io.github.h911002.firenews.base.mvp.IModel;
import io.github.h911002.firenews.base.mvp.IView;
import io.github.h911002.firenews.data.CommonResponse;
import io.github.h911002.firenews.data.User;
import io.github.h911002.firenews.support.retrofit.ApiFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by athrun on 17/5/29.
 */

public class LoginPresenter extends BasePresenter<LoginPresenter.LoginView, IModel> {

    public LoginPresenter(LoginView view, IModel model) {
        super(view, model);
    }

    public void login(String userName, String pwd) {

        ApiFactory.getInstance().create(LoginAPI.class).login(userName,pwd).enqueue(new Callback<CommonResponse<User>>() {
            @Override
            public void onResponse(Call<CommonResponse<User>> call,
                                   Response<CommonResponse<User>> response) {
                mView.loginSuccess(response.body().getData());
            }

            @Override
            public void onFailure(Call<CommonResponse<User>> call, Throwable t) {
                mView.loginFail();
            }
        });
    }

    public interface LoginView extends IView {
        void loginSuccess(User user);
        void loginFail();
    }

}
