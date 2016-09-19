package ru.testapp.utils;

import java.io.Serializable;

public class UserPwd implements Serializable{
    private int id;
    private String hash;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
