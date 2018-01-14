package com.example.fajlehrabbi.appmcci.Model;

import java.io.Serializable;

/**
 * Created by Administrator on 12/28/2017.
 */

public class AllCatSubCatFiles implements Serializable{

    private String status;
    private String status_code;
    FileData data = new FileData();

    public AllCatSubCatFiles() {
    }

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

    public FileData getData() {
        return data;
    }

    public void setData(FileData data) {
        this.data = data;
    }
}
