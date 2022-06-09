package me.nkremlev.kafka.supu.dev.exception

class ValidationException: RuntimeException {
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable): super(message, cause)
}