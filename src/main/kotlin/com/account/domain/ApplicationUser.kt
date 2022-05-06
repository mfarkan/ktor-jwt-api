package com.account.domain

import com.account.domain.enum.Status
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow

data class ApplicationUserDomain(
    val id: Int, val name: String,
    val email: String, val phoneNumber: String,
    val status: Status, val firmId: Int?
)

object ApplicationUser : IntIdTable("application_users") {
    var Name: Column<String> = varchar("name", 100).uniqueIndex()
    var Password: Column<String> = varchar("password", 100)
    var Email: Column<String> = varchar("email", 100).index()
    var PhoneNumber: Column<String> = varchar("phone_number", 20)
    var Status = enumeration("status", Status::class)
    var FirmId = optReference(
        name = "firm_id",
        foreign = ApplicationFirm
    )
}

fun ApplicationUser.fromRow(resultRow: ResultRow): ApplicationUserDomain = ApplicationUserDomain(
    resultRow[id].value,
    resultRow[Name],
    resultRow[Email],
    resultRow[PhoneNumber],
    resultRow[Status],
    resultRow[FirmId]?.value
)
