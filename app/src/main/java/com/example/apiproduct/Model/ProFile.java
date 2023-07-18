package com.example.apiproduct.Model;

import com.google.gson.annotations.SerializedName;

public class ProFile {
    @SerializedName("hoten")
    String hoten;
    @SerializedName("email")
    String email;
    @SerializedName("_id")
    String _id;

    public ProFile(String hoten, String email, String _id) {
        this.hoten = hoten;
        this.email = email;
        this._id = _id;
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
}