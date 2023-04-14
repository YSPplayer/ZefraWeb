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
        Map<String,Object> jsMap = null;
        //转为我们对应的type枚举
        Toos.WebType msgType = null;
        try {
            //传入的数据转为json的map对象
            jsMap = JSONObject.parseObject(msg, Map.class);
            msgType = Toos.WebType.values()[(int)jsMap.get("type")];
        } catch (Exception e) {
            //如果状态获取不到说明客户端传入的参数有误，设置为Null
            msgType = Toos.WebType.NULL;
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
                    code += Toos.getRandomIntegerNumber(0,9);
                }
                //如果邮件发送成功
                try {
                    Toos.sendQQEmail("3068483309@qq.com", "mnolnjnerddndfbe", email, code, "网友");
                    //设置验证码的数据，一个是账号，一个是验证码
                    session.setAttribute(Toos.SessionId.EMAIL, email + "," + code);
                    respMap.put("type", Toos.ServerType.SUCCESS.getValue());
                    respMap.put("msg", "验证码发送成功拉！请去邮箱查看!");
                    respMap.put("text", "");
                } catch (Exception e) {
                    //走这边的话就表示我们的填写的邮箱信息有误
                    respMap.put("type", Toos.ServerType.ERROR.getValue());
                    respMap.put("msg", "填写的邮箱账号有误哦~请重新检查！");
                    respMap.put("text", "邮箱账号信息有误！");
                }
                break;
            case DRAWCODE://发送给我们验证码图片信息
                //获取我们的随机验证码信息
                String resCode = Toos.getRandomCode();
                //把随机验证码信息存储到session中
                String sessionCode = resCode.replace(",","");
                session.setAttribute(Toos.SessionId.DRAWCODE,sessionCode);
                //返回我们的数据
                respMap.put("type", Toos.ServerType.SUCCESS.getValue());
                respMap.put("msg", resCode);
                break;
            case NEXT:
                String post = (String)jsMap.get("post");//邮箱
                String postcode = (String)jsMap.get("code");//注册码
                String imgCode = (String)jsMap.get("imgCode");//图片验证码
                //先获取存储在我们缓存里面的数据
                String sPost="";//邮箱
                String sPostcode="";//注册码
                String sImgCode="";//图片验证码
                if(session.getAttribute(Toos.SessionId.EMAIL) == null) {
                    //我们的注册码过期或未发送
                    respMap.put("type", Toos.ServerType.ERROR.getValue());
                    respMap.put("webType", Toos.WebType.CODE.getValue());
                    respMap.put("msg", "注册码过期或还未发送拉~");
                    respMap.put("text", "注册码过期或未发送，请重新验证！");
                    break;
                } else {
                    String sessionPost = (String)session.getAttribute(Toos.SessionId.EMAIL);
                    String[] postArr = sessionPost.split(",");
                    sPost = postArr[0];
                    sPostcode = postArr[1];
                }
                if(session.getAttribute(Toos.SessionId.DRAWCODE) == null) {
                    //我们的验证码过期或未发送
                    respMap.put("type", Toos.ServerType.ERROR.getValue());
                    respMap.put("webType", Toos.WebType.DRAWCODE.getValue());
                    respMap.put("msg", "验证码过期或还未发送拉~");
                    respMap.put("text", "验证码过期或未发送，请重新验证！");
                    break;
                } else {
                    sImgCode = (String)session.getAttribute(Toos.SessionId.DRAWCODE);
                }
                if(!sPost.equals(post)  ||  !sPostcode.equals(postcode)) {
                    //这个是我们的邮箱输入错误
                    respMap.put("type", Toos.ServerType.ERROR.getValue());
                    respMap.put("webType", Toos.WebType.EMAIL.getValue());
                    respMap.put("msg", "邮箱和注册码不匹配哦~");
                    respMap.put("text", "邮箱和注册码不匹配！");
                    break;
                } else if(!sImgCode.equalsIgnoreCase(imgCode)) {
                    //equalsIgnoreCase忽略字符串大小写进行比较
                    //验证码有误
                    respMap.put("type", Toos.ServerType.ERROR.getValue());
                    respMap.put("webType", Toos.WebType.DRAWCODE.getValue());
                    respMap.put("msg", "输入的验证码有误哦~请重新验证");
                    respMap.put("text", "验证码输入有误！");
                    break;
                } else {
                    //成功 下一步
                    respMap.put("type", Toos.ServerType.SUCCESS.getValue());
                }
                break;
            default:
                respMap.put("type", Toos.ServerType.NULL.getValue());
                System.out.println("信息传输错误" + msgType);
                break;
            }
            //把我们的数据传送给网页端
            Toos.sendRespMessage(resp,respMap);

    }
}
