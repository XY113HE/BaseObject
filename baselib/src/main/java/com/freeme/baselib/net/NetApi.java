package com.freeme.baselib.net;


import com.freeme.baselib.base.BaseBean;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;


public interface NetApi
{
    //获取抽奖规则
    @POST("MediaInterface.aspx")
    @FormUrlEncoded
    Observable<BaseBean> login(@Field("name") String name, @Field("psw") String psw);

    //获取豆瓣Top250 榜单
    @GET("top250")
    Observable<BaseBean> getTop250(@Query("start") int start, @Query("count") int count);

    @GET("project/list/{page}/json?")
    Observable<BaseBean> getNeiCall(@Path("page") String page, @Query("cid") String cid);

    @GET("wxnew/?key=52b7ec3471ac3bec6846577e79f20e4c&num=10&page=10.com")
    Observable<ResponseBody> getCall();

    @GET("/ios/cf/dish_list.php?stage_id=1&limit=20")
    Observable<ResponseBody> getFoodList(@Query("page") String page);

    @GET("/ios/cf/dish_list.php?stage_id=1&limit=20")
    Observable<ResponseBody> getFoodList(@QueryMap Map<String, String> options);

    //get请求且有变量是使用
    @GET("20/{page}")
    Observable<ResponseBody> pathData(@Path("page") String a);

    //拼接url使用
    @GET()
    Observable<ResponseBody> urlData(@Url String url);

    @FormUrlEncoded //表单请求
    @POST("v1/login")
    Observable<ResponseBody> userLogin(@Field("phone") String phone, @Field("password") String password);

    @FormUrlEncoded
    @POST("book/reviews")
    Observable<String> addReviews(@FieldMap Map<String, String> fields);

}