package me.nkremlev.kafka.supu.dev.util.log

inline fun <T : Any> Class<T>.unwrapCompanionClass(): Class<*> = when {
    name.endsWith("\$Companion") -> enclosingClass ?: this
    else -> this
}