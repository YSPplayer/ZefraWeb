/*
* 放置我们开发要用到的一些工具类
* */
package com.zefra.util;

import com.alibaba.fastjson.JSON;
import com.zefra.mapper.AccountMapper;
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
import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

public class Toos {
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
        } catch (Exception e) {}

    }
    public static class SessionId {
        //设置我们的sessionid
        public static String EMAIL = "ZEFRA_EMAIL";
        public static String DRAWCODE = "ZEFRA_DRAWCODE";
        //这个是我们下一步之后存储的邮箱信息
        public static String UNIQUE_EMAIL = "ZEFRA_UNIQUE_EMAIL";
        //这个是存储我们的用户信息
        public static String UNIQUE_USER = "ZEFRA_UNIQUE_USER";
        //这个是存储我们的用户信息的flag，即是否通过检测
        public static String UNIQUE_USER_FLAG = "ZEFRA_UNIQUE_USER_FLAG";
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
        INDEXCONTEXT(13);//索引内容
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
    //检查客户端传入的字符是否为空
    public static String CheckWebParameter(HttpServletRequest req,String key,Map<String,Object> respMap) {
        String value = "";
        if((value = req.getParameter(key)) == null) {
            respMap.put("type", Toos.ServerType.ERROR.getValue());
            respMap.put("msg", "客户端发送的value信息有误！");
        }
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
    //把ISO-8859-1编码转为UTF-8
    public static String encodingUTF8(String key) throws UnsupportedEncodingException {
        //byte是字节，一个字节8个bit位
//        byte[] bytes = key.getBytes("ISO-8859-1");
//        return new String(bytes, "UTF-8");
      return URLDecoder.decode(key, "UTF-8");
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
