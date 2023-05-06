//存放我们索引的对象
var IndexType = {
    "1":"Trends",
    "2":"Program",
    "2-1":"Exception",
    "2-2":"Tool",
    "2-3":"Case",
    "2-4":"Book",
    "3":"Noumenon",
    "4":"Entertainment",
    "4-1":"GameResources",
    "4-2":"NovaAi",
    "4-3":"ChatGpt"
};
var IndexKey = {
    msg_header_index:-1,
    msg_header_context_index:-1,
    msg_title_index:-1,
    msg_header_context:"",
    //存放我们的标签数组
    tags:[]
} 
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
    ZfraTools.createVueObjectWithMethods("webMeun",
    // 获取我们的索引，根据索引做一些事情
    function handleSelect(index) {
        //这里给它返回false，防止客户端一直请求造成卡顿
        if(ZfraObjects.lock.lock_resp_div) return;
        //设置lock
        ZfraObjects.lock.lock_resp_div = true;
        var xhttp = ZfraTools.xhttpCreate();
        xhttp.onreadystatechange = function(){
            // 如果请求成功
            if (this.readyState == 4 && this.status == 200)  {
                var serverData = JSON.parse(this.responseText);
                switch(serverData.type) {
                    case ZfraObjects.ServerType.ERROR:
                        alert(serverData.msg);
                    break;
                case ZfraObjects.ServerType.SUCCESS:
                        //这里我们获取服务器返回给我们的文件数据，这个只是头内容
                        var dataArr = JSON.parse(serverData.msg_title);
                        //这个是我们获取服务器的标签集合，注意是list<obj>类型
                        var tagsArr = JSON.parse(serverData.msg_tags);
                        //这个是我们的标题头
                        var headerArr = JSON.parse(serverData.msg_header);
                        IndexKey.msg_header_index = serverData.msg_header_index;
                        IndexKey.msg_title_index = serverData.msg_title_index;
                        IndexKey.msg_header_context_index = IndexKey.msg_title_index;
                        document.getElementById("_webBody").innerHTML = serverData.html;
                        CreateVue(dataArr,tagsArr,headerArr);
                    break;
                case ZfraObjects.ServerType.NULL:
                        ZfraTools.showWebError();
                    break;
                default:
                        ZfraTools.showServerError();
                    break;
                }
                //ZfraObjects.lock.lock_resp_div = false;
            } 
        };
        ZfraTools.xhttpGetSend(xhttp,["type","value"],[ZfraObjects.WebType.INDEXCONTEXT,IndexType[index]],false);
        ZfraObjects.lock.lock_resp_div = false;
    }
    );
}
function CreateVue(dataArr,tagsArr,headerArr) {
   //arr的长度代表我们有多少个div的盒子
   //动态设置父窗口的高度
   /*规定一行中最多显示12个div盒子*/
   var _webBody = document.getElementById("_webBody");
   _webBody.style.height = `${(dataArr.length + 5)*103}px`;
   var datas = new Array();
   IndexKey.tags = new Array(dataArr.length);
   //遍历数组
   for(var i = 0;i < dataArr.length; ++i) {
        var resTags = [];
        var tags = tagsArr[i].stags.split(",");
        //存储我们的数组                                   
        IndexKey.tags[i] = tags;
        //元素超过5个
        if(tags.length > 5) {
            //只截取前5个数组
            resTags = tags.slice(0, 5);
        }  else resTags = tags;
        datas.push(
            {
                title:dataArr[i],
                tags: resTags,
                time:tagsArr[i].time
            }
        )
   }
   //button-tag-0-2
    new Vue({
        el:`#_textBody`,
        data: {
            //注册我们的数组
            items:datas
        }
    });
    //这个是我们标题头的索引
    new Vue({
        el:`#_li-header`,
        data: {
            //注册我们的数组
            items:headerArr
        }
    });
    for(var i = 0;i < dataArr.length; ++i) {
        //这个地方我们不能写在前面，因为元素还没有初始化成功，只要在调用vue之后
        //才会有这些对应的元素
        //如果我们的标签不超过5个，隐藏掉我们的按钮
        var tag_pre = document.getElementById(`tag-pre${i}`);
        var tag_next = document.getElementById(`tag-next${i}`);
        if(IndexKey.tags[i].length > 5) {
            //设置链接显示
            tag_pre.style.opacity = "1";
            tag_next.style.opacity = "1";
            tag_pre.style.pointerEvents = "auto";
            tag_next.style.pointerEvents = "auto";
            //我们在这个地方给链接绑定点击事件
            //这个地方的i参数通过闭包传进去，闭包的意思是不同作用域中访问 一个变量
            (function(i) {
                tag_pre.addEventListener("click", function() {
                    var first_button_tag = document.getElementById(`_button-tag-${i}-0`);
                    //查询不到这个值
                    var index = -1;
                    if((index = IndexKey.tags[i].indexOf(first_button_tag.value)) < 0) return;
                    //如果第一个元素和数组第一个元素相等，说明已经到头
                    if(first_button_tag.value == IndexKey.tags[i][0]) {
                        ZfraTools.showErrorDiv("前面已经没有拉~(*^ω^*)");
                        return;
                    }
                    first_button_tag.value = IndexKey.tags[i][index - 1];
                    //设置button按钮的值，最大5个
                    for(var j = 1;j < 5; ++j) {
                        ++index;
                        var _button_tag = document.getElementById(`_button-tag-${i}-${j}`);
                        _button_tag.value = IndexKey.tags[i][index - 1];
                    }

                });
                tag_next.addEventListener("click", function() {
                    //最多不超过5个button
                    var last_button_tag = document.getElementById(`_button-tag-${i}-4`);
                    //查询不到这个值
                    var index = -1;
                    if((index = IndexKey.tags[i].indexOf(last_button_tag.value)) < 0) return;
                    //如果第一个元素和数组第一个元素相等，说明已经到头
                    if(last_button_tag.value == IndexKey.tags[i][IndexKey.tags[i].length - 1]) {
                        ZfraTools.showErrorDiv("后面已经没有拉~(*^ω^*)");
                        return;
                    }
                    last_button_tag.value = IndexKey.tags[i][index + 1];
                    //设置button按钮的值，最大5个
                    for(var j = 3;j >= 0; --j) {
                        --index;
                        var _button_tag = document.getElementById(`_button-tag-${i}-${j}`);
                        _button_tag.value = IndexKey.tags[i][index + 1];
                    }

                });
            })(i);
        } else {
            tag_pre.style.opacity = "0";
            tag_next.style.opacity = "0";
            tag_pre.style.pointerEvents = "none";
            tag_next.style.pointerEvents = "none";
        }
    }
   // ZfraTools.createVueObject("paging");
    ZfraTools.createVueObject("el-search");
    addEvent();
}
function setColor(obj,flag,flag2) {
    if(typeof(flag2) == "undefined") flag2 = true;
    if(flag) {
        obj.style.color = "rgb(248, 238, 96)";
        obj.style.backgroundColor = "rgba(70, 68, 68, 0.8)";
        if(flag2) ZfraTools.addClass(obj,"key_best");
    } else {
        //重置
        obj.style.color = "rgb(207, 207, 121)";
        //设置背景透明
        obj.style.backgroundColor = "transparent";
        if(flag2) ZfraTools.removeClass(obj,"key_best");
    }
} 
function getExceptionUlValue(value) {
    if(value == "&gt;&gt;") value = ">>";
    if(value == "&lt;&lt;") value = "<<";
    return value;
}
function setExceptionUltContext(str) {
    str = getExceptionUlValue(str);
    IndexKey.msg_header_context = (str ==">>" || str == "<<") ? "" : str;
}
function addEvent() {
    var str = document.getElementById(`li-header${IndexKey.msg_header_index}`).innerHTML;
    setExceptionUltContext(str);
    //添加指定的元素事件
    const exceptionUl_max = 8;
    for(var i = 0; i < exceptionUl_max; ++i) {
        var li_header = document.getElementById(`li-header${i}`);
        //添加高亮效果
        if(i == IndexKey.msg_header_index) setColor(li_header,true);
        //鼠标移入事件
        li_header.addEventListener("mouseenter",function() {
            //没有的话我们再添加事件
            if(ZfraTools.hasClass(this,"key_best")) return;
            setColor(this,true,false);
        });
        //鼠标移出事件
        li_header.addEventListener("mouseleave",function() {
            if(ZfraTools.hasClass(this,"key_best")) return;
            setColor(this,false,false);

        });
        li_header.addEventListener("click", function() {
                if(ZfraObjects.lock.lock_resp_div) return;
                ZfraObjects.lock.lock_resp_div = true;
                var value = getExceptionUlValue(this.innerHTML);
                var xhttp = ZfraTools.xhttpCreate();
                //获取索引
                var msg_header_index = (value ==">>" || value == "<<") ? IndexKey.msg_header_index : parseInt(this.id[this.id.length - 1]);
                xhttp.onreadystatechange = function(){
                    // 如果请求成功
                    if (this.readyState == 4 && this.status == 200)  {
                        var serverData = JSON.parse(this.responseText);
                        switch(serverData.type) {
                        case ZfraObjects.ServerType.ERROR:
                            alert(serverData.msg);
                          break;
                        case ZfraObjects.ServerType.SUCCESS:
                            
                            if(serverData.arrow) {
                                //这个是数组
                                var msg_header = JSON.parse(serverData.msg_header);
                                IndexKey.msg_header_index = serverData.msg_header_index;
                                for(var j = 0; j < exceptionUl_max; ++j) {
                                    var li_header = document.getElementById(`li-header${j}`);
                                    li_header.innerHTML = msg_header[j];
                                    //上色
                                    li_header.innerHTML == IndexKey.msg_header_context ? setColor(li_header,true) : setColor(li_header,false);
                                }
  
                            } else {
                                for(var j = 0; j < exceptionUl_max; ++j) {
                                    var old_li_header = document.getElementById(`li-header${j}`);
                                    setColor(old_li_header,false);
                                }
                                IndexKey.msg_header_context_index = serverData.msg_header_index;
                                var new_li_header = document.getElementById(`li-header${IndexKey.msg_header_context_index}`);
                                setColor(new_li_header,true);
                                //设置我们的str
                                setExceptionUltContext(new_li_header.innerHTML);
                            }  
                            break;
                        case ZfraObjects.ServerType.RETRY:
                            ZfraTools.showErrorDiv(serverData.msg);
                            break;
                        default:
                            ZfraTools.showServerError();
                            break;
                        }
                    } 
                };
               ZfraTools.xhttpGetSend(xhttp,["type","index","value"],[ZfraObjects.WebType.HEADERINDEX,msg_header_index,value],false);
               ZfraObjects.lock.lock_resp_div = false;
        });
    }
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
function loadEvent() {
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
}
function changeElementUi() {
    var _el_webMeun = document.getElementById("_el-webMeun");
    //修改element的默认样式
    _el_webMeun.style.backgroundColor = "rgba(54, 49, 49, 0.7)";
}
window.onload = function() {
    loadMusic(false);
    loadVueObject();
    loadBg();
    loadEvent();
    changeElementUi();
}
window.addEventListener("load", function() {
  
});