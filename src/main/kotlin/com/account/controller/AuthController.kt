package com.account.controller

import com.account.service.TokenProviderService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.authRoute(tokenProviderService: TokenProviderService) {

    route("/auth") {
        post("/login") {

        }
        authenticate("jwt-token") {
            get("/validate") {

            }
        }
    }
}

fun Route.authController() {
    authenticate("jwt-token") {
        post {
        }
        get {
            call.respond(HttpStatusCode.Accepted, "OK")
        }
    }
}