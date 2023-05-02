package com.zefra.pojo;
/*
* mybatis会把数据库的数据映射为对象类，期间会调用对的get和set方法,如果
*没有get和set方法，会直接访问属性，如果没有属性，会抛出异常
*
* */
public class ExceptionText {
    public String title;
    public String tags;
    public float time;
    public String context;

    public String getTitle() {
        /*title我们只返回一本分，超出部分做...处理*/
        return title.length() > 60 ? title.substring(0, 60) + "......" : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "ExceptionText{" +
                "title='" + title + '\'' +
                ", tags='" + tags + '\'' +
                ", time=" + time +
                ", context='" + context + '\'' +
                '}';
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public ExceptionText(String title, String tags, float time, String context) {
        this.title = title;
        this.tags = tags;
        this.time = time;
        this.context = context;
    }
}
