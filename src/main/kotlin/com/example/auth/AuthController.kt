package com.example.auth

import com.example.auth.request.TokenRequest
import com.example.auth.request.TokenResponse
import com.example.client.SpotifyClient
import io.micronaut.context.annotation.Property
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.QueryValue
import io.micronaut.http.uri.UriBuilder
import io.micronaut.scheduling.TaskExecutors
import io.micronaut.scheduling.annotation.ExecuteOn
import io.micronaut.views.View
import java.util.*
import kotlin.random.Random

@Controller
@ExecuteOn(TaskExecutors.BLOCKING)
class AuthController (private val spotifyClient: SpotifyClient) {
    @Property(name = "spotify.id")
    lateinit var clientId: String
    @Property(name = "spotify.secret")
    lateinit var clientSecret: String
    @Property(name = "spotify.callback")
    lateinit var callbackUrl: String

    @Get("/login")
    fun doLogin() {
        println(clientId)
        val state = generateRandomString(16)
        val authorizeUri = UriBuilder.of("https://accounts.spotify.com/authorize")
            .queryParam("response_type", "code")
            .queryParam("client_id", clientId)
            .queryParam("scope", "user-read-private user-read-email")
            .queryParam("redirect_uri", "http://localhost:8080")
            .queryParam("state", state)
            .build()
        println(authorizeUri.toURL().toString())
        HttpResponse.redirect<Any>(authorizeUri);
    }

    @Get
    @View("callback")
    fun handleCallback(@QueryValue("code") code: String,  @QueryValue("state") state: String?): Any? {
        // FOR DEBUG PURPOSE I PREFERED BLOCKING, BUT LATER ON I WILL CHANGE
        val tokenRequest = TokenRequest("authorization_code",code, "http://localhost:8080")
        println(Base64.getEncoder().encodeToString("$clientId:$clientSecret".toByteArray()))
        val response: TokenResponse = spotifyClient.requestToken(tokenRequest)
        println(response.toString())
        return response
    }

    // ONLY FOR TEST PURPOSE, PLEASE USE A BETTER RANDOM STRING ALGORITHM
    fun generateRandomString(length: Int): String {
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
