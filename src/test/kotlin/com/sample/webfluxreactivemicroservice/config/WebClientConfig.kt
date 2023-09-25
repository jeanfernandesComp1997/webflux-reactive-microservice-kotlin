package com.sample.webfluxreactivemicroservice.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.ClientRequest
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.ExchangeFunction
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Configuration
class WebClientConfig {

    @Bean
    fun webClient(): WebClient {
        return WebClient
            .builder()
            .baseUrl("http://localhost:8080")
            //.defaultHeaders { it.setBasicAuth("username", "password") }
            .filter(this::sessionToken)
            .build()
    }

//    private fun sessionToken(request: ClientRequest, exchangeFunction: ExchangeFunction): Mono<ClientResponse> {
//        println("generating session token")
//        val clientRequest = ClientRequest.from(request)
//            .headers { it.setBearerAuth("some-lengthy-token") }
//            .build()
//        return exchangeFunction.exchange(clientRequest)
//    }

    private fun sessionToken(request: ClientRequest, exchangeFunction: ExchangeFunction): Mono<ClientResponse> {
        // auth --> basic or auth
        val clientRequest = request.attribute("auth")
            .map { if (it == "basic") withBasicAuthentication(request) else withOAuth(request) }
            .orElse(request)
        return exchangeFunction.exchange(clientRequest)
    }

    private fun withBasicAuthentication(request: ClientRequest): ClientRequest {
        return ClientRequest.from(request)
            .headers { it.setBasicAuth("username", "password") }
            .build()
    }

    private fun withOAuth(request: ClientRequest): ClientRequest {
        return ClientRequest.from(request)
            .headers { it.setBearerAuth("some-lengthy-token") }
            .build()
    }
}