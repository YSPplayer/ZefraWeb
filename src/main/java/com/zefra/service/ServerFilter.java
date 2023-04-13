/*
* 服务器的拦截器,做一些数据的拦截和
*
* */
package com.zefra.service;

import com.zefra.util.Toos;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/*

*
*
* */
@WebFilter("/*")
public class ServerFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String mothod = ((HttpServletRequest)servletRequest).getMethod();
        if("POST".equals(mothod)) {
            System.out.println("Filter:进入post请求");
            this.doPostFilter(servletRequest,servletResponse);
        } else if("GET".equals(mothod)) {
            System.out.println("Filter:进入get请求");
            this.doGetFilter(servletRequest,servletResponse);
        }
        System.out.println("Filter:放行");
        //放行
        filterChain.doFilter(servletRequest,servletResponse);

    }
    public void doPostFilter(ServletRequest req,ServletResponse resp) throws UnsupportedEncodingException {
        /*这里是post请求的筛选操作*/
        //1.把我们的字符转为utf8
        req.setCharacterEncoding("UTF-8");

    }
    public void doGetFilter(ServletRequest req,ServletResponse resp) throws UnsupportedEncodingException {
        /*这里是get请求的筛选操作*/
        //1.把我们的字符转为utf8
        //获取所有参数的map集合
        Map<String, String[]> parameterMap = req.getParameterMap();
        for (String key : parameterMap.keySet()) {
            //获取map的key，编码转为UTF8
            String[] values = parameterMap.get(key);
            for (String value : values) {
                 value = Toos.encodingUTF8(value);
            }
            key = Toos.encodingUTF8(key);
        }

    }
    @Override
    public void destroy() {
    }
}
