package com.account.migration

import com.account.domain.ApplicationUser
import org.flywaydb.core.api.migration.BaseJavaMigration
import org.flywaydb.core.api.migration.Context
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

class V1Migration : BaseJavaMigration() {
    override fun migrate(context: Context) {
        transaction {
            ApplicationUser.insert {
                it[Name] = "test-user"
                it[Password] = "123456"
                it[Email] = "test@test.com"
                it[PhoneNumber] = "0123456789"
                it[Status] = com.account.domain.enum.Status.ACTIVE
            }
        }
    }
}