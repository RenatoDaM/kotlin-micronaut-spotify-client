package com.example.client.response

import com.fasterxml.jackson.annotation.JsonProperty
import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class UsersTopItems (
    val href: String,
    val limit: Int,
    val next: String?,
    val offset: Int,
    val previous: String?,
    val total: Int,
    val items: List<SpotifyShow>
)
@Serdeable
data class SpotifyShow(
    @JsonProperty("external_urls")
    val externalUrls: ExternalUrls,
    val followers: Followers,
    val genres: List<String>,
    val href: String,
    val id: String,
    val images: List<Image>,
    val name: String,
    val popularity: Int,
    val type: String,
    val uri: String
)
@Serdeable
data class ExternalUrls(
    val spotify: String
)
@Serdeable
data class Followers(
    val href: String,
    val total: Int
)
@Serdeable
data class Image(
    val url: String,
    val height: Int,
    val width: Int
)

