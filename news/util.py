import requests
from lxml import etree
class htmlUtil:
    # 静态方法
    staticmethod
    def get_html(url):
        headers = {
            'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36 Edg/115.0.1901.203'
        }
        # 获取我们解析后的html文本
        r = requests.get(url, headers=headers)
        return etree.HTML(r.text)