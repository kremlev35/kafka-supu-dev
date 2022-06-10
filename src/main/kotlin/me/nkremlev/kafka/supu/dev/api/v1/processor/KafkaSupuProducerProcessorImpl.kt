package me.nkremlev.kafka.supu.dev.api.v1.processor

import com.fasterxml.jackson.databind.ObjectMapper
import me.nkremlev.kafka.supu.dev.api.common.dto.ProviderServiceDtoRq
import me.nkremlev.kafka.supu.dev.api.common.dto.ResponseDto
import me.nkremlev.kafka.supu.dev.exception.ValidationException
import me.nkremlev.kafka.supu.dev.service.ValidationService
import me.nkremlev.kafka.supu.dev.util.log.Loggable
import org.apache.kafka.clients.CommonClientConfigs
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.config.SslConfigs
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import java.lang.StringBuilder

@Component
class KafkaSupuProducerProcessorImpl(
    private val validationService: ValidationService,
    private val objectMapper: ObjectMapper): KafkaSupuProducerProcessor {

    companion object: Loggable

    private var kafkaTemplate: KafkaTemplate<String, String>? = null
    private val senderProps = mutableMapOf<String, Any>(
        ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
        ProducerConfig.RETRIES_CONFIG to 2
    )

    override suspend fun kafkaInit(bootstrapServers: String,
                                   keyStorePassword: String?,
                                   keyStoreLocation: String?,
                                   trustStorePassword: String?,
                                   trustStoreLocation: String?): ResponseEntity<ResponseDto> {
        try {
            validationService.validKafkaInitSsl(bootstrapServers, keyStorePassword, keyStoreLocation, trustStorePassword, trustStoreLocation)
            addSendProps(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers)
            addSendProps(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, keyStorePassword)
            addSendProps(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, keyStoreLocation)
            addSendProps(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, trustStorePassword)
            addSendProps(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, trustStoreLocation)
            addSendProps(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SSL")

            kafkaTemplate = KafkaTemplate(DefaultKafkaProducerFactory(senderProps))
            return ResponseEntity(ResponseDto.success(), HttpStatus.OK)
        } catch (validEx: ValidationException) {
            logger.error(validEx.message, validEx)
            return ResponseEntity(ResponseDto.fail(validEx.message), HttpStatus.UNPROCESSABLE_ENTITY)
        } catch (ex: Exception) {
            logger.error(ex.message, ex)
            return ResponseEntity(ResponseDto.fail(ex.message), HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    override suspend fun kafkaSendProviderServices(topicName: String, data: List<ProviderServiceDtoRq>): ResponseEntity<ResponseDto> {
        if (kafkaTemplate == null) {
            return ResponseEntity(ResponseDto.fail("Kafka producer не инициализирован"), HttpStatus.UNPROCESSABLE_ENTITY)
        }

        return try {
            val infoMessages = mutableListOf<String>()
            data.forEach { providerService ->
                infoMessages.add(sendToProviderService(topicName, providerService))
            }
            ResponseEntity(ResponseDto.success(infoMessages), HttpStatus.OK)
        } catch (ex: Exception) {
            logger.error(ex.message, ex)
            ResponseEntity(ResponseDto.fail(ex.message), HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    private fun addSendProps(key: String, value: String?) {
        if (!value.isNullOrEmpty())
            senderProps[key] = value
    }

    private fun sendToProviderService(topicName: String, providerService: ProviderServiceDtoRq): String {
        val resultString = StringBuilder()
        try {
            val kafkaResult = kafkaTemplate!!.send(topicName, objectMapper.writeValueAsString(providerService))
            val result = kafkaResult.get()
            resultString.append("Topic name: ${result.recordMetadata.topic()}; ")
            resultString.append("Offset: ${result.recordMetadata.offset()}; ")
            resultString.append("Partition: ${result.recordMetadata.partition()}; ")
            resultString.append("Serialized value size: ${result.recordMetadata.serializedValueSize()};")
        } catch (ex: Exception) {
            logger.error("Ошибка отправления сообщения в kafka", ex)
            throw ex
        }
        return resultString.toString()
    }
}