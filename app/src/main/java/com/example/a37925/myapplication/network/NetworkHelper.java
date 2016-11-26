package com.example.a37925.myapplication.network;

import com.example.a37925.myapplication.entity.UserListEntity;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.NewThreadScheduler;
import rx.schedulers.Schedulers;

/**
 * Created by 37925 on 2016/11/26.
 */

public class NetworkHelper {
    private static NetworkHelper networkHelper;
    private String baseUrl = "https://api.douban.com/v2/";
    private Retrofit retrofit;
    private DoubanApi doubanApi;
    private NetworkHelper(){
         retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                 .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        doubanApi = retrofit.create(DoubanApi.class);
    }

    public static NetworkHelper getInstance(){
        if(networkHelper==null){
            networkHelper = new NetworkHelper();
        }
        return networkHelper;
    }

    public void findUsers(Subscriber<UserListEntity> subscriber,
                          String query, int count, int start){
        doubanApi.findUsers(query, start, count)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
