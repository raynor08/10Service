package com.ten.service.model;

public class OAuth2Request {
    private final String providerId;
    private final String accessToken;

    public OAuth2Request(String providerId, String accessToken) {
        this.providerId = providerId;
        this.accessToken = accessToken;
    }

    public String getProviderId() {
        return providerId;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
