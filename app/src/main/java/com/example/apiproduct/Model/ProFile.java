package com.example.apiproduct.Model;

import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProFile implements Serializable {
    @SerializedName("hoten")
    String hoten;
    @SerializedName("email")
    String email;
    @SerializedName("_id")
    String _id;
    @SerializedName("anh")
    String anh;

    public ProFile() {
    }

    public ProFile(String hoten, String email, String _id, String anh) {
        this.hoten = hoten;
        this.email = email;
        this._id = _id;
        this.anh = anh;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}