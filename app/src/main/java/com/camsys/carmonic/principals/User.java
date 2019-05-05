package com.camsys.carmonic.principals;

public class User {

    private int id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String token;

    public int getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }
}
