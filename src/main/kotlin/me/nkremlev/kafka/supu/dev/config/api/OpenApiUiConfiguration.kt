package me.nkremlev.kafka.supu.dev.config.api

import me.nkremlev.kafka.supu.dev.api.v1.endpoint.kafka.KafkaSupuProducerController
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@Configuration
@PropertySource("classpath:open-api-ui.properties")
//@ComponentScan("me.nkremlev.kafka.supu.dev")
class OpenApiUiConfiguration {

    @Bean
    fun clientV1() = defineOpenApi(
        group = "kafka-supu-producer-test",
        packageName = KafkaSupuProducerController::class.java.`package`.name,
        version = "1",
        title = "Kafka SUPU producer for test",
        description = "Загрузка тестовых данных услуг ПУ в кафка"
    )
}