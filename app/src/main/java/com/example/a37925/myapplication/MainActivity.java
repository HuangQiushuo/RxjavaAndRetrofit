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
import com.example.a37925.myapplication.entity.UserListEntity;
import com.example.a37925.myapplication.network.NetworkHelper;


import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private TextView insertTextView;
    private UserDao userDao;
    private List<User> users;
    private TextView fetchTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initDatabase();
        users = new ArrayList<>();
        //generateUsers(10000);
    }

    public void getUsersFromDouban(){
        for(int i=0;i<10;i++){
            System.out.println(i);
            Subscriber<UserListEntity> subscriber = new Subscriber<UserListEntity>() {
                private int count = 0;
                @Override
                public void onCompleted() {
                    if(count == 20){
                        Toast.makeText(MainActivity.this, "Get 2000 users complete", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(UserListEntity userListEntity) {
                    users.addAll(userListEntity.getUsers());
                    System.out.println(users.size()+":"+userListEntity.getUsers().get(0).getName());
                }
            };
            NetworkHelper.getInstance().findUsers(subscriber, "c", 100, i*100);
        }
    }

    public void initViews(){
        insertTextView = (TextView) findViewById(R.id.insertTextView);

        insertTextView.setOnClickListener(new View.OnClickListener() {
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

        fetchTextView = (TextView) findViewById(R.id.fetchTextView);

        fetchTextView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                getUsersFromDouban();
                //test();
            }
        });
    }

    private void test(){


        for(int i = 0;i<10;i++) {
            Observable observable = Observable.just(i);
            Subscriber<Integer> subscriber = new Subscriber<Integer>() {
                @Override
                public void onCompleted() {
                    Toast.makeText(MainActivity.this, "Insert complete!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(Integer pb) {
                    System.out.println(pb);
                }
            };
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(subscriber);
        }
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
