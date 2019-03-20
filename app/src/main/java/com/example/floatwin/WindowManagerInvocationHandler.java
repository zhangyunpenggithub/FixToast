package com.example.floatwin;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * +----------------------------------------------------------------------
 * | 功能描述:
 * +----------------------------------------------------------------------
 * | 时　　间: 2019/3/20.
 * +----------------------------------------------------------------------
 * | 代码创建: 张云鹏
 * +----------------------------------------------------------------------
 * | 版本信息: V1.0.0
 * +----------------------------------------------------------------------
 **/
public class WindowManagerInvocationHandler implements InvocationHandler {


    private static Object original;

    public static WindowManagerInvocationHandler instance(Object o) {
        original = o;
        return new WindowManagerInvocationHandler();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Log.e("zhangyunpeng", "WindowManagerInvocationHandler++++++++++++++++++++++++++++" + method.getName());

        return method.invoke(original, args);
    }
}
