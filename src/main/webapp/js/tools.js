/*
存放js所用到的一些工具方法，不包含页面的执行逻辑
*/
var ZfraObjects = {
    godstate :false,//是否启用上帝模式，为true相当于编辑网页
    isLockEvent:false,//如果为true，我们点击首页的图片将不会触发占卜的事件
    isCv:false,/*设置一个变量，以跳出随机颜色的循环*/
    isClick:false,//如果我们点击了图片按钮就不执行图片的缩放动画事件
    ms:86400000, //一天的毫秒数
    loginIndex:0,//存放我们从网页端获取的index
    loginTime:0,//存放我们从网页端获取的time
    index:0, //存放我们随机数的index
    loginTiltle:"占卜结果",//存放我们的占卜title文字
    dataName:"zefra_divine",//这个是我们网页端数据的key
    /*
        0
    */
    dataArr : new Array(0,0,0,0,0),//这个是我们存储网页端数据的地方
    loginArr: new Array("blue","gold","green","orange","purple","red"),//新建图片的数组库
    colorArr: new Array( "蓝色","黄色","绿色","橙色","紫色","红色"),//这个是占卜的文字
    backColorArr:new Array( "white","black","white","black","white","white"),//文字的文本框背景色
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
        obj_class = obj_class.replace(/(\s+)/gi, ' '),//将多余的空字符替换成一个空格. ex) ' abc    bcd ' -> ' abc bcd '
        removed = obj_class.replace(' '+cls+' ', ' ');//在原来的 class 替换掉首尾加了空格的 class. ex) ' abc bcd ' -> 'bcd '
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
    saveData:function(name,o) {
        var msg = JSON.stringify(o);
        localStorage.setItem(name,msg);
    },
    //读取我们存储在网页端主机的数据
    loadData:function(name) {
        var msg = localStorage.getItem(name);
        return JSON.parse(msg);
    },
    //查找 网页端是否有该数据
    hasData:function(name) {
        return localStorage.getItem(name) != null;
    },
    //新建一个和传入数组数据相同的数组
    copyArrary:function(arr) {
        var res = new Array(arr.length);
        for(var i = 0;i < arr.length; ++i) {
            res[i] = arr[i];
        }
        return res;
    },
};

