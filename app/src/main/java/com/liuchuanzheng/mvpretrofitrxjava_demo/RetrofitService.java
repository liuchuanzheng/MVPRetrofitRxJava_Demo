package com.liuchuanzheng.mvpretrofitrxjava_demo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Retrofit所有请求接口的定义接口类。通过注解定义
 */
public interface RetrofitService {
    //book/search?q=金瓶梅&tag=&start=0&count=1
    @GET("book/search")
    Call<Book> getSearchBook(@Query("q") String name,
                             @Query("tag") String tag,
                             @Query("start") int start,
                             @Query("count") int count);
}
