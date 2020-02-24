package com.camsys.carmonic.model;


import com.camsys.carmonic.model.AuthInfo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {


    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("authInfo")
    @Expose
    private AuthInfo authInfo;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public AuthInfo getAuthInfo() {
        return authInfo;
    }

    public void setAuthInfo(AuthInfo authInfo) {
        this.authInfo = authInfo;
    }



}
