package com.example.auth

import com.example.auth.request.TokenRequest
import com.example.auth.request.TokenResponse
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
        val state = generateRandomString(16)
        val authorizeUri = UriBuilder.of("https://accounts.spotify.com/authorize")
            .queryParam("response_type", "code")
            .queryParam("client_id", clientId)
            .queryParam("scope", "user-read-private user-read-email")
            .queryParam("redirect_uri", callbackUrl)
            .queryParam("state", state)
            .build()

        return HttpResponse.seeOther(authorizeUri)
    }

    @Get("/callback")
    @View("callback")
    fun handleCallback(@QueryValue("code") code: String,  @QueryValue("state") state: String?): Any {
        // FOR DEBUG PURPOSE I PREFERED BLOCKING, BUT LATER ON I WILL CHANGE
        val tokenRequest = TokenRequest("authorization_code",code, "http://localhost:8080/callback")
        // need to implement state comparison
        val response: TokenResponse = spotifyClient.requestToken(basic, tokenRequest)
        token = response.access_token
        refreshToken = response.refresh_token
        return response
    }



    @Get("/me")
    fun getUser(): Any {
        val myToken = token
        if (token.isNullOrEmpty()) {
            throw TokenNotFoundException()
        }
        return spotifyClient.getUser("Bearer " + myToken!!)
    }


    // ONLY FOR TEST PURPOSE, PLEASE USE A BETTER RANDOM STRING ALGORITHM
    private fun generateRandomString(length: Int): String {
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        val sb = StringBuilder()
        for (i in 1..length) {
            sb.append(chars[Random.nextInt(chars.length)])
        }
        return sb.toString()
    }
}
