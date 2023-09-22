package com.sample.webfluxreactivemicroservice

import com.sample.webfluxreactivemicroservice.dto.MathSquareResponse
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.reactive.function.client.WebClient
import reactor.test.StepVerifier

class Lec01GetSingleResponseTest : BaseTest() {

    @Autowired
    private lateinit var webClient: WebClient

    @Test
    fun `block test`() {
        val response = webClient
            .get()
            .uri("reactive-math/square/{number}", 5)
            .retrieve()
            .bodyToMono(MathSquareResponse::class.java)
            .block()

        println(response)
    }

    @Test
    fun `step verifier test`() {
        val response = webClient
            .get()
            .uri("reactive-math/square/{number}", 5)
            .retrieve()
            .bodyToMono(MathSquareResponse::class.java)

        StepVerifier
            .create(response)
            .expectNextMatches {
                it.output == 25
            }
            .verifyComplete()
    }
}