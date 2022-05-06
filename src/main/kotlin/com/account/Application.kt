package com.account

import com.account.controller.authRoute
import com.account.plugins.configureSecurity
import com.account.plugins.configureSerialization
import com.account.plugins.configureStatusPage
import com.account.service.DatabaseFactory
import com.account.service.TokenProviderService
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    configureSecurity()
    configureSerialization()
    configureStatusPage()

    DatabaseFactory().initialize()
    val tokenProviderService = TokenProviderService()

    routing {
        route("api") {
            authRoute(tokenProviderService)
        }
    }
}
