package com.example.fajlehrabbi.appmcci.Model;

/**
 * Created by Administrator on 12/28/2017.
 */

public class SubComLists {
    private String id;
    private String subcat_name;
    private String cat_id;
    private String status;
    private String position;
    private String type;
    private String short_code;

    public String getId() {
        return id;
    }

    public SubComLists(String id, String subcat_name, String cat_id, String status, String position, String type, String short_code) {
        this.id = id;
        this.subcat_name = subcat_name;
        this.cat_id = cat_id;
        this.status = status;
        this.position = position;
        this.type = type;
        this.short_code = short_code;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubcat_name() {
        return subcat_name;
    }

    public void setSubcat_name(String subcat_name) {
        this.subcat_name = subcat_name;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
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
