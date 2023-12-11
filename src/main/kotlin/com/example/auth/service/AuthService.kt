package com.example.auth.service

import com.example.auth.request.TokenRequest
import com.example.auth.request.TokenResponse
import com.example.client.SpotifyClient
import com.example.exception.TokenNotFoundException
import io.micronaut.context.annotation.Value
import io.micronaut.http.uri.UriBuilder
import jakarta.inject.Singleton
import java.net.URI
import java.util.*
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

    fun handleCallback(code: String, state: String?): TokenResponse {
        // FOR DEBUG PURPOSE I PREFERED BLOCKING, BUT LATER ON I WILL CHANGE
        val tokenRequest = TokenRequest("authorization_code",code, "http://localhost:8080/callback")
        // need to implement state comparison
        val response: TokenResponse = spotifyClient.requestToken(basic, tokenRequest)
        return response
    }

    fun getUser(token: String): Any {
        return spotifyClient.getUser("Bearer $token")
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