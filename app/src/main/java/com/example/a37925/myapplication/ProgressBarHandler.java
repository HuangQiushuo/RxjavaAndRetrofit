package com.example.a37925.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.ProgressBar;

/**
 * Created by 37925 on 2016/11/26.
 */

public class ProgressBarHandler extends Handler {

    public static final int SHOW_PROGRESS_DIALOG = 1;
    public static final int DISMISS_PROGRESS_DIALOG = 2;
    public static final int INCREAMENT = 3;
    private int total;
    private int current = 0;
    private int increaseRate;

    private ProgressBar progressBar;

    public ProgressBarHandler(ProgressBar progressBar, int total, int increaseRate) {
        super();
        this.progressBar = progressBar;
        this.total = total;
        this.increaseRate = increaseRate;
    }

    private void increase(){
        current+=increaseRate;
        int diff = increaseRate*100/total;
        progressBar.incrementProgressBy(diff);

    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case INCREAMENT:
                increase();
                break;
        }
    }
}
