package com.example.a37925.myapplication.network;

import com.example.a37925.myapplication.entity.UserListEntity;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by 37925 on 2016/11/26.
 */

public interface DoubanApi {
    @GET("user")
    public Observable<UserListEntity> findUsers(@Query("q") String query, @Query("start") int start, @Query("count") int count);
}
