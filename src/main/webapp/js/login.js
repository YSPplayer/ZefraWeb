//正面图片 
var godstate = true;
var vue_login_main_img_front = new Vue({
    el:"#_login_main_img_front",
    data:function () {
        return { 
            src:"",
        }
    },
}); 
//背面图片
var vue_login_main_img_back = new Vue({
    el:"#_login_main_img_back",
    data:function () {
        return { 
            src:"",
        }
    },
}); 
//检查zefra_divine数据是否被使用，就是图片不重置
var checkDate = function() {
    var msg = localStorage.getItem("zefra_divine");
    if(!msg) return false;
    var data = JSON.parse(msg);
    ZfraObjects.loginIndex = data.index;
    var willtime = data.time + ZfraObjects.ms;
    return Date.parse(new Date()) < willtime ? true : false;

}
//对网页进行初始化操作，一些动画的初始化
var initialize = function() {
    //移除img上的class，使其加载图片时不播放动画
    ZfraTools.removeClass(document.getElementById("_flipper"),"class_animation_filp");
    ZfraTools.removeClass(document.getElementById("_login_main_text"),"class_animation_emergence");
    ZfraTools.removeAllClass(document.getElementById("_login_main_img_front"));
    ZfraTools.removeAllClass(document.getElementById("_login_main_img_back"));
    //如果我们占卜过，网页加载时只给定占卜的动画
    checkDate() ? Vue.set(vue_login_main_img_front,"src",`pics/login/${ZfraObjects.loginArr[ZfraObjects.loginIndex]}.jpg`) :
                 Vue.set(vue_login_main_img_front,"src","pics/login/divine.jpg");
    

}; 
window.onload = function() {
    initialize();
    var _login_main_imag_front = document.getElementById("_login_main_img_front");
    var _login_main_imag_back = document.getElementById("_login_main_img_back");
    var _flipper = document.getElementById("_flipper");
    var _login_main_text = document.getElementById("_login_main_text");
    //绑定鼠标悬停在图片之上的事件
    _login_main_imag_front.onmouseover = async function() {
        /*
            如果我们在这里写一个死循环，那么即使这个事件结束掉这个函数依然是在执行的
            这个本质上也是开了异步线程的函数
        */
        if(checkDate) return;
        ZfraTools.rebroadcast(this,"class_animation_enlarge",true);
        ZfraObjects.isCv = true;
            var x = ZfraTools.toString(ZfraTools.getRandomNumber(0,256)),
                y = ZfraTools.toString(ZfraTools.getRandomNumber(0,256)),
                z = ZfraTools.toString(ZfraTools.getRandomNumber(0,256));
            var o = 0;
            var flag = false;
            var speed = 1.6;
        while(ZfraObjects.isCv) {
            if(o >= 40 || o < 0) {
                flag = !flag;
                if(o < 0)  {
                   x = ZfraTools.toString(ZfraTools.getRandomNumber(0,256));
                   y = ZfraTools.toString(ZfraTools.getRandomNumber(0,256));
                   z = ZfraTools.toString(ZfraTools.getRandomNumber(0,256));
                }
            } 
            !flag ? o+=speed : o-=speed;
            /*设置图片发光*/
            this.style.boxShadow = `0px 0px ${o}px rgb(${x}, ${y}, ${z})`;
            await ZfraTools.sleep(29);
        };
        this.style.boxShadow = "none";
    };
    //绑定鼠标离开悬停图的事件
    _login_main_imag_front.onmouseout = async function() {
        if(checkDate) return;
        ZfraObjects.isCv = false;
        if(!ZfraObjects.isClick) {
           ZfraTools.rebroadcast(this,"class_animation_reduce",true);
        }
    };
    //单机Img图片时触发的事件
    _flipper.addEventListener("click", async function(e){
        if(checkDate && !godstate) return;
        else {
            //localStorage.removeItem("zefra_divine");
        }
        e.preventDefault;
        ZfraObjects.isClick = true;
        //事件点击时重播动画，旋转图片
        this.style.animationFillMode = "forwards";
        //在翻转卡片之前，通过随机数给我们的卡片增加图片;
        var index = ZfraTools.getRandomIntegerNumber(0,ZfraObjects.loginArr.length);
        if(index >= ZfraObjects.loginArr.length || index < 0) index = 0;
        Vue.set(vue_login_main_img_back,"src",`pics/login/${ZfraObjects.loginArr[index]}.jpg`);
        ZfraTools.rebroadcast(this,"class_animation_filp",false);
        ZfraTools.rebroadcast(_login_main_imag_back,"class_animation_enlarge",true);
        await ZfraTools.sleep(2500);
        ZfraTools.rebroadcast(_login_main_imag_back,"class_animation_reduce",true);
        ZfraObjects.isClick = false;
        var data = new Object;
        data.time = Date.parse(new Date());
        data.index = index;
        var msg = JSON.stringify(data);
        //把当前时间的存入本地
        localStorage.setItem("zefra_divine",msg);
        /*放置好后加载我们的文本动画*/
        ZfraTools.rebroadcast(_login_main_text,"class_animation_emergence",false);
    });
};
