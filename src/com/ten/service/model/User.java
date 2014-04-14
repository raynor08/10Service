package com.ten.service.model;

public class User {
    public String getLongt() {
        return longt;
    }

    public void setLongt(String longt) {
        this.longt = longt;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    private final long id;
    private final String userName;
    private String longt;
    private String lat;

    public User(long id, String userName, String lat, String longt) {
        this.id = id;
        this.userName = userName;
        this.lat = lat;
        this.longt = longt;
    }

}
