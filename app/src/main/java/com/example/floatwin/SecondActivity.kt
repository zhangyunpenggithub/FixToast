package com.example.floatwin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
            val makeText = Toast.makeText(this, "测试Crash", Toast.LENGTH_SHORT)
            makeText.show()
    }
}
