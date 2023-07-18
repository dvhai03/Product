package com.example.apiproduct.Model;

public class Binhluan {
    ProFile id_user;

    SanPham1 id_sanpham;

    String binhluan;

    public Binhluan(ProFile id_user, SanPham1 id_sanpham, String binhluan) {
        this.id_user = id_user;
        this.id_sanpham = id_sanpham;
        this.binhluan = binhluan;
    }

    public ProFile getId_user() {
        return id_user;
    }

    public void setId_user(ProFile id_user) {
        this.id_user = id_user;
    }

    public SanPham1 getId_sanpham() {
        return id_sanpham;
    }

    public void setId_sanpham(SanPham1 id_sanpham) {
        this.id_sanpham = id_sanpham;
    }

    public String getBinhluan() {
        return binhluan;
    }

    public void setBinhluan(String binhluan) {
        this.binhluan = binhluan;
    }
}
