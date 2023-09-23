package com.sample.webfluxreactivemicroservice

import com.sample.webfluxreactivemicroservice.dto.InputFailedValidationResponse
import com.sample.webfluxreactivemicroservice.dto.MathSquareResponse
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

class Lec06ExchangeTest : BaseTest() {

    @Autowired
    private lateinit var webClient: WebClient

    // exchange = retrieve + additional info like http status code, but now IS DEPRECATED

    @Test
    fun `bad request test`() {
        val response = webClient
            .get()
            .uri("reactive-math/square/{number}/throw", 25)
            .exchangeToMono(this::exchange)
            .doOnNext { println(it) }
            .doOnError { print(it.message) }

        StepVerifier
            .create(response)
            .expectNextCount(1)
            .verifyComplete()
    }

    private fun exchange(response: ClientResponse): Mono<Any> {
        return if (response.statusCode().is4xxClientError) {
            response.bodyToMono(InputFailedValidationResponse::class.java)
        } else {
            response.bodyToMono(MathSquareResponse::class.java)
        }
    }
}