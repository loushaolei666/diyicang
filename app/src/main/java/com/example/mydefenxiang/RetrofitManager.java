package com.example.mydefenxiang;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {
    public RetrofitManager() {
    }
    public static volatile RetrofitManager retrofitManager;

    public static RetrofitManager getRetrofitManager(){
        if(retrofitManager == null){
            synchronized (RetrofitManager.class){
                if(retrofitManager == null){
                    retrofitManager = new RetrofitManager();
                }
            }
        }
        return retrofitManager;
    }
    //封装Retrofit对象
    private Retrofit getRetrofit(){
        return new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public <T> T createService(Class<T> tClass){
        return getRetrofit().create(tClass);
    }

}
