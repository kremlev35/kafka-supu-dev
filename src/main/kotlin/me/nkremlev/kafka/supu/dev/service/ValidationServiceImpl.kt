package me.nkremlev.kafka.supu.dev.service

import me.nkremlev.kafka.supu.dev.exception.ValidationException
import org.springframework.stereotype.Service

@Service
class ValidationServiceImpl: ValidationService {

    override fun validKafkaInitSsl(
        bootstrapServers: String,
        keyStorePassword: String?,
        keyStoreLocation: String?,
        trustStorePassword: String?,
        trustStoreLocation: String?
    ) {
        if (!bootstrapServers.matches(Regex("[a-zA-Z0-9-._:]+:[0-9]{4}"))) {
            throw ValidationException("bootstrap servers не соответствует URL формату")
        }
        if (!keyStorePassword.isNullOrEmpty() && keyStoreLocation.isNullOrEmpty()) {
            throw ValidationException("При указании key-store-password параметр key-store-location не может быть пустым")
        }
        if (!trustStorePassword.isNullOrEmpty() && trustStoreLocation.isNullOrEmpty()) {
            throw ValidationException("При указании trust-store-password параметр trust-store-location не может быть пустым")
        }
    }
}