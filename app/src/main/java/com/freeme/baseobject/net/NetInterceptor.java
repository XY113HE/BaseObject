package com.freeme.baseobject.net;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class NetInterceptor implements Interceptor {
        private Map<String, String> mHeaderParamsMap = new HashMap<>();

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Request.Builder builder = request.newBuilder();
            builder.method(request.method(), request.body());
            //添加公共参数,添加到header中
            if (mHeaderParamsMap.size() > 0) {
                for (Map.Entry<String, String> params : mHeaderParamsMap.entrySet()) {
                    builder.header(params.getKey(), params.getValue());
                }
            }
            return chain.proceed(builder.build());
        }

        public static class Builder {
            NetInterceptor interceptor;
            public Builder() {
                interceptor = new NetInterceptor();
            }
            public Builder addHeaderParams(String key, String value) {
                interceptor.mHeaderParamsMap.put(key, value);
                return this;
            }
            public NetInterceptor build(){
                return interceptor;
            }
        }
    }