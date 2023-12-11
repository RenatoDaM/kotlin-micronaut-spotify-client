package com.example.client

import com.example.auth.UserInfoResponse
import com.example.auth.request.TokenRequest
import com.example.auth.request.TokenResponse
import com.example.client.response.UsersTopItems
import io.micronaut.context.annotation.Property
import io.micronaut.core.async.annotation.SingleResult
import io.micronaut.http.HttpHeaders
import io.micronaut.http.HttpHeaders.*
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import io.micronaut.http.client.annotation.Client
import io.micronaut.scheduling.TaskExecutors
import io.micronaut.scheduling.annotation.ExecuteOn
import org.reactivestreams.Publisher
import java.net.URI
import java.util.*

@Client
@ExecuteOn(TaskExecutors.BLOCKING)
interface SpotifyClient {
    @Header(name = AUTHORIZATION, value = "Bearer yourToken")
    @Get("https://api.spotify.com/v1/me/top/tracks?time_range=long_term&limit=5")
    @SingleResult
    fun fetchUsersTopItems(): Publisher<UsersTopItems>

    @Header(name = CONTENT_TYPE, value = MediaType.APPLICATION_FORM_URLENCODED)
    @Post("https://accounts.spotify.com/api/token")
    fun requestToken(@Header(AUTHORIZATION) authorization: String, @Body tokenRequest: TokenRequest): TokenResponse

    // I have implemented login in another way, maybe I can change for calling this function
    /*@Get("https://accounts.spotify.com/authorize")
    fun doLogin(
        @QueryValue("state") state: String?,
        @QueryValue("response_type") responseType: String = "code",
        @QueryValue("client_id") clientId: String,
        @QueryValue("scope") scope: String = "user-read-private user-read-email",
        @QueryValue("redirect_uri") redirectUri: String = "http://localhost:8080/callback"
    )*/

    @Get("https://api.spotify.com/v1/me")
    fun getUser(@Header("Authorization") authorization: String): UserInfoResponse
}

