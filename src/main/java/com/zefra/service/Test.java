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
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Calendar;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.sql.Timestamp;

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
//        SqlSession sqls = Toos.sqlSessionFactory.openSession();
//        ExceptionTextMapper mapper = sqls.getMapper(ExceptionTextMapper.class);
//        for (int i = 13; i < 30; i++) {
//            mapper.insertTableToTags(i,30,Toos.Tags.C|Toos.Tags.C_PLUS);
//        }
//        sqls.commit();
//        sqls.close();
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

        // 获取当前时间
        Date currentTime = new Date();
        // 创建 Calendar 对象
        Calendar calendar = Calendar.getInstance();
        // 设置 Calendar 对象的时间为当前时间
        calendar.setTime(currentTime);
        // 设置时区为北京时区
        TimeZone timeZone = TimeZone.getTimeZone("Asia/Shanghai");
        calendar.setTimeZone(timeZone);
        // 获取北京时间
        Date beijingTime = calendar.getTime();
        Timestamp timestamp = new Timestamp(beijingTime.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedTimestamp = sdf.format(timestamp);
        beijingTime = sdf.parse(formattedTimestamp);
        new Timestamp(beijingTime.getTime());
        System.out.println(formattedTimestamp);
    }
}
