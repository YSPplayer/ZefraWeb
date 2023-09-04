package com.zefra.pojo;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class News {
    //我们的网页地址的存放地点 CopyOnWriteArrayList线程安全
    public static CopyOnWriteArrayList<String> urls = new CopyOnWriteArrayList<String>();
    //我们的网页地址的更新的存放点
    public static CopyOnWriteArrayList<String> newUrls = new CopyOnWriteArrayList<String>();
    //我们的真正资源存放点
    public static CopyOnWriteArrayList<String> news = new CopyOnWriteArrayList<>();
}
