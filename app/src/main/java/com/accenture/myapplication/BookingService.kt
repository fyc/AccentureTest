package com.accenture.myapplication

import android.app.Service
import android.content.Intent
import android.content.ServiceConnection
import android.os.Binder
import android.os.IBinder
import com.accenture.utils.FileUtil
import com.accenture.utils.LogUtil
import com.accenture.utils.SPUtil

class BookingService : Service() {
    val TAG = "BookingService"
    //此变量定义在类外部就可以被全包访问

    private var mBinder = BookingBinder(this@BookingService)

    private var isRun: Boolean = true

    private lateinit var myThread: Thread

    val fileName = "bookjsoncatch"//文件名
    var jsonCache = ""//文件名

    private var mCallback: Callback? = null
    interface Callback {
        fun onDataChange(data: String?)
    }

    fun setCallback(callback: Callback?) {
        mCallback = callback
    }




    override fun onBind(p0: Intent?): IBinder {
        return mBinder
    }


    override fun onCreate() {
        super.onCreate()
        LogUtil.i(TAG, "onCreate")
        SPUtil.getInstance(baseContext)
        myThread = object : Thread() {
            override fun run() {
                println("running from Thread: ${Thread.currentThread()}")
                val time = SPUtil.get("booking_json_time", 0L) as Long
                if (time <= 0L || System.currentTimeMillis() - time >= 5 * 60 * 1000) {
                    //mock response
                    jsonCache = application.assets.open("booking.json").bufferedReader().use {
                        it.readText()
                    }
                    SPUtil.put("booking_json_time", System.currentTimeMillis())
                }
                while (isRun) {
                    try {
                        Thread.sleep(10 * 1000)
                    } catch (e: Exception) {
                    }

                    //mock response
                    jsonCache = application.assets.open("booking.json").bufferedReader().use {
                        it.readText()
                    }
                    mCallback?.onDataChange(jsonCache);
//                    println(jsonCache)
                }
            }
        }
        myThread.start()

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        LogUtil.i(TAG, "onStartCommand")
        jsonCache = FileUtil.readerFile(this, fileName)
        return super.onStartCommand(intent, flags, startId)
    }

    class BookingBinder(private val mService: BookingService) : Binder() {
        val TAG = "BookingBinder"
        fun getService(): BookingService {
            LogUtil.i(TAG, "getService")
            return mService
        }
    }

    override fun unbindService(conn: ServiceConnection) {
        super.unbindService(conn)
        LogUtil.i(TAG, "unbindService")
    }


    override fun onDestroy() {
        super.onDestroy()
        isRun = false
        LogUtil.i(TAG, "onDestroy")
    }

}