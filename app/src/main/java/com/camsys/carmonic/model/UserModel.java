package com.camsys.carmonic.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public  class UserModel {

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




    public class AuthInfo {

        @SerializedName("message")
        @Expose
        private String message;
        private String responseCode;

        public String getMessage() {
            return message;
        }
        public void setMessage(String message) {
            this.message = message;
        }

        public String getResponseCode() {
            return responseCode;
        }

        public void setResponseCode(String message) {
            this.responseCode = responseCode;
        }

    }

}



