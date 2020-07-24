package com.example.day14.net;

import com.example.day14.bean.JavaBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiService {
    String BASE_URL="http://yun918.cn/";

    @GET("ks/jiekou1.json")
    Observable<JavaBean> getJson();
}
