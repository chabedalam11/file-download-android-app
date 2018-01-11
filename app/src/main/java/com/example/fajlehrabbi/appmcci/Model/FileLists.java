package com.example.fajlehrabbi.appmcci.Model;


import java.io.Serializable;

/**
 * Created by Administrator on 12/28/2017.
 */

public class FileLists implements Serializable{
    private String id;
    private String name;
    private String cat_id;
    private String committee;
    private String subcat_id;
    private String sub_committee;
    private String file_upload;
    private String date;
    private String file_extension;
    private String satatus;

    public FileLists() {
    }

    public FileLists(String id, String name, String cat_id, String committee, String subcat_id, String sub_committee, String file_upload, String date, String file_extension, String satatus) {
        this.id = id;
        this.name = name;
        this.cat_id = cat_id;
        this.committee = committee;
        this.subcat_id = subcat_id;
        this.sub_committee = sub_committee;
        this.file_upload = file_upload;
        this.date = date;
        this.file_extension = file_extension;
        this.satatus = satatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getCommittee() {
        return committee;
    }

    public void setCommittee(String committee) {
        this.committee = committee;
    }

    public String getSubcat_id() {
        return subcat_id;
    }

    public void setSubcat_id(String subcat_id) {
        this.subcat_id = subcat_id;
    }

    public String getSub_committee() {
        return sub_committee;
    }

    public void setSub_committee(String sub_committee) {
        this.sub_committee = sub_committee;
    }

    public String getFile_upload() {
        return file_upload;
    }

    public void setFile_upload(String file_upload) {
        this.file_upload = file_upload;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFile_extension() {
        return file_extension;
    }

    public void setFile_extension(String file_extension) {
        this.file_extension = file_extension;
    }

    public String getSatatus() {
        return satatus;
    }

    public void setSatatus(String satatus) {
        this.satatus = satatus;
    }
}