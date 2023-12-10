package com.example.auth

import io.micronaut.core.annotation.Introspected

@Introspected
class SpotifyUser {
    lateinit var login: String
    var name: String? = null
    var email: String? = null
}