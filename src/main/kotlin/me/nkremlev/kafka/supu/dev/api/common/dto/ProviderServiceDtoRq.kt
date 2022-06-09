package me.nkremlev.kafka.supu.dev.api.common.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Информация об услуге")
data class ProviderServiceDtoRq(
    @field:Schema(description = "Идентификатор услуги", required = true)
    val serviceId: String,

    @field:Schema(description = "Поставщик услуги", required = true)
    val provider: ProviderDtoRs,

    @field:Schema(description = "Статус услуги", required = true)
    val isActive: Boolean,

    @field:Schema(description = "Услуга на ОПЭ", required = true)
    val isOnTest: Boolean,

    @field:Schema(description = "Код операции", required = true)
    val operationCode: String,

    @field:Schema(description = "Код фасадной операции")
    val facadeOperationCode: String?,

    @field:Schema(description = "Наименование услуги", required = true)
    val name: String,

    @field:Schema(description = "Короткое наименование услуги", required = true)
    val shortName: String?,

    @field:Schema(description = "Описание услуги", required = true)
    val description: String,

    @field:Schema(description = "Теги для поиска", required = true)
    val tags: List<String>,

    @field:Schema(description = "Получатель платежа", required = true)
    val receiver: ReceiverDtoRs,

    @field:Schema(description = "Старый вариант действий над услугой (маска операции)")
    val legacyActions: Int?,

    @field:Schema(description = "Возможные действия над услугой")
    val actions: ActionsDto?,

    @field:Schema(description = "Получение начислений по услуге", required = true)
    val invoiceEnabled: Boolean,

    @field:Schema(description = "Список идентификаторов категорий каталога, в которых доступна услуга", required = true)
    val categoryIds: List<String>,

    @field:Schema(description = "Список кодов регионов, в которых доступна услуга", required = true)
    val regionIds: List<String>,

    @field:Schema(description = "Доступность услуги в каналах", required = true)
    val channels: ChannelsDtoRs
)

@Schema(description = "Поставщик услуги")
data class ProviderDtoRs(
    @field:Schema(description = "Идентификатор поставщика")
    val id: String,

    @field:Schema(description = "Наименование поставщика")
    val name: String,

    @field:Schema(description = "ИНН получателя платежа", minLength = 10, maxLength = 12)
    val inn: String
)

@Schema(description = "Возможные действия над услугой")
data class ActionsDto(
    @field:Schema(description = "Платеж", required = true)
    val isPaymentAllowed: Boolean,

    @field:Schema(description = "Создание шаблона", required = true)
    val isTemplateCreationAllowed: Boolean,

    @field:Schema(description = "Создание автоплатежа по дате", required = true)
    val isAutopaymentByDateAllowed: Boolean,

    @field:Schema(description = "Создание напоминания", required = true)
    val isRemindAllowed: Boolean
)

@Schema(description = "Доступность в каналах")
data class ChannelsDtoRs(
    @field:Schema(description = "Мобильный банк", required = true)
    val isMobileBankOn: Boolean,

    @field:Schema(description = "Интернет банк", required = true)
    val isInternetBankOn: Boolean,

    @field:Schema(description = "Банкомат", required = true)
    val isAtmBankOn: Boolean
)

@Schema(description = "Получатель услуги")
data class ReceiverDtoRs(
    @field:Schema(description = "Номер счета", required = true, maxLength = 20)
    val account: String,

    @field:Schema(description = "Банк", required = true)
    val bank: BankDtoRs
)

@Schema(description = "Информация о банке")
data class BankDtoRs(
    @field:Schema(description = "Наименование банка", maxLength = 350)
    val name: String,

    @field:Schema(description = "Корреспондентский счет", maxLength = 20)
    val corAccount: String,

    @field:Schema(description = "БИК банка", maxLength = 9)
    val bic: String,

    @field:Schema(description = "КПП банка", maxLength = 9)
    val kpp: String
)