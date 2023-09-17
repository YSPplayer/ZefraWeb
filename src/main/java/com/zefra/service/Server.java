/*
 * 服务器数据请求的中心
 *
 * */
package com.zefra.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.xml.internal.serializer.ToSAXHandler;
import com.zefra.mapper.*;
import com.zefra.pojo.*;
import com.zefra.util.Toos;
import org.apache.ibatis.jdbc.Null;
import org.apache.ibatis.session.SqlSession;

import javax.mail.Session;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.*;
import java.util.function.Function;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/*
control shift a split down分栏
control r 替换
control f 查询
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
@MultipartConfig//解析客户端传入的二进制文件用
public class Server extends HttpServlet {
    @Override
    public void init() throws ServletException {
        System.out.println("server初始化成功");
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
                respMap.put("msg", "客户端发送的value信息有误(>__<)");
                return;
            }
            switch (msgType) {
                case GETBOOK: {
                    respMap.put("type", Toos.ServerType.SUCCESS.getValue());
                    String sindex = Toos.CheckWebParameter(req,"index",respMap);
                    if(sindex == null) break;
                    respMap.put("url","book.html?" + sindex);
                }
                    break;
                case SEARCHBOOK: {
                    String svalue  = Toos.CheckWebParameter(req,"svalue",respMap);
                    String sflag  = Toos.CheckWebParameter(req,"flag",respMap);
                    if(svalue == null) break;
                    String directory = "";
                    if("true".equals(sflag)) {//获取文章的目录
                        try {
                            String key = Toos.liunx ?
                                    "harticle/n_book/1000/directory.txt":  "harticle\\n_book\\1000\\directory.txt";
                            directory = Toos.readString(Toos.rootPath + key);
                        } catch (Exception e) {
                            System.out.println("文件不存在或无法打开：" + directory);
                            respMap.put("type", Toos.ServerType.ERROR.getValue());
                            respMap.put("msg", "文章目录不存在~");
                            break;
                        }
                    }
                    respMap.put("type", Toos.ServerType.SUCCESS.getValue());
                    String key = Toos.liunx ?
                            "harticle/n_book/" + "1000/" + svalue + ".txt":  "harticle\\n_book\\" + "1000\\"  + svalue + ".txt";
                    String fileName = Toos.rootPath + key;
                    String content = "";
                    try {
                        content = Toos.readString(fileName);
                    } catch (Exception e) {
                        System.out.println("文件不存在或无法打开：" + fileName);
                        respMap.put("type", Toos.ServerType.ERROR.getValue());
                        respMap.put("msg", "到头la~文章数据不存在拉~");
                        break;
                    }
                    respMap.put("type", Toos.ServerType.SUCCESS.getValue());
                    respMap.put("context", Toos.encodingBase64(content));
                    if("true".equals(sflag)) {
                        respMap.put("directory", Toos.encodingBase64(directory));
                        respMap.put("html", Toos.encodingBase64(Toos.getHtml("Directory")));
                    }
                }
                    break;
                case GETNEWS: {
                    //获取新闻返回给服务器
                    if(News.news.size() > 2) {
                        //随机获取一个不一样的新闻，返回给客户端
                        String sindex1 =  Toos.CheckWebParameter(req,"index1",respMap);
                        String sindex2 =  Toos.CheckWebParameter(req,"index2",respMap);
                        String sfirst =  Toos.CheckWebParameter(req,"first",respMap);
                        if(sindex1 == null || sindex2 == null || sfirst == null) break;
                        int index1 = -1;
                        int index2 = -1;
                        try{
                            index1 = Integer.parseInt(sindex1);
                            index2 = Integer.parseInt(sindex2);
                        }catch (Exception e) {
                            respMap.put("type", Toos.ServerType.ERROR.getValue());
                            respMap.put("msg", "客户端发送的value信息有误(>__<)");
                            break;
                        }
                        if("true".equals(sfirst)) {
                            int key = Toos.getRandomIntegerNumber(0,News.news.size() - 1);
                            while (true) {
                                int key2 = Toos.getRandomIntegerNumber(0,News.news.size() - 1);
                                if(key2 != key) {
                                    respMap.put("type", Toos.ServerType.SUCCESS.getValue());
                                    respMap.put("news1",News.news.get(key));
                                    respMap.put("news2",News.news.get(key2));
                                    break;
                                }
                            }

                        } else if("false".equals(sfirst)) {
                            while (true) {
                                int key = Toos.getRandomIntegerNumber(0,News.news.size() - 1);
                                if(key != index1 && key != index2) {
                                    respMap.put("type", Toos.ServerType.SUCCESS.getValue());
                                    respMap.put("news",News.news.get(key));
                                    break;
                                }
                            }
                        }
                    } else {
                        respMap.put("type", Toos.ServerType.RETRY.getValue());
                        respMap.put("msg", "服务器暂没有更多的新闻内容~~");
                        break;
                    }
                }
                    break;
                case DELETECHAT: {
                    //删除我们的动态
                    if(!Toos.GodMode) {
                        respMap.put("type", Toos.ServerType.ERROR.getValue());
                        respMap.put("msg", "抱歉~你操作动态的权限不够呀~");
                        break;
                    }
                    String sid =  Toos.CheckWebParameter(req,"id",respMap);
                    int id = -1;
                    try {
                        id = Integer.parseInt(sid);
                    } catch (Exception e) {
                        respMap.put("type", Toos.ServerType.ERROR.getValue());
                        respMap.put("msg", "客户端发送的value信息有误(>__<)");
                        break;
                    }
                    sqls = Toos.sqlSessionFactory.openSession();
                    ChatMapper chatMapper = sqls.getMapper(Toos.getMapperClass("Trends"));
                    chatMapper.deleteChatById(id);
                    sqls.commit();
                    sqls.close();
                    respMap.put("type", Toos.ServerType.SUCCESS.getValue());
                    respMap.put("msg", "动态删除成功拉~");
                }
                    break;
                case UPDATETITLE: {
//                    if(!Toos.GodMode) {
//                        respMap.put("type", Toos.ServerType.ERROR.getValue());
//                        respMap.put("msg", "抱歉~你操作的权限不够呀~");
//                        break;
//                    }
                    //获取我们修改的索引
                    String ttype =  Toos.CheckWebParameter(req,"tindex",respMap);
                    String sindex = Toos.CheckWebParameter(req,"index",respMap);
                    if(sindex == null || ttype == null) break;
                    int index = -1;
                    try {
                        index = Integer.parseInt(sindex);
                    } catch (Exception e) {
                        respMap.put("type", Toos.ServerType.ERROR.getValue());
                        respMap.put("msg", "客户端发送的value信息有误(>__<)");
                        break;
                    }
                    sqls = Toos.sqlSessionFactory.openSession();
                    TextMapper mapper = sqls.getMapper(Toos.getMapperClass(ttype));
                    //这个地方我们查询tags和title以及内容并返回回去
                    String title =  mapper.selectTitleInTitleById(index);
                    String tags = Toos.getExceptionUlTags(mapper.selectTagInTagsById(index));
                    String context = mapper.selectContextInContextById(index);
                    //把数据返回
                    sqls.close();
                    respMap.put("type", Toos.ServerType.SUCCESS.getValue());
                    respMap.put("msg", "数据修改成功拉~");
                    respMap.put("title", Toos.encodingBase64(title));
                    respMap.put("tags", tags);
                    respMap.put("context", Toos.encodingBase64(context));
                }
                    break;
                case DELETETITLE: {
                    if(!Toos.GodMode) {
                        respMap.put("type", Toos.ServerType.ERROR.getValue());
                        respMap.put("msg", "抱歉~你操作的权限不够呀~");
                        break;
                    }
                    //删除文章
                    String ttype =  Toos.CheckWebParameter(req,"tindex",respMap);
                    String sindex = Toos.CheckWebParameter(req,"index",respMap);
                    if(sindex == null || ttype == null) break;
                    int index = -1;
                    try {
                        index = Integer.parseInt(sindex);
                    } catch (Exception e) {
                        respMap.put("type", Toos.ServerType.ERROR.getValue());
                        respMap.put("msg", "客户端发送的value信息有误(>__<)");
                        break;
                    }
                    sqls = Toos.sqlSessionFactory.openSession();
                    TextMapper mapper = sqls.getMapper(Toos.getMapperClass(ttype));
                    try {
                        mapper.deleteTitleById(index);
                        mapper.deleteTagsById(index);
                        mapper.deleteContextById(index);
                        sqls.commit();
                    } catch (Exception e) {
                        respMap.put("type", Toos.ServerType.ERROR.getValue());
                        respMap.put("msg", "数据删除失败！");
                        break;
                    }
                    sqls.close();
                    respMap.put("type", Toos.ServerType.SUCCESS.getValue());
                    respMap.put("msg", "数据删除成功！");
                }
                    break;
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
                    if(sIndex == null) break;
                    int oldIndex = -1;
                    try {
                        oldIndex = Integer.parseInt(sIndex);
                    } catch (Exception e) {
                        respMap.put("type", Toos.ServerType.ERROR.getValue());
                        respMap.put("msg", "客户端发送的value信息有误(>__<)");
                      break;
                    }
                    List<String> fileNames = Toos.mp3classicsFiles;
                    int index = -1;
                    while (true) {
                        index = Toos.getRandomIntegerNumber(0,fileNames.size() - 1);
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
                    if(value == null) break;
                    final int first_index = 0;
                    if("Noumenon".equals(value)) {
                        respMap.put("type", Toos.ServerType.SUCCESS.getValue());
                        respMap.put("html",Toos.encodingBase64(Toos.getHtml(value)));
                    } else if("Trends".equals(value)) {
                        //获取动态
                        sqls = Toos.sqlSessionFactory.openSession();
                        ChatMapper mapper = sqls.getMapper(Toos.getMapperClass(value));
                        //获取从index处的前5个数据
                        List<Chat> Listchats =  mapper.selectChatLimit(0);
                        sqls.close();
                        //把对象转换成json
                        String schat = JSON.toJSONString(Listchats);
                        respMap.put("type", Toos.ServerType.SUCCESS.getValue());
                        respMap.put("data",schat);
                        respMap.put("index",Listchats.size());//这个是我们分页查询所在的下一个开始的索引
                        respMap.put("html",Toos.encodingBase64(Toos.getHtml(value)));
                    } else {
                        //查询数据库
                        sqls = Toos.sqlSessionFactory.openSession();
                        TextMapper mapper = sqls.getMapper(Toos.getMapperClass(value));
                        //获取数据库存放的文章数据
                        List<String> exceptionTitle =  mapper.selectTitleInTitle();
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
                        respMap.put("msg_header_context_index",first_index);
                        respMap.put("msg_header",Toos.getHeaderList(first_index));
                        respMap.put("msg_title",titles);
                        respMap.put("msg_tags",tags);
                        respMap.put("html",Toos.getHtml(value,exceptionTitle.size()));
                        //关闭数据库
                        sqls.close();
                    }
                }
                    break;
                case GETCHAT:{
                    String sindex =  Toos.CheckWebParameter(req,"index",respMap);
                    if(sindex == null) break;
                    int index = -1;
                    try{
                        index = Integer.parseInt(sindex);
                    }catch (Exception e) {
                        respMap.put("type", Toos.ServerType.ERROR.getValue());
                        respMap.put("msg", "客户端发送的value信息有误(>__<)");
                        break;
                    }
                    sqls = Toos.sqlSessionFactory.openSession();
                    ChatMapper mapper = sqls.getMapper(Toos.getMapperClass("Trends"));
                    //获取从index处的前5个数据
                    List<Chat> Listchats =  mapper.selectChatLimit(index);
                    if(Listchats.size() <= 0) {
                        respMap.put("type", Toos.ServerType.ERROR.getValue());
                        respMap.put("msg", "已经到底拉~");
                        sqls.close();
                        break;
                    }
                    sqls.close();
                    String schat = JSON.toJSONString(Listchats);
                    respMap.put("type", Toos.ServerType.SUCCESS.getValue());
                    respMap.put("data",schat);
                    respMap.put("index",Listchats.size() + index);//这个是我们分页查询所在的下一个开始的索引
                }
                break;
                //导航栏索引
                case HEADERINDEX: {
                    //这个是我们的菜单类别
                    String mType =  Toos.CheckWebParameter(req,"tindex",respMap);
                    if(mType == null) break;
                    String svalue = Toos.CheckWebParameter(req,"value",respMap);
                    String sindex = Toos.CheckWebParameter(req,"index",respMap);
                    String soindex = Toos.CheckWebParameter(req,"oindex",respMap);
                    //检查是否为button的点击
                    String isButton = "false";
                    if(req.getParameter("button") != null) {
                        isButton = req.getParameter("button");
                    }
                    int index = 0;
                    int oindex = -1;
                    if(svalue == null || (sindex == null && soindex == null)) break;
                    svalue = Toos.getSvalue(svalue);
                    if(isButton.equals("false")) {
                        //下一页
                        if (svalue.equals(Toos.exceptionUl_next)) {
                            try {
                                //inde为0时我们前进2
                                index = Integer.parseInt(sindex) + 1;
                                if (index == 1) ++index;
                                String headerData = Toos.getHeaderList(index);
                                //为空就是我们的索引到最大值了
                                if (headerData == null) {
                                    respMap.put("type", Toos.ServerType.RETRY.getValue());
                                    respMap.put("msg", "后面已经没有拉~(*^ω^*)");
                                } else {
                                    respMap.put("type", Toos.ServerType.SUCCESS.getValue());
                                    respMap.put("arrow", true);
                                    respMap.put("msg_header_index", index);
                                    respMap.put("msg_header", headerData);
                                }

                            } catch (Exception e) {
                                respMap.put("type", Toos.ServerType.ERROR.getValue());
                                respMap.put("msg", "客户端发送的value信息有误(>__<)");
                                break;
                            }
                        }
                        //上一页
                        else if (svalue.equals(Toos.exceptionUl_prev)) {
                            try {
                                index = Integer.parseInt(sindex) - 1;
                                if (index == 1) --index;
                                String headerData = Toos.getHeaderList(index);
                                //为空就是我们的索引到最大值了
                                if (headerData == null) {
                                    respMap.put("type", Toos.ServerType.RETRY.getValue());
                                    respMap.put("msg", "前面已经没有拉~(*^ω^*)");
                                } else {
                                    respMap.put("type", Toos.ServerType.SUCCESS.getValue());
                                    respMap.put("arrow", true);
                                    respMap.put("msg_header_index", index);
                                    respMap.put("msg_header", headerData);
                                }

                            } catch (Exception e) {
                                respMap.put("type", Toos.ServerType.ERROR.getValue());
                                respMap.put("msg", "客户端发送的value信息有误(>__<)");
                                break;
                            }
                        }
                        //是all，返回所有数据
                        else if (svalue.equals(Toos.exceptionUl_all)) {
                            try {
                                if (sindex != null) index = Integer.parseInt(sindex);
                            } catch (Exception e) {
                                respMap.put("type", Toos.ServerType.ERROR.getValue());
                                respMap.put("msg", "客户端发送的value信息有误(>__<)");
                                break;
                            }
                            sqls = Toos.sqlSessionFactory.openSession();
                            TextMapper mapper = sqls.getMapper(Toos.getMapperClass(mType));
                            List<ExceptionTags> exceptionTags = null;
                            List<String> exceptionTitle = null;
                            if (soindex == null) {//为空索引全部
                                exceptionTags = mapper.selectAllInTags();
                            } else {
                                //直接索引子字段，这个特殊一点，传入的是字符串
                                exceptionTags = mapper.selectByBitAndInTags(Toos.getBitExceptionUlSTags(soindex));
                            }
                            if (exceptionTags.size() > 0) {
                                List<Integer> ids = new ArrayList<>(exceptionTags.size());
                                for (ExceptionTags tag : exceptionTags) {
                                    ids.add(tag.getId());
                                }
                                List<String> exceptionTitles = mapper.selectTitleInTitleLinkTagsId2(ids);
                                Toos.setExceptionUlSTags(exceptionTags);
                                String titles = JSON.toJSONString(exceptionTitles);
                                String tags = JSON.toJSONString(exceptionTags);
                                //下面把我们的数据返回
                                respMap.put("type", Toos.ServerType.SUCCESS.getValue());
                                respMap.put("arrow", false);
                                //我们的第一个索引
                                respMap.put("msg_title_index", index);
                                respMap.put("msg_header_context_index", index);
                                respMap.put("msg_title", titles);
                                respMap.put("msg_tags", tags);
                                respMap.put("html", Toos.getHtml("Exception_search", exceptionTitles.size()));
                            } else {
                                respMap.put("type", Toos.ServerType.ERROR.getValue());
                                respMap.put("msg", "找不到匹配的数据呀~");
                            }
                            sqls.close();
                        }
                        //对应的其他的选项，同时包括button点击的事件
                        else if (Toos.isContainValue(Toos.Tags.exceptionTags, svalue)) {
                            try {
                                if (sindex != null) index = Integer.parseInt(sindex);
                                if (soindex != null) oindex = Integer.parseInt(soindex);
                            } catch (Exception e) {
                                respMap.put("type", Toos.ServerType.ERROR.getValue());
                                respMap.put("msg", "客户端发送的value信息有误(>__<)");
                                break;
                            }
                            sqls = Toos.sqlSessionFactory.openSession();
                            TextMapper mapper = sqls.getMapper(Toos.getMapperClass(mType));
                            List<ExceptionTags> exceptionTags = null;
                            long bitTags = Toos.getBitExceptionUlSTags(svalue);
                            if (oindex > -1) oindex = (1 << oindex);
                            if (oindex == -1) {
                                //单独搜素一个字段，即菜单栏上方的索引
                                exceptionTags = mapper.selectByBitAndInTags(bitTags);
                            } else {
                                //首先索引父字段，然后索引子字段，oindex默认为1
                                exceptionTags = mapper.selectByBitAnd2InTags(bitTags, oindex);
                            }
                            if (exceptionTags.size() > 0) {
                                List<Integer> ids = new ArrayList<>(exceptionTags.size());
                                for (ExceptionTags tag : exceptionTags) {
                                    ids.add(tag.getId());
                                }
                                List<String> exceptionTitles = mapper.selectTitleInTitleLinkTagsId2(ids);
                                Toos.setExceptionUlSTags(exceptionTags);
                                String titles = JSON.toJSONString(exceptionTitles);
                                //这是我们的tags对象
                                String tags = JSON.toJSONString(exceptionTags);
                                //下面把我们的数据返回
                                respMap.put("type", Toos.ServerType.SUCCESS.getValue());
                                respMap.put("arrow", false);
                                //我们的第一个索引，如果是按钮就不返回信息
                                if (soindex == null) {
                                    respMap.put("msg_header_context_index", index);
                                    respMap.put("msg_title_index", index);
                                }
                                respMap.put("msg_title", titles);
                                respMap.put("msg_tags", tags);
                                respMap.put("html", Toos.getHtml("Exception_search", exceptionTitles.size()));
                            } else {
                                respMap.put("type", Toos.ServerType.ERROR.getValue());
                                respMap.put("msg", "找不到匹配的数据呀~");
                            }
                            sqls.close();
                        } else {
                            respMap.put("type", Toos.ServerType.ERROR.getValue());
                            respMap.put("msg", "客户端发送的value信息有误(>__<)");
                        }
                    } else if(isButton.equals("true")) {
                        try {
                            if (sindex != null) index = Integer.parseInt(sindex) + 1;
                        } catch (Exception e) {
                            respMap.put("type", Toos.ServerType.ERROR.getValue());
                            respMap.put("msg", "客户端发送的value信息有误(>__<)");
                            break;
                        }
                        if (index >= Toos.exceptionUl.length || index < 0) {
                            respMap.put("type", Toos.ServerType.ERROR.getValue());
                            respMap.put("msg", "客户端发送的value信息有误(>__<)");
                            break;
                        }
                        String tag = Toos.exceptionUl[index];
                        sqls = Toos.sqlSessionFactory.openSession();
                        TextMapper mapper = sqls.getMapper(Toos.getMapperClass(mType));
                        List<String> exceptionTitles = null;
                        if ("ALL".equals(tag)) {
                            //索引全部
                            exceptionTitles = mapper.selectTitleFromTitleLikeValue(svalue);
                        } else {
                            //索引部分
                            List<Integer> ids = mapper.selectIdByBitAndInTags(Toos.getBitExceptionUlSTags(tag));
                            if (ids.size() > 0) {
                                exceptionTitles = mapper.selectTitleFromTitleLikeValueInId(svalue, ids);
                            }
                        }
                        if (exceptionTitles == null || exceptionTitles.size() <= 0) {
                            respMap.put("type", Toos.ServerType.ERROR.getValue());
                            respMap.put("msg", "找不到匹配的数据呀~");
                        } else {
                            List<ExceptionTags> exceptionTags = mapper.selectInTagsByLinkTitle(exceptionTitles);
                            Toos.setExceptionUlSTags(exceptionTags);
                            String titles = JSON.toJSONString(exceptionTitles);
                            String tags = JSON.toJSONString(exceptionTags);
                            //下面把我们的数据返回
                            respMap.put("type", Toos.ServerType.SUCCESS.getValue());
                            respMap.put("arrow", false);
                            respMap.put("msg_title", titles);
                            respMap.put("msg_tags", tags);
                            respMap.put("html", Toos.getHtml("Exception_search", exceptionTitles.size()));
                        }
                        sqls.close();
                    }
                }
                    break;
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
        Map<String,Object> respMap = new HashMap<String,Object>();//发送回去的map
        Part filePart = null;
        try
        {
            filePart = req.getPart("image");
        } catch (Exception e){}
        if(filePart != null) {
            //专门处理二进制文件数据的地方
            String key = Toos.liunx ?
                    "harticle/img":  "harticle\\img";
            String pathname = Toos.rootPath + key;
            File folder = new File(pathname);
            int count = 0;
            // 加了Objects.requireNonNull() 目录为空或无权读取时会立即抛出一个 NPE 异常信息
            //读取文件操作多个用户操作时需要上锁
            synchronized (Toos.lock) {
                for (File file : Objects.requireNonNull(folder.listFiles())) {
                    String name = file.getName().toLowerCase();
                    if (file.isFile() && (name.endsWith(".jpg"))) {
                        count++;
                    }
                }
                String fileName = count + ".jpg";
                File file = new File(pathname, fileName);
                //保存到本地
                try (InputStream is = filePart.getInputStream()) {
                    Files.copy(is, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    respMap.put("type", Toos.ServerType.SUCCESS.getValue());
                    respMap.put("msg","harticle\\img\\" + fileName);
                } catch (Exception e) { respMap.put("type", Toos.ServerType.ERROR.getValue());}
            }
            Toos.sendRespMessage(resp,respMap);
            return;
        }
        BufferedReader reader = req.getReader();
        String msg = reader.readLine();
        Map<String,Object> jsMap = null;
        //转为我们对应的type枚举
        Toos.WebType msgType = null;
        SqlSession sqls = null;
        AccountMapper mapper = null;
        TextMapper textmapper = null;
        try {
            //传入的数据转为json的map对象
            jsMap = JSONObject.parseObject(msg, Map.class);
            msgType = Toos.WebType.values()[(int)jsMap.get("type")];
        } catch (Exception e) {
            //如果状态获取不到说明客户端传入的参数有误，设置为Null
            msgType = Toos.WebType.NULL;
        }
        /*
         *这里我们需要把用户注册的验证码信息存储到服务器session中
         * 比如说一个人开了多个网页，每个网页都用同一个邮箱注册，这是不被允许的操作
         * */
        HttpSession session = req.getSession();//获取我们的session
        switch (msgType) {
            case GETARTICLE: {
                String title = Toos.CheckWebParameter(jsMap,"msg",respMap);
                String sindex = Toos.CheckWebParameter(jsMap,"index",respMap);
                //解码
                title = URLDecoder.decode(title, "UTF-8");
                sindex = URLDecoder.decode(sindex, "UTF-8");
                if(title == null || sindex == null) break;
                //移除首尾空格
                title = title.trim();
                //根据这个title信息我们获取对应的索引
                sqls = Toos.sqlSessionFactory.openSession();
                List<String> contexts = null;
                List<Integer> ids = null;
                TextMapper mmapper = sqls.getMapper(Toos.getMapperClass(sindex));
                ids = mmapper.selectIdFromTitleByTitle(title);
                if(ids.size() <= 0) {//这个说明数据索引不到
                    respMap.put("type", Toos.ServerType.ERROR.getValue());
                    respMap.put("msg", "抱歉，没有找到符合的数据>_<");
                    break;
                }
                contexts = mmapper.selectcontextFromContextById(ids.get(0));
                if(contexts.size()<= 0) {
                    respMap.put("type", Toos.ServerType.ERROR.getValue());
                    respMap.put("msg", "抱歉，没有找到符合的数据>_<");
                    break;
                }
                if(contexts == null || ids == null) break;
                //首先返回一个副容器Html客户端
                //然后返回文章的内容给客户端
                respMap.put("type", Toos.ServerType.SUCCESS.getValue());
                //这个传输的是iframe的代码
                respMap.put("msg",Toos.getHtml("Article_Iframe"));
                respMap.put("html",Toos.encodingBase64(contexts.get(0)));
                //这里我们再把添加的需要代码的也发送过去
                respMap.put("add_html",Toos.getHtml("Article_Iframe_Add"));
                respMap.put("add_header",Toos.getHtml("Article_Iframe_Header"));
                //把我们的查询索引也返回一下
                respMap.put("index",ids.get(0));
                sqls.close();
            }
             break;
            //当web端用户删除掉图片时，服务器端同步删除该图片
            case DELETEIMG: {
                if(!Toos.GodMode) {
                    respMap.put("type", Toos.ServerType.ERROR.getValue());
                    respMap.put("msg", "抱歉~你操作的权限不够呀~");
                    break;
                }
                String url = Toos.CheckWebParameter(jsMap,"msg",respMap);
                String key = Toos.liunx ?
                        "harticle/img":  "harticle\\img";
                String pathname = Toos.rootPath + key;
                if(url == null) break;
                try {
                    File file = new File(pathname,url);
                    //删除文件时需要 上锁
                    synchronized (Toos.lock) {
                        file.delete();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("删除服务器图片文件有错误");
                }
            }
                break;
            case SAVECHAT:{
                if(!Toos.GodMode) {
                    respMap.put("type", Toos.ServerType.ERROR.getValue());
                    respMap.put("msg", "抱歉~你操作动态的权限不够呀~");
                    break;
                }
                String text = Toos.CheckWebParameter(jsMap,"text",respMap);
                text = URLDecoder.decode(text, "UTF-8");
                if(text == null) break;
                sqls = Toos.sqlSessionFactory.openSession();
                ChatMapper chatMapper = sqls.getMapper(Toos.getMapperClass("Trends"));
                Timestamp time = null;
                try {
                    time = Toos.GetNowlTime();
                } catch (ParseException e) {
                    sqls.close();
                    respMap.put("type", Toos.ServerType.ERROR.getValue());
                    respMap.put("msg", "添加数据失败~");
                    break;
                }
                chatMapper.insertChat(Toos.ChatUrl,Toos.ChatName,time,text);
                Integer id = chatMapper.selectLastId().get(0);
                sqls.commit();
                sqls.close();
                respMap.put("type", Toos.ServerType.SUCCESS.getValue());
                respMap.put("msg", "动态增加成功拉~");
                respMap.put("url",Toos.ChatUrl);
                respMap.put("id",id);
                respMap.put("name",Toos.ChatName);
                respMap.put("time",time);
            }
                break;
            case POSTUPDATETITLE: {
                if(!Toos.GodMode) {
                    respMap.put("type", Toos.ServerType.ERROR.getValue());
                    respMap.put("msg", "抱歉~你操作的权限不够呀~");
                    break;
                }
                //客户端传输的文章信息
                String ttype = Toos.CheckWebParameter(jsMap,"tindex",respMap);
                if(ttype == null) break;
                String html_text = Toos.CheckWebParameter(jsMap,"html",respMap);
                String title = Toos.CheckWebParameter(jsMap,"title",respMap);
                List<String> stags =  (List<String>) jsMap.get("tags");//获取我们的id
                //设置我们的时间
                int times = 0;
                int index = 0;
                try{
                    times = (int)jsMap.get("times");
                    //获取索引
                    index = (int)jsMap.get("index");
                } catch (Exception e) {
                    respMap.put("type", Toos.ServerType.ERROR.getValue());
                    respMap.put("msg", "客户端发送的value信息有误(>__<)");
                    break;
                }
                if(html_text == null || html_text.length() <= 0 ||
                        title == null ) {
                    respMap.put("type", Toos.ServerType.ERROR.getValue());
                    respMap.put("msg", "错啦~，标题不能为空呀~");
                    break;
                }
                //解码
                html_text = URLDecoder.decode(html_text, "UTF-8");
                title = URLDecoder.decode(title, "UTF-8");
                long itag = 0;
                //设置我们的标签数据
                if(stags.size() > 0) {
                    for (String stag : stags) {
                        itag = (itag | Toos.getBitExceptionUlSTags(Toos.getSvalue(stag)));
                    }
                }
                //获取我们的时间数据
                float ftime = ExceptionTags.toTime(times);
                //更新到指定列表
                sqls = Toos.sqlSessionFactory.openSession();
                textmapper = sqls.getMapper(Toos.getMapperClass(ttype));
                try {
                    textmapper.updateTitleInTitleById(title,index);
                    textmapper.updateContextinContextById(html_text,index);
                    textmapper.updateTagsInTagsById(itag,index);
                    textmapper.updateTimeInTagsById(ftime,index);
                    sqls.commit();
                } catch (Exception e) {
                    respMap.put("type", Toos.ServerType.ERROR.getValue());
                    respMap.put("msg", "修改数据失败~");
                    break;
                }
                sqls.close();
                respMap.put("type", Toos.ServerType.SUCCESS.getValue());
                respMap.put("msg", "数据修改成功拉~");
            }
                break;
            case POSTTITLE: {
                if(!Toos.GodMode) {
                    respMap.put("type", Toos.ServerType.ERROR.getValue());
                    respMap.put("msg", "抱歉~你操作的权限不够呀~");
                    break;
                }
                String ttype = Toos.CheckWebParameter(jsMap,"tindex",respMap);
                if(ttype == null) break;
                //客户端传输的文章信息
                String html_text = Toos.CheckWebParameter(jsMap,"html",respMap);
                String title = Toos.CheckWebParameter(jsMap,"title",respMap);
                List<String> stags =  (List<String>) jsMap.get("tags");//获取我们的id
                //设置我们的时间
                int times = (int)jsMap.get("times");
                if(html_text == null || html_text.length() <= 0 ||
                        title == null ) {
                    respMap.put("type", Toos.ServerType.ERROR.getValue());
                    respMap.put("msg", "错啦~，标题不能为空呀~");
                    break;
                }
                //解码
                html_text = URLDecoder.decode(html_text, "UTF-8");
                title = URLDecoder.decode(title, "UTF-8");
                long itag = 0;
                //设置我们的标签数据
                if(stags.size() > 0) {
                    for (String stag : stags) {
                        itag = (itag | Toos.getBitExceptionUlSTags(Toos.getSvalue(stag)));
                    }
                }
                //获取我们的时间数据
                float ftime = ExceptionTags.toTime(times);
                //写入我们的sql数据
                sqls = Toos.sqlSessionFactory.openSession();
                textmapper = sqls.getMapper(Toos.getMapperClass(ttype));
                try {
                    //插入我们的内容
                    textmapper.insertTableToContext(html_text);
                    //插入我们的标题
                    textmapper.insertTableToTitle(title);
                    //插入我们的标签
                    textmapper.insertTableToTags(itag,ftime);
                    //提交
                    sqls.commit();
                } catch (Exception e) {
                    respMap.put("type", Toos.ServerType.ERROR.getValue());
                    respMap.put("msg", "上传数据失败~");
                    break;
                }
                sqls.close();
                respMap.put("type", Toos.ServerType.SUCCESS.getValue());
                respMap.put("msg", "数据上传成功拉~");
            }
                break;
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