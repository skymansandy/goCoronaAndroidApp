package dev.skymansandy.base.util.logger

import android.annotation.SuppressLint
import android.util.Log
import dev.skymansandy.base.util.logger.rule.LOG_TAG
import dev.skymansandy.base.util.logger.rule.Logger

@SuppressLint("LogNotTimber")
class DefaultLogger : Logger {

    override fun d(msg: String) {
        Log.d(LOG_TAG, msg)
    }

    override fun d(tag: String, msg: String) {
        Log.d(tag, msg)
    }

    override fun e(msg: String) {
        Log.e(LOG_TAG, msg)
    }

    override fun e(tag: String, msg: String) {
        Log.e(tag, msg)
    }

    override fun i(msg: String) {
        Log.i(LOG_TAG, msg)
    }

    override fun i(tag: String, msg: String) {
        Log.i(tag, msg)
    }

    override fun v(msg: String) {
        Log.v(LOG_TAG, msg)
    }

    override fun v(tag: String, msg: String) {
        Log.v(tag, msg)
    }

    override fun w(msg: String) {
        Log.w(LOG_TAG, msg)
    }

    override fun w(tag: String, msg: String) {
        Log.w(tag, msg)
    }

    override fun wtf(msg: String) {
        Log.d(LOG_TAG, msg)
    }

    override fun wtf(tag: String, msg: String) {
        Log.wtf(tag, msg)
    }
}