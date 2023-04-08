var vue_login_main_img_front = new Vue({
    el:"#_login_main_img_front",
    data:function () {
        return { 
            src:"",
        }
    }
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
//注册按钮
var vue_login_sub_button_logon = new Vue({
    el:"#_login_sub_button_logon",
    methods:{
        pre_logon() {
           //给客户端传入我们当前页面的数据，仅在网页打开时数据生效
           //跳转到我们的注册页面
           var data = new Object;
           data.bgIndex = ZfraObjects.bgIndex;
           ZfraTools.saveData(ZfraObjects.dataSessionName,data.bgIndex,false);
           window.location.href = `../${ZfraObjects.pathKey}/pre_login.html`;
        }
    }
}); 
//检查zefra_divine数据是否被使用，就是图片不重置
var checkDate = function() {
    if(!ZfraTools.hasData(ZfraObjects.dataName)) return false;
    var data = ZfraTools.loadData(ZfraObjects.dataName);
    ZfraObjects.dataArr = ZfraTools.copyArrary(data.arr);
    ZfraObjects.loginIndex =  ZfraObjects.dataArr[1];
    ZfraObjects.loginTime =  ZfraObjects.dataArr[0];
    var willtime = ZfraObjects.loginTime + ZfraObjects.ms;
    return Date.parse(new Date()) < willtime ? true : false;

}
//设置动态时间
var setTime = async function() {
    var _login_main_p_6 = document.getElementById("_login_main_p_6");
    while(true) {
        var arr =  getRemainingTime();
        _login_main_p_6.innerHTML = `距离下次占卜还剩${arr[0]}时${arr[1]}分${arr[2]}秒（｡･ω･｡)`;
        //过一秒我们再调用
        await ZfraTools.sleep(1000);
    }
};
//对网页进行初始化操作，一些动画的初始化
var initialize = function() {
    //移除img上的class，使其加载图片时不播放动画
    ZfraTools.removeClass(document.getElementById("_flipper"),"class_animation_filp");
    ZfraTools.removeClass(document.getElementById("_login_main_text"),"class_animation_emergence");
    ZfraTools.removeAllClass(document.getElementById("_login_main_img_front"));
    ZfraTools.removeAllClass(document.getElementById("_login_main_img_back"));
    ZfraTools.removeAllClass(document.getElementById("_login_bg_front"));
    ZfraTools.removeAllClass(document.getElementById("_login_bg_back"));
    if(checkDate()) {
        //为true表示我们的日期还没有重置
        ZfraObjects.isLockEvent = true;
        //设置已经触发后的图片
        Vue.set(vue_login_main_img_front,"src",`pics/login/${ZfraObjects.loginArr[ZfraObjects.loginIndex]}.jpg`);
        //设置文字
        setTimeout(setTime);
        document.getElementById("_login_main_title").innerHTML = ZfraObjects.loginTiltle;
        document.getElementById("_login_main_p_1").innerHTML = ZfraObjects.desArr[ZfraObjects.dataArr[2]];
        document.getElementById("_login_main_p_2").innerHTML = `幸运数字：${ZfraObjects.dataArr[3].toString()}`;
        var index = ZfraObjects.dataArr[1];
        document.getElementById("_login_main_p_3").innerHTML = `幸运色：${ZfraObjects.colorArr[index]}`;
        index = ZfraObjects.dataArr[4];
        document.getElementById("_login_main_p_4").innerHTML = `幸运物：${ZfraObjects.luckyArr[index]}`;
        document.getElementById("_login_main_p_5").innerHTML = ZfraObjects.desArr2[ZfraObjects.dataArr[2]];
        //设置我们的文字颜色
        ZfraTools.setColor(ZfraObjects.loginArr[ZfraObjects.dataArr[1]],"_login_main_title","_login_main_p_1",
        "_login_main_p_2","_login_main_p_3","_login_main_p_4","_login_main_p_5","_login_main_p_6");
         //设置占卜窗口为显示
        document.getElementById("_login_main_text").style.backgroundColor = ZfraObjects.backColorArr[ZfraObjects.loginIndex];//背景
        document.getElementById("_login_main_text").style.opacity = 0.5;//透明度

    } else {
        //为false表示我们的日期重置或第一次进来
        Vue.set(vue_login_main_img_front,"src","pics/login/divine.jpg");
    }
    //设置主界面的背景图片，随机加载
    var index = ZfraTools.getRandomIntegerNumber(0,ZfraObjects.bgMax);
    ZfraObjects.bgIndex = index;
    document.getElementById("_login_bg_front").style.backgroundImage = `url(\"../${ZfraObjects.pathKey}/pics/login/bg_${index.toString()}.png\")`;
    //开启替换背景的异步线程
    setTimeout( async function() {
        var _login_bg_front = document.getElementById("_login_bg_front");
        var _login_bg_back = document.getElementById("_login_bg_back");
        var bgarr = new Array(ZfraObjects.bgMax - 1);
        while(true) {
            //每隔一定时间调用一下，3分钟后调用
            await ZfraTools.sleep(180000);
            //背景不是none表示是当前页面被渲染的背景
            if(_login_bg_front.style.backgroundImage != "none") {
                SetBackground(_login_bg_front,_login_bg_back,bgarr);
            } else {
                SetBackground(_login_bg_back,_login_bg_front,bgarr);  
            }
        }        

    });
}; 
var SetBackground = function(o1,o2,bgarr) {
     //设置即将显示的图片
     o1.style.opacity = "1";
     o2.style.opacity = "0";
     //设置我们要替换播放的图片
     for(var i = 0;i < bgarr.length;++i) {
         if(i >= ZfraObjects.bgIndex) {
             bgarr[i] = i + 1;
         } else {
          bgarr[i] = i;
         }
     };
     //随机一个数组的索引
     var index =  bgarr[ZfraTools.getRandomIntegerNumber(0,ZfraObjects.bgMax -1)];
     ZfraObjects.bgIndex = index;
     o2.style.backgroundImage = `url(\"../${ZfraObjects.pathKey}/pics/login/bg_${index.toString()}.png\")`;
     //播放背景消失动画
     ZfraTools.rebroadcast(o1,"class_animation_disappear",true);
     //播放背景显示动画
     ZfraTools.rebroadcast(o2,"class_animation_appear",true);
};
//获取我们占卜的剩余时间
var getRemainingTime = function() {
    return ZfraTools.msTohms((ZfraObjects.loginTime + ZfraObjects.ms - Date.parse(new Date()))/1000);
};
var setText = function() {
    //清空我们的text中的内容
    var Textarrs = ["_login_main_title","_login_main_p_1","_login_main_p_2",
    "_login_main_p_3","_login_main_p_4","_login_main_p_5","_login_main_p_6"];
    //初始化text，设置颜色和文字
    var str = "";
    var key = 0;
    var key2 = 0;
    for(var i = 0;i <= Textarrs.length; ++i) {
        var o = document.getElementById(Textarrs[i]);
        if(!o) continue;
        o.innerHTML = "";
        o.style.color = ZfraObjects.loginArr[ZfraObjects.index];
        switch(i) {
            case 0:
                 //占卜结果
                str = ZfraObjects.loginTiltle;
                break;
                 //抽到这张卡.....
            case 1:
                key = ZfraTools.getRandomIntegerNumber(0,ZfraObjects.desArr.length);
                str = ZfraObjects.desArr[key];
                ZfraObjects.dataArr[2] = key;
                break;
                //幸运数字
            case 2:
                key2 = ZfraTools.getRandomIntegerNumber(0,ZfraObjects.desArr.length);
                str = `幸运数字：${key2.toString()}`;
                ZfraObjects.dataArr[3] = key2;
                break;
                 //幸运色，和上面抽到的卡片匹配
            case 3:
                var tstr = ZfraObjects.colorArr[ZfraObjects.index];
                str = `幸运色：${tstr}`;
                break;
            case 4:
                 //幸运物
                 key2 = ZfraTools.getRandomIntegerNumber(0,ZfraObjects.luckyArr.length);
                 var tstr = ZfraObjects.luckyArr[key2];
                 str = `幸运物：${tstr}`;
                 ZfraObjects.dataArr[4] = key2;
                 break;
            case 5:
                 //愿望，和des的index对应
                 str = ZfraObjects.desArr2[key];
                 break;
            case 6:
                break;
            default:
                break;
        }
        if(i != 6) o.innerHTML = str;
    }
};
var gradientText = function(os) {
    os.forEach(o => {
        let letters = o.textContent.split("");/*获取text中的内容，然后分割每一个字符，转为字符数组存储*/
        o.textContent = "";/*把标签的文字设置成空*/
        letters.forEach((letter, i) => {/*遍历字符数组*/
            let span = document.createElement("span");
            span.textContent = letter;
            //0.05s//
           span.style.animationDelay = `${i * 0.3}s`;
          o.append(span);
        })
    });
};
var showMessage = function() {
     //如果我们已经占卜过，给出提示信息
     var h = ZfraTools.vue_createElement();
     ZfraTools.vue_showMessage({
        message:h("p",null,[h("span",{
        style: {
             color:"gold",
             fontFamily:"font_young_circle"
         }
         },
        "你已经占卜过啦！明天再来吧~(ˊっω•̤ˋ)")]),
         type: "info",
         center: true,
         customClass :"vue_show_message_div"

     });
     var div = document.querySelector(".vue_show_message_div");
     div.style.backgroundColor = "#e55468";
     div.style.borderColor = div.style.backgroundColor;
     ZfraTools.removeClass(div,"vue_show_message_div");
}
window.onload = function() {
    // localStorage.clear();
    // return;
    initialize();
    var _login_bg_front = document.getElementById("_login_bg_front");
    var _login_bg_back = document.getElementById("_login_bg_back");
    var _login_main_imag_front = document.getElementById("_login_main_img_front");
    var _login_main_imag_back = document.getElementById("_login_main_img_back");
    var _flipper = document.getElementById("_flipper");
    var _login_main_text = document.getElementById("_login_main_text");
    //设置显示权重，让我们可以点击图片
    if(!ZfraObjects.isLockEvent || ZfraObjects.godstate) _login_main_text.style.zIndex = "0";
    //绑定鼠标悬停在图片之上的事件
    _login_main_imag_front.onmouseover = async function() {
        /*
            如果我们在这里写一个死循环，那么即使这个事件结束掉这个函数依然是在执行的
            这个本质上也是开了异步线程的函数
        */
        if(ZfraObjects.isLockEvent && !ZfraObjects.godstate) return;
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
        if(ZfraObjects.isLockEvent && !ZfraObjects.godstate) return;
        ZfraObjects.isCv = false;
        if(!ZfraObjects.isClick) {//如果我们的卡片被点击了，就不触发该动画
           ZfraTools.rebroadcast(this,"class_animation_reduce",true);
        }
    };
    /*单机占卜文字框返回已占卜信息*/
    _login_main_text.addEventListener("click", async function(e){
        
        if(ZfraObjects.isLockEvent && !ZfraObjects.godstate) {
            showMessage();
        }
        return;
    });
    //单机Img图片时触发的事件
    _flipper.addEventListener("click", async function(e){
        if(ZfraObjects.isLockEvent && !ZfraObjects.godstate) {
            //如果我们已经占卜过，给出提示信息
            showMessage();
            return;
        } 
        else {
            //从我们网页中将上次存储数据移除
            localStorage.removeItem(ZfraObjects.dataName);
            ZfraObjects.isLockEvent = true;//本次不再触发
        }
        e.preventDefault;
        ZfraObjects.isClick = true;
        //事件点击时重播动画，旋转图片
        this.style.animationFillMode = "forwards";
        //在翻转卡片之前，通过随机数给我们的卡片增加图片;
        var index = ZfraTools.getRandomIntegerNumber(0,ZfraObjects.loginArr.length);
        Vue.set(vue_login_main_img_back,"src",`pics/login/${ZfraObjects.loginArr[index]}.jpg`);
        ZfraTools.rebroadcast(this,"class_animation_filp",false);
        ZfraTools.rebroadcast(_login_main_imag_back,"class_animation_enlarge",true);
        ZfraObjects.index = index;
    });
    /*给元素绑定该元素上class_animation_enlarge动画播放完毕后的事件*/
    /*
    addEventListener和On的区别，addEventListener可以绑定多个相同的事件，on不行，
    addEventListener的兼容性更好
    */
    _login_main_imag_back.addEventListener("animationend",async function() {
        //因为我们一个类中绑定了多个动画，但每次只会执行一个，所以我们可以根据类名判断是否触发该动画的事件
        if(ZfraTools.hasClass(this,"class_animation_reduce")) return;
        ZfraTools.rebroadcast(_login_main_imag_back,"class_animation_reduce",true);
        ZfraObjects.isClick = false;
        ZfraTools.clearArray(ZfraObjects.dataArr,0);
        ZfraObjects.dataArr[0] = Date.parse(new Date());
        ZfraObjects.dataArr[1] = ZfraObjects.index;
        /*放置好后加载我们的文本动画*/
        _login_main_text.style.zIndex = "4";//设置显示权重
        _login_main_text.style.backgroundColor = ZfraObjects.backColorArr[ZfraObjects.index];//设置文本背景框
        ZfraTools.rebroadcast(_login_main_text,"class_animation_emergence",false);
        await ZfraTools.sleep(2000);//以下代码在动画事件中播放会错误，所以我们阻塞线程来运行
        var login_main_titles = document.querySelectorAll(".login_main_title");
        var login_main_ps = document.querySelectorAll(".login_main_p");
        //先设置我们的文本内容
        setText();
        //先给我们的元素设置占卜文本
        gradientText(login_main_titles);
        gradientText(login_main_ps);

        //开一个异步线程，来设置我们文本中的时间
        setTimeout(setTime,5000);

        //保存我们的数据
        //给我们的时间赋值
        ZfraObjects.loginTime = ZfraObjects.dataArr[0];
        var data = new Object;
        data.arr = ZfraTools.copyArrary(ZfraObjects.dataArr);
        //把当前数据存入本地
        ZfraTools.saveData(ZfraObjects.dataName,data);
      }
    );
    _login_bg_front.addEventListener("animationend",async function() {
        if(ZfraTools.hasClass(this,"class_animation_disappear")) {
            //如果我们播放的是消失的动画，则播放完毕后置空掉
            this.style.backgroundImage = "none";
        }
        return;
    });
    _login_bg_back.addEventListener("animationend",async function() {
        if(ZfraTools.hasClass(this,"class_animation_disappear")) {
            //如果我们播放的是消失的动画，则播放完毕后置空掉
            this.style.backgroundImage = "none";
        }
        return;
    });
   
};