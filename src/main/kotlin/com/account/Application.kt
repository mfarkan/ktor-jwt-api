package com.account

import com.account.controller.authRoute
import com.account.plugins.configureSecurity
import com.account.plugins.configureSerialization
import com.account.plugins.configureStatusPage
import com.account.repository.UserRepository
import com.account.service.DatabaseFactory
import com.account.service.TokenProviderService
import com.account.service.UserService
import com.typesafe.config.ConfigFactory
import io.ktor.server.application.*
import io.ktor.server.config.*
import io.ktor.server.routing.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    configureSecurity()
    configureSerialization()
    configureStatusPage()

    val config = HoconApplicationConfig(ConfigFactory.load())
    DatabaseFactory(config).initialize()
    val tokenProviderService = TokenProviderService(config)
    val userRepository = UserRepository()
    val userService = UserService(userRepository)
    routing {
        route("api") {
            authRoute(tokenProviderService, userService)
        }
    }
}
