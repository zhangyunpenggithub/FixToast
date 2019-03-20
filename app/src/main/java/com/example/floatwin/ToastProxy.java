package com.example.floatwin;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;

public class ToastProxy implements InvocationHandler {
 
     private Object mService;
     private Context mContext;
     private static boolean isUseMyProxy = false;
 
     public Object newProxyInstance(Context context, Object sService) {
         this.mService = sService;
         this.mContext = context;
         return Proxy.newProxyInstance(sService.getClass().getClassLoader(),
                 sService.getClass().getInterfaces(), this);
     }
 
     @Override
     public Object invoke(Object arg0, Method method, Object[] args)
             throws Throwable {
         //替换全局样式
//         if ("enqueueToast".equals(method.getName())) {
//             if (args != null && args.length > 0) {
//                 Field mNextViewField = args[1].getClass().getDeclaredField("mNextView");
//                 mNextViewField.setAccessible(true);
//
//                 View mNextView = (View) mNextViewField.get(args[1]);
//                 if (mNextView != null && mNextView instanceof LinearLayout){
// 					//获取原Toast的文字
//                     String nextString = ((TextView) ((LinearLayout) mNextView).getChildAt(0)).getText().toString();
//                     //构造所需变量
//                     TextView value = new TextView(mContext);
//                     value.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//                     value.setBackgroundColor(Color.RED);
//                     value.setText(nextString);
//                     mNextViewField.set(args[1], value);
//                 }
//             }
//         }
//         try {
//             method.invoke(mService, args);
//         } catch (Exception e) {
//             e.printStackTrace();
//         }


         // 第一种：给toast添加try catch
//         if ("enqueueToast".equals(method.getName())) {
//             if (args != null && args.length > 0) {
//                 try {
//                     Method handleShowMethod = args[1].getClass().getDeclaredMethod("handleShow", IBinder.class);
//                     handleShowMethod.invoke(args[1], new Binder());
//                 } catch (Exception e) {
//                     e.printStackTrace();
//                 }
//             }
//         }


//         //第二种：enqueueToast之前先判断是否可以加入
//         try {
////             //加载得到ServiceManager类，然后得到方法getService。
////             Method getServiceMethod = Class.forName("android.os.ServiceManager").getDeclaredMethod("getService", new Class[]{String.class});
////             //通过getServiceMethod得到ServiceManager的实例（隐藏类，所以使用Object）
////             Object ServiceManager = getServiceMethod.invoke(null, new Object[]{"window"});
////             //通过反射的到Stub
////             Class<?> cStub =  Class.forName("android.view.IWindowManager$Stub");
////             //得到Stub类的asInterface 方法
////             Method asInterface = cStub.getMethod("asInterface", IBinder.class);
////             //然后通过类似serviceManager.getIWindowManager的方法获取IWindowManager的实例
////             Object IWindowManager = asInterface.invoke(null, ServiceManager);
////             Class windowManagerServiceClass = IWindowManager.getClass();
//////             Class windowManagerServiceClass = Class.forName("com.android.server.wm.WindowManagerService");
////
////
////             Method getDefaultDisplayContentLockedMethod = windowManagerServiceClass.getDeclaredMethod("getDefaultDisplayContentLocked");
////             getDefaultDisplayContentLockedMethod.setAccessible(true);
////             WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
////             Object displayContent = getDefaultDisplayContentLockedMethod.invoke(windowManager);
////
////             Class displayContentClass = Class.forName("com.android.server.wm.DisplayContent");
////             Method canAddToastWindowForUids = displayContentClass.getDeclaredMethod("canAddToastWindowForUid");
////             canAddToastWindowForUids.setAccessible(true);
////             boolean canAdd = (boolean) canAddToastWindowForUids.invoke(displayContent, Binder.getCallingUid());
////             Log.e("zhangyunpeng", "canAdd+**********************" + canAdd);
//
//
//
//             Class<?> windowManagerGlobalClass = Class.forName("android.view.WindowManagerGlobal");
//             Field sWindowManagerServiceField = windowManagerGlobalClass.getDeclaredField("sWindowManagerService");
//             sWindowManagerServiceField.setAccessible(true);
//             //获取IWindowManager
//             Class<?> IWindowManagerClass = Class.forName("android.view.IWindowManager");
//
//             //sWindowSession需要置空，否则不走我的代理,不需要每次都置空
//             if (!isUseMyProxy){
//                 isUseMyProxy = true;
//                 Field sWindowSessionField = windowManagerGlobalClass.getDeclaredField("sWindowSession");
//                 sWindowSessionField.setAccessible(true);
//                 sWindowSessionField.set(null, null);
//
//
////                 Class<?> viewRootImplClass = Class.forName("android.view.ViewRootImpl");
////                 Field mWindowSessionField = viewRootImplClass.getDeclaredField("mWindowSession");
////                 Method getWindowSessionMethod = windowManagerGlobalClass.getDeclaredMethod("getWindowSession");
////                 mWindowSessionField.setAccessible(true);
////                 Field modifersField = Field.class.getDeclaredField("modifiers");
////                 modifersField.setInt(mWindowSessionField, mWindowSessionField.getModifiers() & ~Modifier.FINAL);
////                 mWindowSessionField.set(, getWindowSessionMethod.invoke(null));
//             }
//
//
//
//             WindowManagerInvocationHandler windowManagerInvocationHandler = WindowManagerInvocationHandler.instance(sWindowManagerServiceField.get(windowManagerGlobalClass));
//             Object proxyInstance = Proxy.newProxyInstance(mContext.getClassLoader(), new Class[]{IWindowManagerClass}, windowManagerInvocationHandler);
//             sWindowManagerServiceField.set(windowManagerGlobalClass, proxyInstance);
//
//
//             method.invoke(mService, args);
//
//         } catch (Exception e) {
//             Log.e("zhangyunpeng", "e" ,e);
//             e.printStackTrace();
//         }

         //第三种 替换toast中的handler
         if ("enqueueToast".equals(method.getName())) {
             if (args != null && args.length > 0) {
                 Field sField_TN = Toast.class.getDeclaredField("mTN");
                 sField_TN.setAccessible(true);
                 Field sField_TN_Handler = sField_TN.getType().getDeclaredField("mHandler");
                 sField_TN_Handler.setAccessible(true);

                 Object tn = args[1];
                 Handler preHandler = (Handler) sField_TN_Handler.get(tn);
                 sField_TN_Handler.set(tn, new SafelyHandlerWarpper(preHandler));
             }
         }

         return method.invoke(mService, args);
     }


