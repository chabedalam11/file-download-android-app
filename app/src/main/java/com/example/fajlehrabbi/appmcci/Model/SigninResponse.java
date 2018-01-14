package com.example.fajlehrabbi.appmcci.Model;

import java.io.Serializable;

/**
 * Created by User on 4/10/2017.
 */

public class SigninResponse implements Serializable{

    private String status;
    private String status_code;
    private String token;
    UserData data=new UserData();
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus_code() {
        return status_code;
    }

    public void setStatus_code(String status_code) {
        this.status_code = status_code;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserData getData() {
        return data;
    }

    public void setData(UserData data) {
        this.data = data;
    }




}
