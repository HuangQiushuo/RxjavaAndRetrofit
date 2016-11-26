package com.example.a37925.myapplication.entity;

import java.util.List;

/**
 * Created by 37925 on 2016/11/26.
 */

public class UserListEntity {
    private int count;
    private int start;
    private int total;
    private List<User> users;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
