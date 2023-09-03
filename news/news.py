from util import htmlUtil
import sys
url = sys.argv[1:][0]  # 接收除脚本名称外的参数
html = htmlUtil.get_html(url)
#获取我们的文章
href_list = html.xpath('//div[@class = "content-article"]')[0]
href_list = href_list.xpath("//h1/text() | //p/text()")
str=""
for href in href_list:
    href = href.replace("\n","")
    str+=href
print(str)