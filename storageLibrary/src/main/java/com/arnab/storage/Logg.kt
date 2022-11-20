package com.arnab.storage

import android.util.Log


/**
 * Custom Logging with line number
 */
@Suppress("unused")
object Logg {
    private fun tag(): String {
        return Thread.currentThread().stackTrace[4].let {
            val link = "(${it.fileName}:${it.lineNumber})"
            val path = "App# ${it.className.substringAfterLast(".")}.${it.methodName}"
            if (path.length + link.length > 80) {
                "${path.take(80 - link.length)}...${link}"
            } else {
                "$path$link"
            }
        }
    }

    fun v(msg: String?) {
        Log.v(tag(), "💜 $msg")
    }

    fun d(msg: String?) {
        Log.d(tag(), "💙 $msg")
    }

    fun i(msg: String?) {
        Log.i(tag(), "💚 $msg")
    }

    fun w(msg: String?) {
        Log.w(tag(), "💛 $msg")
    }

    fun w(e: Throwable?) {
        Log.w(tag(), "💛 ${e?.localizedMessage}")
        e?.printStackTrace()
    }

    fun w(e: Exception?) {
        Log.w(tag(), "💛 ${e?.localizedMessage}")
        e?.printStackTrace()
    }

    fun w(e: LinkageError?) {
        Log.w(tag(), "💛 ${e?.localizedMessage}")
        e?.printStackTrace()
    }

    fun e(msg: String?) {
        Log.e(tag(), "💔 $msg")
    }

    fun e(e: Throwable?) {
        Log.e(tag(), "💔 ${e?.localizedMessage}")
        e?.printStackTrace()
    }

    fun e(e: java.lang.Exception?) {
        Log.e(tag(), "💔 ${e?.localizedMessage}")
        e?.printStackTrace()
    }
}