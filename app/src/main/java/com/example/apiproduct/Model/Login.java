package com.example.apiproduct.Model;

import com.google.gson.annotations.SerializedName;

public class Login {
    @SerializedName("token")
  private String token;

    public Login(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
