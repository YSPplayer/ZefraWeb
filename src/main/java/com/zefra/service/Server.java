/*
 * 服务器数据请求的中心
 *
 * */
package com.zefra.service;

import com.zefra.util.Toos;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

/*
* 这个名称，我们后续在html的action中使用
* */
@WebServlet("/ZefraServer")
public class Server extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
        System.out.println("Server:进入post请求");
        String s1 = req.getHeader("Content-type");
        BufferedReader reader = req.getReader();
        String s2 = reader.readLine();
        System.out.println(s1);
        System.out.println(s2);
    }
}
