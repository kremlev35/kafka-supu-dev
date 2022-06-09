package me.nkremlev.kafka.supu.dev.util.log

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.concurrent.ConcurrentHashMap

object CustomLoggerFactory {
    private val loggersMap: MutableMap<String, Logger> = ConcurrentHashMap()

    fun <T : Any> getLogger(clazz: Class<T>): Logger {
        val nonCompanionClazz = clazz.unwrapCompanionClass()
        return getLogger(nonCompanionClazz.name)
    }

    fun getLogger(className: String): Logger {
        val logger = loggersMap[className]
        return when {
            logger != null -> logger
            else -> LoggerFactory.getLogger(className).also {
                loggersMap[className] = it
            }
        }
    }
}