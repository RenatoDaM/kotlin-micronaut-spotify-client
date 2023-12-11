package com.example.auth

import com.example.auth.request.TokenRequest
import com.example.auth.request.TokenResponse
import com.example.auth.service.AuthService
import com.example.client.SpotifyClient
import com.example.exception.TokenNotFoundException
import com.example.response.ErrorResponse
import io.micronaut.context.annotation.Property
import io.micronaut.context.annotation.Value
import io.micronaut.http.*
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Error
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import io.micronaut.http.annotation.QueryValue
import io.micronaut.http.hateoas.JsonError
import io.micronaut.http.uri.UriBuilder
import io.micronaut.scheduling.TaskExecutors
import io.micronaut.scheduling.annotation.ExecuteOn
import io.micronaut.views.View
import java.net.http.HttpClient.Redirect
import java.util.*
import kotlin.random.Random

@Controller
@ExecuteOn(TaskExecutors.BLOCKING)
class AuthController (
    private val authService: AuthService,
    private val spotifyClient: SpotifyClient,
    @Value("\${spotify.id}")
    private val clientId: String,
    @Value("\${spotify.secret}")
    private val clientSecret: String,
    @Value("\${spotify.callback}")
    private val  callbackUrl: String,
    @Value("\${spotify.basic}")
    private val  basic: String
) {
    private var token: String? = null
    private var refreshToken: String? = null


    @Get("/oauth/login")
    fun doLogin(): MutableHttpResponse<Any> {
        return HttpResponse.seeOther(authService.doLogin())
    }

    @Get("/callback")
    @View("callback")
    fun handleCallback(@QueryValue("code") code: String,  @QueryValue("state") state: String?): Any {
        return authService.handleCallback(code, state)
    }

    @Get("/me")
    fun getUser(): Any {
        return authService.getUser()
    }
}
