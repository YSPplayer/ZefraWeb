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
    }

} 
var _Html = {
    select_option1:"",
    select_option2:"",
    color:"#000000",
    url:"",//存放服务器返回的图片路径
    add_html:"",//存放服务器添加文章时的代码 
    add_header:"",//存放服务器添加文章时的头代码 
    index:-1//这个是我们具体的文章索引
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
                label: 'cpp'
              }, {
                value: '3',
                label: 'csharp'
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
                label: 'htmlmixed'
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
                key:null
            });
       } else {
        //这里修改更新
        ZfraTools.sendMessageToChildHtml( {
            code:'postupdate',
            key:_Html.index
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
                        IndexKey.msg_header_context_index = IndexKey.msg_header_context_index;
                        ZfraTools.reloadHtml(document.getElementById("_webBody"),serverData.html);
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
                                    ZfraTools.reloadHtml(document.getElementById("_textBody"),serverData.msg);
                                    //document.getElementById("_textBody").innerHTML = serverData.msg;
                                    //存储一下数据
                                    _Html.add_html = serverData.add_html;
                                    _Html.add_header = serverData.add_header;
                                    _Html.index = serverData.index;
                                    ZfraTools.createVueObject(`_optionBody`);
                                    //添加事件
                                    for(var i = 0; i < 3; ++i) {
                                        var elements = document.getElementsByClassName(`optionBody_${i}`);
                                        elements[0].addEventListener("click",function() {
                                            var className = this.classList.item(0);
                                            if(className === "optionBody_0") {
                                                ZfraTools.sendMessageToChildHtml( {
                                                    code:'update',
                                                    key:[_Html.index,_Html.add_html]
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
                                                        key:_Html.index
                                                    });
                                                }
                                            }
                                        });
                                    }
                                  
                                   break;
                                default:
                                    ZfraTools.showServerError();
                                 break;
                            }
                        }
                    }
                    ZfraTools.xhttpGetSend(xhttp,["type","msg"],[ZfraObjects.WebType.GETARTICLE,element.innerHTML],false);
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
                        searchContext(["type","oindex","value"],[ZfraObjects.WebType.HEADERINDEX,oindex,value]);
                    } else {
                            //是all标签的索引
                        searchContext(["type","oindex","value"],[ZfraObjects.WebType.HEADERINDEX,value,"ALL"]);
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
       ZfraTools.createVueObject("el-search");
       addEvent();
    }
    //这个是设置我们菜单栏上面选择的框框颜色
    var str = document.getElementById(`li-header${IndexKey.msg_header_index}`).innerHTML;
    setExceptionUltContext(str);
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
            searchContext(["type","index","value"],[ZfraObjects.WebType.HEADERINDEX,msg_header_index,value]);
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
                 alert(serverData.msg);
               break;
             case ZfraObjects.ServerType.SUCCESS:
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