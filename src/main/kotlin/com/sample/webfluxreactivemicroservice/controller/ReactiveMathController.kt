package com.sample.webfluxreactivemicroservice.controller

import com.sample.webfluxreactivemicroservice.dto.MathSquareResponse
import com.sample.webfluxreactivemicroservice.dto.MultiplyRequest
import com.sample.webfluxreactivemicroservice.service.ReactiveMathService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("reactive-math")
class ReactiveMathController(
    private val mathService: ReactiveMathService
) {

    @GetMapping("square/{input}")
    fun findSquare(@PathVariable input: Int): Mono<MathSquareResponse> {
        return mathService.findSquare(input)
    }

    @GetMapping("table/{input}")
    fun multiplicationTable(@PathVariable input: Int): Flux<MathSquareResponse> {
        return mathService.multiplicationTable(input)
    }

    @GetMapping("table/{input}/stream", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun multiplicationTableStream(@PathVariable input: Int): Flux<MathSquareResponse> {
        return mathService.multiplicationTable(input)
    }

    @PostMapping("multiply")
    fun multiply(
        @RequestBody request: Mono<MultiplyRequest>,
        @RequestHeader headers: Map<String, String>
    ): Mono<MathSquareResponse> {
        println("Headers: $headers")
        return mathService.multiply(request)
    }
}