package com.sample.webfluxreactivemicroservice.dto

data class InputFailedValidationResponse(
    val code: Int,
    val input: Int,
    val message: String
)