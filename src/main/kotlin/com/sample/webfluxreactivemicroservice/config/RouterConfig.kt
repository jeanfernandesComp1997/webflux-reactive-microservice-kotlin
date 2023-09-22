package com.sample.webfluxreactivemicroservice.config

import com.sample.webfluxreactivemicroservice.dto.InputFailedValidationResponse
import com.sample.webfluxreactivemicroservice.exception.InputValidationException
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RequestPredicate
import org.springframework.web.reactive.function.server.RequestPredicates
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import java.util.function.BiFunction

@Configuration
class RouterConfig(
    private val requestHandler: RequestHandler
) {

    @Bean
    fun highLevelRouter(): RouterFunction<ServerResponse> {
        return RouterFunctions
            .route()
            .path("router", this::serverResponseRouterFunction)
            .build()
    }

    private fun serverResponseRouterFunction(): RouterFunction<ServerResponse> {
        return RouterFunctions
            .route()
            .GET(
                "square/{input}",
                RequestPredicates.path("*/1?").or(RequestPredicates.path("*/20")),
                requestHandler::squareHandler
            )
            .GET("square/{input}") { ServerResponse.badRequest().bodyValue("only 10-19 allowed") }
            .GET("table/{input}", requestHandler::tableHandler)
            .GET("table/{input}/stream", requestHandler::tableStreamHandler)
            .POST("multiply", requestHandler::multiplyHandler)
            .GET("square/{input}/validation", requestHandler::squareHandlerWithValidation)
            .onError(InputValidationException::class.java, exceptionHandler())
            .build()
    }

    private fun exceptionHandler(): BiFunction<Throwable, ServerRequest, Mono<ServerResponse>> {
        return BiFunction { error, request ->
            val exception = error as InputValidationException
            val response = InputFailedValidationResponse(
                exception.errorCode,
                exception.input,
                exception.message ?: ""
            )
            ServerResponse.badRequest().bodyValue(response)
        }
    }
}