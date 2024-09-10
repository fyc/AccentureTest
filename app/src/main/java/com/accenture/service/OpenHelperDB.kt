package com.accenture.service

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.nio.file.Files.delete

class OpenHelperDB(context: Context?) : SQLiteOpenHelper(context, DATABASE, null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        val create_table =
            "create table " + TABLE + "(id int primary key, uid int,bookingjson varchar not null)"
        db.execSQL(create_table)
//        db.execSQL("insert into " + TABLE + " values(1001, '张三'),(1002, '李四')")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}
    fun query(): Cursor {
        val db = readableDatabase
        val sql = "select * from " + TABLE
        return db.rawQuery(sql, null)
    }

    fun insert(cv: ContentValues?): Long {
        val db = writableDatabase
//        val result = db.insert(TABLE, null, cv)
        val result = db.replace(TABLE, null, cv)
        db.close()
        return result
    }

    fun clear(uid: Int?){
        val db = writableDatabase
        db.delete(TABLE,"uid=" + uid, null);
    }


    companion object {
        private const val DATABASE = "test.db"
        private const val TABLE = "Booking"
    }
}
// class OpenHelperDB(context:Context?, baseName:String, version:Int) : SQLiteOpenHelper(context, baseName, null, version) {
//    //我们的表的创建语句
//    val BASE_TABLE:String = "CREATE TABLE Booking(" +
//            "id integer primary key autoincrement not null," +
//            "name varchar(30) not null);"
//
//    /**
//     * 创建执行函数
//     */
//    override fun onCreate(db: SQLiteDatabase?) {
//        //创建我们的表
//        db?.execSQL(BASE_TABLE)
//    }
//
//    /**
//     * 更新执行函数
//     */
//    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
//
//    }
//
//}
