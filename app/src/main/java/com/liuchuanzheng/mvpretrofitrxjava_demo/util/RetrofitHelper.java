package com.liuchuanzheng.mvpretrofitrxjava_demo.util;

import android.content.Context;

import com.google.gson.GsonBuilder;
import com.liuchuanzheng.mvpretrofitrxjava_demo.retrofitservice.RetrofitService;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 刘传政 on 2018/5/28 0028.
 * QQ:1052374416
 * 电话:18501231486
 * 作用:
 * 注意事项:
 */
public class RetrofitHelper {

    private Context mCntext;

    OkHttpClient client = new OkHttpClient();
    GsonConverterFactory factory = GsonConverterFactory.create(new GsonBuilder().create());
    private static RetrofitHelper instance = null;
    private Retrofit mRetrofit = null;
    public static RetrofitHelper getInstance(Context context){
        if (instance == null){
            instance = new RetrofitHelper(context);
        }
        return instance;
    }
    private RetrofitHelper(Context mContext){
        mCntext = mContext;
        init();
    }

    private void init() {
        resetApp();
    }

    private void resetApp() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl("https://api.douban.com/v2/")
                .client(client)
                .addConverterFactory(factory)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }
    public RetrofitService getService(){
        return mRetrofit.create(RetrofitService.class);
    }
}

