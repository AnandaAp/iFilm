package com.anlian.ifilm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class MainActivity : AppCompatActivity() {
//    private var backPressedTime:Long = 0
//    lateinit var backToast: Toast

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

//    override fun onBackPressed() {
//        backToast = Toast.makeText(this, "Press back again to leave the app.", Toast.LENGTH_LONG)
//        if (backPressedTime + 2000 > System.currentTimeMillis()) {
//            backToast.cancel()
//            super.onBackPressed()
//            finish()
//        } else {
//            backToast.show()
//        }
//        backPressedTime = System.currentTimeMillis()
//    }
}