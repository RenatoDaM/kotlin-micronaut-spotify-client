package com.example.client

import io.micronaut.security.authentication.AuthenticationResponse
import io.micronaut.security.oauth2.endpoint.token.response.OauthAuthenticationMapper
import io.micronaut.security.oauth2.endpoint.token.response.TokenResponse
import jakarta.inject.Named
import jakarta.inject.Singleton
import reactor.core.publisher.Flux
import io.micronaut.security.oauth2.endpoint.authorization.state.State
import org.reactivestreams.Publisher

@Named("spotify") // (1)
@Singleton
class AuthenticationMapper (
    private val apiClient: SpotifyClient) // (2)
    : OauthAuthenticationMapper {

    override fun createAuthenticationResponse(
        tokenResponse: TokenResponse,
        state: State?
    ): Publisher<AuthenticationResponse> { // (3)
        return Flux.from(apiClient.getUser("Bearer " + tokenResponse.accessToken))
            .map { user ->
                AuthenticationResponse.success(user.email, listOf("ROLE_SPOTIFY")) // (4)
            }
    }
}
