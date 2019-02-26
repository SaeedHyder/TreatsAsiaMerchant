package com.app.merchanttreatzasia.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubBranchHistoryEnt {

    @SerializedName("record")
    @Expose
    private List<Record> record;

    @SerializedName("point_total")
    @Expose
    private String pointTotal;

    public List<Record> getRecord() {
        return record;
    }

    public void setRecord(List<Record> record) {
        this.record = record;
    }

    public String getPointTotal() {
        return pointTotal;
    }

    public void setPointTotal(String pointTotal) {
        this.pointTotal = pointTotal;
    }
}
