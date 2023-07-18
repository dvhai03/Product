package com.example.apiproduct.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SanPham1 implements Serializable {
    @SerializedName("tensp")
    private  String tensp;
    @SerializedName("_id")
    private String _id;
    @SerializedName("anh")
    private String anh;
    @SerializedName("mota")
    private String mota;
    @SerializedName("giatien")
    private double giatien;

    public SanPham1(String tensp, String _id, String anh, String mota, double giatien) {
        this.tensp = tensp;
        this._id = _id;
        this.anh = anh;
        this.mota = mota;
        this.giatien = giatien;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public double getGiatien() {
        return giatien;
    }

    public void setGiatien(double giatien) {
        this.giatien = giatien;
    }
}
