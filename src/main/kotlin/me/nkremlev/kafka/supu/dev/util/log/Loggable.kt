package me.nkremlev.kafka.supu.dev.util.log

import org.slf4j.Logger

interface Loggable {
    val logger: Logger get() = CustomLoggerFactory.getLogger(javaClass)

    fun logInfo(s: String?) = logger.info(s)

    fun logDebug(s: String?) = logger.debug(s)

    fun logWarn(t: Throwable) = logger.warn(t.message, t)

    fun logWarn(message: String?) = logger.warn(message)

    fun logTrace(message: String?) = logger.trace(message)

    fun logError(message: String?, throwable: Throwable) = logger.error(message, throwable)

    fun logError(throwable: Throwable, message: String? = throwable.message) = logger.error(message, throwable)
}