    private static class SafelyHandlerWarpper extends Handler {

        private Handler impl;

        public SafelyHandlerWarpper(Handler impl) {
            this.impl = impl;
        }

        @Override
        public void dispatchMessage(Message msg) {
            try {
                super.dispatchMessage(msg);
            } catch (Exception e) {
                Log.e("zhangyunpeng", "e", e);
                
                //TODO 添加判断是already added 的情况在去查找
                findTheViewAlreadyAdded();
            }
        }

        /**
         * 找到那个跟toast冲突的view
         */
        private void findTheViewAlreadyAdded() {
            try {
                Class<?> windowManagerGlobalClass = Class.forName("android.view.WindowManagerGlobal");
                Field mViewsField = windowManagerGlobalClass.getDeclaredField("mViews");
                mViewsField.setAccessible(true);
                Field mRootsField = windowManagerGlobalClass.getDeclaredField("mRoots");
                mRootsField.setAccessible(true);
                Method getInstanceMethod = windowManagerGlobalClass.getMethod("getInstance");
                Object WindowManagerGlobalInstance = getInstanceMethod.invoke(null);
                ArrayList o = (ArrayList) mViewsField.get(WindowManagerGlobalInstance);
                ArrayList o2 = (ArrayList) mRootsField.get(WindowManagerGlobalInstance);

//                ((TextView) ((android.view.View[])((LinearLayout)o.get(0)).mChildren)[0]).getText();


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void handleMessage(Message msg) {
            impl.handleMessage(msg);//需要委托给原Handler执行
        }
    }
 }
