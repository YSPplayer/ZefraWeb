var html_elemnet = {
    index:0,
    passCodes : ["get","image","code","post","add","delete","update","postupdate"]
}
var _Vue = {
    vue_times:null,
    vue_tags:null,
}

function getPostTime() {
    return _Vue.vue_times.num1*60 + _Vue.vue_times.num2;
}
function getPostHtml() {
     // 获取 document 中的所有我们需要上传的元素内容
     const elements = document.body.children;
     var html_context = "";
     // 遍历元素并删除指定的标签
     for (let i = 0; i < elements.length; i++) {
        const element = elements[i];
        if(element == null) continue;
         // 跳过的div 元素
        if (element.id === "title_div"
             || element.id === "tags_div"
             || element.id === "time_div") 
             continue;
         //只上传我们写的正文内容进去
        html_context += element.outerHTML;
    }
    return html_context;

}
var init_add_html = function(tags) {
    if(typeof(tags) == "undefined") tags = [];
    _Vue.vue_times = new Vue({
        el:`#time_div`,
        data() {
            return {
              num1: 1,
              num2: 2
            };
          },
          methods: {
          }
    });
    _Vue.vue_tags = new Vue({
        el:`#tags_div`,
        data() {
            return {
            dynamicTags: tags,
            inputVisible: false,
            inputValue: ''
            };
        },
        methods: {
            handleClose(tag) {
            this.dynamicTags.splice(this.dynamicTags.indexOf(tag), 1);
            },
    
            showInput() {
            this.inputVisible = true;
            this.$nextTick(_ => {
                this.$refs.saveTagInput.$refs.input.focus();
            });
            },
    
            handleInputConfirm() {
            let inputValue = this.inputValue;
            if (inputValue) {
                this.dynamicTags.push(inputValue);
            }
            this.inputVisible = false;
            this.inputValue = '';
            }
        }
    });

};
function loadHtml(html) {
    if(html.length <= 0) return;
    //if(window.location.search.length <= 0) return;
    //设置页面不可被编辑
    document.body.setAttribute("contenteditable", false);
    //获取我们参数中传入的html(不包括?)
    //var search = window.location.search.substring(1);
    //base 64解析成Utf8，并插入html
    ZfraTools.reloadHtml(document.body,ZfraTools.base64UrlDecode(html));
    var imgs = document.querySelectorAll(".img_wrapper");
    imgs.forEach(img => {
        if(img != null) {
            //设置图片不能被拖放大小
            img.style.resize = "none";
        }
    });

}
window.onload  = function() {
    // 获取 body 元素
    const body = document.getElementsByTagName('body')[0];
    // 添加 input 事件监听器，当用户输入时在光标位置插入 <p> 标签
    body.addEventListener('input', () => {
        // 获取当前焦点所在的元素
        const container = window.getSelection().focusNode;
        // 如果当前元素非 <p> 元素，则在其外部插入 <p>
        if(container.parentNode.tagName === 'DIV') {
            return;
        }
        if (container.nodeName !== 'P') {
           document.execCommand('formatBlock', false, 'p');
        }
    });
    //粘贴时只保留纯文本形式
    body.addEventListener('paste', (event) => {
        event.preventDefault(); // 防止默认的粘贴操作
        const plainText = event.clipboardData.getData('text/plain'); // 获取粘贴板中的纯文本
        // 插入纯文本到光标位置，替换任何格式化内容
        document.execCommand('insertHTML', false, plainText);
    });
    document.addEventListener('keydown', (event) => {
        if (event.code === 'Tab') {
            event.preventDefault(); 
             // 获取当前光标所在区域和位置
            const selection = window.getSelection();
            const range = selection.getRangeAt(0);
            const container = range.commonAncestorContainer;
            if(container.nodeType === Node.TEXT_NODE && event.target.isContentEditable) {
                //tab键缩进
                // 阻止浏览器默认行
                // 插入两个空格作为缩进
                document.execCommand('insertHTML', false, '&emsp;&emsp;'); 
            }
        }
        else if(event.code === 'Enter') {
            const selection = window.getSelection();
            const range = selection.getRangeAt(0);
            const container = range.commonAncestorContainer;
           // console.log(container.parentNode.tagName);
            if(container.parentNode.tagName === "DIV") {
                event.preventDefault(); 
                return;
            }
        }
        //这里我们做图片的删除操作
        else if(event.code === 'Delete' || event.code === 'Backspace') {
            const selection = window.getSelection();
            const range = selection.getRangeAt(0);
            const container = range.commonAncestorContainer;
            if(container == null) return;
            const first = container.firstChild;
            if(first == null || first.tagName != "IMG") return;
            var url =  first.src; 
            if(typeof(url) === "undefined" || url==="") return;
            //发送删除请求给服务器，这里使用异步请求
            //我们不接受服务器的返回数据
            //只返回文件名
            url = url.substring(url.lastIndexOf("/") + 1);
            var xhttp = ZfraTools.xhttpCreate();
            ZfraTools.xhttpPostSend(xhttp,{
                type:ZfraObjects.WebType.DELETEIMG,
                msg:url
            },true);
        }
      });
    // 在副 html 中监听消息
    window.addEventListener('message', function(event) {
        if (event.origin === ZfraObjects.formPathOrigin) {
            if(!checkListener(event)) return;
        }
    });
    function checkListener(event) {
        var data = JSON.parse(event.data);
        var code = data.code;
        const selectedText = window.getSelection().toString();
        if(!html_elemnet.passCodes.includes(code) && selectedText.length <= 0) return false;
        switch(code)
        {
            case "get":
                loadHtml(data.key);
                break;
            case "update":
                if(ZfraObjects.lock.lock_resp_div) return;
                var xhttp = ZfraTools.xhttpCreate();
                ZfraObjects.lock.lock_resp_div = true;
                var html_index = data.key[0];
                var html_addhtml = data.key[1];
                //闭包传参
                (function(html_addhtml) {
                    xhttp.onreadystatechange = function() {
                        if (this.readyState == 4 && this.status == 200) {
                            var serverData = JSON.parse(this.responseText);
                            switch(serverData.type) {
                                case ZfraObjects.ServerType.ERROR://错误信息
                                    ZfraTools.sendMessageToParentHtml({
                                        code:'error',
                                        data:serverData.msg
                                    });
                                    break;
                                case ZfraObjects.ServerType.SUCCESS:
                                    //进行读取
                                    ZfraTools.reloadHtml(document.body,html_addhtml);
                                    //初始化vue环境
                                    init_add_html(serverData.tags.split(','));
                                    //加载我们已经有的数据 
                                    document.getElementById("title_area").innerHTML = ZfraTools.base64UrlDecode(serverData.title);
                                   //这种插入不会重新解析渲染，高效安全，用InnerHtml+=会重新解析渲染,就会造成上面vue初始化数据的丢失
                                    document.body.insertAdjacentHTML('beforeend', ZfraTools.base64UrlDecode(serverData.context));
                                    var p  = document.createElement('p');
                                    p.innerHTML = "please continue......"
                                    document.body.appendChild(p);
                                    //时间那个我们就重置不转化了
                                    //设置页面可以被编辑
                                    document.body.setAttribute("contenteditable", true);
                                    break;
                                case ZfraObjects.ServerType.NULL:
                                    ZfraTools.showWebError();
                                    break;
                                default:
                                    ZfraTools.showServerError();
                                    break;
                            }
                        }
                    }
                })(html_addhtml);
                //获取我们需要的对应数据
                ZfraTools.xhttpGetSend(xhttp,["type","index","tindex"],[ZfraObjects.WebType.UPDATETITLE,html_index,data.key[2]],false);
                ZfraObjects.lock.lock_resp_div = false;
                return true;
            case "delete":
                if(ZfraObjects.lock.lock_resp_div) return;
                var xhttp = ZfraTools.xhttpCreate();
                ZfraObjects.lock.lock_resp_div = true;
                xhttp.onreadystatechange = function() {
                    if (this.readyState == 4 && this.status == 200) {
                        var serverData = JSON.parse(this.responseText);
                        switch(serverData.type) {
                            case ZfraObjects.ServerType.ERROR://错误信息
                                ZfraTools.sendMessageToParentHtml({
                                    code:'error',
                                    data:serverData.msg
                                });
                                break;
                            case ZfraObjects.ServerType.SUCCESS:
                                ZfraTools.sendMessageToParentHtml({
                                    code:'success',
                                    data:serverData.msg
                                });
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
                ZfraTools.xhttpGetSend(xhttp,["type","index","tindex"],[ZfraObjects.WebType.DELETETITLE,data.key[0],data.key[1]],false);
                ZfraObjects.lock.lock_resp_div = false;
                return true;
            case "add":
                ZfraTools.reloadHtml(document.body,data.key);
                var p  = document.createElement('p');
                p.innerHTML = "please continue......"
                document.body.appendChild(p);
               // document.body.innerHTML = data.key;
                init_add_html();
                document.body.setAttribute("contenteditable", true);
                return true;
            case "bold":
                document.execCommand('bold', false, null);
                return true;
            case "title":
                document.execCommand('formatBlock', false, data.key);
                return true;
            case "color":
                document.execCommand('foreColor', false, data.key);
                return true;
            case "center":
                var selection = window.getSelection();
                if(selection.rangeCount <= 0) return false;
                var range = selection.getRangeAt(0);
                var selectedParentElement = range.commonAncestorContainer.parentElement;
                if(selectedParentElement.style.textAlign == "center") {
                    selectedParentElement.style.textAlign = "left";
                } else {
                    selectedParentElement.style.textAlign = "center";
                }
                return true;
            case "image":
                var img_div = document.createElement('div');
                img_div.setAttribute('class', 'img_wrapper');
                var previewImage = document.createElement('img');
               // previewImage.style.maxWidth = '100%';
                previewImage.src = data.key;
                previewImage.setAttribute('class', 'preview_Image');
                img_div.appendChild(previewImage);
                document.body.appendChild(img_div);
                var p  = document.createElement('p');
                p.innerHTML = "please continue......"
                document.body.appendChild(p);
                return true;
            case "code":
                var code_div = document.createElement('div');
                code_div.setAttribute('class', `container${html_elemnet.index}`);
                var html_code = document.createElement('htmlcode');
                var text_area = document.createElement('textarea');
                text_area.setAttribute('id', `code${html_elemnet.index}`);
                html_code.appendChild(text_area);
                code_div.appendChild(html_code);
                var mmode = ZfraTools.getCodeMirrorType(data.key);
                 // 创建 CodeMirror 实例并设置参数
                CodeMirror.fromTextArea(text_area, {
                    // 指定高亮方式
                    mode: mmode,
                    // 是否显示行号
                    lineNumbers: true,
                    // 主题样式
                    theme: "base16-dark",
                    // true则只读,防止代码被改变
                    readOnly: false 
                });
                ++html_elemnet.index;
                document.body.appendChild(code_div);
                var p  = document.createElement('p');
                p.innerHTML = "please continue......"
                document.body.appendChild(p);
                return true;
            case "postupdate":
                //修改代码
                if(ZfraObjects.lock.lock_resp_div) return;
                var title_area = document.getElementById("title_area");
                if(title_area.value.length <= 0) {
                    ZfraTools.sendMessageToParentHtml({
                        code:'error',
                        data:"错啦~，标题不能为空呀~"
                    });
                    return true;
                }
                var xhttp = ZfraTools.xhttpCreate();
                ZfraObjects.lock.lock_resp_div = true;
                xhttp.onreadystatechange = function() {
                    if (this.readyState == 4 && this.status == 200) {
                        var serverData = JSON.parse(this.responseText);
                        switch(serverData.type) {
                            case ZfraObjects.ServerType.ERROR://错误信息
                                ZfraTools.sendMessageToParentHtml({
                                    code:'error',
                                    data:serverData.msg
                                });
                                break;
                            case ZfraObjects.ServerType.SUCCESS:
                                ZfraTools.sendMessageToParentHtml({
                                    code:'success',
                                    data:serverData.msg
                                });
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
                // 获取 document 中的所有元素
                var html_context  = getPostHtml();
                //获取分钟时长
                var _times =  getPostTime();
                //vue_tags.dynamicTags
                //把我们的页面信息上传到服务器
                ZfraTools.xhttpPostSend(xhttp,{
                    type:ZfraObjects.WebType.POSTUPDATETITLE,
                    //这个是我们的文章标题
                    title:encodeURIComponent(title_area.value),
                    //这个是我们的tags的数组
                    tags:_Vue.vue_tags.dynamicTags,
                    //时长
                    times:_times,
                    //encodeURIComponent防止解析错误
                    html:encodeURIComponent(html_context),
                    //这个是要更新的索引
                    index:data.key[0],
                    tindex:data.key[1]
                },false);
                ZfraObjects.lock.lock_resp_div = false;
                return true;
            case "post":
                //如果在上传中，就不执行代码块
                if(ZfraObjects.lock.lock_resp_div) return;
                var title_area = document.getElementById("title_area");
                if(title_area.value.length <= 0) {
                    ZfraTools.sendMessageToParentHtml({
                        code:'error',
                        data:"错啦~，标题不能为空呀~"
                    });
                    return true;
                }
                var xhttp = ZfraTools.xhttpCreate();
                ZfraObjects.lock.lock_resp_div = true;
                xhttp.onreadystatechange = function() {
                    if (this.readyState == 4 && this.status == 200) {
                        var serverData = JSON.parse(this.responseText);
                        switch(serverData.type) {
                            case ZfraObjects.ServerType.ERROR://错误信息
                                ZfraTools.sendMessageToParentHtml({
                                    code:'error',
                                    data:serverData.msg
                                });
                                break;
                            case ZfraObjects.ServerType.SUCCESS:
                                ZfraTools.sendMessageToParentHtml({
                                    code:'success',
                                    data:serverData.msg
                                });
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
                // 获取 document 中的所有元素
                var html_context  = getPostHtml();
                //获取分钟时长
                var _times =  getPostTime();
                //vue_tags.dynamicTags
                //把我们的页面信息上传到服务器
                ZfraTools.xhttpPostSend(xhttp,{
                    type:ZfraObjects.WebType.POSTTITLE,
                    //这个是我们的文章标题
                    title:encodeURIComponent(title_area.value),
                    //这个是我们的tags的数组
                    tags:_Vue.vue_tags.dynamicTags,
                    //时长
                    times:_times,
                    //种类
                    tindex:data.key,
                    //encodeURIComponent防止解析错误
                    html:encodeURIComponent(html_context)
                },false);
                ZfraObjects.lock.lock_resp_div = false;
                return true;
            default:
                return false;
        }
    }
}
document.addEventListener('DOMContentLoaded', function() {
    //在资源全部加载完成之后，我们发送请求，让主Html页面传输给
    //我们需要的html内容
    ZfraTools.sendMessageToParentHtml(
        {
            code:"get",
            data:null
        }
    );
    // 在此处可以执行其他操作或调用函数
});
