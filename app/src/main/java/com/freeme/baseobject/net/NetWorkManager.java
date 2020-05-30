package com.freeme.baseobject.net;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetWorkManager {
    //如果不使用Https，则会抛出异常（API版本27以上，华为)
    private static final String URL_BASE = "https://192.168.0.28:8000/Api/";
    private static NetWorkManager netWorkManager;
    private OkHttpClient okHttpClient;
    private static NetInterceptor interceptor = new NetInterceptor.Builder().addHeaderParams("token", "ekf823d2f").build();

    public static NetInterceptor getInterceptor() {
        return interceptor;
    }

    public static NetWorkManager getInstance(NetInterceptor interceptor) {
        if (netWorkManager == null) {
            netWorkManager = new NetWorkManager(interceptor);
        }
        return netWorkManager;
    }

    private NetWorkManager(NetInterceptor interceptor) {
        //初始化Interceptor
        if (interceptor == null) {
            interceptor = new NetInterceptor.Builder()
//                    .addHeaderParams("userToken", "1234343434dfdfd3434")
//                    .addHeaderParams("userId", "admin")
                    .build();
        }
        //初始化OKHttpClient
        this.okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
    }

    public NetApi getNetApi() {
        //获取接口服务
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        //webUrl=WEREWOLF_URL_BASE;

        return retrofit.create(NetApi.class);
    }
}
