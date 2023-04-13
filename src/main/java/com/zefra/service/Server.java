/*
 * 服务器数据请求的中心
 *
 * */
package com.zefra.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zefra.util.Toos;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/*
* 也就是说张三访问WEB服务器，
* 服务器会生成一个张三的session对象，
* 李四去访问WEB服务器，
* 服务器会生成一个李四的session对象。
系统为每一个访问者都设立了独立的session对象，
* 用以存取数据，并且各个访问者的session对象互不干扰。
* 对于不同域名会生成不同的sessionid，也会生成不同的Serverlet
*
* 对于每次访问请求，Servlet引擎都会创建一个新的
* HttpServletRequest请求对象和一个新的HttpServletResponse响应对象
* Servlet 容器处理由多个线程产生的多个请求，
* 每个线程执行一个单一的 Servlet 实例的 service() 方法。
*
* Servlet是服务器启动时创建的唯一对象
* 每次有网页端(域名不同)请求时，会根据请求新开一个线程创建一个req个resp对象，然后
* 每一个resp自带一个session对象
* */
/*
* 这个名称，我们后续在html的action中使用
* */
@WebServlet("/ZefraServer")
public class Server extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Server:进入get请求");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Server:进入post请求");
        BufferedReader reader = req.getReader();
        String msg = reader.readLine();
        //传入的数据转为json的map对象
        Map<String,Object> jsMap = JSONObject.parseObject(msg, Map.class);
        //转为我们对应的type枚举
        Toos.MsgType msgType = null;
        try {
            msgType = Toos.MsgType.values()[(int)jsMap.get("type")];
        } catch (Exception e) {
            //如果状态获取不到说明客户端传入的参数有误，设置为Null
            msgType =  Toos.MsgType.NULL;
        }
        Map<String,Object> respMap = new HashMap<String,Object>();//发送回去的map
        /*
         *这里我们需要把用户注册的验证码信息存储到服务器session中
         * 比如说一个人开了多个网页，每个网页都用同一个邮箱注册，这是不被允许的操作
         * */
        HttpSession session = req.getSession();//获取我们的session
        switch (msgType) {
            case EMAIL://这个是传给我们的注册邮箱信息
                String email = (String)jsMap.get("email");
                String code = "";
                //生成5个0-10之间的随机数
                for (int i = 0; i < 5; i++) {
                    code += Toos.getRandomIntegerNumber(0,10);
                }
                //如果邮件发送成功
                try {
                    if(session.getAttribute(email) != null) {
                        //如果进入，说明我们已经给这个邮箱发送过一次验证码，不需重复发送
                        respMap.put("type", Toos.MsgType.ERROR.getValue());
                        respMap.put("msg", "不可以在同时间重复发送验证码哦~");
                        respMap.put("text", "");
                    } else {
                        Toos.sendQQEmail("3068483309@qq.com", "mnolnjnerddndfbe", email, code, "网友");
                        //设置验证码的数据
                        session.setAttribute(email, code);
                        respMap.put("type", Toos.MsgType.SUCCESS.getValue());
                        respMap.put("msg", "验证码发送成功拉！请去邮箱查看!");
                        respMap.put("text", "");
                    }
                } catch (Exception e) {
                    //走这边的话就表示我们的填写的邮箱信息有误
                    respMap.put("type", Toos.MsgType.ERROR.getValue());
                    respMap.put("msg", "填写的邮箱账号有误哦~请重新检查！");
                    respMap.put("text", "邮箱账号信息有误！");
                }
                break;
            case DRAWCODE://发送给我们验证码图片信息
                //获取我们的随机验证码信息
                String resCode = Toos.getRandomCode();
                //把随机验证码信息存储到session中
                session.setAttribute((String)jsMap.get("sessionId"), resCode);
                //返回我们的数据
                respMap.put("type", Toos.MsgType.SUCCESS.getValue());
                respMap.put("msg", resCode);
                break;
            default:
                respMap.put("type", Toos.MsgType.NULL.getValue());
                System.out.println("信息传输错误" + msgType);
                break;
            }
            //把我们的数据传送给网页端
            Toos.sendRespMessage(resp,respMap);

    }
}
