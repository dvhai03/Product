package com.example.apiproduct.Model;

import com.google.gson.annotations.SerializedName;

public class DonHang {
    @SerializedName("_id")
    private String _id;
    @SerializedName("id_sanpham")
    private  SanPham1 sanpham;
    @SerializedName("id_user")
    private  ProFile proFile;
    private int soluong ;
    private  String diachi;

    public DonHang(String _id, SanPham1 sanpham, ProFile proFile, int soluong, String diachi) {
        this._id = _id;
        this.sanpham = sanpham;
        this.proFile = proFile;
        this.soluong = soluong;
        this.diachi = diachi;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public SanPham1 getSanpham() {
        return sanpham;
    }

    public void setSanpham(SanPham1 sanpham) {
        this.sanpham = sanpham;
    }

    public ProFile getProFile() {
        return proFile;
    }

    public void setProFile(ProFile proFile) {
        this.proFile = proFile;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }
}
