package com.account.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.config.*
import io.ktor.server.response.*
import java.util.*

fun Application.configureSecurity() {
    val jwtConfig = environment.config.config("jwt")

    val jwtAudience = jwtConfig.tryGetString("audience")
    val configRealm = jwtConfig.tryGetString("realm")
    val domain = jwtConfig.tryGetString("domain")
    val subjectConfig = jwtConfig.tryGetString("subject")
    val secretKey = environment.config.property("ktor.security.secret").getString()
    val expireInMs = 1_200_000L

    install(Authentication) {
        jwt("jwt-token") {
            realm = configRealm.orEmpty()
            verifier(
                JWT
                    .require(Algorithm.HMAC512(secretKey))
                    .withAudience(jwtAudience)
                    .withSubject(subjectConfig)
                    .withIssuer(domain)
                    .build()
            )
            validate { credential ->
                if (credential.payload.audience.contains(jwtAudience)
                    && credential.payload.expiresAt > Date(System.currentTimeMillis() + expireInMs)
                )
                    JWTPrincipal(credential.payload) else null
            }
            // if the token is invalid or not set, this will be triggered.
            challenge { _, _ ->
                call.respond(HttpStatusCode.Unauthorized, "The token is invalid.")
            }
        }
    }
}
