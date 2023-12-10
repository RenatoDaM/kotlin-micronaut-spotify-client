package com.example.auth

import com.fasterxml.jackson.annotation.JsonProperty
import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable

@Introspected
@Serdeable
data class UserInfoResponse(
    val display_name: String,
    val external_urls: ExternalUrls,
    val href: String,
    val id: String,
    val images: List<String>,
    val type: String,
    val uri: String,
    val followers: Followers,
    val country: String,
    val product: String,
    val explicit_content: ExplicitContent,
    val email: String
)

@Introspected
@Serdeable
data class ExternalUrls(
    val spotify: String
)

@Introspected
@Serdeable
data class Followers(
    val href: String?,
    val total: Int
)

@Introspected
@Serdeable
data class ExplicitContent(
    val filter_enabled: Boolean,
    val filter_locked: Boolean
)