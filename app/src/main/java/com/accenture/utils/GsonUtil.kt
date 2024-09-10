package com.accenture.utils

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken


object GsonUtil {
    private var gson: Gson? = null

    init {
        if (GsonUtil.gson == null) {
            GsonUtil.gson = Gson()
        }
    }

    /**
     * 将object对象转成json字符串
     *
     * @param object
     * @return
     */
    fun GsonString(`object`: Any?): String? {
        var gsonString: String? = null
        if (GsonUtil.gson != null) {
            gsonString = GsonUtil.gson!!.toJson(`object`)
        }
        return gsonString
    }

    /**
     * 将gsonString转成泛型bean
     *
     * @param gsonString
     * @param cls
     * @return
     */
    fun <T> GsonToBean(gsonString: String?, cls: Class<T>?): T? {
        var t: T? = null
        if (GsonUtil.gson != null) {
            t = GsonUtil.gson!!.fromJson(gsonString, cls)
        }
        return t
    }

    /**
     * 转成list
     * 泛型在编译期类型被擦除导致报错
     * @param gsonString
     * @param cls
     * @return
     */
    //    public static <T> List<T> GsonToList(String gsonString, Class<T> cls) {
    //        List<T> list = null;
    //        if (gson != null) {
    //            list = gson.fromJson(gsonString, new TypeToken<List<T>>() {
    //            }.getType());
    //        }
    //        return list;
    //    }
    /**
     * 转成list
     * 解决泛型在编译期类型被擦除导致报错
     *
     * @param json
     * @param cls
     * @param <T>
     * @return
    </T> */
    fun <T> jsonToList(json: String?, cls: Class<T>?): List<T> {
        val gson = Gson()
        val list: MutableList<T> = ArrayList()
        val array: JsonArray = JsonParser().parse(json).getAsJsonArray()
        for (elem in array) {
            list.add(gson.fromJson(elem, cls))
        }
        return list
    }

    /**
     * 转成list中有map的
     *
     * @param gsonString
     * @return
     */
    fun <T> GsonToListMaps(gsonString: String?): List<Map<String, T>>? {
        var list: List<Map<String, T>>? = null
        if (GsonUtil.gson != null) {
            list = GsonUtil.gson!!.fromJson(gsonString,
                object : TypeToken<List<Map<String?, T>?>?>() {}.getType()
            )
        }
        return list
    }

    /**
     * 转成map的
     *
     * @param gsonString
     * @return
     */
    fun <T> GsonToMaps(gsonString: String?): Map<String, T>? {
        var map: Map<String, T>? = null
        if (GsonUtil.gson != null) {
            map = GsonUtil.gson!!.fromJson(
                gsonString,
                object : TypeToken<Map<String?, T>?>() {}.getType()
            )
        }
        return map
    }
}