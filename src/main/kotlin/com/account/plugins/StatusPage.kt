package com.account.plugins

import com.account.exception.AuthenticationException
import com.account.exception.AuthorizationException
import com.account.exception.TokenCreationException
import com.account.exception.UserNotFoundException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureStatusPage() {
    install(StatusPages) {
        exception<AuthenticationException> { call, _ ->
            call.respond(HttpStatusCode.Unauthorized)
        }
        exception<AuthorizationException> { call, _ ->
            call.respond(HttpStatusCode.Forbidden)
        }
        exception<UserNotFoundException> { call, _ ->
            call.respond(HttpStatusCode.NotFound)
        }
        exception<TokenCreationException> { call, _ ->
            call.respond(HttpStatusCode.InternalServerError)
        }
    }
}