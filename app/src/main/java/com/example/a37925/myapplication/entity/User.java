package com.example.a37925.myapplication.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by 37925 on 2016/11/26.
 */
@Entity
public class User {
    @Id(autoincrement = true)
    private Long _id;
    private String id;
    private String name;
    private String uid;
    private String alt;
    private String avatar;
    public String getAvatar() {
        return this.avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public String getAlt() {
        return this.alt;
    }
    public void setAlt(String alt) {
        this.alt = alt;
    }
    public String getUid() {
        return this.uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public Long get_id() {
        return this._id;
    }
    public void set_id(Long _id) {
        this._id = _id;
    }
    @Generated(hash = 237983040)
    public User(Long _id, String id, String name, String uid, String alt,
            String avatar) {
        this._id = _id;
        this.id = id;
        this.name = name;
        this.uid = uid;
        this.alt = alt;
        this.avatar = avatar;
    }
    @Generated(hash = 586692638)
    public User() {
    }

}
