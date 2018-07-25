package com.xzydonate.news;

import com.xzydonate.basesdk.entity.WanResp;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface NewsApi {

    @GET("/banner/json")
    Observable<WanResp<List<BannerResp>>> queryBanner();

    @GET("/article/list/{page}/json")
    Observable<WanResp<NewsResp>> queryNews(@Path("page") int page, @QueryMap Map<String, Object> req);
}