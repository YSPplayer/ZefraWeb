/*
存放js所用到的一些工具方法，不包含页面的执行逻辑
*/
var ZfraObjects = {
    boxShadow:"none",/*设置图片的默认显示*/
    isCv:false/*设置一个变量，以跳出随机颜色的循环*/
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
    //toString方法
    toString:function(o) {
        return o.toString();
    }

};

