package com.example.floatwin

import android.app.Activity
import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : Activity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showFloatWin()
        button.setOnClickListener {
            Toast.makeText(applicationContext, "haha", Toast.LENGTH_LONG).show()
        }
    }

    fun showTipToast(c: Context) {
        val toast = Toast(c.applicationContext)
        val inflater = c.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layout = inflater.inflate(R.layout.rom_acc_toast_tip_2, null, false) as RelativeLayout
        val img = layout.findViewById(R.id.acc_guide_img) as ImageView
        img.setImageResource(R.drawable.ic_launcher_background)

        try {
            val field = toast.javaClass.getDeclaredField("mTN")
            field.isAccessible = true
            val objTN = field.get(toast)
            val mParams = objTN.javaClass.getDeclaredField("mParams")
            val params = WindowManager.LayoutParams()
            params.gravity = Gravity.LEFT or Gravity.TOP
            params.height = WindowManager.LayoutParams.MATCH_PARENT
            params.width = WindowManager.LayoutParams.MATCH_PARENT
            params.format = PixelFormat.TRANSLUCENT
            params.type = WindowManager.LayoutParams.TYPE_TOAST
            params.title = "Toast"
            var flag = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
            if (Build.VERSION.SDK_INT >= 19) {
                flag = flag or 0x04000000 // 5.0沉浸式状态栏
            }
            params.flags = flag
            params.x = 0
            params.y = 0
            mParams.isAccessible = true
            mParams.set(objTN, params)

        } catch (e: Throwable) {
        }
        toast.duration = Toast.LENGTH_LONG
        toast.view = layout
        toast.show()
    }

    private fun showFloatWin() {
        val windowManager: WindowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val view = LayoutInflater.from(applicationContext).inflate(R.layout.float_win, null, false)
        createFloatWindow(applicationContext, windowManager, view)
    }


    /**
     * 创建一个小悬浮窗。初始位置为屏幕的右下角位置。
     */
    private fun createFloatWindow(context: Context, windowManager: WindowManager, view:View) {
            var wmParams = WindowManager.LayoutParams()
//        if (Build.VERSION.SDK_INT >= 26) {
//            wmParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
//        }
//        else if (Build.VERSION.SDK_INT >= 24) { /*android7.0不能用TYPE_TOAST*/
//            wmParams.type = WindowManager.LayoutParams.TYPE_PHONE
//        } else { /*以下代码块使得android6.0之后的用户不必再去手动开启悬浮窗权限*/
//            val packname = context.packageName
//            val pm = context.packageManager
//            val permission =
//                PackageManager.PERMISSION_GRANTED == pm.checkPermission(
//                    "android.permission.SYSTEM_ALERT_WINDOW",
//                    packname
//                )
//            if (permission) {
//                wmParams.type = WindowManager.LayoutParams.TYPE_PHONE
//            } else {
//                wmParams.type = WindowManager.LayoutParams.TYPE_TOAST
//            }
//        }
            wmParams.type = WindowManager.LayoutParams.TYPE_TOAST

            //设置图片格式，效果为背景透明
            wmParams.format = PixelFormat.RGBA_8888
            //设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作）
            wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
            //调整悬浮窗显示的停靠位置为左侧置顶
            wmParams.gravity = Gravity.START or Gravity.TOP

            val dm = DisplayMetrics()
            //取得窗口属性
            windowManager.defaultDisplay.getMetrics(dm)
            //窗口的宽度
            val screenWidth = dm.widthPixels
            //窗口高度
            val screenHeight = dm.heightPixels
            //以屏幕左上角为原点，设置x、y初始值，相对于gravity
            wmParams.x = screenWidth
            wmParams.y = screenHeight

            //设置悬浮窗口长宽数据
            wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT
            wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT
            view.layoutParams = wmParams
            windowManager.addView(view, wmParams)
    }

}
