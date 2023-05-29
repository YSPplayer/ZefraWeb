window.onload  = function(){
    // 获取 body 元素
    const body = document.getElementsByTagName('body')[0];
    // 添加 input 事件监听器，当用户输入时在光标位置插入 <p> 标签
    body.addEventListener('input', () => {
        // 获取当前焦点所在的元素
        const container = window.getSelection().focusNode;
        // 如果当前元素非 <p> 元素，则在其外部插入 <p>
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
    window.addEventListener('message', function(event) {
        if (event.origin === ZfraObjects.formPathOrigin) {
            console.log(event.data); // 输出：Hello, parent!
        }
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
      });
      
/*
  var iframe = document.getElementById("_web_iframe");
        iframe.contentWindow.postMessage('Hello, iframe!', `${ZfraObjects.formPath}//article.html`);
*/
}
