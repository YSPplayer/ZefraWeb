package com.zefra.pojo;

public class ExceptionContext {
    private int id;
    private String context;

    @Override
    public String toString() {
        return "ExceptionContext{" +
                "id=" + id +
                ", context='" + context + '\'' +
                '}';
    }

    public ExceptionContext(int id, String context) {
        this.id = id;
        this.context = context;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
