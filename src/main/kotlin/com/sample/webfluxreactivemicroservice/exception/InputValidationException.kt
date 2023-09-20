package com.sample.webfluxreactivemicroservice.exception

class InputValidationException(
    val input: Int,
    val errorCode: Int = 100,
    msg: String = "Allowed range is 10 - 20"
) : RuntimeException(msg) {

}