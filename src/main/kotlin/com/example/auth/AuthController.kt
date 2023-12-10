package com.example.auth

import com.example.auth.request.TokenRequest
import com.example.auth.request.TokenResponse
import com.example.client.SpotifyClient
import io.micronaut.context.annotation.Property
import io.micronaut.context.annotation.Value
import io.micronaut.http.HttpResponse
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.QueryValue
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
    private val  callbackUrl: String
) {
    private var token: String? = null
    private var refreshToken: String? = null


    @Get("/oauth/login")
    fun doLogin(): MutableHttpResponse<Any> {
        println(clientId)
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
        println(tokenRequest)
        val response: TokenResponse = spotifyClient.requestToken(tokenRequest)
        token = response.access_token
        refreshToken = response.refresh_token
        return response
    }

    @Get("/me")
    fun getUser(): UserInfoResponse {
        val myToken = token
        println("token: $token")
        println("/me path")
        if (token.isNullOrEmpty()) {
            println("é tá null...")
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

/*
@Get
@View("callback")
fun handleCallback(@QueryValue("code") code: String,  @QueryValue("state") state: String?): Any? {
    val authOptions = TokenRequest(code, "http://localhost:8080", "authorization_code")

    // Enviar a solicitação de token usando o cliente HTTP do Micronaut
    val response: HttpResponse<TokenResponse> = client.toBlocking().exchange(
        HttpRequest.POST("", authOptions)
            .header("Content-Type", "application/x-www-form-urlencoded")
            .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("$clientId:$clientSecret".toByteArray()))
    )
    println(response.toString())
    // Faça algo com a resposta...

    return response
}*/
