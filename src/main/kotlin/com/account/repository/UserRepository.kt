package com.account.repository

import com.account.domain.ApplicationFirm
import com.account.domain.ApplicationUser
import com.account.domain.ApplicationUserDomain
import com.account.domain.fromRow
import com.account.exception.UserNotFoundException
import com.account.model.response.ApplicationUserResponse
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.suspendedTransactionAsync

class UserRepository {
    @Throws(exceptionClasses = [UserNotFoundException::class])
    suspend fun findUserByUserNameAndPassword(userName: String, password: String): ApplicationUserDomain {
        var userPromise = suspendedTransactionAsync(context = Dispatchers.IO) {
            ApplicationUser.select {
                ApplicationUser.Name.eq(userName) and ApplicationUser.Password.eq(password)
            }.firstOrNull()?.let { ApplicationUser.fromRow(it) }
        }
        val appUser = userPromise.await()
        return appUser ?: throw UserNotFoundException()
    }

    @Throws(exceptionClasses = [UserNotFoundException::class])
    suspend fun findUserByName(userName: String): ApplicationUserResponse {
        var userPromise = suspendedTransactionAsync(context = Dispatchers.IO) {
            (ApplicationUser innerJoin ApplicationFirm).slice(
                ApplicationUser.id, ApplicationUser.Name, ApplicationUser.Email,
                ApplicationUser.PhoneNumber, ApplicationUser.Status, ApplicationUser.FirmId,
                ApplicationFirm.Name, ApplicationFirm.Email, ApplicationFirm.PhoneNumber
            ).select {
                ApplicationUser.Name.eq(userName)
            }.firstOrNull()?.let {
                ApplicationUserResponse(
                    id = it[ApplicationUser.id].value,
                    name = it[ApplicationUser.Name],
                    email = it[ApplicationUser.Email],
                    phoneNumber = it[ApplicationUser.PhoneNumber],
                    status = it[ApplicationUser.Status],
                    firmId = it[ApplicationUser.FirmId]?.value,
                    firmName = it[ApplicationFirm.Name],
                    firmEmail = it[ApplicationFirm.Email],
                    firmPhoneNumber = it[ApplicationFirm.PhoneNumber]
                )
            }
        }
        val appUser = userPromise.await()
        return appUser ?: throw UserNotFoundException()
    }

    @Throws(exceptionClasses = [UserNotFoundException::class])
    suspend fun findUserByEmail(email: String): ApplicationUserDomain {
        val userPromise = suspendedTransactionAsync(context = Dispatchers.IO) {
            ApplicationUser.select { ApplicationUser.Email eq email }.firstOrNull()?.let {
                ApplicationUser.fromRow(it)
            }
        }
        val appUser = userPromise.await()
        return appUser ?: throw UserNotFoundException()
    }
}