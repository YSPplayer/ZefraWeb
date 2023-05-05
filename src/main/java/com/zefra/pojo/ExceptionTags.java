package com.zefra.pojo;

public class ExceptionTags {
    private int id;
    private float time;
    private long tags;
    private String stags;
    @Override
    public String toString() {
        return "ExceptionTags{" +
                "time=" + time +
                ", tags=" + tags +
                ", id=" + id +
                '}';
    }

    public String getStags() {
        return stags;
    }

    public void setStags(String stags) {
        this.stags = stags;
    }

    public ExceptionTags(int id, long tags, float time ) {
        this.time = time;
        this.tags = tags;
        this.id = id;
        this.stags = "";
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public long getTags() {
        return tags;
    }

    public void setTags(long tags) {
        this.tags = tags;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
