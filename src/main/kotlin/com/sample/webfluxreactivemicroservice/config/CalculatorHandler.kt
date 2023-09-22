package com.sample.webfluxreactivemicroservice.config

import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import java.util.function.BiFunction

@Service
class CalculatorHandler {

    fun additionHandler(request: ServerRequest): Mono<ServerResponse> {
        return process(request) { value1, value2 -> ServerResponse.ok().bodyValue(value1 + value2) }
    }

    fun subtractionHandler(request: ServerRequest): Mono<ServerResponse> {
        return process(request) { value1, value2 -> ServerResponse.ok().bodyValue(value1 - value2) }
    }

    fun multiplicationHandler(request: ServerRequest): Mono<ServerResponse> {
        return process(request) { value1, value2 -> ServerResponse.ok().bodyValue(value1 * value2) }
    }

    fun divisionHandler(request: ServerRequest): Mono<ServerResponse> {
        return process(request) { value1, value2 ->
            if (value2 != 0) {
                ServerResponse.ok().bodyValue(value1 / value2)
            } else {
                ServerResponse.badRequest().bodyValue("Value2 can not be 0")
            }
        }
    }

    private fun process(
        request: ServerRequest,
        operationLogic: BiFunction<Int, Int, Mono<ServerResponse>>
    ): Mono<ServerResponse> {
        val value1 = getValue(request, "value1")
        val value2 = getValue(request, "value2")
        return operationLogic.apply(value1, value2)
    }

    private fun getValue(request: ServerRequest, key: String): Int {
        return request.pathVariable(key).toInt()
    }
}