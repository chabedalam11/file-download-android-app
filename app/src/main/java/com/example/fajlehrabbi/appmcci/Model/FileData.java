package com.example.fajlehrabbi.appmcci.Model;

import java.util.ArrayList;

/**
 * Created by Administrator on 12/28/2017.
 */

public class FileData {
    ArrayList<ComLists> committee_list = new ArrayList<ComLists>();
    ArrayList<SubComLists> sub_committee_list = new ArrayList<SubComLists>();
    ArrayList<FileLists> file_list = new ArrayList<FileLists>();

    public ArrayList<ComLists> getCommittee_list() {
        return committee_list;
    }

    public void setCommittee_list(ArrayList<ComLists> committee_list) {
        this.committee_list = committee_list;
    }

    public ArrayList<SubComLists> getSub_committee_list() {
        return sub_committee_list;
    }

    public void setSub_committee_list(ArrayList<SubComLists> sub_committee_list) {
        this.sub_committee_list = sub_committee_list;
    }

    public ArrayList<FileLists> getFile_list() {
        return file_list;
    }

    public void setFile_list(ArrayList<FileLists> file_list) {
        this.file_list = file_list;
    }
}
