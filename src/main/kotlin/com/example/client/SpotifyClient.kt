package com.example.client

import com.example.auth.request.TokenRequest
import com.example.auth.request.TokenResponse
import com.example.auth.response.UserInfoResponse
import com.example.client.response.UsersTopItems
import io.micronaut.context.annotation.Property
import io.micronaut.core.async.annotation.SingleResult
import io.micronaut.http.HttpHeaders.*
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Header
import io.micronaut.http.annotation.Headers
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.annotation.Client
import io.micronaut.scheduling.TaskExecutors
import io.micronaut.scheduling.annotation.ExecuteOn
import org.reactivestreams.Publisher
import java.util.*

@Client
@ExecuteOn(TaskExecutors.BLOCKING)
interface SpotifyClient {
    @Get("https://api.spotify.com/v1/me/top/tracks?time_range=long_term&limit=5")
    @SingleResult
    fun fetchUsersTopItems(@Header("Authorization") authorization: String): Publisher<UsersTopItems>

    @Get("https://api.spotify.com/v1/me")
    @SingleResult
    fun getUser(@Header("Authorization") authorization: String): Publisher<UserInfoResponse>


    @Header(name = AUTHORIZATION, value = "Basic =")
    @Header(name = CONTENT_TYPE, value = MediaType.APPLICATION_FORM_URLENCODED)
    @Post("https://accounts.spotify.com/api/token")
    fun requestToken(@Body tokenRequest: TokenRequest): Publisher<TokenResponse>
}

