package com.handsome.api.http.response

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
@Suppress("unused")
class ExceptionsHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(CompanyAlreadyExistsException::class)
    fun handleConflict(ex: Exception, request: WebRequest): ResponseEntity<Any> = ResponseEntity(
        ErrorResponse(
            errorCode = HttpStatus.CONFLICT.value(),
            errorMessage = ex.message ?: "unknown exception",
            errorType = "409 CONFLICT"
        ),
        HttpStatus.CONFLICT
    )

    @ExceptionHandler(Exception::class)
    fun handleAny(ex: Exception, request: WebRequest): ResponseEntity<Any> = ResponseEntity(
        ErrorResponse(
            errorCode = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            errorMessage = ex.message ?: "unknown exception",
            errorType = "unhandled"
        ),
        HttpStatus.INTERNAL_SERVER_ERROR
    )
}

data class ErrorResponse(
    val errorCode: Int,
    val errorType: String,
    val errorMessage: String
)
