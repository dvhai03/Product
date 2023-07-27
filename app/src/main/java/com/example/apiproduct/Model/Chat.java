package com.example.apiproduct.Model;

public class Chat {
   ProFile id_user;
    String mess;

    public Chat(ProFile id_user, String mess) {
        this.id_user = id_user;
        this.mess = mess;
    }

    public ProFile getId_user() {
        return id_user;
    }

    public void setId_user(ProFile id_user) {
        this.id_user = id_user;
    }

    public String getMess() {
        return mess;
    }

    public void setMess(String mess) {
        this.mess = mess;
    }
}
