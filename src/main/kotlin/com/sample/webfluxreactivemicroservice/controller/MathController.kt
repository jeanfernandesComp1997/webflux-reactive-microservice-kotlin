package com.sample.webfluxreactivemicroservice.controller

import com.sample.webfluxreactivemicroservice.dto.MathSquareResponse
import com.sample.webfluxreactivemicroservice.service.MathService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("math")
class MathController(
    private val mathService: MathService
) {

    @GetMapping("square/{input}")
    fun findSquare(@PathVariable input: Int): ResponseEntity<MathSquareResponse> {
        return ResponseEntity.ok(mathService.findSquare(input))
    }

    @GetMapping("table/{input}")
    fun multiplicationTable(@PathVariable input: Int): ResponseEntity<List<MathSquareResponse>> {
        return ResponseEntity.ok(mathService.multiplicationTable(input))
    }
}