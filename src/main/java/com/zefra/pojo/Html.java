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

    public Object getParameter() {
        return parameter;
    }

    //这个是存储我们的数据库应该有多少页，根据传入的数据数量来决定
    //或者是html的信息
     private Object parameter;
     //一页存放12个内容
     private final int ONE_PAGE_NUMBER = 12;
    public Html(String type,Object parameter) {
        //设置我们索引页的数量
        setParameter(type,parameter);
        setContext(type);
    }
    private void setParameter(String type,Object parameter) {
        switch (type) {
            case "Exception_search":
            case "Exception":
                setPages((Integer)parameter);
                break;
            default:
                this.parameter = parameter;
                break;
        }
    }
    private void setPages(double count) {
        //向上取整
        this.parameter = ((int)Math.ceil(count / ONE_PAGE_NUMBER)) * 10;
    }
    private void setContext(String type) {
        //获取我们指定的文本内容
        String context = "";
        context = htmlsMap.get(type);
        if(context == null) context = "";
        //定义解析标识符
        ST template = new ST(stGroup,context);
        switch (type) {
            case "Exception_search":
            case "Exception":
                template.add("pages",this.parameter);
                break;
            case "Article_Iframe":
                template.add("html",this.parameter);
                break;
            default:
                break;
        }
        this.context = template.render();
    }
    public String getContext() {
        return this.context;
    }
}
