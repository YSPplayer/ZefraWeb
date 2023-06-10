var html_elemnet = {
    index:0
}
window.onload  = function(){
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
            console.log(container.parentNode.tagName);
            if(container.parentNode.tagName === "DIV") {
                event.preventDefault(); 
                return;
            }
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
        if(code != "image" && code != "code" &&
         selectedText.length <= 0) return false;
        switch(code)
        {
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
                 // 创建 CodeMirror 实例并设置参数
                CodeMirror.fromTextArea(text_area, {
                    // 指定高亮方式
                    mode: data.key,
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
            default:
                return false;
        }
    }
  
}
