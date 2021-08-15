package com.zaenalanzarry.warungku.loginregist;

public class User {

    public String namaWarung, noHP, email;

    public String key;

    public User(){

    }

    public User(String namaWarung, String noHP, String email){
        this.namaWarung = namaWarung;
        this.noHP = noHP;
        this.email = email;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}