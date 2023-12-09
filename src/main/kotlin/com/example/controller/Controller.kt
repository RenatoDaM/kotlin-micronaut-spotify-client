package com.example.controller

import com.example.client.SpotifyClient
import com.example.client.response.UsersTopItems
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import org.reactivestreams.Publisher

@Controller
class Controller (private val client: SpotifyClient) {
    @Get("users/topItems")
    fun fetchUsersTopItems(): Publisher<UsersTopItems> {
        return client.fetchUsersTopItems()
    }
}