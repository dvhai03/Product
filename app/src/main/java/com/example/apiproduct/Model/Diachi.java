package com.example.apiproduct.Model;

import com.google.gson.annotations.SerializedName;

public class Diachi {
    @SerializedName("hoten")
    private String hoten;
    @SerializedName("sodienthoai")
    private String sodienthoai;
    @SerializedName("diachi")
    private String diachi;

    public Diachi(String hoten, String sodienthoai, String diachi) {
        this.hoten = hoten;
        this.sodienthoai = sodienthoai;
        this.diachi = diachi;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getSodienthoai() {
        return sodienthoai;
    }

    public void setSodienthoai(String sodienthoai) {
        this.sodienthoai = sodienthoai;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }
}
