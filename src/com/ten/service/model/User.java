package com.ten.service.model;

import java.util.Arrays;


import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "users")
public class User{
    @Id
    private String id;
    private String userName;
    private double[] location;
    private long createTime;
    private long lastLoginTime;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String imageUrl;
    private String displayName;
    private String credential;

    @PersistenceConstructor
    public User(final String id, String userName, long createTime, long lastLoginTime,
            double[] location, String firstName, String lastName, String emailAddress,
            String imageUrl, String displayName, final String credential) {
        this.userName = userName;
        this.id = id;
        this.createTime = createTime;
        this.lastLoginTime = lastLoginTime;
        this.location = location;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.imageUrl = imageUrl;
        this.displayName = displayName;
        this.credential = credential;
    }

    public String getCredential() {
        return credential;
    }

    public void setCredential(String credential) {
        this.credential = credential;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + userName + ", location=" + Arrays.toString(location)
                + "]";
    }
}
