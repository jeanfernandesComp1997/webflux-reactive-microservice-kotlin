package com.sample.webfluxreactivemicroservice.controller

import com.sample.webfluxreactivemicroservice.dto.MathSquareResponse
import com.sample.webfluxreactivemicroservice.exception.InputValidationException
import com.sample.webfluxreactivemicroservice.service.ReactiveMathService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("reactive-math")
class ReactiveMathValidationController(
    private val mathService: ReactiveMathService
) {

    @GetMapping("square/{input}/throw")
    fun findSquare(@PathVariable input: Int): Mono<MathSquareResponse> {
        if (input < 10 || input > 20) {
            throw InputValidationException(input)
        }

        return mathService.findSquare(input)
    }

    @GetMapping("square/{input}/mono-error")
    fun monoError(@PathVariable input: Int): Mono<MathSquareResponse> {
        return Mono.just(input)
            .handle { int, sink ->
                if (int in 10..20) {
                    sink.next(int)
                } else {
                    sink.error(InputValidationException(int))
                }
            }
            .flatMap {
                mathService.findSquare(it)
            }
    }
}