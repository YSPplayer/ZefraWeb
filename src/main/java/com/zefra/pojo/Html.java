package com.zefra.pojo;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Html {
    //预加载我们的html文件
    public static Map<String,String> htmlsMap = new HashMap<>();
    //解析的标识符
    private static final char delimiterStartChar = '¥';
    private static final char delimiterStopChar = '¥';
    private static STGroup stGroup = new STGroup(delimiterStartChar, delimiterStopChar);
    private String context;
     //这个是存储我们的数据库应该有多少页，根据传入的数据数量来决定
     private int pages;
     //一页存放12个内容
     private final int ONE_PAGE_NUMBER = 12;
    public Html(String type,int count) {
        //设置我们索引页的数量
        setPages(count);
        setContext(type);
    }
    public Html(String type) {
        //设置我们索引页的数量
        this.pages = 0;
        setContext(type);
    }
    private void setPages(double count) {
        //向上取整
        this.pages = ((int)Math.ceil(count / ONE_PAGE_NUMBER)) * 10;
    }
    private void setContext(String type) {
        //获取我们指定的文本内容
        String context = "";
        context = htmlsMap.get(type);
        if(context == null) context = "";
        //定义解析标识符
        ST template = new ST(stGroup,context);
        template.add("pages",this.pages);
        this.context = template.render();
    }
    public String getContext() {
        return this.context;
    }
}
