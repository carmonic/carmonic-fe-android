package com.camsys.carmonic.networking;

import com.camsys.carmonic.principals.User;

public class LoginResponse {

    private User user;
    private AuthInfo authInfo;

    public User getUser() {
        return user;
    }

    public AuthInfo getAuthInfo() {
        return authInfo;
    }
}
