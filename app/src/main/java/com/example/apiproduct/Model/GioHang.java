package com.example.apiproduct.Model;

import com.google.gson.annotations.SerializedName;

public class GioHang {
    @SerializedName("_id")
    private  String id;
    @SerializedName("id_sanpham")
    private SanPham1 sanPham;
    @SerializedName("soluong")
    private int soluong;

    public GioHang(String id, SanPham1 sanPham, int soluong) {
        this.id = id;
        this.sanPham = sanPham;
        this.soluong = soluong;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SanPham1 getSanPham() {
        return sanPham;
    }

    public void setSanPham(SanPham1 sanPham) {
        this.sanPham = sanPham;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }
}
