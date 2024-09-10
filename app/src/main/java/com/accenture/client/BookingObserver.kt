package com.accenture.client

import android.content.ContentResolver
import android.content.Context
import android.database.ContentObserver
import android.net.Uri
import android.os.Handler

class BookingObserver(context: Context, var mHandler: Handler) :
    ContentObserver(mHandler) {
    val uri = Uri.parse("content://" + AUTHORITES + "/insert")
    private val mResolver: ContentResolver

    init {
        mResolver = context.contentResolver
    }

    override fun onChange(selfChange: Boolean) {
        mHandler.sendEmptyMessage(0x111)
    }

    fun registerObserver() {
        mResolver.registerContentObserver(uri, false, this)
    }

    fun unregisterObserver() {
        mResolver.unregisterContentObserver(this)
    }

    companion object {
        private const val AUTHORITES = "com.accenture.myapplication.bookingDataProvider"
    }
}