package com.accenture.service

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.util.Log

class BookingDataProvider : ContentProvider() {
    private var mDBHelper: OpenHelperDB? = null
    override fun onCreate(): Boolean {
        mDBHelper = OpenHelperDB(context)
        return false
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        val code = sUriMatcher.match(uri)
        return if (code == QUERY) {
            mDBHelper?.query() //数据库
        } else null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val code = sUriMatcher.match(uri)
        if (code == INSERT) {
            mDBHelper?.insert(values)
        }
        context!!.contentResolver.notifyChange(uri, null) //通知外界，数据发生变化
        return null
    }

    override fun getType(uri: Uri): String? { //获取资源 MIME
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }

    companion object {
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH) //用于匹配URI，并返回对应的操作编码
        private const val AUTHORITES = "com.accenture.myapplication.bookingDataProvider"
        private const val QUERY = 1 //查询操作编码
        private const val INSERT = 2 //插入操作编码

        init { //添加有效的 URI 及其编码
            sUriMatcher.addURI(AUTHORITES, "/query", QUERY)
            sUriMatcher.addURI(AUTHORITES, "/insert", INSERT)
        }
    }
}
