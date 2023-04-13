
function randomColor() {//得到随机的颜色值
    var r = Math.floor(Math.random() * 256);
    var g = Math.floor(Math.random() * 256);
    var b = Math.floor(Math.random() * 256);
    return "rgb(" + r + "," + g + "," + b + ")";
  
}
var DrawCode = {
    clear:function() {//清空我们的画布
        var cnv = document.getElementById("_prelogin_main_step1_img_canvas");
        var ctx = cnv.getContext("2d");
        ctx.clearRect(0, 0, cnv.width, cnv.height); // 清空矩形
    },
    draw:function(sCode) {
        var show_num = [];
        var canvas = document.getElementById("_prelogin_main_step1_img_canvas");//获取canvas
        var canvas_width = canvas.width;
        var canvas_height = canvas.height;
        var context = canvas.getContext("2d");//获取到canvas画图的环境
        var aCode = sCode.split(",");
        var aLength = aCode.length;//获取到数组的长度
        for (var i = 0; i < aLength; i++) { //这里的for循环可以控制验证码位数
            var deg = Math.random() - 0.5; //产生一个随机弧度 
            var txt = aCode[i];//得到随机的一个内容
            show_num[i] = txt.toLowerCase();
            var x = 10 + i * 20;//文字在canvas上的x坐标
            var y = 20 + Math.random() * 8;//文字在canvas上的y坐标
            context.font = "bold 24px 微软雅黑";     
            context.translate(x, y);
            context.rotate(deg);
            context.fillStyle = randomColor();
            context.fillText(txt, 0, 0);
            context.rotate(-deg);
            context.translate(-x, -y);
        }
        for (var i = 0; i <= 5; i++) { //验证码上显示线条
        context.strokeStyle = randomColor();
        context.beginPath();
        context.moveTo(Math.random() * canvas_width, Math.random() * canvas_height);
        context.lineTo(Math.random() * canvas_width, Math.random() * canvas_height);
        context.stroke();
       }
       for (var i = 0; i <= 20; i++) { //验证码上的小点
        context.strokeStyle = randomColor();//随机生成
        context.beginPath();
        var x = Math.random() * canvas_width;
        var y = Math.random() * canvas_height;
        context.moveTo(x, y);
        context.lineTo(x + 1, y + 1);
        context.stroke();
       }
    }
}