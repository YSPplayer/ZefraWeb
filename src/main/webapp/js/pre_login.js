var vue_prelogin_main_step1_text = new Vue({
    //获得邮箱
    el:"#_prelogin_main_step1_text",
    methods:{
        focus_error_text() {
            //设置提示文本为空
            document.getElementById("_prelogin_main_error_div").innerHTML = "";   
        }
    }
});
var _prelogin_main_step1_img_canvas = new Vue({
    //绘制我们的二维码
    el:"#_prelogin_main_step1_img_canvas",
    methods:{
        click_canvas() {
            draw();
        }
    }
});
var vue_prelogin_main_step1_code = new Vue({
    // 获取验证码
    el:"#_prelogin_main_step1_code",
    methods:{
        click_code() {
            //创建post请求 true是异步请求
            /*
            1.post使用场景:有text文字用Post，数据过大用post
            */
           var _prelogin_main_step1_text = document.getElementById("_prelogin_main_step1_text");
           var msg_post = _prelogin_main_step1_text.value; 
           if(msg_post.length <= 0) {
                //如果邮箱框没有输入内容，返回错误提示信息
                var h = ZfraTools.vue_createElement();
                ZfraTools.vue_showMessage({
                    message:h("p",null,[h("span",{
                    style: {
                        fontFamily:"font_young_circle",
                        color:"rgb(224, 99, 99)"
                    }
                    },
                    "错了哦，邮箱不能为空~")]),
                    type: "error",
                    center: true,
            
                });
                //在我们的页面上显示错误信息
                document.getElementById("_prelogin_main_error_div").innerHTML = "邮箱账号不能为空！";

           } else {
              ZfraObjects.lock.lock_resp_div = true;
              //创建xhttp对象
              var xhttp = ZfraTools.xhttpCreate();
              //发送客户的邮箱信息到服务器
              var webData = new Object;
              webData.type = ZfraObjects.msgType.EMAIL;
              webData.email = msg_post;
               // 当服务器端发送消息给我们时触发这个函数
                xhttp.onreadystatechange = function() {
                if (this.readyState == 4 && this.status == 200) {
                    //获取服务器传输过来的信息，转为json存储
                    var serverData = JSON.parse(this.responseText);
                    var h = ZfraTools.vue_createElement();
                    switch(serverData.type) {
                        case ZfraObjects.msgType.SUCCESS://数据传送成功
                            ZfraTools.vue_showMessage({
                                message:h("p",null,[h("span",{
                                style: {
                                    fontFamily:"font_young_circle",
                                    color:"rgb(35, 180, 35)"
                                }
                                },
                                serverData.msg)]),
                                type: "success",
                                center: true,
                        
                            });
                            ZfraObjects.waitSeconds = 60;//设置重发秒数 
                            break;
                        case ZfraObjects.msgType.ERROR://数据有误，邮箱解析不成功
                            ZfraTools.vue_showMessage({
                                message:h("p",null,[h("span",{
                                style: {
                                    fontFamily:"font_young_circle",
                                    color:"rgb(224, 99, 99)"
                                }
                                },
                                serverData.msg)]),
                                type: "error",
                                center: true,
                        
                            });
                            //设置提示错误文本
                            document.getElementById("_prelogin_main_error_div").innerHTML = serverData.text;
                            ZfraObjects.waitSeconds = 5;//设置重发秒数    
                            break;
                        case ZfraObjects.msgType.NULL:
                            ZfraTools.showWebError();
                            break;
                        default:
                            alert("网页端信息传输错误！");
                            break;
                    }
                    //这个地方把我们的文本动画重置掉
                    ZfraObjects.lock.lock_resp_div = false;
                    document.getElementById("_prelogin_main_resp_div").innerHTML = "";
                    //设置我们的元素显示(重新发送)
                    setTimeout(async function(){
                        var seconds = ZfraObjects.waitSeconds;
                        var _prelogin_main_step1_code = document.getElementById("_prelogin_main_step1_code");
                        _prelogin_main_step1_code.style.fontSize = "1.3em";
                        while(seconds >= 0) {
                            _prelogin_main_step1_code.value = `重新发送(${seconds}s)`;
                            --seconds;
                            await ZfraTools.sleep(1000);
                        }
                        //结束后给我们的元素状态重置
                        _prelogin_main_step1_code.style.fontSize = "1.5em";
                        _prelogin_main_step1_code.value = "获取验证码";
                        _prelogin_main_step1_code.style.borderColor = "rgb(234, 99, 99)";
                        _prelogin_main_step1_code.style.backgroundColor = "rgb(239, 116, 159)";
                        _prelogin_main_step1_code.style.color = "rgb(239, 205, 67)";
                        _prelogin_main_step1_code.disabled = false;//设置按钮可以被点击
                    });

                }
                };
                ZfraTools.xhttpPostSend(xhttp,webData,true);
                //开启另外一个线程来播放等待服务器响应的动画
                setTimeout(
                    async function() {
                        //设置控件为黑白色且不可选中的状态
                        var _prelogin_main_step1_code = document.getElementById("_prelogin_main_step1_code");
                        _prelogin_main_step1_code.style.borderColor = "rgb(53, 52, 52)";
                        _prelogin_main_step1_code.style.backgroundColor = "rgb(160, 158, 158)";
                        _prelogin_main_step1_code.style.color = "rgb(62, 59, 59)";
                        _prelogin_main_step1_code.disabled = true;//设置按钮不可被点击
                        var count = 1;
                        var str = "";
                        while(ZfraObjects.lock.lock_resp_div){
                            str = "";
                            if(count > 3) count = 1;
                            for(var i = 0;i < count; ++i) {
                                str += ".";    
                            }
                            ++count;
                            document.getElementById("_prelogin_main_resp_div").innerHTML 
                            = "等待服务器响应中" + str;
                            await ZfraTools.sleep(850);
                        }
                    }
                );
           }

        }
    }
}); 
var draw = function() {
    var xhttp = ZfraTools.xhttpCreate();
    var webData = new Object;
    webData.type = ZfraObjects.msgType.DRAWCODE;//绘制验证码的信息
    webData.sessionId = "DRAWCODE";//设置我们的唯一sessionId
    xhttp.onreadystatechange = function() {
        //如果传输成功
        if (this.readyState == 4 && this.status == 200) {
            //获取服务器传输过来的信息，转为json存储
            var serverData = JSON.parse(this.responseText);
            switch(serverData.type) {
                case ZfraObjects.msgType.SUCCESS://数据传送成功
                    //根据取出来的数据绘制我们的二维码
                    DrawCode.clear();
                    DrawCode.draw(serverData.msg);
                    break;
                case ZfraObjects.msgType.NULL://客户端传入的参数有问题
                        ZfraTools.showWebError();
                    break;
                default:
                    alert("网页端信息传输错误！");
                    break;
            }
        }
    }
    //发送请求验证码的数据信息
    ZfraTools.xhttpPostSend(xhttp,webData,true);

}
var initialize = function() {
    var index = ZfraTools.loadData(ZfraObjects.dataSessionName,false);
    index = typeof(index) == "undefined" ? 0 : index;
    //设置背景
    document.body.style.backgroundImage = `url(\"../${ZfraObjects.pathKey}/pics/login/bg_${index.toString()}.png\")`;
    //绘制验证码图片    
    draw();
   
};
window.onload = function() {
    initialize();
};