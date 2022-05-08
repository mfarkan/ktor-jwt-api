package com.account.controller

import com.account.model.request.TokenRequest
import com.account.service.TokenProviderService
import com.account.service.UserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.authRoute(tokenProviderService: TokenProviderService, userService: UserService) {

    route("/auth") {
        post("/login") {
            val request = call.receive<TokenRequest>()
            if (request.userName == null || request.password == null)
                return@post call.respond(message = "Check your request body", status = HttpStatusCode.BadRequest)

            val appUser = userService.findUserByNameAndPassword(
                name = request.userName,
                password = request.password
            )

            val accessToken = tokenProviderService.createToken(appUser)
            call.respond(message = accessToken, status = HttpStatusCode.OK)

        }
        authenticate("jwt-token") {
            get("/validate") {
                val principal = call.principal<JWTPrincipal>()
                val username = principal!!.payload.getClaim("name").asString()
                val expiresAt = principal.expiresAt?.time?.minus(System.currentTimeMillis())
                call.respondText("Hello, $username! Token is expired at $expiresAt ms.")
            }
        }
    }
}