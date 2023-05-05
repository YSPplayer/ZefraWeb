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
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

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
    public static void main(String[] args) throws Exception {
        //doWork()
        Toos.getExceptionUlTags(3);
    }
}
