package com.example.apiproduct.Model;

public class Reg {
    private String email;
    private String matkhau;
    private String hoten;

    public Reg(String email, String matkhau, String hoten) {
        this.email = email;
        this.matkhau = matkhau;
        this.hoten = hoten;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }
}
