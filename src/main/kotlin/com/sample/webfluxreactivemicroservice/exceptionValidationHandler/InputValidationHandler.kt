package com.sample.webfluxreactivemicroservice.exceptionValidationHandler

import com.sample.webfluxreactivemicroservice.dto.InputFailedValidationResponse
import com.sample.webfluxreactivemicroservice.exception.InputValidationException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class InputValidationHandler {

    @ExceptionHandler(InputValidationException::class)
    fun handleInputValidationException(
        exception: InputValidationException
    ): ResponseEntity<InputFailedValidationResponse> {
        val response = InputFailedValidationResponse(
            exception.errorCode,
            exception.input,
            exception.message ?: ""
        )

        return ResponseEntity.badRequest().body(response)
    }
}