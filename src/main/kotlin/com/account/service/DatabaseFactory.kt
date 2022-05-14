package com.account.service

import com.account.domain.ApplicationFirm
import com.account.domain.ApplicationUser
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.config.*
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Schema
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

class DatabaseFactory(private val config: HoconApplicationConfig) {
    fun initialize() {
        val schemaName = config.property("ktor.security.schemaName").getString()
        val dataSource = hikariDataSource()
        Database.connect(dataSource)
        transaction {
            SchemaUtils.createSchema(Schema(schemaName))
            SchemaUtils.create(tables = arrayOf(ApplicationFirm, ApplicationUser))
        }
        runFlyWay(dataSource)
    }

    private fun runFlyWay(dataSource: HikariDataSource): Unit {
        val flyway = Flyway.configure()
            .dataSource(dataSource)
            .load()
        try {
            flyway.info()
            flyway.migrate()
        } catch (e: Exception) {
            throw e
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