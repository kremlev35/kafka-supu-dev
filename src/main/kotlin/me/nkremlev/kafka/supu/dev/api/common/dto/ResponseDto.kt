package me.nkremlev.kafka.supu.dev.api.common.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Информация о выполнении запроса")
data class ResponseDto(
    @field:Schema(description = "Успешное выполнение")
    val success: Boolean? = null,
    @field:Schema(description = "")
    val infoMessages: List<String>? = null,
    @field:Schema(description = "")
    val fail: Boolean? = null,
    @field:Schema(description = "")
    val errorMessage: String? = null
) {
    companion object {
        fun success(infoMessages: List<String>? = null) = ResponseDto(success = true, infoMessages = infoMessages)
        fun fail(errorMessage: String? = null) =
            ResponseDto(fail = true, errorMessage = if(errorMessage.isNullOrEmpty()) "Ошибка выполнения запроса" else errorMessage)
    }
}