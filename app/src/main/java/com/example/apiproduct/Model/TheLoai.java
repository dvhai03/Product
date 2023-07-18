package com.example.apiproduct.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TheLoai implements Serializable {
    @SerializedName("_id")
    private String _id;
    @SerializedName("ten_tl")
    private String ten_tl;

    public TheLoai(String _id, String ten_tl) {
        this._id = _id;
        this.ten_tl = ten_tl;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTen_tl() {
        return ten_tl;
    }

    public void setTen_tl(String ten_tl) {
        this.ten_tl = ten_tl;
    }
}
