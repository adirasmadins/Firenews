package io.github.h911002.firenews.api;

import io.github.h911002.firenews.data.CommonResponse;
import io.github.h911002.firenews.data.User;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by athrun on 17/5/30.
 */

public interface LoginAPI {
    @POST("login")
    Call<CommonResponse<User>> login(@Query("userName") String userName, @Query("pwd") String pwd);
}
