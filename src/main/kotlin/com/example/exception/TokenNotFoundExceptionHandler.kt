package com.example.exception

import com.example.response.ErrorResponse
import com.example.response.JavaErrorResponse
import io.micronaut.context.annotation.Requires
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Produces
import io.micronaut.http.server.exceptions.ExceptionHandler
import io.micronaut.json.JsonMapper
import io.micronaut.serde.ObjectMapper
import jakarta.inject.Singleton

@Produces
@Singleton
@Requires(classes = [TokenNotFoundException::class, ExceptionHandler::class])
class TokenNotFoundExceptionHandler(private val jsonMapper: JsonMapper) : ExceptionHandler<TokenNotFoundException, HttpResponse<*>> {
    override fun handle(request: HttpRequest<Any>, exception: TokenNotFoundException): HttpResponse<ErrorResponse> {
        println("passou no handler")
        val errorResponse = ErrorResponse(code = 400, message = "Token not found. Please do login again")
        return HttpResponse.badRequest<ErrorResponse>().body(errorResponse)
    }
}