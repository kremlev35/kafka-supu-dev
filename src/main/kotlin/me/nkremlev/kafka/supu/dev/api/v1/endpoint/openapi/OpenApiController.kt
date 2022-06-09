package me.nkremlev.kafka.supu.dev.api.v1.endpoint.openapi

import io.swagger.v3.oas.annotations.Operation
import org.springdoc.core.GroupedOpenApi
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ServerWebExchange

@RestController
@RequestMapping("\${springdoc.api-docs.path}")
class OpenApiController(
    _apis: List<GroupedOpenApi>,
    @Value("\${springdoc.api-docs.path}")
    private val path: String
) {
    val apis = _apis.map {
        ApiInfo(
            group = it.group,
            url = "${path}/${it.group}"
        )
    }

    data class ApiInfo(val group: String, val url: String)

    @Operation(hidden = true)
    @GetMapping
    fun groupInfo(exchange: ServerWebExchange): Any = if (apis.size == 1) {
        ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT)
            .header(
                HttpHeaders.LOCATION,
                "${exchange.request.uri.scheme}://${exchange.request.uri.authority}${apis.first().url}")
            .build<Unit>()
    } else {
        apis
    }
}