function loadMusic(isPlay) {
    // 用于存储mp3文件名的数组
   // let mp3Files = [];
   var xhttp = ZfraTools.xhttpCreate();
    // 当请求完成时触发回调函数
    xhttp.onreadystatechange = function(){
        // 如果请求成功
        if (this.readyState == 4 && this.status == 200)  {
            var serverData = JSON.parse(this.responseText);
            switch(serverData.type) {
                case ZfraObjects.ServerType.ERROR:
                    alert(serverData.msg);
                break;
            case ZfraObjects.ServerType.SUCCESS:
                var urlName = serverData.msg;
                var music = document.getElementById("playMusic");
                music.volume = 0.5; // 音量范围：0.0 - 1.0
                music.src = urlName;
                //存放上一次播放的索引，确保每次播放的都不一样
                ZfraObjects.musicIndex = serverData.index;
                if(isPlay) music.play();
                break;
            case ZfraObjects.ServerType.NULL:
                ZfraTools.showWebError();
                break;
            default:
                ZfraTools.showServerError();
                break;
            }
        } 
    };
   ZfraTools.xhttpGetSend(xhttp,["type","index"],[ZfraObjects.WebType.PLAYMUSIC,ZfraObjects.musicIndex],true);
}
function loadVueObject() {
    ZfraTools.createVueObject("webMeun");
}
function loadBg() {
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
            if(_login_bg_front.style.opacity != "0") {
                SetBackground(_login_bg_front,_login_bg_back,bgarr);
            } else {
                SetBackground(_login_bg_back,_login_bg_front,bgarr);  
            }
        }        

    });
}
var SetBackground = function(o1,o2,bgarr) {
    //设置即将显示的图片
    o1.style.opacity = "0";
    o2.style.opacity = "1";
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
window.onload = function() {
    loadMusic(false);
    loadVueObject();
    loadBg();
}
window.addEventListener("load", function() {
    // 处理代码
    var music = document.getElementById("playMusic");
    var img = document.getElementById("webMusicImg");
    //结束播放
    music.addEventListener("ended", function() {
        //播放完成之后我们随机播放下一首音乐
        loadMusic(true);
    });
    //暂停播放
    music.addEventListener("pause", function() {
        ZfraTools.removeClass(img,"animation_img_rotate");
    });
    //正在播放
    music.addEventListener('playing', function() {
        //播放我们的动画
       ZfraTools.rebroadcast(img,"animation_img_rotate",true);
    });
});