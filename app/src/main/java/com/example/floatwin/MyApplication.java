package com.example.floatwin;

import android.app.Application;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        SystemServiceHookUtil.hookNMS(this);
        setToast();
    }

    private void setToast() {
        try {
            // 获得HOOK点
            Toast toast = new Toast(this);
            Method getService = toast.getClass().getDeclaredMethod("getService");
            getService.setAccessible(true);
            final Object sService = getService.invoke(toast);
            // 生成代理对象
            ToastProxy toastProxy = new ToastProxy();
            Object proxyNotiMng = toastProxy.newProxyInstance(this,sService);
            // 替换 sService
            Field sServiceField = Toast.class.getDeclaredField("sService");
            sServiceField.setAccessible(true);
            sServiceField.set(sService, proxyNotiMng);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}