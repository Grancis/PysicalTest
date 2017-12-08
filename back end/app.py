# coding=utf-8
import sys

reload(sys)
sys.setdefaultencoding('utf-8')

from flask import Flask, render_template, redirect, url_for, session, request, jsonify, send_file
from spider import *
from chardet import detect

app = Flask(__name__)
app.secret_key = 'g\x1a\xfa\xab\xd4\xcd\x89\xc9O\xde\xb1\xd9\x88\xfa\xf7Z\x892\x97F'


def has_login():
    return session.get('account') is not None


# 主页
@app.route('/', methods=['GET', 'POST'])
def index():
    if not has_login():
        return send_file('test.html')
    else:
        return render_template('home.html', name=session['name'])


# 登录
@app.route('/login', methods=['POST'])
def login():
    account = request.form['account']
    password = request.form['password']
    # 登录学院网站
    try:
        s = login_scu(account, password)
        # 成功跳转到主页面
        if s:
            session['account'] = account
            session['password'] = password
            return redirect(url_for('index'))
        # 失败提示错误
        else:
            session['account'] = None
            session['password'] = None
            session['name'] = None
            return '<p>账号或密码错误</p>'
    except Exception, e:
        return '<p>出错啦!,%s</p>' % str(e)


# 登出
@app.route('/logout', methods=['GET'])
def logout():
    session.pop('account', None)
    session.pop('name', None)
    session.pop('password', None)
    # print session
    return redirect(url_for('index'))


# 返回测试数据
@app.route('/query', methods=['POST'])
def query():
    if has_login():
        # 从本地数据库获取体侧数据
        account = request.form.get('account', '')
        password = request.form.get('password', '')
        try:
            # 返回json
            test_datas = get_datas(account, password)
            return jsonify(test_datas)
        except Exception, e:
            return '<p>出错啦!,%s</p>' % str(e)

    else:
        return '<p>请登录!!</p>'


# 返回可预约信息
@app.route('/tests', methods=['GET', 'POST'])
def tests():
    pass
    # return render_template('tests.html', datas=result)


# 返回预约信息
@app.route('/reserve', methods=['GET', 'POST'])
def reserve():
    pass
    # if has_login():
    #     return render_template('reservation.html', data=result)
    # else:
    #     return '<p>亲,请登录哦!</p>'


# 预约
@app.route('/do_reserve', methods=['POST'])
def do_reserve():
    pass
    # if has_login():
    #     try:
    #         _id = request.form.get('id', '-1')
    #     except:
    #         _id = '-1'
    #     if _id != '-1':
    #         result = db.query_tests_by_id(_id)
    #         has_reserved = db.query_has_reserved(session['account'])
    #         has_cancled = db.query_cancel(session['account'])
    #         if (not has_reserved and not has_cancled):
    #             db.insert_reservation(session['account'], result[1], result[2], _id)
    #             db.decrease_tests_capacity(_id)
    #     return redirect(url_for('index'))
    # else:
    #     return '<p>亲,请登录哦!</p>'


# 取消预约
@app.route('/cancle_reservation', methods=['POST'])
def cancel_reservation():
    pass
    # db = DataBase()
    # has_reserved = db.query_has_reserved(session['account'])
    # if has_reserved:
    #     _id = db.get_tests_id(session['account'])
    #     db.increase_tests_capacity(_id)
    #     db.delete_reservation(session['account'])
    # return redirect(url_for('index'))


# 申请免测
@app.route('/cancle', methods=['POST'])
def cancle():
    pass
    # db = DataBase()
    # result = db.query_cancel(session['account'])
    # if not result:
    #     reason = request.form.get('reason', None)
    #     if reason:
    #         db.insert_cancels(session['account'], reason)
    #         has_reserved = db.query_has_reserved(session['account'])
    #         if has_reserved:
    #             _id = db.get_tests_id(session['account'])
    #             db.increase_tests_capacity(_id)
    #             db.delete_reservation(session['account'])
    # return redirect(url_for('index'))


if __name__ == '__main__':
    app.run('0.0.0.0', debug=True, port=2333)
