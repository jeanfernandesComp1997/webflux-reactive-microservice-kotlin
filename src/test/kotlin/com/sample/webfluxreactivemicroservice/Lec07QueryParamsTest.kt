package com.sample.webfluxreactivemicroservice

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriComponentsBuilder
import reactor.test.StepVerifier

class Lec07QueryParamsTest : BaseTest() {

    @Autowired
    private lateinit var webClient: WebClient

    var queryString = "http://localhost:8080/jobs/search?count={count}&page={page}"

    @Test
    fun `query parameters test`() {
//        val uri = UriComponentsBuilder
//            .fromUriString(queryString)
//            .build(10, 20)

        val params = mapOf(
            "count" to 10,
            "page" to 20
        )

        val response = webClient
            .get()
            .uri {
                it.path("jobs/search")
                    .query("count={count}&page={page}")
                    //.build(10, 20)
                    .build(params)
            }
            //.uri(uri)
            .retrieve()
            .bodyToFlux(Int::class.java)
            .doOnNext { println(it) }

        StepVerifier.create(response)
            .expectNextCount(2)
            .verifyComplete()
    }
}