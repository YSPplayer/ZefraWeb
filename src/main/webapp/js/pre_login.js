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
           var msg_post =  _prelogin_main_step1_text.value; 
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
              //创建xhttp对象
              var xhttp = ZfraTools.xhttpCreate();
              //发送客户的邮箱信息到服务器
              var data = new Object;
              data.type = ZfraObjects.msgType.EMAIL;
              data.email = msg_post;
               // 当服务器端发送消息给我们时触发这个函数
                xhttp.onreadystatechange = function() {
                if (this.readyState == 4 && this.status == 200) {
                    //获取服务器传输过来的信息，转为json存储
                    var data = JSON.parse(this.responseText);
                    var h = ZfraTools.vue_createElement();
                    switch(data.type) {
                        case ZfraObjects.msgType.SUCCESS://数据传送成功
                            ZfraTools.vue_showMessage({
                                message:h("p",null,[h("span",{
                                style: {
                                    fontFamily:"font_young_circle",
                                    color:"rgb(35, 180, 35)"
                                }
                                },
                                "验证码发送成功拉！请去邮箱查看!")]),
                                type: "success",
                                center: true,
                        
                            });
                            break;
                        case ZfraObjects.msgType.ERROR://数据有误，邮箱解析不成功
                            ZfraTools.vue_showMessage({
                                message:h("p",null,[h("span",{
                                style: {
                                    fontFamily:"font_young_circle",
                                    color:"rgb(224, 99, 99)"
                                }
                                },
                                "填写的邮箱账号有误哦~请重新检查！")]),
                                type: "error",
                                center: true,
                        
                            });
                            //设置提示错误文本
                            document.getElementById("_prelogin_main_error_div").innerHTML = "邮箱账号信息有误！";   
                            break;
                        default:
                            alert("网页端信息传输错误！");
                            break;
                    }

                }
                };
                ZfraTools.xhttpPostSend(xhttp,data,true);
           }

        }
    }
}); 

var initialize = function() {
    var index = ZfraTools.loadData(ZfraObjects.dataSessionName,false);
    index = typeof(index) == "undefined" ? 0 : index;
    //设置背景
    document.body.style.backgroundImage = `url(\"../${ZfraObjects.pathKey}/pics/login/bg_${index.toString()}.png\")`;
};
window.onload = function() {
    initialize();
};