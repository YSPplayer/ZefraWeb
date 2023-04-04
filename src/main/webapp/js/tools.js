/*
存放js所用到的一些工具方法，不包含页面的执行逻辑
*/
var ZfraObjects = {
    isCv:false,/*设置一个变量，以跳出随机颜色的循环*/
    isClick:false,//如果我们点击了图片按钮就不执行图片的缩放动画事件
    ms:86400000, //一天的毫秒数
    loginIndex:0,
    loginArr: new Array("bule","gold","green","orange","purple","red"),//新建图片的数组库
    colorArr: new Array( "蓝色","黄色","绿色","橙色","紫色","红色"),
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
var ZfraTools = {
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
        return key >= max ? key - 1 : key; 
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
    
};

