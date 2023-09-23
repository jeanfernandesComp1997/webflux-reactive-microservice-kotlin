package com.sample.webfluxreactivemicroservice

import com.sample.webfluxreactivemicroservice.dto.MathSquareResponse
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException.BadRequest
import reactor.test.StepVerifier

class Lec05BadRequestTest : BaseTest() {

    @Autowired
    private lateinit var webClient: WebClient

    @Test
    fun `bad request test`() {
        val response = webClient
            .get()
            .uri("reactive-math/square/{number}/throw", 25)
            .retrieve()
            .bodyToMono(MathSquareResponse::class.java)
            .doOnNext { println(it) }
            .doOnError {
                println(it.message)
            }

        StepVerifier
            .create(response)
            .verifyError(BadRequest::class.java)
    }
}