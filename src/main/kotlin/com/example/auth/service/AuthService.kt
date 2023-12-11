package com.example.auth.service

import com.example.auth.request.TokenRequest
import com.example.auth.request.TokenResponse
import com.example.client.SpotifyClient
import com.example.exception.TokenNotFoundException
import io.micronaut.context.annotation.Value
import io.micronaut.core.annotation.NonNull
import io.micronaut.http.HttpResponse
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.QueryValue
import io.micronaut.http.uri.UriBuilder
import io.micronaut.views.View
import jakarta.inject.Singleton
import java.net.URI
import kotlin.random.Random

@Singleton
class AuthService (
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


    fun doLogin(): URI {
        val state = generateRandomString(16)
        val authorizeUri = UriBuilder.of("https://accounts.spotify.com/authorize")
            .queryParam("response_type", "code")
            .queryParam("client_id", clientId)
            .queryParam("scope", "user-read-private user-read-email")
            .queryParam("redirect_uri", callbackUrl)
            .queryParam("state", state)
            .build()

        return authorizeUri
    }

    fun handleCallback(code: String,state: String?): Any {
        // FOR DEBUG PURPOSE I PREFERED BLOCKING, BUT LATER ON I WILL CHANGE
        val tokenRequest = TokenRequest("authorization_code",code, "http://localhost:8080/callback")
        // need to implement state comparison
        val response: TokenResponse = spotifyClient.requestToken(basic, tokenRequest)
        token = response.access_token
        refreshToken = response.refresh_token
        return response
    }



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