package com.example.client

import com.example.auth.request.TokenRequest
import com.example.auth.request.TokenResponse
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
import org.reactivestreams.Publisher
import java.util.*

@Client
interface SpotifyClient {
    @Header(name = AUTHORIZATION, value = "Bearer yourToken")
    @Get("https://api.spotify.com/v1/me/top/tracks?time_range=long_term&limit=5")
    @SingleResult
    fun fetchUsersTopItems(): Publisher<UsersTopItems>

    @Header(name = AUTHORIZATION, value = "Basic yourEncoded")
    @Header(name = CONTENT_TYPE, value = MediaType.APPLICATION_FORM_URLENCODED)
    @Post("https://accounts.spotify.com/api/token")
    fun requestToken(@Body tokenRequest: TokenRequest): TokenResponse
}

