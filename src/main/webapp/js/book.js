var Book = {
    page:-1,
    first:false,
    last:false,
    linkArray:[]
}
function getParameter() {
    var url = window.location.href;
    return url.slice(url.indexOf('?') + 1);
  } 
function LoadBook(pvalue,flag) {
    var xhttp = ZfraTools.xhttpCreate();
    (function(value,f){
            xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200)  {
                var serverData = JSON.parse(this.responseText);
                switch(serverData.type) {
                    case ZfraObjects.ServerType.ERROR:
                        ZfraTools.showErrorDiv(serverData.msg);
                    break;
                case ZfraObjects.ServerType.SUCCESS:
                    var data = ZfraTools.base64UrlDecode(serverData.context);
                    var webBody = document.getElementById("_webBody");
                    webBody.style.height = "";
                    ZfraTools.reloadHtml(webBody,data);
                    if(f) {
                        //加载目录
                        var html = ZfraTools.base64UrlDecode(serverData.html);
                        ZfraTools.reloadHtml(document.getElementById("_webMeun"),html);
                        var directory = ZfraTools.base64UrlDecode(serverData.directory);
                        //对应目录数据
                        var odirectory = new Vue({
                            el: '#_webMeun',
                            data: {
                              list: []
                            },
                            methods: {
                                searchClick:function(index) {
                                    LoadBook(Book.linkArray[index],false);
                                }
                            }
                        });
                        var darray = directory.split('#').filter(item => item.trim() !== '');
                        var findex = 0;
                        darray.forEach(key => {
                            var numString =""
                            var i;
                            for (i = 0; i < key.length; i++) {
                                // 判断当前字符是否为数字
                                if (!isNaN(Number(key[i]))) {
                                    numString += key[i]; // 将数字字符加到结果字符串的前面
                                  } else {
                                    break; // 如果遇到非数字字符则停止遍历
                                }
                               
                            }
                            Book.linkArray.push(numString);
                            //只存放关键信息
                            odirectory.list.push({text:key.slice(i)});
                            ++findex;
                        });
                    }
                    Book.page = parseInt(value);
                    //设置长度
                    if(webBody.offsetHeight < 924) {
                        webBody.style.height = "924px";
                    }
                    document.documentElement.scrollTop = 0;
                    break;
                case ZfraObjects.ServerType.NULL:
                    ZfraTools.showWebError();
                    break;
                default:
                    ZfraTools.showServerError();
                    break;
                }
            };
        }
    })(pvalue,flag);
ZfraTools.xhttpGetSend(xhttp,["type","svalue","flag"],[ZfraObjects.WebType.SEARCHBOOK,pvalue,flag],true);

}
function AddEvent() {
    ZfraTools.createVueObject(`_optionBody`);
    document.getElementById(`_optionBody_0`).addEventListener("click",function() {
        LoadBook((Book.page - 1),false);
    });
    document.getElementById(`_optionBody_1`).addEventListener("click",function() {
        LoadBook((Book.page + 1),false);
    });
}
window.onload  = function() {
    LoadBook(getParameter(),true);
    AddEvent();
}
document.addEventListener('DOMContentLoaded', function() {

});