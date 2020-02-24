package com.camsys.carmonic.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
