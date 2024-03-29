/*
 * 放置我们开发要用到的一些工具类
 * */
package com.zefra.util;

import com.alibaba.fastjson.JSON;
import com.zefra.mapper.*;
import com.zefra.pojo.ExceptionTags;
import com.zefra.pojo.Html;
import com.zefra.pojo.News;
import com.zefra.pojo.PyScript;
import com.zefra.service.ServerRunnable;
import com.zefra.service.ServerThreadPool;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import javax.mail.*;
import javax.mail.internet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.text.html.HTML;
import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Toos {
    //获取 Base64.Encoder对象
    private static final Base64.Encoder encoder = Base64.getEncoder();
    // 定义一个静态共享对象锁，确保不同的实例都可以使用同一个锁
    public static final Object lock = new Object();
    //我们的根目录
    public static String rootPath = "";
    //这个是我们的头标数据
    public static final String[] exceptionUl = {
            "ALL","C","C++","C#","Java","JavaScript","Lua","Python","Other"
    };
    public static class Mode {
        static final int Local = 0;
        static final int Server = 1;
    }
    //修改mode属性
    public static int mode = Mode.Local;
    public static boolean liunx  = false;
    public static class Tags {
        //JavaScript Python 太长的简写
        public static final String[] exceptionTags = {
                "C","C++","C#","Java","Js","Lua","Py","Other",
                "Tact","Web","Sql","Json","Css","Spring","Mbatis",
                "Dll","Zip"
                //数组索引计算:header + context - 2
                //如果header为0就是默认context
        };
        public static final long C = 0X1;
        public static final long C_PLUS = 0X2;
        public static final long C_SHARP = 0X4;
        public static final long JAVA = 0X8;
        public static final long JAVA_SCRIPT = 0X10;
        public static final long LUA = 0X20;
        public static final long PYTHON = 0X40;
        public static final long OTHER = 0X80;
        public static final long TOMACT = 0X100;
        public static final long WEB = 0X200;
        public static final long SQL = 0X400;
        public static final long JSON = 0X800;
        public static final long CSS = 0X1000;
        public static final long SPRING = 0X2000;
        public static final long MYBATIS = 0X4000;
        public static final long DLL = 0X8000;
        public static final long ZIP = 0X10000;
        public static final long MAX = ZIP;

    }
    public static final String exceptionUl_all = "ALL";
    public static final String exceptionUl_prev = "<<";
    public static final String exceptionUl_next = ">>";
    //减去左右2个箭头的数量
    public static final int exceptionUl_max = 6;
    //如果是正式非测试模式，请标记为false
    public static boolean GodMode = true;
    //博主的数据
    public static final String ChatUrl = "./pics/index/test.jpg";
    public static final String ChatName = "屑小shu";
    public static boolean musicIsInit = false;
    //这个是存储我们音频路径对象的集合，只在服务器开启的时候调用一次
    public static List<String> mp3cfreeFiles = new ArrayList<>();
    public static List<String> mp3jfreeFiles = new ArrayList<>();
    public static List<String> mp3classicsFiles = new ArrayList<>();
    //这个是我们sql的对象，只创建一个
    public static SqlSessionFactory sqlSessionFactory;
    private Toos(){}
    static {
        try {
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (Exception e) { e.printStackTrace();}
    }
    public static class SessionId {
        //设置我们的sessionid
        public static final String EMAIL = "ZEFRA_EMAIL";
        public static final String DRAWCODE = "ZEFRA_DRAWCODE";
        //这个是我们下一步之后存储的邮箱信息
        public static final String UNIQUE_EMAIL = "ZEFRA_UNIQUE_EMAIL";
        //这个是存储我们的用户信息
        public static final String UNIQUE_USER = "ZEFRA_UNIQUE_USER";
        //这个是存储我们的用户信息的flag，即是否通过检测
        public static final String UNIQUE_USER_FLAG = "ZEFRA_UNIQUE_USER_FLAG";
    }
    private static String sCode = "A,B,C,E,F,G,H,J,K,L,M,N,P,Q,R,S,T,W,X,Y,Z,a,b,c,d,e,f,g,h,i,j,k,m,n,p,q,r,s,t,u,v,w,x,y,z,1,2,3,4,5,6,7,8,9,0";
    public enum WebType {
        EMAIL(0),//这个是我们的注册邮箱的信息
        DRAWCODE(1),//验证码
        NULL(2),//无效的操作
        NEXT(3),//下一步的操作
        CODE(4),//注册码
        LOGIN(5),//注册操作
        SIGN(6),//登录操作
        CLOSEWINDOW(7),//关闭浏览器
        ACTIVECONNECT(8),//主动握手，便于服务器主动发送请求给网页
        REPEATLOGIN(9),//网页端重复登录
        NOOPERATE(10),//网页端长时间没有操作
        PASSWORD(11),//登录密码
        PLAYMUSIC(12),//播放音乐
        INDEXCONTEXT(13),//索引内容
        HEADERINDEX(14),//头标签的导航
        POSTTITLE(15),//上传我们的文章
        DELETEIMG(16),//删除服务器上的图片
        GETARTICLE(17),//删除服务器上的图片
        DELETETITLE(18),//删除服务器上的图片
        UPDATETITLE(19),//更新文章的请求
        POSTUPDATETITLE(20),//更新我们的文章
        HEADERSEARCH(21),//导航栏索引按钮
        GETBOOK(22),//更新我们的文章
        SEARCHBOOK(23),//获取指定页面的文章
        SAVECHAT(24),//保存我们的动态
        DELETECHAT(25),//删除我们的动态
        GETCHAT(26),//获取我们的动态
        GETNEWS(27);//获取我们的新闻
        private int value;
        private WebType(int value) {
            this.value = value;
        }
        public int getValue(){
            return value;
        }
    }
    public enum ServerType {
        SUCCESS(0),//交互成功
        ERROR(1),//交互失败
        NULL(2),//识别不了的信息我们发送这个
        RETRY(3);//让客户端重试
        private int value;
        private ServerType(int value) {
            this.value = value;
        }
        public int getValue(){
            return value;
        }

    }
    public static String GetNowDate() {
        // 获取当前日期
        LocalDate currentDate = LocalDate.now();
        // 格式化日期为字符串
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");
        return currentDate.format(formatter);
    }
    //获取当前时间
    public static Timestamp GetNowlTime() throws ParseException {
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
        return new Timestamp(beijingTime.getTime());
    }
    private static void getNews() {
        ServerThreadPool.executor.execute(()->{
            for (int i = 0; i < News.urls.size(); i++) {
                //读取脚本并存放
                PyScript.getNews(News.urls.get(i));
            }
            System.out.println("异步线程新闻数据存储成功！");
            String formattedDate = GetNowDate();
            while (true) {
                //对新闻进行实时更新的检测
                int index = 0;
                try {
                    //休眠一个小时检查一次
                    long time = 600000 * 6;
                    Thread.sleep(time);
                    //如果不是今天，明天
                    if(!formattedDate.equals(GetNowDate())) {
                        //重置所有新闻
                        News.urls.clear();
                        News.news.clear();
                    }
                    //重新加载新闻
                    if(PyScript.updateNews() && News.newUrls.size() > 0) {
                        for (int i = 0; i < News.newUrls.size(); i++) {
                            //读取脚本并存放
                            PyScript.getNews(News.newUrls.get(i));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }
    //备份我们的数据库
    private static void saveSql() {
        ServerThreadPool.executor.execute(()->{
            while (true) {
                String key = liunx ? "data/backups.sql" : "data\\backups.sql";
                String command = liunx ? "mysqldump -uroot -p5341115YSP200119, zefraweb > " + rootPath +  key : "mysqldump -uroot -phsp zefraweb > " + rootPath +  key;
                try {
                    //每休眠10分钟保存一次数据库
                    Thread.sleep(600000);
                    Process process = liunx ?
                                    Runtime.getRuntime().exec(new String[]{"sh", "-c", command}):
                                    Runtime.getRuntime().exec("cmd /c " + command);
                    int exitCode = process.waitFor();
                    if (exitCode == 0) {
                        System.out.println("数据库保存成功！");
                    } else {
                        System.err.println("数据库保存失败，请检查命令和权限！");
                    }
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    //初始化我们的一些需要的工具
    public static boolean init() {
        String tempPath = System.getProperty("user.dir");
        String parentPath  = "";
        if(mode == Mode.Server) {
            File file = new File(tempPath);
            parentPath = file.getParent();
            rootPath = liunx ? parentPath + "/webapps/ZefraWeb/" : parentPath + "\\webapps\\ZefraWeb\\";
        } else {
            //我们的根目录
            rootPath = tempPath + "\\src\\main\\webapp\\";
        }
        String fPath = tempPath;
        if(mode == Mode.Server) {
            fPath = liunx ? rootPath + "/WEB-INF/classes/data.properties" : rootPath + "\\WEB-INF\\classes\\data.properties";
        } else {
            //我们的根目录
            fPath = tempPath + "\\src\\main\\resources\\data.properties";
        }
        //初始化配置文件
        if(!Config.init(fPath)) return false;
        if(mode == Mode.Server) {
//            fPath = parentPath + "\\webapps\\news\\";
            fPath = Config.ReadConfig("npath");
        } else {
            fPath = tempPath + "\\news\\";
        }
        //初始化爬虫脚本
        boolean newsSuccess = PyScript.init(fPath);
        //boolean newsSuccess = false;
        //创建一个线程每隔一段时间调用一次来保存我们的sql
        saveSql();
        //创建一个线程来爬取新闻脚本
        if(newsSuccess) getNews();
        //配置模式
        GodMode = "true".equals(Config.ReadConfig("godMode")) ? true : false;
        //html数据
        return loadHtml();
    }
    private static boolean loadHtml() {
        String path = rootPath + "htxt";
        //初始化html中的容器
        File dir = new File(path);
        //获取目录中所有文件的File对象
        final File[] files = dir.listFiles();
        // 遍历所有文件，将所有以".txt"结尾的文件名添加到集合中
        path += liunx ? "/" : "\\";
        for (final File file : files) {
            if (file.getName().endsWith(".txt")) {
                String content = "";
                try {
                    content = readString(path + file.getName());  // 读取文件内容
                } catch (Exception e) {
                    System.out.println("读取txt文件失败");
                    return false;
                }
                Html.htmlsMap.put(getFileNameWithoutExtension(file),content);
            }
        }
        System.out.println("html数据库加载成功！");
        return true;
    }
    //获取文件的类型名
    public static String getFileNameWithoutExtension(File file) {
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf(".");
        return (dotIndex == -1) ? fileName : fileName.substring(0, dotIndex);
    }
    //把指定路径的文件读取成字符串并返回
    public static String readString(String path) throws Exception {
        String content = "";
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        content = new String(bytes, StandardCharsets.UTF_8);
        return content;
    }
    //转义一下
    public static String getSvalue(String svalue) {
        if("JavaScript".equals(svalue)) return "Js";
        if("Python".equals(svalue)) return "Py";
        if("Tomact".equals(svalue)) return "Tact";
        if("Mybatis".equals(svalue)) return "Mbatis";
        return svalue;
    }
    public static String getExceptionUlTags(long tag) {
        StringBuilder resTag = new StringBuilder("");
        int j = Tags.exceptionTags.length - 1;
        for (long i = Tags.MAX;i != 0; i >>= 1,--j) {
            if((tag & i) > 0) {
                resTag.append(Tags.exceptionTags[j]);
                resTag.append(",");
            }
        }
        //移除最后一个,字符
        if(resTag.length() > 0)  resTag.deleteCharAt(resTag.length() - 1);
        return resTag.toString();
    }
    public static List<String> getExceptionUlTags(List<ExceptionTags> exceptionTags) {
        List<String> tagsList = new ArrayList<>();
        for (ExceptionTags exceptionTag : exceptionTags) {
            // 循环体语句
            tagsList.add(getExceptionUlTags(exceptionTag.getTags()));
        }
        return tagsList;
    }
    public static <T>Class<T> getMapperClass(String type) {
        switch (type)
        {
            case "Exception":
                return (Class<T>)ExceptionTextMapper.class;
            case "Course":
                return (Class<T>) CourseTextMapper.class;
            case "Tool":
                return (Class<T>) ToolTextMapper.class;
            case "Trends":
                return (Class<T>) ChatMapper.class;
            default:
                return null;
        }
    }
    public static void setExceptionUlSTags(List<ExceptionTags> exceptionTags) {
        for (ExceptionTags exceptionTag : exceptionTags)
            exceptionTag.setStags(getExceptionUlTags(exceptionTag.getTags()));
    }
    public static long getBitExceptionUlSTags(String value) {
        long index = -1;
        long key  = -1;
        for(String stag : Tags.exceptionTags) {
            ++index;
            if(stag == null) continue;
            if((stag.toLowerCase()).equals(value.toLowerCase())) {key = index; break;}
        }
        if(key < 0) return 0;
        return (1 << key);
    }
    public static boolean isContainValue(String[] arr,String vaule) {
        for (String element: arr) {
            if(element == null) continue;
            if(element.equals(vaule)) return true;
        }
        return false;
    }
    //检查客户端传入的字符是否为空
    public static String CheckWebParameter(HttpServletRequest req,String key,Map<String,Object> respMap) throws UnsupportedEncodingException {
        String value = "";
        if((value = encodingUTF8(req.getParameter(key))) == null) {
            if(!respMap.containsKey("type")) {
                respMap.put("type", Toos.ServerType.ERROR.getValue());
            }
            if(!respMap.containsKey("msg")) {
                respMap.put("msg", "客户端发送的value信息有误！");
            }
        }
        return value;
    }
    public static String CheckWebParameter(Map<String,Object> reqMap,String key,Map<String,Object> respMap) throws UnsupportedEncodingException {
        String value = "";
        if((value = encodingUTF8((String) reqMap.get(key))) == null) {
            respMap.put("type", Toos.ServerType.ERROR.getValue());
            respMap.put("msg", "客户端发送的value信息有误！");
        }
       // System.out.println(value);
        return value;
    }
    //加载我们的音频文件到数组中
    public static void initMusic(String path,List<String> list) {
        File dir = new File(path);
        // 获取目录中所有文件的File对象
        final File[] files = dir.listFiles();
        // 遍历所有文件，将所有以".mp3"结尾的文件名添加到集合中
        for (final File file : files) {
            if (file.getName().endsWith(".mp3")) {
                list.add(file.getName());
            }
        }
    }
    public static void sendWebNullMsg(Map<String,Object> respMap) {
        respMap.put("type", Toos.ServerType.NULL.getValue());
    }
    //获取我们的html文本，传输到web端
    public static String getHtml(String type,Object parameter) {
        com.zefra.pojo.Html html = new Html(type,parameter);
        return html.getContext();
    }
    //获取我们的html文本，传输到web端
    public static String getHtml(String type) {
        return getHtml(type,null);
    }
    //设置session的生效时间，这里用分钟为单位
    public static void setMaxInactiveInterval(HttpSession session,int time) {
        session.setMaxInactiveInterval(60 * time);
    }
    //写入我们要返回的数据
    public static void sendRespMessage(HttpServletResponse resp, Map<String,Object> msgMap) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        sendRespMessage(resp,msgMap,"","","");
        return;
    }
    public static void sendRespMessage(HttpServletResponse resp, Map<String,Object> msgMap,String ctype,String cheaderKey,String cheaderValue) throws IOException {
        //map转string
        String msg =  JSON.toJSONString(msgMap);
        //设置请求头，防止乱码
        if(ctype != "") resp.setContentType(ctype);
        if(cheaderKey !="") resp.setHeader(cheaderKey,cheaderValue);
        PrintWriter writer = resp.getWriter();
        writer.write(msg);
        return;
    }
    public static String getHeaderList(int index) {
        //过滤掉超出范围的索引，注意减1是因为我们有"<<和>>"
        if(index > exceptionUl.length - exceptionUl_max || index < 0) return null;
        List<String> exceptionUlList = new ArrayList<>();
        if(index > 0) exceptionUlList.add(exceptionUl_prev);
        for (int i = index; i< exceptionUl.length ; ++i) {
            if(exceptionUlList.size() >= 7) break;//只添加7个元素进去
            exceptionUlList.add(exceptionUl[i]);
        }
        exceptionUlList.add(exceptionUl_next);
        //返回Json对象的字符
        return JSON.toJSONString(exceptionUlList);
    }
    //获取到随机的验证码
    public static String getRandomCode() {
        String[] aCode = sCode.split(",");
        String resCode = "";//存放最终二维码结果的数组
        int index = -1;
        for (int i = 0;i< 4;++i) {
            index = getRandomIntegerNumber(0,aCode.length);
            if(index < 0 || index >= aCode.length) index = 0;
            resCode += aCode[index];
            //拼接字符，用,隔开
            if(i < 3) resCode += ",";
        }
        return resCode;
    }
    //获取随机数
    public static double getRandomNumber(int min,int max) {
        return Math.random() * (max - min + 1) + min;
    }
    //获取随机整数
    public static int getRandomIntegerNumber(int min,int max) {
        return (int)(Math.random() * (max - min + 1) + min);
    }
    //把sting转成Base64，防止html类型的文本转成json错误
    public static String encodingBase64(String key)  {
        // 将字符串编码为 Base64 字符串
        return encoder.encodeToString(key.getBytes(StandardCharsets.UTF_8));
    }
    //把ISO-8859-1编码转为UTF-8
    public static String encodingUTF8(String key) throws UnsupportedEncodingException {
        //byte是字节，一个字节8个bit位
        if(key == null) return null;
        byte[] bytes = key.getBytes("ISO-8859-1");
        return new String(bytes, "UTF-8");
       // return URLDecoder.decode(key, "UTF-8");
    }

    /**
     * 发送邮件(参数自己根据自己的需求来修改，发送短信验证码可以直接套用这个模板)
     *
     * @param from_email 发送人的邮箱
     * @param pwd        发送人的授权码
     * @param recevices  接收人的邮箱
     * @param code       验证码
     * @param name       收件人的姓名
     * @return
     */
    public static boolean sendQQEmail(String from_email, String pwd, String recevices, String code, String name) throws Exception {
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");     //使用smpt的邮件传输协议
        props.setProperty("mail.smtp.host", "smtp.qq.com");       //主机地址
        props.setProperty("mail.smtp.auth", "true");      //授权通过
        Session session = Session.getInstance(props);     //通过我们的这些配置，得到一个会话程序
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from_email));     //设置发件人
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(recevices, "用户", "utf-8"));      //设置收件人
        message.setSubject("演示发邮件验证码网", "utf-8");      //设置主题
        message.setSentDate(new Date());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        String str = "<!DOCTYPE html><html><head><meta charset='UTF-8'></head><body><p style='font-size: 20px;font-weight:bold;'>尊敬的" + name + "，您好！</p>"
                + "<p style='text-indent:2em; font-size: 20px;'>欢迎注册屑小shu的个人网站，您本次的注册码是 "
                + "<span style='font-size:30px;font-weight:bold;color:red'>" + code + "</span>，1分钟之内有效，请尽快使用！</p>"
                + "<p style='text-align:right; padding-right: 20px;'"
                + "<a href='http://www.hyycinfo.com' style='font-size: 18px'>xxxx有限公司</a></p>"
                + "<span style='font-size: 18px; float:right; margin-right: 60px;'>" + sdf.format(new Date()) + "</span></body></html>";

        Multipart mul = new MimeMultipart();  //新建一个MimeMultipart对象来存放多个BodyPart对象
        BodyPart mdp = new MimeBodyPart();  //新建一个存放信件内容的BodyPart对象
        mdp.setContent(str, "text/html;charset=utf-8");
        mul.addBodyPart(mdp);  //将含有信件内容的BodyPart加入到MimeMultipart对象中
        message.setContent(mul); //把mul作为消息内容
        message.saveChanges();
        //创建一个传输对象
        Transport transport = session.getTransport("smtp");
        //建立与服务器的链接  465端口是 SSL传输
        transport.connect("smtp.qq.com", 587, from_email, pwd);
        //发送邮件
        transport.sendMessage(message, message.getAllRecipients());
        //关闭邮件传输
        transport.close();
        return true;
    }
    //密码加密
    public static String encodePass(String rawPassword) {
        // 加密过程
        // 1. 使用MD5算法
        // 2. 使用随机的盐值
        // 3. 循环5次
        // 4. 盐的处理方式为：盐 + 原密码 + 盐 + 原密码 + 盐
        // 注意：因为使用了随机盐，盐值必须被记录下来，本次的返回结果使用$分隔盐与密文
        String salt = UUID.randomUUID().toString().replace("-", "");
        String encodedPassword = rawPassword;
        for (int i = 0; i < 5; i++) {
            //md5DigestAsHex
            encodedPassword = DigestUtils.md5Hex(
                    (salt + encodedPassword + salt + encodedPassword + salt).getBytes());
        }
        return salt + encodedPassword;
    }
    //密码解密
    public static boolean matches(String rawPassword, String encodedPassword) {
        String salt = encodedPassword.substring(0, 32);
        String newPassword = rawPassword;
        for (int i = 0; i < 5; i++) {
            newPassword = DigestUtils.md5Hex(
                    (salt + newPassword + salt + newPassword + salt).getBytes());
        }
        newPassword = salt + newPassword;
        return newPassword.equals(encodedPassword);
    }
}