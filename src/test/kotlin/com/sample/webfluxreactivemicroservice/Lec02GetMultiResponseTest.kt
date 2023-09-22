package com.sample.webfluxreactivemicroservice

import com.sample.webfluxreactivemicroservice.dto.MathSquareResponse
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToFlux
import reactor.test.StepVerifier

class Lec02GetMultiResponseTest : BaseTest() {

    @Autowired
    private lateinit var webClient: WebClient

    @Test
    fun `flux test`() {
        val response = webClient
            .get()
            .uri("reactive-math/table/{number}", 5)
            .retrieve()
            .bodyToFlux<MathSquareResponse>()
            .doOnNext { println(it) }

        StepVerifier
            .create(response)
            .expectNextCount(10)
            .verifyComplete()
    }

    @Test
    fun `flux stream test`() {
        val response = webClient
            .get()
            .uri("reactive-math/table/{number}/stream", 5)
            .retrieve()
            .bodyToFlux<MathSquareResponse>()
            .doOnNext { println(it) }

        StepVerifier
            .create(response)
            .expectNextCount(10)
            .verifyComplete()
    }
}