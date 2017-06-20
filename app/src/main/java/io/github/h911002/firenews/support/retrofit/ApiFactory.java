package io.github.h911002.firenews.support.retrofit;

import java.util.concurrent.TimeUnit;

import io.github.h911002.firenews.support.constant.DomainConstants;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by liaoheng on 2017/6/8.
 */

public class ApiFactory {
    private static final ApiFactory ourInstance = new ApiFactory();
    private OkHttpClient httpClient;

    public static ApiFactory getInstance() {
        return ourInstance;
    }

    private ApiFactory() {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.connectTimeout(5, TimeUnit.SECONDS);
        builder.addInterceptor(new CommonIntercepter());
        httpClient = builder.build();
    }

    public <T> T create(Class<T> cls) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(DomainConstants.DOMAIN).client
                (httpClient).addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit.create(cls);
    }
}
