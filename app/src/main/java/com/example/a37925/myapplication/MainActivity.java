package com.example.a37925.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a37925.myapplication.entity.DaoMaster;
import com.example.a37925.myapplication.entity.User;
import com.example.a37925.myapplication.entity.UserDao;
import com.example.a37925.myapplication.network.NetworkHelper;


import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private TextView tv;
    private UserDao userDao;
    private List<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initDatabase();
        generateUsers(10000);

    }

    public void initViews(){
        tv = (TextView) findViewById(R.id.textView);

        tv.setOnClickListener(new View.OnClickListener() {
            private boolean isInserting = false;
            @Override
            public void onClick(View view) {
                if(isInserting){
                    Toast.makeText(MainActivity.this, "Now is inserting", Toast.LENGTH_SHORT).show();
                }else {
                    isInserting = true;
                    Toast.makeText(MainActivity.this, "Insert start", Toast.LENGTH_SHORT).show();
                    insertUsers();
                }
            }
        });
    }

    private void initDatabase(){
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(getApplicationContext(), "lenve.db", null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
        userDao = daoMaster.newSession().getUserDao();
    }

    private void generateUsers(int num){
        users = new ArrayList<>();
        while(num>0){
            String str = String.valueOf(num);
            User u = new User(null, str,str,str,str,str);
            users.add(u);
            num--;
        }
    }

    private void insertUsers(){
        Observable observable = Observable.create(new Observable.OnSubscribe<ProgressBar>() {
            @Override
            public void call(Subscriber<? super ProgressBar> subscriber) {
                ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar);
                int total = users.size();
                int current = 0;
                for(User user: users) {
                    userDao.insert(user);
                    current++;
                    if(current==total/100){
                        subscriber.onNext(pb);
                        current = 0;
                    }
                }
                subscriber.onCompleted();
            }
        });

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ProgressBar>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(MainActivity.this, "Insert complete!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ProgressBar pb) {
                        pb.incrementProgressBy(1);
                    }
                });
    }
}
