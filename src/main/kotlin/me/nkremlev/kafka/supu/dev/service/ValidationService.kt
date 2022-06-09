package me.nkremlev.kafka.supu.dev.service

/**
 * Сервис для валидации данных
 */
interface ValidationService {

    /**
     * Валидация параметров для инициализации producer kafka
     *
     * @param bootstrapServers хост и порт к kafka
     * @param keyStorePassword пароль для key-store
     * @param keyStoreLocation путь до key-store
     * @param trustStorePassword пароль для trust-store
     * @param trustStoreLocation путь до trust-store
     *
     * @throws ValidationException
     */
    fun validKafkaInitSsl(bootstrapServers: String,
                          keyStorePassword: String?, keyStoreLocation: String?,
                          trustStorePassword: String?, trustStoreLocation: String?)
}