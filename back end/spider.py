# coding=utf-8
import requests
import re
from bs4 import BeautifulSoup
from chardet import detect

wrong_pattern = re.compile(u"学号或者密码错误")
login_url = 'http://pead.scu.edu.cn/jncx/logins.asp'
host = 'http://pead.scu.edu.cn/jncx/'
mid_url = 'http://pead.scu.edu.cn/jncx/?security_verify_data=313932302c31303830'
data_url = 'http://pead.scu.edu.cn/jncx/tcsh2.asp'


def has_logined(page):
    """
    判断是否登录成功
    """
    return wrong_pattern.search(page) is None


def login_scu(account, password):
    """
    登录, 需要进行跳转
    """
    s = requests.session()
    user_info = {
        'xh': account,
        'xm': password,
        'Submit': '',
    }
    # 访问主页
    s.get(host)
    # 跳转
    s.get(mid_url)
    # 登录
    content = s.post(login_url, user_info).content
    home_page = content.decode('gbk')
    if has_logined(home_page):
        return s
    else:
        return None


def deal_string(string):
    """
    处理数据字符串,如果为空则返回'无'
    """
    if not string:
        return '无'
    return string.strip()


def parse_datas(session):
    if session != None:
        soup = BeautifulSoup(session.get(data_url).content.decode('gbk'), 'lxml')
        name = soup.find('caption').text
        trs = soup.find_all('tr')[5:]
        test_datas = []

        # 奇数行是数据, 偶数行是得分
        for i in range(len(trs))[0::2]:
            data = {}
            data['datas'] = []
            data['scores'] = []
            # 获取数据, 学期
            tds = trs[i].find_all('td')[1:16]
            for j in range(len(tds)):
                if j == 14:
                    data['term'] = deal_string(tds[j].text)
                else:
                    text = deal_string(tds[j].find('div').text)
                    data['datas'].append(text)
            # 获取分数, 总评, 建议
            tds = trs[i+1].find_all('td')[1:]
            for j in range(len(tds)):
                if j == 12:
                    data['scores'].append('无')
                elif j == 13:
                    text = deal_string(tds[j].text)
                    data['assessment'] = text
                elif j == 14:
                    text = deal_string(tds[j].text)
                    data['suggestion'] = text
                else:
                    text = deal_string(tds[j].find('div').text)
                    data['scores'].append(text)
            data['name'] = name
            test_datas.append(data)
        return test_datas
    else:
        print(u'登录失败')


def get_datas(account, password):
    s = login_scu(account, password)
    if s:
        return parse_datas(s)
    else:
        return None


if __name__ == '__main__':
    print get_datas('2016141462307', '462307')
