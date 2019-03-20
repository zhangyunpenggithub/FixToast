package com.example.floatwin;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * +----------------------------------------------------------------------
 * | 功能描述:
 * +----------------------------------------------------------------------
 * | 时　　间: 2019/3/19.
 * +----------------------------------------------------------------------
 * | 代码创建: 张云鹏
 * +----------------------------------------------------------------------
 * | 版本信息: V1.0.0
 * +----------------------------------------------------------------------
 **/
public class NotificationInvocationHandler implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        Log.e("zhangyunpeng", "hook method" + method.getName());
        return null;
    }
}
