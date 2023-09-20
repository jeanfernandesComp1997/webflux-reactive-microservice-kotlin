package com.sample.webfluxreactivemicroservice.service

import com.sample.webfluxreactivemicroservice.dto.MathSquareResponse
import org.springframework.stereotype.Service

@Service
class MathService {

    fun findSquare(input: Int): MathSquareResponse {
        return MathSquareResponse(input * input)
    }

    fun multiplicationTable(input: Int): List<MathSquareResponse> {
        return (1..10).map {
            SleepUtil.sleepSeconds(1)
            println("math service processing: $it")
            MathSquareResponse(it * input)
        }
    }
}