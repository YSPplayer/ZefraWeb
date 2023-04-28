package com.zefra.service;

import com.zefra.pojo.ServerMessage;
import com.zefra.util.Toos;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.servlet.ServletContext;
import java.io.*;
import java.util.Hashtable;

public class Test {
    private static class People{
        public int age;
        public People(int age) {
            this.age = age;
        }
    }
    public static void changeName(String path,String value) {
        File folder = new File(path);
        File[] files = folder.listFiles();
        int index = 0;
        for (File file : files) {
            if (file.isFile()  && file.getName().endsWith(value)) { // 判断是否为 MP3 格式的文件
                String oldName = file.getName();
                String newName = "music" + Integer.toString(index)+".mp3"; // 新文件名前缀为 "my-"
                File newFile = new File(folder, newName);
                boolean success = file.renameTo(newFile); // 重命名该文件
                if (success) {
                    System.out.println("Rename successfully: " + oldName + " -> " + newName);
                } else {
                    System.out.println("Failed to rename: " + oldName);
                }
                ++index;
            }
        }
    }
    public static void main(String[] args) throws Exception {
        changeName("D:\\ZefraWeb\\src\\main\\webapp\\music\\jfree",".mp3");
    }
}
