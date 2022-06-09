package me.nkremlev.kafka.supu.dev.api.common.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Запрос добавления или обновления услуг ПУ")
data class AddProviderServicesDtoRq(
    @field:Schema(description = "Услуги ПУ")
    val data: List<ProviderServiceDtoRq>)