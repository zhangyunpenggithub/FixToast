package com.example.floatwin;

import android.app.NotificationManager;
import android.content.Context;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

class SystemServiceHookUtil {


    public static void hookNMS(Context context) {
        try {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Method getService = NotificationManager.class.getDeclaredMethod("getService");
            getService.setAccessible(true);
            Class iNotiMngClz = Class.forName("android.app.INotificationManager");
            Object proxyNotiMng = Proxy
                    .newProxyInstance(context.getClass().getClassLoader(), new Class[]{iNotiMngClz}, new NotificationInvocationHandler());
            Field sServiceField = NotificationManager.class.getDeclaredField("sService");
            sServiceField.setAccessible(true);
            sServiceField.set(notificationManager, proxyNotiMng);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
