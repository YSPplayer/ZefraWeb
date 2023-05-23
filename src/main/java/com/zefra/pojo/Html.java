package com.zefra.pojo;

public class Html {
     private String context;
     //这个是存储我们的数据库应该有多少页，根据传入的数据数量来决定
     private int pages;
     //一页存放12个内容
     private final int ONE_PAGE_NUMBER = 12;
    public Html(String type,int count) {
        //设置我们索引页的数量
        setPages(count);
        setContext(type);
    }
    public Html(String type) {
        //设置我们索引页的数量
        this.pages = 0;
        setContext(type);
    }
    private void setPages(double count) {
        //向上取整
        this.pages = ((int)Math.ceil(count / ONE_PAGE_NUMBER)) * 10;
    }

    //根据不同的情况，我们插入不同的文本
    private void setContext(String type) {
        switch (type) {
            case "Exception":{
                //这个地方插入我们的内容
                /*
                * 标签属性前面加了:，就是vue的动态绑定
                * javascript:void(0); 让链接不起作用，如果用#点击后页面会刷新返回到顶部
                * */
              this.context = new StringBuilder(" <div class=\"searchMainBox\" id =\"_searchMainBox\">\n" +
                       "                <div id=\"_li-header\">\n" +
                       "                <ul>\n"  +
                       "               <li v-for=\"(item, index) in items\" :key=\"index\"><a href=\"#\" :id =\"`li-header${index}`\" >{{ item }}</a></li>\n" +
                       "                </ul>\n" + "</div>\n" +
                       "                <!-- 搜索框 -->\n" +
                       "                <div class=\"searchBox\">\n" +
                       "                        <!-- 搜索图标 -->\n" +
                       "                    <el-button type=\"primary\" icon=\"el-icon-search\" id=\"el-search\">搜索</el-button>\n" +
                       "                    <!-- 搜索文本 -->\n" +
                       "                    <input type=\"text\">\n" +
                       "                </div>\n" +
                       "            </div>\n" +
                       "            <div class=\"textBody\" id =\"_textBody\">\n" +
                       "                <div v-for=\"(item, index) in items\" :key=\"index\" :class=`textBody-${index}`>\n" +
                       "                    <a href=\"#\" id=\"_title\"> {{item.title}} </a>\n" + "<a href=\"javascript:void(0);\" :id=\"`tag-pre${index}`\"><<</a>\n" +
                       "                    <input v-for=\" (tag,tagIndex) in item.tags\" :key=\"tagIndex\" :id=\"`_button-tag-${index}-${tagIndex}`\" type=\"button\" :value=\"tag\" ref=\"tagInputs\">\n" +
                       "                    <a href=\"javascript:void(0);\" :id=\"`tag-next${index}`\">>></a>\n" +
                       "                    <el-progress :percentage=\"item.time\" :id=\"`el-day-${index}`\"></el-progress>\n" +
                       "                </div>\n" + "<div id=\"paging\">\n" +
                       "                    <el-pagination background layout=\"prev, pager, next\"" + " @current-change=\"handleCurrentChange\" @prev-click=\"handlePrevClick\" @next-click=\"handleNextClick\"" +  ":total=\"" + pages + "\"> \n" +
                       "                    </el-pagination>\n" +
                       "                </div>\n" +
                       "            </div>").toString();
            }
                break;
            case "Exception_search": {
                this.context =  new StringBuilder("<div v-for=\"(item, index) in items\" :key=\"index\" :class=`textBody-${index}`>\n" +
                        "                    <a href=\"#\" id=\"_title\"> {{item.title}} </a>\n" + "<a href=\"javascript:void(0);\" :id=\"`tag-pre${index}`\"><<</a>\n" +
                        "                    <input v-for=\"(tag, tagIndex) in item.tags\" :key=\"tagIndex\" :id=\"`_button-tag-${index}-${tagIndex}`\" type=\"button\" :value=\"tag\" ref=\"tagInputs\">\n" +
                        "                    <a href=\"javascript:void(0);\" :id=\"`tag-next${index}`\">>></a>\n" +
                        "                    <el-progress :percentage=\"item.time\" :id=\"`el-day-${index}`\"></el-progress>\n" +
                        "                </div>\n" + "<div id=\"paging\">\n" +
                        "                    <el-pagination background layout=\"prev, pager, next\"" + " @current-change=\"handleCurrentChange\" @prev-click=\"handlePrevClick\" @next-click=\"handleNextClick\"" + ":total=\""+ pages +"\"> \n" +
                        "                    </el-pagination>\n").toString();
            }
                break;
            default:
                this.context = "";
                break;
        }
    }
    public String getContext() {
        return this.context;
    }
}
