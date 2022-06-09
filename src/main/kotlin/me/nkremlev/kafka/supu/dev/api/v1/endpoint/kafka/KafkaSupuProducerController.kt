package me.nkremlev.kafka.supu.dev.api.v1.endpoint.kafka

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import io.swagger.v3.oas.annotations.responses.ApiResponse
import me.nkremlev.kafka.supu.dev.api.common.KAFKA_APPLICATION_PATH
import me.nkremlev.kafka.supu.dev.api.common.KAFKA_PRODUCER_INIT
import me.nkremlev.kafka.supu.dev.api.common.KAFKA_SEND_PROVIDER_SERVICES
import me.nkremlev.kafka.supu.dev.api.common.dto.AddProviderServicesDtoRq
import me.nkremlev.kafka.supu.dev.api.common.dto.ResponseDto
import me.nkremlev.kafka.supu.dev.api.v1.processor.KafkaSupuProducerProcessor
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(KAFKA_APPLICATION_PATH, produces = [MediaType.APPLICATION_JSON_VALUE])
@Tag(name = "Kafka-producer SUPU test", description = "Отправка тестовых услуг ПУ в кафка СУПУ")
class KafkaSupuProducerController(private val kafkaSupuProducerProcessor: KafkaSupuProducerProcessor) {

    @Operation(
        summary = "Initialization kafka producer",
        description = "Инициализация kafka producer",
        responses = [
            ApiResponse(responseCode = "200"),
            ApiResponse(responseCode = "422", description = "Ошибка валидации параметров запроса"),
            ApiResponse(responseCode = "500", description = "Ошибка приложения")
        ]
    )
    @GetMapping(KAFKA_PRODUCER_INIT)
    suspend fun kafkaInit(
        @Parameter(description = "Хост и порт для подключения к kafka", required = true)
        @RequestParam(required = true, defaultValue = "localhost:9092")
        bootstrapServers: String,

        @Parameter(description = "key-store password", required = false)
        @RequestParam(required = false)
        keyStorePassword: String?,

        @Parameter(description = "key-store location", required = false)
        @RequestParam(required = false)
        keyStoreLocation: String?,

        @Parameter(description = "trust-store password", required = false)
        @RequestParam(required = false)
        trustStorePassword: String? = null,

        @Parameter(description = "trust-store location", required = false)
        @RequestParam(required = false)
        trustStoreLocation: String? = null
    ): ResponseEntity<ResponseDto> {
        return kafkaSupuProducerProcessor.kafkaInit(
            bootstrapServers,
            keyStorePassword, keyStoreLocation,
            trustStorePassword, trustStoreLocation)
    }

    @Operation(
        summary = "Send kafka provider services",
        description = "Отправка в кафка услуг ПУ",
        responses = [
            ApiResponse(responseCode = "200"),
            ApiResponse(responseCode = "422", description = "Ошибка инициализации kafka producer"),
            ApiResponse(responseCode = "500", description = "Ошибка приложения")
        ]
    )
    @PostMapping(KAFKA_SEND_PROVIDER_SERVICES)
    suspend fun kafkaSendProviderServices(
        @Parameter(description = "название топика в кафке", required = true)
        @RequestParam(required = true, defaultValue = "test")
        topicName: String,

        @Parameter(description = "Услуги ПУ")
        @RequestBody
        producerServices: AddProviderServicesDtoRq
    ): ResponseEntity<ResponseDto> {
        return kafkaSupuProducerProcessor.kafkaSendProviderServices(topicName, producerServices.data)
    }
}