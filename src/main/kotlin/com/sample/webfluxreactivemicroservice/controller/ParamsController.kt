package com.sample.webfluxreactivemicroservice.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
@RequestMapping("jobs")
class ParamsController {

    @GetMapping("search")
    fun search(
        @RequestParam("count") count: Int,
        @RequestParam("page") page: Int
    ): Flux<Int> {
        return Flux.just(count, page)
    }
}