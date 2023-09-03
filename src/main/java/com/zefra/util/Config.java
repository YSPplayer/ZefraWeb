package com.zefra.util;
import java.io.FileInputStream;
import java.util.Properties;

public class Config {
    private static String configPath = "";
   private static Properties properties = new Properties();
   public static boolean init(String path) {
       configPath = path;
       //初始化我们的properties
       try (FileInputStream fileInputStream = new FileInputStream(configPath)) {
           properties.load(fileInputStream);
           System.out.println("初始化配置文件成功！");
           return true;
       } catch (Exception e) {
           e.printStackTrace();
           System.out.println("初始化配置文件失败！");
           return false;
       }
   }
   public static String ReadConfig(String key) {
       return properties.getProperty("database." + key);
   }
}
