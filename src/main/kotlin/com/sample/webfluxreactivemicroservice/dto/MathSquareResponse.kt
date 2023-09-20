package com.sample.webfluxreactivemicroservice.dto

import java.util.Date

data class MathSquareResponse(
    val output: Int,
    val date: Date? = Date()
)