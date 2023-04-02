window.onload = function() {
    var _login_main_imag = document.getElementById("_login_main_img");
    //绑定鼠标悬停在图片之上的事件
    _login_main_imag.onmouseover = async function() {
        /*
            如果我们在这里写一个死循环，那么即使这个事件结束掉这个函数依然是在执行的
            这个本质上也是开了异步线程的函数
        */
        ZfraObjects.isCv = true;
            var x = ZfraTools.toString(ZfraTools.getRandomNumber(0,256));
            var y = ZfraTools.toString(ZfraTools.getRandomNumber(0,256));
            var z = ZfraTools.toString(ZfraTools.getRandomNumber(0,256));
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
        this.style.boxShadow = ZfraObjects.boxShadow;
    };
    //绑定鼠标离开悬停图的事件
    _login_main_imag.onmouseout = async function() {
        ZfraObjects.isCv = false; 
    };
    _login_main_imag.onclick = async function() {
        //perspective(800px) rotateX(0deg) rotateY(150deg)
        this.style.transform = 'rotateY(150deg)';
        //_login_main_imag.style.transform
        //transform: rotateY(150deg);
        //oWrap.style.transform='perspective(800px) rotateX('+roX+'deg) rotateY('+roY+'deg)';
    }
};
