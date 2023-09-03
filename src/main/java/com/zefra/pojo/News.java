package com.zefra.pojo;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class News {
    //创建唯一锁
    public static ReentrantLock news_lock = new ReentrantLock();
    //我们的网页地址的存放地点
    public static ArrayList<String> urls = new ArrayList<String>();
    //我们的网页地址的更新的存放点
    public static ArrayList<String> newUrls = new ArrayList<String>();
    //我们的真正资源存放点
    public static ArrayList<String> news = new ArrayList<>();
}
