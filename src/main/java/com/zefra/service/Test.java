package com.zefra.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zefra.mapper.AccountMapper;
import com.zefra.mapper.ExceptionTextMapper;
import com.zefra.pojo.Account;
import com.zefra.pojo.ServerMessage;
import com.zefra.util.Config;
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
    public static void main(String[] args) throws Exception {
    }
}
