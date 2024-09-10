package com.accenture.client

import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.accenture.myapplication.databinding.ActivityClientBinding

class ClientActivity : AppCompatActivity() {

    private var mObserver: BookingObserver? = null
    private var mResolver: BookingResolver? = null
    private lateinit var binding: ActivityClientBinding

    private val mHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            if (msg.what === 0x111) {
                Toast.makeText(this@ClientActivity,"数据改变了,开始自动刷新",Toast.LENGTH_SHORT).show()
                query()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClientBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mObserver = BookingObserver(this, mHandler)
        mObserver?.registerObserver()
        mResolver = BookingResolver(this)
        binding.btnGet.setOnClickListener {
            query()
        }
    }

    /**
     * 查询
     */
    private fun query(): Unit {
//        val cursor:Cursor? = mResolver?.query()
//        if(cursor == null) println("cursor为空")
//        if(cursor != null){
//            while(cursor.moveToNext()){
//                var index_name:Int = cursor.getColumnIndex(NAME)
//                println(cursor.getString(index_name))
//            }
        val list = mResolver?.query()
        if (list?.size!! <= 0) binding.show.setText("没有数据")
        else binding.show.setText(list?.get(0))
    }


    /**
     * 插入数据
     */
//private fun insert() {
//    val values: ContentValues = ContentValues()
//    values.put(NAME, "旧名")
//    if (resolver == null) println("resolver为空")
//    resolver?.insert(ALL_URI, values)
//}

    /**
     * 删除操作
     */
//private fun delete() {
//    resolver?.delete(ALL_URI, null, null)
//}

    /**
     * 更新操作
     */
//private fun update() {
//    val values: ContentValues = ContentValues()
//    values.put(NAME, "新")
//    var select: String = "$NAME = ?"
//    resolver?.update(ALL_URI, values, select, arrayOf("旧名"))
//}

    override fun onDestroy() {
        mObserver?.unregisterObserver()
        super.onDestroy()
    }

}




