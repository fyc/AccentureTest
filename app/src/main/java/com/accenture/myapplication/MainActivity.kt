package com.accenture.myapplication

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Message
import androidx.appcompat.app.AppCompatActivity
import com.accenture.myapplication.databinding.ActivityMainBinding
import com.accenture.utils.LogUtil


private val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    //    private val mHandler: Handler = object : Handler(Looper.getMainLooper()) {
//        override fun handleMessage(msg: Message) {
//            super.handleMessage(msg)
//            when (msg?.what) {
//            }
//        }
//    }
    class Conn : ServiceConnection {
        val TAG = "Conn"
        private var mCallback: BookingService.Callback? = null
        override fun onServiceDisconnected(p0: ComponentName?) {
            LogUtil.i(TAG, "onServiceDisconnected")
        }

        var mService: BookingService? = null

        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            LogUtil.i(TAG, "onServiceConnected")
            val mBinder = p1 as BookingService.BookingBinder
            mService = mBinder.getService()
//            mService?.setCallback(object : BookingService.Callback {
//                override fun onDataChange(data: String?) {
//                    if (data != null) {
//                        LogUtil.d("从service获取的jsonCache", data)
//                    }
//                }
//            })
            mService?.setCallback(mCallback)
        }

        fun setCallback(callback: BookingService.Callback?) {
            mCallback = callback
        }
    }

    private var isBound = false
    private val conn = Conn()
    private var bookIntent = Intent()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogUtil.i(TAG, "onCreate")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        conn.setCallback(object : BookingService.Callback {
            override fun onDataChange(data: String?) {
                if (data != null) {
                    binding.show.setText(data)
                } else {
                    binding.show.setText("没有数据")
                }
            }
        })
        bookIntent.setClass(this, BookingService().javaClass)
        //直接启动service
        binding.btnStart.setOnClickListener {
            startService(bookIntent)
        }
        //停止service
        binding.btnStop.setOnClickListener {
            stopService(bookIntent)
        }

        binding.btnSecond.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
//            intent.putExtra("main_data", "data_from_main")
            startActivity(intent)
        }

        //绑定service
        binding.btnBind.setOnClickListener {
            isBound = true
            bindService(bookIntent, conn, Service.BIND_AUTO_CREATE)
        }
        //解除绑定
        binding.btnUnbind.setOnClickListener {
            if (isBound) {
                unbindService(conn)
                isBound = false
            }
        }
        //获取数据
        binding.btnGetdata.setOnClickListener {
            val jsonCache = conn.mService?.jsonCache
//            println("从service获取的jsonCache===")
//            println(jsonCache)
            if (jsonCache != null) {
                binding.show.setText(jsonCache)
            } else {
                binding.show.setText("可能service没有启动成功，没有数据")
            }
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