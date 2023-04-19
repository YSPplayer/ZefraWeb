package com.zefra.service;

import com.zefra.util.Toos;

import javax.mail.Session;
import javax.servlet.ServletContext;
import javax.servlet.ServletResponse;
import javax.servlet.http.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class SessionListener implements HttpSessionAttributeListener,HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("用户被创建拉");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        //session被销毁(超时x秒没有session交互操作|)
        HttpSession session = se.getSession();
        //获取ServletContext，这个是全局对象
        ServletContext context = session.getServletContext();
        Hashtable<String,String> data = (Hashtable<String,String>)context.getAttribute(Toos.SessionId.UNIQUE_USER);
        try {
            data.remove(session.getAttribute(Toos.SessionId.UNIQUE_USER));//移除我们的用户
        } catch (Exception e){}
        System.out.println("用户退出了");
    }
    private void checkLogin(HttpSessionBindingEvent event) {
        //获取我们当前事件变化的session
        HttpSession session = event.getSession();
        //这里给登录的交互session设置成30分钟
        Toos.setMaxInactiveInterval(session,30);
        //value是我们存储的唯一账号名
        String key = (String) session.getAttribute(Toos.SessionId.UNIQUE_USER);
        //获取ServletContext，这个是全局对象
        ServletContext context = session.getServletContext();
        if(context.getAttribute(Toos.SessionId.UNIQUE_USER) == null) {
            //Hashtable是线程安全的，里面的方法自带锁，多线程访问带锁的方法排队进入
            context.setAttribute(Toos.SessionId.UNIQUE_USER, new Hashtable<String,String>());
        }
        Hashtable<String,String> data = (Hashtable<String,String>)context.getAttribute(Toos.SessionId.UNIQUE_USER);
        //注意，我们把里面的唯一session存储进去
        //注意hashmap的put会替换掉原来已经有的值
        DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String strdate = dateformat.format(new Date());
        String id = session.getId() + strdate;
        data.put(key,id);
        session.setAttribute(Toos.SessionId.UNIQUE_USER_FLAG,id);
    }
    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        //只要我们需要触发事件的session id
        if(Toos.SessionId.UNIQUE_USER.equals(event.getName())) {
            System.out.println("【SessionListener】进入attributeAdded");
            //session中添加属性时触发这个事件
            checkLogin(event);
        }

    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
        //session属性替换时触发这个事件
        if(Toos.SessionId.UNIQUE_USER.equals(event.getName())) {
            //进入到这里说明我们在同一个电脑上再次登录了账户
            System.out.println("【SessionListener】进入attributeReplaced");
            checkLogin(event);
        }
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
        return;//这个方法必须继承做修改，不然会用父类的方法出错
    }
}
