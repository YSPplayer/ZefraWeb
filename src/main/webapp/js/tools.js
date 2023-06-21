/*
存放js所用到的一些工具方法，不包含页面的执行逻辑
*/
var ZfraObjects = {

    godstate :false,//是否启用上帝模式，为true相当于编辑网页
    isLockEvent:false,//如果为true，我们点击首页的图片将不会触发占卜的事件
    isCv:false,/*设置一个变量，以跳出随机颜色的循环*/
    isClick:false,//如果我们点击了图片按钮就不执行图片的缩放动画事件
    ms:86400000, //一天的毫秒数
    bgMax: 11,//背景图片的最大张数，以后我们只要修改这一个参数就可以解决图片增加的问题
    bgIndex: 0,//存放背景图片的索引
    musicIndex: -1,//记录我们上一次播放音乐的索引
    loginIndex:0,//存放我们从网页端获取的index
    loginTime:0,//存放我们从网页端获取的time
    index:0, //存放我们随机数的index
    loginTiltle:"占卜结果",//存放我们的占卜title文字
    dataName:"zefra_data",//这个是我们网页端数据的key
    dataSessionName:"zefra_session_data",//这个是我们网页端数据的key
    pathKey:"ZefraWeb",//服务器上和正常路径不一样webapp
    formPathOrigin:"http://localhost:8027",//这个是我们子页面跨域传输的来源路径
    formPath:"http://localhost:8027/ZefraWeb//ZefraServer",//我们服务器所在的位置
    dataArr : new Array(0,0,0,0,0),//这个是我们存储网页端数据的地方
    loginArr: new Array("blue","gold","green","orange","purple","red"),//新建图片的数组库
    colorArr: new Array( "蓝色","黄色","绿色","橙色","紫色","红色"),//这个是占卜的文字
    backColorArr:new Array( "white","black","white","black","white","white"),//文字的文本框背景色
    waitSeconds:0,//我们重新发送时的秒数
    desArr: new Array(
        "抽到这张卡的今天，运气超级差！",
        "抽到这张卡的今天，运气非常不好！",
        "抽到这张卡的今天，运气不太好！",
        "抽到这张卡的今天，运气马马虎虎！",
        "抽到这张卡的今天，运气非常好！",
        "抽到这张卡的今天，运气超级好！"
    ),
    desArr2: new Array(
        "也许会被竞争对手超越！加油！！",
        "小心弄丢东西！",
        "向东可能是幸运的朝向。",
        "也许能找到丢失的东西。",
        "可能会有快乐的事?",
        "任何愿望都能实现！"
    ),
    luckyArr: new Array( "闪光饰物","金鱼","植物","伞","墨镜","皮鞋"), 
    WebType:{ //我们传入服务器端的信息种类
        EMAIL:0,//这个是我们的注册邮箱的信息
        DRAWCODE:1,//验证码
        NULL:2,//无效的操作
        NEXT:3,//下一步
        CODE:4,//注册码
        LOGIN:5,//注册操作
        SIGN:6,//登录操作
        CLOSEWINDOW:7,//关闭浏览器
        ACTIVECONNECT:8,//主动握手，便于服务器主动发送请求给网页
        REPEATLOGIN:9,//网页端重复登录
        NOOPERATE:10,//网页端长时间没有操作
        PASSWORD:11,//登录密码
        PLAYMUSIC:12,//音乐播放
        INDEXCONTEXT:13,//索引内容
        HEADERINDEX:14,//头标签的导航
        POSTTITLE:15,//上传我们的文章
        DELETEIMG:16,//删除服务器上的图片
        GETARTICLE:17,//向服务器端请求我们的文章数据
        DELETETITLE:18,//删除服务器上的文章
        UPDATETITLE:19,//更新文章的请求
        POSTUPDATETITLE:20,//更新我们的文章
        HEADERSEARCH:21//导航栏索引按钮
    },
    ServerType:{//服务器返回给我们的信息种类
        SUCCESS:0,//交互成功
        ERROR:1,//交互失败
        NULL:2,//识别不了的信息我们发送这个
        RETRY:3//让客户端重试
    },
    SpecialValue:[
    "C++",
    "C#"
    ],
    lock:{//和线程相关的变量我们都放这里
        lock_resp_div:false
    }
}
//定义vue方法
var vue_methods = new Vue({
    methods: {
        showMessage(obj) {
            /*当我们单机图片时，如果我们占卜过，则触发弹窗警告!*/
            this.$message({
             /*
                this.$createElement用法
                第一个参数为标签，即创建的节点元素的标签是什么
                第二个参数是属性配置，如class、style等
                第三个参数是节点元素的内容
            */
                message :  obj.message,
                type : obj.type,
                center : obj.center,
                customClass : obj.customClass
            });
        },
        //分配创建环境
        createElement() {
            return this.$createElement;
        }
    }
})
var ZfraTools = {
    //element中的弹窗展示方法
    vue_showMessage:function(obj) {
        return vue_methods.showMessage(obj);
    },
    //获取vue的创建环境
    vue_createElement:function() {
        return vue_methods.createElement();
    },
    //让当前函数休眠time秒
    sleep:function(time) {
        return new Promise((resolve) => {
        /*setTimeout 异步执行函数，隔5秒后执行该函数，后面的函数会继续执行不影响*/
            window.setTimeout(() => {
            resolve();
        }, time);
        });
    },
    //获取min-max之间的一个随机数，包括min不包括max
    getRandomNumber:function(min,max) {
        return Math.random()*(max - min + 1) + min;
    },
    //获取min-max之间的一个随机整数，包括min不包括max
    getRandomIntegerNumber:function(min,max) {
        var key = Math.round(Math.random()*(max - min + 1) + min);
        key = (key >= max) ? key - 1 : key;
        if(key >= max || key < 0) key = 0;
        return key; 
    },
    //toString方法
    toString:function(o) {
        return o.toString();
    },
    //给对象增加属性名
    addClass:function(obj, cls) {
        var obj_class = obj.className,//获取 class 内容.
        blank = (obj_class != '') ? ' ' : '';//判断获取到的 class 是否为空, 如果不为空在前面加个'空格'.
        added = obj_class + blank + cls;//组合原来的 class 和需要添加的 class.
        obj.className = added;//替换原来的 class.
    },
     //判断对象是否有属性名
    hasClass:function(obj, cls) {
        var obj_class = obj.className,//获取 class 内容.
        obj_class_lst = obj_class.split(/\s+/),//通过split空字符将cls转换成数组.
        x = 0;
        for(x in obj_class_lst) {
            if(obj_class_lst[x] == cls) {//循环数组, 判断是否包含cls
            return true;
            }
        }
        return false;
    }, 
    //移除对象的类名
    removeClass:function(obj, cls) {

        var obj_class = ' '+obj.className+' ';//获取 class 内容, 并在首尾各加一个空格. ex) 'abc    bcd' -> ' abc    bcd '
        obj_class = obj_class.replace(/(\s+)/gi, ' ');//将多余的空字符替换成一个空格. ex) ' abc    bcd ' -> ' abc bcd '
        var removed = obj_class.replace(' '+cls+' ', ' ');//在原来的 class 替换掉首尾加了空格的 class. ex) ' abc bcd ' -> 'bcd '
        removed = removed.replace(/(^\s+)|(\s+$)/g, '');//去掉首尾空格. ex) 'bcd ' -> 'bcd'
        obj.className = removed;//替换原来的 class.
    },
    //移出对象的所有类名
    removeAllClass:function(obj) {
        obj.className = "";
    },
    //对指定标签下的动画执行重播，实现原理就是移除类然后再添加类重新加载
    rebroadcast:function(obj,cls,flag) {
     // 1、删除动画的class，如果flag为true删除全部类防止动画的播放冲突
     flag ? this.removeAllClass(obj) : this.removeClass(obj,cls);
     // 2、改变元素的offsetWidth
     var temp = obj.offsetWidth;
     obj.offsetWidth = 0;
     obj.offsetWidth = temp;
     // 3、重新添加动画的class
     this.addClass(obj,cls);
    },
    //清空html一个元素中的内容
    clearText:function(...ids) {
        for(var i = 0;i < ids.length; ++i) {
            var o = document.getElementById(ids[i]);
            if(!o) continue;
            o.innerHTML = "";
        }
    },
    //设置任意元素的color
    setColor:function(color,...ids) {
        for(var i = 0;i < ids.length; ++i) {
            var o = document.getElementById(ids[i]);
            if(!o) continue;
            o.style.color = color;
        }
    },
    //毫秒转为时分秒
    msTohms:function(ms) {
        if(ms < 0) ms = 0;
        let h = parseInt(ms / 60 / 60 % 24);
        h = h < 10 ? '0' + h : h;
        let m = parseInt(ms / 60 % 60);
        m = m < 10 ? '0' + m : m;
        let s = parseInt(ms % 60);
        s = s < 10 ? '0' + s : s;
        return [h, m, s]
    },
    //把arry清空为一个默认值
    clearArray:function(arrs,o) {
        for(var i = 0;i < arrs.length; ++i) {
            arrs[i] = o;
        }
    },
    //保存我们的数据到网页端主机
    saveData:function(name,o,islocal) {
        islocal = typeof(islocal) != "undefined" ? islocal : true;
        var msg = JSON.stringify(o);
        islocal? localStorage.setItem(name,msg) : sessionStorage.setItem(name,msg);
    },
    //读取我们存储在网页端主机的数据
    loadData:function(name,islocal) {
        islocal = typeof(islocal) != "undefined" ? islocal : true;
        var msg = islocal ? localStorage.getItem(name) : sessionStorage.getItem(name);
        return JSON.parse(msg);
    },
    //查找 网页端是否有该数据
    hasData:function(name,islocal) {
        islocal = typeof(islocal) != "undefined" ? islocal : true;
        return islocal ? localStorage.getItem(name) != null : sessionStorage.getItem(name) != null;
    },
    //新建一个和传入数组数据相同的数组
    copyArrary:function(arr) {
        var res = new Array(arr.length);
        for(var i = 0;i < arr.length; ++i) {
            res[i] = arr[i];
        }
        return res;
    },
    //创建xhttp的对象
    xhttpCreate:function(){
        return window.XMLHttpRequest ? new XMLHttpRequest() : new ActiveXObject("Microsoft.XMLHTTP");
    },
    //post方式send我们的xhttp
    xhttpPostSend:function(xhttp,obj,type) {
        xhttp.open("POST", ZfraObjects.formPath, type);
        xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xhttp.send(JSON.stringify(obj));
    },
    xhttpPostBinarySend:function(xhttp,obj,type,_boundary) {
        xhttp.open("POST", ZfraObjects.formPath, type);
        //这里给它自动设置，我们别手动设置，需要2个参数
        /*
        Content-Type: multipart/form-data; 
        boundary=----WebKitFormBoundaryphwuwgEq9VsnZ66l
        */
        //boundary目前不知道如何获取
        //xhttp.setRequestHeader("Content-type", `multipart/form-data`);
        xhttp.send(obj);
    },
    //get方式send我们的xhttp
    xhttpGetSend:function(xhttp,key,value,type) {
        if(key.length != value.length) return;
        var msg = `${ZfraObjects.formPath}?`; 
        for(var i = 0; i < key.length; ++i) {
            var svalue = value[i].toString();
            //这里要做特殊字符的转换
            if(ZfraObjects.SpecialValue.includes(svalue)) {
                svalue = encodeURIComponent(svalue);
            }
            msg += `${key[i].toString()}=${svalue}`;
            if(i < key.length - 1) {
                msg += "&";
            }
        }
        xhttp.open("GET",msg,type);
        xhttp.send();
    },
    //客户端信息有误
    showWebError:function() {
        alert("客户端发送的信息有误！");
    },
    //服务器端信息有误
    showServerError:function() {
        alert("服务器发送的信息有误！");
    },
    //设置成功的弹窗
    showSuccessDiv:function(msg1,id,msg2) {
        //如果邮箱框没有输入内容，返回错误提示信息
        var h = this.vue_createElement();
        this.vue_showMessage({
            message:h("p",null,[h("span",{
            style: {
                fontFamily:"font_young_circle",
                color:"rgb(35, 180, 35)"
            }
            },
            msg1)]),
            type: "success",
            center: true,
    
        });
        //在我们的页面上显示错误信息
        if(typeof(id) != "undefined") {
        document.getElementById(id).innerHTML = msg2;
        }
    },
    showErrorDiv:function(msg1,id,msg2) {
        //如果邮箱框没有输入内容，返回错误提示信息
        var h = this.vue_createElement();
        this.vue_showMessage({
            message:h("p",null,[h("span",{
            style: {
                fontFamily:"font_young_circle",
                color:"rgb(224, 99, 99)"
            }
            },
            msg1)]),
            type: "error",
            center: true,
      
        });
        //在我们的页面上显示错误信息
        if(typeof(id) != "undefined") {
           document.getElementById(id).innerHTML = msg2;
        }
    },
    //向服务器发送一个阻塞请求，便于服务器下次主动向客户端传递信息
    activePostSend:function(func,name,sessionId) {
        var xhttp = this.xhttpCreate();
        var webData = new Object;
        webData.type = ZfraObjects.WebType.ACTIVECONNECT;
        webData.name = name;
        webData.sessionId = sessionId;
        xhttp.onreadystatechange = async function() {
            if (this.readyState == 4 && this.status == 200) {
                func(this);
            }
        }
        //发送请求注册的数据信息
        this.xhttpPostSend(xhttp,webData,true);
        
    },
    createVueObject: function(id) {
      return new Vue({
            //获得邮箱
            el:`#${id}`
        });
    },
    createVueObjectWithMethods: function(id,...func) {
        //不行，必须佩服一下自己！！！(>__<)
        var newMethods = {};
         //把函数赋值 ,func.name获取函数名
         for(var i = 0;i < func.length; ++i) {
            newMethods[func[i].name] = func[i];
         }
         return new Vue({
            //获得邮箱
            el:`#${id}`,
            methods:newMethods
        });
    },
    //设置元素为可用状态，flag为true元素会占用原来的位置
    setElementEnable: function(obj,flag) {
        if(typeof(flag) == "undefined") flag = true;
        if(flag) {
            obj.style.opacity = "1" 
        } else {
            //为block或inline，具体由元素本身的类型决定
            //但是如果被修改成none就无法再获取
            //var displayType = window.getComputedStyle(obj).display;
            obj.style.display = "block";
        }
        obj.style.pointerEvents = "auto";
    },
    //设置元素不可用，flag为true元素会占用原来的位置
    setElementDisable:function(obj,flag) {
        if(typeof(flag) == "undefined") flag = true;
        flag ? obj.style.opacity = "0" : obj.style.display = "none";
        obj.style.pointerEvents = "none";
    },
    //获取指定类名的首个元素
    getElementByClassName:function(str) {
        var objs = document.getElementsByClassName(str);
        if(objs == null) return null;
        return objs.length > 0 ? objs[0] : null;
    },
    //把我们html的信息传递给子html
    sendMessageToChildHtml:function(obj) {
        var iframe = document.getElementById("_web_iframe");
        iframe.contentWindow.postMessage(JSON.stringify(obj), `${ZfraObjects.formPath}//article.html`);
    },
    sendMessageToParentHtml:function(obj) {
        window.parent.postMessage(JSON.stringify(obj), '*'); // "*"代表允许通过任何来源发起通信
    },
    //base64字符转换成utf8字符
    base64UrlDecode(str) {
        // 先将 Base64 编码转换成标准的 Base64 编码
        str = str.replace('-', '+').replace('_', '/');
        // 将 Base64 编码解码成二进制数据
        const binaryStr = atob(str);
        // 将二进制数据转换为 UTF-8 编码的字符串
        const utf8Str = Array.from(binaryStr).map((byte) => String.fromCharCode(byte.charCodeAt(0))).join('');
        return decodeURIComponent(escape(utf8Str));
    },
    //消除我们的对象内存，在innerhtml之前,innerhtml=""只是消除内容
    reloadHtml(element,html) {
        const children = Array.from(element.childNodes);
        while (element.firstChild) {
            element.removeChild(element.firstChild);
        }
        children.forEach(child => {
            if(child != null) {
                child.remove();
            }
        });
        element.innerHTML = html;
        
    }
};