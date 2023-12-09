package com.example.auth.request

import com.fasterxml.jackson.annotation.JsonProperty
import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable

@Serdeable
@Introspected
data class TokenRequest(
    val grant_type: String = "authorization_code",
    val code: String,
    val redirect_uri: String
)