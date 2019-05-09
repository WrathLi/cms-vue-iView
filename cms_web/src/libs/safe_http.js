import $ from 'jquery';
import Cookies from 'js-cookie';

let safeHttp = {

};

safeHttp.get = function (url, param, callback) {
    if (param === undefined || param === null) {
        $.get(url, function (data, status) {
            if (callback !== undefined && callback !== null) {
                callback(data, status);
            }
        });
    } else {
        $.get(url, param, function (data, status) {
            if (callback !== undefined && callback !== null) {
                callback(data, status);
            }
        });
    }
};

safeHttp.post = function (url, body, callback) {
    $.post({
        'url': url,
        'type': 'post',
        'data': JSON.stringify(body),
        'cache': false,
        dataType: 'text',
        'contentType': 'application/json; charset=utf-8',
        'success': function (data, status) {
            if (callback !== undefined && callback !== null) {
                callback(data, status);
            }
        }
    });
};

safeHttp.jwtGet = function (url, param, callback, vm) {
    let loginId = Cookies.get('loginId');
    let token = Cookies.get('token');
    let jwtParam = {
        loginId: loginId,
        token: token
    };
    if (param !== undefined && param !== null) {
        $.extend(jwtParam, param);
    }
    $.get(url, jwtParam, function (data, status) {
        if (data.code === 403) {
            if (vm !== undefined && vm !== null) {
                vm.$Message.error({
                    content: 'Token失效,请重新登录',
                    duration: 4
                });
            }
        }
        if (callback !== undefined && callback !== null) {
            callback(data, status);
        }
    });
};

safeHttp.jwtPost = function (url, body, callback, vm) {
    let loginId = Cookies.get('loginId');
    let token = Cookies.get('token');
    let finalUrl = '';
    if (url.indexOf('?') !== -1) {
        finalUrl = url + '&loginId=' + loginId + '&token=' + token;
    } else {
        finalUrl = url + '?loginId=' + loginId + '&token=' + token;
    }
    $.post({
        'url': finalUrl,
        'type': 'post',
        'data': JSON.stringify(body),
        'cache': false,
        dataType: 'text',
        'contentType': 'application/json; charset=utf-8',
        'success': function (data, status) {
            let jsonObj = JSON.parse(data);

            if (jsonObj.code === 403) {
                if (vm !== undefined && vm !== null) {
                    vm.$Message.error({
                        content: 'Token失效,请重新登录',
                        duration: 4
                    });
                }
            }
            if (callback !== undefined && callback !== null) {
                callback(data, status);
            }
        }
    });
};

export default safeHttp;
