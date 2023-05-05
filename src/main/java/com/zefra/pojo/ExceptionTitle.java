package com.zefra.pojo;
/*
* mybatis会把数据库的数据映射为对象类，期间会调用对的get和set方法,如果
*没有get和set方法，会直接访问属性，如果没有属性，会抛出异常
*
* */
public class ExceptionTitle {
    private int id;
    private String title;
    private String getTitle() {
        /*title我们只返回一本分，超出部分做...处理*/
        return title.length() > 60 ? title.substring(0, 60) + "......" : title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "ExceptionTitle{" +
                "title='" + title + '\'' +
                ", id=" + id +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ExceptionTitle(int id,String title) {
        this.id = id;
        this.title = title;
    }
}
