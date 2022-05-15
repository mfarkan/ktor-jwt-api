package db.migration

import com.account.domain.ApplicationFirm
import org.flywaydb.core.api.migration.BaseJavaMigration
import org.flywaydb.core.api.migration.Context
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

class V2__create_fake_firm : BaseJavaMigration() {
    override fun migrate(p0: Context?) {
        transaction {
            ApplicationFirm.insert {
                it[Name] = "TEST-FIRM"
                it[PhoneNumber] = "0123456789"
                it[Status] = com.account.domain.enum.Status.ACTIVE
                it[Email] = "test-test@test.com"
            }
        }
    }
}