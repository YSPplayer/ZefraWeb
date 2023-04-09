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
        System.out.println("Filter:已进入请求");
        String mothod = ((HttpServletRequest)servletRequest).getMethod();
        System.out.println("Filter:" + mothod);
        if("POST".equals(mothod)) {
            this.doPostFilter(servletRequest,servletResponse);

        } else if("GET".equals(mothod)) {
            this.doGetFilter(servletRequest,servletResponse);
        }
        //放行
        filterChain.doFilter(servletRequest,servletResponse);

    }
    public void doPostFilter(ServletRequest req,ServletResponse resp) {
        /*这里是post请求的筛选操作*/
        //1.把我们的字符转为utf8
        try {
            req.setCharacterEncoding("UTF-8");
        } catch (Exception e){
            System.out.println("[" + this.getClass().getName()
                    + "]" + "[Error]Post-ServletRequest编码无法转为UTF-8");
        }

    }
    public void doGetFilter(ServletRequest req,ServletResponse resp) {
        /*这里是get请求的筛选操作*/
        //1.把我们的字符转为utf8
        //获取所有参数的map集合
        Map<String, String[]> parameterMap = req.getParameterMap();
        for (String key : parameterMap.keySet()) {
            //获取map的key，编码转为UTF8
            String[] values = parameterMap.get(key);
            for (String value : values) {
                try {
                    value = Toos.EncodingUTF8(value);
                } catch (Exception e) {
                    System.out.println("[" + this.getClass().getName()
                            + "]" + "[Error]Get-ServletRequest编码无法转为UTF-8");
                }
            }
            try {
                key = Toos.EncodingUTF8(key);
            } catch (Exception e) {
                System.out.println("[" + this.getClass().getName()
                        + "]" + "[Error]Get-ServletRequest编码无法转为UTF-8");
            }
        }

    }
    @Override
    public void destroy() {
    }
}
