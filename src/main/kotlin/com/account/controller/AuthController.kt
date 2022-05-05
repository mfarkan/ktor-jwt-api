package com.account.controller

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.authRoute() {

    route("/auth") {
        authController()
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