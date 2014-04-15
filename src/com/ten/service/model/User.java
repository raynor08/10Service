package com.ten.service.model;

import java.util.Arrays;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String userName;
    private double[] location;
    private long createTime;
    private long lastLoginTime;

    @PersistenceConstructor
    public User(final String id, String userName, long createTime, long lastLoginTime, double[] location) {
        this.userName = userName;
        this.id = id;
        this.createTime = createTime;
        this.lastLoginTime = lastLoginTime;
        this.location = location;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public double[] getLocation() {
        return location;
    }

    public void setLocation(double[] location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + userName + ", location=" + Arrays.toString(location)
                + "]";
    }
}
