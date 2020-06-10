package dev.skymansandy.base.util.logger

import dev.skymansandy.base.util.logger.rule.LOG_TAG
import dev.skymansandy.base.util.logger.rule.Logger
import timber.log.Timber

class TimberLogger : Logger {

    override fun d(msg: String) = Timber.tag(LOG_TAG).d(msg)
    override fun d(tag: String, msg: String) = Timber.tag(tag).d(msg)

    override fun e(msg: String) = Timber.tag(LOG_TAG).e(msg)
    override fun e(tag: String, msg: String) = Timber.tag(tag).e(msg)


    override fun i(msg: String) = Timber.tag(LOG_TAG).i(msg)
    override fun i(tag: String, msg: String) = Timber.tag(tag).i(msg)

    override fun v(msg: String) = Timber.tag(LOG_TAG).v(msg)
    override fun v(tag: String, msg: String) = Timber.tag(tag).v(msg)

    override fun w(msg: String) = Timber.tag(LOG_TAG).w(msg)
    override fun w(tag: String, msg: String) = Timber.tag(tag).w(msg)

    override fun wtf(msg: String) = Timber.tag(LOG_TAG).d(msg)
    override fun wtf(tag: String, msg: String) = Timber.tag(tag).wtf(msg)
}