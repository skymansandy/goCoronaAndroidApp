package dev.skymansandy.base.util.logger.rule

interface Logger {
    fun d(msg: String)
    fun d(tag: String, msg: String)

    fun e(msg: String)
    fun e(tag: String, msg: String)

    fun i(msg: String)
    fun i(tag: String, msg: String)

    fun v(msg: String)
    fun v(tag: String, msg: String)

    fun w(msg: String)
    fun w(tag: String, msg: String)

    fun wtf(msg: String)
    fun wtf(tag: String, msg: String)
}

const val LOG_TAG = "Logger"