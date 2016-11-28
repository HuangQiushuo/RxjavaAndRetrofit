package com.example.a37925.myapplication.network;

import com.example.a37925.myapplication.entity.User;
import com.example.a37925.myapplication.entity.UserDao;

import java.util.List;

import rx.Subscriber;

/**
 * Created by 37925 on 2016/11/26.
 */

public class ProgressSubscriber<T> extends Subscriber<T> {

    private ProgressBarHandler handler;
    private UserDao userDao;
    public ProgressSubscriber(UserDao userDao, ProgressBarHandler handler) {
        this.handler = handler;
        this.userDao = userDao;
    }

    private void showProgressDialog(){
        if (handler != null) {
            handler.obtainMessage(ProgressBarHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    private void dismissProgressDialog(){
        if (handler != null) {
            handler.obtainMessage(ProgressBarHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            handler = null;
        }
    }

    private void increaseProgressDialog(){
        if (handler != null) {
            handler.obtainMessage(ProgressBarHandler.INCREAMENT).sendToTarget();
        }
    }

    @Override
    public void onStart() {
        showProgressDialog();
    }

    @Override
    public void onCompleted() {
        increaseProgressDialog();
    }

    @Override
    public void onError(Throwable e) {
        dismissProgressDialog();
    }

    public void onNext(T t) {
        List<User> users = (List<User>) t;
        int total = users.size();
        int current = 0;
        System.out.println(current);
        for(User user: users) {
            userDao.insert(user);
            current++;
            if(current==total/100){
                increaseProgressDialog();
                current = 0;
            }
        }
    }
}
