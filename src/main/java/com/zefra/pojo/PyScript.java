package com.zefra.pojo;

import com.zefra.util.Config;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.function.Function;

//加载python的进程，爬取新闻
public class PyScript {
    private static String nPath;
    //初始化py脚本数据
    public static boolean init(String path){
        nPath = path;
        return doPyScript(path,"init.py", "",(output)->{
            Collections.addAll(News.urls, ((StringBuilder)output).toString().split(","));
            return null;
        });
    }
    public static boolean updateNews() {
        return doPyScript(nPath,"init.py","", (output)->{
            News.newUrls.clear();
            String[] urls = ((StringBuilder)output).toString().split(",");
            for (int i = 0; i < urls.length; i++) {
                if(!News.urls.contains(urls[i])) {
                    //不包含就加载进去
                    News.newUrls.add(urls[i]);
                    News.urls.add(urls[i]);
                }
            }
            return null;
        });
    }
    public static boolean getNews(String arg) {
        return doPyScript(nPath,"news.py",arg, (output)->{
            News.news.add(((StringBuilder)output).toString());
            return null;
        });
    }
    //每隔时间段更新地址
    private static boolean doPyScript(String path,String pyName,String arg,Function<Object,Object> func) {
        String pyUrl =  Config.ReadConfig("pyUrl");
        String exeUrl = path + pyName;
        // 创建ProcessBuilder对象，传入要执行的命令和参数
        ProcessBuilder pd = createProcess(pyUrl,exeUrl,arg);
        // 启动进程并获取它的输出流
        try {
            doProcess(pd,func);
            return true;
        } catch (Exception e) {
            System.out.println("初始化爬虫脚本失败！");
            return false;
        }

    }
    private static void doProcess(ProcessBuilder pd, Function<Object,Object> func) throws Exception {
        Process process = pd.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(),"utf-8"));
        //读取进程输出结果
        StringBuilder output = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }
        // 等待进程执行完毕
        int exitCode = process.waitFor();
        if (exitCode == 0) {
            System.out.println("初始化爬虫脚本成功！");
            //把连接保存下来
            func.apply(output);
        }
//        else {
//           System.err.println("初始化爬虫脚本失败！无法获取进程信息！");
//        }

    }
    private static ProcessBuilder createProcess(String pyUrl,String exeUrl,String arg) {
        // 创建ProcessBuilder对象，传入要执行的命令和参数
        ProcessBuilder pb = new ProcessBuilder(pyUrl, exeUrl,arg);
        // 设置进程的字符编为UTF-8
        pb.redirectErrorStream(true);
        pb.environment().put("PYTHONIOENCODING", "utf-8");
        return pb;
    }
}
