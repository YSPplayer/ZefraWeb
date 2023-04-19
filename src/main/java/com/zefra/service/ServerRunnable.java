package com.zefra.service;

import java.util.function.Function;
//让线程函数带着参数传递
public class ServerRunnable implements Runnable{
    //一个参数是Object，返回值是Object的函数，参数一是参数，参数二是返回值
    private Function<Object,Object> func;
    private Object parameter;//这个是我们传入的参数
    public ServerRunnable(Function<Object, Object> func,Object parameter) {
        this.func = func;
        this.parameter = parameter;
    }
    public Object getParameter() {
        return parameter;
    }
    @Override
    public void run() {
        //运行我们的函数
        if(func != null) func.apply(parameter);
    }
}
