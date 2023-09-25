package com.sample.webfluxreactivemicroservice

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

class Lec09AssignmentTest : BaseTest() {

    companion object {
        private const val FORMAT = "%d %s %d = %s"
        private const val VALUE_1 = 10
    }

    @Autowired
    private lateinit var webClient: WebClient

    @Test
    fun test() {
        val flux = Flux.range(1, 5)
            .flatMap { value2 ->
                Flux.just("+", "-", "*", "/")
                    .flatMap { op -> send(value2, op) }
            }
            .doOnNext { println(it) }

        StepVerifier.create(flux)
            .expectNextCount(20)
            .verifyComplete()
    }

    private fun send(value2: Int, operation: String): Mono<String> {
        return webClient
            .get()
            .uri("calculator/{value1}/{value2}", VALUE_1, value2)
            .headers { it.set("OP", operation) }
            .retrieve()
            .bodyToMono(String::class.java)
            .map { String.format(FORMAT, VALUE_1, operation, value2, it) }
    }
}