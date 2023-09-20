package com.sample.webfluxreactivemicroservice.service

import com.sample.webfluxreactivemicroservice.dto.MathSquareResponse
import com.sample.webfluxreactivemicroservice.dto.MultiplyRequest
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration

@Service
class ReactiveMathService {

    fun findSquare(input: Int): Mono<MathSquareResponse> {
        return Mono.fromSupplier {
            input * input
        }
            .map {
                MathSquareResponse(it)
            }
    }

    fun multiplicationTable(input: Int): Flux<MathSquareResponse> {
        return Flux
            .range(1, 10)
            .delayElements(Duration.ofSeconds(1))
            .doOnNext { println("math service processing: $it") }
            .map { MathSquareResponse(it * input) }
    }

    fun multiply(request: Mono<MultiplyRequest>): Mono<MathSquareResponse> {
        return request
            .map { it.first * it.second }
            .map { MathSquareResponse(it) }
    }
}