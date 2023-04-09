var vue_prelogin_main_step1_code = new Vue({
    // 获取验证码
    el:"#_prelogin_main_step1_code",
    methods:{
        click_code() {
            //创建xhttp对象
            var xhttp = ZfraTools.xhttpCreate();
            //创建post请求 true是异步请求
            /*
            1.post使用场景:有text文字用Post，数据过大用post
            */
           ZfraTools.xhttpPostSend(xhttp,{name:1,key:10},true);
        }
    }
}); 

var initialize = function() {
    var index = ZfraTools.loadData(ZfraObjects.dataSessionName,false);
    index = typeof(index) == "undefined" ? 0 : index;
    //设置背景
    document.body.style.backgroundImage = `url(\"../${ZfraObjects.pathKey}/pics/login/bg_${index.toString()}.png\")`;
}

window.onload = function() {
    initialize();

};