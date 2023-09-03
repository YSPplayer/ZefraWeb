#初始化使用的py
from util import htmlUtil
import threading
#获取腾讯最新新闻
html = htmlUtil.get_html("https://www.qq.com/")
#获取我们可用的链接
href_list = html.xpath('//a/@href')
result = ""
for href in href_list:
    #把可用新闻list存储起来
    if(href.startswith("https://new.qq.com/rain/a/")):
        result += f'{href},'
result = result[:-1]
print(result)