package com.example.mydefenxiang;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    public static final String BASE_URL = "https://gank.io/";
    @GET("api/data/%E7%A6%8F%E5%88%A9/20/{page}")
    Observable<GirlsBean> getGirls(@Path("page") int page);
}
