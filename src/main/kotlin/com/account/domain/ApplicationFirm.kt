package com.account.domain

import com.account.domain.enum.Status
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow

data class ApplicationFirmDomain(var name: String, var phoneNumber: String, var email: String, var status: Status)

object ApplicationFirm : IntIdTable("application_firms") {
    var Name: Column<String> = varchar("name", 100).uniqueIndex()
    var PhoneNumber: Column<String> = varchar("phone_number", 100)
    var Email: Column<String> = varchar("email", 100)
    var Status = enumeration("status", com.account.domain.enum.Status::class)
}

fun ApplicationFirm.fromRow(resultRow: ResultRow): ApplicationFirmDomain = ApplicationFirmDomain(
    resultRow[Name],
    resultRow[PhoneNumber],
    resultRow[Email],
    resultRow[Status]
)