var initialize = function() {
    var index = ZfraTools.loadData(ZfraObjects.dataSessionName,false);
    index = typeof(index) == "undefined" ? 0 : index;
    //设置背景
    document.body.style.backgroundImage = `url(\"../${ZfraObjects.pathKey}/pics/login/bg_${index.toString()}.png\")`;
}

window.onload = function() {
    initialize();
};