package com.accenture.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.accenture.client.ClientActivity
import com.accenture.myapplication.databinding.ActivitySecondBinding
import com.accenture.service.BookingService2
import com.accenture.utils.LogUtil


private val TAG = "SecondActivity"
class SecondActivity : AppCompatActivity() {

    private var intent = Intent()
    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogUtil.i(TAG, "onCreate")
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.setClass(this, BookingService2().javaClass)
        //直接启动service
        binding.btnStart.setOnClickListener {
            intent.putExtra("msg1",1001)
            startService(intent)
        }
        //停止service
        binding.btnStop.setOnClickListener {
            stopService(intent)
        }

        binding.btnThird.setOnClickListener {
            val intent = Intent(this, ClientActivity::class.java)
            startActivity(intent)
        }

        binding.btnClear.setOnClickListener{
            intent.putExtra("msg1",1000)
            startService(intent)
        }

    }


    override fun onStart() {
        super.onStart()
        LogUtil.i(TAG, "onStart")
    }

    override fun onRestart() {
        super.onRestart()
        LogUtil.i(TAG, "onRestart")
    }

    override fun onResume() {
        super.onResume()
        LogUtil.i(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        LogUtil.i(TAG, "onPause")
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtil.i(TAG, "onDestroy")
    }


}