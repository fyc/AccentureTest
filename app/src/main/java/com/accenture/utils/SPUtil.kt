package com.accenture.utils

import android.content.Context
import android.content.SharedPreferences

class SPUtil private constructor(context: Context) {
    init {
        sharedPreferences = context.getSharedPreferences("shared_data", 0)
        editor = sharedPreferences.edit()
    }

    companion object {
        private lateinit var sharedPreferences: SharedPreferences
        const val FILE_NAME = "shared_data"
        private lateinit var editor: SharedPreferences.Editor
        private var mSharedPreferencesUtils: SPUtil? = null
        fun getInstance(context: Context): SPUtil? {
            if (mSharedPreferencesUtils == null) {
                val var1: Class<*> = SPUtil::class.java
                synchronized(SPUtil::class.java) {
                    if (mSharedPreferencesUtils == null) {
                        mSharedPreferencesUtils = SPUtil(context)
                    }
                }
            }
            return mSharedPreferencesUtils
        }

        fun put(key: String?, `object`: Any) {
            if (`object` is String) {
                editor.putString(key, `object`)
            } else if (`object` is Int) {
                editor.putInt(key, `object`)
            } else if (`object` is Boolean) {
                editor.putBoolean(key, `object`)
            } else if (`object` is Float) {
                editor.putFloat(key, `object`)
            } else if (`object` is Long) {
                editor.putLong(key, `object`)
            } else {
                editor.putString(key, `object`.toString())
            }
            editor.commit()
        }

        operator fun get(key: String?, defaultObject: Any?): Any? {
            return if (defaultObject is String) {
                sharedPreferences.getString(key, defaultObject as String?)
            } else if (defaultObject is Int) {
                sharedPreferences.getInt(key, (defaultObject as Int?)!!)
            } else if (defaultObject is Boolean) {
                sharedPreferences.getBoolean(
                    key,
                    (defaultObject as Boolean?)!!
                )
            } else if (defaultObject is Float) {
                sharedPreferences.getFloat(
                    key,
                    (defaultObject as Float?)!!
                )
            } else {
                if (defaultObject is Long) sharedPreferences.getLong(
                    key,
                    (defaultObject as Long?)!!
                ) else sharedPreferences.getString(key, null as String?)
            }
        }

        fun remove(key: String?) {
            editor.remove(key)
            editor.commit()
        }

        fun clear() {
            editor.clear()
            editor.commit()
        }

        operator fun contains(key: String?): Boolean {
            return sharedPreferences.contains(key)
        }

        val all: Map<String, *>
            get() = sharedPreferences.all
    }
}

