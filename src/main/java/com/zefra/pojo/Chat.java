package com.zefra.pojo;

import java.sql.Timestamp;

public class Chat {
    public int id;
    public String url;
    public String name;
    public Timestamp time;
    public String txt;

    @Override
    public String toString() {
        return "Chat{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", time=" + time +
                ", txt='" + txt + '\'' +
                '}';
    }

    public Chat(int id, String url, String name, Timestamp time, String txt) {
        this.id = id;
        this.url = url;
        this.name = name;
        this.time = time;
        this.txt = txt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }
}
