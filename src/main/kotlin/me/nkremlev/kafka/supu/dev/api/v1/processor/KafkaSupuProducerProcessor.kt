package me.nkremlev.kafka.supu.dev.api.v1.processor

import me.nkremlev.kafka.supu.dev.api.common.dto.ProviderServiceDtoRq
import me.nkremlev.kafka.supu.dev.api.common.dto.ResponseDto
import org.springframework.http.ResponseEntity

/**
 * Процессор для клиентского API
 */
interface KafkaSupuProducerProcessor {

    /**
     * Инициализация kafka producer
     *
     * @param bootstrapServers хост и порт к kafka
     * @param keyStorePassword пароль для key-store
     * @param keyStoreLocation путь до key-store
     * @param trustStorePassword пароль для trust-store
     * @param trustStoreLocation путь до trust-store
     *
     * @return ответ успешного или неуспешного выполнения данного запроса
     */
    suspend fun kafkaInit(bootstrapServers: String,
                          keyStorePassword: String? = null,
                          keyStoreLocation: String? = null,
                          trustStorePassword: String? = null,
                          trustStoreLocation: String? = null): ResponseEntity<ResponseDto>

    /**
     * Отправка услуг ПУ в кафка
     *
     * @param topicName название топика kafka
     * @param data список услуг ПУ
     *
     * @return ответ успешного или неуспешного выполнения данного запроса
     */
    suspend fun kafkaSendProviderServices(topicName: String, data: List<ProviderServiceDtoRq>): ResponseEntity<ResponseDto>
}