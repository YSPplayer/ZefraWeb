/*
 * 服务器数据请求的中心
 *
 * */
package com.zefra.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zefra.mapper.AccountMapper;
import com.zefra.mapper.ExceptionTextMapper;
import com.zefra.pojo.*;
import com.zefra.util.Toos;
import org.apache.ibatis.jdbc.Null;
import org.apache.ibatis.session.SqlSession;

import javax.mail.Session;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

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
    public void init() throws ServletException {
    }

    @Override
    public void destroy() {
        this.getServletContext().removeAttribute(Toos.SessionId.UNIQUE_USER);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Server:进入get请求");
        //这里我们初始化我们的代码
        // 获取 name 参数
        String stype = req.getParameter("type");
        Map<String,Object> respMap = new HashMap<String,Object>();//发送回去的map
        Toos.WebType msgType = null;
        SqlSession sqls = null;
        if(stype != null) {
            try {
                int index = Integer.parseInt(stype);
                msgType = Toos.WebType.values()[index];
            } catch (Exception e) {
                respMap.put("type", Toos.ServerType.ERROR.getValue());
                respMap.put("msg", "客户端发送的value信息有误！");
                return;
            }
            switch (msgType) {
                case PLAYMUSIC: {
                    // 构建一个File对象，表示音乐目录的位置
                    if(!Toos.musicIsInit) {
                        Toos.musicIsInit = true;
                        String realPath = getServletContext().getRealPath("/");
                        Toos.initMusic(realPath + "music/cfree", Toos.mp3cfreeFiles);
                        Toos.initMusic(realPath + "music/jfree", Toos.mp3jfreeFiles);
                        Toos.initMusic(realPath + "music/classics", Toos.mp3classicsFiles);
                    }
                    String sIndex = Toos.CheckWebParameter(req,"type",respMap);
                    if(sIndex == null) return;
                    int oldIndex = -1;
                    try {
                        oldIndex = Integer.parseInt(sIndex);
                    } catch (Exception e) {
                        respMap.put("type", Toos.ServerType.ERROR.getValue());
                        respMap.put("msg", "客户端发送的value信息有误！");
                        return;
                    }
                    List<String> fileNames = Toos.mp3classicsFiles;
                    int index = -1;
                    while (true) {
                        index = Toos.getRandomIntegerNumber(0,fileNames.size());
                        if(index == oldIndex) continue;
                        break;
                    }
                    String mp3Name = "";
                    if(index <= 0 || index >= fileNames.size()) index = 0;
                    mp3Name = "./music/classics/" + fileNames.get(index);
                    respMap.put("type", Toos.ServerType.SUCCESS.getValue());
                    respMap.put("msg",mp3Name);
                    respMap.put("index",index);
                }
                break;
                //获取索引
                case INDEXCONTEXT:{
                    String value  = Toos.CheckWebParameter(req,"value",respMap);
                    if(value == null) return;
                    switch (value) {
                        //异常索引
                        case "Exception":{
                            final int first_index = 0;
                            //查询数据库
                            sqls = Toos.sqlSessionFactory.openSession();
                            ExceptionTextMapper mapper = sqls.getMapper(ExceptionTextMapper.class);
                            //获取数据库存放的文章数据
                            List<String> exceptionTitle =  mapper.selectTitleInEtitle();
                            //获取到所有标签
                            List<ExceptionTags> exceptionTags = mapper.selectAllInTags();
                            //标签转义
                            Toos.setExceptionUlSTags(exceptionTags);
                            //把我们的对象数据转成json
                            String titles = JSON.toJSONString(exceptionTitle);
                            //这是我们的tags对象
                            String tags = JSON.toJSONString(exceptionTags);
                            //下面把我们的数据返回
                            respMap.put("type", Toos.ServerType.SUCCESS.getValue());
                            //我们的第一个索引
                            respMap.put("msg_header_index",first_index);
                            respMap.put("msg_title_index",first_index);
                            respMap.put("msg_header",Toos.getHeaderList(first_index));
                            respMap.put("msg_title",titles);
                            respMap.put("msg_tags",tags);
                            respMap.put("html",Toos.getHtml(value));
                            //关闭数据库
                            sqls.close();
                            break;
                        }
                        default:
                            break;
                    }
                }
                break;
                //导航栏索引
                case HEADERINDEX: {
                    String svalue = Toos.CheckWebParameter(req,"value",respMap);
                    String sindex = Toos.CheckWebParameter(req,"index",respMap);
                    int index = 0;
                    if(svalue == null || sindex == null) return;
                    //下一页
                    if(svalue.equals(Toos.exceptionUl_next)) {
                        try {
                            //inde为0时我们前进2
                            index = Integer.parseInt(sindex) + 1;
                            if(index == 1) ++index;
                            System.out.println(sindex);
                            System.out.println(index);
                            String headerData = Toos.getHeaderList(index);
                            //为空就是我们的索引到最大值了
                            if(headerData == null) {
                                respMap.put("type", Toos.ServerType.RETRY.getValue());
                                respMap.put("msg", "后面已经没有拉~(*^ω^*)");
                                break;
                            } else {
                                respMap.put("type", Toos.ServerType.SUCCESS.getValue());
                                respMap.put("arrow",true);
                                respMap.put("msg_header_index",index);
                                respMap.put("msg_header", headerData);
                                break;
                            }

                        } catch (Exception e) {
                            respMap.put("type", Toos.ServerType.ERROR.getValue());
                            respMap.put("msg", "客户端发送的value信息有误！");
                            break;
                        }
                    }
                    //上一页
                    else if(svalue.equals(Toos.exceptionUl_prev)) {
                        try {
                            index = Integer.parseInt(sindex) - 1;
                            if(index == 1) --index;
                            String headerData = Toos.getHeaderList(index);
                            //为空就是我们的索引到最大值了
                            if(headerData == null) {
                                respMap.put("type", Toos.ServerType.RETRY.getValue());
                                respMap.put("msg", "前面已经没有拉~(*^ω^*)");
                                break;
                            } else {
                                respMap.put("type", Toos.ServerType.SUCCESS.getValue());
                                respMap.put("arrow",true);
                                respMap.put("msg_header_index",index);
                                respMap.put("msg_header", headerData);
                                break;
                            }

                        } catch (Exception e) {
                            respMap.put("type", Toos.ServerType.ERROR.getValue());
                            respMap.put("msg", "客户端发送的value信息有误！");
                            break;
                        }
                    }
                    //对应的其他的选项
                    else if(Toos.isContainValue(Toos.exceptionUl,svalue)) {
                        index = Integer.parseInt(sindex);
                        respMap.put("type", Toos.ServerType.SUCCESS.getValue());
                        respMap.put("arrow",false);
                        respMap.put("msg_header_index",index);
                        break;
                    }
                    else {
                        respMap.put("type", Toos.ServerType.ERROR.getValue());
                        respMap.put("msg", "客户端发送的value信息有误！");
                        break;
                    }
                }
                default:
                    Toos.sendWebNullMsg(respMap);
                    System.out.println("【get】信息传输错误" + msgType);
                    break;
            }
            Toos.sendRespMessage(resp,respMap);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Server:进入post请求");
        BufferedReader reader = req.getReader();
        String msg = reader.readLine();
        Map<String,Object> jsMap = null;
        //转为我们对应的type枚举
        Toos.WebType msgType = null;
        SqlSession sqls = null;
        AccountMapper mapper = null;
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
            case ACTIVECONNECT: {
                //获取我们唯一的id和账号名
                String name = (String) jsMap.get("name");//获取我们的唯一账号名
                String sessionId = (String) jsMap.get("sessionId");//获取我们的id
                //存储一个引用类型，方便我们设置flag
                ServerMessage message = new ServerMessage(Toos.ServerType.NULL,name,sessionId,session.getServletContext());
                //这里是主动的阻塞函数请求
                Thread connectThread = new Thread(new ServerRunnable(
                        ThreadObject -> {
                            ServerMessage threadMessage = (ServerMessage)ThreadObject;
                            ServletContext servletContext = threadMessage.getServletContext();
                            Hashtable<String,String> data = (Hashtable<String,String>)servletContext.getAttribute(Toos.SessionId.UNIQUE_USER);
                            String key = threadMessage.getName();//这个是账号
                            String value = threadMessage.getSessionId();//这个是账号的id
                    while (value.equals(data.get(key))) {
                        try {
                            //休息3秒后调用
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            threadMessage.setServerType(Toos.ServerType.ERROR);//重新设置变量里面的值，表示这是个错误
                            return null;
                        }
                    }
                    //如果走到这里说明的话就说明触发重复登录了，结束函数
                    if(data.get(key) == null) {
                        //为空的话表示我们很久没有操作,当前的session对象被自动销毁了
                        threadMessage.setServerType(Toos.ServerType.NULL);
                        return null;
                    } else {
                        //走到这里说明我们的session被替换掉了，替换掉就是强制登录
                        threadMessage.setServerType(Toos.ServerType.SUCCESS);
                        return null;
                    }
                }, message
                ));
                connectThread.start();
                try {
                    connectThread.join();//阻塞当前想线程直到子线程运行完毕
                } catch (InterruptedException e) {
                    //线程出错调用
                    e.printStackTrace();
                    respMap.put("type", Toos.ServerType.RETRY.getValue());
                    //线程出了问题怎么办？发一个消息让它再次尝试
                    respMap.put("name", name);
                    respMap.put("id", sessionId);
                    break;
                }
                if(message.getServerType() == Toos.ServerType.ERROR) {
                    //走入这个说明线程调用出问题了
                    respMap.put("type", Toos.ServerType.RETRY.getValue());
                    //线程出了问题怎么办？发一个消息让它再次尝试
                    respMap.put("name", name);
                    respMap.put("id", sessionId);
                } else if(message.getServerType() == Toos.ServerType.NULL) {
                    //为空说明我们长时间没有操作了
                    respMap.put("type", Toos.ServerType.ERROR.getValue());
                    respMap.put("webType", Toos.WebType.NOOPERATE.getValue());
                } else if(message.getServerType() == Toos.ServerType.SUCCESS) {
                    //走这里说明线程函数运行完毕，检测到异地|重复登录了
                    respMap.put("type", Toos.ServerType.ERROR.getValue());
                    respMap.put("webType", Toos.WebType.REPEATLOGIN.getValue());
                }
            }
                break;
            case CLOSEWINDOW:{//客户端关闭浏览器或刷新浏览器时触发的事件，我们要重置掉当前的session
                    session.invalidate();//释放掉session的内存
            }
                return;//这里我们就不传输信息了
            case EMAIL: {//这个是传给我们的注册邮箱信息
                    String email = (String) jsMap.get("email");
                    //首先去数据库匹配一下是否有这个当前账号名，如果存在，则失败
                    sqls = Toos.sqlSessionFactory.openSession();
                    mapper = sqls.getMapper(AccountMapper.class);
                    //查询是否存在这个账户对象
                    if (mapper.selectByEmail(email) != null) {
                        //如果不为null说明我们已经用这个邮箱注册过一个号码了，不能重复注册
                        respMap.put("type", Toos.ServerType.ERROR.getValue());
                        respMap.put("msg", "同一个邮箱不能重复注册哦~");
                        respMap.put("text", "邮箱不能重复注册！");
                    } else {
                        String code = "";
                        //生成5个0-10之间的随机数
                        for (int i = 0; i < 5; i++) {
                            code += Toos.getRandomIntegerNumber(0, 9);
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
                    }
                    sqls.close();
                }
                break;
            //发送给我们验证码图片信息
            case DRAWCODE: {
                    //获取我们的随机验证码信息
                    String resCode = Toos.getRandomCode();
                    //把随机验证码信息存储到session中
                    String sessionCode = resCode.replace(",", "");
                    session.setAttribute(Toos.SessionId.DRAWCODE, sessionCode);
                    //返回我们的数据
                    respMap.put("type", Toos.ServerType.SUCCESS.getValue());
                    respMap.put("msg", resCode);
                }
                break;
            case NEXT: {
                    String post = (String) jsMap.get("post");//邮箱
                    String postcode = (String) jsMap.get("code");//注册码
                    String imgCode = (String) jsMap.get("imgCode");//图片验证码
                    //先获取存储在我们缓存里面的数据
                    String sPost = "";//邮箱
                    String sPostcode = "";//注册码
                    String sImgCode = "";//图片验证码
                    if (session.getAttribute(Toos.SessionId.EMAIL) == null) {
                        //我们的注册码过期或未发送
                        respMap.put("type", Toos.ServerType.ERROR.getValue());
                        respMap.put("webType", Toos.WebType.CODE.getValue());
                        respMap.put("msg", "注册码过期或还未发送拉~");
                        respMap.put("text", "注册码过期或未发送，请重新验证！");
                        break;
                    } else {
                        String sessionPost = (String) session.getAttribute(Toos.SessionId.EMAIL);
                        String[] postArr = sessionPost.split(",");
                        sPost = postArr[0];
                        sPostcode = postArr[1];
                    }
                    if (session.getAttribute(Toos.SessionId.DRAWCODE) == null) {
                        //我们的验证码过期或未发送
                        respMap.put("type", Toos.ServerType.ERROR.getValue());
                        respMap.put("webType", Toos.WebType.DRAWCODE.getValue());
                        respMap.put("msg", "验证码过期或还未发送拉~");
                        respMap.put("text", "验证码过期或未发送，请重新验证！");
                        break;
                    } else {
                        sImgCode = (String) session.getAttribute(Toos.SessionId.DRAWCODE);
                    }
                    if (!sPost.equals(post) || !sPostcode.equals(postcode)) {
                        //这个是我们的邮箱输入错误
                        respMap.put("type", Toos.ServerType.ERROR.getValue());
                        respMap.put("webType", Toos.WebType.EMAIL.getValue());
                        respMap.put("msg", "邮箱和注册码不匹配哦~");
                        respMap.put("text", "邮箱和注册码不匹配！");
                        break;
                    } else if (!sImgCode.equalsIgnoreCase(imgCode)) {
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
                        respMap.put("msg", "匹配成功拉~");
                        //这个地方存储我们的邮箱地址，方便下一次匹配
                        session.setAttribute(Toos.SessionId.UNIQUE_EMAIL, sPost);
                        Toos.setMaxInactiveInterval(session,2);
                    }
                }
                break;
            case LOGIN: {
                    if (session.getAttribute(Toos.SessionId.UNIQUE_EMAIL) == null) {
                        //我们的账号信息没了，就发送重新注册
                        respMap.put("type", Toos.ServerType.ERROR.getValue());
                        respMap.put("msg", "邮箱数据已过期，请刷新页面重新发送邮箱验证哦~");
                        respMap.put("text", "");
                        break;
                    }
                    String semail = (String) session.getAttribute(Toos.SessionId.UNIQUE_EMAIL);
                    String name = (String) jsMap.get("name");//账号
                    String pass = (String) jsMap.get("pass");//密码
                    //首先去数据库匹配一下是否有这个当前账号名，如果存在，则失败
                    sqls = Toos.sqlSessionFactory.openSession();
                    mapper = sqls.getMapper(AccountMapper.class);
                    Account users = mapper.selectByName(name);//查询是否存在users这个对象
                    if (users != null) {//不为空说明这个账户名的用户已存在
                        respMap.put("type", Toos.ServerType.ERROR.getValue());
                        respMap.put("msg", "这个用户名已经存在拉，换一个叭~");
                        respMap.put("text", "用户名已存在！");
                    } else {
                        //如果不存在的话我们就给它插入到我们的数据库里，属于提交事务
                        mapper.insertTable(semail, name, Toos.encodePass(pass));
                        sqls.commit();
                        respMap.put("type", Toos.ServerType.SUCCESS.getValue());
                        respMap.put("msg", "注册成功拉~");
                    }
                    sqls.close();
                }
                break;
            case SIGN: {
                    String name = (String) jsMap.get("name");//账号
                    String password = (String) jsMap.get("password");//密码
                    //首先去数据库匹配一下是否有这个账号名和密码名
                    sqls = Toos.sqlSessionFactory.openSession();
                    mapper = sqls.getMapper(AccountMapper.class);
                    if(mapper.selectByName(name) == null) {
                        //如果数据库查询name为空则表示账号名不存在
                        respMap.put("type", Toos.ServerType.ERROR.getValue());
                        respMap.put("webType", Toos.WebType.EMAIL.getValue());
                        respMap.put("msg", "登录的账号名不存在哦~");
                        respMap.put("text", "登录的账号名不存在！");
                        break;
                    }
                    if(!Toos.matches(password,mapper.selectPasswordByName(name))) {
                        //如果密码匹配不成功
                        respMap.put("type", Toos.ServerType.ERROR.getValue());
                        respMap.put("webType", Toos.WebType.PASSWORD.getValue());
                        respMap.put("msg", "登录的密码错误啦~");
                        respMap.put("text", "登录密码错误！");
                        break;
                    }
                    //设置我们的flag
                    //session.setAttribute(Toos.SessionId.UNIQUE_USER_FLAG,true);
                    /*
                    * 这个地方会优先执行SessionListener中的方法
                    * 注意，如果这里是使用异步的话，后续我们改成线程休眠传递的方式
                    * 我的推测：listner中的方法很有可能是在session.setAttribute中执行的
                    * 经过线程测试还是优先执行setAttribute中的代码
                    * */
                    //触发listener中的事件
                    session.setAttribute(Toos.SessionId.UNIQUE_USER,name);
                    //这里返回登录成功
                    respMap.put("type", Toos.ServerType.SUCCESS.getValue());
                    //这里我们把session的唯一id以及账号名返回一下
                    respMap.put("name",name);
                    respMap.put("sessionId",session.getAttribute(Toos.SessionId.UNIQUE_USER_FLAG));
            }
                break;
            default:
                Toos.sendWebNullMsg(respMap);
                System.out.println("【post】信息传输错误" + msgType);
                break;
            }
            //把我们的数据传送给网页端
            Toos.sendRespMessage(resp,respMap);

    }
}
