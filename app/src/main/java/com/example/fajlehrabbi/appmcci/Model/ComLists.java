package com.example.fajlehrabbi.appmcci.Model;

/**
 * Created by Administrator on 12/28/2017.
 */

public class ComLists {
    private String id;
    private String cat_name;
    private String status;
    private String position;
    private String type;
    private String short_code;

    public ComLists(String id, String cat_name, String status, String position, String type, String short_code) {
        this.id = id;
        this.cat_name = cat_name;
        this.status = status;
        this.position = position;
        this.type = type;
        this.short_code = short_code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getShort_code() {
        return short_code;
    }

    public void setShort_code(String short_code) {
        this.short_code = short_code;
    }
}
