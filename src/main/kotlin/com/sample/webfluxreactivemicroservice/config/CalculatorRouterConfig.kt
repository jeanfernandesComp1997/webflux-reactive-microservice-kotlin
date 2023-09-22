package com.sample.webfluxreactivemicroservice.config

import com.sample.webfluxreactivemicroservice.exception.InputValidationException
import org.springframework.boot.autoconfigure.rsocket.RSocketProperties.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RequestPredicate
import org.springframework.web.reactive.function.server.RequestPredicates
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.ServerResponse

@Configuration
class CalculatorRouterConfig(
    private val calculatorHandler: CalculatorHandler
) {

    @Bean
    fun highLevelCalculatorRouter(): RouterFunction<ServerResponse> {
        return RouterFunctions
            .route()
            .path("calculator", this::serverResponseRouterFunction)
            .build()
    }

    private fun serverResponseRouterFunction(): RouterFunction<ServerResponse> {
        return RouterFunctions
            .route()
            .GET("{value1}/{value2}", isOperation("+"), calculatorHandler::additionHandler)
            .GET("{value1}/{value2}", isOperation("-"), calculatorHandler::subtractionHandler)
            .GET("{value1}/{value2}", isOperation("*"), calculatorHandler::multiplicationHandler)
            .GET("{value1}/{value2}", isOperation("/"), calculatorHandler::divisionHandler)
            .GET("{value1}/{value2}") { ServerResponse.badRequest().bodyValue("OP should be + - * /") }
            .build()
    }

    private fun isOperation(operation: String): RequestPredicate {
        return RequestPredicates.headers {
            operation == it.asHttpHeaders().toSingleValueMap()["OP"]
        }
    }
}