/*
* 放置我们开发要用到的一些工具类
* */
package com.zefra.util;

import com.alibaba.fastjson.JSON;

import javax.mail.*;
import javax.mail.internet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

public class Toos {
    private Toos(){}
    private static String sCode = "A,B,C,E,F,G,H,J,K,L,M,N,P,Q,R,S,T,W,X,Y,Z,a,b,c,d,e,f,g,h,i,j,k,m,n,p,q,r,s,t,u,v,w,x,y,z,1,2,3,4,5,6,7,8,9,0";
    public enum MsgType {
        EMAIL(0),//这个是我们的注册邮箱的信息
        SUCCESS(1),//交互成功
        ERROR(2),//交互失败
        DRAWCODE(3),//验证码
        NULL(4);//识别不了的信息我们发送这个
        private int value;
        private MsgType(int value) {
            this.value = value;
        }
        public int getValue(){
            return value;
        }

    }
    //写入我们要返回的数据
    public static void sendRespMessage(HttpServletResponse resp, Map<String,Object> msgMap) throws IOException {
        //map转string
        String msg =  JSON.toJSONString(msgMap);
        //设置请求头，防止乱码
        resp.setContentType("text/html; charset=utf-8");
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
       // return Math.random() * (max - min) + min + 1;
        return Math.random() * (max - min + 1) + min;
    }
    //获取随机整数
    public static int getRandomIntegerNumber(int min,int max) {
        return (int)(Math.random() * (max - min + 1) + min);
    }
    //把ISO-8859-1编码转为UTF-8
    public static String encodingUTF8(String key) throws UnsupportedEncodingException {
        //byte是字节，一个字节8个bit位
        byte[] bytes = key.getBytes("ISO-8859-1");
        return new String(bytes, "UTF-8");
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
}
