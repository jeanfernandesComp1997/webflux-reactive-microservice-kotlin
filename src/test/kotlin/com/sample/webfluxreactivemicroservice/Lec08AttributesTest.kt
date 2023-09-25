package com.sample.webfluxreactivemicroservice

import com.sample.webfluxreactivemicroservice.dto.MathSquareResponse
import com.sample.webfluxreactivemicroservice.dto.MultiplyRequest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.test.StepVerifier

class Lec08AttributesTest : BaseTest() {

    @Autowired
    private lateinit var webClient: WebClient

    @Test
    fun `post test`() {
        val response = webClient
            .post()
            .uri("reactive-math/multiply")
            .bodyValue(buildRequest(7, 5))
            .attribute("auth", "oauth")
            .header("someKey", "someValue")
            .retrieve()
            .bodyToMono<MathSquareResponse>()
            .doOnNext { println(it) }

        StepVerifier
            .create(response)
            .expectNextCount(1)
            .verifyComplete()

        StepVerifier
            .create(response)
            .expectNextCount(1)
            .verifyComplete()
    }

    private fun buildRequest(value1: Int, value2: Int): MultiplyRequest {
        return MultiplyRequest(value1, value2)
    }
}