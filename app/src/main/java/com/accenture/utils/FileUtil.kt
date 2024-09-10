package com.accenture.utils

import android.content.Context
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class FileUtil {
    companion object {
        /*保存文件*/
        @JvmStatic
        fun saveFile(context: Context, fileName: String, cont: String) {
            try {
                //第一个参数是文件名
                //第二个参数是文件的操作模式(相同文件MODE_PRIVATE覆盖，MODE_APPEND追加内容)
                val output = context.openFileOutput(fileName, Context.MODE_PRIVATE)
                val writer = BufferedWriter(OutputStreamWriter(output))
                writer.use {
                    it.write(cont)
                }
            } catch (e: Exception) {
            }
        }

        /*读取文件*/
        @JvmStatic
        fun readerFile(context: Context, fileName: String): String {
            val content = StringBuffer()
            try {
                val input = context.openFileInput(fileName)
                val reader = BufferedReader(InputStreamReader(input))
                reader.use {
                    reader.forEachLine {
                        content.append(it)
                    }
                }
            } catch (e: Exception) {
            }
            return content.toString()
        }
    }
}