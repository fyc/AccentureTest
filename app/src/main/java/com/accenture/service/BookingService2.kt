package com.accenture.service

import android.app.Service
import android.content.ContentValues
import android.content.Intent
import android.content.ServiceConnection
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.os.Binder
import android.os.IBinder
import com.accenture.client.BookingResolver
import com.accenture.utils.FileUtil
import com.accenture.utils.GsonUtil
import com.accenture.utils.LogUtil
import com.accenture.utils.SPUtil
import com.google.gson.Gson


class BookingService2 : Service() {
    val TAG = "BookingService2"

    //此变量定义在类外部就可以被全包访问
    private var mDBHelper: OpenHelperDB? = null

    private var mBinder = BookingBinder(this@BookingService2)

    private var isRun: Boolean = true

    private var myThread: Thread? = null

    var jsonCache = ""//文件名

    override fun onBind(p0: Intent?): IBinder {
        return mBinder
    }


    override fun onCreate() {
        super.onCreate()
        mDBHelper = OpenHelperDB(baseContext)
        SPUtil.getInstance(baseContext)
        LogUtil.i(TAG, "onCreate")
//        myThread = object : Thread() {
//            override fun run() {
//                println("running from Thread: ${Thread.currentThread()}")
//                while (isRun) {
//                    try {
//                        Thread.sleep(5 * 1000) //5分钟执行
//                    } catch (e: Exception) {
//                    }
//
//                    //mock response
//                    jsonCache = application.assets.open("booking.json").bufferedReader().use {
//                        it.readText()
//                    }
////                    val data = GsonUtil.GsonToBean(jsonCache, BigBookingData::class.java)
//                    SPUtil.put("booking_json_time", System.currentTimeMillis())
//                    insert(111, jsonCache)
//                }
//            }
//        }
//        myThread.start()

    }

    private fun insert(uid: Int, json: String) {
        val cv = ContentValues()
        cv.put("uid", uid)
        cv.put("bookingjson", json)
        mDBHelper?.insert(cv)

        val uri = Uri.parse("content://" + AUTHORITES + "/insert") //通知外界，数据发生变化
        baseContext.getContentResolver().notifyChange(uri, null);

    }

//    private fun update(){
//        val values:ContentValues = ContentValues()
//        values.put(NAME, "新")
//        var select:String = "$NAME = ?"
//        resolver?.update(ALL_URI, values, select, arrayOf("旧名"))
//    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        LogUtil.i(TAG, "onStartCommand")
        val msg1 = intent?.getIntExtra("msg1", -1)
        if (msg1 == 1000) {
            val t = object : Thread() {
                override fun run() {
                    SPUtil.clear()
                    mDBHelper?.clear(111)
                    stopSelf()
                }
            }
            t.start()
        } else {
//            val t = object : Thread() {
//                override fun run() {
//
//                }
//            }
//            t.start()

            if (myThread == null) {
                myThread = object : Thread() {
                    override fun run() {
                        println("running from Thread: ${Thread.currentThread()}")
                        val time = SPUtil.get("booking_json_time", 0L) as Long
                        if (time <= 0L || System.currentTimeMillis() - time >= 5 * 60 * 1000) {
                            //mock response
                            jsonCache =
                                application.assets.open("booking.json").bufferedReader().use {
                                    it.readText()
                                }
                            SPUtil.put("booking_json_time", System.currentTimeMillis())
                            insert(111, jsonCache)
                        }

                        while (isRun) {
                            try {
                                Thread.sleep(5 * 1000) //5分钟执行
                            } catch (e: Exception) {
                            }

                            if (isRun && isAlive) {
                                //mock response
                                jsonCache =
                                    application.assets.open("booking.json").bufferedReader().use {
                                        it.readText()
                                    }
//                    val data = GsonUtil.GsonToBean(jsonCache, BigBookingData::class.java)
                                SPUtil.put("booking_json_time", System.currentTimeMillis())
                                insert(111, jsonCache)
                            }
                        }
                    }
                }
                myThread?.start()
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    class BookingBinder(private val mService: BookingService2) : Binder() {
        val TAG = "BookingBinder"
        fun getService(): BookingService2 {
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

    companion object {
        private const val AUTHORITES = "com.accenture.myapplication.bookingDataProvider"
    }

}