package com.example.apiproduct.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SanPham implements Serializable {
    @SerializedName("tensp")
    private  String tensp;
    @SerializedName("_id")
    private String _id;
    @SerializedName("id_theloai")
    private TheLoai id_theloai;
    @SerializedName("anh")
    private String anh;
    @SerializedName("mota")
    private String mota;
    @SerializedName("giatien")
    private double giatien;

    public SanPham(String tensp, String _id, TheLoai id_theloai, String anh, String mota, double giatien) {
        this.tensp = tensp;
        this._id = _id;
        this.id_theloai = id_theloai;
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

    public TheLoai getId_theloai() {
        return id_theloai;
    }

    public void setId_theloai(TheLoai id_theloai) {
        this.id_theloai = id_theloai;
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
