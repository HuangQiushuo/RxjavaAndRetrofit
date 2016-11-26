package com.example.a37925.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.a37925.myapplication.entity.DaoMaster;
import com.example.a37925.myapplication.entity.User;
import com.example.a37925.myapplication.entity.UserDao;
import com.example.a37925.myapplication.entity.UserListEntity;
import com.example.a37925.myapplication.network.NetworkHelper;
import com.example.a37925.myapplication.network.ProgressSubscriber;
import com.example.a37925.myapplication.network.SubscriberOnNextListener;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private NetworkHelper networkHelper;
    private TextView tv;
    private UserDao userDao;
    private SubscriberOnNextListener findUsersOnNext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.textView);
        initNetwork();
        initDatabase();
//        tv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                tv.setText("ddd");
//                //userDao.insert(new User(null,"1","1","1","1","1"));
//                int i = userDao.loadAll().size();
//               tv.setText(String.valueOf(i));
//            }
//        });
               findTenThousandUsers();
    }

    private void change(View view){
        tv.setText(userDao.loadAll().size());
    }

    private void initDatabase(){
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(getApplicationContext(), "lenve.db", null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
        userDao = daoMaster.newSession().getUserDao();
    }

    private void initNetwork(){
        networkHelper = NetworkHelper.getInstance();
    }

    private void findTenThousandUsers(){

        String query = "c";
        int count = 100;
        for (int i=0; i<100; i++){
            findUsers(query, count, i*100);
        }
    }

    private void findUsers(String query, int count, final int start){
        SubscriberOnNextListener<UserListEntity> subscriber = new SubscriberOnNextListener<UserListEntity>() {
            @Override
            public void onNext(UserListEntity userListEntity) {
                List<User> userList = userListEntity.getUsers();
                userDao.insertInTx(userList);
            }
        };
        ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar);
        ProgressBarHandler handler = new ProgressBarHandler(pb, 100, 1);

        ProgressSubscriber<UserListEntity> progressSubscriber =
                new ProgressSubscriber<>(subscriber, handler, this.getApplicationContext());
        networkHelper.findUsers(progressSubscriber, query, count, start);
    }
}
