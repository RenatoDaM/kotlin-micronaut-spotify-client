package com.example.auth.response

import com.fasterxml.jackson.annotation.JsonProperty
import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable

@Introspected
@Serdeable
data class UserInfoResponse(
    @JsonProperty("display_name") val displayName: String,
    @JsonProperty("external_urls") val externalUrls: ExternalUrls,
    val href: String,
    val id: String,
    val images: List<String>,
    val type: String,
    val uri: String,
    val followers: Followers,
    val country: String,
    val product: String,
    @JsonProperty("explicit_content") val explicitContent: ExplicitContent,
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
    @JsonProperty("filter_enabled") val filterEnabled: Boolean,
    @JsonProperty("filter_locked") val filterLocked: Boolean
)
