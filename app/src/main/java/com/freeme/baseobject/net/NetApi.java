package com.freeme.baseobject.net;



import com.freeme.baseobject.base.BaseBean;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;


public interface NetApi
{
    //获取抽奖规则
    @POST("MediaInterface.aspx")
    @FormUrlEncoded
    Observable<BaseBean> getRule(@Field("name") String name, @Field("psw") String psw);

    //获取豆瓣Top250 榜单
    @GET("top250")
    Observable<BaseBean> getTop250(@Query("start") int start, @Query("count")int count);


}