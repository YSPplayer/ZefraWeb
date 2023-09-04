//存放我们索引的对象
var IndexType = {
    "1":"Trends",
    "2":"Program",
    "2-1":"Exception",
    "2-2":"Tool",
    "2-3":"Course",
    "3":"Noumenon",
    "4":"Entertainment",
    "4-1":"GameResources",
    "4-2":"NovaAi",
    "4-3":"ChatGpt"
};
var _Vue = {
    vue_chat:null,
    vue_index:-1,
    last_index:""

}  
var IndexKey = {
    msg_header_index:0,
    msg_header_context_index:0,
    msg_title_index:-1,
    msg_header_context:"",
    //存放我们的标签数组
    tags:[],
    //如果传入的数据多于12个，我们分开存储
    //这里我们用二维数组存储，方便索引
    remainingData: {
        dataArr:null,
        tagsArr:null
    },
    //这个是我们菜单的索引
    MenuIndexType:"",
    //需要下载的文件名:
    downLoadName:""

} 
var _Html = {
    select_option1:"",
    select_option2:"",
    color:"#000000",
    url:"",//存放服务器返回的图片路径
    add_html:"",//存放服务器添加文章时的代码 
    add_header:"",//存放服务器添加文章时的头代码 
    index:-1,//这个是我们具体的文章索引
    html:""//这个存放我们页面的内容数据
}
function setSelectOptionColor() {
    //获取我们所有的ul标签
    const element_uls = document.querySelectorAll("body .el-select-dropdown .el-scrollbar__view");
    //获取当前标签下的所有子标签
    for(let j = 0; j < element_uls.length; j++) {
        var element_li = element_uls[j].querySelectorAll("li"); 
        for (let i = 0; i < element_li.length; i++) {
            //获取当前Li下的span标签
            const element_span = element_li[i].querySelector("span");
            if(element_span == null) continue;
            if(element_span.innerText === _Html.select_option1 
               || element_span.innerText === _Html.select_option2) {
            //修改颜色
                element_span.style.color = "rgb(207, 207, 121)";
            } else {
                element_span.style.color = "#606266";
            }
        }
    }
}
function loadAdd_Html_Header() {
    var vue_option = new Vue({
        //获得邮箱
        el:`#option_title1`,
        data() {
            return {
              options: [{
                value: '1',
                label: 'H1'
              }, {
                value: '2',
                label: 'H2'
              }, {
                value: '3',
                label: 'H3'
              }, {
                value: '4',
                label: 'H4'
              }, {
                value: '5',
                label: 'H5'
              }],
              value: ''
            }
        },
        methods:{
           selectChange:function(item) {
                //把我们选择的数据变成其他颜色
                //获取数组
                var options = this.options;
                //获取我们当前被选中的元素
                options = options.filter(option => option.value === item);
                if(options.length <= 0) return;
                _Html.select_option1 = options[0].label;
                setSelectOptionColor();
            }
        }
        
    });
    var vue_option2 = new Vue({
        //获得邮箱
        el:`#option_title2`,
        data() {
            return {
              options: [{
                value: '1',
                label: 'c'
              }, {
                value: '2',
                label: 'c++'
              }, {
                value: '3',
                label: 'c#'
              }, {
                value: '4',
                label: 'java'
              }, {
                value: '5',
                label: 'javascript'
              }, {
                value: '6',
                label: 'lua'
              }, {
                value: '7',
                label: 'python'
              }, {
                value: '8',
                label: 'xml'
              }, {
                value: '9',
                label: 'html'
              }],
              value: ''
            }
        },
        methods:{
            selectChange:function(item) {
                //把我们选择的数据变成其他颜色
                //获取数组
                var options = this.options;
                //获取我们当前被选中的元素
                options = options.filter(option => option.value === item);
                if(options.length <= 0) return;
                _Html.select_option2 = options[0].label;
                setSelectOptionColor();
           }
        }
    });
  var title_a = document.getElementById("title_a");
  var bold_a = document.getElementById("bold_a");
  var color_text_a = document.getElementById("color_text_a");
  var code_a = document.getElementById("code_a");
  var pics_a = document.getElementById("pics_a");
  var upload_input = document.getElementById("upload_input");
  var center_a = document.getElementById("center_a");
  var post_a = document.getElementById("post_a");
  post_a.addEventListener("click",function() {
    if (confirm("确定上传当前页面的信息嘛？")) {
        //这个是上传
       if(this.innerText === "上传") {
            ZfraTools.sendMessageToChildHtml( {
                code:'post',
                key:IndexKey.MenuIndexType
            });
       } else {
        //这里修改更新
        ZfraTools.sendMessageToChildHtml( {
            code:'postupdate',
            key:[_Html.index,IndexKey.MenuIndexType]
        });
       }

    } 
  });
  center_a.addEventListener("click",function() {
    // 在主 html 中发送消息
   ZfraTools.sendMessageToChildHtml( {
        code:'center',
        key:null
    });
  }); 
  pics_a.addEventListener("click",function() {
        upload_input.click();
        return;
  });
  upload_input.addEventListener('change', function() {
    if(ZfraObjects.lock.lock_resp_div) return;
    if (upload_input.files && upload_input.files[0]) {
      // var blobUrl = URL.createObjectURL(upload_input.files[0]);
        //首先将我们的图片数据上传到服务器上
        ZfraObjects.lock.lock_resp_div = true;
        _Html.url = "";
        var xhttp = ZfraTools.xhttpCreate();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                var serverData = JSON.parse(this.responseText);
                switch(serverData.type) {
                    case ZfraObjects.ServerType.ERROR://错误信息
                        ZfraTools.showErrorDiv("上传到服务器的图片出现错误啦~");
                        break;
                    case ZfraObjects.ServerType.SUCCESS:
                        _Html.url = serverData.msg;
                        break;
                    default:
                        ZfraTools.showServerError();
                        break;
                }
            }
        };
        //上传二进制文件使用
        var formData = new FormData();
        formData.append('image', upload_input.files[0]);
        ZfraTools.xhttpPostBinarySend(xhttp,formData,false);
        ZfraObjects.lock.lock_resp_div = false;
        if(_Html.url === "") {
            return;
        }
        ZfraTools.sendMessageToChildHtml({
            code:'image',
            key:`..\\${ZfraObjects.pathKey}\\${_Html.url}`
        });
    }
  });
  color_text_a.addEventListener("click",function() {
    // 在主 html 中发送消息
    ZfraTools.sendMessageToChildHtml({
            code:'color',
            key:_Html.color
        });
    });
  title_a.addEventListener("click",function() {
    // 在主 html 中发送消息
    if(_Html.select_option1.length <= 0) return;
    ZfraTools.sendMessageToChildHtml({
            code:'title',
            key:_Html.select_option1
        });
    });
  code_a.addEventListener("click",function() {
    // 在主 html 中发送消息
    if(_Html.select_option2.length <= 0) return;
    ZfraTools.sendMessageToChildHtml({
            code:'code',
            key:_Html.select_option2
        });
    });
  bold_a.addEventListener("click",function() {
        // 在主 html 中发送消息
       ZfraTools.sendMessageToChildHtml( {
            code:'bold',
            key:null
        });
  });
  const colorPicker = document.querySelector('#color-picker'); // 获取颜色选择器元素
    colorPicker.addEventListener('input', (event) => {
        //我们的颜色选择器选择颜色变化时触发
        _Html.color = event.target.value; // 获取目前选中的颜色值
    });
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
function createCardVue(id,flag) {
    return new Vue({
        el:id,
        data() {
            return {
                cards: []
            };
        },
        mounted(){
            this.$nextTick(() => {
                // 在 `nextTick` 中执行滚动条的设置
                //该函数在渲染结束后调用执行，一般执行无法使用
                if(flag){
                  this.scrollToBottom();
                }
            });
        },
        methods:{
            scrollToBottom() {
                var rightDivs = document.getElementsByClassName("right");
                rightDivs[0].scrollTo(0,rightDivs[0].scrollHeight - rightDivs[0].clientHeight);
            },
            leftScroll() {
            /*
            实现原理：
                leftDiv.scrollTop 是我们滚动后距离顶部的距离
                leftDiv.scrollHeight - leftDiv.clientHeight 是我们滚动条的最大长度
                对 leftDiv.scrollTop 取剩下的部分就是相反的方向
            */
                var rightDivs = document.getElementsByClassName("right");
                var leftDivs = document.getElementsByClassName("left");
                var rightDiv = rightDivs[0];
                var leftDiv = leftDivs[0];
                var scrollPosition = leftDiv.scrollHeight - leftDiv.clientHeight - leftDiv.scrollTop;
                rightDiv.scrollTo(0, scrollPosition);
            },
            rightScroll() {
                var rightDivs = document.getElementsByClassName("right");
                var leftDivs = document.getElementsByClassName("left");
                var rightDiv = rightDivs[0];
                var leftDiv = leftDivs[0];
                var scrollPosition = rightDiv.scrollHeight - rightDiv.clientHeight - rightDiv.scrollTop;
                leftDiv.scrollTo(0, scrollPosition);
            },
            handleMouseover(item) {
                var card = document.getElementById(item.id);
                var divChild = card.getElementsByTagName('div')[0];
                var imgChild = card.getElementsByTagName('img')[0];
                ZfraTools.setElementEnable(divChild,false);
                imgChild.style.filter = 'brightness(100%)';
            },
            handleMouseout(item) {
                var card = document.getElementById(item.id);
                var divChild = card.getElementsByTagName('div')[0];
                var imgChild = card.getElementsByTagName('img')[0];
                ZfraTools.setElementDisable(divChild,false);
                imgChild.style.filter = 'brightness(70%)';
            },
            handleClick(item) {
                //向服务器请求内容
                var xhttp = ZfraTools.xhttpCreate();
                xhttp.onreadystatechange = function(){
                    if (this.readyState == 4 && this.status == 200)  {
                        var serverData = JSON.parse(this.responseText);
                        switch(serverData.type) {
                            case ZfraObjects.ServerType.ERROR:
                                alert(serverData.msg);
                            case ZfraObjects.ServerType.SUCCESS:
                                window.open(`${ZfraObjects.formPathOrigin}//ZefraWeb//${serverData.url}`);
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
                ZfraTools.xhttpGetSend(xhttp,["type","index"],[ZfraObjects.WebType.GETBOOK,item.code],true);
            }

        }  
    });
}
function handleSelect(index) {
    if(_Vue.last_index == index) return;
    _Vue.last_index = index;
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
                    //存放我们的菜单选项，方便后面的操作
                    IndexKey.MenuIndexType = IndexType[index];
                    if(IndexKey.MenuIndexType === "Noumenon") {
                        //重置主页面的高度
                        document.getElementById("_webBody").style.height = `904px`;
                        ZfraTools.reloadHtml(document.getElementById("_webBody"),ZfraTools.base64UrlDecode(serverData.html));
                        //创建vue对象
                        var vue_left = createCardVue("#_left",true);
                        var vue_right = createCardVue("#_right",false);
                        //初始化2个元素
                        var vuei = 0;
                        var tvalue = 0;
                        for (vuei = 0,tvalue = 0; vuei < ZfraObjects.BookLeft.length; vuei++,tvalue+=50) {
                            const bookLeft = ZfraObjects.BookLeft[vuei];
                            var vdata = {};
                            vdata.id = `cardleft_${vuei}`;
                            vdata.img = `${ZfraObjects.formPathOrigin}//ZefraWeb//harticle//n_book//bg//${bookLeft[0]}.jpg`;
                            vdata.book = `书名：${bookLeft[1]}`;
                            vdata.author = `作者：${bookLeft[2]}`;
                            vdata.code = bookLeft[0];
                            if(vuei == 0) {
                                vdata.topValue = "20px";
                            } else {
                                vdata.topValue = `${tvalue}%`;
                            }
                            vue_left.cards.push(vdata);
                        }
                        for (vuei = 0,tvalue = 0; vuei < ZfraObjects.BookRight.length; vuei++,tvalue+=50) {
                            const bookRight = ZfraObjects.BookRight[vuei];
                            var vdata = {};
                            vdata.id = `cardright_${vuei}`;
                            vdata.img = `${ZfraObjects.formPathOrigin}//ZefraWeb//harticle//n_book//bg//${bookRight[0]}.jpg`;
                            vdata.book = `书名：${bookRight[1]}`;
                            vdata.author = `作者：${bookRight[2]}`;
                            vdata.code = bookRight[0];
                            if(vuei == 0) {
                                vdata.topValue = "20px";
                            } else {
                                vdata.topValue = `${tvalue}%`;
                            }
                             vue_right.cards.push(vdata);
                        }
                    } else if(IndexKey.MenuIndexType === "Trends") {
                        //重置主页面的高度
                        document.getElementById("_webBody").style.height = `904px`;
                        var schatArr = JSON.parse(serverData.data);
                        //先加载html
                        ZfraTools.reloadHtml(document.getElementById("_webBody"),ZfraTools.base64UrlDecode(serverData.html));
                        //创建vue对象
                        var vue_chat = new Vue({
                            el:"#_wchat",
                            data() {
                                return {
                                    cusers:[]
                                };
                            },
                            mounted(){
                                this.$nextTick(() => {
                                    //根据文字尺寸动态修改div宽度
                                    this.setWidth();
                                });
                            },
                            methods:{
                                setWidth() {
                                    var elements = document.querySelectorAll('.context');
                                    elements.forEach(el => {
                                        if(el.offsetWidth > 450) el.style.width = "450px";
                                    });
                                },
                                imgClick(id) {
                                    if (!confirm("确定删除当前数据嘛？")) return;
                                    //删除当前所在元素
                                    var xhttp = ZfraTools.xhttpCreate();
                                    xhttp.onreadystatechange = function() {
         
                                        if (this.readyState == 4 && this.status == 200) {
                                            var serverData = JSON.parse(this.responseText);
                                            switch(serverData.type) {
                                                case ZfraObjects.ServerType.ERROR://错误信息
                                                    ZfraTools.showErrorDiv(serverData.msg);
                                                    break;
                                                case ZfraObjects.ServerType.SUCCESS:
                                                    ZfraTools.showSuccessDiv(serverData.msg);
                                                    //修改当前网页中的动态数据
                                                    for (var i = 0; i < _Vue.vue_chat.cusers.length; i++) {
                                                        var el = _Vue.vue_chat.cusers[i];
                                                        if(el.id != id) continue;
                                                        //移除所在索引元素
                                                        _Vue.vue_chat.cusers.splice(i, 1); 
                                                        break;
                                                    }
                                                    --_Vue.vue_index;
                                                    break;
                                                default:
                                                    ZfraTools.showServerError();
                                                    break;
                                            }
                                        }
                                    };
                                    ZfraTools.xhttpGetSend(xhttp,["type","id"],[ZfraObjects.WebType.DELETECHAT,id],true);
                                }
                            }
                        });
                        _Vue.vue_chat = vue_chat;
                        //存储索引
                        _Vue.vue_index = serverData.index;
                        var send_el = document.getElementById("send_el");
                        var wchat = document.getElementById("_wchat");
                        //设置鼠标滚动条滚动到底部的事件
                        wchat.addEventListener("scroll",function(){
                            if (this.scrollTop + this.clientHeight >= this.scrollHeight) {
                                // 滚动条滚动到底部时触发的代码逻辑
                                //这里加载新的数据
                                var xhttp = ZfraTools.xhttpCreate();
                                xhttp.onreadystatechange = function(){
                                    if (this.readyState == 4 && this.status == 200)  {
                                        var serverData = JSON.parse(this.responseText);
                                        switch(serverData.type) {
                                        case ZfraObjects.ServerType.ERROR:
                                           ZfraTools.showErrorDiv(serverData.msg);
                                          break;
                                        case ZfraObjects.ServerType.SUCCESS:
                                            var schatArr = JSON.parse(serverData.data);
                                            _Vue.vue_index = serverData.index;
                                            for (var si= 0; si < schatArr.length; si++) {
                                                var chat = schatArr[si];
                                                _Vue.vue_chat.cusers.push({
                                                    id:chat.id,
                                                    url:chat.url,
                                                    ttext:`${chat.name}【${ZfraTools.formatTimestamp(chat.time)}】`,
                                                    context:chat.txt,
                                                });
                                            }
                                            break;
                                        default:
                                            ZfraTools.showServerError();
                                            break;
                                        }
                                    } 
                                };
                                ZfraTools.xhttpGetSend(xhttp,["type","index"],[ZfraObjects.WebType.GETCHAT,_Vue.vue_index],false);
                            }
                        });
                        send_el.addEventListener("click",function() {
                            var ssend_text = document.getElementById("ssend_text");
                            var value = ssend_text.value;
                            if(value <= 0){
                                ZfraTools.showErrorDiv("信息不能为空~");
                                return;
                            } 
                            var xhttp = ZfraTools.xhttpCreate();
                            (function(tvalue,sstext){
                            xhttp.onreadystatechange = function() {
                                if (this.readyState == 4 && this.status == 200) {
                                    var serverData = JSON.parse(this.responseText);
                                    switch(serverData.type) {
                                        case ZfraObjects.ServerType.ERROR://错误信息
                                            ZfraTools.showErrorDiv(serverData.msg);
                                            break;
                                        case ZfraObjects.ServerType.SUCCESS:
                                            //重置文本
                                            sstext.value = "";
                                            ZfraTools.showSuccessDiv(serverData.msg);
                                            _Vue.vue_chat.cusers.push({
                                                id:serverData.id,
                                                url:serverData.url,
                                                ttext:`${serverData.name}【${ZfraTools.formatTimestamp(serverData.time)}】`,
                                                context:tvalue,
                                            });
                                            ++_Vue.vue_index; 
                                            break;
                                        default:
                                            ZfraTools.showServerError();
                                            break;
                                    }
                                }
                            };})(value,ssend_text);
                            //发送数据到服务器
                            ZfraTools.xhttpPostSend(xhttp,{
                                type:ZfraObjects.WebType.SAVECHAT,
                                text:encodeURIComponent(value)
                            },true);
                            
                        });
                        for (var ci = 0; ci < schatArr.length; ci++) {
                            var chat = schatArr[ci];
                            vue_chat.cusers.push({
                                id:chat.id,
                                url:chat.url,
                                ttext:`${chat.name}【${ZfraTools.formatTimestamp(chat.time)}】`,
                                context:chat.txt,
                            });
                            
                        }

                    } else {
                        //这里我们获取服务器返回给我们的文件数据，这个只是头内容
                        var dataArr = JSON.parse(serverData.msg_title);
                        //这个是我们获取服务器的标签集合，注意是list<obj>类型
                        var tagsArr = JSON.parse(serverData.msg_tags);
                        //这个是我们的标题头
                        var headerArr = JSON.parse(serverData.msg_header);
                        IndexKey.msg_header_index = serverData.msg_header_index;
                        IndexKey.msg_title_index = serverData.msg_title_index;
                        IndexKey.msg_header_context_index = IndexKey.msg_header_context_index;
                        ZfraTools.reloadHtml(document.getElementById("_webBody"),serverData.html);
                        CreateVue(dataArr,tagsArr,headerArr);
                    }
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
function loadVueObject() {
    ZfraTools.createVueObjectWithMethods("webMeun",
    // 获取我们的索引，根据索引做一些事情
    handleSelect
    );
}
//把多余的数据存储到我们的数组中
function setDataArr(dataArr,tagsArr) {
    //一页只显示12个内容
     const ONE_PAGE_NUMBER = 12;
     var min = 0;
     var max = 0;
     var pages = Math.ceil(dataArr.length/ONE_PAGE_NUMBER);
     IndexKey.remainingData.dataArr = new Array(pages);
     IndexKey.remainingData.tagsArr = new Array(pages);
    //我们所有的数据存储到二维数组中    
     for(var i = 0;i < pages; ++i) {
        max = min + ONE_PAGE_NUMBER;
        if(max > dataArr.length) max = dataArr.length;
        IndexKey.remainingData.dataArr[i] = dataArr.slice(min,max);
        IndexKey.remainingData.tagsArr[i] = tagsArr.slice(min,max);
        min = max;
    }
    //page比1小的话，我们就不显示转换索引页面
    if(pages <= 1) {
        ZfraTools.setElementDisable(document.getElementById("paging"));
    } else {
        ZfraTools.setElementEnable(document.getElementById("paging"));
    }
  
}
//设置我们的菜单栏下方内容
function setDataContext(dataArr,tagsArr) {
//arr的长度代表我们有多少个div的盒子
   //动态设置父窗口的高度
   /*规定一行中最多显示12个div盒子*/
   //这里我们放置12个元素盒子，不管有无都这样放，方便页面索引
   const context_len = 12;
   const body_len = dataArr.length;
   var _webBody = document.getElementById("_webBody");
   var _textBody = ZfraTools.getElementByClassName("textBody");
   //+2是因为和索引页留一定的距离
   var body_hight = (body_len + 2)*103;
   //让我们的页面最少是充满一个页面
   body_hight =  body_hight  < 904 ? 904 : body_hight;
   _webBody.style.height = `${body_hight}px`;
   //-70是因为一个是上面放置，这个是下面放置
   _textBody.style.height = `${body_hight - 70}px`;
   var datas = new Array();
   IndexKey.tags = new Array(dataArr.length);
   //遍历数组
   for(var i = 0;i < context_len; ++i) {
        if(i >= dataArr.length) {//超出部分置空处理
            IndexKey.tags[i] = [];
            datas.push(
                {
                    title:"",
                    tags: "",
                    time:0
                }
            )
        } else {
            var resTags = [];
            var stags = tagsArr[i].stags;
            var tags = stags.split(",");
            //存储我们的数组                                   
            IndexKey.tags[i] = tags;
            //元素超过5个
            if(tags.length > 5) {
                //只截取前5个数组
                resTags = tags.slice(0, 5);
            }  else {
                resTags = tags;
                var times = 5- resTags.length;
                while(times > 0) {
                    --times;
                    //传递空元素，占位
                    resTags.push("");
                }
            }
            datas.push(
                {
                    title:dataArr[i],
                    tags: resTags,
                    time:tagsArr[i].time
                }
            )
        }
   }
   return datas;
}
//隐藏掉我们的进度条
function disAbleEls(){
    if (IndexKey.MenuIndexType === "Exception") return;
    var els = document.querySelectorAll(".el-progress");
    els.forEach(function(el) {
        if(el != null) {
            ZfraTools.setElementDisable(el,false);
        }
     })

}
//设置我们内容上的标签,flag为true表示我们第一次进这个函数
//也就意味着给每一个控件元素增加一个事件，否则不添加事件
function setDataTags(len,flag) {
    if(typeof(flag) == "undefined") flag = true;
    const button_len = 5;
    const context_len = 12;
    if(flag) {
         //这个地方我们再设置一下标题连接被点击时的事件
         //获取所有元素
         var elements = document.querySelectorAll('.aSearchTitle');
         elements.forEach(function(element) {
            if(element != null) {
                element.addEventListener("click",function() {
                    if(ZfraObjects.lock.lock_resp_div) return;
                    ZfraObjects.lock.lock_resp_div = true;
                    //添加事件
                    IndexKey.downLoadName = "";
                    var xhttp = ZfraTools.xhttpCreate();
                    xhttp.onreadystatechange = function(){
                        if (this.readyState == 4 && this.status == 200)  {
                            var serverData = JSON.parse(this.responseText);
                            switch(serverData.type) {
                                case ZfraObjects.ServerType.ERROR:
                                    ZfraTools.showErrorDiv(serverData.msg);
                                  break;
                                case ZfraObjects.ServerType.SUCCESS:
                                    //然后加载到主页面
                                    var _webBody = document.getElementById("_webBody");
                                    var _textBody = document.getElementById("_textBody");
                                    ZfraTools.reloadHtml(_textBody,serverData.msg);
                                    //重置body的高
                                    _webBody.style.height = "904px";
                                    _textBody.style.height = "834px";
                                    //document.getElementById("_textBody").innerHTML = serverData.msg;
                                    //存储一下数据
                                    _Html.html = serverData.html;
                                    _Html.add_html = serverData.add_html;
                                    _Html.add_header = serverData.add_header;
                                    _Html.index = serverData.index;
                                    ZfraTools.createVueObject(`_optionBody`);
                                    //添加事件
                                    for(var i = 0; i < 4; ++i) {
                                        var elements = document.getElementsByClassName(`optionBody_${i}`);
                                        elements[0].addEventListener("click",function() {
                                            var className = this.classList.item(0);
                                            if(className === "optionBody_0") {
                                                ZfraTools.sendMessageToChildHtml( {
                                                    code:'update',
                                                    key:[_Html.index,_Html.add_html,IndexKey.MenuIndexType]
                                                });
                                                //插入我们的头代码
                                                ZfraTools.reloadHtml(document.getElementById("_searchMainBox"),_Html.add_header);
                                                //创建环境
                                                loadAdd_Html_Header();
                                                //修改上传的文字
                                                document.getElementById("post_a").innerHTML = "<span class=\"el-icon-upload2\"></span>修改";
                                                //隐藏我们的元素
                                                ZfraTools.setElementDisable(document.getElementById("_optionBody"));
                                            }
                                            else if(className === "optionBody_1") {
                                                //对当前页面的文章进行添加操作
                                                if(_Html.add_html === "" || _Html.add_header === "") return;
                                                ZfraTools.sendMessageToChildHtml( {
                                                    code:'add',
                                                    key:_Html.add_html
                                                });
                                                //插入我们的头代码
                                                ZfraTools.reloadHtml(document.getElementById("_searchMainBox"),_Html.add_header);
                                                //创建环境
                                                loadAdd_Html_Header();
                                                ZfraTools.setElementDisable(document.getElementById("_optionBody"));
                                            }
                                            else if(className === "optionBody_2") {
                                                if (confirm("确定删除当前数据嘛？")) {
                                                    ZfraTools.sendMessageToChildHtml( {
                                                        code:'delete',
                                                        key:[_Html.index,IndexKey.MenuIndexType]
                                                    });
                                                }
                                            }
                                            else if(className === "optionBody_3") {
                                                //这个是下载
                                                if(IndexKey.MenuIndexType === "Tool" &&
                                                IndexKey.downLoadName != "") {
                                            
                                                    //下载我们的文件
                                                    var url = `${ZfraObjects.formPathOrigin}//ZefraWeb//harticle//resources//${IndexKey.downLoadName}`; 
                                                    var xhttp = new XMLHttpRequest();
                                                    xhttp.open("GET", url, true);
                                                    //二进制文件响应
                                                    xhttp.responseType = "blob";
                                                    xhttp.onload = function() {
                                                        if (xhttp.status === 200) {
                                                          var blob = xhttp.response;
                                                          saveAs(blob, IndexKey.downLoadName); //使用FileSaver.js保存我们已经下载的文件
                                                        }
                                                    };
                                                    xhttp.send();
                                                }
                                            }
                                        });
                                    }
                                    if(IndexKey.MenuIndexType !== "Tool") {
                                        //隐藏掉我们的下载元素
                                        var div = document.getElementsByClassName("optionBody_3");
                                        ZfraTools.setElementDisable(div[0],false);
                                    }
                                   break;
                                default:
                                    ZfraTools.showServerError();
                                 break;
                            }
                        }
                    }
                    //这个地方匹配我们的文件，是下载的名称
                    var input = element.innerHTML;
                    if(IndexKey.MenuIndexType === "Tool" ) {
                        var regex = /【(.*?)】/; // 匹配方括号中的任意字符，非贪婪模式
                        var matches = input.match(regex);
                        if (matches) {
                            IndexKey.downLoadName = matches[1];
                        }
                    }
                    ZfraTools.xhttpPostSend(xhttp,{
                        type:ZfraObjects.WebType.GETARTICLE,
                        index:IndexKey.MenuIndexType,
                        msg:encodeURIComponent(input)
                    },false);
                    ZfraObjects.lock.lock_resp_div = false; 
                });
            }
         });
    }
    //这个是标签的事件
    for(var i = 0;i < context_len; ++i) {
        //这个地方我们不能写在前面，因为元素还没有初始化成功，只要在调用vue之后
        //才会有这些对应的元素
        //如果我们的标签不超过5个，隐藏掉我们的按钮
        //IndexKey.tags[i].length
        for(var j = 0;j < button_len; ++j) {
            var _button_tag = document.getElementById(`_button-tag-${i}-${j}`);
            //给每一个Button添加一个事件，索引效果相同
            if(_button_tag == null) continue;
            if(flag) {
                _button_tag.addEventListener("click",function(){
                    //已经被点击就不触发这个事件
                    if(ZfraObjects.lock.lock_resp_div) return;
                    ZfraObjects.lock.lock_resp_div = true;
                    var value =this.value;
                    var oindex = (IndexKey.msg_header_index == 0 && IndexKey.msg_header_context_index == 0)
                    ? null : IndexKey.msg_header_index > 0 ?
                    ( IndexKey.msg_header_context_index + IndexKey.msg_header_index - 2 ) : IndexKey.msg_header_context_index - 1;
                    if(oindex != null) {
                        searchContext(["type","oindex","tindex","value"],[ZfraObjects.WebType.HEADERINDEX,oindex,IndexKey.MenuIndexType,value]);
                    } else {
                            //是all标签的索引
                        searchContext(["type","oindex","tindex","value"],[ZfraObjects.WebType.HEADERINDEX,value,IndexKey.MenuIndexType,"ALL"]);
                    }
                    //点击完返回页面顶部
                    document.documentElement.scrollTop = 0;
                    ZfraObjects.lock.lock_resp_div = false;
                });
            }
            //如果我们有不存在的标签，也隐藏掉，即元素值为""的标签
            if(IndexKey.tags[i] == "" || IndexKey.tags[i][j] == "") {
                ZfraTools.setElementDisable(_button_tag);
            } else {
                ZfraTools.setElementEnable(_button_tag);
            }
        }
        var tag_pre = document.getElementById(`tag-pre${i}`);
        var tag_next = document.getElementById(`tag-next${i}`);
         //我们在这个地方给链接绑定点击事件
        //这个地方的i参数通过闭包传进去，闭包的意思是不同作用域中访问 一个变量
        if(flag) {
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
        }
        if(IndexKey.tags[i].length > 5) {
            //设置链接显示
            ZfraTools.setElementEnable(tag_pre);
            ZfraTools.setElementEnable(tag_next);
        } else {
            //隐藏掉2个
            ZfraTools.setElementDisable(tag_pre);
            ZfraTools.setElementDisable(tag_next);
        } 
        var textBody = ZfraTools.getElementByClassName(`textBody-${i}`); 
        if(i >= len) {
            //超出了数组索引的部分，我们要隐藏掉控件，这里我们直接隐藏掉主元素
            ZfraTools.setElementDisable(textBody,false);
        }  else {
            ZfraTools.setElementEnable(textBody,false);
        }
    }
}
function CreateVue(dataArr,tagsArr,headerArr,ArrIndex) {
    //只截取前12个数据存储
    //这里我们存储为二维数组
    setDataArr(dataArr,tagsArr);
    //这里进行索引，如果没有索引就说明是默认的
    if(typeof(ArrIndex) == "undefined") {
        ArrIndex = 0;
    }
    dataArr = IndexKey.remainingData.dataArr[ArrIndex];
    tagsArr = IndexKey.remainingData.tagsArr[ArrIndex];
   
   //button-tag-0-2
   //这个是我们的主界面，包括菜单下方内容+页索引
    var datas = setDataContext(dataArr,tagsArr);
    new Vue({
        el:`#_textBody`,
        data: {
            //注册我们的数组
            items:datas
        },
        methods: {
            //点击页面数调用此方法
            handleCurrentChange(newPage) {
               var ArrIndex =  newPage - 1;
               //获取我们对应页码下存储的内容
               var dataArr = IndexKey.remainingData.dataArr[ArrIndex];
               var tagsArr = IndexKey.remainingData.tagsArr[ArrIndex];
               var disableArr = [];
               var datas = setDataContext(dataArr,tagsArr,disableArr);
               // 使用Vue.set方法修改当前对象的items值
               Vue.set(this.$data, "items", datas);
               setDataTags(dataArr.length,false);
               var str = document.getElementById(`li-header${IndexKey.msg_header_index}`).innerHTML;
               setExceptionUltContext(str);
               //点击完返回页面顶部
               document.documentElement.scrollTop = 0;
            },
            //点击前后调用下面这些方法
            handlePrevClick() {
            },
            handleNextClick() {
            }
        }
    });
    //这个是我们标题头的索引
    if(typeof(headerArr) != "undefined") {
        new Vue({
            el:`#_li-header`,
            data: {
                //注册我们的数组
                items:headerArr
            }
        });
    }
    //设置我们内容上的标签
   setDataTags(dataArr.length);
   if(typeof(headerArr) != "undefined") {
       ZfraTools.createVueObjectWithMethods("el-search",function searchClick() {
        var search_text =  document.getElementById("search_text");
        var context = search_text.value;
        if(context.length <= 0) return;
        var oindex = (IndexKey.msg_header_index == 0 && IndexKey.msg_header_context_index == 0)
                    ? null : IndexKey.msg_header_index > 0 ?
                    ( IndexKey.msg_header_context_index + IndexKey.msg_header_index - 2 ) : IndexKey.msg_header_context_index - 1;
        if(oindex == null) oindex = -1;
        searchContext(["type","index","tindex","value","button"],[ZfraObjects.WebType.HEADERINDEX,oindex,IndexKey.MenuIndexType,encodeURIComponent(context),"true"]);
       });
       addEvent();
    }
    //这个是设置我们菜单栏上面选择的框框颜色
    var str = document.getElementById(`li-header${IndexKey.msg_header_index}`).innerHTML;
    setExceptionUltContext(str);
    disAbleEls();
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
//这个是上面部分的菜单事件
function addEvent() {
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
            //已经被点击就不触发这个事件 || ZfraTools.hasClass(this,"key_best")
            if(ZfraObjects.lock.lock_resp_div ) return;
            ZfraObjects.lock.lock_resp_div = true;
            var value = getExceptionUlValue(this.innerHTML);
             //获取索引
            var msg_header_index = (value ==">>" || value == "<<") ? IndexKey.msg_header_index : parseInt(this.id[this.id.length - 1]);
            searchContext(["type","index","tindex","value"],[ZfraObjects.WebType.HEADERINDEX,msg_header_index,IndexKey.MenuIndexType,value]);
            ZfraObjects.lock.lock_resp_div = false;
        });
    }
}
function searchContext(keyArr,valueArr) {
     var xhttp = ZfraTools.xhttpCreate();
     xhttp.onreadystatechange = function(){
         // 如果请求成功
         if (this.readyState == 4 && this.status == 200)  {
             var serverData = JSON.parse(this.responseText);
             switch(serverData.type) {
             case ZfraObjects.ServerType.ERROR:
                ZfraTools.showErrorDiv(serverData.msg);
               break;
             case ZfraObjects.ServerType.SUCCESS:
                //重置我们的搜索
                document.getElementById("search_text").value = "";
                var exceptionUl_max = 7; 
                 if(serverData.arrow) {
                     //这个是数组
                     var msg_header = JSON.parse(serverData.msg_header);
                     IndexKey.msg_header_index = serverData.msg_header_index;
                     for(var j = 0; j < exceptionUl_max; ++j) {
                         var li_header = document.getElementById(`li-header${j}`);
                        // li_header.innerHTML = msg_header[j];
                         ZfraTools.reloadHtml(li_header,msg_header[j]);
                         //上色
                         li_header.innerHTML == IndexKey.msg_header_context ? setColor(li_header,true) : setColor(li_header,false);
                     }

                 } else {
                    if(serverData.msg_header_context_index != null) IndexKey.msg_header_context_index = serverData.msg_header_context_index;
                    if(serverData.msg_title_index != null)  IndexKey.msg_title_index = serverData.msg_title_index;
                     for(var j = 0; j < exceptionUl_max; ++j) {
                         var old_li_header = document.getElementById(`li-header${j}`);
                         setColor(old_li_header,false);
                     }
                     var new_li_header = document.getElementById(`li-header${IndexKey.msg_header_context_index}`);
                     setColor(new_li_header,true);
                     //设置我们的str
                     setExceptionUltContext(new_li_header.innerHTML);
                     var dataArr = JSON.parse(serverData.msg_title);
                     var tagsArr = JSON.parse(serverData.msg_tags);
                     ZfraTools.reloadHtml(document.getElementById("_textBody"),serverData.html);
                    // document.getElementById("_textBody").innerHTML = serverData.html;
                     CreateVue(dataArr,tagsArr);
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
    ZfraTools.xhttpGetSend(xhttp,keyArr,valueArr,false);
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
window.addEventListener('message',  function(event) {
    if (event.origin === ZfraObjects.formPathOrigin) {
        try {
            var data = JSON.parse(event.data);
            var code = data.code;
            var msg = data.data;
            switch(code)
            {
                case "get":
                    ZfraTools.sendMessageToChildHtml(
                        {
                            code:"get",
                            key:_Html.html
                        }
                    );
                    break;
                case "error":
                    ZfraTools.showErrorDiv(msg);
                    break;
                case "success":
                    ZfraTools.showSuccessDiv(msg);
                    break;
                default:
                    break;
    
            }
        } catch (error) {}
    }
}, false);
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
  document.addEventListener('keydown', (event) => {
        //改变颜色
        var color = document.getElementById("color-picker");
        if(color == null) return;
        if (event.ctrlKey && event.code === 'Digit1') {
            event.preventDefault(); 
            color.value = "#FFFFFF";
            _Html.color = color.value;
        }
        else if (event.ctrlKey && event.code === 'Digit2') {
            event.preventDefault(); 
            color.value = "#EA2E2E";
            _Html.color = color.value;
        }
        else if (event.ctrlKey && event.code === 'Digit3') {
            event.preventDefault(); 
            color.value = "#2EEA67";
            _Html.color = color.value;
        }
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
    //加载我们的主页面动态
    handleSelect(1);
    //发送主动新闻请求
    getNews();
}
var News = {
    index1:-1,
    index2:-1,
    Vue:null,
    next_news:""//存储我们下一个所需要存放的新闻
}

function getServerNews(flag) {
    //获取服务器的最新新闻
    var xhttp = ZfraTools.xhttpCreate();
    xhttp.onreadystatechange = async function() {
        if (this.readyState == 4 && this.status == 200) {
            var serverData = JSON.parse(this.responseText);
            switch(serverData.type) {
                case ZfraObjects.ServerType.ERROR://错误信息
                    ZfraTools.showErrorDiv(serverData.msg);
                    break;
                case ZfraObjects.ServerType.SUCCESS:
                    if(flag) {
                        News.Vue.message = serverData.news1;
                        News.next_news =  serverData.news2;
                    } else {
                        News.Vue.message = News.next_news;
                        News.next_news =  serverData.news;
                    }
                    //设置时间
                    var _scrollText = document.getElementById("_scrollText");
                    //每隔5个字符+1秒
                    var time = 14 + Math.floor(News.Vue.message.length / 5) + 1;
                    //修改播放速度
                    _scrollText.style.animationDuration = `${time}s`;
                    break;
                case ZfraObjects.ServerType.RETRY:
                    News.Vue.message = serverData.msg;
                    if(flag) {    
                        News.next_news = News.Vue.message;
                    }
                    break;
                default:
                    ZfraTools.showServerError();
                    break;
            }
        }
    }
    ZfraTools.xhttpGetSend(xhttp,["type","index1","index2","first"],[ZfraObjects.WebType.GETNEWS,News.index1,
    News.index2,flag],true);
}
function getNews() {
    //初始化news的vue
    News.Vue = new Vue({
        el:"#_scrollText",
        data() {
            return {
              message: "暂无更多内容，请耐心等待服务器响应~~"
            }
        }
    })
    var _scrollText = document.getElementById("_scrollText");
    _scrollText.addEventListener('animationend', function(){
        //动画播放完毕
        getServerNews(false);
        ZfraTools.rebroadcast(this,"scrollAnimation",false);
    });
    getServerNews(true);

}
