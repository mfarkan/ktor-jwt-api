package com.account.repository

import com.account.domain.ApplicationUser
import com.account.domain.ApplicationUserDomain
import com.account.domain.fromRow
import com.account.exception.UserNotFoundException
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.suspendedTransactionAsync

class UserRepository {
    @Throws(exceptionClasses = [UserNotFoundException::class])
    suspend fun findUserByUserNameAndPassword(userName: String, password: String): ApplicationUserDomain {
        var userQuery = suspendedTransactionAsync(context = Dispatchers.IO) {
            ApplicationUser.select {
                ApplicationUser.Name.eq(userName) and ApplicationUser.Password.eq(password)
            }.firstOrNull()?.let { ApplicationUser.fromRow(it) }
        }
        val appUser = userQuery.await()
        return appUser ?: throw UserNotFoundException()
    }

    @Throws(exceptionClasses = [UserNotFoundException::class])
    suspend fun findUserByEmail(email: String): ApplicationUserDomain {
        val userQuery = suspendedTransactionAsync(context = Dispatchers.IO) {
            ApplicationUser.select { ApplicationUser.Email eq email }.firstOrNull()?.let {
                ApplicationUser.fromRow(it)
            }
        }
        val appUser = userQuery.await()
        return appUser ?: throw UserNotFoundException()
    }
}