package com.example.auth

import com.example.auth.request.TokenResponse
import com.example.auth.service.AuthService
import com.example.exception.TokenNotFoundException
import io.micronaut.http.*
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.scheduling.TaskExecutors
import io.micronaut.scheduling.annotation.ExecuteOn
import io.micronaut.session.Session

@Controller
@ExecuteOn(TaskExecutors.BLOCKING)
class AuthController (
    private val authService: AuthService,
) {
    @Get("/oauth/login")
    fun doLogin(session: Session): MutableHttpResponse<Any> {
        return HttpResponse.seeOther(authService.doLogin())
    }

    @Get("/callback")
    fun callback(session: Session, code: String, state: String?): Any {
        val response: TokenResponse = authService.handleCallback(code, state)
        session.put("token", response.access_token)
        session.put("refreshtoken", response.refresh_token)
        return response
    }

    @Get("/me")
    fun getUser(session: Session): Any {
        // Recupere as informações do token da sessão
        val token = session["token", String::class.java].orElse(null)
        println("valor token $token")
        if (token.isNullOrEmpty()) {
            throw TokenNotFoundException()
        }
        return authService.getUser(token)
    }
}
