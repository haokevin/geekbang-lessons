package org.geektimes.projects.user.web.model;

public class Oauth2Properties {
    private String clientId = "111baa726d13585d1039";
    private String clientSecret = "7022f8a453d238a846e97b4224f136855b56ce1d";
    private String authorizeUrl = "https://github.com/login/oauth/authorize";
    private String redirectUrl = "http://localhost:8080/oauth2/callback";
    private String accessTokenUrl = "https://github.com/login/oauth/access_token";
    private String userInfoUrl = "https://api.github.com/user";


    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getAuthorizeUrl() {
        return authorizeUrl;
    }

    public void setAuthorizeUrl(String authorizeUrl) {
        this.authorizeUrl = authorizeUrl;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getAccessTokenUrl() {
        return accessTokenUrl;
    }

    public void setAccessTokenUrl(String accessTokenUrl) {
        this.accessTokenUrl = accessTokenUrl;
    }

    public String getUserInfoUrl() {
        return userInfoUrl;
    }

    public void setUserInfoUrl(String userInfoUrl) {
        this.userInfoUrl = userInfoUrl;
    }
}
