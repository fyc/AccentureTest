package com.accenture.client

import android.content.ContentResolver
import android.content.Context
import android.net.Uri

class BookingResolver(context: Context) {
    private val mContentResolver: ContentResolver

    init {
        mContentResolver = context.contentResolver
    }

    fun query(): List<String>? {
//        val list: ArrayList<BookingData> = ArrayList<BookingData>()
        val uri = Uri.parse("content://" + AUTHORITES + "/query")
        val cursor = mContentResolver.query(uri, null, null, null, null)
        val  list:ArrayList<String> = ArrayList()
        while (cursor!!.moveToNext()) {
             val uid = cursor.getInt(1)
             val json= cursor.getString(2)
//            println("uid:$uid-----json:$json")
            list.add(json)
        }
//        cursor?.moveToFirst()
        cursor.close()
        return list
    }

//    fun insert(user: BookingData) {
//        val uri = Uri.parse("content://" + AUTHORITES + "/insert")
//        val cv = ContentValues()
//        cv.put("id", user.id)
//        cv.put("originAndDestinationPair", user.originAndDestinationPair)
//        mContentResolver.insert(uri, cv)
//    }

    companion object {
        private const val AUTHORITES = "com.accenture.myapplication.bookingDataProvider"
    }
}