package com.account.domain

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column

object ApplicationFirm : IntIdTable("application_firms") {
    var Name: Column<String> = varchar("name", 100).uniqueIndex()
    var PhoneNumber: Column<String> = varchar("phone_number", 100)
    var Email: Column<String> = varchar("email", 100)
    var Status = enumeration("status", com.account.domain.enum.Status::class)
}