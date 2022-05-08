package com.account.service

import com.account.domain.ApplicationFirm
import com.account.domain.ApplicationUser
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.config.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

class DatabaseFactory(private val config: HoconApplicationConfig) {
    fun initialize() {
        Database.connect(hikariDataSource())
        transaction {
            SchemaUtils.create(tables = arrayOf(ApplicationFirm, ApplicationUser))
        }

    }

    private fun hikariDataSource(): HikariDataSource {
        val hikariDataSource = HikariDataSource()
        hikariDataSource.password = config.property("ktor.security.password").getString()
        hikariDataSource.jdbcUrl = config.property("ktor.security.connection-string").getString()
        hikariDataSource.username = config.property("ktor.security.userName").getString()
        hikariDataSource.schema = "public"
        hikariDataSource.driverClassName = "org.postgresql.Driver"
        return hikariDataSource
    }
}