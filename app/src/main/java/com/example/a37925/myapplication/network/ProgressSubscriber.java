package com.example.a37925.myapplication.network;

import android.content.Context;
import android.widget.Toast;

import com.example.a37925.myapplication.ProgressBarHandler;

import rx.Subscriber;

/**
 * Created by 37925 on 2016/11/26.
 */

public class ProgressSubscriber<T> extends Subscriber<T> {

    private ProgressBarHandler handler;
    private Context context;
    private SubscriberOnNextListener nextListener;

    public ProgressSubscriber(SubscriberOnNextListener listener, ProgressBarHandler handler, Context context) {
        this.context = context;
        this.nextListener = listener;
        this.handler = handler;
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
        nextListener.onNext(t);
        increaseProgressDialog();
    }
}
