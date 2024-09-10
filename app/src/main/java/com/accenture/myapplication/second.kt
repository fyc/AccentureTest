//package com.accenture.myapplication
//
//import android.app.Service
//import android.content.ComponentName
//import android.content.Intent
//import android.content.ServiceConnection
//import android.os.*
//import androidx.appcompat.app.AppCompatActivity
//import com.accenture.myapplication.databinding.ActivityMainBinding
//import com.accenture.services.BookingService
//import com.accenture.utils.LogUtil
//
//
//private val TAG = "MainActivity"
//
//
//val RECEIVE_MESSAGE_CODE : Int  = 0x0002
//
////客户端的消息处理
//class ClientHndler : Handler(){
//    override fun handleMessage(msg: Message) {
//        super.handleMessage(msg)
//        LogUtil.i(TAG,"clientHndler")
//        when (msg.what) {
//            RECEIVE_MESSAGE_CODE -> {
//                LogUtil.i(TAG,"这是来自服务端的问候：" + msg.obj)
//            }
//            else -> {
//                LogUtil.i(TAG,"未接收到服务端的问候！！！")
//            }
//        }
//    }
//}
//
////初始化客户端的messenger
//val clientMessenger = Messenger(ClientHndler())
//
//class second : AppCompatActivity(),ServiceConnection{
//    override fun onServiceDisconnected(p0: ComponentName?) {
//        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//        LogUtil.i(TAG,"onServiceDisconnected")
//    }
//
//    private lateinit var mService: BookingService
//
//    override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
//        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//        LogUtil.i(TAG,"onServiceConnected")
//        var mBinder = p1 as BookingService.BookingBinder
//        mService = mBinder.getService()
//    }
//
//
//
//    class Conn : ServiceConnection{
//        val TAG = "Conn"
//        override fun onServiceDisconnected(p0: ComponentName?) {
//            // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            LogUtil.i(TAG,"onServiceDisconnected")
//        }
//
//        lateinit var serviceMessenger: Messenger
//
//        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
//            // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            LogUtil.i(TAG,"onServiceConnected")
//            serviceMessenger = Messenger(p1)
//        }
//
//    }
//
//
//    val conn = Conn()
//
//    private var bookbinders = Intent()
//    private var movieBinders = Intent()
//    private var sbStart = Intent()
//    private var sbBind = Intent()
//
//    private lateinit var binding: ActivityMainBinding
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
////        setContentView(R.layout.activity_main)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        LogUtil.i(TAG,"onCreate")
//        //直接启动service
//        binding.btnStart.setOnClickListener {
//            bookbinders.setClass(this,BookingService().javaClass)
//            startService(bookbinders)
//        }
//        //停止service
//        btn_stop.setOnClickListener {
//            stopService(bookbinders)
//        }
//
//        //绑定service
//        btn_bind.setOnClickListener {
//            movieBinders.setClass(this,BookingService::class.java)
//            bindService(movieBinders,this,Service.BIND_AUTO_CREATE)
//        }
//        //解除绑定
//        btn_unbind.setOnClickListener {
//            unbindService(this)
//        }
//        //获取数据
//        btn_getdata.setOnClickListener {
//            val count = movieService.movieName
//            println("从service获取的count===$count")
//        }
//        //msg  bind
//        btn_msg_bind.setOnClickListener {
//            var  intent = Intent()
//            intent.setClass(this@MainActivity,MusicService::class.java)
//            bindService(intent,conn,Service.BIND_AUTO_CREATE)
//        }
//        //给service发送消息
//        btn_send_msg.setOnClickListener {
//            val message = Message.obtain(null, SEND_MESSAGE_CODE, 0, 0)
//            message.obj = "来自客户端的问候！！！"
//            message.replyTo = clientMessenger
//            conn.serviceMessenger.send(message)
//        }
//        //取消绑定
//        btn_msg_unbind.setOnClickListener {
//            unbindService(conn)
//        }
//
//        //先启动service
//        btn_sb_start.setOnClickListener {
//            sbStart.setClass(this@MainActivity,StartAndBindService::class.java)
//            startService(sbStart)
//        }
//        btn_sb_stop.setOnClickListener {
//            stopService(sbStart)
//        }
//        //再绑定service
//        btn_sb_bind.setOnClickListener {
//            bindService(Intent(this@MainActivity,StartAndBindService::class.java),conn,Service.BIND_AUTO_CREATE)
//        }
//        btn_sb_unbind.setOnClickListener {
//            unbindService(conn)
//        }
//
//    }
//
//
//    override fun onStart() {
//        super.onStart()
//        LogUtil.i(TAG,"onStart")
//    }
//
//    override fun onRestart() {
//        super.onRestart()
//        LogUtil.i(TAG,"onRestart")
//    }
//
//    override fun onResume() {
//        super.onResume()
//        LogUtil.i(TAG,"onResume")
//    }
//    override fun onPause() {
//        super.onPause()
//        LogUtil.i(TAG,"onPause")
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        LogUtil.i(TAG,"onDestroy")
//    }
//
//
//}