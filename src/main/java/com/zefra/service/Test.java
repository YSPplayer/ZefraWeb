package com.zefra.service;

import com.zefra.util.Toos;

public class Test {
    private  static  class Us {
        private  int a;
        public void US() {
        }
    }
    public static void main(String[] args) throws Exception {
//        Us us = new Us();
//        System.out.println("[" + us.getClass().getName()
//                + "]" + "[Error]ServletRequest编码无法转为UTF-8");
        boolean a = Toos.sendQQEmail("3068483309@qq.com"
        ,"mnolnjnerddndfbe","47931吓死阿大发哇@qq.com",
                "1245","网友");
        System.out.println(a);
    }
}
