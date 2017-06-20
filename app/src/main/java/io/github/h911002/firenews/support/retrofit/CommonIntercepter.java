package io.github.h911002.firenews.support.retrofit;

import java.io.IOException;

import io.github.h911002.firenews.support.constant.SPConstants;
import io.github.h911002.firenews.support.utils.AppUtils;
import io.github.h911002.firenews.support.utils.DeviceUtils;
import io.github.h911002.firenews.support.utils.EncryptUtils;
import io.github.h911002.firenews.support.utils.SPUtils;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by liaoheng on 2017/6/8.
 */

public class CommonIntercepter implements Interceptor {
    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl.Builder urlBuilder = request.url().newBuilder();
        urlBuilder.addQueryParameter("imei", SPUtils.getInstance().getString(SPConstants.IMEI));
        urlBuilder.addQueryParameter("ostype", "android "+ DeviceUtils.getSDKVersion());
        urlBuilder.addQueryParameter("version", AppUtils.getAppVersionName());
        urlBuilder.addQueryParameter("timespan", String.valueOf(System.currentTimeMillis()));
        urlBuilder.addQueryParameter("keystring", EncryptUtils.encryptMD5ToString("".getBytes()));
        urlBuilder.addQueryParameter("pid", "1.0.0");
        urlBuilder.addQueryParameter("session", "");
        Request.Builder requestBuilder = request.newBuilder();
        Request newRequest = requestBuilder.url(urlBuilder.build()).build();
        Response response = chain.proceed(newRequest);
        return response;
    }
}