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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

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
        Toos.MsgType msgType = Toos.MsgType.values()[(int)jsMap.get("type")];
        switch (msgType) {
            case EMAIL://这个是传给我们的注册邮箱信息
                String email = (String)jsMap.get("email");
                String code = "";
                //生成5个0-10之间的随机数
                for (int i = 0; i < 5; i++) {
                    code += Toos.getRandomIntegerNumber(0,10);
                }
                Map<String,Object> respMap = new HashMap<String,Object>();
                //如果邮件发送成功
                try {
                    Toos.sendQQEmail("3068483309@qq.com", "mnolnjnerddndfbe", email, code, "网友");
                    //.ordinal()把我们的枚举转为int
                    respMap.put("type", Toos.MsgType.SUCCESS.getValue());
                } catch (Exception e) {
                    //走这边的话就表示我们的填写的邮箱信息有误
                    respMap.put("type", Toos.MsgType.ERROR.getValue());
                }
                //map转string
                String jsrespMap= JSON.toJSONString(respMap);
                //把我们的数据传送给网页端
                //设置请求头，防止乱码
                resp.setContentType("text/html; charset=utf-8");
                PrintWriter writer = resp.getWriter();
                writer.write(jsrespMap);
                break;
            default:
                System.out.println("信息传输错误" + msgType);
                break;
            }

    }
}
