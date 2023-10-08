# ZefraWeb
## ●基于java servlet编写的个人网站    
[# 作者网站](http://121.37.142.104:8080/ZefraWeb/index.html#)    

![](https://github.com/YSPplayer/ZefraWeb/blob/main/temp.jpg)

## ●V1.1主要功能
①：随机新闻    
②：编程文章分享    
③：编程库分享    
④：书籍分享

## ●项目运行
### 一.安装第三库
①：在项目中的`./webapp/js/dependency/`文件夹下添加如下依赖库：    
`base64.js`·中文转码    
`FileSaver.js`-文件下载    
`vue.js`·动态html渲染    
②：在项目的`./webapp`文件夹下添加如下依赖库，命名为文件夹：    
`element-ui`-集合ui(https://element.eleme.cn/#/zh-CN/component/cascader)    
`codemirror`-代码块渲染(https://codemirror.net)    

### 二.编译准备
①：推荐使用`idea`打开项目    
②：需要先安装`java`,`python`,`mysql`的系统环境    
③：打开项目中的`mybatis-config.xml`文件，配置`mysql`的用户名和密码    
④：把`./webapp/data/`下的`backups.sql`数据库文件导入到系统的`mysql`中，数据库命名为`zefraweb`，执行命令`mysql -u 用户名 -p zefraweb < /backups.sql`    
⑤：打开项目中的`data.properties`文件，配置如下属性：    
1.`database.pyUrl`： 系统上`python`的起始命令    
2.`database.godMode`： 为`true`表示网页模式下开启修改数据库的权限    
3.`database.npath`： 表示`./news/`文件夹所在的绝对路径    
⑥：打开项目中`toos.java`文件，找到`mode`属性，如果是服务器运行，值修改为`Mode.Server`;如果是`liunx`运行，属性`liunx`值改为`true`    

### 三.本地部署
①：`idea`右击`项目`—>`run maven`—>`tomact7:run`启动项目    
②：即可在本地如下地址打开网页：(http://localhost:8027/ZefraWeb/index.html)    

### 四.Liunx服务器部署
①：修改项目的`toos.js`文件中的`formPathOrigin`属性和`formPath`属性的地址为服务器对应的公网地址    
②：`idea`单机`build`—>`build Artifacts`项目打包成`.war`文件    
③：服务器安装`tomact`，把`.war`包放入`tomact`的`./webapps/`文件夹下        
④：进入`tomact`的`./bin`文件夹下，执行`sh startup.sh`命令运行项目，默认开放在8080端口    
⑤：即可在如下公网地址打开网页：`http://公网ip:8080/ZefraWeb/index.html#`

