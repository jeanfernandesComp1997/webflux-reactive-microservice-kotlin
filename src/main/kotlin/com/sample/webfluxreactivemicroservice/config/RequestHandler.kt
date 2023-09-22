package com.sample.webfluxreactivemicroservice.config

import com.sample.webfluxreactivemicroservice.dto.MathSquareResponse
import com.sample.webfluxreactivemicroservice.dto.MultiplyRequest
import com.sample.webfluxreactivemicroservice.exception.InputValidationException
import com.sample.webfluxreactivemicroservice.service.ReactiveMathService
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Service
class RequestHandler(
    private val mathService: ReactiveMathService
) {

    fun squareHandler(serverRequest: ServerRequest): Mono<ServerResponse> {
        val input = serverRequest.pathVariable("input").toInt()
        val response = this.mathService.findSquare(input)
        return ServerResponse.ok().body(response, MathSquareResponse::class.java)
    }

    fun tableHandler(serverRequest: ServerRequest): Mono<ServerResponse> {
        val input = serverRequest.pathVariable("input").toInt()
        val response = this.mathService.multiplicationTable(input)
        return ServerResponse.ok().body(response, MathSquareResponse::class.java)
    }

    fun tableStreamHandler(serverRequest: ServerRequest): Mono<ServerResponse> {
        val input = serverRequest.pathVariable("input").toInt()
        val response = this.mathService.multiplicationTable(input)
        return ServerResponse
            .ok()
            .contentType(MediaType.TEXT_EVENT_STREAM)
            .body(response, MathSquareResponse::class.java)
    }

    fun multiplyHandler(serverRequest: ServerRequest): Mono<ServerResponse> {
        val request = serverRequest.bodyToMono(MultiplyRequest::class.java)
        val response = mathService.multiply(request)
        return ServerResponse
            .ok()
            .body(response, MathSquareResponse::class.java)
    }

    fun squareHandlerWithValidation(serverRequest: ServerRequest): Mono<ServerResponse> {
        val input = serverRequest.pathVariable("input").toInt()
        if (input < 10 || input > 20) {
            return Mono.error(InputValidationException(input))
        }
        val response = this.mathService.findSquare(input)
        return ServerResponse.ok().body(response, MathSquareResponse::class.java)
    }
}