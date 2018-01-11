package com.example.fajlehrabbi.appmcci.Model;

import java.util.ArrayList;

/**
 * Created by FajlehRabbi on 12/31/2017.
 */

public class AllListResponse {
    private String status;
    private String username;
    private String token;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ArrayList<ComLists> getData() {
        return data;
    }

    public void setData(ArrayList<ComLists> data) {
        this.data = data;
    }

    public ArrayList<SubComLists> getData1() {
        return data1;
    }

    public void setData1(ArrayList<SubComLists> data1) {
        this.data1 = data1;
    }

    public ArrayList<FileLists> getData2() {
        return data2;
    }

    public void setData2(ArrayList<FileLists> data2) {
        this.data2 = data2;
    }

    public AllListResponse(String status, String username, String token, ArrayList<ComLists> data, ArrayList<SubComLists> data1, ArrayList<FileLists> data2) {
        this.status = status;
        this.username = username;

        this.token = token;
        this.data = data;
        this.data1 = data1;
        this.data2 = data2;
    }

    ArrayList<ComLists> data = new ArrayList<>();
    ArrayList<SubComLists> data1 = new ArrayList<>();
    ArrayList<FileLists> data2 = new ArrayList<>();

}