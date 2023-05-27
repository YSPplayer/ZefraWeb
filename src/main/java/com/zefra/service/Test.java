package com.zefra.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zefra.mapper.AccountMapper;
import com.zefra.mapper.ExceptionTextMapper;
import com.zefra.pojo.Account;
import com.zefra.pojo.ServerMessage;
import com.zefra.util.Toos;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.servlet.ServletContext;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

public class Test {
    private static class People{
        public int age;
        public People(int age) {
            this.age = age;
        }
    }
    public static void doWork() throws IOException {
        SqlSession sqls = Toos.sqlSessionFactory.openSession();
        ExceptionTextMapper mapper = sqls.getMapper(ExceptionTextMapper.class);
        for (int i = 13; i < 30; i++) {
            mapper.insertTableToTags(i,30,Toos.Tags.C|Toos.Tags.C_PLUS);
        }
        sqls.commit();
        sqls.close();
    }
    public static String readString(String path) {
        String content = "";
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(path));
            content = new String(bytes, StandardCharsets.UTF_8);
        } catch (Exception e){}
        return content;
    }
    public static void do2() {
        String path = System.getProperty("user.dir") + "\\src\\main\\webapp\\htxt\\temp.txt";
        try {
            String content = readString(path);  // 读取文件内容
            STGroup group = new STGroup('¥', '¥'); //定义解析标识符
            ST template = new ST(group,content);
            int vale = 1;
            template.add("name", vale);
           // template.add("city", "Beijing");
            String result = template.render();
            System.out.println(result);  // 输出结果
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws Exception {
        do2();
    }
}
