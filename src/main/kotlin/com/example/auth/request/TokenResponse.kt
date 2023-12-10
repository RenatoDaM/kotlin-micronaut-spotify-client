package com.example.auth.request

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable

@Serdeable
@Introspected
data class TokenResponse(
    val access_token: String?,
    val token_type: String?,
    val scope: String?,
    val expires_in: Int?,
    val refresh_token: String?
)