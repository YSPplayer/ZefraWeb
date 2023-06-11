package com.zefra.pojo;

public class ExceptionTags {
    private int id;
    private float time;
    private long tags;
    private String stags;
    private final static int TOTAL_DAYS = 3 ;
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
        //得到保留一位小数的浮点数;
        return (float) (Math.floor(time * 10) / 10);
    }

    public void setTime(float time) {
        this.time = time;
    }
    public static float toTime(int time) {
        return (float) (((double)time / (double)(TOTAL_DAYS * 24 * 60)) * 100.0);
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
