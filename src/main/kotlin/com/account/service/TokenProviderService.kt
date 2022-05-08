package com.account.service

import com.account.domain.ApplicationUserDomain
import com.account.exception.TokenCreationException
import com.account.model.response.TokenResponse
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.config.*
import io.ktor.util.date.*
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*

class TokenProviderService(private val config: HoconApplicationConfig) {
    private val log: Logger = LoggerFactory.getLogger(TokenProviderService::class.java)

    @Throws(exceptionClasses = [TokenCreationException::class])
    suspend fun createToken(applicationUserResponse: ApplicationUserDomain): TokenResponse = coroutineScope {
        var accessToken: String
        val createdAt = getTimeMillis()
        try {
            async {
                val subject = config.tryGetString("jwt.subject")
                val audience = config.tryGetString("jwt.audience")
                val domain = config.tryGetString("jwt.domain")
                val secretKey = config.property("ktor.security.secret").getString()
                val expireInMs = 1_200_000L

                JWT.create()
                    .withAudience(audience)
                    .withIssuer(domain)
                    .withExpiresAt(Date(createdAt + expireInMs))
                    .withSubject(subject)
                    .withClaim("email", applicationUserResponse.email)
                    .withClaim("name", applicationUserResponse.name)
                    .withClaim("phoneNumber", applicationUserResponse.phoneNumber)
                    .withClaim("firmId", applicationUserResponse.firmId)
                    .sign(Algorithm.HMAC512(secretKey))
            }.also {
                accessToken = it.await()
            }
        } catch (ex: Exception) {
            log.warn("Something failed when creating access token for $${ex.message}")
            throw TokenCreationException()
        }
        TokenResponse(accessToken, createdAt)
    }
}