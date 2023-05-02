package com.zefra.pojo;

public class Html {
    //这个是我们的头标数据
     public static String[] exceptionUl = {
         "ALL","C","C++","C#","Java","JavaScript","Lua",
             "Python"
     };
     private String context;

    public Html(String type) {
        setContext(type);
    }
    //根据不同的情况，我们插入不同的文本
    private void setContext(String type) {
        switch (type) {
            case "Exception":{
                //这个地方插入我们的内容
                /*
                * 标签属性前面加了:，就是vue的动态绑定
                * */
              this.context =  " <div class=\"searchMainBox\" id =\"_searchMainBox\">\n" +
                       "                <!-- <ul>\n" +
                       "                    <li v-for=\"(item, index) in items\" :key=\"index\"><a href=\"#\">{{ item }}</a></li>\n" +
                       "                </ul> -->\n" +
                       "                <ul>\n" +
                       "                    <li><a href=\"#\">ALL</a></li>\n" +
                       "                    <li><a href=\"#\">C</a></li>\n" +
                       "                    <li><a href=\"#\">C++</a></li>\n" +
                       "                    <li><a href=\"#\">C#</a></li>\n" +
                       "                    <li><a href=\"#\">Java</a></li>\n" +
                       "                    <li><a href=\"#\">JavaScript</a></li>\n" +
                       "                    <li><a href=\"#\">Lua</a></li>\n" +
                       "                    <li><a href=\"#\">>></a></a></a></li>\n" +
                       "                </ul>\n" +
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
                       "                    <a href=\"#\"> {{item.title}} </a>\n" +
                       "                    <input v-for=\"(tag, iindex) in item.tags\" type=\"button\" :value=\"tag\">\n" +
                       "                    <a href=\"#\" id=\"tag-next\">>></a>\n" +
                       "                    <el-progress :percentage=\"item.time\" :id=\"`el-day-${index}`\"></el-progress>\n" +
                       "                </div>\n" +
                       "            </div>";
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
